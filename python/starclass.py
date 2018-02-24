import requests as req
from bs4 import BeautifulSoup as bs
import numpy as np

######################################################################
def getRow(tds):
    row = []
    # 모델명
    td = tds[2]
    x = td.select('a')[0].getText()
    row.append(x)
    # 기타정보
    if td.select('img') != []:
        x = td.select('img')[0].get('title')
    else:
        x = '일반'
    row.append(x)
    # 가격
    td = tds[3]
    x = td.getText()
    row.append(x)
    # 연식
    td = tds[4]
    x = td.getText()
    row.append(x)
    # 주행거리
    td = tds[5]
    x = td.getText()
    row.append(x)
    # 연료타입
    td = tds[6]
    x = td.getText()
    row.append(x)
    # 외부색상
    td = tds[7]
    x = td.getText()
    row.append(x)
    # 사고유무
    td = tds[8]
    x = td.getText()
    row.append(x)
    
    return(row)
######################################################################
def starData(page):
    url = r'http://www.mbstarclass.co.kr/index.php?page='
    url = url+str(page)
    res = req.get(url)
    res.encoding = 'utf-8'
    soup = bs(res.text,'lxml')

    path = [
        'body',
        'div',
        'div#main',
        'div.search.pt13',
        'div.compare',
        'div.car_list',
        'table',
        'tr'
    ]
    path = ' > '.join(path)
    tag = soup.select(path)[1]

    path = [
        'td',
        'form',
        'table'
    ]
    path = ' > '.join(path)
    table = tag.select(path)[0]
    trs = table.select('tr')
    
    rows = []
    for tr in trs:
        tds = tr.select('td')
        row = getRow(tds)
        rows.append(row)
        
    return rows
######################################################################
for i in range(1,30):
    if i == 1:
        data = np.array(starData(i))
    temp = np.array(starData(i))
    if len(temp) == 0:
        break
    data = np.append(data,temp,axis=0)
    
print(data)
