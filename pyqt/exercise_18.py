import sys
from PyQt5.QtWidgets import QApplication, QWidget
import sqlite3

class Sql(QWidget):
    
    def __init__(self):
        super().__init__()
        self.sqlConnect()
        self.initUI()
        self.run()
        
    def sqlConnect(self):
        try:
            self.conn = sqlite3.connect('C:/DB/ESG.db')
        except:
            print('문제가 있습니다')
            exit(1)
            
        print('연결 성공!')
        self.cur = self.conn.cursor()
        
    def initUI(self):
        self.setGeometry(300, 300, 500, 200)
        self.setWindowTitle('데이터베이스 연결 중')
        self.show()
        
    def run(self):
        self.cmd = 'SELECT * FROM EAS_BIZ_APLY_CRD_SPREAD'
        self.cur.execute(self.cmd)
        self.conn.commit()
        print(self.cur.fetchall())
        
    def closeEvent(self, QCloseEvent):
        print('닫히기 성공함!')
        self.conn.close()
        
if __name__ == '__main__':
    app = QApplication(sys.argv)
    w = Sql()
    sys.exit(app.exec_())
    