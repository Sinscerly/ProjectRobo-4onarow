package Connect;

import java.util.Random;
import java.util.Scanner;

public class Game {
	static Scanner scanner = new Scanner(System.in);
	private int wich_turn = 0;
	Object whoBegan = Board.red;
	private int dificulty;
	Game(int startPlayer, int set_dificulty) {
		/* startPlayer = 0 Computer starts, = 1 Player starts */
		if(startPlayer == 1) { wich_turn = startPlayer; }
		dificulty = set_dificulty;
	}
	
	void start() {
		int computer = 0, player = 0;
		//player player1 = new player(board.red);
		Board play = new Board();
		Random r = new Random();
		Board.print_board();
		AI helloAI = new AI(dificulty); //rename this yet to be done
		while(Rules.checkWin(Board.getBoard(), true) == false && play.check_not_full()) {
			if(wich_turn == 0) {
				computer = 7;
				while(computer < 0 || 6 < computer)
				{
					computer = helloAI.doSet(Board.getBoard(), whoBegan);
					if(computer < 0 || 6 < computer) {
						computer = r.nextInt(7);
					}
					if(Board.check_empty(computer) == -1)
							computer = 7;
				}
				System.out.print("Computer placed in row: " + (computer+1));
				play.place_move(computer, Board.red);
				Board.print_board();
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
				play.place_move(player-1, Board.yellow);
			}
			/* Let the other player make a move */
			if(wich_turn < 1)
				wich_turn++;
			else
				wich_turn = 0;	
		}
		if(wich_turn == 1)
			whoBegan = Board.yellow;
		else
			whoBegan = Board.red;
		Board.print_board();
	}
}
