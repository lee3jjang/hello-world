import numpy as np
import pandas as pd

ver = pd.read_csv("downloads/train.csv")

melt = pd.melt(ver, id_vars = 'Name')

melt.iloc[0:5,:]

ver.describe()

pd.crosstab(ver['Sex'],ver['Survived'])

subset = ver[(ver['Age'] > 18) & (ver['Fare'] > 50)]
subset.head()

subset.shape

qry = '(Age>18)&(Fare>50)'
qry1 = ver.query(qry)
qry1.head(5)

grouped1 = ver.groupby(['Pclass','Sex']).mean()

grouped1

(ver.loc[:,'Fare'] > 50).all()

#ver.loc[:,'Fare']
ver.Fare.describe()

grpagg = ver.groupby('Embarked').aggregate(np.mean)
grpagg

criterion = ver['Sex'].map(lambda x: x.startswith('m'))
criterion.head()
