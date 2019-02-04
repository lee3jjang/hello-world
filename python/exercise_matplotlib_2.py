from PyQt5.QtWidgets import *
from PyQt5.uic import loadUi
from matplotlib.backends.backend_qt5agg import NavigationToolbar2QT as NavigationToolbar
import numpy as np
import random
import sys

class MatplotlibWidget(QMainWindow):

    def __init__(self):
        super().__init__()
        loadUi('matplotlib.ui', self)

        self.random_signal.clicked.connect(self.update_graph)
        #self.addToolBar(NavigationToolbar(self.MplWidget.canvas, self))

        self.setWindowTitle('Matplotlib Example GUI')
        self.show()

    def update_graph(self):

        f = random.randint(1, 100)
        length_of_signal = 100
        t = np.linspace(0, 1, length_of_signal)

        cosinus_signal = np.cos(2*np.pi*f*t)
        sinus_signal = np.sin(2*np.pi*f*t)

        self.MplWidget.canvas.axes.clear()
        self.MplWidget.canvas.axes.plot(t, cosinus_signal)
        self.MplWidget.canvas.axes.plot(t, sinus_signal)
        self.MplWidget.canvas.axes.legend(('cosinus', 'sinus'), loc='upper right')
        self.MplWidget.canvas.axes.set_title('Cosinus - Sinus Signal')
        self.MplWidget.canvas.draw()

app = QApplication(sys.argv)
window = MatplotlibWidget()
sys.exit(app.exec_())

