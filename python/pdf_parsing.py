import pyautogui as gui
import time
import pyperclip

position_library = {
    '83':(647,317,1277,1004)
}

def macro(page):
    page = str(page)
    x1,y1,x2,y2 = position_library[page]
    gui.click(770,1180)                        # pdf 파일 버튼 누르기
    gui.hotkey('ctrl','shift','n')             # 페이지 찾아가기 창 띄우기
    gui.typewrite(page)                        # 찾아갈 페이지 입력
    gui.press('enter')                         # 엔터치기
    gui.moveTo(x1,y1)                          # 드래그 시작점
    gui.dragTo(x2,y2, duration=0.25)           # 드래그
    gui.hotkey('ctrl','c')                     # 복사
    gui.click(463,1180)                        # excel 파일 버튼 누르기
    gui.press(['alt','e','s','enter'])         # 붙여넣기
    gui.hotkey('ctrl','down')                  # 아래로 이동 (1)
    gui.press('down')                          # 아래로 이동 (2) 
    


macro(83)
text = pyperclip.paste()
data = text.split('\r\n')
data
