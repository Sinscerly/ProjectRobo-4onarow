package Connect;

public class AI {
	private int dificulty;
	AI(int set_dificulty) {
		dificulty = set_dificulty;
	}
	int doSet(Object[][] grid) {
		//get position y position of all colums.
		int[] moves = new int[7];
		int do_move = 7;
		moves = Board.free_slots();
		do_move = isWin(grid, moves, Board.red); 	//if there is a winning move AI will make that move
		if(do_move < 7) { return do_move; }
		do_move = isWin(grid, moves, Board.yellow);	//if yellow can have a winning move AI will try to block it
		if(do_move < 7) { return do_move; }
		return 7;
	}
	/* Depth 1 search for the winning move */
	int isWin(Object[][] grid, int[] moves, Object color) {
		for(int x = 0; x < 7; x++) {
			if(moves[x] != -1 && check_winning_move(grid, x, moves[x], color)) {
				return x;
			}
		}
		return 7;
	}
	/* check placement of piece, if it will be the winning move. */
	boolean check_winning_move(Object[][] grid, int x, int y, Object color) {
		grid = Board.add(grid, x, y, color);
		boolean win = Rules.checkWin(grid, false);
		Board.remove(grid, x, y);
		return win;
	}
}
