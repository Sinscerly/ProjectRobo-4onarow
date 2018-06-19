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

	color = "red"

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
    #0,175,125
    #255,220,220
        lower_red = np.array([0, 71 ,0])
        upper_red = np.array([15, 255 ,255])
        
        mask_red_1 = cv.inRange(hsv, lower_red, upper_red)

        lower_red = np.array([177, 0, 0])
        upper_red = np.array([255, 255, 255])
#apply the filter ranges
        mask_red_2 = cv.inRange(hsv, lower_red, upper_red)

        #mask_red = cv.bitwise_and(blur, blur, mask = mask_red1)
        mask_red3 = cv.bitwise_and(blur, blur, mask = mask_red_2)
#show the color image with mask.
	res_red = cv.bitwise_and(blur, blur, mask = mask_red_1)
        
        res = cv.add(res_red, mask_red3)
#show image
	cv.imshow("source", source)
	cv.imshow("mask-black/white", mask_red_1)
        cv.imshow("mask-black/white2", mask_red_2)
	cv.imshow("rest-color", res_red)
        cv.imshow("hi" , res)

	out = cv.add(mask_red_1, mask_red_2)
        #out = mask_red_1
#save the black/white image of the board where only the RED disc are visible
	save = "REDblackwhiteboard.jpg"
	cv.imwrite(save, out)
#detect the circles 
	src = cv.imread(save)
	img = cv.cvtColor(src, cv.COLOR_BGR2GRAY)
	img = cv.medianBlur(img, 5)
	cimg = src.copy() # numpy function

	circles = cv.HoughCircles(img, cv.HOUGH_GRADIENT, 1, 10, np.array([]), 100, 10, 20, 45)

	# Check if circles have been found and only then iterate over these and add them to the image
	if circles is not None and len(circles): 
		print(circles)
		a, b, c = circles.shape
		for i in range(b):
		    cv.circle(cimg, (circles[0][i][0], circles[0][i][1]), circles[0][i][2], (0, 0, 255), 3, cv.LINE_AA)
		    cv.circle(cimg, (circles[0][i][0], circles[0][i][1]), 2, (0, 255, 0), 3, cv.LINE_AA)  # draw center of circle
		cv.imshow("detected circles", cimg)
		print ('Total of', color, 'circles is', (i+1))
#End the program with ESC
	while True:
		k = cv.waitKey(5) & 0xFF
		if k == 27:
			break
	cv.destroyAllWindows()
