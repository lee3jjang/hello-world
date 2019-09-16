import os
import time
from glob import glob
import pandas as pd
from random import random
from bs4 import BeautifulSoup
from selenium import webdriver
from selenium.webdriver.support.ui import Select
from selenium.common.exceptions import NoSuchElementException

__version__ = '0.1.0'
__author__ = '11700205'

def get_source(url, name, page_max=200):
    '''
        Description:
            주어진 url로 접근 → 일반등록 차량에 있는 데이터를 html 형태로 수집
            
        Input:
            url - 접근할 url 주소
            name - 저장할 폴더명
            
        Output:
            result 폴더에 결과 저장
        
        Example:
            url = 'http://www.encar.com/fc/fc_carsearchlist.do?carType=for&searchType=model&TG.R=B#!%7B%22action%22%3A%22(And.Hidden.N._.(C.CarType.N._.Manufacturer.%EB%B2%A4%EC%B8%A0.))%22%2C%22toggle%22%3A%7B%7D%2C%22layer%22%3A%22%22%2C%22sort%22%3A%22ModifiedDate%22%2C%22page%22%3A1%2C%22limit%22%3A20%7D'
            name = 'benz'
            get_source(url, name)
    '''
    
    driver = webdriver.Chrome('chromedriver')
    # 폴더 생성
    if not any([s == name for s in os.listdir('./result/')]):
        os.mkdir('./result/{}'.format(name))
    
    # 접속하기
    print('({}) 첫 페이지에 접근합니다.'.format(name))
    driver.get(url)

    # [20개씩 보기] → [50개씩 보기]로 변환
    viewer = Select(driver.find_element_by_css_selector('select#pagerow'))
    viewer.select_by_value('50')

    time.sleep(10)

    # 수집하기
    page = 1
    print('수집을 시작합니다.')
    while(True):
        if(page > page_max):
            print('수집을 종료합니다. (페이지 지정치 도달)')
            break
        with open('./result/{}/carlist_{}_{:04d}.html'.format(name, name, page), 'w', -1, encoding='utf-8') as f:
            soup = BeautifulSoup(driver.find_element_by_xpath('//tbody[@id="sr_normal"]/ancestor::table').get_attribute('outerHTML'), 'lxml')
            html = str(soup)
            f.write(html)
        try:
            driver.find_element_by_css_selector('div#pagination').find_element_by_xpath('//a[@data-page="{}"]'.format(page+1)).click()
        except NoSuchElementException:
            print('수집을 종료합니다. (NoSuchElementException)')
            break
        page += 1
        time.sleep(1+2*random())
    driver.close()
    
def parse(name):
    '''
        Description:
            get_source를 통해 수집된 html파일에서 데이터를 추출하여 xlsx 형태로 저장
            
        Input:
            name - get_source를 통해 저장한 폴더명
            
        Output:
            result 폴더에 결과 저장
        
        Example:
            name = 'benz'
            parse(url, name)
    '''
    
    print('({}) 파싱을 시작합니다.'.format(name))
    result = []
    files = glob('./result/{}/*.html'.format(name))
    for file in files:
        with open(file, 'r', -1, encoding='utf-8') as f:
            html = f.read()
        soup = BeautifulSoup(html, 'lxml')
        carlist_batch = soup.select('tr')[1:]
        result_batch = []
        for car in carlist_batch:
            name1 = car.select_one('span.cls > strong').text
            name2 = car.select_one('span.cls > em').text
            name3 = car.select_one('span.dtl > strong').text
            name4 = car.select_one('span.dtl > em').text
            yer = car.select_one('span.yer').text
            km = car.select_one('span.km').text
            fue = car.select_one('span.fue').text
            loc = car.select_one('span.loc').text
            ins = '' if car.select_one('span.ins') == None else car.select_one('span.ins').text
            ass = '' if car.select_one('span.ass') == None else car.select_one('span.ass').text
            prc = car.select_one('td.prc_hs').text
            link = car.select_one('a').attrs['href']
            result_batch.append((name1, name2, name3, name4, yer, km, fue, loc, ins, ass, prc, link))
        column_name = ['name1', 'name2', 'name3', 'name4', 'yer', 'km', 'fue', 'loc', 'ins', 'ass', 'prc', 'link']
        df = pd.DataFrame(result_batch, columns=column_name)
        result.append(df)
    df = pd.concat(result).reset_index(drop=True)
    df['link'] = 'http://www.encar.com' + df['link']
    with pd.ExcelWriter('./result/{}/carlist_{}.xlsx'.format(name, name), 'xlsxwriter') as writer:
        df.to_excel(writer, index=False)
    print('파싱이 종료되었습니다.')