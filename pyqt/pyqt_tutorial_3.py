# coding: utf-8

import sys
from PyQt5 import QtWidgets
from PyQt5 import uic

__author__ = "lee3jjang@gmail.com"

class myForm(QtWidgets.QDialog):
    def __init__(self, parent=None):
        QtWidgets.QDialog.__init__(self, parent)
        self.ui = uic.loadUi("ui_dial.ui", self)
        self.ui.show()

        
if __name__ == "__main__":
    app = QtWidgets.QApplication(sys.argv)
    myForm()
    sys.exit(app.exec())
