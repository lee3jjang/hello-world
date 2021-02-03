class 사람:
    def 인사(self):
        print("안녕하세요")

class 대학:
    def 학점관리(self):
        print("학점 관리")

    def 인사(self):
        print("안녕!")

class 재학생(사람, 대학):
    def 공부(self):
        print("공부하기")

이상진 = 재학생()
이상진.인사()