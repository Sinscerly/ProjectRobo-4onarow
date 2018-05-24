#!/usr/bin/python

'''
This example illustrates how to use cv.HoughCircles() function.

Usage:
    houghcircles.py [<image_name>]
    image argument defaults to ../data/board.jpg
'''

# Python 2/3 compatibility
from __future__ import print_function

import cv2 as cv
import numpy as np
import sys

def main():
    print(__doc__)

    try:
        fn = sys.argv[1]
    except IndexError:
        fn = "../data/board.jpg"

    src = cv.imread(fn, 1)
    img = cv.cvtColor(src, cv.COLOR_BGR2GRAY)
#   img = cv.medianBlur(img, 5)
    cimg = src.copy() # numpy function

    circles = cv.HoughCircles(img, cv.HOUGH_GRADIENT, 1, 10, np.array([]), 100, 30, 20, 30)

    # Check if circles have been found and only then iterate over these and add them to the image
    if circles is not None and len(circles): 
        print(circles)
        a, b, c = circles.shape
        for i in range(b):
            cv.circle(cimg, (circles[0][i][0], circles[0][i][1]), circles[0][i][2], (0, 0, 255), 3, cv.LINE_AA)
            cv.circle(cimg, (circles[0][i][0], circles[0][i][1]), 2, (0, 255, 0), 3, cv.LINE_AA)  # draw center of circle
        index = (i+1) 
    
	output = cimg
	array = read_circles(circles, index)
#Output graphic
	cv.imshow("detected circles", output)
	print("total amount of circles: " + str(index))
	print_circles(array, index, 140)
	print()
	if index > 41:
		order_array(array, index)
	print_circles(array, index, 1000)
	i = 2
	for i in range(i, 5):
		print(str(i))
    cv.imshow("source", src)
    cv.waitKey(0)

def read_circles(a_circles, i):
	data = [[0 for x in range(2)] for y in range(i)]
	for j in range(i):
		point_x = a_circles[0][j][0]
		point_y = a_circles[0][j][1]
		data[j][0] = point_x
		data[j][1] = point_y
#	print (str(data[0][0]))
	return data

def print_circles(array, i, high):
	print()
	for j in range(i):
		if array[j][0] < high:
			print("\tx:" + str(array[j][0]) + "\t\ty:" + str(array[j][1]))
	return 1

def order_array(array, length):
# gesorteerd op kollommen, van links naar rechts
	start_pos = 0
	for k in range(7):	
		for j in range(start_pos, 6 + start_pos):
			next_lowest = find_lowest_x(array, j, length)
			array = swapped(array, next_lowest, length, j)
		start_pos += 6
# nu sorteren op rijen, van laag naar hoog
	
	return array

def find_lowest_x(array, index, length):
	lowest = 10000
	nearest = 0
	for i in range(index, length):
		if array[i][0] < lowest:
			lowest = array[i][0]
	return lowest

def swapped(array, num, length, new_place):
	from_j = 0
	for j in range(length):
		if array[j][0] == num:
			from_j = j
	tmp_x = array[new_place][0]
	tmp_y = array[new_place][1]
	array[new_place][0] = array[from_j][0]
	array[new_place][1] = array[from_j][1]
	array[from_j][0] = tmp_x
	array[from_j][1] = tmp_y
	return array

if __name__ == '__main__':
	main()
