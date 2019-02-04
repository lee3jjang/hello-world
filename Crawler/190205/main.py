import sqlite3
import pandas as pd
from cetizen import PnoCrawler, ReleasePriceCrawler, UsedPriceCrawler
from dbhandler import DBHandler

if __name__ == '__main__':

    # DB 연결
    conn = sqlite3.connect('cetizen.db')
    db = DBHandler(conn)

    # 상품정보 수집
    #pno_crawler = PnoCrawler()
    #df = pno_crawler.crawling()
    #db.insTable('상품정보', df)

    # 상품코드 목록 추출
    pno = list(db.getTableAll('상품정보')['PNO'])

    # 출고가정보 수집
    release_price_crawler = ReleasePriceCrawler(pno)
    df = release_price_crawler.crawling()
    release_price_crawler.save()
    db.insTable('출고가정보', df)

    # 중고가정보 수집
    used_price_crawler = UsedPriceCrawler(pno)
    df = used_price_crawler.crawling()
    used_price_crawler.save()
    db.insTable('중고가정보', df)

    print('--done--')


