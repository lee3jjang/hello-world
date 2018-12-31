"""
Main class

When starting, LupinCrawler send 'start message' into your messanger.
And then the crawler checks difference of webtoon sites / before and now.
If there is any difference, the crawler send 'new webtoon message' into your messanger.
"""

__author__ = 'sj'

import time
import sys
import inspect
import Crawlers
from threading import Thread
import urllib.request as req
import json
import requests
import datetime
import traceback

class LupinCrawler(Thread):
    jandi_url = 'https://wh.jandi.com/connect-api/webhook/17368154/aad86fa137c6151552afb5ee9f27b117'
    jandi_header = {
        'Accept':'application/vnd.tosslab.jandi-v2+json',
        'Content-Type':'application/json'
    }
    channel = '#lupin_crawler'
    check_time = 0
    
    def __init__(self):
        Thread.__init__(self)
        payload = {
            'body':self.channel + ' start (body)',
            'connectColor':'#FAC11B',
            'connectInfo':[{
                'title':'Hello Title',
                'description':self.channel+' start (desc)'
            }]
        }
        requests.post(self.jandi_url, data=json.dumps(payload), headers=self.jandi_header)

    def loadCrawlers(self):
        print('import Crawlers')
        self.crawlers = []
        for name, obj in inspect.getmembers(Crawlers):
            if name == 'HtmlCrawler':
                continue
            if inspect.isclass(obj):
                print(name)
                crawlerObj = getattr(Crawlers, name)()
                self.crawlers.append(crawlerObj)
    
    def start(self):
        Thread.start(self)
        
    def stop(self):
        pass
    
    def run(self):
        self.data = {}
        for i in range(0, len(self.crawlers)):
            self.data[i] = ''
            while(True):
                for i in range(0, len(self.crawlers)):
                    crawler = self.crawlers[i]
                    try:
                        data = crawler.get()
                        if ''.join(data) != self.data[i]:
                            if self.data[i] == '':
                                payload = {
                                    'body': crawler.__class__.__name__ + " : new webtoon arrived!",
                                    'connectColor': '#FAC11B',
                                    'connectInfo': [{
                                        'description':
                                            'Crawler name : ' + crawler.__class__.__name__ + '\n' + 
                                            'Crawler webtoon list : ' + '\n' + '\n'.join(data)
                                    }]
                                }
                                requests.post(self.jandi_url, data=json.dumps(payload), headers=self.jandi_header)
                                print(data[:200])
                        self.data[i] = ''.join(data)
                           
                    except Exception as e:
                        self.data[i] = ''
                        print(crawler.url)
                        exc_type, exc_value, exc_traceback = sys.exc_info()
                        lines = traceback.format_exception(exc_type, exc_value, exc_traceback)
                        print(''.join('* ') + line for line in lines)
                    
                now = datetime.datetime.now()
                
                if int(now.hour)%12 == 1 and self.check_time != now.hour:
                    self.check_time = int(now.hour)

                    count = 0
                    none_work_list = ''

                    for i in range(0, len(self.crawlers)):
                        crawler = self.crawlers[i]
                        if self.data[i] == '':
                            count += 1
                            none_work_list += crawler.__class__.__name__ + '\n'
                        payload = {
                            'body':self.channel + ' is alive!',
                            'connectColor':'#FAC11B',
                            'connectInfo':[{
                                'title': self.channel,
                                'description':str(count) + " crawlers are not working!\n" + none_work_list
                            }]
                        }
                        requests.post(self.jandi_url, data=json.dumps(payload), headers=self.jandi_header)
                time.sleep(3600)
                

                
if __name__ == '__main__':
    monitor = LupinCrawler()
    monitor.loadCrawlers()
    monitor.start()