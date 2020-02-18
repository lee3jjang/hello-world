#!/usr/bin/env python
# coding: utf-8

# In[1]:


import os
import sys
import time
import pandas as pd
from datetime import datetime
from dateutil.relativedelta import relativedelta
from selenium import webdriver
from selenium.webdriver.support.ui import Select


# In[2]:

# year
year = int(sys.argv[1])

# column name
column_name = ['기준일자', '공항', '항공사', '편명', '목적지', '계획', '예상', '출발', '구분', '현황', '비정상원인', '비고']

# ports
# '인천' 제외
ports = ['김포', '청주', '양양', '군산', '원주', '김해', '제주', '대구', '광주', '여수', '울산', '포항', '사천', '무안']


# dates
dates = []
start_date = datetime(year,1,1)
end_date = datetime(year,12,31)
date = start_date
while(date <= end_date):
    dates.append(date.strftime('%Y%m%d'))
    date = date + relativedelta(days=1)


# In[3]:

options = webdriver.ChromeOptions()
options.add_argument('headless')
options.add_argument("disable-gpu")
driver = webdriver.Chrome('chromedriver', options=options)


# In[4]:


data = []
log = ''
for date in dates:
    for port in ports:
        driver.get('http://www.airportal.go.kr/life/airinfo/RbBejFrm.jsp')
        driver.find_elements_by_css_selector('input[name="depArr"]')[1].click()
        driver.execute_script('''
            x = $("#current_date").focus();
            x[0]['value'] = {};
        '''.format(date))
        Select(driver.find_element_by_css_selector('select[name="airport"]')).select_by_visible_text(port)
        driver.execute_script('go_search()')
        time.sleep(2.5)
        driver.switch_to.frame(driver.find_element_by_tag_name('iframe'))

        rows = driver.find_elements_by_tag_name('table')[1].find_elements_by_tag_name('tr')
        bulk = []
        if len(rows) == 4:
            text = '[{}, {}] 검색된 결과가 없습니다.'.format(date, port)
            print(text)
            log += text + '\n'
        else:
            for row in rows:
                if len(row.find_elements_by_tag_name('td')) == 1: continue
                cols = row.find_elements_by_css_selector('td[width$="0"]')
                bulk.append([date, port]+[x.text for x in cols])
            text = '[{}, {}] {:3}건의 결과가 검색되었습니다.'.format(date, port, len(bulk))
            print(text)
            log += text + '\n'
            data += bulk


# In[5]:


df = pd.DataFrame(data, columns=column_name)
if not any([s=='result' for s in os.listdir('.')]): os.mkdir('result')
now = datetime.now().strftime('%Y%m%d%H%M%S')
file_name = 'airportal_{}'.format(year)
with pd.ExcelWriter('result/{}_{}.xlsx'.format(file_name, now), 'xlsxwriter') as writer:
    df.to_excel(writer, index=False)
with open('result/{}_{}.log'.format(file_name, now), 'w') as f:
    f.write(log)

