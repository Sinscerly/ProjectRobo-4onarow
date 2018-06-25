package Connect;
import java.io.*;


public class executeVision {
	private static final int ROWS = 7, COLUMS = 6; // dimensions of the board
	private static int[][] board = new int[ROWS][COLUMS]; // board that consist
	private int faulty = 0;
	
	public void execute()
	{	
		System.out.println("Execute Vision");
		int output = 0;
		while(output == 1) 
		{
			//execute_vision();
			try {
				output = read_output();
			} catch (Exception e) {
				System.out.println(e.toString());
				e.printStackTrace();
			}
			if(output == 1)
			{
				break;
			}
			System.out.println("Will try a new picture. This is the: " + Integer.toString(faulty) + " time");
		}
		System.out.println("Grid transfered and returned to board.");
		//return something..
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
  		File file = new File("grid/output.txt");
 
  		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
  		    	String line = null;
			System.out.println("Hi..");
  		    	//Check if grid is not faulty.
  		    	if ((line = br.readLine()).equals("TRUE")) {
  		    		faulty = 0;
  		    		int l = 0;
  		    		System.out.println("Hi");
	    			for (int y = 6; y >= 0; y--) {
	    				String numbers = br.readLine();
	    				System.out.println(numbers);
	    				for(int x = 0; x < 6; x ++) {
	    					board[y][x] = Integer.parseInt(numbers.substring(x));
	    				}
	    			}
  		    	} else if ((line = br.readLine()).equals("FALSE")) {
  		    		System.out.println("The output wasn't good and need to be retried.");
  		    		faulty++;
  		    		return 0;
  		    	} else { System.out.println("Not good"); }
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
}