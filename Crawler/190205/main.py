import sqlite3
from cetizen import PnoCrawler, ReleasePriceCrawler, UsedPriceCrawler
from dbhandler import DBHandler

if __name__ == '__main__':

    # DB 연결
    conn = sqlite3.connect('cetizen.db')
    db = DBHandler(conn)

    db.delTableAll('상품정보')
    db.delTableAll('출고가정보')
    db.delTableAll('중고가정보')
    db.commit()

    # 상품정보 수집
    pno_crawler = PnoCrawler()
    df = pno_crawler.crawling()
    db.insTable('상품정보', df)

    # 상품코드 목록 추출
    pno = list(db.getTableAll('상품정보')['PNO'])

    # 출고가정보 수집
    release_price_crawler = ReleasePriceCrawler(pno[:3])
    df = release_price_crawler.crawling()
    db.insTable('출고가정보', df)

    # 중고가정보 수집
    used_price_crawler = UsedPriceCrawler(pno[:3])
    df = used_price_crawler.crawling()
    db.insTable('중고가정보', df)

    print('--done--')


