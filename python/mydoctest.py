import doctest

def doubler(num):
    """
    >>> doubler(10)
    30
    """
    return num*2


doctest.testmod()