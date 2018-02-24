import requests as req
import json

##########################################################################################
def queryStr(url,query):
    if url[-1] != '?':
        url+='?' 
    qstring = ''
    for key in query.keys():
        qstring += str(key)+"="+query[key]+"&"
    full_url = url+qstring[:-1]
    return (full_url)
################################### statistics ###########################################

url = r'http://fisis.fss.or.kr/openapi/statisticsInfoSearch.json?'
query = {'lang' :'kr',                                              # 언어
         'auth' : '7774090942ede970746a7cd9b2e10577',               # 인증키
         'financeCd' : '0010927',                                   # 금융회사코드
         'listNo':'SA001',                                          # 통계코드
         'accountCd':'A11',                                         # 계정항목코드
         'term':'Q',                                                # 시기구분(Y:연도, H:반기, Q:분기)
         'startBaseMm':'201306',                                    # 검색시작년월
         'endBaseMm':'201306'}                                      # 검색종료년월
         
url = queryStr(url,query)
res = req.get(url)
print(json.loads(res.text))

#################################### company #############################################
url = r'http://fisis.fss.or.kr/openapi/companySearch.json?'
query = {
    'lang':'kr',
    'auth':'7774090942ede970746a7cd9b2e10577',
    'partDiv':'C',                               # 금융권역코드
    'financeCd':'0010224'                        # 금융회사코드
}

url = queryStr(url,query)
res = req.get(url)
print('\n',json.loads(res.text))
################################ statistics list #########################################
url = r'http://fisis.fss.or.kr/openapi/statisticsListSearch.json?'
query = {
    'lang':'kr',
    'auth':'7774090942ede970746a7cd9b2e10577',
    'lrgDiv':'A',                                # 금융권역코드
    'smlDiv':'A'                                 # 통계표분류코드
}

url = queryStr(url,query)
res = req.get(url)
print('\n',json.loads(res.text))
################################## account list ##########################################
url = r'http://fisis.fss.or.kr/openapi/accountListSearch.json?'
query = {
    'lang':'kr',
    'auth':'7774090942ede970746a7cd9b2e10577',
    'listNo':'SA002'                              # 통계코드
}

url = queryStr(url,query)
res = req.get(url)
print('\n',json.loads(res.text))

# 요청결과
# result : err_cd, err_msg, total_count, description, date_of_settlement, unit, list
# err_cd : 응답코드
# err_msg : 응답메시지
# total_count : 결과건수
# description : column_id, column_nb
#   column_id : 컬럼ID
#   column_nm : 컬럼이름
# date_of_settlement
# unit : 단위
# list : base_month, finance_cd, finance_nb
#   base_month : 기준년월
#   finance_cd : 회사코드
#   finance_nm : 회사명
#   account_cd : 계정코드
#   account_nm : 계정명
