package Connect;

public class Main {

	static int diffCpu1;
	static int diffCpu2;
	public static boolean vali = false;

	public static void main(String[] args) throws InterruptedException {
		String input;
		while (true) {
			System.out.println("What type of game do you want?");
			System.out.println("a: player VS cpu.");
			System.out.println("b: cpu VS cpu.");
			System.out.println("x: exit.");
			System.out.print("Your anwser: ");
			input =  Game.scanner.next();
			if(input.equals("a"))
			{
				pvc();
			}
			else if(input.equals("b"))
			{
				cvc();
			}
			else if(input.equals("x"))
			{
				break;
			}
		}
	}
	
	private static void pvc() throws InterruptedException
	{
		Game startGame = new Game(Game.AI);
		System.out.println("Do you want to play a game?");
		diffCpu1 = -1;
		while(0 > diffCpu1 || diffCpu1 > 8) {
			printDiff("1(red)");
			diffCpu1 = scanInt();	
		}
		startGame.setDifficulty1(diffCpu1);
		System.out.println(diffCpu1);
		System.out.println("");
		startGame.start();
	}
	private static void cvc() throws InterruptedException
	{
		System.out.println("Do you want to see the progress?");
		System.out.println("y/n?");
		Game startGame = new Game(Game.AI);
		if(Game.scanner.next().equals("y"))
		Game.seeProgress = true;
		System.out.println("Do you want to play a game?");
		diffCpu1 = -1;
		while(0 > diffCpu1 || diffCpu1 > 8) {
			printDiff("1(red)");
			diffCpu1 = scanInt();	
		}
		diffCpu2 = -1;
		while(0 > diffCpu2 || diffCpu2 > 8) {
			printDiff("2(yellow)");
			diffCpu2 = scanInt();	
		}
		startGame.setDifficulty1(diffCpu1);
		startGame.setDifficulty2(diffCpu2);
		System.out.println(diffCpu1 + " vs " + diffCpu2);
		System.out.println("");
		startGame.start();
	}
	static void printDiff(String player)
	{
		System.out.println(
				"At what difficulty does cpu " + player + " play? \n"
				+ "0: Monkey \n"
				+ "1: Very Easy \n"
				+ "2: Easy \n"
				+ "3: Medium \n"
				+ "4: Hard \n"
				+ "5: Very Hard \n"
				+ "6: Extreem \n"
				+ "7: Legend \n"
				+ "8: Jeroen killer");
	}
	static int scanInt()
	{
		return Game.scanner.nextInt();
	}

}
