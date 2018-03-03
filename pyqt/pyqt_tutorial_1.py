# coding: utf-8

import sys
from PyQt5 import QtWidgets
from PyQt5 import uic

class Form(QtWidgets.QDialog):
    def __init__(self, parent=None):
        QtWidgets.QDialog.__init__(self, parent)
        self.ui = uic.loadUi("form.ui")
        self.ui.show()
        
if __name__ == '__main__':
    app = QtWidgets.QApplication(sys.argv)
    w = Form()
    sys.exit(app.exec())
