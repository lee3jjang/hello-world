import numpy as np
from scipy.optimize import minimize_scalar, minimize
from scipy.stats import multivariate_normal

{'Updates' : '2020-01-09', 'Version': '1.0.1'}

def sample():
    dt = 1/12
    maturity = np.array([1,3,5,10,20])
    data = np.array([
        [0.01995,0.02039,0.02158,0.02415,0.02603],
        [0.01981,0.02024,0.02116,0.02346,0.02518],
        [0.01838,0.01865,0.01969,0.02276,0.02466],
        [0.01703,0.01739,0.01857,0.02177,0.02373],
        [0.01746,0.01875,0.0211,0.0249,0.0271],
        [0.0163,0.01773,0.0204,0.02468,0.02679],
        [0.01597,0.01777,0.02048,0.0245,0.02658],
        [0.01582,0.01735,0.01946,0.02308,0.02498],
        [0.01553,0.01651,0.01846,0.02216,0.02388],
        [0.01546,0.01627,0.01784,0.02088,0.02222],
        [0.01631,0.01752,0.01945,0.02254,0.02366],
        [0.01635,0.01719,0.01902,0.02181,0.02278],
        [0.01587,0.01628,0.01772,0.02025,0.02121],
        [0.01469,0.01474,0.01586,0.01826,0.01919],
        [0.01507,0.01498,0.01611,0.01854,0.01918],
        [0.01493,0.01468,0.01569,0.0181,0.01892],
        [0.0148,0.01455,0.01551,0.01787,0.01886],
        [0.01361,0.01334,0.01406,0.01617,0.01712],
        [0.0126,0.01218,0.01246,0.01401,0.01482],
        [0.01265,0.01238,0.01264,0.01417,0.01489],
        [0.01322,0.01312,0.01353,0.01512,0.01545],
        [0.01369,0.01361,0.01412,0.01596,0.01641],
        [0.01511,0.01609,0.01739,0.01965,0.0204],
        [0.01576,0.01692,0.01873,0.02159,0.02186],
        [0.01496,0.01643,0.01821,0.02111,0.02168],
        [0.01465,0.01665,0.01861,0.02163,0.02214],
        [0.01485,0.01709,0.01909,0.02221,0.02302],
        [0.01467,0.01678,0.01859,0.02182,0.02302],
        [0.01464,0.0169,0.01906,0.02257,0.02388],
        [0.01461,0.01673,0.01864,0.02165,0.02266],
        [0.01464,0.0174,0.01942,0.02252,0.02317],
        [0.01471,0.0178,0.01987,0.02287,0.02364],
        [0.01481,0.01785,0.01989,0.02286,0.02318],
        [0.01654,0.02026,0.02247,0.02455,0.02411],
        [0.01787,0.0215,0.02355,0.0254,0.02518],
        [0.01833,0.021,0.02298,0.02472,0.02439],
        [0.01839,0.0219,0.02444,0.02626,0.02571],
        [0.01851,0.02277,0.02537,0.0277,0.02738],
        [0.01875,0.02271,0.02501,0.02708,0.02704],
        [0.01873,0.02192,0.02436,0.02655,0.02671],
        [0.01887,0.0225,0.02532,0.02757,0.02754],
        [0.01851,0.02175,0.02442,0.02656,0.02649],
        [0.01842,0.02097,0.02338,0.02549,0.02547],
        [0.0183,0.02019,0.02242,0.02458,0.02429],
        [0.0179,0.01953,0.02128,0.02315,0.02265],
        [0.01853,0.0201,0.02164,0.02338,0.02284],
        [0.01844,0.0194,0.02048,0.02209,0.02159],
        [0.01776,0.01825,0.01893,0.01992,0.01969],
        [0.01733,0.01807,0.01879,0.01991,0.02023],
        [0.0176,0.01802,0.01869,0.01988,0.0205],
        [0.01769,0.01789,0.01838,0.01953,0.01995],
        [0.01751,0.01736,0.01771,0.01889,0.01913],
        [0.01711,0.01679,0.01716,0.01828,0.01865],
        [0.0156,0.01496,0.0153,0.01618,0.0166],
        [0.01478,0.01382,0.01421,0.01506,0.0152],
        [0.01186,0.01164,0.01201,0.01254,0.01251],
        [0.01228,0.01287,0.01353,0.0142,0.01386],
        [0.01298,0.01357,0.01442,0.01577,0.01568],
        [0.01386,0.01492,0.01593,0.0175,0.01709],
        [0.0135,0.0139,0.01481,0.01653,0.01628]
    ])
    return dt, maturity, data





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
    
    
class DynamicNelsonSiegel:
    
    """
    Example
        -------
            >>> dt, maturity, data = dbesg.sample()
            >>> dns = DynamicNelsonSiegel(dt, maturity)
            >>> dns.train(data)
            >>> time, num = 1, 10
            >>> x, Q, z, R, state, measurement = dns.generate(time, num, random_state=20200109)
    
    """
    
    def __init__(self, dt, maturity):
        self.dt = dt
        self.maturity = maturity
        self.params = None
        self.x0 = None
        self.A = None
        self.B = None
        self.Q = None
        self.H = None
        self.R = None
    
    def train(self, X):
        params_init = self._initial_value(X)
        self.params = minimize(lambda p: -self._filtering(p, X)[2], x0=params_init, method='nelder-mead', options={'disp': False}).x
        self.A, self.B, self.Q, self.H, self.R = self._system(self.params)
        self.x0 = self._filtering(self.params, X)[0]
    
    def _system(self, params):
        lambda_, eps, kappa11, kappa22, kappa33, theta1, theta2, theta3, sigma11, sigma21, sigma22, sigma31, sigma32, sigma33 = params
        A = np.array([[1-kappa11*self.dt, 0, 0],
                      [0, 1-kappa22*self.dt, 0],
                      [0, 0, 1-kappa33*self.dt]])
        B = np.array([kappa11*theta1, kappa22*theta2, kappa33*theta3])*self.dt
        L = np.array([[sigma11, 0, 0],
                      [sigma21, sigma22, 0],
                      [sigma31, sigma32, sigma33]])
        Q = self.dt*L@L.T
        H = np.c_[np.ones_like(self.maturity), (1-np.exp(-lambda_*self.maturity))/(lambda_*self.maturity), (1-np.exp(-lambda_*self.maturity))/(lambda_*self.maturity)-np.exp(-lambda_*self.maturity)]
        R = np.identity(len(self.maturity))*eps**2
        return A, B, Q, H, R
    
    def _initial_value(self, X):
        def obj_fun(lambda_):
            design_matrix = np.c_[np.ones_like(self.maturity), (1-np.exp(-lambda_*self.maturity))/(lambda_*self.maturity), (1-np.exp(-lambda_*self.maturity))/(lambda_*self.maturity)-np.exp(-lambda_*self.maturity)]
            beta = np.linalg.inv(design_matrix.T@design_matrix)@design_matrix.T@X.T
            rmse = np.sqrt(np.mean((X.T-design_matrix@beta)**2))
            return rmse
        res = minimize_scalar(obj_fun, method='bounded', bounds=(1e-2,1), options={'disp':False})
        lambda_ = res.x
        eps = obj_fun(lambda_)
        design_matrix = np.c_[np.ones_like(self.maturity), (1-np.exp(-lambda_*self.maturity))/(lambda_*self.maturity), (1-np.exp(-lambda_*self.maturity))/(lambda_*self.maturity)-np.exp(-lambda_*self.maturity)]
        beta = (np.linalg.inv(design_matrix.T@design_matrix)@design_matrix.T@X.T).T
        
        x, y = beta[:-1], beta[1:]
        beta1 = (np.mean(x**2, axis=0)*np.mean(y, axis=0)-np.mean(x, axis=0)*np.mean(x*y, axis=0))/(np.mean(x**2, axis=0)-np.mean(x, axis=0)**2)
        beta2 = (np.mean(x*y, axis=0)-np.mean(x, axis=0)*np.mean(y, axis=0))/(np.mean(x**2, axis=0)-np.mean(x, axis=0)**2)
        kappa = (1-beta2)/self.dt
        theta = beta1/kappa/self.dt
        e = y-(beta1+x*beta2)
        sigma = np.linalg.cholesky(e.T@e/(len(x)-3))/np.sqrt(self.dt)
        params_init = np.array([lambda_, eps, kappa[0], kappa[1], kappa[2], theta[0], theta[1], theta[2], sigma[0][0], sigma[1][0], sigma[1][1], sigma[2][0], sigma[2][1], sigma[2][2]])
        return params_init
    
    def _filtering(self, params, X):
        A, B, Q, H, R = self._system(params)
        
        x_update = np.array([0., 0.,  0.])
        P_update = np.identity(3)
        
        logL = 0
        for i in range(len(X)):
            # Predict
            x_pred = A@x_update+B
            P_pred = A@P_update@A.T+Q

            # Measurement
            z_meas = X[i]

            # Update
            z_pred = H@x_pred
            v = z_meas-z_pred
            F = H@P_pred@H.T+R
            F_inv = np.linalg.inv(F)
            detF = np.fmax(np.linalg.det(F), 1e-60)
            K = P_pred@H.T@F_inv
            x_update = x_pred+K@v
            P_update = P_pred-K@H@P_pred
            logL += -0.5*np.log(2*np.pi)-0.5*np.log(detF)-0.5*v.T@F_inv@v
            
        return x_update, P_update, logL
    
    def generate(self, time, num, random_state=None):        
        x = self.x0.copy()
        for _ in range(int(time/self.dt)):
            x = self.A@x+self.B
        state = multivariate_normal.rvs(mean=x, cov=time/self.dt*self.Q, size=num, random_state=random_state)
        measurement_mean = state@self.H.T
        measurement = np.r_[[multivariate_normal.rvs(mean=measurement_mean[i], cov=self.R, random_state=random_state) for i in range(num)]]
        return x, time/self.dt*self.Q, x@self.H.T, self.R, state, measurement
