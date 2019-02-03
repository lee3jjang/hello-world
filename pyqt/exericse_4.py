import sys
from PyQt5.QtWidgets import QApplication, QMainWindow, QAction, QMenu, qApp


class Exam(QMainWindow):

    def __init__(self):
        super().__init__()
        self.initUI()

    def initUI(self):
        self.statusBar().showMessage('안녕하십니까~')

        # 메뉴바 생성
        menu = self.menuBar()
        menu_file = menu.addMenu('파일')
        menu_view = menu.addMenu('보기')
        
        # 아무 액션
        menu_file_new = QAction('새로만들기', self)
        menu_file_new.setStatusTip('이 것은 새로만들기입니다')

        menu_file_exit = QAction('나가기', self)
        menu_file_exit.triggered.connect(qApp.exit)

        # 체크 가능한 액션
        menu_view_stat = QAction('상태표시줄', self, checkable=True)
        menu_view_stat.setChecked(True)
        menu_view_stat.triggered.connect(self.tglStat)

        # 메뉴에 액션 추가하기
        menu_file.addAction(menu_file_new)
        menu_file.addAction(menu_file_exit)
        menu_view.addAction(menu_view_stat)

        self.setWindowTitle('연습장')
        self.resize(500,400)
        self.show()

    def tglStat(self, state):
        if state:
            print('체크가 되었습니다')
            self.statusBar().show()
        else:
            print('체크가 되지 않았습니다')
            self.statusBar().hide()

    # Overriding
    # 오른쪽 클릭
    def contextMenuEvent(self, QContextMenuEvent):
        # cm 메뉴 추가하기
        cm = QMenu(self)
        
        # cm 메뉴에 액션 추가하기
        run = cm.addAction('실행하기')
        quit = cm.addAction('나가기')

        run.triggered.connect(lambda: print('Run 실행됨'))
        quit.triggered.connect(lambda: print('Quit 실행됨'))

        # 오른쪽 클릭해서 선택되는 QAction 객체를 action 으로 받음
        action = cm.exec_(self.mapToGlobal(QContextMenuEvent.pos()))
        
        if action == run:
            print('Run 입니다')
        elif action == quit:
            qApp.quit()
        else:
            print('아무 것도 선택되지 않았습니다')


app = QApplication(sys.argv)
w = Exam()
sys.exit(app.exec_())