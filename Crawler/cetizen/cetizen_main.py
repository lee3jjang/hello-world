import sys
import sqlite3
from PyQt5.QtWidgets import QApplication, QMainWindow
from PyQt5.QtGui import QStandardItemModel
from PyQt5.uic import loadUi
from PyQt5.QtCore import pyqtSlot, Qt
from dbhandler import DBHandler
from cetizen import PnoCrawler, ReleasePriceCrawler, UsedPriceCrawler
from datetime import datetime

__author__ = 'Sangjin Lee <lee3jjang@gmail.com>'


class CetizenWindow(QMainWindow):

    def __init__(self, parent=None):
        super().__init__(parent)
        conn = sqlite3.connect('cetizen.db')
        self.db = DBHandler(conn)
        self.ui = loadUi('cetizen.ui', self)


        self.logger.setText('')
        self.table.setAlternatingRowColors(True)
        #self.table.setRootIsDecorated(False)

        self.setWindowTitle('Cetizen Crawler')
        self.ui.show()

    @pyqtSlot()
    def slot_pno_select(self):
        df = self.db.getTableAll('상품정보')
        n = df.shape[0]
        rows = QStandardItemModel(n, 4, self)
        rows.setHeaderData(0, Qt.Horizontal, '통신사')
        rows.setHeaderData(1, Qt.Horizontal, '상품코드')
        rows.setHeaderData(2, Qt.Horizontal, '상품명')
        rows.setHeaderData(3, Qt.Horizontal, '모델명')
        for i in range(n):
            for j in range(4):
                rows.setData(rows.index(i, j), df.iloc[i, j])
        self.table.setModel(rows)

    @pyqtSlot()
    def slot_pno_insert(self):
        self.logger.append('-- 상품정보    수집 시작 -- {}'.format(datetime.now()))
        pno_crawler = PnoCrawler()
        df = pno_crawler.crawling(save=True)
        self.logger.append('-- 상품정보    수집 완료 -- {}'.format(datetime.now()))
        self.db.insTable('상품정보', df)
        self.logger.append('-- 상품정보    적재 완료 -- {}'.format(datetime.now()))

    @pyqtSlot()
    def slot_pno_delete(self):
        self.db.delTableAll('상품정보')
        self.logger.append('-- 상품정보    삭제 완료 -- {}'.format(datetime.now()))

    @pyqtSlot()
    def slot_rel_select(self):
        df = self.db.getTableAll('출고가정보')
        n = df.shape[0]
        rows = QStandardItemModel(n, 3, self)
        rows.setHeaderData(0, Qt.Horizontal, '상품코드')
        rows.setHeaderData(1, Qt.Horizontal, '기준일자')
        rows.setHeaderData(2, Qt.Horizontal, '출고가')
        for i in range(n):
            for j in range(3):
                rows.setData(rows.index(i, j), str(df.iloc[i, j]))
        self.table.setModel(rows)

    @pyqtSlot()
    def slot_rel_insert(self):
        self.logger.append('-- 출고가정보 수집 시작 -- {}'.format(datetime.now()))
        pno = list(self.db.getTableAll('상품정보')['PNO'])[:10]
        release_price_crawler = ReleasePriceCrawler(pno)
        df = release_price_crawler.crawling(save=True)
        self.logger.append('-- 출고가정보 수집 완료 -- {}'.format(datetime.now()))
        self.db.insTable('출고가정보', df)
        self.logger.append('-- 출고가정보 적재 완료 -- {}'.format(datetime.now()))

    @pyqtSlot()
    def slot_rel_delete(self):
        self.db.delTableAll('출고가정보')
        self.logger.append('-- 출고가정보 삭제 완료 -- {}'.format(datetime.now()))

    @pyqtSlot()
    def slot_used_select(self):
        df = self.db.getTableAll('중고가정보')
        n = df.shape[0]
        rows = QStandardItemModel(n, 5, self)
        rows.setHeaderData(0, Qt.Horizontal, '상품코드')
        rows.setHeaderData(1, Qt.Horizontal, '기준일자')
        rows.setHeaderData(2, Qt.Horizontal, '저가')
        rows.setHeaderData(3, Qt.Horizontal, '중간')
        rows.setHeaderData(4, Qt.Horizontal, '고가')
        for i in range(n):
            for j in range(5):
                rows.setData(rows.index(i, j), str(df.iloc[i, j]))
        self.table.setModel(rows)

    @pyqtSlot()
    def slot_used_insert(self):
        self.logger.append('-- 중고가정보 수집 시작 -- {}'.format(datetime.now()))
        pno = list(self.db.getTableAll('상품정보')['PNO'])[:10]
        used_price_crawler = UsedPriceCrawler(pno)
        df = used_price_crawler.crawling(save=True)
        self.logger.append('-- 중고가정보 수집 완료 -- {}'.format(datetime.now()))
        self.db.insTable('중고가정보', df)
        self.logger.append('-- 중고가정보 적재 완료 -- {}'.format(datetime.now()))

    @pyqtSlot()
    def slot_used_delete(self):
        self.db.delTableAll('중고가정보')
        self.logger.append('-- 중고가정보 삭제 완료 -- {}'.format(datetime.now()))




if __name__ == '__main__':
    app = QApplication(sys.argv)
    cetizen = CetizenWindow()
    sys.exit(app.exec_())
