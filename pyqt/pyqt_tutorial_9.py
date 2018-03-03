# -*- coding: utf-8 -*-
"""
Created on Sat Mar  3 23:50:05 2018

@author: PC
"""

import sys

from PyQt5.QtWidgets import QWidget
from PyQt5.QtWidgets import QPushButton
from PyQt5.QtWidgets import QGroupBox
from PyQt5.QtWidgets import QSpacerItem
from PyQt5.QtWidgets import QLineEdit
from PyQt5.QtWidgets import QTextEdit
from PyQt5.QtWidgets import QLabel

from PyQt5.QtWidgets import QBoxLayout
from PyQt5.QtWidgets import QHBoxLayout
from PyQt5.QtWidgets import QGridLayout
from PyQt5.QtWidgets import QFormLayout

from PyQt5.QtCore import Qt
from PyQt5.QtWidgets import QApplication

__author__ = "lee3jjang@gmail.com"

class myForm(QWidget):
    def __init__(self):
        QWidget.__init__(self, flags=Qt.Widget)
        
        self.setWindowTitle("Various Layout Widgets application")
        self.setFixedWidth(640)
        self.setFixedHeight(400)
        
        layout_base = QBoxLayout(QBoxLayout.TopToBottom, self)
        self.setLayout(layout_base)
    
        grp_1 = QGroupBox("1st group")
        layout_base.addWidget(grp_1)
        layout = QHBoxLayout()
        layout.addWidget(QPushButton("Button 1"))
        layout.addWidget(QPushButton("Button 2"))
        layout.addWidget(QPushButton("Button 3"))
        grp_1.setLayout(layout)
        
        grp_2 = QGroupBox("2nd group")
        layout_base.addWidget(grp_2)
        grp_2_layout = QBoxLayout(QBoxLayout.LeftToRight)
        grp_2.setLayout(grp_2_layout)
        layout = QGridLayout()
        layout.addItem(QSpacerItem(100,200))
        layout.addWidget(QLabel("Line Edit 1:"), 1, 0)
        layout.addWidget(QLabel("Line Edit 2:"), 2, 0)
        layout.addWidget(QLabel("Line Edit 3:"), 3, 0)
        layout.addWidget(QLineEdit(), 1, 1)
        layout.addWidget(QLineEdit(), 2, 1)
        layout.addWidget(QLineEdit(), 3, 1)
        grp_2_layout.addLayout(layout)
        grp_2_layout.addWidget(QTextEdit())
        
        grp_3 = QGroupBox("Last group")
        layout_base.addWidget(grp_3)
        layout = QFormLayout()
        grp_3.setLayout(layout)
        layout.addRow(QLabel("Line Edit 1:"), QLineEdit())
        layout.addRow(QLabel("Line Edit 2:"), QLineEdit())
        layout.addRow(QLabel("Line Edit 3:"), QLineEdit())
        
        
        
        

if __name__ == "__main__":
    app = QApplication(sys.argv)
    form = myForm()
    form.show()
    exit(app.exec_())
