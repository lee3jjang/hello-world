from glob import glob
import PyPDF2
import re

directory = 'C:/Users/11700205/Clarkson Report/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    ClarkSea[ ]Index[$]
    (\d{1,3}(,\d{3})*)
""", re.VERBOSE)
data = []
for f in files:
    with open(f, 'rb') as fp:
        reader = PyPDF2.PdfFileReader(fp)
        page = reader.getPage(0)
        content = page.extractText()
        m = p.search(content)
    print(m.group(1))
    data.append(m.group(1))
data
