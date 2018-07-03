package Connect;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

class Motor {
	public static void main(String[] args)
	{
		input_row(0);
	}
	public static int input_row(int row)
	{
		File currentDirFile = new File(".");
		String pwd = currentDirFile.getAbsolutePath();
		pwd = pwd.substring(0, pwd.length() - 1);
		if(pwd.contains("Sinsc"))
			return 1;
		/*
		 * Input can only be row 0-6
		 * Row's 0-5 are driven by execute motor
		 * Row 6 only requires a push from the stack. Because all the others are closed.
		 */
		if(row == 6) {
			execute_stack();
		} else if(0 <= row && row < 6) {
			execute_motor(row);
		}
		return 1;
	}
	private static void execute_stack()
	{
		/*
		 * Stack push
		 */
		System.out.println("Execute stack.py\n");
		try {
			Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec("python stack.py");
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
	}
	private static void execute_motor(int motor)
	{
		/*
		 * Motor range is from 0-5
		 */
		System.out.println("Execute motor.py " + motor + "\n");
		try {
			Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec("python motor.py " + motor);
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
	}
}
