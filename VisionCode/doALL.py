#!/usr/bin/python

from __future__ import print_function

import cv2 as cv
import numpy as np
import sys
import os
import time

def main():
    print(__doc__)
    
#Parameters for HoughCircles detection for board positions:
    p_hc  = [15, 65, 20, 35]
#Parameters for HoughCircles detection for the color masks:
    cf_hc = [30, 10, 20, 30]
    c_red = "red"
    c_yellow = "yellow"
    graphics = False
    print_arr = False
    del_pic = True
#FOR DEBUG ENABLE print_arr TO COMPARE THE ARRAYS

    if len(sys.argv) == 2:
        if sys.argv[1] == 0:
            del_pic = False
            print("Picture will not be deleted")
        else:
            if sys.argv[1] != 1:
                print("Syntax: \t python doALL.py <0-1> \n <0-1> remove picture")
        
#---------------------- Make the picture --------------------------------
#get current time to make a unique timestamp
    x_time = time.strftime("%Y%m%d_%H%M%S")
    pic_n = (x_time + ".jpg")
#Picture will be saved in directory: pic
    if (os.path.isdir("pic") == False):
        os.system("mkdir pic")
    pic_n_loc = ("pic/" + pic_n)
    
#make picture of the board
    try:
        os.system("raspistill -o " + pic_n_loc)
    except:
        print("Take a look at the 'raspistill' command")
    if (os.path.exists(pic_n_loc) == False):
        sys.exit("Picture wasn't token")
    print("Picture is token, named: " + pic_n)
    
#Get size of image
    size_pic = os.path.getsize(pic_n_loc)
#For the convert command it is needed to have imagemagick to be installed.
    os.system("convert -resize 20% " + pic_n_loc + " " + pic_n_loc)
    if (size_pic == os.path.getsize(pic_n_loc) or size_pic < os.path.getsize(pic_n_loc)):
        sys.exit("Picture couldn't be resized, check if ImageMagick is installed")
    print("Picture is resized to 20% of original")

#--------------------------- IMPORT -------------------------------------
#Read/import the pictureDocumentbeheer: la
    source = cv.imread(pic_n_loc)
#print (source.shape)
    if graphics:
        cv.imshow("IMPORT", source)
#Blur the image
    blur = cv.medianBlur(source, 5)
#make a HSV image from the blurred picture
    hsv_source  = cv.cvtColor(source, cv.COLOR_BGR2HSV)
    hsv_blur    = cv.cvtColor(blur,   cv.COLOR_BGR2HSV)

#-----------------------------------------------------------------------
#------------------ Detect Circles -------------------------------------
# Here we look for all the positions a disc of the game can be. 
# So we can make a grid with this information and compare the place of the colored discs we have found
    img_dc = cv.cvtColor(source, cv.COLOR_BGR2GRAY)
    #img_dc = cv.medianBlur(img, 5)
    output_dc = source.copy() # numpy function
    circles_dc = cv.HoughCircles(img_dc, cv.HOUGH_GRADIENT, 1, 10, np.array([]), p_hc[0], p_hc[1], p_hc[2], p_hc[3])
    #Check if circles have been found and only then iterate over these and add them to the image
    index_dc = 0
    if circles_dc is not None and len(circles_dc): 
        print(circles_dc)
        a, b, c = circles_dc.shape
        for i in range(b):
            cv.circle(output_dc, (circles_dc[0][i][0], circles_dc[0][i][1]), circles_dc[0][i][2], (0, 0, 255), 3, cv.LINE_AA)
            cv.circle(output_dc, (circles_dc[0][i][0], circles_dc[0][i][1]), 2, (0, 255, 0), 3, cv.LINE_AA)  # draw center of circle
        index_dc = (i+1)
    if index_dc != 42:
        print("Picture will be renamed")
        os.system("mv " + pic_n_loc + " " + (pic_n_loc + "-false"))
    if index_dc == 0:
        print("The index_DetectedCircles is 0, so there were no circles found.")
        print("The HoughCircle detection could not find any circles... pls, check the picture!")
        #show output why there are no circles found.
        if graphics:
            show_circles(output_dc)
        #stop program
        sys.exit("Error")
    else:
        array_dc = read_circles(circles_dc, index_dc)
        if index_dc != 42:
            print("The index is: " + str(index_dc))
            print("The index from detect circles is not 42 so we cannot find the optimal locations at the board.")
            #show output why there are to less or may circles found.
            if graphics:
                show_circles(output_dc)
            #stop program
            sys.exit("Error")

