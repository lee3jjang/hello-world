from glob import glob
import PyPDF2
import re
import pandas as pd

directory = 'C:/Users/11700205/Clarkson Report/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

data = []
p = re.compile(r"""
    (82,000|84,000)m³\ modern\~?
    (\d{1,3},\d{3})\n?
    (\d{1,3},\d{3})\n?
    (\d{1,3},\d{3})\n?
    (\d{1,3},\d{3})\n?
    (\d{1,3},\d{3})
""", re.VERBOSE)
i = 0
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

#####################################################################

from glob import glob
import PyPDF2
import re
# LPG Yoyage Rattets $/mt 44/46K mt Gulf/Jap
directory = 'C:/Users/11700205/Clarkson Report/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

data = []
p = re.compile(r"""
    78,000m³\ modern\~?
    (\d{1,3},\d{3})\n?
    (\d{1,3},\d{3})\n?
    (\d{1,3},\d{3})\n?
    (\d{1,3},\d{3})\n?
    (\d{1,3},\d{3})
""", re.VERBOSE)
i = 0
index = [1,2,3,4,5]
for fp in files:
    row = []
    reader = PyPDF2.PdfFileReader(fp)
    page = reader.getPage(5)
    content = page.extractText()
    m = p.search(content)
    try:
        for j in index:
            row.append(m.group(j))
    except AttributeError:
        break
    print(i,":",row)
    data.append(row)
    i = i+1
    
df = pd.DataFrame(data)
