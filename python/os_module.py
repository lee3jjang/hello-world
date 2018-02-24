import os

############################### exercise 1 ###############################
print('current path :', os.getcwd())
print('path before conversion :', '.\\Scripts')
print('path after conversion :', os.path.abspath('.\\Scripts'))
print('result :', os.path.isabs(r'C:\Users\Administrator'))
print('relative path :', os.path.relpath(r'C:\inetpub\history',r'C:\inetpub\history\CFGHISTORY_0000000008\schema'))

############################### exercise 2 ###############################
dev = r'C:\Users\Administrator\Desktop\dev\regex.py'
print('\nfile name :', os.path.basename(dev))
print('folder name :', os.path.dirname(dev))
print('folder, file :', os.path.split(dev))
print('seperator :', os.path.sep)
print('\n')

############################### exercise 3 ###############################
dev = r'C:\Users\Administrator\Desktop\dev'
files = os.listdir(dev)

for file in files:
    path = os.path.join(dev,file)
    if os.path.isfile(path):
        print(path)
    else:
        files2 = os.listdir(path)
        for file2 in files2:
            path2 = os.path.join(path,file2)
            if os.path.isfile(path2):
                print(path2)
