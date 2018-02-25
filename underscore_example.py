x, _, y = (4,3,2)
print(x)
print(y)

for _ in range(10):
    print("hello")
    
# interpreter에서는 _가 최신 결과값을 의미함
# __ : method 앞에 double underscore를 붙이면 _classname 이 붙여짐(맹글링?)

class A:
    def _method1():
        print("A's method1!")
    def __method2():
        print("A's method2!")

class B(A):
    def _method1():
        print("B's method1!")
    def __method2():
        print("B's method2!")
        
print(dir(A))
print(dir(B))
print('------------------------------------------------')
B._method1()
B._B__method2()
B._A__method2()
A._A__method2()
