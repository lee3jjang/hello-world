# -*- coding: utf-8 -*-
"""
Created on Sat Mar  3 23:33:52 2018

@author: PC
"""

import sys

from PyQt5.QtWidgets import QWidget
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
        self.lb_3 = QLabel()
        self.lb_4 = QLabel()
        self.lb_5 = QLabel()
        
        self.layout_1 = QBoxLayout(QBoxLayout.LeftToRight, self)
        self.setLayout(self.layout_1)
        self.init_widget()
        
    def init_widget(self):
        self.setWindowTitle("Layout exercise application")
        self.setFixedWidth(640)
        self.setFixedHeight(480)
        
        self.lb_1.setText("Label 1")
        self.lb_2.setText("Label 2")
        self.lb_3.setText("Label 3")
        self.lb_4.setText("Label 4")
        self.lb_5.setText("Label 5")
        
        self.lb_1.setStyleSheet("background-color: blue")
        self.lb_2.setStyleSheet("background-color: yellow")
        self.lb_3.setStyleSheet("background-color: red")
        self.lb_4.setStyleSheet("background-color: green")
        self.lb_5.setStyleSheet("background-color: pink")
        
        self.layout_1.addWidget(self.lb_1)
        self.layout_1.addWidget(self.lb_2, alignment=Qt.AlignTop)
        self.layout_1.addWidget(self.lb_3, alignment=Qt.AlignBottom)
        self.layout_1.addWidget(self.lb_4, alignment=Qt.AlignVCenter)
        self.layout_1.addWidget(self.lb_5, alignment=Qt.AlignHCenter)
        

if __name__ == "__main__":
    app = QApplication(sys.argv)
    form = myForm()
    form.show()
    exit(app.exec_())
