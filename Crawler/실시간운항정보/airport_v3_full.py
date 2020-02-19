#!/usr/bin/env python
# coding: utf-8

# In[5]:


import os
import sys
import time
import sqlite3
import pandas as pd
from datetime import datetime
from dateutil.relativedelta import relativedelta
from selenium import webdriver
from selenium.webdriver.support.ui import Select


# In[58]:


conn = sqlite3.connect('airport.db')
cursor = conn.cursor()


# In[59]:


cursor.execute('''
    CREATE TABLE IF NOT EXISTS AIRPORT (
        기준일자 TEXT,
        공항 TEXT,
        항공사 TEXT,
        편명 TEXT,
        목적지 TEXT,
        계획 TEXT,
        예상 TEXT,
        출발 TEXT,
        구분 TEXT,
        현황 TEXT,
        비정상원인 TEXT,
        비고 TEXT
    )
''')

cursor.execute('''
    CREATE TABLE IF NOT EXISTS EXCEPTION (
        기준일자 TEXT,
        공항 TEXT
    )
''')


# In[67]:

# delay
delay = 0.5

k = 1

# year, quarter
# year = int(sys.argv[1])
# div = sys.argv[2]

# column name
# column_name = ['기준일자', '공항', '항공사', '편명', '목적지', '계획', '예상', '출발', '구분', '현황', '비정상원인', '비고']

# ports
# '인천' 제외
ports = ['김포', '청주', '양양', '군산', '원주', '김해', '제주', '대구', '광주', '여수', '울산', '포항', '사천', '무안']


# dates
dates = []
# if div == '1Q':
#     start_month, end_month = 1, 3
# elif div == '2Q':
#     start_month, end_month = 4, 6
# elif div == '3Q':
#     start_month, end_month = 7, 9
# elif div == '4Q':
#     start_month, end_month = 10, 12
# elif div == '1H':
#     start_month, end_month = 1, 6
# elif div == '2H':
#     start_month, end_month = 7, 12
# elif div == '1Y':
#     start_month, end_month = 1, 12
# else:
#     raise Exception('분기 혹은 반기를 다시 입력해주세요.')
    
# start_date = datetime(year, start_month, 1)
# end_date = datetime(year, end_month, 1) + relativedelta(months=1) - relativedelta(days=1)
start_date = datetime(2015, 1, 1)
end_date = datetime(2020, 1, 31)

date = start_date
while(date <= end_date):
    dates.append(date.strftime('%Y%m%d'))
    date = date + relativedelta(days=1)

# loop(filtering)
cursor.execute('SELECT DISTINCT 기준일자, 공항 FROM AIRPORT')
ended = set(cursor.fetchall())
total = set([(date, port) for date in dates for port in ports])
loop = total - ended
cursor.execute('SELECT DISTINCT 기준일자, 공항 FROM EXCEPTION')
exc = set(cursor.fetchall())
loop = list(loop - exc)
# print('{:,.0f}번의 수집을 시도합니다. (기준년도: {}, 분기(반기): {}, 딜레이: {}초)'.format(len(loop), year, div, delay))
print('{:,.0f}번의 수집을 시도합니다. (딜레이: {}초)'.format(len(loop), delay))
print('Total: {:,.0f}, Ended: {:,.0f}, Exception: {:,.0f}, Net: {:,.0f}'.format(len(total), len(ended), len(exc), len(loop)))

# In[68]:


options = webdriver.ChromeOptions()
options.add_argument('headless')
options.add_argument("disable-gpu")
driver = webdriver.Chrome('chromedriver', options=options)



# In[ ]:


data = []
exceptions = []
log = ''
try:
    for date, port in loop:
        driver.get('http://www.airportal.go.kr/life/airinfo/RbBejFrm.jsp')
        driver.find_elements_by_css_selector('input[name="depArr"]')[1].click()
        driver.execute_script('''
            x = $("#current_date").focus();
            x[0]['value'] = {};
        '''.format(date))
        Select(driver.find_element_by_css_selector('select[name="airport"]')).select_by_visible_text(port)
        driver.execute_script('go_search()')
        time.sleep(delay)
        driver.switch_to.frame(driver.find_element_by_tag_name('iframe'))

        rows = driver.find_elements_by_tag_name('table')[1].find_elements_by_tag_name('tr')
        bulk = []
        if len(rows) == 4:
            exceptions.append([date, port])
            text = '{:3}: [{}, {}]  검색된 결과가 없습니다.'.format(k, date, port)
            print(text)
            log += text + '\n'
            k += 1
        else:
            for row in rows:
                if len(row.find_elements_by_tag_name('td')) == 1: continue
                cols = row.find_elements_by_css_selector('td[width$="0"]')
                bulk.append([date, port]+[x.text for x in cols])
            data += bulk
            text = '{:3}: [{}, {}] {:3}건의 결과가 검색되었습니다.'.format(k, date, port, len(bulk))
            print(text)
            log += text + '\n'
            k += 1
except:
    print('수집: {:,.0f}개, 무효: {:,.0f}개'.format(len(data), len(exceptions)))
    cursor.executemany('INSERT INTO AIRPORT VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)', data)
    cursor.executemany('INSERT INTO EXCEPTION VALUES (?, ?)', exceptions)
    conn.commit()
    conn.close()

    if not any([s=='log' for s in os.listdir('.')]):
        os.mkdir('log')
    
    now = datetime.now().strftime('%Y%m%d%H%M%S')
    with open('log/airport_{}_{}.log'.format('full', now), 'a') as f:
        f.write(log)
else:
    print('수집: {:,.0f}개, 무효: {:,.0f}개'.format(len(data), len(exceptions)))
    cursor.executemany('INSERT INTO AIRPORT VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)', data)
    cursor.executemany('INSERT INTO EXCEPTION VALUES (?, ?)', exceptions)
    conn.commit()
    conn.close()

    if not any([s=='log' for s in os.listdir('.')]):
        os.mkdir('log')
    
    now = datetime.now().strftime('%Y%m%d%H%M%S')
    with open('log/airport_{}_{}.log'.format(year, now), 'a') as f:
        f.write(log)
