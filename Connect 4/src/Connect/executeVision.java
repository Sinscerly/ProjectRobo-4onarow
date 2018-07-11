package Connect;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;

/*
 * 
 		executeVision eV = new executeVision();
		eV.execute();
		System.exit(1);
 * 
 */
public class executeVision {
	private static final int ROWS = 7, COLUMS = 6; // dimensions of the new_board
	private static Box[][] new_board = new Box[ROWS][COLUMS]; // new_board that consist of boxes
	private int faulty = 0;
	private static Box[][] current_board = new Box[ROWS][COLUMS];
	
	public executeVision() {
	}
	
	public Box[][] execute(int whoBegan)
	{	
		int output = 0;
		while(output != 2) 
		{
			System.out.println("print current board");
			printBoard(current_board);
			execute_vision();
			try {
				System.out.println("Read output");
				output = read_output();
			} catch (Exception e) {
				System.out.println(e.toString());
				e.printStackTrace();
			}
			if(output == 1) {
				System.out.println("Output good, \t now check grid");
				output = check_board(whoBegan);
				if(output == 0)
					System.out.println("Grid is not good");
	    		faulty = 0;
			}
			if(output == 0) {
	    		faulty++;
				System.out.println("Will try a new picture. This is the: " + Integer.toString(faulty) + " time");
			}
			if(output == 3) {
				System.out.println("Someone cheated. Reseting the game!");
				return emptyBoard();
			}
		}
		System.out.println("Grid transfered and returned to new_board.");
		//return something..
		//printBoard();
		return new_board;
	}
	private int check_board(int whoBegan) 
	{
		System.out.println("Check board now");
		int flag = 0;
		int Red = 0, Yellow = 0;
		int is_same = 0, co_x = 0, co_y = 0, difs = 0;
		for(int x = 0; x < ROWS; x++) {
			for(int y = 0; y < COLUMS; y++) {
				if(new_board[x][y] == Board.red) {
					Red++;
				} else if(new_board[x][y] == Board.yellow) {
					Yellow++;
				}
				//check discs are 'flying'.
				if(y != 0 && new_board[x][y] != Board.empty && new_board[x][y-1] == Board.empty) {
					flag = 1;
					System.out.println("Flag is: " + Integer.toString(flag));
					return 0;
				}
				if(new_board[x][y] == current_board[x][y]) {
					is_same++;
				} else {
					co_x = x;
					co_y = y;
					difs++;
				}
				if(difs > 1) {
					System.out.println("difference between the 2 boards is to big");
					return 0;
				}
			}
		}
		if(is_same == 42) {
			System.out.println("there is no difference");
			return 0;
		}
		//xy was 0
		//xy moet nu 1 of 2 zijn
		if(!(current_board[co_x][co_y] == Board.empty && (new_board[co_x][co_y] == Board.red || new_board[co_x][co_y] == Board.yellow))) {
			//System.out.println("board x: " + co_x + " y: " + co_y);
			//System.out.println("Currentboard: " + current_board[co_x][co_y] + "\nNew_board: " + new_board[co_x][co_y]);
			System.out.println("The difference between boards is not correct, current is red or yellow and new is differend");
			return 0;
		}
		System.out.println("Flag is: " + Integer.toString(flag));
		System.out.println("Red: \t" + Red + "\nYellow: " + Yellow + "\nDifferences: " + (Math.abs(Red - Yellow)));
		//0 = red began 1 = yellow began
		if(Yellow - Red == 1 && whoBegan == 0 || Red - Yellow == 1 && whoBegan == 1) {
			System.out.println("Difference is to big, so someone is cheating.");
			if(whoBegan == 1) {
				System.out.println("Red is cheating");
			} else {
				System.out.println("Yellow is cheating");
			}
			return 3;
		}
		return 2;
	}
	private int execute_vision()
	{
		File currentDirFile = new File(".");
		String pwd = currentDirFile.getAbsolutePath();
		pwd = pwd.substring(0, pwd.length() - 1);
		if(pwd.contains("Sinsc"))
			return 1;
		//This part of code is made to find out how executing a python script works from a Java program!
		//System.out.println("Execute Vision");
		System.out.println("Execute doALL.py\n");
		try {
			Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec("python doALL.py");
				
			BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			
			String line = null;

			while((line = input.readLine()) != null) {
				System.out.println(line);
			}
			int exitVal = pr.waitFor();
			if(exitVal != 0) {
				System.out.println("Exited with error code " + exitVal);
			}
		} catch(Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}	
		//System.out.println("\nI waited to finish my writing");
		//System.out.println("Java project can go further now");
		return 1;
	}
	public int read_output() throws FileNotFoundException
  	{
  		// We need to provide file path as the parameter:
  		// double backquote is to avoid compiler interpret words
  		// like \test as \t (ie. as a escape sequence)
		//File file = new File("C:/Users/Sinsc/Documents/GitHub/ProjectRobo/Connect 4/src/Connect/grid/output.txt");
  		//File file = new File("grid/output.txt");
		File currentDirFile = new File(".");
		String pwd = currentDirFile.getAbsolutePath();
		pwd = pwd.substring(0, pwd.length() - 1);
		//System.out.println(pwd);
		File file = null;
		if(pwd.contains("Sinsc")) {
			//file = new File(pwd + "src/Connect/grid/output.txt");
			boolean suc = false;
			try {
				file = new File(pwd + "src/Connect/grid/output.txt");
			} catch (Exception e) {
				suc = true;
			} 
			if (suc) {
		  		file = new File("grid/output.txt");
			}
		} else
	  		file = new File("grid/output.txt");
  		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String new_line = null;
	    	//Check if grid is not faulty.
	    	if ((new_line = br.readLine()).equals("TRUE")) {
	    		for (int y = COLUMS - 1; y >= 0; y--) {
	    			new_line = br.readLine();
  		  			for (int x = 0; x < ROWS; x++) {
  		  				if(Integer.parseInt(new_line.substring(x,x+1)) == 0)
  		  					new_board[x][y] = Board.empty;
  		  				else if(Integer.parseInt(new_line.substring(x,x+1)) == 1)
  		  					new_board[x][y] = Board.red;
  		  				else
  		  					new_board[x][y] = Board.yellow;
  		  			}
	    		}
	    	} else {
	    		System.out.println("output.txt = FALSE. The output wasn't good and need to be retried.");
	    		return 0;
    		}
  		} catch (Exception e) {
  				System.out.println(e.toString());
  				e.printStackTrace();
  		}
  		return 1;
  	}	
	public static void printBoard(Box[][] grid) {
		System.out.println("");
		System.out.println("| 1   2   3   4   5   6   7 |");
		for (int y = COLUMS - 1; y >= 0; y--) {
			System.out.print("| ");
			for (int x = 0; x < ROWS; x++) {
				if (grid[x][y] == Board.empty)
					System.out.print("0" + " | ");
				else if (grid[x][y] == Board.red)
					System.out.print("R" + " | ");
				else
					System.out.print("Y" + " | ");
			}
			System.out.println();
		}
	}
	public void getCurrent_board(Box[][] grid)
	{
		for(int x=0;x!=7;x++)
			for(int y=0;y!=6;y++)
				current_board[x][y] = grid[x][y];
	}
	public Box[][] emptyBoard() {
		for(int x = 0; x != 7; x++)
			for(int y = 0; y != 6; y++)
				new_board[x][y] = Board.empty;
		return new_board;
	}
}