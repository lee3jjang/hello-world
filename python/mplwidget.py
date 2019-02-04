from PyQt5.QtWidgets import *
from matplotlib.backends.backend_qt5agg import FigureCanvasQTAgg as FigureCanvas
from matplotlib.figure import Figure


class MplWidget(QWidget):

    def __init__(self, parent=None):
        super().__init__(parent)
        self.canvas = FigureCanvas(Figure())
        vbox = QVBoxLayout()
        vbox.addWidget(self.canvas)
        self.canvas.axes = self.canvas.figure.add_subplot(1, 1, 1)
        self.setLayout(vbox)