import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns
plt.figure(figsize=(20,6))

Fs = 60.0                         # 1분에 60번 샘플링
Ts = 1.0/Fs                       # 1/60분에 1번씩 샘플링
t = np.arange(0,10,Ts)            # 10분 샘플링
ff = 3                            # 진동수 3Hz/분
y = np.sin(2*np.pi*ff*t) + 3*np.sin(2*np.pi*12*t)

sns.set_style('whitegrid')
plt.plot(t,y)
sns.despine(left=False)
plt.tight_layout()

n = len(y)             # (총 600번) 샘플링 횟수 / 총 시간
half = int(n/2)
k = np.arange(n)       # 샘플링 번호  
T = n/Fs               # 샘플링 시간
frq = k/T              # 샘플링 번호 / 총시간
frq = frq[range(half)]

plt.figure(figsize=(20,6))

Y = np.fft.fft(y)/n
Y = Y[range(half)]
plt.plot(frq,abs(Y))

plt.tight_layout()

abs(Y)[30], abs(Y)[120]
