#!/usr/bin/python

from __future__ import print_function

import cv2 as cv
import numpy as np
import sys
import os
import time

def main():
    time_name = time.strftime("%Y%m%d_%H%M%S")
    time_name = (time_name + ".jpg")
    print(time_name)
    
    time_name = ("pic/" + time_name)

    os.system("raspistill -o " + time_name)
    


if __name__ == '__main__':
	main()
