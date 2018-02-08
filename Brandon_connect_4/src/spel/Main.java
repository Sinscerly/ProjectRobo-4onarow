package spel;
public class Main {

	public static boolean end = false;
	
	public static void main(String[] args)
	{
		while(end == false)
		{
			Spel.nextMoveRed();
			Spel.nextMoveYellow();
		}
	}
}
