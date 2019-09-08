from selenium import webdriver
from selenium.webdriver.support.ui import Select
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from urllib.parse import urlparse, parse_qs
import pandas as pd
import random
import time
import glob
import os
from bs4 import BeautifulSoup
from datetime import datetime
import numpy as np

def get_url(url):
    # result 폴더 생성
    if not any([s == 'result' for s in os.listdir('.')]):
        os.mkdir('result')
        
    delay = 2   # 딜레이 시간
    driver = webdriver.Chrome('chromedriver')
    
    # url 접속하기
    driver.get(url)
    time.sleep(delay)

    # [20개씩 보기] → [50개씩 보기]로 변환
    viewer = Select(driver.find_element_by_css_selector('select#pagerow'))
    viewer.select_by_value('50')
    time.sleep(delay)
    # 데이터 url 수집
    batch = []
    page = 1
    while(True):
        try:
            driver2 = WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.CSS_SELECTOR, 'div#pagination')))
            driver2.find_element_by_xpath('//a[@data-page="{}"]'.format(page)).click()
        except:
            break
        time.sleep(delay)
        rows = driver.find_element_by_css_selector('tbody#sr_normal').find_elements_by_tag_name('tr')
        for row in rows:
            car_url = row.find_element_by_css_selector('td.inf > a').get_attribute('href')
            batch.append(car_url)
        print('현재페이지: p.{}'.format(page))
        page += 1

    # url 저장
    now = datetime.now().strftime('%Y%m%d%H%M%S')

    # 결과 저장하기
    df = pd.DataFrame(batch)
    df = df.reset_index(drop=True)
    df[1] = df[0].apply(lambda x: parse_qs(urlparse(x).query)['carid'][0])
    df = df.loc[df[1].drop_duplicates().index, :]
    writer = pd.ExcelWriter('./result/car_url_{}.xlsx'.format(now), 'xlsxwriter')
    df.to_excel(writer, index=False)
    writer.save()
    writer.close()
    print('작업완료(파일명: car_url_{}.xlsx)'.format(now))

def get_page(params):
    # source 폴더 생성
    if not any([s == 'source' for s in os.listdir('.')]):
        os.mkdir('source')
        
    file_name, index = params
    x = np.array(list(map(lambda x: int(x[13:-5]), glob.glob('./source/*.html'))))
    index_reduced = np.setdiff1d(index, x)
    
    driver = webdriver.Chrome('chromedriver')
    df = pd.read_excel('./result/{}.xlsx'.format(file_name))
    df = df.reset_index(drop=True)
    df2 = df.loc[index_reduced, :]
    for i, url in df2.iterrows():
#         print('p.{} 수집완료...'.format(i))
        driver.get(url[0])
        time.sleep(1 + random.random())
        with open('./source/car_{}.html'.format(i+1), 'w', -1, encoding='utf-8') as f:
            f.write(driver.page_source)
    driver.close()
    
    
