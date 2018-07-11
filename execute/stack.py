import RPi.GPIO as GPIO
import time

GPIO.setwarnings(False)
GPIO.setmode(GPIO.BOARD)
GPIO.setup(19, GPIO.OUT)

#frequentie
stack = GPIO.PWM(19, 50)

#startpositie
stack.start(2.5)
time.sleep(0.5)

#180 degrees change
stack.ChangeDutyCycle(7.8)
time.sleep(0.5)

#back to 0 degrees
stack.ChangeDutyCycle(2.5)
time.sleep(0.5)

#exit
stack.stop()
GPIO.cleanup()
