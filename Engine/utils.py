import os
import re
import glob
import pandas as pd
from datetime import datetime
from dateutil.relativedelta import relativedelta

# 수정일자 : 2020.11.26

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

def __load_rpt(path, code):
    '''
        Description
        -----------
        Raw Data → 정제된 업무보고서로 변환
        
        Input
        -----
        path : 파일경로
        code : 사업실적표 코드(AI004, AI009, ...) (대문자로 입력 해야 함)
        
        Output
        ------
        dataframe : 정제된 업무보고서
        
        Example
        -------
        from fss_rpt import load_rpt
        ai059 = __load_rpt('data/업무보고서_201701.xlsx', 'AI059')
    '''
    locations = {
        'AI004': (11, 2),
        'AI009': (11, 2),
        'AI057': (11, 2),
        'AI059': (13, 3),
        'AI060': (13, 2),
        'AI062': (11, 2),
        'AI135': (12, 2),
        'AI163': (12, 3),
    }
    if code not in locations.keys():
        raise Exception('코드 입력 에러')
    else:
        x, y = locations.get(code)
    df = pd.read_excel(path, sheet_name=code)
    index = df.iloc[x:, 0].str.replace('\r\n', '').values
    columns = df.iloc[9, y:].values
    rpt = pd.DataFrame(df.iloc[x:, y:].values, index=index, columns=columns).fillna(0).astype(float)
    return rpt

def load_rpt_all(path, code):
    '''
        Description   
        -----------
        경로 안에 있는 사업실적표 전부 로드하여 dict 형태로 반환
        ※ 파일명 "업무보고서_yyyymm.xlsx" 형태로 되어 있어야 함
        
        Input
        -----
        path : 파일들이 들어 있는 경로
        code : 업무보고서 코드 (AI059, AI004 등)
        
        Output
        ------
        dict (key: 기준년월, value: 보고서 데이터프레임)
        
        Example
        -------
        rpt = load_rpt_all('data', 'AI059')
    '''
    
    # 기준년월 생성 및 데이터 목록 유효성 검증
    m = re.compile('^업무보고서_\d{6}.xlsx$')
    data = [f for f in os.listdir(path) if m.search(f)]
    yyyymm_list_data = list(map(lambda x: x[6:12], data))
    yyyymm_list_data.sort()
    start_yymm, end_yymm = yyyymm_list_data[0], yyyymm_list_data[-1]
    start_year, start_month = int(start_yymm[:4]), int(start_yymm[-2:])
    end_year, end_month = int(end_yymm[:4]), int(end_yymm[-2:])
    yyyymm_list = generate_yyyymm((start_year, start_month), (end_year, end_month))
    if yyyymm_list_data != yyyymm_list:
        raise('생성된 기준년월 리스트와 데이터 목록상 기준년월 불일치')

    # 데이터 로드
    rpt = {}
    for yyyymm in yyyymm_list:
        rpt[yyyymm] = __load_rpt(os.path.join(path, '업무보고서_{}.xlsx'.format(yyyymm)), code)
        
    return rpt

def rpt_value_inc_ts(rpt, row, column):
    '''
        Description
        -----------
        dict 형태의 업무보고서 중 특정 변수의 월별 증감액을 출력
        ※ 기준월 1월로 시작해야 함
        
        Input
        -----
        rpt : load_rpt_all의 output
        row : 행 Key
        column : 열 Key
        
        Output
        ------
        series (key: 기준년월, value: 특정변수)
        
        Example
        -------
        # 손익계산서 불러오기
        rpt = load_rpt_all('data', 'AI009')

        # 데이터 불러오기
        earned_prem_ts = rpt_value_inc_ts(rpt, 'A', 'H') # 경과보험료(총괄)
        loss_ts = rpt_value_inc_ts(rpt, 'B', 'H') # 발생손해액(총괄)
        net_expense_ts = rpt_value_inc_ts(rpt, 'D', 'H') # 순사업비(총괄)
        ins_profit_ts = rpt_value_inc_ts(rpt, 'H', 'H') # 보험영업이익(총괄)
        inv_profit_ts = rpt_value_inc_ts(rpt, 'K', 'H') # 투자영업이익(총괄)
        net_income_ts = rpt_value_inc_ts(rpt, 'U', 'H') # 당기순이익(총괄)
        tot_income_ts = rpt_value_inc_ts(rpt, 'X', 'H') # 총포괄손익(총괄)
    '''
    if list(rpt.keys())[0][-2:] != '01':
        raise Exception('기준월 1월로 시작해야 함')
          
    # 누적
    values = {}
    for yyyymm in rpt.keys():
        values[yyyymm] = rpt[yyyymm].loc[row, column]
    values_ts = pd.Series(values)
    values_ts.index = pd.to_datetime(values_ts.keys(), format='%Y%m')

    # 월별
    values_inc_ts = values_ts.copy()
    for i in range(len(values_ts)):
        values_inc_ts[i] = values_ts[i] if values_ts.index[i].month==1 else values_ts[i] - values_ts[i-1]

    return values_inc_ts

