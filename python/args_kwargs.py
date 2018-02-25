def f1(*args):
    print(args)
    for p in args:
        print(p)
    print('-------- f1() --------')

f1(3,4,5,'a','b','c')
#################################################################
def f2(**kwargs):
    print(kwargs)
    print(kwargs.keys())
    print(kwargs.values())
    for key, value in kwargs.items():
        print('key is %s, and value is %s' % (key, value))
    print('-------- f2() --------')    
f2(first='a',second='b',thrid=3)
#################################################################
def f3(*x,**y):
    print(x)
    print(y)
    print('-------- f3() --------')  
    
f3(1,2,3)
f3(a=1,b=2,c=3)
f3(1,b=-6,a=-4,c=-3)
#################################################################
def f4(x,*args,**kwargs):
    print(x)
    print(args)
    print(kwargs)
    print('-------- f4() --------')  
    
f4(999,2,3)
f4(999,a=1,b=2,c=3)
f4(999,1,2,b=-6,a=-4,c=-3)
f4(999)

    
