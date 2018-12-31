"""
HtmlCrawler class

When getting data from webpage, user HtmlCrawler
it uses BeautifulSoup to find tag, class, id and etc.
"""

__author__ = 'sj'

from bs4 import BeautifulSoup
from myPackage.BaseCrawler import BaseCrawler

class HtmlCrawler(BaseCrawler):
    def getBs(self):
        try:
            data = BaseCrawler.getHtml(self)
            soup = BeautifulSoup(data, 'html.parser')
        except CrawlerException as e:
            raise CrawlerException(e)
        return soup