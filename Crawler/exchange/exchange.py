#!/usr/bin/env python
# coding: utf-8

# In[2]:


from PyQt5.QtWidgets import QApplication, QDialog
from PyQt5.uic import loadUi
from PyQt5.QtCore import pyqtSlot
import sys
import os
import pandas as pd
from datetime import datetime
import matplotlib.pyplot as plt

class Exchange(QDialog):
    def __init__(self, parent=None):
        super().__init__(parent)
        self.ui = loadUi('./exchange.ui', self)
        self.setWindowTitle('네이버 환율정보 수집기')
        self.ui.show()
        self.country_list = {
            '미국': 'USD', '유럽연합': 'EUR', '일본': 'JPY', '중국': 'CNY',
            '홍콩': 'HKD', '대만': 'TWD', '영국': 'GBP', '오만': 'OMR',
            '캐나다': 'CAD', '스위스': 'CHF','스웨덴': 'SEK', '호주': 'AUD',
            '뉴질랜드':'NZD', '체코': 'CZK', '칠레': 'CLP', '터키': 'TRY',
            '몽골': 'MNT','이스라엘': 'ILS', '덴마크': 'DKK','노르웨이': 'NOK',
            '사우디아라비아': 'SAR', '쿠웨이트': 'KWD', '바레인': 'BHD', '아랍에미리트':
            'AED','요르단': 'JOD','이집트': 'EGP', '태국': 'THB', '싱가포르': 'SGD',
            '말레이시아': 'MYR','인도네시아': 'IDR', '카타르': 'QAR', '카자흐스탄': 'KZT',
            '브루나이': 'BND', '인도': 'INR', '파키스탄': 'PKR', '방글라데시': 'BDT',
            '필리핀': 'PHP', '멕시코': 'MXN', '브라질': 'BRL', '베트남': 'VND',
            '남아프리카공화국':'ZAR', '러시아': 'RUB', '헝가리': 'HUF', '폴란드': 'PLN'
        }
        
    @pyqtSlot()
    def accept(self):
        country_list_selected = {}
        if self.ui.usd.isChecked(): country_list_selected['미국'] = self.country_list['미국']
        if self.ui.eur.isChecked(): country_list_selected['유럽연합'] = self.country_list['유럽연합']
        if self.ui.jpy.isChecked(): country_list_selected['일본'] = self.country_list['일본']
        if self.ui.cny.isChecked(): country_list_selected['중국'] = self.country_list['중국']
        if self.ui.hkd.isChecked(): country_list_selected['홍콩'] = self.country_list['홍콩']
        if self.ui.twd.isChecked(): country_list_selected['대만'] = self.country_list['대만']
        if self.ui.gbp.isChecked(): country_list_selected['영국'] = self.country_list['영국']
        if self.ui.omr.isChecked(): country_list_selected['오만'] = self.country_list['오만']
        if self.ui.cad.isChecked(): country_list_selected['캐나다'] = self.country_list['캐나다']
        if self.ui.chf.isChecked(): country_list_selected['스위스'] = self.country_list['스위스']
        if self.ui.sek.isChecked(): country_list_selected['스웨덴'] = self.country_list['스웨덴']
        if self.ui.aud.isChecked(): country_list_selected['호주'] = self.country_list['호주']
        if self.ui.nzd.isChecked(): country_list_selected['뉴질랜드'] = self.country_list['뉴질랜드']
        if self.ui.czk.isChecked(): country_list_selected['체코'] = self.country_list['체코']
        if self.ui.clp.isChecked(): country_list_selected['칠레'] = self.country_list['칠레']
        if self.ui.try_2.isChecked(): country_list_selected['터키'] = self.country_list['터키']
        if self.ui.mnt.isChecked(): country_list_selected['몽골'] = self.country_list['몽골']
        if self.ui.ils.isChecked(): country_list_selected['이스라엘'] = self.country_list['이스라엘']
        if self.ui.dkk.isChecked(): country_list_selected['덴마크'] = self.country_list['덴마크']
        if self.ui.nok.isChecked(): country_list_selected['노르웨이'] = self.country_list['노르웨이']
        if self.ui.sar.isChecked(): country_list_selected['사우디아라비아'] = self.country_list['사우디아라비아']
        if self.ui.kwd.isChecked(): country_list_selected['쿠웨이트'] = self.country_list['쿠웨이트']
        if self.ui.bhd.isChecked(): country_list_selected['바레인'] = self.country_list['바레인']
        if self.ui.aed.isChecked(): country_list_selected['아랍에미리트'] = self.country_list['아랍에미리트']
        if self.ui.jod.isChecked(): country_list_selected['요르단'] = self.country_list['요르단']
        if self.ui.egp.isChecked(): country_list_selected['이집트'] = self.country_list['이집트']
        if self.ui.thb.isChecked(): country_list_selected['태국'] = self.country_list['태국']
        if self.ui.sgd.isChecked(): country_list_selected['싱가포르'] = self.country_list['싱가포르']
        if self.ui.myr.isChecked(): country_list_selected['말레이시아'] = self.country_list['말레이시아']
        if self.ui.idr.isChecked(): country_list_selected['인도네시아'] = self.country_list['인도네시아']
        if self.ui.qar.isChecked(): country_list_selected['카타르'] = self.country_list['카타르']
        if self.ui.kzt.isChecked(): country_list_selected['카자흐스탄'] = self.country_list['카자흐스탄']
        if self.ui.bnd.isChecked(): country_list_selected['브루나이'] = self.country_list['브루나이']
        if self.ui.inr.isChecked(): country_list_selected['인도'] = self.country_list['인도']
        if self.ui.pkr.isChecked(): country_list_selected['파키스탄'] = self.country_list['파키스탄']
        if self.ui.bdt.isChecked(): country_list_selected['방글라데시'] = self.country_list['방글라데시']
        if self.ui.php.isChecked(): country_list_selected['필리핀'] = self.country_list['필리핀']
        if self.ui.mxn.isChecked(): country_list_selected['멕시코'] = self.country_list['멕시코']
        if self.ui.brl.isChecked(): country_list_selected['브라질'] = self.country_list['브라질']
        if self.ui.vnd.isChecked(): country_list_selected['베트남'] = self.country_list['베트남']
        if self.ui.zar.isChecked(): country_list_selected['남아프리카공화국'] = self.country_list['남아프리카공화국']
        if self.ui.rub.isChecked(): country_list_selected['러시아'] = self.country_list['러시아']
        if self.ui.huf.isChecked(): country_list_selected['헝가리'] = self.country_list['헝가리']
        if self.ui.pln.isChecked(): country_list_selected['폴란드'] = self.country_list['폴란드']
        print('선택된 국가: {}'.format(list(country_list_selected.keys())))
        self._crawling(country_list_selected)
    
    def _crawling(self, country_list_selected):
        # result 폴더 생성
        if not any(['result' == s for s in os.listdir('.')]):
            os.mkdir('./result')
        # 데이터 수집
        result = list()
        print('----- 수집 시작 -----')
        for country in country_list_selected.keys():
            index_cd = country_list_selected[country]
            page = 1
            while(True):
                url = 'https://finance.naver.com/marketindex/exchangeDailyQuote.nhn?marketindexCd=FX_{}KRW&page={}'.format(index_cd, page)
                data = pd.read_html(url)[0]
                result.append(data)
                data.insert(0, '화폐구분', index_cd)
                data.insert(0, '국가구분', country)
                if (data.shape[0] != 10):
                    break
                page += 1
            print('환율정보 수집 완료(구분: {}({}), 페이지 수: {})'.format(country, index_cd, page))
        df = pd.concat(result)
        df.columns = ['국가구분', '화폐구분', '날짜', '매매기준율', '전일대비', '현찰_사실때', '현찰_파실때', '송금_보내실때', '송금_받으실때', 'T/C_사실때', '외화수표_파실때']
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
        
        # 데이터 시각화
        plt.figure(figsize=(12,8))
        for _, grp in df.groupby('화폐구분'):
            plt.plot(grp['날짜'], grp['매매기준율'], label=grp['화폐구분'].iloc[0])
        plt.legend()
        plt.grid()
        plt.tight_layout()
        plt.savefig('./result/exchange_example_{}.png'.format(basetime))

if __name__ == '__main__':
    app = QApplication(sys.argv)
    crawler = Exchange()
    sys.exit(app.exec_())

