import java.io.*;

class execute_doALL
{
	private static final int ROWS = 7, COLUMS = 6; // dimensions of the board
	private static int[][] board = new int[ROWS][COLUMS]; // board that consist
	
	public static void main(String args[])
	{	
		//execute_vision();
		try {
			read_output();
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
		//return something..
	}
	private static void execute_vision()
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
	public static int read_output() throws FileNotFoundException
  	{
  		// We need to provide file path as the parameter:
  		// double backquote is to avoid compiler interpret words
  		// like \test as \t (ie. as a escape sequence)
  		File file = new File("grid/output.txt");
 
  		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
  		    	String line;
			System.out.println("Hi..");
  		    	//Check if grid is not faulty.
  		    	if ((line = br.readLine()) == "5") {
  		    		int l = 0; j
  		    		System.out.println("Hi");
	    			for (int y = 6; y >= 0; y--) {
	    				String numbers = br.readLine();
	    				System.out.println(numbers);
	    				for(int x = 0; x < 6; x ++) {
	    					board[y][x] = Integer.parseInt(numbers.substring(x));
	    				}
	    			}
  		    	} else if ((line = br.readLine()) == "Fault") {
  		    		System.out.println("Grid is not okay.");
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
