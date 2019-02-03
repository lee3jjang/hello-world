import sys
from PyQt5.QtWidgets import QWidget, QLCDNumber, QSlider, QVBoxLayout, QApplication
from PyQt5.QtCore import Qt


class Example(QWidget):

    def __init__(self):
        super().__init__()
        self.initUI()

    def initUI(self):

        # 레이아웃 설정
        lcd = QLCDNumber(self)
        sld = QSlider(Qt.Horizontal, self)
        # QVBoxLayout : 레이아웃을 세로로 쌓는 형식임
        vbox = QVBoxLayout()
        vbox.addWidget(lcd)
        vbox.addWidget(sld)
        self.setLayout(vbox)

        # sld 객체가 valueChanged 되면, lcd의 display method랑 연결시킴
        # 아마도 lcd.display는 숫자를 받아 프린트하는 메소드가 아닐까 생각되고
        # valueChanged는 다른 애를 호출할 때 숫자를 인자로 넘겨주지 않을까 생각됨
        sld.valueChanged.connect(lcd.display)

        self.setGeometry(300, 300, 250, 150)
        self.setWindowTitle('충격..!')
        self.show()

app = QApplication(sys.argv)
ex = Example()
sys.exit(app.exec_())
