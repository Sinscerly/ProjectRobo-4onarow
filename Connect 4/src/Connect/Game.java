package Connect;

import java.util.Random;
import java.util.Scanner;

public class Game {
	static Scanner scanner = new Scanner(System.in);
	private int wich_turn = 0;
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
		while(Rules.checkWin(Board.getBoard(), true) == false /* && check of het bord niet meer leeg is. */) {
			if(wich_turn == 0) {
				computer = 7;
				while(computer == 7)
				{
					//computer = doAI(Board.getCopiedBoard());
					computer = helloAI.doSet(Board.getCopiedBoard());
					if(computer == 7) {
						computer = r.nextInt(7);
					}
					if(!Board.is_empty(computer))
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
			if(wich_turn < 1)
				wich_turn++;
			else
				wich_turn = 0;	
		}
		Board.print_board();
	}
}
