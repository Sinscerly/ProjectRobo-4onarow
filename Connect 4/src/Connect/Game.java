package Connect;

import java.util.Scanner;

public class Game {
	static Scanner scanner = new Scanner(System.in);
	private int whosTurn = 0;
	Box whoBegan = Board.red;
	private int difficulty;
	public static int AI = 0, player = 1;
	Game(int startPlayer, int set_dificulty) {
		/* startPlayer = 0 Computer starts, = 1 Player starts */
		if(startPlayer == 1) { whosTurn = startPlayer; }
		difficulty = set_dificulty;
	}
	
	void start() {
		int computer = 0, player = 0;
		Board newBoard = new Board();
		Board.printBoard();
		AI miniMaxAI = new AI(difficulty); //rename this yet to be done
		while(GoodMoves.checkWin(Board.getBoard(), true) == false && Board.checkNotFull()) {
			if(whosTurn == 0) {
				computer = miniMaxAI.doSet(Board.getBoard(), whoBegan);
				System.out.print("Computer placed in row: " + (computer+1));
				Board.placeMove(computer, Board.red);
				Board.printBoard();
			}
			else {
				player = 0;
				System.out.println("");
				System.out.print("Enter a row Yellow: ");
				while(player < 1 || player > 7)									//check of het tussen de 1 en 7 is
				{
					player = scanner.nextInt();
					if(player < 1 || player > 7) System.out.println("Number out of bounds");
				}
				Board.placeMove(player-1, Board.yellow);
			}
			/* Let the other player make a move */
			if(whosTurn < 1)
				whosTurn++;
			else
				whosTurn = 0;	
		}
		if(whosTurn == 1)
			whoBegan = Board.yellow;
		else
			whoBegan = Board.red;
		Board.printBoard();
	}
}
