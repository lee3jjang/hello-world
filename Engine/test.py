import pandas as pd
import sys

path = sys.argv[-1]
data = pd.DataFrame({'x':[1,2,3], 'y':[4,2,3]})
writer = pd.ExcelWriter(path+'result.xlsx', engine='xlsxwriter')
data.to_excel(writer)
writer.save()
writer.close()
