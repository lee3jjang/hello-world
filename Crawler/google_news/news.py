#!/usr/bin/env python
# coding: utf-8

# In[1]:


from PyQt5.QtWidgets import QApplication, QDialog
from PyQt5.uic import loadUi
from PyQt5.QtCore import pyqtSlot
from datetime import datetime
import random
import time
import urllib.request
import bs4
import pandas as pd
import os
import sys

class GoogleNews(QDialog):
    
    def __init__(self, parent=None):
        super().__init__(parent)
        self.ui = loadUi('./news.ui', self)
        self.setWindowTitle('구글 뉴스 수집기')
        self.ui.show()
        
    @pyqtSlot()
    def accept(self):
        keyword = self.ui.keyword.text()
        pages = int(self.ui.pages.text())
        print('----- 수집 시작 -----')
        print('  키워드: {}\n  페이지 수: {}'.format(keyword, pages))
        self._crawling(keyword, pages)
        print('----- 수집 완료 -----')
        
    def _crawling(self, keyword, pages):
        
        
        # result 폴더 생성
        if not any(['result' == s for s in os.listdir('.')]):
            os.mkdir('./result')

        # 페이지별 루프
        result = list()
        basedate = datetime.now().strftime('%Y%m%d') # 수집일자
        for page in range(1, pages+1):

            # 페이지 얻기
            time.sleep(random.random()*3) # 딜레이
            url = 'https://www.google.co.kr/search?q={}&tbm=nws&start={}'.format(urllib.parse.quote(keyword), 10*(page-1))
            opener = urllib.request.build_opener()
            opener.addheaders=[('User-Agent', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.47 Safari/537.36')]
            res = opener.open(url)
            html = res.read()

            # 파일 저장
            with open('./result/google_search_{}_{}_{}.html'.format(basedate, keyword, page), 'wb') as f:
                f.write(html)
                f.close()

            # 데이터 가져오기
            soup = bs4.BeautifulSoup(html, 'lxml')
            articles = soup.select('div#rso')[0].select('div.g')
            data = list()
            for article in articles:
                article2 = article.select('div.gG0TJc')[0] # ISSUE : gGoTJC 말고 gZQPfd에도 기사 있음
                link = article2.select('h3 > a')[0]['href']
                title = article2.select('h3 > a')[0].text
                press = article2.select('span.xQ82C')[0].text
                date =  article2.select('span.f')[0].text
                contents = article2.select('div.st')[0].text.replace('\xa0', '')
                data.append([datetime.now(), keyword, page, link, title, press, date, contents])
            result.append(pd.DataFrame(data))

        # 수집 결과 집계
        df = pd.concat(result)
        df.columns = ['BASEDATE', 'KEYWORD', 'PAGE', 'LINK', 'TITLE', 'PRESS', 'DATE', 'CONTENTS']

        # 데이터 저장
        writer = pd.ExcelWriter('./result/google_search_{}_{}.xlsx'.format(basedate, keyword))
        df.to_excel(writer, index=False)
        writer.save()
        writer.close()
        


# In[ ]:


if __name__ == '__main__':
    app = QApplication(sys.argv)
    crawler = GoogleNews()
    sys.exit(app.exec_())

