#!/usr/bin/python

from __future__ import print_function

import cv2 as cv
import numpy as np
import sys
import os

def main():
        '''
        This function will find all the cricles it can find and print them.	
        '''
	print(__doc__)

	c_red = "red"
	c_yellow = "yellow"

	try:
		fn = sys.argv[1]
	except IndexError:
		fn = "board.jpg"
        src = cv.imread(fn)
        #src = cv.bitwise_not(src)
        	
        img = cv.medianBlur(cv.cvtColor(src, cv.COLOR_BGR2GRAY), 3)
	cimg = src.copy() # numpy function
        circles = cv.HoughCircles(img, cv.HOUGH_GRADIENT, 1, 10, np.array([]), 30, 40, 20, 30)
	# Check if circles have been found and only then iterate over these and add them to the image
        found_cir = src
	if circles is not None and len(circles): 
	    print(circles)
	    a, b, c = circles.shape
	    for i in range(b):
                cv.circle(cimg, (circles[0][i][0], circles[0][i][1]), circles[0][i][2], (0, 0, 255), 3, cv.LINE_AA)
	        cv.circle(cimg, (circles[0][i][0], circles[0][i][1]), 2, (0, 255, 0), 3, cv.LINE_AA)  
	    found_cir = cimg
        cv.imshow("Circels", found_cir)

        #End the program with ESC
	while True:
		k = cv.waitKey(5) & 0xFF
		if k == 27:
			break
	cv.destroyAllWindows()

if __name__ == '__main__':
	main()
