__author__ = 'lee3jjang@naver.com'

import matplotlib.pyplot as plt
import math

PI = 3.141592
xs,ys1,ys2 = [],[],[]

for x in range(360):
    xs.append(x)
    ys1.append(math.sin(PI*x/180))
    ys2.append(math.cos(PI*x/180))
    
plt.plot(xs,ys1,label='sin curve')
plt.plot(xs,ys2,label='cos curve')
plt.xlabel('x-value')
plt.ylabel('y-value')
plt.title('trigonometric function')
plt.legend()
plt.show()

##################################################################

import matplotlib.pyplot as plt

x1 = [1,3,5,7,9]
y1 = [5,3,9,2,8]
x2 = [2,4,6,8,10]
y2 = [8,7,2,11,4]

plt.bar(x1,y1, label='set 1', color='b')
plt.bar(x2,y2, label='set 2', color='#AA2848', width=0.5)
plt.legend()
plt.title('bar chart')
plt.show()

##################################################################

%matplotlib inline
import matplotlib.pyplot as plt

data = [22,55,62,45,21,22,34,42,42,4,99,102,110,120,121,
       122,130,111,115,112,80,75,65,54,44,43,42,48,16,11]

bs = [x*10 for x in range(14)]

plt.hist(data,bins=bs,rwidth=0.5)
plt.show()

##################################################################

import matplotlib.pyplot as plt
from random import shuffle

xs = [x+1 for x in range(100)]
ys = [y+1 for y in range(100)]

shuffle(xs)
shuffle(ys)

plt.scatter(xs,ys,label='scatter', color='b',marker='*',alpha=0.7)
plt.show()

##################################################################

import matplotlib.pyplot as plt
import numpy as np

n = 100
x = np.random.rand(n)
y = np.random.rand(n)
colors = np.random.rand(n)
area = np.pi*(15*np.random.rand(n))**2

plt.scatter(x,y,
            label='samples',
            s=[x for x in range(1,101)],
            c=[x/100 for x in range(1,101)],
            alpha=0.8)
plt.legend()
plt.show()

##################################################################

import matplotlib.pyplot as plt
import numpy as np

n = 100
x = np.random.rand(n)
y = np.random.rand(n)
colors = np.random.rand(n)
area = np.pi*(15*np.random.rand(n))**2

plt.scatter(x,y,
            label='samples',
            s=[x for x in range(1,101)],
            c=[x/100 for x in range(1,101)],
            alpha=0.8)
plt.legend()
plt.show()

##################################################################

import matplotlib.pyplot as plt
import math

pi = 3.14
n = 180
days = list(range(1,n+1))

y1,y2,y3,y4 = [],[],[],[]
cr =['#aa2848','#28aa48','#2848aa','#1155ff']

for x in days:
    y1.append(math.sin(pi*x/180))
    y2.append(math.sin(pi*x/180))
    y3.append(math.sin(pi*x/180))
    y4.append(math.sin(pi*x/180))
    
#plt.plot([],[], color=cr[0], label='1st', linewidth=3)
#plt.plot([],[], color=cr[1], label='2st', linewidth=3)
#plt.plot([],[], color=cr[2], label='3st', linewidth=3)
#plt.plot([],[], color=cr[3], label='4st', linewidth=3)
#plt.legend()

plt.xlabel('angle')
plt.ylabel('value')
plt.title('stacked area')
plt.stackplot(days,y1,y2,y3,y4,colors=cr)
plt.show()

##################################################################

import matplotlib.pyplot as plt
import math

days = list(range(30))
sleeping = [5,7,5,6,8,11,6,6,7,8,5,5,7,7,7,
            8,4,5,6,9,8,5,4,7,5,6,8,5,6,6]
working = [8,9,9,10,11,9,9,12,9,9,10,14,7,9,
           9,9,12,8,8,9,8,9,4,7,7,8,8,11,9,7]
eating = [3,3,2,3,3,2,2,3,2,1,3,3,2,3,2,2,
          3,1,3,4,2,2,2,3,2,2,2,3,3,3]
playing = [8,5,8,5,2,2,7,3,6,6,6,2,8,5,6,5,
           5,10,7,2,6,8,14,7,10,8,6,5,6,8]
cr =['#aa2848','#28aa48','#2848aa','k']

plt.xlabel('day')
plt.ylabel('time')
plt.stackplot(days,sleeping,working,eating,playing,colors=cr)
plt.show()
