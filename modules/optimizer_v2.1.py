#!/usr/bin/env python
# coding: utf-8

# In[ ]:





# In[38]:


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

def gradient_descent(grad_fn, x0, learning_rate=0.01, gamma=0.0, eps=1e-5):
    """
    example:
    >>> test_grad = lambda x: 2*np.cos(x)-3.9*np.sin(1.3*x)+0.03*(x-3)**2
    >>> x = gradient_descent(test_grad, np.pi)
    >>> x
    2.805273557590184
    """
    
    v = np.zeros_like(x0)
    x_current = x0
    x_next = x_current + learning_rate * grad_fn(x_current)
    while(np.linalg.norm(x_next-x_current) > eps):
        x_current = x_next
        v = gamma*v + learning_rate * grad_fn(x_current)
        x_next = x_current - v
        
    return x_next


# In[ ]:




