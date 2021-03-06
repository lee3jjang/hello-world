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
    
# Line Sesarch Method
def LineSearch(f, a, b, method="bisection"):

    # Bisection
    if method=="bisection":
        # setup
        eps = 1e-5
        L = 1e-5
        n = 10000

        for _ in range(n):
            x = (a+b)/2-eps
            y = (a+b)/2+eps

            if f(x) < f(y):
                b = y
            else:
                a = x

            if(abs(b-a) < L):
                break

        return (a+b)/2
    
    # Golden section
    elif method=="golden-section":

        # setup
        C = (sqrt(5)-1)/2
        L = 1e-5
        n = 10000

        x = b-C*(b-a)
        y = a+C*(b-a)

        for _ in range(n):
            if f(x) < f(y):
                b = y
                y = x
                x = b-C*(b-a)
            else:
                a = x
                x = y
                y = a+C*(b-a)
            if(abs(b-a) < L):
                break

        return (a+b)/2
    
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
    
########### Linear Algebra ###########

num_rows = lambda A:len(A)
num_cols = lambda A:len(A[0])

def print_mat(A):
    n = num_rows(A)
    
    for i in range(n):
        print(A[i])
    print()

def zeros(n, m):
    A = []  
    
    for i in range(n):
        row = []
        for j in range(m):
            row.append(0)
        A.append(row)       
    return A

def identity(n):
    A = zeros(n,n)
    
    for i in range(n):
        A[i][i] = 1
    return A

def add(A, B):
    n = num_rows(A)
    m = num_cols(A)
    C = zeros(n,m)
    
    for i in range(n):
        for j in range(m):
            C[i][j] = A[i][j] + B[i][j]    
    return C

def scalar(c, A):
    n = num_rows(A)
    m = num_cols(A)
    B = zeros(n,m)
    
    for i in range(n):
        for j in range(m):
            B[i][j] = c*A[i][j]     
    return B

def minus(A):
    return scalar(-1,A)

def subtract(A, B):
    return add(A, minus(B))

def matmul(A, B):
    n = num_rows(A)
    m = num_cols(A)
    l = num_cols(B)
    C = zeros(n,l)
    
    for i in range(n):
        for j in range(l):
            for k in range(m):
                C[i][j] += A[i][k]*B[k][j]
    return C

def submatrix(A, r, s):
    n = num_rows(A)
    m = num_cols(A)
    B = zeros(n-1,m-1)
    
    for i in range(r):
        for j in range(s):
            B[i][j] = A[i][j]
            
    for i in range(r):
        for j in range(s, m-1):
            B[i][j] = A[i][j+1]
            
    for i in range(r, n-1):
        for j in range(s):
            B[i][j] = A[i+1][j]
    
    for i in range(r, n-1):
        for j in range(s, m-1):
            B[i][j] = A[i+1][j+1]   
    return B

def det(A):
    n = num_rows(A)
    res = 0
    
    if n == 1:
        res = A[0][0]
    elif n == 2:
        res = A[0][0]*A[1][1] - A[0][1]*A[1][0]
    else:
        for i in range(n):
            res += det(submatrix(A,0,i))*A[0][i]*(-1)**i
    return res

def t(A):
    n = num_rows(A)
    m = num_cols(A)
    
    B = zeros(m,n)
    for i in range(m):
        for j in range(n):
            B[i][j] = A[j][i]
    return B

def symmetric(A):
    B = scalar(0.5,add(A, t(A)))
    return B

def skew_symmetric(A):
    B = scalar(0.5,add(A, minus(t(A))))
    return B

def chol(A):
    n = num_rows(A)
    R = zeros(n, n)
    R[0][0] = sqrt(A[0][0])

    if n==1:
        return [[R[0][0]]]
    else:
        for i in range(1,n):
            R[0][i] = A[0][i]/R[0][0]

        subR = chol(subtract(submatrix(A,0,0),outer(R[0][1:],R[0][1:])))
        for i in range(n-1):
            for j in range(n-1):
                R[i+1][j+1] = subR[i][j]
        return R

def zeros_vec(n):
    v = []
    for i in range(n):
        v.append(0)
    return v    

def dot(u, v):
    n = len(v)
    res = 0
    for i in range(n):
        res += u[i]*v[i]
    return res

def add_vec(u, v):
    n = len(u)
    w = zeros_vec(n)
    for i in range(n):
        w[i] = u[i] + v[i]
    return w

def outer(u, v):
    n = len(u)
    m = len(v)
    A = zeros(n,m)
    for i in range(n):
        for j in range(m):
            A[i][j] = u[i]*v[j]
    return A

# Only valid when dim A = 2
def inv(A):
    n = num_rows(A)
    B = zeros(n,n)
    d = det(A)
    B[0][0] = A[1][1]/d
    B[1][0] = -A[1][0]/d
    B[0][1] = -A[0][1]/d
    B[1][1] = A[0][0]/d
    return B
    
    