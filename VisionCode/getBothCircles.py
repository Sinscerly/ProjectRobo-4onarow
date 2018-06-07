#!/usr/bin/python

from __future__ import print_function

import cv2 as cv
import numpy as np
import sys
import os

def main():
	'''
	This code will filter out all colors except RED
	Then it will search for circles and will find only the RED circles.
	'''
	print(__doc__)
        d_hc = [30, 10, 20, 30]
	c_red = "red"
	c_yellow = "yellow"

	try:
		fn = sys.argv[1]
	except IndexError:
		fn = "board.jpg"
#Read/import the picture
	source = cv.imread(fn)
	print (source.shape)
#Blur the image
	blur = cv.medianBlur(source, 5)
	
#make a HSV image from the blurred picture
	hsv = cv.cvtColor(blur, cv.COLOR_BGR2HSV)
#HSV. H: Hue, S: Saturation, V: Value
#lower_red lowest values.	H:  0, S:90, V:0
#upper_red highest values	H:20, S:255, V:255
	lower_red = np.array([0, 71,0])
	upper_red = np.array([15, 255,255])

        lower_red2 = np.array([177, 0, 0])
        upper_red2 = np.array([255, 255, 255])
#lower_yellow lowest values H: 20, S:190, V: 20
#upper_yellow lowest values H: 30, S:255, V:255
	lower_yellow = np.array([20,190,20])
	upper_yellow = np.array([30,255,255])
#apply the filter ranges
	mask_red_low	= cv.inRange(hsv, lower_red, upper_red)
        mask_red_high   = cv.inRange(hsv, lower_red2, upper_red2)
	mask_yellow     = cv.inRange(hsv, lower_yellow, upper_yellow)
#output for erode
	red_mask       = cv.add(mask_red_low, mask_red_high)
        #cv.imshow("mask_yellow", mask_yellow)
        yellow_mask 	= mask_yellow
#erode for better quality
        kernel = np.ones((5,5), np.uint8)
        red_erode       = cv.erode(red_mask, kernel, iterations = 4)
        yellow_erode    = cv.erode(yellow_mask, kernel, iterations = 3)
        cv.imshow("rip", yellow_erode)
        for i in range(4): 
                red_erode = cv.dilate(red_erode, kernel, iterations = 1)
                red_erode = cv.bitwise_and(red_erode, red_erode, mask = red_mask)
                yellow_erode = cv.dilate(yellow_erode, kernel, iterations = 1)
                yellow_erode = cv.bitwise_and(yellow_erode, yellow_mask, mask = yellow_mask)
#output mask for futher work
        out_red     = red_erode
        out_yellow  = yellow_erode
#show the color image with mask.
	res_red 	= cv.bitwise_and(blur, blur, mask = out_red)
	res_yellow 	= cv.bitwise_and(blur, blur, mask = out_yellow)
#show image
	cv.imshow("source", source)
#	cv.imshow("mask_red-black/white", 		mask_red)
#	cv.imshow("mask_yellow-black/white", 	mask_yellow)	
#	cv.imshow("rest-red", 		res_red)
#	cv.imshow("rest-yellow", 	res_yellow)
	
#save the black/white image of the board, for RED and YELLOW.
	save_red = "REDblackwhiteboard.jpg"
	save_yellow = "YELLOWblackwhiteboard.jpg"
	cv.imwrite(save_red, out_red)	
        cv.imwrite(save_yellow, out_yellow)
#detect the red circles 
	src_r = cv.imread(save_red)	
	img_r = cv.medianBlur(cv.cvtColor(src_r, cv.COLOR_BGR2GRAY), 5)
	cimg_r = src_r.copy() # numpy function
	circles = cv.HoughCircles(img_r, cv.HOUGH_GRADIENT, 1, 10, np.array([]), d_hc[0], d_hc[1], d_hc[2], d_hc[3])
	# Check if circles have been found and only then iterate over these and add them to the image
        cir_red = src_r
	if circles is not None and len(circles): 
		print(circles)
		a, b, c = circles.shape
		for i in range(b):
		    cv.circle(cimg_r, (circles[0][i][0], circles[0][i][1]), circles[0][i][2], (0, 0, 255), 3, cv.LINE_AA)
		    cv.circle(cimg_r, (circles[0][i][0], circles[0][i][1]), 2, (0, 255, 0), 3, cv.LINE_AA)  
		cir_red = cimg_r
		index_red = (i+1)
#detect the yellow circles
	src_y = cv.imread(save_yellow)	
	img_y = cv.medianBlur(cv.cvtColor(src_y, cv.COLOR_BGR2GRAY), 5)
	cimg_y = src_y.copy() # numpy function
	circles2 = cv.HoughCircles(img_y, cv.HOUGH_GRADIENT, 1, 10, np.array([]), d_hc[0], d_hc[1], d_hc[2], d_hc[3])
	cir_yellow = src_y
        if circles2 is not None and len(circles2): 
		print(circles2)
		a, b, c = circles2.shape
		for i in range(b):
		    cv.circle(cimg_y, (circles2[0][i][0], circles2[0][i][1]), circles2[0][i][2], (0, 255, 255), 3, cv.LINE_AA)
		    cv.circle(cimg_y, (circles2[0][i][0], circles2[0][i][1]), 2, (0, 255, 0), 3, cv.LINE_AA)  
		cir_yellow = cimg_y
		index_yellow = (i+1)
#Read out data from circles
#		point_x = circles2[0][0][0]
#		point_y = circles2[0][0][1]
#		point_r = circles2[0][0][2]
#		print ("x:" + str(point_x) + " y:" + str(point_y))

#make new arrays for both colors.
		array1 = read_circles(circles, 	index_red)
		array2 = read_circles(circles2, index_yellow)
#Show found circels and output on display
	cv.imshow("detected circles red", 	cir_red)
	cv.imshow("detected circles yellow", 	cir_yellow)
        cv.imshow("all", cv.add(cir_red, cir_yellow))
	print_arrays(array1, array2, index_red, index_yellow)
#End the program with ESC
	while True:
		k = cv.waitKey(5) & 0xFF
		if k == 27:
			break
	cv.destroyAllWindows()

#Functions ---------------------------------------------------------------------------
def read_circles(a_circles, i):
	data = [[0 for x in range(2)] for y in range(i)]
	for j in range(i):
		point_x = a_circles[0][j][0]
		point_y = a_circles[0][j][1]
		data[j][0] = point_x
		data[j][1] = point_y
#	print (str(data[0][0]))
	return data

def print_arrays(array_r, array_y, index_r, index_y):
	print()
	print("Colortype: RED. \tDiscs: " + str(index_r))
	print_circles(array_r, index_r)
	print()
	print("Colortype: Yellow. \tDiscs: " + str(index_y))
	print_circles(array_y, index_y)
	print("Total amount of discs: " + str(index_r + index_y))
        if index_r > index_y:
		print("Yellow is on set")
	else:
		print("Red is on set")
	return 1

def print_circles(array, i):
	print()
	for j in range(i):
		print("\tx:" + str(array[j][0]) + "\t\ty:" + str(array[j][1]))
	return 1


if __name__ == '__main__':
	main()
