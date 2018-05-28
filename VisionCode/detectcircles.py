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

	if index > 41:
		order_array(array, index)
	print_circles(array, index)

	make_grid(array)

    cv.imshow("source", src)
    cv.waitKey(0)

def read_circles(a_circles, i):
#Conferting the array from circles to what we need
	data = [[0 for x in range(2)] for y in range(i)]
	for j in range(i):
		point_x = a_circles[0][j][0]
		point_y = a_circles[0][j][1]
		data[j][0] = point_x
		data[j][1] = point_y
	return data

def print_circles(array, top):
#Function for printing the new created array. Splitted in COLLUMS.
	print()
	x = 0
	for index in range(top):
		print("\tx:" + str(array[index][0]) + "\t\ty:" + str(array[index][1]))
		x += 1
		if x > 5:
			print()
			x = 0
	print("total amount of circles: " + str(top))
	return 1

def order_array(array, length):
#Sorting the COLLUMS, the X positions from LOW to HIGH
	start_pos = 0
	for k in range(7):	
		for j in range(start_pos, 6 + start_pos):
			next_lowest = find_lowest_x(array, j, length)
			array = swapp(array, next_lowest, length, j, 0, 0)
		start_pos += 6
#Sorting the ROWS, the Y positions from HIGH to LOW
	start_pos 	= 0
	end_pos 	= 6
	for k in range(7):
		for j in range(start_pos, end_pos):
			next_highest = find_highest_y(array, j, end_pos)
			array = swapp(array, next_highest, length, j, 1, end_pos)
		start_pos 	+= 6
		end_pos 	+= 6
	return array

def find_lowest_x(array, index, length):
#Finds the next lowest in the array that isn't been processed yet.
	lowest = 10000
	for i in range(index, length):
		if array[i][0] < lowest:
			lowest = array[i][0]
	return lowest
def find_highest_y(array, index, length):
#Finds the next highest in the array that isn't been processed yet.
	highest = 0
	for i in range(index, length):
    	if array[i][1] > highest:
			highest = array[i][1]
	return highest

def swapp(array, num, length, new_place, x_or_y, end_pos):
	from_j = 0
	if x_or_y == 1:
		length = end_pos
	for j in range(length):
        if array[j][x_or_y] == num:
			from_j = j
	tmp_x = array[new_place][0]
	tmp_y = array[new_place][1]
	array[new_place][0] = array[from_j][0]
	array[new_place][1] = array[from_j][1]
	array[from_j][0] = tmp_x
	array[from_j][1] = tmp_y
	return array

def make_grid(array):
	grid = [[0 for x in range(6)] for y in range(7)]
	grid[6][5] = 1
	print(grid[6][5])

if __name__ == '__main__':
	main()
