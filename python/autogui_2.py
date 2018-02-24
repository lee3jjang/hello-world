import pyautogui as gui
import matplotlib.pyplot as plt
#gui.click(6,29)
#gui.doubleClick(209,91)
#gui.moveTo(119,88)
#gui.dragTo(287,146)
#gui.scroll(-100)
im = gui.screenshot()
#plt.imshow(im)
#plt.show()
#plt.close()
gui.click(120,400)
gui.typewrite('gui.typewrite("hello world!")')
#print(gui.position())
