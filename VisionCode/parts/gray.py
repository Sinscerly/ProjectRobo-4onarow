#!/usr/bin/python

from __future__ import print_function

import cv2 as cv
import numpy as np
import sys
import os

if __name__ == '__main__':
	fn = "board.jpg"
	image = cv.imread(fn, 0)
	a = 90
	b = 120
	for j in range(6):
		y = b + (j * 60 + j)
		for i in range(7):
			x = a + (i * 71 - i)
			if i > 5:
				x = x - 2 * i - 4
			if j > 4:
				x = x + 2 * i + 4
			cv.line(image, (x,y), (x,y), (0,255,0), 5)
	cv.imshow('grey scale image',image)

	while True:
		k = cv.waitKey(5) & 0xFF
		if k == 27:
			break
	cv.destroyAllWindows()
