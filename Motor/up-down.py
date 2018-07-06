import RPi.GPIO as GPIO
import time
import sys
import os

pin = [11, 12, 13, 15, 16, 18]
pos = [7, 3.5]

def main():
    move(0)

    time.sleep(10)

    move(1)
        
def move(pos):
    for i in range(6):
        os.system("half-motor.py " + str(pin[i]) + " " + pos



if __name__ == '__main__':
    main()


