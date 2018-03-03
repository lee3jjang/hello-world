import numpy as np
import pandas as pd
import seaborn as sns

ver = pd.read_csv("downloads/train.csv")

ver.Pclass.value_counts().plot(kind='barh')

dir(ver.Pclass.value_counts().plot)[-9:]

ver.Pclass.value_counts().plot(kind='bar')

ver.Age.plot(kind='density')

ver.Age.plot(kind='hist')

ver.Embarked.value_counts().plot(kind='pie')

g = sns.factorplot('Sex','Fare',hue='Pclass',data=ver,kind='box',palette="PRGn",size=3,aspect=2)
g.set(ylim=(0,300))

sns.factorplot('Sex','Fare',data=ver,palette="BuPu_d",kind='bar')

#sns.violinplot()
sns.violinplot(ver.Embarked, ver.Fare, palette="BuPu_d")
sns.despine(left=True)

sns.regplot("Age","Fare",data=ver, robust=False, ci=99.9, color='seagreen')

sns.factorplot('Sex','Fare',data=ver,palette="BuPu_d",kind='bar',estimator=np.max)
