from glob import glob
import PyPDF2
import re
# LPG Yoyage Rattets $/mt 44/46K mt Gulf/Jap
directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    LPG[ ]Voyage[ ]Rates,[ ][$]/mt[ ]44/46K[ ]mt[ ]Gulf/Jap[*]
    (\d{1,3}[.]\d{2})
    (\d{1,3}[.]\d{2})
    (\d{1,3}[.]\d{2})
    (\d{1,3}[.]\d{2})
    (\d{1,3}[.]\d{2})
""", re.VERBOSE)

fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(5)
content = page.extractText()
m = p.search(content)
print(m.group(5))



# LPG TCE, $/day 82,000m³ modern
directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    82,000m³[ ]modern
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    \n(\d{1,3}[,]\d{3})
""", re.VERBOSE)

fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(5)
content = page.extractText()
m = p.search(content)
m.group(5)



# LPG TCE, $/day 78,000m³ modern
directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    78,000m³[ ]modern
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    \n(\d{1,3}[,]\d{3})
""", re.VERBOSE)

fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(5)
content = page.extractText()
m = p.search(content)
m.group(5)




# LPG 12 mths T/C, $/day 82,000m³ modern
directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    82,000m³[ ]modern[ ]
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
""", re.VERBOSE)

fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(5)
content = page.extractText()
m = p.search(content)
m.group(5)



# LPG 12 mths T/C, $/day 78,000m³ modern
directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    78,000m³[ ]modern
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
""", re.VERBOSE)

fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(5)
content = page.extractText()
m = p.search(content)
m.group(5)




# LPG 12 mths T/C, $/day 59,000m³ modern
directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    59,000m³[ ]modern
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
""", re.VERBOSE)

fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(5)
content = page.extractText()
m = p.search(content)
m.group(5)



# LPG 12 mths T/C, $/day 35,000m³
directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    35,000m³
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
""", re.VERBOSE)

fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(5)
content = page.extractText()
m = p.search(content)
m.group(5)




# LPG 12 mths T/C, $/day 22,500m³ modern
directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    22,500m³[ ]modern
    (\d{2,3}[,]\d{3})
    (\d{2,3}[,]\d{3})
    (\d{2,3}[,]\d{3})
    (\d{2,3}[,]\d{3})
    (\d{2,3}[,]\d{3})
""", re.VERBOSE)

fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(5)
content = page.extractText()
m = p.search(content)
m.group(5)




# LPG 12 mths T/C, $/day 20,500m³ Semi-Ref^
directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    20,500m³[ ]Semi-Ref\^
    (\d{2,3}[,]\d{3})
    (\d{2,3}[,]\d{3})
    (\d{2,3}[,]\d{3})
    (\d{2,3}[,]\d{3})
    (\d{2,3}[,]\d{3})
""", re.VERBOSE)

fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(5)
content = page.extractText()
m = p.search(content)
m.group(5)




# LPG 12 mths T/C, $/day 15,000m³ Semi-Ref
directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    15,000m³[ ]Semi-Ref
    (\d{2,3}[,]\d{3})
    (\d{2,3}[,]\d{3})
    (\d{2,3}[,]\d{3})
    (\d{2,3}[,]\d{3})
    (\d{2,3}[,]\d{3})
""", re.VERBOSE)

fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(5)
content = page.extractText()
m = p.search(content)
m.group(5)




# LPG 12 mths T/C, $/day 8,250m³ Ethylene
directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    8,250m³[ ]Ethylene
    (\d{2,3}[,]\d{3})
    (\d{2,3}[,]\d{3})
    (\d{2,3}[,]\d{3})
    (\d{2,3}[,]\d{3})
    (\d{2,3}[,]\d{3})
""", re.VERBOSE)

fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(5)
content = page.extractText()
m = p.search(content)
m.group(5)




# LPG 12 mths T/C, $/day 3,500m³ Pressure (East)
directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    3,500m³[ ]Pressure[ ][(]East[)]
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
""", re.VERBOSE)
    
fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(5)
content = page.extractText()
m = p.search(content)
m.group(5)




