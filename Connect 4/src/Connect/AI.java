package Connect;

public class AI {
	private int difficulty;

	AI(int set_dificulty) {
		// sets the total amount of turns the AI 'thinks' forward.
		difficulty = set_dificulty;
	}

	// returns the turn from the AI.
	int doSet(Box[][] grid, Box whoBegan) {
		long start = System.currentTimeMillis();
		int bestMove = miniMax(grid, whoBegan);
		long end = System.currentTimeMillis();
		System.out.println("Took: " + ((end - start) / 1000) + "S.");
		return bestMove;
	}
	// copies the grid so not to change the original
	private Box[][] copyGrid(Box[][] grid) {
		Box[][] copy = new Box[7][6];
		for (int y = 0; y != 6; y++)
			for (int x = 0; x != 7; x++)
				copy[x][y] = grid[x][y];
		return copy;
	}

	// decides the first turn of the game for the AI.
	public int miniMax(Box[][] grid, Box whoBegan) {
		int turns = 0;
		// checks if the player took more than one turn.
		for (int col = 0; col < 7; col++) {
			for (int row = 0; row < 6; row++) {
				if (grid[col][row] == Board.yellow) {
					turns++;
				}
			}
		}
		// if no turns has been had or one by the player than place a stone.
		if (turns == 0 && whoBegan == Board.red || turns == 1 && whoBegan == Board.yellow) {
			if (grid[2][5] == Board.yellow) {
				return 4;
			} else if (grid[4][5] == Board.yellow) {
				return 2;
			} else {
				return 3;
			}
		}
		// if the AI took a turn.
		return miniMax_move(grid, 0, whoBegan);
	}

	// decides which move is the best move. (The Board, total amount of turns the AI 'thinks' forward, who's turn it is checking)
	int miniMax_move(Box[][] grid, int diff, Box color) {
		//copies the grid to a temporary grid
		Box[][] copy = copyGrid(grid);
		int bestMove = 0;
		int bestValue = -1000000;
		int player;
		Box nextColor;
		//checks who's turn it is.
		if (color == Board.red) {
			player = 2;
			nextColor = Board.yellow;
		} else {
			player = 1;
			nextColor = Board.red;
		}
		//check if there is a win and assign that as the route
		if (GoodMoves.checkWin(copy, false))
			if (GoodMoves.checkWinningCondition(copy) == "Red")
				bestValue = 1000000 - diff;
			else
				bestValue = -1000000 + diff;
		//else check if there has been a tie and assign that as the route
		else if (!Board.checkFull(copy))
			bestValue = 0;
		//else check if this is the last turn that the AI 'thinks' forward
		else if (diff == difficulty) {
			//check the value of the last turn and if it is not 0 than change the value = player * (i - diff) else make best value 0
			//red = 2
			//yellow = 1
			int i = check_value(copy, color);
			if (i != 0)
				bestValue = player * (i - diff);
			else
				bestValue = i;
		} else {
			//for every place that is free place a stone and repeat until diff == dificulty, c stands for column
			for (int c = 0; c < 7; c++) {
				//if the column has space left, r stands for row
				int r = Board.checkColumnEmpty(c, copy);
				if (r != -1) {
					Box[][] newGrid = new Box[7][6];
					//if this is not the last turn
					if (diff < difficulty) {
						//copy the grid to newGrid
						newGrid = copyGrid(grid);
						//place a piece on (c,r)
						newGrid[c][r] = color;
						//do this over again but than the returning number times -1 since it is the other player, v stands for value
						int v = -miniMax_move(newGrid, diff + 1,  nextColor);
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
	//check the value of the given grid
	int check_value(Box[][] grid, Box color) {
		int value = 0;
		// vertical modifier = 10
		// diagonal modifier = 20
		// horizontal modifier = 30
		// 2 in a row = 10
		// open 2 in a row = 20
		// 3 in a row = 1000
		// open 3 in a row = 2000
		int i1 = 0, i2 = 0, i3 = 0, i4 = 0;
		int v = 10, d = 20, h = 30;
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
						value += 10 * h;
						if (i == 5) { value += 10 * h; }
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
						value += 1000 * h;
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
					value += 10 * v;
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
					value += 1000 * v;
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
						value += 10 * d;
						if (i == 5) { value += 10 * d; }
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
						value += 1000 * d;
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
						value += 10 * d;
						if (i == 5) { value += 10 * d; }
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
						value += 1000 * d;
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
					value += 2000 * h;
				}
		return value;
	}
}
