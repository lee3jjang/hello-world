import numpy as np
from scipy.optimize import minimize_scalar

{'Updates' : '2020-01-09'}

class SmithWilson:
    
    """
        Example
        -------
            >>> data = np.array([
                    [1, 0.01301],
                    [3, 0.01325],
                    [5, 0.01415],
                    [10, 0.01600],
                    [20, 0.01625],
                    [30, 0.01604]
                ])

            >>> X_train = data[:, 0]
            >>> y_train = data[:, 1]
            >>> ufr = 0.045
            >>> terminal = 60

            >>> sw = SmithWilson(np.log(1+ufr), terminal)
            >>> sw.train(X_train, y_train)

            >>> maturity = np.linspace(1/12, 100, 1200)
            >>> spot_rate = sw.spot(maturity)
            >>> bond_price = sw.bond(maturity)
            >>> forward_rate = sw.forward(maturity)
    """
    
    def __init__(self, ufr, terminal):
        self.ufr = ufr
        self.terminal = terminal
    
    def train(self, X, y):
        m = 1/(1+y)**X
        mu = np.exp(-self.ufr*X)
        n = len(X)
        
        def obj_fun(alpha):
            W = self._wilson(X[:, None], X, alpha)
            zeta = (m-mu)@np.linalg.inv(W)
            W_T = self._wilson(self.terminal, X, alpha)
            derivW_T = self._wilson(self.terminal, X, alpha, order=1)
            bond0_T = np.exp(-self.ufr*self.terminal) + W_T@zeta
            bond1_T = -self.ufr*np.exp(-self.ufr*self.terminal)+derivW_T@zeta
            forward_T = -bond1_T/bond0_T
            error = abs(1e-4-abs(forward_T-self.ufr))
            return error
        
        res = minimize_scalar(obj_fun, method='bounded', bounds=(1e-4,1), options={'disp':False})
        self._alpha = res.x
        W = self._wilson(X[:, None], X, self._alpha)
        self._zeta = (m-mu)@np.linalg.inv(W)
        self._u = X.copy()
        
    def bond(self, t, order=0):
        bond = (-self.ufr)**order*np.exp(-self.ufr*t)+self._wilson(t[:, None], self._u, self._alpha, order)@self._zeta
        return bond
        
    def spot(self, t):
        t = np.fmax(t, 1e-6)
        P = np.exp(-self.ufr*t)+self._wilson(t[:, None], self._u, self._alpha)@self._zeta
        return (1/P)**(1/t) - 1
    
    def forward(self, t, order=0):
        if order==0:
            forward = -self.bond(t, 1)/self.bond(t, 0)
        elif order==1:
            forward = 1/self.bond(t, 0)*(-self.bond(t, 1)**2/self.bond(t, 0)+self.bond(t, 2))
        else:
            print('유효한 Order가 아닙니다.')
            return None
        return np.exp(forward)-1
    
    def _wilson(self, t, u, alpha, order=0):
        if order == 0:
            W = np.exp(-self.ufr*(t+u))*(alpha*np.fmin(t,u) - np.exp(-alpha*np.fmax(t,u))*np.sinh(alpha*np.fmin(t,u)))
        elif order == 1:
            W = np.where(t < u, np.exp(-self.ufr*t-(alpha+self.ufr)*u)*(self.ufr*np.sinh(alpha*t)-alpha*np.cosh(alpha*t)-alpha*(self.ufr*t-1)*np.exp(alpha*u)), \
                    np.exp(-self.ufr*u-(alpha+self.ufr)*t)*((alpha+self.ufr)*np.sinh(alpha*u)-alpha*self.ufr*u*np.exp(alpha*t)))
        elif order == 2:
            W = np.where(t < u, np.exp(-self.ufr*t-(alpha+self.ufr)*u)*(-(alpha**2+self.ufr**2)*np.sinh(alpha*t)+2*alpha*self.ufr*np.cosh(alpha*t)+alpha*self.ufr*(self.ufr*t-2)*np.exp(alpha*u)), \
                    np.exp(-self.ufr*u-(alpha+self.ufr)*t)*(alpha*self.ufr**2*u*np.exp(alpha*t)-(alpha+self.ufr)**2*np.sinh(alpha*u)))
        else:
            print('유효한 Order가 아닙니다.')
            return None
        return W
    

class NelsonSiegel:
    
    """
        Example
        -------
            >>> data = np.array([
                    [1, 0.01301],
                    [3, 0.01325],
                    [5, 0.01415],
                    [10, 0.01600],
                    [20, 0.01625],
                    [30, 0.01604]
                ])

            >>> X_train = data[:, 0]
            >>> y_train = data[:, 1]

            >>> ns = NelsonSiegel()
            >>> ns.train(X_train, y_train)

            >>> maturity = np.linspace(1/12, 100, 1200)
            >>> spot_rate = ns.spot(maturity)
            >>> bond_price = ns.bond(maturity)
            >>> forward_rate = ns.forward(maturity)
    """
    
    def train(self, X, y):
        def obj_fun(lambda_):
            design_matrix = np.c_[np.ones_like(X), (1-np.exp(-lambda_*X))/(lambda_*X), (1-np.exp(-lambda_*X))/(lambda_*X)-np.exp(-lambda_*X)]
            beta = np.linalg.inv(design_matrix.T@design_matrix)@design_matrix.T@y
            error = np.sum((y-design_matrix@beta)**2)
            return error
        res = minimize_scalar(obj_fun, method='bounded', bounds=(1e-2,1), options={'disp':False})
        self.lambda_ = res.x
        design_matrix = np.c_[np.ones_like(X), (1-np.exp(-self.lambda_*X))/(self.lambda_*X), (1-np.exp(-self.lambda_*X))/(self.lambda_*X)-np.exp(-self.lambda_*X)]
        self.beta = np.linalg.inv(design_matrix.T@design_matrix)@design_matrix.T@y
    
    def spot(self, t):
        t = np.fmax(t, 1e-6)
        design_matrix = np.c_[np.ones_like(t), (1-np.exp(-self.lambda_*t))/(self.lambda_*t), (1-np.exp(-self.lambda_*t))/(self.lambda_*t)-np.exp(-self.lambda_*t)]
        return design_matrix@self.beta
    
    def bond(self, t):
        return (1+self.spot(t))**(-t)
    
    def forward(self, t):
        t = np.fmax(t, 1e-6)
        design_matrix = np.c_[np.ones_like(t), np.exp(-self.lambda_*t), self.lambda_*t*np.exp(-self.lambda_*t)]
        return design_matrix@self.beta