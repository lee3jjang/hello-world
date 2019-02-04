from PyQt5.QtWidgets import QMainWindow, QApplication, QPushButton
import sys
from matplotlib.backends.backend_qt5agg import FigureCanvasQTAgg as FigureCanvas
from matplotlib.figure import Figure
import numpy as np

class Window(QMainWindow):

    def __init__(self):
        super().__init__()
        self.initUI()

    def initUI(self):
        title = 'Matplotlib Embeding In PyQt5'
        left = 400
        top = 400
        width = 900
        height = 450

        self.setWindowTitle(title)
        self.setGeometry(left, top, width, height)
        self.MyUI()
        self.show()

    def MyUI(self):
        canvas = Canvas(self, width=8, height=4)
        canvas.move(0,0)
        button = QPushButton('누르세요', self)
        button.move(100, 400)
        button2 = QPushButton('누르세요2', self)
        button2.move(250, 400)




class Canvas(FigureCanvas):

    # Parent : Window 클래스 객체
    # dpi : width*dpi = 가로, height*dpi = 세로
    def __init__(self, parent=None, width=5, height=4, dpi=100):
        # Figure 객체 fig를 생성함
        # fig에서 subplot를 만듬
        # fig 객체를 넣어 FigureCanvas 초기화 실행
        # setParent를 parent를 넣어 함

        fig = Figure(figsize=(width, height), dpi=dpi)
        #self.axes = fig.add_subplot(1, 1, 1)
        super().__init__(fig)
        self.setParent(parent)
        self.plot()

    def plot(self):
        x = np.array([50, 30, 40])
        labels = ['Apples', 'Bananas', 'Melons']
        ax = self.figure.add_subplot(1, 1, 1)
        ax.pie(x, labels=labels)


app = QApplication(sys.argv)
ex = Window()
sys.exit(app.exec_())