def rpt_value_ts(rpt, row, column):
    '''
        Description
        -----------
        dict 형태의 업무보고서 중 특정 변수의 시점값을 출력
        
        Input
        -----
        rpt : load_rpt_all의 output
        row : 행 Key
        column : 열 Key
        
        Output
        ------
        series (key: 기준년월, value: 특정변수)
        
        Example
        -------
        # 손익계산서 불러오기
        rpt = load_rpt_all('data', 'AI004')

        # 데이터 불러오기
        op_asset_ts = rpt_value_ts(rpt, 'A1', 'H') # 운용자산(총괄)
    '''
          
    values = {}
    for yyyymm in rpt.keys():
        values[yyyymm] = rpt[yyyymm].loc[row, column]
    values_ts = pd.Series(values)
    values_ts.index = pd.to_datetime(values_ts.keys(), format='%Y%m')

    return values_ts

def __load_tb(path):
    '''
        Description
        -----------
        Raw Data → 정제된 시산표로 변환
        
        Input
        -----
        path : 파일경로
        
        Output
        ------
        dataframe : 정제된 업무보고서
        
        Example
        -------
        from fss_rpt import load_tb
        ai059 = __load_tb('data/시산표_20170131.xlsx')
    '''

    시산표 = pd.read_excel(path)
    시산표2 = 시산표.copy()
    시산표2 = 시산표2[~시산표2['Unnamed: 3'].isnull()]
    시산표2 = 시산표2.loc[:,~시산표2.iloc[0].isnull()]
    시산표2.columns = 시산표2.iloc[0].str.strip()
    시산표2.columns.name = None
    시산표2 = 시산표2.iloc[1:]
    시산표2 = 시산표2.query('계정과목 != " 계정과목"')
    시산표2 = 시산표2.fillna(0).reset_index(drop=True)
    시산표2 = 시산표2[['계정과목','계정과목명', '차    변', '대    변']]
    시산표2.columns = ['계정과목','계정과목명','차변','대변']
    시산표2.eval('잔액 = 차변 - 대변', inplace=True)
    시산표2[['차변','대변','잔액']] = 시산표2[['차변','대변','잔액']].astype(float)
    시산표2['계정과목'] = 시산표2['계정과목'].astype(str)
    시산표2 = 시산표2.query('계정과목 != "                      합             계"')
    return 시산표2

# 기준년월말일자생성
def generate_enddate(start_yyyymm, end_yyyymm, typ=1):
    '''
        Description
        -----------
        기준년월말일자 리스트를 %Y%m%d 형식으로 생성
        
        Example
        -------
        enddate_list = generate_enddate((2017, 1), (2019, 12))
        
    '''
    (start_year, start_month), (end_year, end_month) = start_yyyymm, end_yyyymm
    enddate_list = []
    year_month = datetime(start_year, start_month, 1)
    while(year_month <= datetime(end_year, end_month, 1)):
        enddate = year_month + relativedelta(months=1, days=-1)
        if typ==1:
            enddate_list.append(enddate.strftime('%Y%m%d'))
        elif typ==0:
            enddate_list.append(enddate)
        year_month += relativedelta(months=1)
    return enddate_list

def load_tb_all(path):
    '''
        Description   
        -----------
        경로 안에 있는 사업실적표 전부 로드하여 dict 형태로 반환
        ※ 파일명 "시산표_yyyymmdd.xlsx" 형태로 되어 있어야 함 (매월 말일자)
        
        Input
        -----
        path : 파일들이 들어 있는 경로
        
        Output
        ------
        dict (key: 기준년월, value: 보고서 데이터프레임)
        
        Example
        -------
        rpt = load_rpt_all('data')
    '''
    
    # 기준년월 생성 및 데이터 목록 유효성 검증
    m = re.compile('^시산표_\d{8}.xlsx$')
    data = [f for f in os.listdir(path) if m.search(f)]
    enddate_list_data = list(map(lambda x: x[4:12], data))
    enddate_list_data.sort()
    start_enddate, end_enddate = enddate_list_data[0], enddate_list_data[-1]
    start_year, start_month = int(start_enddate[:4]), int(start_enddate[4:-2])
    end_year, end_month = int(end_enddate[:4]), int(end_enddate[4:-2])
    enddate_list = generate_enddate((start_year, start_month), (end_year, end_month))
    if enddate_list_data != enddate_list:
        raise('생성된 기준년월 리스트와 데이터 목록상 기준년월말일 불일치')

    # 데이터 로드
    tb = {}
    for enddate in enddate_list:
        tb[enddate[:6]] = __load_tb(os.path.join(path, f'시산표_{enddate}.xlsx'))
        
    return tb