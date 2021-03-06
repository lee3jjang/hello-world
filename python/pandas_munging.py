
import numpy as np
import pandas as pd

ver = pd.read_csv("train.csv")

pd.set_option('display.max_columns',6)
ver.head(3)

ver.shape

len(ver)

ver.columns

ver['Name'][:5]

interval = pd.cut(ver['Fare'],11)
interval[:5]

pd.value_counts(interval)

ver.iloc[0,0:6]

sortedData = ver.sort_values(['Fare','Pclass','PassengerId'])
#sortedData.loc[:,['Fare','Pclass','PassengerId']].head(5)
sortedData.iloc[:,[0,2,3,4,9]].head(5)

ver['Sex'].value_counts()

list(zip(ver.columns, [type(x) for x in ver.iloc[0,:]]))

ver.dtypes

ver['Embarked'].unique()

ver.loc[0:5,'Embarked'] == "S"
