import RPi.GPIO as GPIO
import time
import sys
import os

def main():
    pin = [11, 12, 13, 15, 16, 18]
    
    GPIO.setwarnings(False)
    GPIO.setmode(GPIO.BOARD)

    for i in range(6):
        GPIO.setup(pin[i], GPIO.OUT)

    m0 = GPIO.PWM(pin[0], 50)
    m1 = GPIO.PWM(pin[1], 50)
    m2 = GPIO.PWM(pin[2], 50)
    m3 = GPIO.PWM(pin[3], 50)
    m4 = GPIO.PWM(pin[4], 50)
    m5 = GPIO.PWM(pin[5], 50)

    change_all_motors(3.5)
    time.sleep(10)
    change_all_motors(7)
    time.sleep(0.5)
#exit
    m0.stop()
    m1.stop()
    m2.stop()
    m3.stop()
    m4.stop()
    m5.stop()

    GPIO.cleanup()

def change_all_motors(position)
    m0.ChangeDutyCycle(position)
    m1.ChangeDutyCycle(position)
    m2.ChangeDutyCycle(position)
    m3.ChangeDutyCycle(position)
    m4.ChangeDutyCycle(position)
    m5.ChangeDutyCycle(position)
    return 1

if __name__ == '__main__':
    main()
