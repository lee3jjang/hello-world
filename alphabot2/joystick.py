import RPi.GPIO as gpio
import time

CTR=7
A=8
B=9
C=10
D=11

gpio.setmode(gpio.BCM)
gpio.setwarnings(False)

gpio.setup(CTR,gpio.IN,gpio.PUD_UP)
gpio.setup(A,gpio.IN,gpio.PUD_UP)
gpio.setup(B,gpio.IN,gpio.PUD_UP)
gpio.setup(C,gpio.IN,gpio.PUD_UP)
gpio.setup(D,gpio.IN,gpio.PUD_UP)

i=0
while True:
  if i == 10:
    print("Loop Out")
    gpio.cleanup()
    break
    
  if gpio.input(CTR) == 0:
    print("Center")
  elif gpio.input(A) == 0:
    print("A")
  elif gpio.input(B) == 0:
    print("B")
  elif gpio.input(C) == 0:
    print("C")
  elif gpio.input(D) == 0:
    print("D")
  else:
    print("Nothing")
    
  i+=1
  time.sleep(1)  
