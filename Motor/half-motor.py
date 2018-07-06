import RPi.GPIO as GPIO
import time
import sys
import os

pin = [11, 12, 13, 15, 16, 18]

if len(sys.argv) == 3:
    if (0 <= int(sys.argv[1]) and int(sys.argv[1]) < 6):
        motor = int(sys.argv[1])
        position = float(sys.argv[2])
    else:
        print(sys.argv[1] + "\n" + sys.argv[2])
        print("choose motor: (0-5)\n choose position: (2.5-7.5)")
        sys.exit()
else:
    print("no argument, choose motor: (0-5)\n choose position: (2.5-7.5")
    sys.exit()

GPIO.setwarnings(False)
GPIO.setmode(GPIO.BOARD)

GPIO.setup(pin[motor], GPIO.OUT)

m = GPIO.PWM(pin[motor], 50)
#start position
m.start(7)
time.sleep(0.35)
   
print("changed pos to " + str(position))
m.ChangeDutyCycle(position)
time.sleep(0.35)
#exit
m.stop()
GPIO.cleanup()

