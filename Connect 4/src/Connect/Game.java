package Connect;

import java.util.Scanner;

public class Game {
	static Scanner scanner = new Scanner(System.in);
	private int whosTurn = 0;
	Box whoBegan = Board.red;
	public static boolean seeProgress = false;

	Game() {}

	void pvp() {
		Board.setupBoard();

		while (GoodMoves.checkWin(Board.getBoard(), true) == false && Board.checkFull(Board.getBoard())) {
			if (whosTurn == 0) {
				player(Board.red);
				whosTurn = 1;
			} else {
				player(Board.yellow);
				whosTurn = 0;
			}
		}
	}

	void pvc(int difficulty) {
		AI miniMaxAI = new AI(difficulty);
		Board.setupBoard();
		Board.printBoard();
<<<<<<< HEAD
		//AI miniMaxAI = new AI(difficulty);
		AI_neural neuralAI = new AI_neural();
		while (GoodMoves.checkWin(Board.getBoard(), true) == false && Board.checkFull(Board.getBoard())) {
			if (whosTurn == 0) {
				//computer = miniMaxAI.doSet(Board.getBoard(), whoBegan);
				computer = neuralAI.guess();
				System.out.print("Computer placed in row: " + (computer + 1));
				Board.placeMove(computer, Board.red);
				Board.printBoard();
=======

		while (GoodMoves.checkWin(Board.getBoard(), true) == false && Board.checkFull(Board.getBoard())) {
			if (whosTurn == 0) {
				AI(miniMaxAI, Board.red);
				whosTurn = 1;
			} else {
				player(Board.yellow);
				whosTurn = 0;
			}
		}
		Board.printBoard();
	}

	void cvc(int difficulty1, int difficulty2) throws InterruptedException {
		AI miniMaxAI = new AI(difficulty1);
		AI m2 = new AI(difficulty2);
		Board.setupBoard();
		Board.printBoard();

		while (GoodMoves.checkWin(Board.getBoard(), true) == false && Board.checkFull(Board.getBoard())) {
			if (whosTurn == 0) {
				AI(miniMaxAI, Board.red);
				if (seeProgress) {
					Board.printBoard();
					Thread.sleep(1000);
				}
>>>>>>> refs/remotes/origin/aiVSai_alldiff
				whosTurn = 1;
			} else {
				AI(m2, Board.yellow);
				if (seeProgress) {
					Board.printBoard();
					Thread.sleep(1000);
				}
				whosTurn = 0;
			}
		}
		Board.printBoard();
	}

	void cvcad(int difficulty1, int difficulty2) throws InterruptedException {
		this.cvc(difficulty1, difficulty2);
		// puts who won in the print string.
		if (whosTurn == 1)
			Main.printString += difficulty1 + " VS " + difficulty2 + ": cpu 1 wins.";
		else
			Main.printString += difficulty1 + " VS " + difficulty2 + ": cpu 2 wins.";
	}

	void player(Box color) {
		Board.printBoard();
		int player = 0;
		System.out.println("");
		System.out.print("Enter a row player: ");
		while (player < 1 || player > 7) // check of het tussen de 1 en
											// 7 is
		{
			player = scanner.nextInt();
			if (player < 1 || player > 7)
				System.out.println("Number out of bounds");
		}
		Board.placeMove(player - 1, color);
	}

	void AI(AI ai, Box color) {
		int set = ai_move(ai);
		Board.placeMove(set, color);
	}

	public int ai_move(AI smart) {
		// change set to move
		int ai_set = 0;
		ai_set = smart.doSet(Board.getBoard(), whoBegan);
		System.out.print("Computer placed in row: " + (ai_set + 1));
		System.out.println("Took: " + ((smart.end - smart.start) / 1000) + "S.");
		return ai_set;
	}
}
