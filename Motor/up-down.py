import RPi.GPIO as GPIO
import time
import sys
import os

pos = [7, 3.7, 3.3]

def main():
    move(pos[1])

    time.sleep(2)

    move(pos[0])
        
def move(pos):
    for i in range(6):
        xpos = pos
        if(i == 2):
            if(pos != 7):
                xpos = pos[2]
        os.system("python half-motor.py " + str(i) + " " + str(xpos))
    return 1



if __name__ == '__main__':
    main()


