from selenium import webdriver
import time
driver = webdriver.Chrome(r'C:\Users\Administrator\Desktop\dev\chromedriver.exe')

url = r'http://www.encar.com/fc/fc_carsearchlist.do?carType=for#!%7B%22action%22%3A%22(And.Hidden.N._.(C.CarType.N._.(C.Manufacturer.%EB%B2%A4%EC%B8%A0._.(C.ModelGroup.E-%ED%81%B4%EB%9E%98%EC%8A%A4._.Model.E-%ED%81%B4%EB%9E%98%EC%8A%A4%20W213.))))%22%2C%22toggle%22%3A%7B%7D%2C%22layer%22%3A%22%22%2C%22sort%22%3A%22ModifiedDate%22%2C%22page%22%3A1%2C%22limit%22%3A20%7D'
driver.get(url)

data = []
for i in range(1,21):
    path = [
        'body',
        'div.container',
        'div.body',
        'div.contents',
        'div#rySearch2015_wrap',
        'div#rySch_result',
        'div.rySch_con',
        'div.section.list',
        'div.part.list',
        'table',
        'tbody',
    ]    
    path.append('tr:nth-of-type('+str(i)+')')
    path = ' > '.join(path)
    
    result = driver.find_element_by_css_selector(path)
    row = '\t'.join(result.text.split('\n'))
    data.append(row)
    

with open('data.txt','w') as f:
    for line in data:
        f.write(line)
        f.write('\n')
        
