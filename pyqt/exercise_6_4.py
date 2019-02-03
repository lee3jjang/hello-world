import sys
from PyQt5.QtWidgets import QMainWindow, QApplication
from PyQt5.QtWidgets import QPushButton

class Example(QMainWindow):

    def __init__(self):
        super().__init__()
        self.initUI()

    def initUI(self):

        btn1 = QPushButton('버튼1', self)
        btn1.move(30, 50)

        btn2 = QPushButton('버튼2', self)
        btn2.move(150, 50)


        btn1.clicked.connect(self.buttonClicked)
        btn2.clicked.connect(self.buttonClicked)

        self.statusBar().showMessage('아무 것도 없음')

        self.setGeometry(300, 300, 450, 200)
        self.setWindowTitle('이벤트 핸들러')
        self.show()

    # 버튼의 이름이 전달됨
    # self 안에 sender 라는 메소드를 통해 버튼 객체 생성
    def buttonClicked(self):
        sender = self.sender()
        self.statusBar().showMessage(sender.text() + ' was pressed')


app = QApplication(sys.argv)
ex = Example()
sys.exit(app.exec_())
