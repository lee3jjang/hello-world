from glob import glob
import PyPDF2
import re

directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    Gas\ Vessels\n 
    (\d{1,2}.\d{1})
    (\d{1,2}.\d{1})
    (\d{1,2}.\d{1})
    (\d{1,2}.\d{1})
""", re.VERBOSE)
    
fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(14)
content = page.extractText()
m = p.search(content)

m.group(1), m.group(2), m.group(3), m.group(4)



directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

# Scrap Prices
p = re.compile(r"""
    Tankers
    (\d{3})
    (\d{3})
    (\d{3})
    (\d{3})
""", re.VERBOSE)
    
fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(14)
content = page.extractText()
m = p.search(content)

m.group(1), m.group(2), m.group(3), m.group(4)



directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

# China
p = re.compile(r"""
    China\n?
    (\d{1,3}.\d)
    (\d{1,3}.\d)
    (\d{1,3}.\d)
    (\d{1,3}.\d)
""", re.VERBOSE)
    
fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(14)
content = page.extractText()
m = p.search(content)

m.group(1), m.group(2), m.group(3), m.group(4)



directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

# India
p = re.compile(r"""
    India\n?
    (\d{1,3}.\d)
    (\d{1,3}.\d)
    (\d{1,3}.\d)
    (\d{1,3}.\d)
""", re.VERBOSE)
    
fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(14)
content = page.extractText()
m = p.search(content)

m.group(1), m.group(2), m.group(3), m.group(4)



directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

# Bangladesh
p = re.compile(r"""
    Bangladesh\n?
    (\d{1,3}.\d)
    (\d{1,3}.\d)
    (\d{1,3}.\d)
    (\d{1,3}.\d)
""", re.VERBOSE)
    
fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(14)
content = page.extractText()
m = p.search(content)

m.group(1), m.group(2), m.group(3), m.group(4)



directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

# Pakistan
p = re.compile(r"""
    Pakistan\n?
    (\d{1,3}.\d)
    (\d{1,3}.\d)
    (\d{1,3}.\d)
    (\d{1,3}.\d)
""", re.VERBOSE)
    
fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(14)
content = page.extractText()
m = p.search(content)

m.group(1), m.group(2), m.group(3), m.group(4)



directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

# Pakistan
p = re.compile(r"""
    Other\n?
    (\d{1,3}.\d)
    (\d{1,3}.\d)
    (\d{1,3}.\d)
    (\d{1,3}.\d)
""", re.VERBOSE)
    
fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(14)
content = page.extractText()
m = p.search(content)

m.group(1), m.group(2), m.group(3), m.group(4)from glob import glob
import PyPDF2
import re

directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    Gas\ Vessels\n 
    (\d{1,2}.\d{1})
    (\d{1,2}.\d{1})
    (\d{1,2}.\d{1})
    (\d{1,2}.\d{1})
""", re.VERBOSE)
    
fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(14)
content = page.extractText()
m = p.search(content)

m.group(1), m.group(2), m.group(3), m.group(4)



directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

# Scrap Prices
p = re.compile(r"""
    Tankers
    (\d{3})
    (\d{3})
    (\d{3})
    (\d{3})
""", re.VERBOSE)
    
fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(14)
content = page.extractText()
m = p.search(content)

m.group(1), m.group(2), m.group(3), m.group(4)



directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

# China
p = re.compile(r"""
    China\n?
    (\d{1,3}.\d)
    (\d{1,3}.\d)
    (\d{1,3}.\d)
    (\d{1,3}.\d)
""", re.VERBOSE)
    
fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(14)
content = page.extractText()
m = p.search(content)

m.group(1), m.group(2), m.group(3), m.group(4)



directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

# India
p = re.compile(r"""
    India\n?
    (\d{1,3}.\d)
    (\d{1,3}.\d)
    (\d{1,3}.\d)
    (\d{1,3}.\d)
""", re.VERBOSE)
    
fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(14)
content = page.extractText()
m = p.search(content)

m.group(1), m.group(2), m.group(3), m.group(4)



directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

# Bangladesh
p = re.compile(r"""
    Bangladesh\n?
    (\d{1,3}.\d)
    (\d{1,3}.\d)
    (\d{1,3}.\d)
    (\d{1,3}.\d)
""", re.VERBOSE)
    
fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(14)
content = page.extractText()
m = p.search(content)

m.group(1), m.group(2), m.group(3), m.group(4)



directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

# Pakistan
p = re.compile(r"""
    Pakistan\n?
    (\d{1,3}.\d)
    (\d{1,3}.\d)
    (\d{1,3}.\d)
    (\d{1,3}.\d)
""", re.VERBOSE)
    
fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(14)
content = page.extractText()
m = p.search(content)

m.group(1), m.group(2), m.group(3), m.group(4)



directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

# Other
p = re.compile(r"""
    Other\n?
    (\d{1,3}.\d)
    (\d{1,3}.\d)
    (\d{1,3}.\d)
    (\d{1,3}.\d)
""", re.VERBOSE)
    
fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(14)
content = page.extractText()
m = p.search(content)

m.group(1), m.group(2), m.group(3), m.group(4)
