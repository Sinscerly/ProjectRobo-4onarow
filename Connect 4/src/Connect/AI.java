package Connect;

public class AI {
	private int dificulty;
	private Object[][] globalGrid = new Object[7][6];

	AI(int set_dificulty) {
		// sets the total amount of turns the AI 'thinks' forward.
		dificulty = set_dificulty;
	}

	// decides a turn for the AI.
	int doSet(Object[][] grid, Object whoBegan) {
		globalGrid = grid;
		int do_move = miniMax(whoBegan);
		return do_move;
	}
	// copies the grid so not to change the original
	private Object[][] copyGrid(Object[][] grid) {
		Object[][] copy = new Object[7][6];
		for (int y = 0; y != 6; y++)
			for (int x = 0; x != 7; x++)
				copy[x][y] = grid[x][y];
		return copy;
	}

	// decides the first turn of the game for the AI.
	public int miniMax(Object whoBegan) {
		int count = 0;
		// checks if the player took more than one turn.
		for (int col = 0; col < 7; col++) {
			if (globalGrid[col][0] == Board.yellow) {
				count++;
			}
		}
		// if no turns has been had or one by the player than place a piece.
		if (count == 0 && whoBegan == Board.red || count == 1 && whoBegan == Board.yellow) {
			if (globalGrid[2][5] == Board.yellow) {
				return 4;
			} else if (globalGrid[4][5] == Board.yellow) {
				return 2;
			} else {
				return 3;
			}
		}
		// if the AI took a turn.
		return miniMax_move(globalGrid, 0, -1000000, whoBegan);
	}

