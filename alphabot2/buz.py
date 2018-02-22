import RPi.GPIO as GPIO
import time

GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)
BUZ = 4

GPIO.setup(BUZ,GPIO.OUTPUT)
GPIO.output(BUZ,GPIO.HIGH)

try:
	while True:
		time.sleep(1)
except KeyboardInterrupt:
	GPIO.cleanup()
