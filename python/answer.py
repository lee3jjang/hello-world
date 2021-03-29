## Quiz
# 2023년 일반보험의 지급보험금 합계를 구하는 코드를 작성하세요 

import pandas as pd
  
보험료보험금_1월 = pd.read_excel('data/202301.xlsx', header=[0, 1])
보험료보험금_2월 = pd.read_excel('data/202302.xlsx', header=[0, 1])
보험료보험금_3월 = pd.read_excel('data/202303.xlsx', header=[0, 1])
보험료보험금_4월 = pd.read_excel('data/202304.xlsx', header=[0, 1])
보험료보험금_5월 = pd.read_excel('data/202305.xlsx', header=[0, 1])
보험료보험금_6월 = pd.read_excel('data/202306.xlsx', header=[0, 1])
보험료보험금_7월 = pd.read_excel('data/202307.xlsx', header=[0, 1])
보험료보험금_8월 = pd.read_excel('data/202308.xlsx', header=[0, 1])
보험료보험금_9월 = pd.read_excel('data/202309.xlsx', header=[0, 1])
보험료보험금_10월 = pd.read_excel('data/202310.xlsx', header=[0, 1])
보험료보험금_11월 = pd.read_excel('data/202311.xlsx', header=[0, 1])
보험료보험금_12월 = pd.read_excel('data/202312.xlsx', header=[0, 1])
보험료보험금_2023년 = pd.concat([보험료보험금_1월, 보험료보험금_2월, 보험료보험금_3월, 보험료보험금_4월, 보험료보험금_5월, 보험료보험금_6월, 보험료보험금_8월, 보험료보험금_8월, 보험료보험금_9월, 보험료보험금_10월, 보험료보험금_11월, 보험료보험금_12월])
보험료보험금_2023년
보험료_일반_2023년 = 보험료보험금_2023년['지급보험금']['일반'].sum()
보험료_일반_2023년
