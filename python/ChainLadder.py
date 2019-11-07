import numpy as np
import pandas as pd



class ChainLadder:
    
    """
        Version
        -------
        1.0.2
        
        Description
        -----------
        incr2cum : increment triangle을 cumulative triangle로 변환
        cum2incr : cumulative triangle을 increment triangle로 변환
        dev_factor : development factor 생성
        develop : developed triangle 생성
        reserve : reserve 산출

        Example
        -------
            >>> triangle = pd.DataFrame(data=np.array([[ 4.,  4.,  4.],
                                                       [ 4.,  2.,  3.],
                                                       [ 2.,  1., np.nan],
                                                       [ 1., np.nan, np.nan]]),
                                        index=pd.Index([2015, 2016, 2017, 2018], name='Accident', dtype=str),
                                        columns=pd.Index(np.arange(3)+1, name='Development', dtype=str))
            >>> cl = ChainLadder(triangle, type='incr')
            >>> cl.triangle_incr
            >>> cl.triangle_cum
            >>> development_factor = cl.dev_factor('weighted average', 1.1020302)
            >>> cl.develop(development_factor)
            >>> cl.reserve(development_factor)
    """

    
    def __init__(self, triangle, type='incr'):
        
        # increment triangle as input
        if type=='incr':
            self.triangle_incr = triangle.copy()
            self.triangle_cum = self.incr2cum(self.triangle_incr)
            
        # cumulative triangle as input
        elif type=='cum':
            self.triangle_cum = triangle.copy()
            self.triangle_incr = self.cum2incr(self.triangle_cum)
            
        # raise error
        else:
            raise Exception('triangle type error')
    
    def incr2cum(self, triangle_incr):
        triangle_cum = np.array(triangle_incr)
        for j in range(triangle_cum.shape[1]-1):
            triangle_cum[:-(j+1), j+1] = triangle_cum[:-(j+1), j] + triangle_cum[:-(j+1), j+1]
        return pd.DataFrame(triangle_cum, index=triangle_incr.index, columns=triangle_incr.columns)

    def cum2incr(self, triangle_cum):
        triangle_incr = np.array(triangle_cum)
        for j in range(triangle_cum.shape[1]-1):
            triangle_incr[:-(j+1), j+1] = triangle_cum.iloc[:-(j+1), j+1] - triangle_cum.iloc[:-(j+1), j]
        return pd.DataFrame(triangle_incr, index=triangle_cum.index, columns=triangle_cum.columns)
    
    def dev_factor(self, method, tail_factor):
        triangle = np.array(self.triangle_cum)
        num_row, num_col = triangle.shape
        
        # weighted average
        if method=='weighted average':
            development_factor = np.zeros(num_col)
            for j in range(num_col-1):
                development_factor[j] = sum(triangle[:(-j-1), j+1])/sum(triangle[:(-j-1), j])
            development_factor[-1] = tail_factor
            
            return development_factor
        
        # other
        if method=='other':
            development_factor = np.zeros(num_col)
            development_factor[0] = (np.product(triangle[(-5):(-1), 1]/triangle[(-5):(-1), 0]))**(1/4)
            development_factor[1] = (np.product(triangle[(-5):(-2), 2]/triangle[(-5):(-2), 1]))**(1/3)
            for j in range(2, num_col-1):
                development_factor[j] = (np.product(triangle[(-5):(-j-1), j+1]/triangle[(-5):(-j-1), j+1]))**(1/3)
            development_factor[-1] = tail_factor
            
            return development_factor
        
        # raise error
        else:
            raise Exception('method error')

    def develop(self, development_factor):
        triangle = np.array(self.triangle_cum)
        num_row, num_col = triangle.shape

        # developed triangle
        triangle_dev_cum = np.c_[triangle, np.zeros(num_row)]
        for j in range(num_col-1):
            triangle_dev_cum[(-j-1):, j+1] = triangle_dev_cum[(-j-1):, j]*development_factor[j]
        triangle_dev_cum[:, -1] = triangle_dev_cum[:, -2] * development_factor[-1]

        return pd.DataFrame(triangle_dev_cum, index=self.triangle_cum.index, columns=self.triangle_cum.columns.insert(self.triangle_cum.columns.size, 'Ult'))

    def reserve(self, development_factor):
        # 최적화 시켜야 함
        return (self.develop(development_factor)['Ult']-self.triangle_incr.fillna(0).sum(axis=1)).to_frame('Reserve')