def extract_data():
    batch = []
    files = glob.glob('./source/*.html')
    n = len(files)
    print('총 파일 수: {}개'.format(n))
    i = 0
    for file in files:
        with open(file, 'r', encoding='utf-8') as f:
            html = f.read()
        soup = BeautifulSoup(html[html.find('<div id="areaBase"'):html.find('<div class="product_detail"')], features='lxml')
        result = {}
        try:
            # 일반
            result['브랜드'] = [soup.select('div.product_left > div.area_info > h1.prod_name > span.brand')[0].text]
            result['디테일'] = [soup.select('div.product_left > div.area_info > h1.prod_name > span.detail')[0].text]
            prod_infomain_list = soup.select('div.product_left > div.area_info > div.prod_infomain > ul > li')
            prod_infomain = {}
            for p in prod_infomain_list:
                key = p.select('span.blind')[0].text.replace(':', '')
                value = p.text.replace(' ', '').replace('\n', '').replace('자세히보기', '').replace(p.select('span.blind')[0].text, '')
                prod_infomain[key] = [value]
            result.update(prod_infomain)
            if not '수입형태' in result.keys():
                result['수입형태'] = ['']
            if len(soup.select('div.prod_price_lease')) != 0:
                continue
            result['가격'] = [soup.select('div.product_left > div.area_info > div.prod_price > span.num')[0].text]
            result['등록번호'] = [soup.select('div.product_left > div.area_info2nd > div.prod_infoetc > ul.reg > li')[0].text.replace('등록번호', '').replace('\n', '').replace('자세히보기', '').replace(' ', '')]
            result['조회수'] = [soup.select('div.product_left > div.area_info2nd > div.prod_infoetc > ul.reg > li')[1].text.replace('조회수', '').replace('\n', '').replace('자세히보기', '').replace(' ', '')]
            result['찜'] = [soup.select('div.product_left > div.area_info2nd > div.prod_infoetc > ul.reg > li')[2].text.replace('찜', '').replace('\n', '').replace('자세히보기', '').replace(' ', '')]
            #result['핫마크'] = [', '.join(list(map(lambda x: x.text.replace('\n', '').replace(' ', ''), soup.select('div.product_left > div.area_info2nd > div.prod_addon > span'))))]

            soup = BeautifulSoup(html[html.find('<div class="product_detail"'):html.find('<div><div id="ad_btm">')], features='lxml')
            option_classes = soup.select('div#areaOption > div.box_opt > div.con.option_hover')
            option_effective = []
            for option_class in option_classes:
                options = option_class.select('dl > dd.on')
                for option in options:
                    try:
                        option_effective.append(option.select('a')[0].text.replace('\n', '').replace(' ', ''))
                    except:
                        option_effective.append(option.select('p')[0].text.replace('\n', '').replace(' ', ''))
            df = pd.DataFrame.from_dict(result)
            df.insert(0, '진단구분', '일반')
            batch.append(df)
        except:
            # 진단
            result['브랜드'] = [soup.select('div#carPic > div.info_product > div.wrap_tit > strong.prod_name > span.brand')[0].text]
            result['디테일'] = [soup.select('div#carPic > div.info_product > div.wrap_tit > strong.prod_name > span.detail')[0].text]
            prod_infomain_list = soup.select('div#carPic > div.info_product > ul.list_carinfo')[0].select('li')
            prod_infomain = {}
            for p in prod_infomain_list:
                key = p.select('span.blind')[0].text.replace(':', '')
                value = p.text.replace(' ', '').replace('\n', '').replace('자세히보기', '').replace(p.select('span.blind')[0].text, '')
                prod_infomain[key] = [value]
            result.update(prod_infomain)
            if not '수입형태' in result.keys():
                result['수입형태'] = ['']
            if len(soup.select('ul.list_leaseinfo')) != 0:
                continue
            result['가격'] = [soup.select('div#scrFix > div.wrap_keyinfo > div.info_purchase > em.emph_price')[0].text]
            result['등록번호'] = [soup.select('div#carPic > div.info_product > ul.list_carinfo')[1].select('li')[0].text.replace('등록번호', '').replace('\n', '').replace('자세히보기', '').replace(' ', '')]
            result['조회수'] = [soup.select('div#carPic > div.info_product > ul.list_carinfo')[1].select('li')[1].text.replace('조회수', '').replace('\n', '').replace('자세히보기', '').replace(' ', '')]
            result['찜'] = [soup.select('div#carPic > div.info_product > ul.list_carinfo')[1].select('li')[2].text.replace('찜', '').replace('\n', '').replace('자세히보기', '').replace(' ', '')]
            # result['핫마크'] = [', '.join(list(map(lambda x: x.text.replace('\n', '').replace(' ', ''), soup.select('div.product_left > div.area_info2nd > div.prod_addon > span'))))]

            soup = BeautifulSoup(html[html.find('<div class="product_detail"'):html.find('<div><div id="ad_btm">')], features='lxml')
            option_classes = soup.select('div#areaOption > div.area_options > dl.option_all')
            option_effective = []
            for option_class in option_classes:
                options = option_class.select('dd.on')
                for option in options:
                    try:
                        option_effective.append(option.select('a')[0].text.replace('\n', '').replace(' ', ''))
                    except:
                        option_effective.append(option.select('p')[0].text.replace('\n', '').replace(' ', ''))
            df = pd.DataFrame.from_dict(result)
            df.insert(0, '진단구분', '진단')
            batch.append(df)
        print('{}개 작업 완료'.format(i+1))
        i += 1
        
    now = datetime.now().strftime('%Y%m%d%H%M%S')
    writer = pd.ExcelWriter('./result/result_{}.xlsx'.format(now), 'xlsxwriter')
    pd.concat(batch).to_excel(writer, index=False)
    writer.save()
    writer.close()