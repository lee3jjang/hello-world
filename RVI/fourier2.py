import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
%matplotlib inline

df = pd.read_csv('../rvi/LPG 22-24cbm NB prices.csv')
ts = df.price
ts.index = pd.to_datetime(df.date)
plt.plot(ts)
ts.describe()

from sklearn import linear_model
from sklearn.preprocessing import scale

t = np.arange(len(ts)).reshape(len(ts),1)
lm = linear_model.LinearRegression()
model = lm.fit(t,np.log(ts.values))
yhat = np.exp(model.predict(t))
ts_adj = scale((ts.values - yhat))

plt.figure(figsize=(22,6))

ax = plt.subplot(121)
ax.plot(yhat)
ax.plot(ts.values)
ax.set_title('Original Price')

ax = plt.subplot(122)
ax.plot(ts_adj)
ax.set_title('Inflation-adjusted and Normalized Price')

plt.show()

print("Inflation Rate (per yr) : {0:0.2f}%".format((np.exp(12*model.coef_[0])-1)*100))

w = np.fft.fft(ts_adj)

k = 10
low = w.copy()
low[k:] = 0
high = w.copy()
high[:-k] = 0

####### low-pass filter ########
a = w.real[:k]
b = w.imag[:k]
omega_low = np.array([2*np.pi/len(ts)*i for i in range(k)])
curve_low = lambda t:(np.sum(-b*np.sin(omega_low*t))+np.sum(a*np.cos(omega_low*t)))/len(ts)
################################

####### high-pass filter #######
c = w.real[-k:]
d = w.imag[-k:]
omega_high = np.array([2*np.pi/len(ts)*i for i in np.arange(len(ts)-k,len(ts))])
curve_high = lambda t:(np.sum(-d*np.sin(omega_high*t))+np.sum(c*np.cos(omega_high*t)))/len(ts)
################################


plt.figure(figsize=(22,6))

y_low = np.fft.ifft(low)
y_high = np.fft.ifft(high)
y_sum = y_low + y_high
resid = ts_adj - y_sum
y_pred = np.array([curve_high(x)+curve_low(x) for x in range(len(ts)+120)])

plt.figure(figsize=(22,6))

ax = plt.subplot(121)
#plt.plot(y_low.real, label='low')
#plt.plot([curve_low(x) for x in range(len(ts))], label='low2')
#plt.plot(y_high.real, label='high')
#plt.plot([curve_high(x) for x in range(len(ts))], label='high2')
#plt.plot(y_sum.real, label='Prediction')
plt.plot(y_pred, label='Prediction')
plt.legend()

plt.plot(ts_adj)
ax.set_title('Original and Filtered Price Cycle')

ax = plt.subplot(122)
ax.plot(resid.real)
ax.set_title('Residuals')

plt.show()

t = np.arange(len(ts)+120).reshape(len(ts)+120,1)
yhat2 = np.exp(model.predict(t))
y_unwind = y_pred*(ts.values - yhat).std()+(ts.values - yhat).mean() + yhat2
plt.plot(y_unwind)
print("Predicted Price Increase (10y) : {0:0.2f}%".format((y_unwind[-1]/y_unwind[120]-1)*100))
