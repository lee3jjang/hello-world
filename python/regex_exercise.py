import re

with open('C:/Users/Administrator/Desktop/dev/number.txt','r') as f:
    phone = f.read()

##################### case 1 ##################### 
pattern = r'(\d\d\d)-(\d\d\d[\d]?)-(\d\d\d\d)'
regex = re.compile(pattern)
mo = regex.search(phone)

print("1 group: ",mo.group(1))
print("2 group: ",mo.group(2))
print("3 group: ",mo.group(3))
print("0 group: ",mo.group(0))
print("all: ",mo.group())
print("groups: ",mo.groups())

##################### case 2 #####################
pattern = r'sangjin|jiwon'
regex = re.compile(pattern)
txt1 = 'sangjin is 29'
txt2 = 'and jiwon is 24' 
mo1 = regex.search(txt1)
mo2 = regex.search(txt2)


print('\ntext1 : ', mo1.group())
print('text2 : ', mo2.group())

##################### case 1-2 ##################### 
pattern = r'(\d{3})-(\d{3,4})-(\d{4})'
regex = re.compile(pattern)
mo = regex.search(phone)

print("\n1 group: ",mo.group(1))
print("2 group: ",mo.group(2))
print("3 group: ",mo.group(3))
print("0 group: ",mo.group(0))
print("all: ",mo.group())
print("groups: ",mo.groups())

##################### case 1-3 ##################### 
pattern = r'\d{3}-\d{3,4}-\d{4}'
regex = re.compile(pattern)
mo = regex.findall(phone)
print('\n',mo)

##################### case 3 ##################### 
pattern = r'(\d{3})-(\d{3,4})-(\d{4})'
regex = re.compile(pattern)
regex.sub(r'\1-xxxx-yyyy',phone)
print('\n',mo)
