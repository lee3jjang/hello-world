import glob
import pandas as pd
from datetime import datetime
from dateutil.relativedelta import relativedelta

# xlsx → csv 변환
def convert_to_csv(path, filename, dtype):
    """
        Example
        -------
        >> convert_to_csv('data', '연동수수료_특약보종별_출재보험료') 
    """
    
    files = glob.glob(f'{path}/{filename}*.xlsx')
    result = []
    for f in files:
        data = pd.read_excel(f, dtype=dtype)
        result.append(data)
    tmp = pd.concat(result)
    tmp.to_csv(f'{path}/{filename}.csv', index=False)
    
# 정합성 검사
def validate_uniqueness(df, group, var):
    """
        Description
        -----------
        데이터프레임 내 그룹별 변수가 유일하게 정의되는지를 판별합니다.
        
        Parameters
        ----------
        df : 검사할 데이터프레임
        group : 그룹핑할 변수
        var : 검사할 변수
        
        Example
        -------
        >> validate_uniqueness(contract, '증권번호', '증권개시일')    
    """
    if type(group) == list:
        x = df[group + [var]].drop_duplicates().groupby(group).size()
    else:
        x = df[[group, var]].drop_duplicates().groupby(group).size()
    return x[x>1].index

# 기준년월생성
def generate_yyyymm(start_yyyymm, end_yyyymm, typ=1):
    '''
        Description
        -----------
        기준년월 리스트를 %Y%m 형식으로 생성
        
        Example
        -------
        yyyymm_list = generate_yyyymm((2017, 1), (2019, 12))
        
    '''
    (start_year, start_month), (end_year, end_month) = start_yyyymm, end_yyyymm
    yyyymm_list = []
    year_month = datetime(start_year, start_month, 1)
    while(year_month <= datetime(end_year, end_month, 1)):
        if typ==1:
            yyyymm_list.append(year_month.strftime('%Y%m'))
        elif typ==0:
            yyyymm_list.append(year_month)
        year_month += relativedelta(months=1)
    return yyyymm_list