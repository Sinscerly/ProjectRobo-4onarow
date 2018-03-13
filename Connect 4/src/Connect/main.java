package Connect;

import java.lang.reflect.Array;
import java.util.Random;
import java.util.Scanner;

public class main {
	public int game = 0;
	
	static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int computer = 0, player = 0;
		//player player1 = new player(board.red);
		board play = new board();
		Random r = new Random();
		
		int wich_turn = 0;
		while(rules.checkWin(board.getBoard(), true) == false) {
			if(wich_turn == 0) {
				computer = 7;
				while(computer == 7)
				{
					computer = best_move(board.getCopiedBoard());
					if(computer == 7) {
						computer = r.nextInt(7);
					}
					if(!board.is_empty(computer))
							computer = 7;
				}
				System.out.println("");
				System.out.print("Computer placed in row: " + computer);
				play.place_move(computer, board.red);
				board.print_board();
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
				play.place_move(player, board.yellow);
			}
			if(wich_turn < 1)
				wich_turn++;
			else
				wich_turn = 0;	
		}
		board.print_board();
	}
	public static int (Object[][] grid) {
		//get position y position of all colums.
		int[] moves = new int[7];
		int do_move = 7;
		moves = board.free_slots();
		do_move = isWin(grid, moves);
		if(do_move < 7) { return do_move; }
		return 7;
	}
	private static boolean doIwin(Object[][] grid, int x, int y) {
		grid = board.add(grid, x, y);
		boolean win = rules.checkWin(grid, false);
		board.remove(grid, x, y);
		return win;
	}
	public static int isWin(Object[][] grid, int[] moves) {
		for(int x = 0; x < 7; x++) {
			if(doIwin(grid, x, moves[x])) {
				return x;
			}
		}
		return 7;
	}
}
