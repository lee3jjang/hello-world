import sys
from PyQt5.QtWidgets import QApplication, QWidget
from PyQt5.QtWidgets import QPushButton

class Exam(QWidget):
    def __init__(self):
        super().__init__()
        self.initUI()

    def initUI(self):
        # 나 자신에게 버튼을 추가하겠다
        btn = QPushButton('Hello World', self)
        # 글씨를 바탕으로 알아서 사이즈 조절해줌
        btn.resize(btn.sizeHint())
        # 마우스 갖다 대면 글씨가 튀어나옴
        # HTML 태그 사용 가능
        btn.setToolTip('이 것은 <b>툴팁</b>입니다.')
        # 버튼 위치 움직이기
        btn.move(30,30)
        # 윈도우의 위치 및 크기 조절
        self.setGeometry(300,300,400,400)
        # 윈도우의 타이틀 넣기
        self.setWindowTitle("첫 번째 학습시간")

        self.show()

if __name__ == "__main__":
    # 어플리케이션 객체
    app = QApplication(sys.argv)
    # 위젯 객체
    w = Exam()
    # 프로그램을 깨끗하게 종료한다
    # app.exec_() : 이벤트 처리를 위한 루프를 실행함
    # exec는 키워드라 안 쓴다고 함
    sys.exit(app.exec_())
    # 위젯창, 일반창 차이점 : 메뉴가 없고, 있고


