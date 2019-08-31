#!/usr/bin/env python
# coding: utf-8

# In[1]:


import os
from selenium import webdriver
import time
from datetime import datetime
from dateutil.relativedelta import relativedelta
import shutil


# In[2]:


driver = webdriver.Chrome('chromedriver')


# In[3]:


# 남아있는 파일 제거
download_path = r'C:\Users\{}\Downloads'.format(os.environ['USERNAME'])
if any(['최종호가 수익률.xls' == s for s in os.listdir(download_path)]):
    os.remove(download_path + '/최종호가 수익률.xls')

# result 파일 생성
if not any(['result' == s for s in os.listdir('.')]):
    os.mkdir('./result')

url = 'http://www.kofiabond.or.kr/websquare/websquare.html?w2xPath=/xml/bondint/lastrop/BISLastAskPrc.xml&divisionId=MBIS01010010000000&serviceId=&topMenuIndex=0&w2xHome=/xml/'
driver.get(url)

# 기간별 이동
driver.find_element_by_css_selector('li#tabContents1_tab_tabs2').click()

time.sleep(5)

# 프레임 선택
driver.switch_to.frame(driver.find_element_by_css_selector('iframe#tabContents1_contents_tabs2_body'))

# 채권 종류 선택
for i in [9, 10, 13, 15, 0, 3, 4, 5]:
    driver.find_element_by_css_selector('input#chkAnnItm_input_{}'.format(i)).click()    
    
# 조회일자 선택
driver.find_element_by_css_selector('input#endDtDD_input').text

# 조회
driver.find_element_by_css_selector('a#group154').click()
time.sleep(1)

# 다운로드
driver.find_element_by_css_selector('a#grpExcel').click()


# result 폴더로 이동
while(not any(['최종호가 수익률.xls' == s for s in os.listdir(download_path)])):
    time.sleep(1)
now = datetime.now().strftime('%Y%m%d%H%M%S')
shutil.move(download_path + '/최종호가 수익률.xls', './result/risk_free_interest_rate_{}.xls'.format(now))

