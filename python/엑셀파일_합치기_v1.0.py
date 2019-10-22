#!/usr/bin/env python
# coding: utf-8

# In[17]:


import os
import pandas as pd
from datetime import datetime


'''
    용도
    각 엑셀파일을 모아 하나의 엑셀파일로 만드는 프로그램
    사용법
    1. .xlsx 파일들이 모여 있는 곳에 프로그램을 놓는다
    2. 실행시킨다
'''

if __name__ == '__main__':
    if not any([f == 'result' for f in os.listdir()]):
        os.mkdir('result')

    files = list(filter(lambda x: x[-5:] == '.xlsx', os.listdir()))
    now = datetime.now().strftime('%Y%m%d%H%M%S')
    with pd.ExcelWriter('./result/result_{}.xlsx'.format(now), 'xlsxwriter') as writer:
        for file in files:
            filename = file[:-5]
            pd.read_excel(file).to_excel(writer, filename, index=False)

