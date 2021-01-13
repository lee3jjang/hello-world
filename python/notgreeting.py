
from functools import wraps

# decorator
def nothello(func):
    @wraps(func)
    def __wrapper(*args, **kwargs):
        print("="*20)
        func(*args, **kwargs)
        print("="*20)
    return __wrapper

@nothello
def printx(x):
    print(f"Input Value : {x}")


def hello(a):
    def __deco(func):
        @wraps(func)
        def __wrapper(*args, **kwargs):
            print(a*20)
            func(*args, **kwargs)
            print(a*20)
        return __wrapper
    return __deco

@hello(a="*")
def printy(y):
    print(f"Output Value : {y}")

# @property 
class Person:

    def __init__(self):
        self.__name = 'hong'

    @property
    def name(self):
        return self.__name

    @name.setter
    def name(self, name):
        self.__name = name

if __name__ == '__main__':
    # print(printx.__name__)
    # printx(7)

    # person = Person()
    # print(person.name)
    # person.name = 'park'
    # print(person.name)
    
    print(printy.__name__)
    printy(10)