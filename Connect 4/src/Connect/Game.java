package Connect;

import java.util.Scanner;

public class Game {
	static Scanner scanner = new Scanner(System.in);
	private int whosTurn = 0;
	Box whoBegan = Board.red;
	private int difficulty;
	public static int AI = 0, player = 1;

	Game(int startPlayer) {
		/* startPlayer = 0 Computer starts, = 1 Player starts */
		if (startPlayer == 1) {
			whosTurn = startPlayer;
		}
	}
	//starts the game
	void start() {
		int computer = 0, player = 0;
		Board.setupBoard();
		Board.printBoard();
		AI miniMaxAI = new AI(difficulty);
		while (GoodMoves.checkWin(Board.getBoard(), true) == false && Board.checkFull(Board.getBoard())) {
			if (whosTurn == 0) {
				computer = miniMaxAI.doSet(Board.getBoard(), whoBegan);
				System.out.print("Computer placed in row: " + (computer + 1));
				Board.placeMove(computer, Board.red);
				Board.printBoard();
				whosTurn = 1;
			} else {
				player = 0;
				System.out.println("");
				System.out.print("Enter a row Yellow: ");
				while (player < 1 || player > 7) // check of het tussen de 1 en
													// 7 is
				{
					player = scanner.nextInt();
					if (player < 1 || player > 7)
						System.out.println("Number out of bounds");
				}
				Board.placeMove(player - 1, Board.yellow);
				whosTurn = 0;
			}
		}
		if (whosTurn == 1)
			whoBegan = Board.yellow;
		else
			whoBegan = Board.red;
		Board.printBoard();
	}
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
}