# LPG 12 mths T/C, $/day 3,500m³ Pressure (East)
directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    138-145,000m³[ ]modern
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
""", re.VERBOSE)
    
fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(5)
content = page.extractText()
m = p.search(content)
m.group(5)from glob import glob
import PyPDF2
import re
# LPG Yoyage Rattets $/mt 44/46K mt Gulf/Jap
directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    LPG[ ]Voyage[ ]Rates,[ ][$]/mt[ ]44/46K[ ]mt[ ]Gulf/Jap[*]
    (\d{1,3}[.]\d{2})
    (\d{1,3}[.]\d{2})
    (\d{1,3}[.]\d{2})
    (\d{1,3}[.]\d{2})
    (\d{1,3}[.]\d{2})
""", re.VERBOSE)

fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(5)
content = page.extractText()
m = p.search(content)
print(m.group(5))



# LPG TCE, $/day 82,000m³ modern
directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    82,000m³[ ]modern
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    \n(\d{1,3}[,]\d{3})
""", re.VERBOSE)

fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(5)
content = page.extractText()
m = p.search(content)
m.group(5)



# LPG TCE, $/day 78,000m³ modern
directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    78,000m³[ ]modern
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    \n(\d{1,3}[,]\d{3})
""", re.VERBOSE)

fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(5)
content = page.extractText()
m = p.search(content)
m.group(5)




# LPG 12 mths T/C, $/day 82,000m³ modern
directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    82,000m³[ ]modern[ ]
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
""", re.VERBOSE)

fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(5)
content = page.extractText()
m = p.search(content)
m.group(5)



# LPG 12 mths T/C, $/day 78,000m³ modern
directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    78,000m³[ ]modern
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
""", re.VERBOSE)

fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(5)
content = page.extractText()
m = p.search(content)
m.group(5)




# LPG 12 mths T/C, $/day 59,000m³ modern
directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    59,000m³[ ]modern
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
""", re.VERBOSE)

fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(5)
content = page.extractText()
m = p.search(content)
m.group(5)



# LPG 12 mths T/C, $/day 35,000m³
directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    35,000m³
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
""", re.VERBOSE)

fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(5)
content = page.extractText()
m = p.search(content)
m.group(5)




# LPG 12 mths T/C, $/day 22,500m³ modern
directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    22,500m³[ ]modern
    (\d{2,3}[,]\d{3})
    (\d{2,3}[,]\d{3})
    (\d{2,3}[,]\d{3})
    (\d{2,3}[,]\d{3})
    (\d{2,3}[,]\d{3})
""", re.VERBOSE)

fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(5)
content = page.extractText()
m = p.search(content)
m.group(5)




# LPG 12 mths T/C, $/day 20,500m³ Semi-Ref^
directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    20,500m³[ ]Semi-Ref\^
    (\d{2,3}[,]\d{3})
    (\d{2,3}[,]\d{3})
    (\d{2,3}[,]\d{3})
    (\d{2,3}[,]\d{3})
    (\d{2,3}[,]\d{3})
""", re.VERBOSE)

fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(5)
content = page.extractText()
m = p.search(content)
m.group(5)




# LPG 12 mths T/C, $/day 15,000m³ Semi-Ref
directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    15,000m³[ ]Semi-Ref
    (\d{2,3}[,]\d{3})
    (\d{2,3}[,]\d{3})
    (\d{2,3}[,]\d{3})
    (\d{2,3}[,]\d{3})
    (\d{2,3}[,]\d{3})
""", re.VERBOSE)

fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(5)
content = page.extractText()
m = p.search(content)
m.group(5)




# LPG 12 mths T/C, $/day 8,250m³ Ethylene
directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    8,250m³[ ]Ethylene
    (\d{2,3}[,]\d{3})
    (\d{2,3}[,]\d{3})
    (\d{2,3}[,]\d{3})
    (\d{2,3}[,]\d{3})
    (\d{2,3}[,]\d{3})
""", re.VERBOSE)

fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(5)
content = page.extractText()
m = p.search(content)
m.group(5)




# LPG 12 mths T/C, $/day 3,500m³ Pressure (East)
directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    3,500m³[ ]Pressure[ ][(]East[)]
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
""", re.VERBOSE)
    
fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(5)
content = page.extractText()
m = p.search(content)
m.group(5)




# LPG 12 mths T/C, $/day 3,500m³ Pressure (East)
directory = 'C:/Users/noble/Downloads/'
files = glob(directory + '*.pdf')
print("Files :",len(files))

p = re.compile(r"""
    138-145,000m³[ ]modern
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
    (\d{1,3}[,]\d{3})
""", re.VERBOSE)
    
fp = files[0]
reader = PyPDF2.PdfFileReader(fp)
page = reader.getPage(5)
content = page.extractText()
m = p.search(content)
m.group(5)
