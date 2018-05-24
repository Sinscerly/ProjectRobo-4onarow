#!/usr/bin/python

from __future__ import print_function

import cv2 as cv
import numpy as np
import sys
import os

if __name__ == '__main__':
	print(__doc__)

	try:
		fn = sys.argv[1]
	except IndexError:
		fn = "board.jpg"
#while(1):
	src = cv.imread(fn)

	out = cv.medianBlur(src, 7)
	p_in = out
#till here reading photo
	hsv = cv.cvtColor(p_in, cv.COLOR_BGR2HSV)

	lower_red = np.array([0,175,125])
	upper_red = np.array([255,220,220])

	mask = cv.inRange(hsv, lower_red, upper_red)
	res = cv.bitwise_and(p_in,p_in, mask = mask)

#show image
	cv.imshow("source", src)
	cv.imshow("mask", mask)
	cv.imshow("rest", res)
	cv.imwrite("blackwhiteboard.jpg", mask)		
	while True:
		k = cv.waitKey(5) & 0xFF
		if k == 27:
			break

	cv.destroyAllWindows()
#fn.release()

