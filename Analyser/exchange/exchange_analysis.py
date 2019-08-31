#!/usr/bin/env python
# coding: utf-8

# In[1]:


from PyQt5.QtWidgets import QApplication, QDialog, QFileDialog
from PyQt5.uic import loadUi
from PyQt5.QtCore import pyqtSlot
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
from pandas.plotting import register_matplotlib_converters
import matplotlib
import numpy as np
from datetime import datetime
import scipy.stats
import sys
import os


class ExchangeAnalysis(QDialog):
    def __init__(self, parent=None):
        super().__init__(parent)
        self.ui = loadUi('./exchange_analysis.ui', self)
        self.setWindowTitle('환율 분석기')
        self.ui.show()
        
    
    @pyqtSlot()
    def findPath(self):
        fname = QFileDialog.getOpenFileName(self)
        self.lineEdit.setText(fname[0])
        
    @pyqtSlot()
    def accept(self):
        filepath = self.lineEdit.text()
        if filepath == '':
            raise Exception('파일경로를 입력해야 합니다.')
            
        if self.usd.isChecked():
            currency = 'USD'
        elif self.jpy.isChecked():
            currency = 'JPY'
        elif self.eur.isChecked():
            currency = 'EUR'
         
        print('----- 분석 시작(화폐: {}) -----'.format(currency))
        self._analysis(filepath, currency)
        print('----- 분석 완료 -----')
        
    def _analysis(self, filepath, currency):
        register_matplotlib_converters()
        sns.set_style('darkgrid')
        matplotlib.rc('font', family='Malgun Gothic', size='10')
        matplotlib.rcParams['axes.unicode_minus'] = False
        if not any([s == 'result' for s in os.listdir()]):
            os.mkdir('result')

        # 데이터 불러오기
        exchange = pd.read_excel(filepath)

        # 데이터 전처리
        exchange_currency = exchange.query('화폐구분 == @currency')[['날짜', '매매기준율']].sort_values(by='날짜').set_index('날짜')['매매기준율']
        # exchange_currency.describe()


        # 탐색적 데이터 분석
        fig, ax = plt.subplots(3, 2, figsize=(13, 18))

        ax[0][0].plot(exchange_currency)
        ax[0][0].set_title('KRW/{} 환율 추이'.format(currency))
        sns.distplot(exchange_currency, ax=ax[0][1])
        ax[0][1].set_title('KRW/{} 환율 분포'.format(currency))

        ax[1][0].plot(exchange_currency.pct_change()[1:])
        ax[1][0].set_title('KRW/{} 환율 증감량 추이'.format(currency))
        sns.distplot(exchange_currency.pct_change()[1:], ax=ax[1][1])
        ax[1][1].set_title('KRW/{} 환율 증감량 분포'.format(currency))


        ax[2][0].plot(exchange_currency.diff()[1:])
        ax[2][0].set_title('KRW/{} 환율 증감율 추이'.format(currency))
        sns.distplot(exchange_currency.diff()[1:], ax=ax[2][1])
        ax[2][1].set_title('KRW/{} 환율 증감율 분포'.format(currency))

        plt.savefig('./result/exchange_exploratory_data_analysis_{}.png'.format(currency))
        # plt.show()

        exchange_currency_diff = exchange_currency.diff()[1:]
        # exchange_currency_diff.describe()



        # 모수 추정
        # dist_list = ['dgamma', 'norm', 't', 'nct', 'dweibull']
        dist_list = ['dgamma', 'norm', 'dweibull']
        k = len(dist_list)
        fig, ax = plt.subplots(k, 2, figsize=(13, 6*k))
        q = 0.001

        result = []
        for i, dist_name in enumerate(dist_list):
            dist = getattr(scipy.stats, dist_name)
            param = dist.fit(exchange_currency_diff)
            X = dist(*param[:-2], loc=param[-2], scale=param[-1])
            x_range = np.linspace(X.ppf(q), X.ppf(1-q), 1000)
            y = X.pdf(x_range)
            ax[i][0].plot(x_range, y, label='Fitted({})'.format(dist_name))
            sns.distplot(exchange_currency_diff, bins=300, label='Actual', ax=ax[i][0])
            ax[i][0].set_xlim(np.quantile(exchange_currency_diff, q), np.quantile(exchange_currency_diff, 1-q))
            ax[i][0].set_title('Actual vs Fitted (distribution: {})'.format(dist_name))
            ax[i][0].legend()
            scipy.stats.probplot(exchange_currency_diff, dist=dist, sparams=(*param[:-2], param[-2], param[-1]), plot=ax[i][1])

            loglik = np.sum(np.log(X.pdf(exchange_currency_diff)))
            n = exchange_currency_diff.size
            p = len(param)
            aic = -2*loglik + 2*p
            bic = -2*loglik + p*np.log(n)
            result.append([dist_name, n, p, loglik, aic, bic, param])

        plt.savefig('./result/estimation_result_{}.png'.format(currency))
        # plt.show()
        estimation_result = pd.DataFrame(result, columns=['name', 'n', 'p', 'loglik', 'AIC', 'BIC', 'parameter'])
        estimation_result = estimation_result.sort_values(by='loglik', ascending=False)

        # 시뮬레이션
        n = exchange_currency.size
        t = 250
        num_simulation = 1000

        dist_selected = estimation_result.iloc[0]
        dist = getattr(scipy.stats, dist_selected['name'])
        param = dist_selected['parameter']
        X = dist(*param[:-2], loc=param[-2], scale=param[-1])
        np.random.seed(20190831)
        exchange_currency_diff_simulation = X.rvs([num_simulation, t])
        exchange_currency_simulation = pd.DataFrame(np.insert(exchange_currency_diff_simulation, 0, exchange_currency[-1], axis=1).cumsum(axis=1)[:, 1:])

        # 시뮬레이션 결과 시각화
        plt.figure(figsize=(13, 6))
        plt.plot(np.arange(n), exchange_currency)
        for _, simulation in exchange_currency_simulation.iterrows():
            plt.plot(np.arange(n, n+t), simulation)
        plt.title('KRW/{} Monte Carlo Simulation Result'.format(currency))
        plt.savefig('./result/exchange_monte_carlo_simulation_{}.png'.format(currency))
        # plt.show()

        # exchange_currency_simulation.iloc[:, -1].describe()

        # 결과 저장
        today = datetime.now().strftime('%Y%m%d%H%M%S')

        writer = pd.ExcelWriter('./result/estimation_result_{}_{}.xlsx'.format(currency, today), 'xlsxwriter')
        estimation_result.to_excel(writer, index=False)
        writer.save()
        writer.close()

        writer = pd.ExcelWriter('./result/exchange_simulation_{}_{}.xlsx'.format(currency, today), 'xlsxwriter')
        exchange_currency_simulation.to_excel(writer, index=True)
        writer.save()
        writer.close()
        
if __name__ == '__main__':
    app = QApplication(sys.argv)
    analyser = ExchangeAnalysis()
    sys.exit(app.exec_())

