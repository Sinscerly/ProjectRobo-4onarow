package Connect;
import java.io.*;


public class executeVision {
	private static final int ROWS = 7, COLUMS = 6; // dimensions of the new_board
	private static int[][] new_board = new int[ROWS][COLUMS]; // new_board that consist
	private int faulty = 0;
	
	public int[][] execute()
	{	
		System.out.println("Execute Vision");
		int output = 0;
		while(output == 0) 
		{
			//execute_vision();
			try {
				output = read_output();
			} catch (Exception e) {
				System.out.println(e.toString());
				e.printStackTrace();
			}
			if(output == 1) {
				System.out.println("Got Grid");
				output = check_board();
			}
			if(output == 0)
				System.out.println("Will try a new picture. This is the: " + Integer.toString(faulty) + " time");
		}
		System.out.println("Grid transfered and returned to new_board.");
		//return something..
		printBoard();
		return new_board;
	}
	private int check_board() 
	{
		int flag = 0;
		for(int x = 0; x < ROWS; x++) {
			for(int y = 1; y < COLUMS; y++) {
				if(new_board[x][y] != 0 && new_board[x][y-1] == 0) {
					flag = 1;
					System.out.println("Flag is: " + Integer.toString(flag));
					return 0;
				}
			}
		}
		System.out.println("Flag is: " + Integer.toString(flag));
		return 1;
	}
	private void execute_vision()
	{
		//This part of code is made to find out how executing a python script works from a Java program!
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
			System.out.println("Exited with error code " + exitVal);

		} catch(Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}	

		//System.out.println("\nI waited to finish my writing");
		System.out.println("Java project can go further now");
	}
	public int read_output() throws FileNotFoundException
  	{
  		// We need to provide file path as the parameter:
  		// double backquote is to avoid compiler interpret words
  		// like \test as \t (ie. as a escape sequence)
		File file = new File("C:/Users/Sinsc/Documents/GitHub/ProjectRobo/Connect 4/src/Connect/grid/output.txt");
  		//File file = new File("/grid/output.txt");
 
  		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String new_line = null;
	    	//Check if grid is not faulty.
	    	if ((new_line = br.readLine()).equals("TRUE")) {
	    		faulty = 0;
	    		for (int y = COLUMS - 1; y >= 0; y--) {
	    			new_line = br.readLine();
  		  			for (int x = 0; x < ROWS; x++) {
  		  				new_board[x][y] = Integer.parseInt(new_line.substring(x,x+1));
  		  			}
	    		}
	    	} else {
	    		System.out.println("The output wasn't good and need to be retried.");
	    		faulty++;
	    		return 0;
    		}
  		} catch (Exception e) {
  				System.out.println(e.toString());
  				e.printStackTrace();
  		}
  		return 1;
  		//
		
		//String whole = "something";
		//String first = whole.substring(0, 1);
		//System.out.println(first);


  	}	
	public static void printBoard() {
		System.out.println("");
		System.out.println("| 1   2   3   4   5   6   7 |");
		for (int y = COLUMS - 1; y >= 0; y--) {
			System.out.print("| ");
			for (int x = 0; x < ROWS; x++) {
				if (new_board[x][y] == 0)
					System.out.print("0" + " | ");
				else if (new_board[x][y] == 1)
					System.out.print("R" + " | ");
				else
					System.out.print("Y" + " | ");
			}
			System.out.println();
		}
	}
}