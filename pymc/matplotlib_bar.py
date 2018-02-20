from IPython.core.pylabtools import figsize
import numpy as np
import matplotlib.pyplot as plt
import matplotlib
matplotlib.rc('font', family='Malgun Gothic')

figsize(12.5, 4.5)
data = np.loadtxt(r"txtdata.csv")
n = len(data)
plt.bar(np.arange(n), data, color="#348ABD")
plt.xlabel("시간(일수)",fontsize=13)
plt.ylabel("수신한 문자 메시지 개수",fontsize=13)
plt.title("사용자의 메시지 습관이 시간에 따라 변하는가?")
plt.xlim(0,n)
plt.show()