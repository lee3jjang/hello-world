from random import random
from math import pi, sin, cos, sqrt, log, exp
import itertools

# arange similar to numpy
def arange(x, y, n=None):
    
    if n==None:
        return list(range(x,y))
    return [x+(y-x)/n*i for i in range(n)]

# Sampling Normal distribution
def normal(n=1, mu=0, sigma=1, method="box-muller"):
    
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
            w = [x*sigma+mu for x in w]
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
            w = [x*sigma+mu for x in w]
            if remainder == 1:
                w = w[:-1]
            if n==1:
                w = w[0]
            
                
    return w

# Rosenbrock Function
# only valid when input is a 2-dim vector, v=(x,y)
def rosen(x, y, order=None):
    
    a = 1
    b = 100
    
    # function value
    if order == None:
        f = (a-x**2)+b*(y-x**2)**2
        return f
    
    # Jacobian
    elif order == "jaco":
        Jx = -2*(a-x) + 2*b*(y-x**2)*(-2*x)
        Jy = 2*b*(y-x**2)
        J = [Jx, Jy]
        return J
    
    # Hessian
    elif order == "hess":
        Hxx = 2 - 4*b*(y-x**2) - 4*b*x*(-2*x)
        Hxy = Hyx = -4*b*x
        Hyy = 2*b
        H = [[Hxx, Hxy], [Hyx, Hyy]]
        return H
    
    else:
        return None
    
# Optimization(Gradient Descent)
# only valid when input is a 2-dim vector, v=(x,y)
def optim(f, x0, method="grad"):
    
    if method=="grad":
        # Setup
        h = 0.0001
        n = 100000
        eps = 1e-5
        
        # Initial value
        x = x0[0]
        y = x0[1]

        # Loop
        for i in range(n):
            # Update
            J = rosen(x, y, "jaco")
            x = x-h*J[0]
            y = y-h*J[1]

            # Tolerance
            tol = sqrt(J[0]**2 + J[1]**2)
            if(tol < eps):
                break
        return [x, y]
    
    else:
        return None
      
# Multivariate normal distribution
# only valid when input is a 2-dim vector, v=(x,y)
def gaussian(p, **kwargs):
    mu = kwargs['mu']
    cov = kwargs['cov']
    
    det = cov[0][0]*cov[1][1] - cov[1][0]*cov[0][1]
    cov_inv = [[cov[1][1]/det, -cov[0][1]/det], [-cov[1][0]/det, cov[0][0]/det]]
    v = [p[0]-mu[0], p[1]-mu[1]]
    dist = v[0]*(v[0]*cov_inv[0][0]+v[1]*cov_inv[1][0]) + v[1]*(v[0]*cov_inv[0][1]+v[1]*cov_inv[1][1])
    z = exp(-dist/2)/(2*pi*sqrt(det))
    return z

# Test Function for Metropolis-Hastings algorithm
def test(rate, n=1):
    k = 3
    theta = 10
    return rate**(k-1)*exp(-rate/theta)*exp(-rate)*rate**n

# Metropolis-Hastings
def metropolis(density, x0, n=1000, burn_in=100, thin=5, **kwargs):
    # Multivarate normal distribution
    if density.__name__ == "gaussian":
        # Setting
        mu = kwargs['mu']
        cov = kwargs['cov']

        # Initial value
        x = x0[0]
        y = x0[1]
        
        # Loop
        trace = []
        for _ in range(n):
            xp = normal(1, mu=x, sigma=3)
            yp = normal(1, mu=y, sigma=3)    
            alpha = min(1,density([xp, yp], mu=mu, cov=cov)/density([x, y], mu=mu, cov=cov))
            u = random()
            if (u < alpha):
                x = xp
                y = yp
            trace.append([x,y])
            
        return trace[burn_in::thin]
    
    # For test
    elif density.__name__ == "test":
        # Setting
        m = kwargs['m']

        # Initial value
        x = x0
        
        # Loop
        trace = []
        for _ in range(n):
            xp = normal(1, mu=x, sigma=1)  
            alpha = min(1, density(xp, n=m)/density(x, n=m))
            u = random()
            if (u < alpha):
                x = xp
            trace.append(x)
            
        return trace[burn_in::thin]
    
    # For test2
    elif density.__name__ == "test2":

        # Initial value
        x = x0[0]
        y = x0[1]
        
        # Loop
        trace = []
        for _ in range(n):
            xp = normal(1, mu=x, sigma=3)
            yp = normal(1, mu=y, sigma=3)
            alpha = density([xp, yp], x=kwargs['x'], y=kwargs['y'])-density([x, y], x=kwargs['x'], y=kwargs['y'])
            u = log(random())
            if (u < alpha):
                x = xp
                y = yp
            trace.append([x,y])
            
        return trace[burn_in::thin]
    
    else:
        return None
    
    