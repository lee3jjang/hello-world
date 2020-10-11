def deco(func):

    def wrapper(*args, **kwargs):
        print("전처리")
        print(func(*args, **kwargs))
        print("후처리")

    return wrapper


@deco
def example():
    return '함수'


class Deco:
    def __init__(self, name):
        self.name = name
    
    def __call__(self, func):
        def wrappee(*args, **kwargs):
            print(self.name)
            print(func(*args, **kwargs))
            print("후처리")
        return wrappee

@Deco(name='지오니')
def example(x, y):
    return x, y

example(4, 2)

        