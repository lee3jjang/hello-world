import sys
from PyQt5.QtWidgets import QWidget, QApplication, QLabel, QGridLayout, QLineEdit, QTextEdit


class Example(QWidget):
    
    def __init__(self):
        super().__init__()
        self.initUI()
    
    def initUI(self):

        # QLabel : 라벨. 못 바꿈
        title = QLabel('타이틀')
        author = QLabel('제작자')
        review = QLabel('리뷰')

        # QLineEdit : 한 줄만 있음
        # QTextEdit : 여러 줄 있음
        titleEdit = QLineEdit()
        authorEdit = QLineEdit()
        reviewEdit = QTextEdit()

        grid = QGridLayout()
        grid.setSpacing(10)

        grid.addWidget(title, 1, 0)
        grid.addWidget(titleEdit, 1, 1)
        grid.addWidget(author, 2, 0)
        grid.addWidget(authorEdit, 2, 1)
        grid.addWidget(review, 3, 0)
        grid.addWidget(reviewEdit, 3, 1, 5, 1)

        self.setLayout(grid)
        
        self.setGeometry(300, 300, 350, 300)
        self.setWindowTitle('테스트입니다')
        self.show()
        

app = QApplication(sys.argv)
ex = Example()
sys.exit(app.exec_())