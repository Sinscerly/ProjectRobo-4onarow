package Connect;

import java.util.List;

public class AI {
	private int[] moves = new int[7];
	private int dificulty;
	private int valueMove;
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
			//2depht
			if(moves[x] != -1 && check_2_to_win(grid, x, moves[x], color)) {
				return x;
			} 
		}
		return 7;
	}
	int miniMax(Object[][] grid, int diff, Object color)
	{
		if(diff == 0) return valueMove;
		if(color == Board.red)
		{
			for(int i = 0; i != 6; i++)
			{
				if(moves[i] != -1)
				{
					
				}
			}
		}
		else
		{
			for(int i = 0; i != 6; i++)
			{
				if(moves[i] != -1)
				{
					
				}
			}
		}
	}
	int check_value_of_move(Object[][] grid, Object color)
	{
		//2 op een rij = 10
		//3 op een rij = 1000
		int player, value = 0;
		if(color == Board.red)
			player = 1;
		else
			player = -1;
		value = check_move(grid, color);
		return 0;
	}
	int check_move(Object[][] grid, Object color)
	{
		int value = 0;
		//2 in a row = 10
		//open 2 in a row = 20
		//3 in a row = 1000
		//open 3 in a row = 2000
		int i1 = 0,i2 = 0,i3 = 0,i4 = 0;
		for(int row = 0; row < 6; row++)
		{
			for(int col = 0; col < 4; col++)
			{
				//xx00
				//x0x0
				//x00x
				//0x0x
				//00xx
				//0xx0
				for(int i = 0; i != 6; i++)
				{
					switch(i)
					{
					case 0: i1 = 0; i2 = 1; i3 = 2; i4 = 3; break;
					case 1: i2 = 2; i3 = 1; break;
					case 2: i2 = 3; i4 = 2; break;
					case 3: i1 = 1; i3 = 0; break;
					case 4: i1 = 2; i4 = 1; break;
					case 5: i1 = 1; i2 = 2; i4 = 3; break;
					}
					if(grid[col + i1][row] == color &&
							grid[col + i2][row] == color &&
							grid[col + i3][row] == Board.empty &&
							grid[col + i4][row] == Board.empty)
					{
						value += 10;
						if(i == 5) value += 10;
					}
				}
				//xxx0
				//xx0x
				//x0xx
				//0xxx
				for(int i = 0; i != 4; i++)
				{
					switch(i)
					{
					case 0: i1 = 0; i2 = 1; i3 = 2; i4 = 3; break;
					case 1: i3 = 3; i4 = 2; break;
					case 2: i2 = 2; i4 = 1; break;
					case 3: i1 = 1; i4 = 0; break;
					}
					if(grid[col + i1][row] == color &&
							grid[col + i2][row] == color &&
							grid[col + i3][row] == color &&
							grid[col + i4][row] == Board.empty)
					{
						value += 1000;
					}
				}
			}
		}
		//0
		//x
		//x
		for(int row = 0; row < 4; row++)
    	{
    		for(int col = 0; col < 7; col++)
    		{
    			if(grid[col][row] == color &&
    					grid[col][row + 1] == color &&
    					grid[col][row + 2] == Board.empty)
    			{
    				value += 10;
    			}
    		}
    	}
		//0
		//x
		//x
		//x
		for(int row = 0; row < 3; row++)
		{
			for(int col = 0; col < 7; col++)
			{
		 		if(grid[col][row] == color &&
		   				grid[col][row + 1] == color &&
		   				grid[col][row + 2] == color &&
		   				grid[col][row + 3] == Board.empty)
		 		{
		    		value += 1000;
		    	}
		 	}
		}
		//(/)
		//xx00
		//x0x0
		//x00x
		//0x0x
		//00xx
		//0xx0
		for(int row = 0; row < 2; row ++)
		{
			for(int col = 0; col < 4; col++)
			{
				for(int i = 0; i != 6; i++)
				{
					switch(i)
					{
					case 0: i1 = 0; i2 = 1; i3 = 2; i4 = 3; break;
					case 1: i2 = 2; i3 = 1; break;
					case 2: i2 = 3; i4 = 2; break;
					case 3: i1 = 1; i3 = 0; break;
					case 4: i1 = 2; i4 = 1; break;
					case 5: i1 = 1; i2 = 2; i4 = 3; break;
					}
					if(grid[col + i1][row + i1] == color &&
							grid[col + i2][row + i2] == color &&
							grid[col + i3][row + i3] == Board.empty &&
							grid[col + i4][row + i4] == Board.empty)
					{
						value += 10;
						if(i == 5) value += 10;
					}
				}
				//xxx0
				//xx0x
				//x0xx
				//0xxx
				for(int i = 0; i != 4; i++)
				{
					switch(i)
					{
					case 0: i1 = 0; i2 = 1; i3 = 2; i4 = 3; break;
					case 1: i3 = 3; i4 = 2; break;
					case 2: i2 = 2; i4 = 1; break;
					case 3: i1 = 1; i4 = 0; break;
					}
					if(grid[col + i1][row + i1] == color &&
							grid[col + i2][row + i2] == color &&
							grid[col + i3][row + i3] == color &&
							grid[col + i4][row + i4] == Board.empty)
					{
						value += 10;
					}
				}
			}
		}
		//(\)
		//xx00
		//x0x0
		//x00x
		//0x0x
		//00xx
		//0xx0
		for(int row = 0; row < 2; row ++)
		{
			for(int col = 3; col < 8; col++)
			{
				for(int i = 0; i != 6; i++)
				{
					switch(i)
					{
					case 0: i1 = 0; i2 = 1; i3 = 2; i4 = 3; break;
					case 1: i2 = 2; i3 = 1; break;
					case 2: i2 = 3; i4 = 2; break;
					case 3: i1 = 1; i3 = 0; break;
					case 4: i1 = 2; i4 = 1; break;
					case 5: i1 = 1; i2 = 2; i4 = 3; break;
					}
					if(grid[col - i1][row + i1] == color &&
							grid[col - i2][row + i2] == color &&
							grid[col - i3][row + i3] == Board.empty &&
							grid[col - i4][row + i4] == Board.empty)
					{
						value += 10;
						if(i == 5) value += 10;
					}
				}
				//xxx0
				//xx0x
				//x0xx
				//0xxx
				for(int i = 0; i != 4; i++)
				{
					switch(i)
					{
					case 0: i1 = 0; i2 = 1; i3 = 2; i4 = 3; break;
					case 1: i3 = 3; i4 = 2; break;
					case 2: i2 = 2; i4 = 1; break;
					case 3: i1 = 1; i4 = 0; break;
					}
					if(grid[col + i1][row + i1] == color &&
							grid[col + i2][row + i2] == color &&
							grid[col + i3][row + i3] == color &&
							grid[col + i4][row + i4] == Board.empty)
					{
						value += 10;
					}
				}
			}
		}
		for(int row = 0; row < 6; row++)
		{
			for(int col = 0; col < 3; col++)
			{
				if(grid[col][row] == Board.empty &&
						grid[col + 1][row] == color &&
						grid[col + 2][row] == color &&
						grid[col + 3][row] == color &&
						grid[col + 4][row] == Board.empty)
				{
					value += 10;
				}
			}
		}
		return value;
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
		if(x < 6 && y < 5) {
			win = do_2_to_win(grid, x, 1, y, 1, color);
		}
		//
		if(x > 0 && y < 5) {
			win = do_2_to_win(grid, x, -1, y, 1, color);
		}
		//
		if(x > 6) {
			win = do_2_to_win(grid, x, 1, y, 0, color);
		}
		//
		if(x > 0) {
			win = do_2_to_win(grid, x, -1, y, 0, color);
		}
		if(y < 5) {
			win = do_2_to_win(grid, x, 0, y, 1, color);
		}
		
		return win;
	}
	boolean do_2_to_win(Object[][] grid, int x, int xx, int y, int yy, Object color) {
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
