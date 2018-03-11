from glob import glob
import PyPDF2
import re

directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    LPG82,000m³\n
    (\d{1,3}.\d{1})
    (\d{1,3}.\d{1})
    (\d{1,3}.\d{1})
    (\d{1,3}.\d{1})
""", re.VERBOSE)
    
fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(13)
content = page.extractText()
m = p.search(content)

m.group(1), m.group(2), m.group(3), m.group(4)



directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    Clarkson\ Index
    (\d{3})
    (\d{3})
    (\d{3})
    (\d{3})
""", re.VERBOSE)
    
fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(13)
content = page.extractText()
m = p.search(content)

m.group(1), m.group(2), m.group(3), m.group(4)



directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    TOTAL\ CONTRACTING
    (\d{1,3}(,\d{3})?)
    (\d{1,3}(,\d{3})?)
    (\d{1,3}(,\d{3})?)
    (\d{1,3}(,\d{3})?)

""", re.VERBOSE)
    
fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(13)
content = page.extractText()
m = p.search(content)

m.group(1), m.group(3), m.group(5), m.group(7)
