#!/usr/bin/env python
# coding: utf-8

# In[30]:


import numpy as np

def inverse_quadratic_interpolation(fn, x0, x1, x2, eps=1e-10):
    """
    example:
    >>> test_func = lambda x: 2*np.sin(x)+3*np.cos(1.3*x)+0.01*(x-3)**3
    >>> x = inverse_quadratic_interpolation(test_func, 4, 2, 3)
    >>> x
    7.52149764604774
    """
    
    y0 = fn(x0)
    y1 = fn(x1)
    y2 = fn(x2)
    
    while(abs(y2) > eps):
        x_next = y1*y2/((y0-y1)*(y0-y2))*x0 + y0*y2/((y1-y0)*(y1-y2))*x1 + y0*y1/((y2-y0)*(y2-y1))*x2
        x0 = x1
        x1 = x2
        x2 = x_next
        y0 = y1
        y1 = y2
        y2 = fn(x_next)
        
    return x2

