"""
BaseCrawler class

Each crawler has the same way to get data.
BaseCrawler uses urllib library to get data and returns string data encoded with UTF-8
"""

__author__ = 'sj'

import urllib.request as req

class CrawlerException(Exception):
    pass

class BaseCrawler:
    url = ''
    
    def __init__(self, url):
        self.url = url
        
    def getHtml(self):
        headers = {'Accept':'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/8;q=0.8',
                   'Accept-Language':'ko-KR,ko;',
                   'User-Agent':'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36'}
        try:
            res = req.Request(self.url, None, headers)
            html = req.urlopen(res).read().decode('utf-8')
            
        except Exception as e:
            raise CrawlerException(e)
            
        return html