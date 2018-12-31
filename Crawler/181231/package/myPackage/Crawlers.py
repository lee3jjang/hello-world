"""
Crawler list class

When listing each websites or any other urls to get any webtoon information, list up Crawlers.py,
You haver to initialize each Crawler with name and you have to choose which way to get data like HtmlCrawler or BaseCrawler.
And then the class returns new webtoon list.
"""

__author__ = 'sj'

from myPackage.HtmlCrawler import HtmlCrawler
from urllib.parse import urljoin

class NaverCrawler(HtmlCrawler):
    def __init__(self):
        HtmlCrawler.__init__(self, 'http://comic.naver.com/webtoon/weekday.nhn')
    
    def get(self):
        soup = HtmlCrawler.getBs(self)
        data = soup.findAll('div',{'class':'thumb'})
        
        result = []
        for obj in data:
            if obj.find('em', {'class':'ico_updt'}) != None:
                result.append(obj.img['title'] + " / " + urljoin(self.url, obj.a['href']))
        return result