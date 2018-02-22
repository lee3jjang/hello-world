import RPi.GPIO as gpio
import time

DL=16
DR=19

gpio.setmode(gpio.BCM)
gpio.setwarnings(False)

gpio.setup(DL,gpio.IN,gpio.PUD_UP)
gpio.setup(DR,gpio.IN,gpio.PUD_UP)

i=0
while True:
  if i == 10:
    print("Loop Out")
    gpio.cleanup()
    break
    
  if gpio.input(DL) == 0:
    print("Left Obstacle")
  else:
    print("Nothing Left")
    
  if gpio.input(DR) == 0:
    print("Right Obstacle")
  else:
    print("Nothing Right") 
    
  i+=1
  time.sleep(1) 