	// decides which move is the best move.
	int miniMax_move(Object[][] grid, int diff, int alpha, Object color) {
		//copies the grid to a temporary grid
		Object[][] copy = copyGrid(grid);
		int bestMove = 0;
		int bestValue = alpha;
		int player;
		Object nextColor;
		//checks who's turn it is.
		if (color == Board.red) {
			player = 2;
			nextColor = Board.yellow;
		} else {
			player = 1;
			nextColor = Board.red;
		}
		//check if there is a win and assign that as the route
		if (Rules.checkWin(copy, false))
			if (Rules.checkWin(copy) == "Red")
				bestValue = 1000000 - diff;
			else
				bestValue = -1000000 + diff;
		//else check if there has been a tie and assign that as the route
		else if (!Board.check_not_full(copy))
			bestValue = 0;
		//else check if this is the last turn that the AI 'thinks' forward
		else if (diff == dificulty) {
			//check the value of the last turn and if it is not 0 than change the value = player * (i - diff) else make best value 0
			//red = 2
			//yellow = 1
			int i = check_value(copy, color);
			if (i != 0)
				bestValue = player * (i - diff);
			else
				bestValue = i;
		} else {
			//for every place that is free place a piece and repeat until diff == dificulty
			for (int c = 0; c < 7; c++) {
				//if the column has space left
				int r = Board.check_empty(c, copy);
				if (r != -1) {
					Object[][] nGrid = new Object[7][6];
					//if this is not the last turn
					if (diff < dificulty) {
						//copy the grid to nGrid
						nGrid = copyGrid(grid);
						//place a piece on (c,r)
						nGrid[c][r] = color;
						//do this over again but than the returning number times -1 since it is the other player
						int v = -miniMax_move(nGrid, diff + 1, -1000000, nextColor);
						//if the next move has a higher value
						if (v >= bestValue) {
							bestMove = c;
							bestValue = v;
						}
					}
				}
			}
		}
		//if this is the first instance of miniMax_move than return the best move else return the bestValue
		if (diff == 0)
			return bestMove;
		else
			return bestValue;
	}
	//check the value of the board
	int check_value(Object[][] grid, Object color) {
		int value = 0;
		// 2 in a row = 10
		// open 2 in a row = 20
		// 3 in a row = 1000
		// open 3 in a row = 2000
		int i1 = 0, i2 = 0, i3 = 0, i4 = 0;
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 4; col++) {
				//what the switch + the if statement represents
				// xx00
				// x0x0
				// x00x
				// 0x0x
				// 00xx
				// 0xx0
				for (int i = 0; i != 6; i++) {
					switch (i) {
					case 0:
						i1 = 0;
						i2 = 1;
						i3 = 2;
						i4 = 3;
						break;
					case 1:
						i2 = 2;
						i3 = 1;
						break;
					case 2:
						i2 = 3;
						i4 = 2;
						break;
					case 3:
						i1 = 1;
						i3 = 0;
						break;
					case 4:
						i1 = 2;
						i4 = 1;
						break;
					case 5:
						i1 = 1;
						i2 = 2;
						i4 = 3;
						break;
					}
					//check if one of the horizontal 2 in a row possibility's is true
					if (grid[col + i1][row] == color && grid[col + i2][row] == color
							&& grid[col + i3][row] == Board.empty && grid[col + i4][row] == Board.empty) {
						value += 10;
						if (i == 5) { value += 10; }
					}
				}
				//what the switch + the if statement represents
				// xxx0
				// xx0x
				// x0xx
				// 0xxx
				for (int i = 0; i != 4; i++) {
					switch (i) {
					case 0:
						i1 = 0;
						i2 = 1;
						i3 = 2;
						i4 = 3;
						break;
					case 1:
						i3 = 3;
						i4 = 2;
						break;
					case 2:
						i2 = 2;
						i4 = 1;
						break;
					case 3:
						i1 = 1;
						i4 = 0;
						break;
					}
					//check if one of the horizontal 3 in a row possibility's is true
					if (grid[col + i1][row] == color && grid[col + i2][row] == color && grid[col + i3][row] == color
							&& grid[col + i4][row] == Board.empty) {
						value += 1000;
					}
				}
			}
		}
		//what the if statement represents
		// 0
		// x
		// x
		for (int row = 0; row < 4; row++)
			for (int col = 0; col < 7; col++)
				//check if one of the vertical 2 in a row possibility's is true
				if (grid[col][row] == color && grid[col][row + 1] == color && grid[col][row + 2] == Board.empty) {
					value += 10;
				}
		//what the if statement represents
		// 0
		// x
		// x
		// x
		for (int row = 0; row < 3; row++)
			for (int col = 0; col < 7; col++)
				//check if one of the vertical 3 in a row possibility's is true
				if (grid[col][row] == color && grid[col][row + 1] == color && grid[col][row + 2] == color
						&& grid[col][row + 3] == Board.empty) {
					value += 1000;
				}
		//what the switch + the if statement represents
		// (/)
		// xx00
		// x0x0
		// x00x
		// 0x0x
		// 00xx
		// 0xx0
		for (int row = 0; row < 2; row++)
			for (int col = 0; col < 4; col++) {
				for (int i = 0; i != 6; i++) {
					switch (i) {
					case 0:
						i1 = 0;
						i2 = 1;
						i3 = 2;
						i4 = 3;
						break;
					case 1:
						i2 = 2;
						i3 = 1;
						break;
					case 2:
						i2 = 3;
						i4 = 2;
						break;
					case 3:
						i1 = 1;
						i3 = 0;
						break;
					case 4:
						i1 = 2;
						i4 = 1;
						break;
					case 5:
						i1 = 1;
						i2 = 2;
						i4 = 3;
						break;
					}
					//check if one of the diagonal 2 in a row possibility's is true
					if (grid[col + i1][row + i1] == color && grid[col + i2][row + i2] == color
							&& grid[col + i3][row + i3] == Board.empty && grid[col + i4][row + i4] == Board.empty) {
						value += 10;
						if (i == 5) { value += 10; }
					}
				}
				//what the switch + the if statement represents
				// xxx0
				// xx0x
				// x0xx
				// 0xxx
				for (int i = 0; i != 4; i++) {
					switch (i) {
					case 0:
						i1 = 0;
						i2 = 1;
						i3 = 2;
						i4 = 3;
						break;
					case 1:
						i3 = 3;
						i4 = 2;
						break;
					case 2:
						i2 = 2;
						i4 = 1;
						break;
					case 3:
						i1 = 1;
						i4 = 0;
						break;
					}
					//check if one of the diagonal 3 in a row possibility's is true
					if (grid[col + i1][row + i1] == color && grid[col + i2][row + i2] == color
							&& grid[col + i3][row + i3] == color && grid[col + i4][row + i4] == Board.empty) {
						value += 1000;
					}
				}
			}
		//what the switch + the if statement represents
		// (\)
		// xx00
		// x0x0
		// x00x
		// 0x0x
		// 00xx
		// 0xx0
		for (int row = 0; row < 2; row++)
			for (int col = 3; col < 6; col++) {
				for (int i = 0; i != 6; i++) {
					switch (i) {
					case 0:
						i1 = 0;
						i2 = 1;
						i3 = 2;
						i4 = 3;
						break;
					case 1:
						i2 = 2;
						i3 = 1;
						break;
					case 2:
						i2 = 3;
						i4 = 2;
						break;
					case 3:
						i1 = 1;
						i3 = 0;
						break;
					case 4:
						i1 = 2;
						i4 = 1;
						break;
					case 5:
						i1 = 1;
						i2 = 2;
						i4 = 3;
						break;
					}
					//check if one of the diagonal 2 in a row possibility's is true
					if (grid[col - i1][row + i1] == color && grid[col - i2][row + i2] == color
							&& grid[col - i3][row + i3] == Board.empty && grid[col - i4][row + i4] == Board.empty) {
						value += 10;
						if (i == 5) { value += 10; }
					}
				}
				//what the switch + the if statement represents
				// xxx0
				// xx0x
				// x0xx
				// 0xxx
				for (int i = 0; i != 4; i++) {
					switch (i) {
					case 0:
						i1 = 0;
						i2 = 1;
						i3 = 2;
						i4 = 3;
						break;
					case 1:
						i3 = 3;
						i4 = 2;
						break;
					case 2:
						i2 = 2;
						i4 = 1;
						break;
					case 3:
						i1 = 1;
						i4 = 0;
						break;
					}
					//check if one of the diagonal 3 in a row possibility's is true
					if (grid[col - i1][row + i1] == color && grid[col - i2][row + i2] == color
							&& grid[col - i3][row + i3] == color && grid[col - i4][row + i4] == Board.empty) {
						value += 1000;
					}
				}
			}

		//what the if statement represents
		//0xxx0
		for (int row = 0; row < 6; row++)
			for (int col = 0; col < 3; col++)
				//check if one of the horizontal open 3 in a row possibility's is true
				if (grid[col][row] == Board.empty && grid[col + 1][row] == color && grid[col + 2][row] == color
						&& grid[col + 3][row] == color && grid[col + 4][row] == Board.empty) {
					value += 2000;
				}
		return value;
	}
}
