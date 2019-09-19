import sys
from PyQt5.uic import loadUi
from PyQt5.QtCore import pyqtSlot
from PyQt5.QtWidgets import QApplication, QDialog
from raas import Searcher, Router

class RAAS(QDialog):
    
    def __init__(self, parent=None):
        super().__init__(parent)
        self.ui = loadUi('./raas.ui', self)
        self.setWindowTitle('RAAS 폴더 정리기')
        self.ui.show()
        
    @pyqtSlot()
    def accept(self):
        path = self.ui.path.text()
        self._run(path)
        
    def _run(self, path):
        sear = Searcher(path)
        sear.run()
        router = Router('./source', sear.origin_path)
        router.run()
        

if __name__ == '__main__':
    '''
        Description:
            압축 해제한 파일들을 원래 폴더 경로로 정리해주는 프로그램(source 폴더를 만들어서 그 안에 넣어놔야 함)
            
            → python main.py "탐색할 폴더의 경로"
        Example:
            python main.py C:\Users\11700205\dev
    '''
    app = QApplication(sys.argv)
    bot = RAAS()
    sys.exit(app.exec_())

    
