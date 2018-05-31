#!/usr/bin/python

from __future__ import print_function

import cv2 as cv
import numpy as np
import sys
import os

def main():
        '''
	This code crops the board.
	'''
	print(__doc__)

	try:
		fn = sys.argv[1]
	except IndexError:
		fn = "board.jpg"
	source = cv.imread(fn)
	print (source.shape)
#Blur the image
	blur = cv.medianBlur(source, 5)
	blur = source
#make a HSV image from the blurred picture
	hsv = cv.cvtColor(blur, cv.COLOR_BGR2HSV)
#HSV. H: Hue, S: Saturation, V: Value
#lower_red lowest values.	H:  0, S:90, V:0
#upper_red highest values	H:20, S:255, V:255
	lower_blue = np.array([104, 15,90])
	upper_blue = np.array([150, 255,255])
        
        mask_blue = cv.inRange(hsv, lower_blue, upper_blue)
        res_blue = cv.bitwise_and(source, source, mask = cv.bitwise_not(mask_blue))
        #cv.imshow("source", source)
        #cv.imshow("mask", mask_blue)
        cv.imshow("res", res_blue)
#Use of nonzero to get 0 and 1 values. To get the start of the board.
        nonzero = cv.findNonZero(mask_blue)
        start = start_board(nonzero, 10)
        x_low   = nonzero[start_board(nonzero, 10)][0][0]
        y_low   = nonzero[start_board(nonzero, 10)][0][1]
        x_high  = nonzero[(len(nonzero)-1)][0][0]        
        y_high  = nonzero[(len(nonzero)-1)][0][1]
#Crop the img to the desired format. First you give the lowest y coordinate then the end y. Same for x.
        crop_source     = source[y_low:y_high, x_low:x_high]
        crop_mask_blue  = mask_blue[y_low:y_high, x_low:x_high]
#Show the images.
        cv.imshow("crop"            ,crop_source)
        cv.imshow("Crop Mask Blue"  ,crop_mask_blue)
        cv.imwrite("cropped.jpg"    ,crop_source)
        cv.imwrite("cropped_mask.jpg" ,crop_mask_blue)
#the end -----------------------------------------------
	while True:
		k = cv.waitKey(5) & 0xFF
		if k == 27:
			break
	cv.destroyAllWindows()

#function to find for our picture the start of the board. This function is situational written and will probably only work for us.
def start_board(nonzero, more):
        more = 10
        for i in range(5000):
            x = nonzero[i][0][0] - more
            y = nonzero[i][0][1]
            for j in range(more*2):
                if(x + j == y):
                    #print(nonzero[i][0])
                    return i
        print("Found nothing, error: start_board, cannot find two points that are close enough to each other")
        return 0

if __name__ == '__main__':
	main()