#-----------------------------------------------------------------------
#------------------ Color Filters --------------------------------------
#HSV. H: Hue, S: Saturation, V: Value
#Back Up
    #lower_red1     F:  0,  71,   0
    #upper_red1     F: 20, 255, 255

    #lower_red2     F:177,   0,   0
    #upper_red2     F:255, 255, 255

    #lower_yellow   F: 20, 190,  20
    #upper_yellow   F: 30, 255, 255
#Filters:
    #Red spectre low
	lower_red = np.array([0, 71,0])
	upper_red = np.array([15, 255,255])
    #Red spectre high
    lower_red2 = np.array([177, 0, 0])
    upper_red2 = np.array([255, 255, 255])
    #Yellow spectre
    lower_yellow = np.array([10,140,20])
    upper_yellow = np.array([100,255,255])
#Make masks from the filter ranges
    mask_red_low	= cv.inRange(hsv_blur, lower_red, upper_red)
    mask_red_high   = cv.inRange(hsv_blur, lower_red2, upper_red2)
    mask_yellow     = cv.inRange(hsv_blur, lower_yellow, upper_yellow)
    #Combine both red masks for one big red mask
    mask_red        = cv.add(mask_red_low, mask_red_high)
#erode for better quality
    kernel = np.ones((5,5), np.uint8)
    red_erode       = cv.erode(mask_red,    kernel, iterations = 4)
    yellow_erode    = cv.erode(mask_yellow, kernel, iterations = 4)
    #Debug erode:
    #cv.imshow("rip_red",    red_erode)
    #cv.imshow("rip_yellow", yellow_erode)
    for i in range(4): 
        red_erode = cv.dilate(red_erode, kernel, iterations = 1)
        red_erode = cv.bitwise_and(red_erode, red_erode, mask = mask_red)
        yellow_erode = cv.dilate(yellow_erode, kernel, iterations = 1)
        yellow_erode = cv.bitwise_and(yellow_erode, mask_yellow, mask = mask_yellow)
#output mask for futher work
    out_red     = red_erode
    out_yellow  = yellow_erode
#show the color image with mask.
    res_red 	= cv.bitwise_and(blur, blur, mask = out_red)
    res_yellow 	= cv.bitwise_and(blur, blur, mask = out_yellow)

#-------------------------------- SHOW OUTPUT / Backup -----------------------------
#show image
    if graphics:
#	    cv.imshow("source", source)
#	    cv.imshow("mask_red-black/white", 	mask_red)
        cv.imshow("mask_yellow-black/white", 	mask_yellow)	
#	    cv.imshow("rest-red", 		res_red)
        cv.imshow("rest-yellow", 	res_yellow)
        cv.imshow("detected circles", circles_dc)

#save the black/white image of the board, for RED and YELLOW.
    save_red    = "pic/" + "REDblackwhiteboard.jpg"
    save_yellow = "pic/" + "YELLOWblackwhiteboard.jpg"
    cv.imwrite(save_red, out_red)	
    cv.imwrite(save_yellow, out_yellow)

#--------------------------------- Detecting the COLOR circles from the masks ------------------------
#detect the red circles 
    src_r = cv.imread(save_red)
    if del_pic:
        #remove the save_red image
        os.system("rm " + save_red)
    img_r = cv.medianBlur(cv.cvtColor(src_r, cv.COLOR_BGR2GRAY), 5)
    cimg_r = src_r.copy() # numpy function
    circles_r = cv.HoughCircles(img_r, cv.HOUGH_GRADIENT, 1, 10, np.array([]), cf_hc[0], cf_hc[1], cf_hc[2], cf_hc[3])
    # Check if circles have been found and only then iterate over these and add them to the image
    cir_red = src_r
    if circles_r is not None and len(circles_r): 
        print(circles_r)
        a, b, c = circles_r.shape
        for i in range(b):
            cv.circle(cimg_r, (circles_r[0][i][0], circles_r[0][i][1]), circles_r[0][i][2], (0, 0, 255), 3, cv.LINE_AA)
            cv.circle(cimg_r, (circles_r[0][i][0], circles_r[0][i][1]), 2, (0, 255, 0), 3, cv.LINE_AA)  
            cir_red = cimg_r
        index_red = (i+1)
