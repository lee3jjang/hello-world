import matplotlib.pyplot as plt
from datetime import datetime
import pandas as pd
from pandas_datareader import data as pdr
import numpy as np

start = datetime(2017,1,1)
end = datetime(2018,3,3)
df = pdr.get_data_google("KRX:KOSPI",start,end)

top = plt.subplot2grid((5,4),(0,0), rowspan=3, colspan=4)
top.plot(df.index, df["Close"])
plt.title("google Price from 2017 - 2018")

bottom = plt.subplot2grid((5,4),(4,0), rowspan=1, colspan=4)
bottom.bar(df.index, df["Volume"])
plt.title("google Trading Volume")

plt.gcf().set_size_inches(15,8)

df.Volume.plot()

df.plot(subplots=True, figsize=(8,8))
plt.legend(loc='best')
plt.show()

df['Close_MA3'] = df['Close'].rolling(window=3).apply(np.mean)
df['Close_MA5'] = df['Close'].rolling(window=5).apply(np.mean)
df['Close_MA10'] = df['Close'].rolling(window=10).apply(np.mean)
df.plot(subplots=True, figsize=(8,8))
plt.legend(loc='best')
plt.show()

df.Close.plot(label='Google')
df.Close_MA10.plot(label='MA10')
plt.legend()
plt.gcf().set_size_inches(15,8)
plt.show()

df.Close.plot(kind='kde')
plt.gcf().set_size_inches(15,8)
