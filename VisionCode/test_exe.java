import java.io.*;

class execute_doALL
{
	public static void main(String args[])
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

		System.out.println("\nI waited to finish my writing");
		System.out.println("Java project can go further now");
	}
}
