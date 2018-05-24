from __future__ import print_function

import cv2 
import numpy as np
import sys

if __name__ == '__main__':
    print(__doc__)

	green = np.uint8([[[0,255,0]]])
	
	hsv_green = cv2.cvtColor(green, cv2.COLOR_BGR2HSV)

	print hsv_green
