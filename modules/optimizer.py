#!/usr/bin/env python
# coding: utf-8

# In[1]:


import numpy as np

def golden_section_search(fn, x1, x2, eps=1e-5):
    """
    example:
    >>> test_func = lambda x: 2*np.sin(x)+3*np.cos(1.3*x)+0.01*(x-3)**3
    >>> x = golden_section_search(test_func, 0, 2*np.pi)
    >>> x
    2.8050312239035757    
        
    """
    
    phi = (1+np.sqrt(5))/2
    x3 = x2 - (x2-x1)/phi
    x4 = x1 + (x2-x1)/phi
    while abs(x3-x4) > eps:
        if fn(x3) < fn(x4):
            x2 = x4
            x4 = x3
            x3 = x2 - (x2-x1)/phi
        else:
            x1 = x3
            x3 = x4
            x4 = x1 + (x2-x1)/phi
        x_trace.append([x1, x2])
    
    return (x1+x2)/2

