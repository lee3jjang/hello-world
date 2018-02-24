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
##########################################################################################
url = r'http://fisis.fss.or.kr/openapi/statisticsInfoSearch.json?'
query = {'lang' :'kr',                                              # 언어
         'auth' : '7774090942ede970746a7cd9b2e10577',               # 인증키
         'financeCd' : '0010927','listNo':'SA001',                  # 금융회사코드
         'accountCd':'A11',                                         # 계정항목코드
         'term':'Q',                                                # 시기구분(Y:연도, H:반기, Q:분기)
         'startBaseMm':'201306',                                    # 검색시작년월
         'endBaseMm':'201306'}                                      # 검색종료년월
         
url = queryStr(url,query)
res = req.get(url)
print(json.loads(res.text))
