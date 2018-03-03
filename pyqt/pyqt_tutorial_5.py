# -*- coding: utf-8 -*-
"""
Created on Sat Mar  3 22:53:35 2018

@author: lee3jjang
"""


from PyQt5.QtCore import Qt
from PyQt5.QtWidgets import QWidget
from PyQt5.QtWidgets import QApplication
from PyQt5.QtWidgets import QLabel

__author__ = "lee3jjang@gmail.com"

class myForm(QWidget):
    def __init__(self):
        QWidget.__init__(self, flags=Qt.Widget)
        self.mouse_x = 0
        self.mouse_y = 0
        self.i = 0
        
        self.lb = QLabel(self)
        
        self.properties_list = (
            "width","height","x","y","geometry",
            "maximumHeight","maximumWidth","maximumSize","minimumSize","minimumWidth",
            "size","windowFilePath","windowTitle",
            "underMouse"
        )
        
        self.init_widget()
        
    def init_widget(self):
        self.setWindowTitle("Hello PyQt5!!")
        self.setGeometry(100,100,640,480)
        self.setMouseTracking(True)
        
        self.lb.setStyleSheet("background-color: yellow")
        self.lb.setMouseTracking(True)
        msg = self.get_properties_value(self.properties_list)
        self.lb.setText(msg)
        
    def get_properties_value(self, properties):
        msg = []
        for p in properties:
            if not hasattr(self, p):
                continue
            value = getattr(self, p)()
            msg.append("{:30s} : {:>30s}".format(p, str(value)))
        msg.append("{:30s} : {:>30s}".format("mouse_x", str(self.mouse_x)))
        msg.append("{:30s} : {:>30s}".format("mouse_y", str(self.mouse_y)))
        msg = "\n".join(msg)
        return msg
    
    def mouseMoveEvent(self, QMouseEvent):
        self.mouse_x = QMouseEvent.x()
        self.mouse_y = QMouseEvent.y()
        self.update()
        
    def moveEvent(self, QMoveEvent):
        self.update()
        
    def paintEvent(self, QPaintEvent):
        #msg = self.get_properties_value(self.properties_list)
        #self.lb.setText(msg)
        self.i += 1
        self.lb.setText("i:{:6s}, x:{:4s}, y:{:4s}".format(str(self.i),str(self.mouse_x),str(self.mouse_y)))
        
if __name__ == "__main__":
    app = QApplication(["C:/Users/PC/dev/pyqt_tutorial_5"])
    form = myForm()
    form.show()
    exit(app.exec_())
    
