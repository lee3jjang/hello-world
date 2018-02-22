import RPi.GPIO as GPIO
import time

AIN1 = 12
ENA = 6
PA = 50
GPIO.setmode(GPIO.BCM)
GPIO.setwarining(False)
GPIO.setup(AIN1,0)
GPIO.setup(ENA,0)
PWMA = GPIO.PWM(ENA,500)
PWMA.start(PA)
PWMA.ChangeDutyCycle(50)
GPIO.output(AIN1,1)


if __name__=='__main__':
  
  try:
    while True:
      print("The Left Wheel is rotating!")
      time.sleep(1)
  except KeyboardInterrupt:
    GPIO.cleanup()
