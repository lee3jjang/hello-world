#!/usr/bin/env python
# coding: utf-8

# In[1]:


import pandas as pd
import numpy as np
import json
import matplotlib.pyplot as plt
import urllib
import matplotlib
from datetime import datetime
import seaborn as sns
import time
import sys
import os
from PyQt5.QtWidgets import QApplication, QDialog
from PyQt5.uic import loadUi
from PyQt5.QtCore import pyqtSlot
matplotlib.rc('font', family='Malgun Gothic', size='10')
matplotlib.rcParams['axes.unicode_minus'] = False


# In[2]:


def get_data(service, params):
    params['lang'] = 'kr'
    params['auth'] = '7774090942ede970746a7cd9b2e10577'
    tmp = []
    for k, v in params.items():
        tmp.append(k + "=" + v)
    query = '&'.join(tmp)
    url = 'http://fisis.fss.or.kr/openapi/{}.json?'.format(service) + query
    res = urllib.request.urlopen(url)
    data = json.loads(res.read())
    return data


# In[7]:


# # 금융권역별 금융회사 현황
# pnc_company_list_all = pd.DataFrame(get_data('companySearch', {'partDiv': 'I'})['result']['list'])
# # 금융권역별 통계자료 현황
# code_list_all = pd.DataFrame(get_data('statisticsListSearch', {'lrgDiv': 'I'})['result']['list'])
# # 통계자료별 계정 현황
# code = 'SI136'   # SI020: 사업실적표, SI123: 책임준비금 현황, SI139: RBC 비율, SI003: 요약재무상태표(자산-전체), SI004: 요약재무상태표(부채 및 자본-전체), SI007: 요약손익계산서(전체), SI136: 보험종류별 경과손해율	
# account_list_by_code = pd.DataFrame(get_data('accountListSearch', {'listNo': code})['result']['list'])


# In[5]:


class Fss(QDialog):
    def __init__(self, parent=None):
        super().__init__(parent)
        self.ui = loadUi('./fss.ui', self)
        self.setWindowTitle('금융감독원 동업사 통계자료 수집기')
        self.basedate = datetime.today().strftime('%Y%m%d')
        self.pnc_company_list_selected = [
            '0010636', '0010634', '0010633', '0010626', '0010630', '0010628',
            '0010635', '0011354', '0010627', '0011354', '0013174'
        ]
        self.ui.show()
 
    @pyqtSlot()
    def accept(self):
        code_list_selected = []
        if self.ui.si001.isChecked(): code_list_selected.append('SI001')
        if self.ui.si002.isChecked(): code_list_selected.append('SI002')
        if self.ui.si003.isChecked(): code_list_selected.append('SI003')
        if self.ui.si004.isChecked(): code_list_selected.append('SI004')
        if self.ui.si007.isChecked(): code_list_selected.append('SI007')
        if self.ui.si020.isChecked(): code_list_selected.append('SI020')
        if self.ui.si021.isChecked(): code_list_selected.append('SI021')
        if self.ui.si114.isChecked(): code_list_selected.append('SI114')
        if self.ui.si115.isChecked(): code_list_selected.append('SI115')
        if self.ui.si123.isChecked(): code_list_selected.append('SI123')
        if self.ui.si136.isChecked(): code_list_selected.append('SI136')
        if self.ui.si137.isChecked(): code_list_selected.append('SI137')
        if self.ui.si138.isChecked(): code_list_selected.append('SI138')
        if self.ui.si139.isChecked(): code_list_selected.append('SI139')
        print('선택된 통계자료: {}'.format(code_list_selected))
        self._crawling(self.pnc_company_list_selected, code_list_selected)
        
    def _crawling(self, pnc_company_list_selected, code_list_selected):
        # result 폴더 생성
        if not any(['result' == s for s in os.listdir('.')]):
            os.mkdir('./result')
            
        # 데이터 수집
        params = {'term': 'Q', 'startBaseMm': '200901', 'endBaseMm': self.basedate[:-2]}
        result = []
        print('----- 수집 시작 -----')
        for finance_cd in pnc_company_list_selected:
            for code in code_list_selected:
                params['financeCd'] = finance_cd
                params['listNo'] = code
                df = pd.DataFrame(get_data('statisticsInfoSearch', params)['result']['list'])
                df['a'] = pd.to_numeric(df['a'].str.strip()).fillna(0)
                df = df.loc[:, ['finance_nm', 'account_nm', 'base_month', 'a']]
                result.append(df)
        print('----- 수집 완료 -----')
        stats = pd.concat(result)
        stats.columns = ['회사명', '계정명', '기준년월', '값']
        stats = stats.sort_values(by=['회사명', '계정명', '기준년월']).reset_index(drop=True)
        
        # 데이터 저장
        writer = pd.ExcelWriter('./result/fss_{}.xlsx'.format(self.basedate))
        stats.to_excel(writer, index=False)
        writer.save()
        writer.close()
        print('----- 엑셀 저장 완료 -----')

        # 데이터 시각화(샘플)
        account_list = ['[ 자 산 총 계 ]', '[ 부 채 총 계 ]', '[ 자 본 총 계 ]', 'Ⅰ. 책임준비금' , 'RBC 비율',
                        '경과손해율_자동차', '경과손해율_일반', '경과손해율_장기', '경과손해율_합계',
                        '순사업비율_자동차', '순사업비율_일반', '순사업비율_장기', '순사업비율_합계',
                        '합산비율_자동차', '합산비율_일반', '합산비율_장기', '합산비율_합계']
        plt.figure(figsize=(10, 6))
        for account in account_list:
            if stats.query('계정명 == @account').shape[0] == 0:
                break
            for _, grp in stats.query('계정명 == @account').groupby('회사명'):
                plt.plot(grp['기준년월'], grp['값'], '-o', linewidth=2, label=grp['회사명'].iloc[0])
            plt.xticks(rotation=60)
            plt.legend()
            plt.grid()
            plt.title('손해보험사 {} 현황'.format(account))
            sns.despine(top=True, right=True, left=True)
            plt.tight_layout()
            plt.savefig('./result/동업사비교_{}_{}.png'.format(account, self.basedate))
            plt.clf()
        print('----- 그림 저장 완료 -----')


# In[6]:


if __name__ == '__main__':
    app = QApplication(sys.argv)
    crawler = Fss()
    sys.exit(app.exec_())

