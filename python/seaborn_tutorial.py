import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns
np.random.seed(sum(map(ord,"aesthetics")))
def sinplot(flip=1):
    x = np.linspace(0,14,100)
    for i in range(1,7):
        plt.plot(x, np.sin(x+i*0.5)*(7-i)*flip)
        
sinplot()
plt.show()

#################################################

sns.set()
sinplot()
plt.show()

#################################################

sns.set_style("whitegrid")
data = np.random.normal(size=(20,6)) + np.arange(6)
sns.boxplot(data=data)
plt.show()

#################################################

sns.set_style("dark")
sinplot()
plt.show()

#################################################

sns.set_style("ticks")
sinplot()
plt.show()

#################################################
sinplot()
sns.despine()
plt.show()

#################################################

f, ax = plt.subplots()
sns.violinplot(data=data)
sns.despine(offset=10, trim=True)
plt.show()
#################################################

sns.set_style("whitegrid")
sns.boxplot(data=data, palette="deep")
sns.despine(left=True)
plt.show()

#################################################

with sns.axes_style("darkgrid"):
    plt.subplot(211)
    sinplot()
plt.subplot(212)
sinplot(-1)
plt.show()

#################################################

sns.axes_style()

#################################################

sns.set_style("darkgrid", {"axes.facecolor":".9"})
sinplot()
plt.show()

#################################################

sns.set()
sns.set_context("paper")
sinplot()
plt.show()

#################################################

sns.set_context("talk")
sinplot()
plt.show()

#################################################

sns.set_context("poster")
sinplot()
plt.show()

#################################################

sns.set_context("notebook", font_scale=1.5, rc={"lines.linewidth":2.5})
sinplot()
plt.show()


