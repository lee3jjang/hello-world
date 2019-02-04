import sys
import pickle
from PyQt5.QtWidgets import QApplication, QWidget, QTableWidget, \
    QTableWidgetItem, QVBoxLayout, QPushButton

class App(QWidget):

    def __init__(self):
        super().__init__()
        self.size = 4
        self.initUI()

    def initUI(self):
        self.setWindowTitle('이 것이 타이틀입니다')
        self.setGeometry(50, 50, 660, 240)

        # 버튼
        self.btn = QPushButton('저장')

        # 테이블
        self.createTable()

        # 레이아웃
        self.layout = QVBoxLayout()
        self.layout.addWidget(self.table)
        self.layout.addWidget(self.btn)

        self.setLayout(self.layout)
        self.show()

    def createTable(self):
        self.table = QTableWidget()
        self.table.setRowCount(self.size)
        self.table.setColumnCount(self.size)
        self.table.setHorizontalHeaderLabels(('이름', '국어', '영어', '수학'))


        fp = open('out.txt', 'rb')
        data = pickle.load(fp)
        for r in range(self.size):
            for c in range(self.size):
                self.table.setItem(r, c,
                                   QTableWidgetItem(data[r][c]))
        fp.close()


app = QApplication(sys.argv)
ex = App()
sys.exit(app.exec_())
