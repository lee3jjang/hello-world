from selenium import webdriver
import pandas as pd
import numpy as np

driver = webdriver.Chrome(r'C:\Users\sangjin\chromedriver.exe')
url = r'http://www.academyinfo.go.kr/UIPISA/uipnh/unt/ipsrch/UntUnvAcdtSrchPupInr.do?'
query = {
    'schlId':'0000203',
    'svyYr':'2018',
    'schlDivCd':'02'
}

for key in query.keys():
    url += key+"="+query[key]+"&"
url = url[:-1]

driver.get(url)
driver.execute_script("fn_submit('2','01')")

path = ['table#tb','tbody']
path = ' > '.join(path)
result = driver.find_element_by_css_selector(path)
