#!/usr/bin/python

from __future__ import print_function

import cv2 as cv
import numpy as np
import sys
import os
import time

def main():
    x_time = time.strftime("%Y%m%d_%H%M%S")
    pic_n = (x_time + ".jpg")
    
    #Picture is saved in pic
    pic_n_loc = ("pic/" + pic_n)

    if (os.path.isdir("pic") == False):
        os.system("mkdir pic")
    try:
        os.system("raspistill -o " + pic_n_loc)
    if (os.path.exists(pic_n_loc) == False):
        sys.exit("Picture wasn't token")
    print("Picture is token, named: " + pic_n)
	
	#For the convert command it is needed to have imagemagick to be installed.
    os.system("convert -resize 20% " + pic_n_loc + " " + pic_n_loc)
    print("Picture is resized to 20% of original")


if __name__ == '__main__':
	main()
