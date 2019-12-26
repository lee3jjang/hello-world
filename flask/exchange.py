import os
import pandas as pd
from datetime import datetime

# result 폴더 생성
if not any(['result' == s for s in os.listdir('.')]):
    os.mkdir('./result')

# 데이터 수집
result = list()
print('----- 수집 시작 -----')
index_cd = 'USD'
page = 1
while(True):
    url = 'https://finance.naver.com/marketindex/exchangeDailyQuote.nhn?marketindexCd=FX_{}KRW&page={}'.format(index_cd, page)
    data = pd.read_html(url)[0]
    result.append(data)
    if (data.shape[0] != 10):
        break
    page += 1
print('환율정보 수집 완료(페이지 수: {})'.format(page))
df = pd.concat(result)
df.columns = ['날짜', '매매기준율', '전일대비', '현찰_사실때', '현찰_파실때', '송금_보내실때', '송금_받으실때', 'T/C_사실때', '외화수표_파실때']
df = df.drop('전일대비', axis=1)
df['날짜'] = pd.to_datetime(df['날짜'], format='%Y.%m.%d')
df = df.reset_index(drop=True)
print('----- 수집 완료 -----')

# 데이터 저장
basetime = datetime.now().strftime('%Y%m%d%H%M%S')
writer = pd.ExcelWriter('./result/exchange_{}.xlsx'.format(basetime), 'xlsxwriter')
df.to_excel(writer, index=False)
writer.save()
writer.close()