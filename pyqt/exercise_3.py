import sys
from PyQt5.QtWidgets import QApplication, QMainWindow, QAction, QMenu
from PyQt5.QtCore import QCoreApplication

# QWidget과 QMainWindow의 차이 : 메뉴 추가 가능한지 여부
class Exam(QMainWindow):

    def __init__(self):
        super().__init__()
        self.initUI()

    def initUI(self):
        # self 중에 statusBar라고 하는 메소드가 존재함
        self.statusBar().showMessage('안녕하세요')

        menu = self.menuBar()                  # 메뉴 생성
        menu_file = menu.addMenu('파일')       # 그룹 생성
        menu_edit = menu.addMenu('수정')       # 그룹 생성

        #####################################################################

        # QAction
        file_exit = QAction('나가기', self)    # QAction 객체 생성
        file_exit.setShortcut('Ctrl+Q')
        file_exit.setStatusTip('누르면 영원히 빠이빠이')

        # QAction 객체 -> triggered -> connect
        file_exit.triggered.connect(QCoreApplication.instance().quit)

        # QAction (2)
        file_new_txt = QAction('텍스트 파일', self)
        file_new_txt.setShortcut('alt+t')
        file_new_py = QAction('파이썬 파일', self)
        file_new_py.setShortcut('alt+p')

        # QMenu
        file_new = QMenu('새로하기', self)                  # 서브그룹
        file_new.addAction(file_new_txt)                    # 서브메뉴 추가
        file_new.addAction(file_new_py)

        #####################################################################

        # QMenu에 QAction 추가하고 menu에 QMenu 추가
        # 메뉴 안에 메뉴를 넣을 수 있는 구조인 듯 하다
        menu_file.addMenu(file_new)                         # 주메뉴 추가
        menu_file.addAction(file_exit)

        self.setWindowTitle('윈도우 이름')
        self.resize(450, 400)
        self.show()

app = QApplication(sys.argv)
w = Exam()
sys.exit(app.exec_())
