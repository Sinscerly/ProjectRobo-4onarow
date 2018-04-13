package Connect;

import java.util.Scanner;

public class Game {
	static Scanner scanner = new Scanner(System.in);
	private int whosTurn = 0;
	Box whoBegan = Board.red;
	private int difficulty1, difficulty2 = 2;
	public static int AI = 0, player = 1;
	public int sets; // total number of sets of a game..

	Game(int startPlayer) {
		/* startPlayer = 0 Computer starts, = 1 Player starts */
		if (startPlayer == 1) {
			whosTurn = startPlayer;
		}
	}
	AI miniMaxAI = new AI(difficulty1);
	AI m2 = new AI(difficulty2);
	//starts the game
	void start() {
		//change set to move
		int p1_set = 0, p2_set = 0;
		sets = 0;
		Board.setupBoard();
		Board.printBoard();
		
		while (GoodMoves.checkWin(Board.getBoard(), true) == false && Board.checkFull(Board.getBoard())) {
			if (whosTurn == 0) {
				p1_set = ai_move(miniMaxAI);
				Board.placeMove(p1_set, Board.red);
//				Board.printBoard();
				whosTurn = 1;
			} else {
				p2_set = ai_move(m2);
				Board.placeMove(p2_set, Board.yellow);
				whosTurn = 0;
			}
			sets++;
		}
		// what was this???
		if (whosTurn == 1)
			whoBegan = Board.yellow;
		else
			whoBegan = Board.red;
		Board.printBoard();
	}
	public int ai_move(AI smart) {
		//change set to move
		int ai_set = 0;
		ai_set = smart.doSet(Board.getBoard(), whoBegan);
		System.out.print("Computer placed in row: " + (ai_set + 1));
		return ai_set;
	}
	public int player_move() {
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
		//because the player will not think in numbers of 0 to 6 it's made from 1 to 7 so we need to put all the awnsers 1 lower.
		return player - 1;
	}
	public void setDifficulty(int difficulty) {
		this.difficulty1 = difficulty;
	}
}
