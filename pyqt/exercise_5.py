import sys
from PyQt5.QtWidgets import QWidget, QApplication, QPushButton, QHBoxLayout, QVBoxLayout

class Example(QWidget):

    def __init__(self):
        super().__init__()
        self.initUI()

    def initUI(self):
        #lbl1 = QLabel('나의 코드', self)
        #lbl1.move(100, 50)

        okBtn = QPushButton('오케이')
        cancelBtn = QPushButton('취소')
        
        # hbox 생성한 후 스트레치 한 다음에 버튼 2개 집어넣기
        hbox = QHBoxLayout()
        hbox.addStretch(1)
        hbox.addWidget(okBtn)
        hbox.addWidget(cancelBtn)

        # vbox 생성한 후 hbox을 vbox에 집어넣기
        vbox = QVBoxLayout()
        vbox.addStretch(1)
        vbox.addLayout(hbox)
        
        # self 의 Layout을 vbox로 설정
        self.setLayout(vbox)

        self.setGeometry(300, 300, 500, 300)
        self.setWindowTitle('절대반지')
        self.show()

app = QApplication(sys.argv)
ex = Example()
sys.exit(app.exec_())