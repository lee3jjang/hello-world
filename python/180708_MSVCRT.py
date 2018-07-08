import msvcrt
import gym

LEFT = 0
DOWN = 1
RIGHT = 2
UP = 3

while(True):
    key = msvcrt.getch()
    maps = {b'w': UP, b's': DOWN, b'a': LEFT, b'd': RIGHT}
    command = maps.get(key)
    print(command)
    if(key==b'\x1b'):
        print('== GAME OVER ==')
        break