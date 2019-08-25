#!/usr/bin/env python
# coding: utf-8

# In[1]:


import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import os
from selenium import webdriver
from selenium.webdriver.support.ui import Select
import time
import random
from datetime import datetime
import shutil


# In[2]:


options = webdriver.ChromeOptions()
os.environ['webdriver.chrome.driver'] = os.path.abspath('./chromedriver')
prefs = {'profile.default_content_setting_values.automatic_downloads': 1}
options.add_experimental_option("prefs", prefs)
driver = webdriver.Chrome('chromedriver', options=options)


# In[3]:


driver.get('http://tearstop.seoul.go.kr/mulga/info/price01.jsp')


# In[4]:


# 시장 목록 구하기
area_list = driver.find_element_by_css_selector('ul#areaList').find_elements_by_css_selector('li')
result = list()
for area in area_list:
    function = area.find_element_by_css_selector('a').get_attribute('onclick')
    code, name = function.replace('goSearchNew(this,', '').replace(');', '').replace('\'', '').split(',')
    result.append([code, name, function])
area_traditional_market = pd.DataFrame(result)
area_traditional_market.columns = ['CODE', 'NAME', 'FUNCTION']


# In[ ]:


# 파일 수집 시작
today = datetime.now().strftime('%Y%m%d')
download_path = r'C:\Users\{}\Downloads'.format(os.environ['USERNAME'])
# 다운로드 경로에 파일 존재 시 삭제
if any(['{}.xls'.format(today) == s for s in os.listdir(download_path)]):
    os.remove(download_path + '/' + '{}.xls'.format(today))

if not any(['result' == s for s in os.listdir('.')]):
    os.mkdir('./result')
for i in range(len(area_traditional_market)):
    name = area_traditional_market.iloc[i]['NAME']
    func = area_traditional_market.iloc[i]['FUNCTION']
    driver.execute_script(func)
    for year in range(2009, int(today[:4])+1):
        select_year = Select(driver.find_element_by_css_selector('select#m_year'))
        select_year.select_by_value(str(year))
        time.sleep(2)
        driver.execute_script('go_YearExcel()')
        while(not any(['{}.xls'.format(today) == s for s in os.listdir(download_path)])):
            time.sleep(2)
        shutil.move(download_path + '/' + '{}.xls'.format(today), './result/{}_{}.xls'.format(name, year))
        print('----- 다운로드 완료: (시장: {}, 기준년도: {}년) -----'.format(name, year))

