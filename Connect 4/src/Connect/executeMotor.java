package Connect;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class executeMotor {

	private double[] position = {7, 3.7, 3.3};
	public int Player_on_set(boolean state_motor) {
		double pos = position[0];
		if(state_motor) {
			pos = position[1];
		}
		for(int i = 0; i < 6; i++) {
			if(i == 2 && state_motor) {
				execute_motor(i, position[2]);
			} else {
				execute_motor(i, pos);
			}
		}
		System.out.println("");
		return 1;
	}
	public int AI_Input_Row(int row, boolean state_motor, boolean push_stack)
	{
		/*
		File currentDirFile = new File(".");
		String pwd = currentDirFile.getAbsolutePath();
		pwd = pwd.substring(0, pwd.length() - 1);
		if(pwd.contains("Sinsc"))
			return 0;
		*/
		/*
		 * Input can only be row 0-6
		 * Row's 0-5 are driven by execute motor
		 * Row 6 only requires a push from the stack. Because all the others are closed.
		 */
		if(0 <= row && row < 6) {
			double pos = position[0];
			if(row == 2 && state_motor) {
				pos = position[2];
			} else if (state_motor) {
				pos = position[1];
			}
			execute_motor(row, pos);
		}
		if(push_stack) {
			execute_stack();
		}
		System.out.println("");
		return 1;
	}
	private void execute_stack()
	{
		/*
		 * Stack push, Needs to be developed still. Code has functionality but no hardware yet.
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
			if(exitVal != 0) {
				System.out.println("Exited with error code " + exitVal);
			}
		} catch(Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	private void execute_motor(int motor, double pos)
	{
		/*
		 * Motor range is from 0-5.
		 */
		System.out.println("Execute motor.py " + motor + "with pos: " + pos);
		try {
			Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec("python half-motor.py " + motor + " " + pos);
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
	}
}