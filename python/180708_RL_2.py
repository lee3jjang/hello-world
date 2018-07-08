import readchar
import msvcrt

class Hello():
    def __init__(self, p):
        self.p = p
    def __call__(self, f):
        def g():
            print("Come on", f.__name__)
            print("p =", self.p)
            f()
            print("Bye Bye~", f.__name__)
        
        return g

@Hello('foo bar')
def Exit():
    print('Hello')

@Hello('show')
def noExit():
    print("C++ plus!")


class Point:
    def __init__(self, x, y):
        self.x = x
        self.y = y
    

if __name__ == '__main__':
    Exit()
    noExit()
    p = Point(4,2)
    print(p.x, p.y)
    print(getattr(p, 'w','no w'))
    print(globals())
    q = Point(3,2)
    print(hash(p))
    print(hash(q))
    print(any([0,0,0]))


    
