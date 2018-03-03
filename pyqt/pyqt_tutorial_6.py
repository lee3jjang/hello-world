# -*- coding: utf-8 -*-
"""
Created on Sat Mar  3 23:33:52 2018

@author: PC
"""

import sys

from PyQt5.QtWidgets import QWidget
from PyQt5.QtWidgets import QPushButton
from PyQt5.QtWidgets import QLabel
from PyQt5.QtWidgets import QBoxLayout

from PyQt5.QtCore import Qt
from PyQt5.QtWidgets import QApplication

__author__ = "lee3jjang@gmail.com"

class myForm(QWidget):
    def __init__(self):
        QWidget.__init__(self, flags=Qt.Widget)
        
        self.lb_1 = QLabel()
        self.lb_2 = QLabel()
        self.pb_1 = QPushButton()
        self.pb_2 = QPushButton()
        
        self.layout_1 = QBoxLayout(QBoxLayout.LeftToRight, self)
        self.setLayout(self.layout_1)
        self.init_widget()
        
    def init_widget(self):
        self.setWindowTitle("Layout exercise application")
        self.setFixedWidth(640)
        
        self.lb_1.setText("Label 1")
        self.lb_1.setStyleSheet("background-color: blue")
        self.pb_1.setText("Button 1")
        self.layout_1.addWidget(self.lb_1)
        self.layout_1.addWidget(self.pb_1)
        

        self.layout_1.addWidget(self.lb_2)
        self.layout_1.addWidget(self.pb_2)
        self.lb_2.setText("Label 99")
        self.lb_2.setStyleSheet("background-color: red")
        self.pb_2.setText("Button 99")

if __name__ == "__main__":
    app = QApplication(sys.argv)
    form = myForm()
    form.show()
    exit(app.exec_())
