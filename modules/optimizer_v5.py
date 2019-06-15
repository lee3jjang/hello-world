#!/usr/bin/env python
# coding: utf-8

# In[37]:


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

def nelder_mead(fn, x0, eps=1e-5):
    """
    example:
    >>> test_func = lambda x: np.sum(100*(x[1:] - x[:-1]**2)**2 + (1-x[:-1])**2)
    >>> x = nelder_mead(test_func, np.array([1, 3, 2]))
    >>> x
    array([1., 1., 1.])
    """
    
    n = x0.shape[0]
    x = np.random.multivariate_normal(x0, np.identity(n), size=n+1)
    
    alpha = 1.0
    gamma = 2.0
    rho = 0.5
    sigma = 0.5
    
    while(np.array([fn(t) for t in x]).std() > eps):

        #1. Order
        x = np.array(sorted(list(x), key=lambda t: fn(t)))
        #2. Centroid
        x_o = x[:-1].mean(axis=0)
        #3. Reflection
        x_r = x_o + alpha*(x_o - x[-1])
        if(fn(x_r) >= fn(x[0]) and fn(x_r) < fn(x[-2])):
            x[-1] = x_r
        elif (fn(x_r) < fn(x[0])):
            #4. Expansion
            x_e = x_o + gamma*(x_r - x_o)
            if(fn(x_e) < fn(x_r)):
                x[-1] = x_e
            else:
                x[-1] = x_r
        else:
            #5. Contraction
            x_c = x_o + rho*(x[-1] - x_o)
            if(fn(x_c) < fn(x[-1])):
                x[-1] = x_c
            else:
                #6. Shrink
                x[1:] = x[0] + sigma*(x[1:] - x[0])
                
    return x[0]

def simulated_annealing(fn, x0, eps=1e-4):
    """
    example:
    >>> test_func = lambda x: np.sum(100*(x[1:] - x[:-1]**2)**2 + (1-x[:-1])**2)
    >>> x = simulated_annealing(test_func, np.array([1, 3, 2]))
    >>> x
    array([0.99835622, 0.99659037, 0.99304054])
    """
    
    x_best = x = x0
    T = 10
    while T > eps:
        nhb = np.random.multivariate_normal(x, np.identity(x.shape[0]))
        x = nhb if np.random.random() < np.fmin(np.exp(-(fn(nhb) - fn(x))/T), 1) else x
        if(fn(x) < fn(x_best)):
            x_best = x
        T = T - 1e-4
    
    return x_best

def gauss_newton(fn, grad_fn, p0, inputs, outputs):
    """
    example:
    >>> test_func = lambda x, p: p[0]*np.sin(x)+p[1]*np.cos(p[2]*x)+p[3]*(x-3)**3
    >>> test_grad = lambda x, p: np.array([np.sin(x), np.cos(p[2]*x), -x*p[1]*np.sin(p[2]*x), (x-3)**3])
    >>> inputs = 10*np.random.random(1000)
    >>> outputs = test_func(inputs, np.array([2, 3, 1.3, 0.01]))+np.random.normal(0, 0.5, size=1000)
    >>> p = gauss_newton(test_func, test_grad, np.array([1, 4, 1.2, 1]), inputs, outputs)
    >>> p
    array([2.07141149, 3.03695764, 1.29758023, 0.0097883 ])
    """
        
    jacobian = lambda p: -grad_fn(inputs, p).T
    residual = lambda p: outputs - fn(inputs, p)
    p = p0.copy()
    while(1):
        J = jacobian(p)
        r = residual(p)
        delta_p = - np.linalg.inv(J.T@J)@J.T@r
        p = p + delta_p
        if(np.sum(delta_p**2) < 1e-15):
            break
    return p

def levenberg_marquardt(fn, grad_fn, p0, inputs, outputs, mu0=0.01):
    """
    example:
    >>> test_func = lambda x, p: p[0]*np.sin(x)+p[1]*np.cos(p[2]*x)+p[3]*(x-3)**3
    >>> test_grad = lambda x, p: np.array([np.sin(x), np.cos(p[2]*x), -x*p[1]*np.sin(p[2]*x), (x-3)**3])
    >>> inputs = 10*np.random.random(1000)
    >>> outputs = test_func(inputs, np.array([2, 3, 1.3, 0.01]))+np.random.normal(0, 0.5, size=1000)
    >>> p = levenberg_marquardt(test_func, test_grad, np.array([2, 10, 1.4, 1]), inputs, outputs)
    >>> p
    array([1.98848513, 2.96908844, 1.30001155, 0.01027959])
    """
        
    jacobian = lambda p: -grad_fn(inputs, p).T
    residual = lambda p: outputs - fn(inputs, p)
    p = p0.copy()
    r = residual(p0)
    E_hat = r@r
    mu = mu0
    while(1):
        J = jacobian(p)
        r = residual(p)
        E = E_hat
        E_hat = r@r
        mu = mu + mu0 if E_hat > E else mu0
        delta_p = - np.linalg.inv(J.T@J+mu*np.diag(np.diag(J.T@J)))@J.T@r
        p = p + delta_p
        if(np.sum(delta_p**2) < 1e-15):
            break
    return p

