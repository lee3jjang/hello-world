import numpy as np
import pandas as pd



class ChainLadder:
    
    """
        incr2cum : increment triangle을 cumulative triangle로 변환
        cum2incr : cumulative triangle을 increment triangle로 변환
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
            >>> cl.develop('weighted average', 1.1020302)
            >>> cl.reserve()
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
            triangle_cum[:, j+1] = triangle_cum[:, j] + triangle_cum[:, j+1]
        return pd.DataFrame(triangle_cum, index=triangle_incr.index, columns=triangle_incr.columns)

    def cum2incr(self, triangle_cum):
        triangle_incr = np.array(triangle_cum)
        for j in range(triangle_cum.shape[1]-1):
            triangle_incr[:, j+1] = triangle_cum.iloc[:, j+1] - triangle_cum.iloc[:, j]
        return pd.DataFrame(triangle_incr, index=triangle_cum.index, columns=triangle_cum.columns)

    def develop(self, method, tail_factor):
        triangle = np.array(self.triangle_cum)
        num_row, num_col = triangle.shape

        # weighted average
        if method=='weighted average':
            # development factor
            development_factor = np.zeros(num_col)
            for j in range(num_col-1):
                development_factor[j] = sum(triangle[:(-j-1), j+1])/sum(triangle[:(-j-1), j])
            development_factor[-1] = tail_factor

            # developed triangle
            triangle_dev_cum = np.c_[triangle, np.zeros(num_row)]
            for j in range(num_col-1):
                triangle_dev_cum[(-j-1):, j+1] = triangle_dev_cum[(-j-1):, j]*development_factor[j]
            triangle_dev_cum[:, -1] = triangle_dev_cum[:, -2] * development_factor[-1]

            return pd.DataFrame(triangle_dev_cum, index=self.triangle_cum.index, columns=self.triangle_cum.columns.insert(self.triangle_cum.columns.size, 'Ult'))

        # raise error
        else:
            raise Exception('method error')

    def reserve(self, method='weighted average', tail_factor=1):
        # 최적화 시켜야 함
        return (self.develop(method, tail_factor).iloc[:, -1] - self.incr2cum(self.cum2incr(self.triangle_cum).fillna(0)).iloc[:, -1]).to_frame('Reserve')