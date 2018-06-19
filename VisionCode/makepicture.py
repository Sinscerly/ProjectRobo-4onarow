#!/usr/bin/python

from __future__ import print_function

import cv2 as cv
import numpy as np
import sys
import os
import time

def main():
	#get current time to make a unique timestamp
    x_time = time.strftime("%Y%m%d_%H%M%S")
    pic_n = (x_time + ".jpg")
    #Picture will be saved in directory: pic
    if (os.path.isdir("pic") == False):
        os.system("mkdir pic")
	pic_n_loc = ("pic/" + pic_n)
    #make picture of the board
	try:
		os.system("raspistill -o " + pic_n_loc)
    if (os.path.exists(pic_n_loc) == False):
		sys.exit("Picture wasn't token")
    print("Picture is token, named: " + pic_n)
	#Get size of image
	size_pic = os.path.getsize(pic_n_loc)
	#For the convert command it is needed to have imagemagick to be installed.
    os.system("convert -resize 20% " + pic_n_loc + " " + pic_n_loc)
	if (size_pic == os.path.getsize(pic_n_loc) or size_pic < os.path.getsize(pic_n_loc)):
		sys.exit("Picture couldn't be resized, check if ImageMagick is installed")
	else
		print("Picture is resized to 20% of original")
	:

if __name__ == '__main__':
	main()
