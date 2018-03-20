package Connect;

public class main {
	
	public static void main(String[] args) {
		Game startGame = new Game(0, 1); //Start player, Difficulty
		int x = 0;
		while(x == 0) {
			System.out.println("A new game will be started!");
			System.out.println("");
			startGame.start();
			
		}
	}
	
}
