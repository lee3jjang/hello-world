from glob import glob
import PyPDF2
import re

directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    <5,000\ cbm
    (\d{1,3}(,\d{3})?)
    (\d{1,3}(,\d{3})?)
    (\d{1,3}(,\d{3})?)
    (\d{1,3}(,\d{3})?)
    (\d{1,3}(,\d{3})?)
    (\d{1,3}(,\d{3})?)
""", re.VERBOSE)
    
fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(17)
content = page.extractText()
m = p.search(content)
m.group(1), m.group(3), m.group(5), m.group(7), m.group(9), m.group(11)



directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    5-20,000\ cbm
    (\d{1,3}(,\d{3})?)
    (\d{1,3}(,\d{3})?)
    (\d{1,3}(,\d{3})?)
    (\d{1,3}(,\d{3})?)
    (\d{1,3}(,\d{3})?)
    (\d{1,3}(,\d{3})?)
""", re.VERBOSE)
    
fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(17)
content = page.extractText()
m = p.search(content)
m.group(1), m.group(3), m.group(5), m.group(7), m.group(9), m.group(11)



directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    20-40,000\ cbm
    (\d{1,3}(,\d{3})?)
    (\d{1,3}(,\d{3})?)
    (\d{1,3}(,\d{3})?)
    (\d{1,3}(,\d{3})?)
    (\d{1,3}(,\d{3})?)
    (\d{1,3}(,\d{3})?)
""", re.VERBOSE)
    
fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(17)
content = page.extractText()
m = p.search(content)
m.group(1), m.group(3), m.group(5), m.group(7), m.group(9), m.group(11)



directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    40-60,000\ cbm
    (\d{1,3}(,\d{3})?)
    (\d{1,3}(,\d{3})?)
    (\d{1,3}(,\d{3})?)
    (\d{1,3}(,\d{3})?)
    (\d{1,3}(,\d{3})?)
    (\d{1,3}(,\d{3})?)
""", re.VERBOSE)
    
fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(17)
content = page.extractText()
m = p.search(content)
m.group(1), m.group(3), m.group(5), m.group(7), m.group(9), m.group(11)



directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    >60,000\ cbm
    (\d{1,3}(,\d{3})?)
    (\d{1,3}(,\d{3})?)
    (\d{1,3}(,\d{3})?)
    (\d{1,3}(,\d{3})?)
    (\d{1,3}(,\d{3})?)
    (\d{1,3}(,\d{3})?)
""", re.VERBOSE)
    
fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(17)
content = page.extractText()
m = p.search(content)
m.group(1), m.group(3), m.group(5), m.group(7), m.group(9), m.group(11)



directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    TOTAL\ FLEET
    (\d{1,3}(,\d{3})?)
    (\d{1,3}(,\d{3})?)
    (\d{1,3}(,\d{3})?)
    (\d{1,3}(,\d{3})?)
    (\d{1,3}(,\d{3})?)
    (\d{1,3}(,\d{3})?)
""", re.VERBOSE)
    
fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(17)
content = page.extractText()
m = p.search(content)
m.group(1), m.group(3), m.group(5), m.group(7), m.group(9), m.group(11)



directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    TOTAL\ M\.\ DWT
    (\d{1,3}.\d{1})
    (\d{1,3}.\d{1})
    (\d{1,3}.\d{1})
    (\d{1,3}.\d{1})
    (\d{1,3}(,\d{3}))
    (\d{1,3}.\d{1})
""", re.VERBOSE)
    
fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(17)
content = page.extractText()
m = p.search(content)
m.group(1), m.group(2), m.group(3), m.group(4), m.group(5), m.group(7)
