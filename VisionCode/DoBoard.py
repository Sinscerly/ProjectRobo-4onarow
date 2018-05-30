#!/usr/bin/python

from __future__ import print_function

import cv2 as cv
import numpy as np
import sys
import os

def main():
        '''
	This code will filter out the board and will take cirlces then.
	'''
	print(__doc__)

	c_red = "red"
	c_yellow = "yellow"

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
	lower_blue = np.array([100, 0,0])
	upper_blue = np.array([200, 255,255])
        
        mask_blue = cv.inRange(hsv, lower_blue, upper_blue)
        res_blue = cv.bitwise_and(source, source, mask = cv.bitwise_not(mask_blue))
        cv.imshow("source", source)
        cv.imshow("mask", mask_blue)
        cv.imshow("res", res_blue)

        nonzero = cv.findNonZero(mask_blue)
        #print (nonzero)
        #print (nonzero[0][0][1])
        start = start_board(nonzero, 10)
        end = len(nonzero) -1
        y_low   = nonzero[start][0][1]
        y_high  = nonzero[end][0][1]
        x_low   = nonzero[start][0][0]
        x_high  = nonzero[end][0][0]
        crop_img = source[y_low:y_high, x_low:x_high]
        cv.imshow("crop", crop_img)
        print(x_high)
        print(y_high)

        res_blue = crop_img
        img = cv.cvtColor(res_blue, cv.COLOR_BGR2GRAY)
#   img = cv.medianBlur(img, 5)
        cimg = res_blue.copy() # numpy function

        circles = cv.HoughCircles(img, cv.HOUGH_GRADIENT, 1, 10, np.array([]), 100, 30, 22, 35)

# Check if circles have been found and only then iterate over these and add them to the image
        if circles is not None and len(circles): 
            print(circles)
            a, b, c = circles.shape
            for i in range(b):
                cv.circle(cimg, (circles[0][i][0], circles[0][i][1]), circles[0][i][2], (0, 0, 255), 3, cv.LINE_AA)
                cv.circle(cimg, (circles[0][i][0], circles[0][i][1]), 2, (0, 255, 0), 3, cv.LINE_AA)  # draw center of circle
            output = cimg
        cv.imshow("detected circles", output)

#the end -----------------------------------------------
	while True:
		k = cv.waitKey(5) & 0xFF
		if k == 27:
			break
	cv.destroyAllWindows()

def start_board(nonzero, more):
        more = 10
        for i in range(5000):
            x = nonzero[i][0][0] - more
            y = nonzero[i][0][1]
            for j in range(more*2):
                if(x + j == y):
                    print(nonzero[i][0])
                    return i
        print("Found nothing")
        return 0

if __name__ == '__main__':
	main()
