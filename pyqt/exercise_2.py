import sys
from PyQt5.QtWidgets import QApplication, QWidget, QPushButton, QMessageBox
from PyQt5.QtCore import QCoreApplication

class Exam(QWidget):
    def __init__(self):
        super().__init__()
        self.initUI()

    def initUI(self):
        btn = QPushButton('눌러주세요!', self)
        btn.resize(btn.sizeHint())
        btn.move(225, 50)
        # btn 객체가 clicked 됐을 때 함수를 호출함
        btn.clicked.connect(QApplication.instance().quit)

        self.resize(500, 500)
        self.setWindowTitle('두 번째 시간')
        self.show()

    # QWidget Class에 이미 정의되어 있는 메소드를 Override 하고 있음
    # 객체가 Close 되면 실행되는 듯
    def closeEvent(self, QCloseEvent):
        # QMessageBox.Yes 인지, QMessageBox.No 인지 받아서 ans로 집어 넣기
        ans = QMessageBox.question(self, '종료 확인', '종료하시겠습니까?',
                                   QMessageBox.Yes | QMessageBox.No, QMessageBox.Yes)
        # QCloseEvent.accept() : 이벤트를 받아들임
        # QCloseEvent.ignore() : 이벤트를 거부함
        if ans == QMessageBox.Yes:
            QCloseEvent.accept()
        else:
            QCloseEvent.ignore()





if __name__ == '__main__':
    app = QApplication(sys.argv)
    w = Exam()
    sys.exit(app.exec_())

