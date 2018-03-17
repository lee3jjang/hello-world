import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
import numpy as np
plt.figure(figsize=(12,10))

df = pd.read_csv('LPG 22-24cbm NB prices.csv')
ts = df.price
ts.index = pd.to_datetime(df.date)

sns.set_style('darkgrid')
plt.plot(ts)
sns.despine(left=False)
plt.tight_layout()

rolmean = ts.rolling(window=180, center=False).mean()
rolstd = ts.rolling(window=180, center=False).std()

# Rolling Statistics
orig = plt.plot(ts, color='blue', label='Original')
mean = plt.plot(rolmean, color='red', label='Rolling Mean')
std = plt.plot(rolstd, color='black', label='Rolling Std')
plt.legend(loc='best')
plt.title('Rolling Mean & Standard Deviation')
plt.tight_layout()

# Checking Stationarity of a Times Series
import statsmodels.api as sm

def test_stationarity(ts):
    print('Results of Dickey-Fuller Test:')
    dftest = sm.tsa.adfuller(ts, autolag='AIC')
    dfoutput = pd.Series(dftest[0:4], index=['Test Statistics','p-value','#Lags Used','Number of Observations Used'])
    for key, value in dftest[4].items():
        dfoutput['Critical Value (%s)' % key] = value
    print(dfoutput)
    
test_stationarity(ts)

# Differencing
ts_log = np.log(ts)
ts_log_diff = ts_log - ts_log.shift()
plt.plot(ts_log_diff)
plt.legend(loc='best')
plt.tight_layout()

ts_log_diff.dropna(inplace=True)
test_stationarity(ts_log_diff)

# Decomposition
decomposition = sm.tsa.seasonal_decompose(ts_log)
trend = decomposition.trend
seasonal = decomposition.seasonal
residual = decomposition.resid

decomposition.plot()
plt.legend(loc='best')
plt.tight_layout()

ts_log_decompose = residual
ts_log_decompose.dropna(inplace=True)
test_stationarity(ts_log_decompose)

# ACF and PACF

lag_acf = sm.tsa.acf(ts_log_diff, nlags=20)
lag_pacf = sm.tsa.pacf(ts_log_diff, nlags=20, method='ols')

plt.subplot(121)
plt.plot(lag_acf)
plt.axhline(y=0, linestyle='--', color='gray')
plt.axhline(y=-1.96/np.sqrt(len(ts_log_diff)), linestyle='--', color='gray')
plt.axhline(y=1.96/np.sqrt(len(ts_log_diff)), linestyle='--', color='gray')
plt.title('Autocorrelation Function')

plt.subplot(122)
plt.plot(lag_pacf)
plt.axhline(y=0, linestyle='--', color='gray')
plt.axhline(y=-1.96/np.sqrt(len(ts_log_diff)), linestyle='--', color='gray')
plt.axhline(y=1.96/np.sqrt(len(ts_log_diff)), linestyle='--', color='gray')
plt.title('Partial Autocorrelation Function')

# p = 3, q = 3

# AR Model
import warnings
warnings.filterwarnings("ignore")

model = sm.tsa.ARIMA(ts_log, order=(3,1,0))  #(p,d,q)
results_AR = model.fit(disp=-1)
plt.plot(ts_log_diff)
plt.plot(results_AR.fittedvalues, color='red')
plt.title('Residual Sum Squared: %.4f' % sum((results_AR.fittedvalues - ts_log_diff)**2))

# MA Model

model = sm.tsa.ARIMA(ts_log, order=(0,1,3))  #(p,d,q)
results_MA = model.fit(disp=-1)
plt.plot(ts_log_diff)
plt.plot(results_MA.fittedvalues, color='red')
plt.title('Residual Sum Squared: %.4f' % sum((results_MA.fittedvalues - ts_log_diff)**2))

# Combined Model

model = sm.tsa.ARIMA(ts_log, order=(3,1,3))  #(p,d,q)
results_ARIMA = model.fit(disp=-1)
plt.plot(ts_log_diff)
plt.plot(results_ARIMA.fittedvalues, color='red')
plt.title('Residual Sum Squared: %.4f' % sum((results_ARIMA.fittedvalues - ts_log_diff)**2))

predictions_ARIMA_diff = pd.Series(results_ARIMA.fittedvalues, copy=True)
print(predictions_ARIMA_diff.head())

predictions_ARIMA_diff_cumsum = predictions_ARIMA_diff.cumsum()
print(predictions_ARIMA_diff_cumsum.head())

predictions_ARIMA_log = pd.Series(ts_log.iloc[0], index=ts_log.index)
predictions_ARIMA_log = predictions_ARIMA_log.add(predictions_ARIMA_diff_cumsum, fill_value=0)
predictions_ARIMA_log.head()

predictions_ARIMA = np.exp(predictions_ARIMA_log)
plt.plot(ts)
plt.plot(predictions_ARIMA)
plt.title('RMSE: %.4f' % np.sqrt(sum((predictions_ARIMA - ts)**2)/len(ts)))