#detect the yellow circles
    src_y = cv.imread(save_yellow)
    if del_pic:
        #remove the save_yellow image
        os.system("rm " + save_yellow)	
    img_y = cv.medianBlur(cv.cvtColor(src_y, cv.COLOR_BGR2GRAY), 5)
    cimg_y = src_y.copy() # numpy function
    circles_y = cv.HoughCircles(img_y, cv.HOUGH_GRADIENT, 1, 10, np.array([]), cf_hc[0], cf_hc[1], cf_hc[2], cf_hc[3])
    cir_yellow = src_y
    if circles_y is not None and len(circles_y): 
        print(circles_y)
        a, b, c = circles_y.shape
        for i in range(b):
            cv.circle(cimg_y, (circles_y[0][i][0], circles_y[0][i][1]), circles_y[0][i][2], (0, 255, 255), 3, cv.LINE_AA)
            cv.circle(cimg_y, (circles_y[0][i][0], circles_y[0][i][1]), 2, (0, 255, 0), 3, cv.LINE_AA)  
        cir_yellow = cimg_y
        index_yellow = (i+1)
#make new arrays for both colors.
    array_red       = read_circles(circles_r,    index_red)
    array_yellow    = read_circles(circles_y,    index_yellow)
#--------------------------- OUTPUT GRAPICS -----------------------------
#Show found circels and output on display
    if graphics:
            #cv.imshow("detected circles red", 	cir_red)
	    #cv.imshow("detected circles yellow", 	cir_yellow)

        #Combined image of circles RED and YELLOW
        cv.imshow("all", cv.add(cir_red, cir_yellow))

    if index_red == 0 and index_yellow == 0:
        #stop program
        sys.exit("No discs have been found at color masks. PLS check image, if there are any discs on it.")
    elif (index_red + index_yellow) > 42:
        #stop program
        print("Number of red discs: " + str(index_red) + ". \tNumber of yellow discs: " + str(index_yellow) + ".")
        print("NUmber of total discs: " + str(index_red + index_yellow))
        sys.exit("There have been found more discs on the board then possible places on the board")
    order_array_for_grid(array_dc, index_dc)
    if print_arr:
        print_arrays(array_dc, array_red, index_red, array_yellow, index_yellow)
    
    grid = fill_and_print_grid(array_dc, array_red, index_red, array_yellow, index_yellow)

#--------------------------- OUPUT GRID ------------------------------
#Grid to file.txt
    grid_n = (x_time + ".txt")
    #Grid will be saved in directory: grid
    if (os.path.isdir("grid") == False):
        os.system("mkdir grid")
        print("Directory grid is created to store grid.txt files")
    grid_n_loc = ("grid/" + grid_n)
    
    file = open(grid_n_loc, "w")
    for y in range(5,-1,-1):
        this_print = ""
        for x in range(7):
            #0 = empty
            #1 = Red
            #2 = Yellow
            this_print = this_print + str(grid[x][y])
        file.write(this_print + "\n")
    file.close()
   
    
#Delete picture, not needed any more
    if del_pic:
        os.system("rm " + pic_n_loc)
        print("\nRemoved token picture, to keep picture add argument: 0")

#End the program with ESC
        if graphics:
            while True:
	        k = cv.waitKey(5) & 0xFF
	        if k == 27:
		    break
	    cv.destroyAllWindows()

#--------------------------------------------------------------------
#--------------------------------------------------------------------
#-------------------------- END OF MAIN -----------------------------
#-------------------------- END OF MAIN -----------------------------
#-------------------------- END OF MAIN -----------------------------
#--------------------------------------------------------------------
#--------------------------------------------------------------------

# -------------------------- Functions ------------------------------
def fill_and_print_grid(array_dc, array_red, index_red, array_yellow, index_yellow):
        grid = make_grid()
        #fill the grid with the RED     discs
        grid = fill_grid(grid, array_dc, array_red, index_red, 1)
        #fill the grid with the YELLOW  discs
        grid = fill_grid(grid, array_dc, array_yellow, index_yellow, 2)
        print_grid(grid)
        return grid
            
def find_according_circle(array_dc, x, y):
#find correct matching coordinates
        r = 20
        #print("Red discs co: " + str(x) + "," + str(y))
        for i in range(42):
            dc_x = array_dc[i][0]
            dc_y = array_dc[i][1]
            if (x-r) < dc_x and dc_x < (x+r) and (y-r) < dc_y and dc_y < (y+r):
                #print("match_dc: " + str(dc_x) + "," + str(array_dc[i][1]))
                return i
        sys.exit("There could not be found any match for disc: " + str(x) + "," + str(y))
        #If this error is been given, you may need to adjust the range of r
def fill_grid(grid, array_dc, array_color, index_color, color_code):
        #Color_code: 1; is for RED      discs
        #Color_code: 2; is for YELLOW   discs
        tmp = 0
        for i in range(index_color):
            tmp = find_according_circle(array_dc, array_color[i][0], array_color[i][1])
            #print(tmp)
            if tmp < 6:
                grid[0][tmp] = color_code
            elif tmp < 12:
                grid[1][tmp - 6] = color_code
            elif tmp < 18:
                grid[2][tmp - 12] = color_code
            elif tmp < 24:
                grid[3][tmp - 18] = color_code
            elif tmp < 30:
                grid[4][tmp - 24] = color_code
            elif tmp < 36:
                grid[5][tmp - 30] = color_code
            else:
                grid[6][tmp - 36] = color_code
        return grid

