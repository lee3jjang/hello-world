def helloBye(f):
    def g(x,y):
        print("hello")
        f(x,y)
        print("bye")
    return g
        
@helloBye
def f1(x,y):
    print("f1 함수를 수행했습니다.",x+y)
  
def f2():
    print("f2 함수를 수행했습니다.")
    
if __name__ == '__main__':
    f1(4,3)
    f2()
    
