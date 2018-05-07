
# coding: utf-8

import requests
from bs4 import BeautifulSoup
import pandas as pd
import numpy as np
import sqlite3
pd.options.display.float_format = '{:,.1f}'.format

# DB 생성
conn = sqlite3.connect('mobile.db')
cur = conn.cursor()

# 테이블 생성
try:
    cur.execute('create table 스마트폰(모델명, 제목, 가격, 배송비, 판매자, 날짜)')
except sqlite3.OperationalError as e:
    pass

# 데이터 수집
for j in range(1,421):
    # 접속
    query = {'p':j,'auc_sale':1,'escrow_motion':3,
             'w%5B1%5D':1,'w%5B2%5D':2,'w%5B3%5D':3,'w%5B4%5D':4,'sc':1,'q':'market','cat%5B0%5D':3}
    req = requests.get('http://market.cetizen.co.kr/market.php',params=query)
    html = req.text
    soup = BeautifulSoup(html, 'html.parser')
    
    # 페이지 수집
    selector = ['form[name=AucListForm]','div','ul:nth-of-type(2)']
    selector = ' > '.join(selector)
    pos = soup.select(selector)[0]

    data = []
    for _ in range(20):
        row = []
        pos = pos.select('li')[0]
        row.append(pos.text.replace('\n',''))
        for _ in range(5):
            pos = pos.find_next_sibling()
            row.append(pos.text.replace('\n',''))
        pos = pos.find_next_sibling()
        data.append(row)
        pos = pos.findParent().find_next_sibling()

    cur.executemany("insert into 스마트폰 values (?,?,?,?,?,?)", data)
    if j % 40 == 0:
        conn.commit()
        print(data[-1])    
        
conn.commit()
conn.close()