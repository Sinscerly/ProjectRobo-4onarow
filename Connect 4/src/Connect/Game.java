package Connect;

import java.util.Random;
import java.util.Scanner;

public class Game {
	static Scanner scanner = new Scanner(System.in);
	private int wich_turn = 0;
	Game(int startPlayer, int dificulty) {
		/* startPlayer = 0 Computer starts, = 1 Player starts */
		if(startPlayer == 1) { wich_turn = startPlayer; }
	}
	
	void start() {
		int computer = 0, player = 0;
		//player player1 = new player(board.red);
		Board play = new Board();
		Random r = new Random();
		Board.print_board();
		while(Rules.checkWin(Board.getBoard(), true) == false /* && check of het bord niet meer leeg is. */) {
			if(wich_turn == 0) {
				computer = 7;
				while(computer == 7)
				{
					computer = ai(Board.getCopiedBoard());
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
	int ai(Object[][] grid) {
		//get position y position of all colums.
		int[] moves = new int[7];
		int do_move = 7;
		moves = Board.free_slots();
		do_move = isWin(grid, moves, Board.red);
		if(do_move < 7) { return do_move; }
		do_move = isWin(grid, moves, Board.yellow);
		if(do_move < 7) { return do_move; }
		return 7;
	}
	int isWin(Object[][] grid, int[] moves, Object color) {
		for(int x = 0; x < 7; x++) {
			if(moves[x] != -1 && doIwin(grid, x, moves[x], color)) {
				return x;
			}
		}
		return 7;
	}
	boolean doIwin(Object[][] grid, int x, int y, Object color) {
		grid = Board.add(grid, x, y, color);
		boolean win = Rules.checkWin(grid, false);
		Board.remove(grid, x, y);
		return win;
	}
}
