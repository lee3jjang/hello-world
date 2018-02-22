import RPi.GPIO as GPIO
import time

leftF = 13
leftB = 12
rightF = 21
rightB = 20
enL = 6
enR = 26
#Ducty Cycle
pL = 50
pR = 50

GPIO.setmode(GPIO.BCM)
GPIO.setwarining(False)

GPIO.setup(leftF,GPIO.OUT)
GPIO.setup(rightF,GPIO.OUT)
GPIO.setup(enL,GPIO.OUT)
GPIO.setup(enR,GPIO.OUT)
# Pulsw Width Modulation(frequency = 500Hz)
PWML = GPIO.PWM(enL,500)
PWMR = GPIO.PWM(enR,500)
PWML.start(pL)
PWMR.start(pR)
GPIO.output(leftF,GPIO.HIGH)
GPIO.output(rightF,GPIO.HIGH)
#PWML.ChangeDutyCycle(0)
#PWML.ChangeDutyCycle(0)

if __name__=='__main__':
  
  try:
    while True:
      print("The Left Wheel is rotating!")
      time.sleep(1)
  except KeyboardInterrupt:
    GPIO.cleanup()
