import pyautogui as gui
import time

width, height = gui.size()
gui.moveTo(500,300,duration=0.25)
gui.moveRel(150,-250, duration=0.25)

try:
    while True:
        print(gui.position())
        time.sleep(0.1)
except KeyboardInterrupt:
    print('\nDone.')
