from IPython.core.pylabtools import figsize
import numpy as np
import matplotlib.pyplot as plt
import matplotlib
import pymc as pm
matplotlib.rc('font', family='Malgun Gothic')

figsize(12.5, 3.5)
data = np.loadtxt(r"txtdata.csv")
n = len(data)
plt.bar(np.arange(n), data, color="#348ABD")
plt.xlabel("시간(일수)",fontsize=13)
plt.ylabel("수신한 문자 메시지 개수",fontsize=13)
plt.title("사용자의 메시지 습관이 시간에 따라 변하는가?")
plt.xlim(0,n)
plt.show()



alpha = 1.0/data.mean()
lambda_1 = pm.Exponential("lambda_1", alpha)
lambda_2 = pm.Exponential("lambda_2", alpha)
tau = pm.DiscreteUniform("tau", lower=0, upper=n)
print("Random output:", tau.random(), tau.random(), tau.random())

@pm.deterministic
def lambda_(i=tau, x1=lambda_1, x2=lambda_2):
    out = np.zeros(n)
    out[:i] = x1
    out[i:] = x2
    return out

observation = pm.Poisson("obs", lambda_, value=data, observed=True)
model = pm.Model([observation, lambda_1, lambda_2, tau])
mcmc = pm.MCMC(model)
mcmc.sample(40000,10000)



lambda_1_samples = mcmc.trace('lambda_1')[:]
lambda_2_samples = mcmc.trace('lambda_2')[:]
tau_samples = mcmc.trace('tau')[:]

figsize(14.5, 10)

ax = plt.subplot(311)
ax.set_autoscaley_on(False)
plt.hist(lambda_1_samples,histtype="stepfilled",color="#A60626",normed=True,label="$\lambda_1$의 사후확률분포",bins=30,alpha=0.85)
plt.legend(loc="upper left")
plt.title(r"모수 $\lambda_1,\;\lambda_2\;\tau$의 사후확률분포")
plt.xlim([15,30])
plt.xlabel("$\lambda_1$ 값")
plt.ylabel("밀도", fontsize=13)

ax = plt.subplot(312)
ax.set_autoscaley_on(False)
plt.hist(lambda_2_samples,color="#7A68A6",histtype="stepfilled",normed=True,label="$\lambda_2$의 사후확률분포",bins=30,alpha=0.85)
plt.legend(loc="upper left")
plt.xlim([15,30])
plt.xlabel("$\lambda_2$ 값")
plt.ylabel("밀도", fontsize=13)

ax = plt.subplot(313)
w = 1.0 / tau_samples.shape[0]*np.ones_like(tau_samples)
plt.hist(tau_samples, bins=n, alpha=1, label=r"$\tau$의 사후확률분포", color="#467821",weights=w,rwidth=2.0)
plt.xticks(np.arange(n))
plt.legend(loc="upper left")
plt.xlim([41, 46])
plt.ylim([0,0.6])
plt.xlabel(r"$\tau$ (일수)",fontsize=13)
plt.ylabel("확률",fontsize=13)

plt.show()
