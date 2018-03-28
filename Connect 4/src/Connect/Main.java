package Connect;

public class Main {

	public static void main(String[] args) {
		int difficulty = -1;
		int x = 0;
		Game startGame = new Game(Game.AI);
		while (x == 0) {
			System.out.println("Do you want to play a game?");
			while(0 > difficulty || difficulty > 8) {
				System.out.println(
					"At what difficulty do you want to play? \n"
					+ "0: Monkey \n"
					+ "1: Very Easy \n"
					+ "2: Easy \n"
					+ "3: Medium \n"
					+ "4: Hard \n"
					+ "5: Very Hard \n"
					+ "6: Extreem \n"
					+ "7: Legend \n"
					+ "8: Jeroen killer");
				difficulty = Game.scanner.nextInt();	
			}
			startGame.setDifficulty(difficulty);
			System.out.println(difficulty);
			System.out.println("");
			startGame.start();
		}
	}

}
