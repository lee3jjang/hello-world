from glob import glob
import PyPDF2
import re
import pandas as pd

# LPG Yoyage Rattets $/mt 44/46K mt Gulf/Jap
directory = 'C:/Users/11700205/Clarkson Report/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

data = []
p = re.compile(r"""
    Voyage[ ]Rates,?\ [(]?\$/mt[)]?\ ?(44/46|46\.2)K\ mt\ Gulf/Jap\*?
    (\d{1,3}[.]\d{2})
    (\d{1,3}[.]\d{2})
    (\d{1,3}[.]\d{2})
    (\d{1,3}[.]\d{2})
    (\d{1,3}[.]\d{2})
""", re.VERBOSE)
i = 1
index = [1,2,3,4,5,6]
for fp in files:
    row = []
    reader = PyPDF2.PdfFileReader(fp)
    page = reader.getPage(5)
    content = page.extractText()
    m = p.search(content)
    for j in index:
        row.append(m.group(j))
    print(i,":",row)
    data.append(row)
    i = i+1
    
df = pd.DataFrame(data)
df
