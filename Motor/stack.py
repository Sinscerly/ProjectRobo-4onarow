import RPI.GPIO as GPIO
import time as T

GPIO.setwarnings(TRUE)
GPIO.setmode(GPIO.BOARD)
GPIO.setup(7, GPIO.OUT)

#frequentie
stack = GPIO.PWM(7, 50)

#startpositie
stack.start(2.5)

#180 degrees change
time.sleep(0.05)
stack.ChangeDutyCycle(7.5)

#wait
time.sleep(1)

#back to 0 degrees
stack.ChangeDutyCycle(2.5)
time.sleep(0.05)

#exit
stack.stop()
GPIO.cleanup()
