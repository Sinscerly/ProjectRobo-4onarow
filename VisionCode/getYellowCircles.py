#!/usr/bin/python
'''
This code will filter out all colors except RED
Then it will search for circles and will find only the RED circles.
'''
from __future__ import print_function

import cv2 as cv
import numpy as np
import sys
import os

if __name__ == '__main__':
	print(__doc__)

	color = "yellow"

	try:
		fn = sys.argv[1]
	except IndexError:
		fn = "board.jpg"
#Read/import the picture
	source = cv.imread(fn)
#Blur the image
	blur = cv.medianBlur(source, 7)
#make a HSV image from the blurred picture
	hsv = cv.cvtColor(blur, cv.COLOR_BGR2HSV)
#Values for the RED filter
#HSV. H: Hue, S: Saturation, V: Value
#lower_red lowest values
#upper_red highest values
#Yellow values
#H: 20, S:190, V: 20
#H: 30, S:255, V:255
	lower_yellow = np.array([20,190,20])
	upper_yellow = np.array([30,255,255])
#apply the filter ranges
	mask_yellow = cv.inRange(hsv, lower_yellow, upper_yellow)
#show the color image with mask.
	res_yellow = cv.bitwise_and(blur, blur, mask = mask_yellow)
#show image
	cv.imshow("source", source)
	cv.imshow("mask-black/white", mask_yellow)
	cv.imshow("rest-color", res_yellow)
	
	out = mask_yellow
#save the black/white image of the board where only the RED disc are visible
	save = "REDblackwhiteboard.jpg"
	cv.imwrite(save, out)
#detect the circles 
	src = cv.imread(save)
#	img = cv.cvtColor(src, cv.COLOR_BGR2GRAY)
	img = cv.medianBlur(cv.cvtColor(src, cv.COLOR_BGR2GRAY), 5)
	cimg = src.copy() # numpy function

	circles = cv.HoughCircles(img, cv.HOUGH_GRADIENT, 1, 10, np.array([]), 100, 10, 20, 45)

	# Check if circles have been found and only then iterate over these and add them to the image
	if circles is not None and len(circles): 
		print(circles)
		a, b, c = circles.shape
		for i in range(b):
		    cv.circle(cimg, (circles[0][i][0], circles[0][i][1]), circles[0][i][2], (0, 0, 255), 3, cv.LINE_AA)
		    cv.circle(cimg, (circles[0][i][0], circles[0][i][1]), 2, (0, 255, 0), 3, cv.LINE_AA)  # draw center of circle
		cv.imshow("yellow circles", cimg)
		print ('Total of', color, 'circles is', (i+1))
#End the program with ESC
	while True:
		k = cv.waitKey(5) & 0xFF
		if k == 27:
			break
	cv.destroyAllWindows()
