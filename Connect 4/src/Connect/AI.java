package Connect;

public class AI {
	private int[] moves = new int[7];
	private int dificulty;
	AI(int set_dificulty) {
		dificulty = set_dificulty;
	}
	int doSet(Object[][] grid) {
		//get position y position of all colums.
		int do_move = 7;
		moves = Board.free_slots();
		do_move = isWin(grid, Board.red); 	//if there is a winning move AI will make that move or when 2 moves from win
		if(do_move < 7) { return do_move; }
		do_move = isWin(grid, Board.yellow);	//if yellow can have a winning move AI will try to block it
		if(do_move < 7) { return do_move; }
		return 7;
	}
	/* Depth 1 search for the winning move */
	int isWin(Object[][] grid, Object color) {
		for(int x = 0; x < 7; x++) {
			if(moves[x] != -1 && check_winning_move(grid, x, color)) {
				return x;
			}
			if(moves[x] != -1 && check_2_to_win(grid, x, moves[x], color)) {
				return x;
			} 
		}
		return 7;
	}
	/* check placement of piece, if it will be the winning move. */
	boolean check_winning_move(Object[][] grid, int x, Object color) {
		grid = Board.add(grid, x, moves[x], color);
		boolean win = Rules.checkWin(grid, false);
		Board.remove(grid, x, moves[x]);
		return win;
	}
	/* Brandon's hardcoded variant for 2depth algoritme */
	boolean check_2_to_win(Object[][] grid, int x, int y, Object color) {
		boolean win = false;
		//
		if(x != 6 && y != 1) {
			win = brandonvereenvoudig(grid, x, 1, y, 1, color);
			/*
			if(Board.is_empty(x + 1, y + 1)) {
			grid = Board.add(grid, x, y, color);
			grid = Board.add(grid, x + 1, y + 1, color);
			win = Rules.checkWin(grid, false);
			Board.remove(grid, x, y);
			Board.remove(grid, x + 1, y + 1);
			}*/
		}
		//
		if(x != 0 && y != 1) {
			win = brandonvereenvoudig(grid, x, -1, y, 1, color);
			/*if(Board.is_empty(x - 1, y + 1)) {
			grid = Board.add(grid, x, y, color);
			grid = Board.add(grid, x - 1, y + 1, color);
			win = Rules.checkWin(grid, false);
			Board.remove(grid, x, y);
			Board.remove(grid, x - 1, y + 1);
			}*/
		}
		//
		if(x != 6) {
			win = brandonvereenvoudig(grid, x, 1, y, 0, color);
			/*if(Board.is_empty(x + 1, y)) {
			grid = Board.add(grid, x, y, color);
			grid = Board.add(grid, x + 1, y, color);
			win = Rules.checkWin(grid, false);
			Board.remove(grid, x, y);
			Board.remove(grid, x + 1, y);
			} */
		}
		//
		if(x != 0) {
			win = brandonvereenvoudig(grid, x, -1, y, 0, color);
			/*
			if(Board.is_empty(x - 1, y)) {
			grid = Board.add(grid, x, y, color);
			grid = Board.add(grid, x - 1, y, color);
			win = Rules.checkWin(grid, false);
			Board.remove(grid, x, y);
			Board.remove(grid, x - 1, y);
			}
			*/
		}
		if(y != 1) {
			win = brandonvereenvoudig(grid, x, 0, y, 1, color);
			/*
			grid = Board.add(grid, x, y, color);
			grid = Board.add(grid, x, y + 1, color);
			win = Rules.checkWin(grid, false);
			Board.remove(grid, x, y);
			Board.remove(grid, x , y + 1);
			*/
		}
		
		return win;
	}
	boolean brandonvereenvoudig(Object[][] grid, int x, int xx, int y, int yy, Object color) {
		if(Board.is_empty(x + xx, y)) {
			grid = Board.add(grid, x, y, color);
			grid = Board.add(grid, x+xx, y+yy, color);
			boolean win = Rules.checkWin(grid, false);
			Board.remove(grid, x, y);
			Board.remove(grid, x+xx, y+yy);
			return win;
		}
		return false;
	}
}
