import sys
from PyQt5.QtWidgets import QMainWindow, QApplication
from PyQt5.QtCore import pyqtSignal, QObject


class Communicate(QObject):
    closeApp = pyqtSignal()


class Example(QMainWindow):

    def __init__(self):
        super().__init__()
        self.initUI()

    def initUI(self):

        self.c = Communicate()
        # self.c에 있는 pyqtSignal 객체랑 close랑 연결함
        # c는 QObject를 상속받은 클래스의 객체여야 함
        self.c.closeApp.connect(self.close)

        self.setGeometry(300, 300, 450, 200)
        self.setWindowTitle('이벤트 핸들러')
        self.show()
    
    # 마우스를 클릭하면
    # self.c에 있는 pyqtSignal 객체가 emit라는 메소드를 호출함
    # pyqtSignal이라는 객체가 emit 메소드를 호출하면, connect로 연결되는 메소드가 호출됨
    def mousePressEvent(self, event):
        self.c.closeApp.emit()


app = QApplication(sys.argv)
ex = Example()
sys.exit(app.exec_())