# -------------------------- READ / PRINT ----------------------------
def read_circles(a_circles, i):
#Conferting the array from circles to what we need
	data = [[0 for x in range(2)] for y in range(i)]
	for j in range(i):
	    point_x = a_circles[0][j][0]
	    point_y = a_circles[0][j][1]
	    data[j][0] = point_x
	    data[j][1] = point_y
#	print (str(data[0][0]))
	return data

def print_arrays(array_dc, array_r, index_r, array_y, index_y):
	print()
        print("The board contains: 42 holes.")
        print_circles_2(array_dc, 42)
        print()
	print("Colortype: RED. \tDiscs: " + str(index_r))
	print_circles_1(array_r, index_r)
	print()
	print("Colortype: Yellow. \tDiscs: " + str(index_y))
	print_circles_1(array_y, index_y)
	print("Total amount of discs: " + str(index_r + index_y))
        if index_r > index_y:
		print("Yellow is on set")
	else:
		print("Red is on set")
	return 1

#---------------------- Print Circles --------------------------

def print_circles_1(array, i):
	print()
	for j in range(i):
		print("\tx:" + str(array[j][0]) + "\t\ty:" + str(array[j][1]))
	return 1

def print_circles_2(array, top):
#Function for printing the new created array. Splitted in COLLUMS.
	print()
	x = 0
	for index in range(top):
		print("\tx:" + str(array[index][0]) + "\t\ty:" + str(array[index][1]))
                x += 1
		if x > 5:
			print()
			x = 0
	return 1

#------------- WORK TO BE DONE ------ WORK TO BE DONE ------------
#Do I need this? Probably not but leave it here.

def order_array(array, length):
        start_pos = 0
        for k in range(length):
            next_lowest = find_lowest_x(array, k, length)
            array = swapp(array, next_lowest, length, k, 0, 0)
        return array

#------------------------------------------------------------------
#--------------------- Order Array For Grid -----------------------

def order_array_for_grid(array, length):
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
#Used in order_array and for ""_for_grid
#Finds the next lowest in the array that isn't been processed yet.
	lowest = 10000
	for i in range(index, length):
            if array[i][0] < lowest:
	        lowest = array[i][0]
	return lowest
def find_highest_y(array, index, length):
#Used in order_array_for_grid
#Finds the next highest in the array that isn't been processed yet.
	highest = 0
	for i in range(index, length):
            if array[i][1] > highest:
		    highest = array[i][1]
	return highest

def swapp(array, num, length, new_place, x_or_y, end_pos):
#Used in order_array and for ""_for_grid
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

#---------------------- FINAL GRID FOR SHIPPING TO JAVA -----------------------
def make_grid():
    	return [[0 for x in range(6)] for y in range(7)]

def print_grid(grid):
        #Ugly
        '''
        print()
        grid[5][5] = 1
	grid[6][5] = 1
        for i in range(6):
            y = 5 - i
            print(str(grid[0][y])+"|"+str(grid[1][y])+"|"+str(grid[2][y])+"|"+str(grid[3][y])+"|"+str(grid[4][y])+"|"+str(grid[5][y])+"|"+str(grid[6][y]))
        '''
        #This looks best
        print()
        for y in range(5,-1,-1):
            this_print = "| "
            for x in range(7):
                if grid[x][y] == 1:
                    this_print = this_print + "R | "
                elif grid[x][y] == 2:
                    this_print = this_print + "Y | "
                else:
                    this_print = this_print + "0 | "
            print(this_print)
        return 1

#--------------------------------Show Circles Grapics ----------------------
def show_circles(out):
        cv.imshow("detected circles", out)
#End the program with ESC
	while True:
	    k = cv.waitKey(5) & 0xFF
	    if k == 27:
		break
	cv.destroyAllWindows()
        return 0

#-------------------------------------------------------------------
def error():
    sys.exit("python " + sys.argv[0] + " <picture> <0 or 1> \n Parameter 1: picture of four on a row board. \n Parameter 2: enable graphics.")
#-------------------------------------------------------------------
#-------------------------------- Main -----------------------------


if __name__ == '__main__':
	main()


#Some code needs to be switch from locations.
#Two functions need to be finished
#Also the final grid needs to be filled.

