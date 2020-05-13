import sys
import pandas as pd
import time
from datetime import datetime

def get_stock_price(code, t, header_flag):
    delay = 0.1
    page = 1
    result = []
    while(True):
        if code == "KOSPI":
            url = 'https://finance.naver.com/sise/sise_index_time.nhn?code={code}&thistime={now}&page={page}'.format(code=code, now=t, page=page)
        else:    
            url = 'https://finance.naver.com/item/sise_time.nhn?code={code}&thistime={now}&page={page}'.format(code=code, now=t, page=page)
        data = pd.read_html(url)[0].dropna()
        if page != 1:
            try:
                if data.iloc[-1, 0] == result[-1].iloc[-1, 0]:
                    break
            except:
                break
        result.append(data)
        break # 1번만
        page += 1
        time.sleep(delay)
    stock_price = pd.concat(result).sort_values(by='체결시각').reset_index(drop=True)
    print(stock_price.tail(1).to_string(index=False, header=header_flag))

# 코드 리스트
code_list = {
    '코스피': 'KOSPI',
    'DB': '005830',
    '현대': '001450',
    '메리츠': '000060',
    '한화': '000370',
    '삼성': '000810',
}

if __name__ == '__main__':
    name = sys.argv[1]
    code = code_list.get(name)
    now = datetime.now().strftime('%Y%m%d%H%M%S')
    flag = True
    while(True):
        get_stock_price(code, now, flag)
        time.sleep(60)
        if flag:
            flag = False
    