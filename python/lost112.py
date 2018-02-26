from selenium import webdriver
import pandas as pd
import numpy as np
driver = webdriver.Chrome(r'C:\Users\sangjin\chromedriver.exe')
url = r'https://www.lost112.go.kr/lost/lostList.do'
driver.get(url)

def lostData(i):
    path = ['div#contents','div.find_listBox','table.type01']
    path = ' > '.join(path)
    driver.execute_script('fn_find_link_page(' + str(i) + ')')
    result = driver.find_element_by_css_selector(path)
    raw_text = result.text.split('\n')
    table = np.array(raw_text[2:])
    table.shape = (10,4)
    return table


for i in range(1,10):
    if i == 1:
        data = lostData(i)
    temp = lostData(i)
    data = np.append(data,temp,axis=0)
    
df = pd.DataFrame(data)
df.columns = ['관리번호','분실물명','분실장소','분실일자']
writer = pd.ExcelWriter('lost.xlsx')
df.to_excel(writer,'sheet1')
