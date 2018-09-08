from random import random
from math import pi, sin, cos, sqrt, log, exp
import itertools

# arange similar to numpy
def arange(x, y, n=None):
    
    if n==None:
        return list(range(x,y))
    return [x+(y-x)/n*i for i in range(n)]

# Sampling Normal distribution
def normal(n=1, method="box-muller"):
    
    # Box-Muller transform
    if method == "box-muller":
        remainder = n%2
        quotient = n//2
        m = quotient + remainder
        z = []
        for _ in range(m):
            u1 = random()
            u2 = random()
            theta = 2*pi*u2
            R = sqrt(-2*log(u1))
            z0 = R*cos(theta)
            z1 = R*sin(theta)
            z.append([z0, z1])
            w = list(itertools.chain([x[0] for x in z], [x[1] for x in z]))
            if remainder == 1:
                w = w[:-1]
            if n==1:
                w = w[0]
                
    # Marsaglia polar method
    elif method == "polar":
        remainder = n%2
        quotient = n//2
        m = quotient + remainder
        z = []
        for _ in range(m):
            while(True):
                u = 2*random()-1
                v = 2*random()-1
                s = u**2 + v**2
                if s < 1 and s > 0:
                    z0 = u*sqrt(-2*log(s)/s)
                    z1 = v*sqrt(-2*log(s)/s)
                    break
            z.append([z0, z1])
            w = list(itertools.chain([x[0] for x in z], [x[1] for x in z]))
            if remainder == 1:
                w = w[:-1]
            if n==1:
                w = w[0]
                
    return w