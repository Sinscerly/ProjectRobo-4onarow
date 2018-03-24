package Connect;

public class main {
	
	public static void main(String[] args) {
		int difficulty = 0;
		System.out.println("At what difficulty do you want to play? From 1 to 7 is recommended and the max is 41 but don't do it!");
		difficulty = Game.scanner.nextInt();
		Game startGame = new Game(Game.AI, difficulty); //Start player, Difficulty
		int x = 0;
		while(x == 0) {
			System.out.println("A new game will be started!");
			System.out.println("");
			startGame.start();
		}
	}
	
}
