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
query = {'lang' :'kr',
         'auth' : '7774090942ede970746a7cd9b2e10577',
         'financeCd' : '0010927','listNo':'SA001',
         'accountCd':'A11',
         'term':'Q',
         'startBaseMm':'201306',
         'endBaseMm':'201306'}
         
url = queryStr(url,query)
res = req.get(url)
print(json.loads(res.text))
