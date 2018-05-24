package Connect;

public class AI {

	AI() {
	}

	int doSet(Box[][] grid, Box ai, Box en) {
		Box[][] copiedgrid = copyGrid(grid);
		int cFree;
		String Ai;
		if (ai == Board.red)
			Ai = "Red";
		else
			Ai = "Yellow";
		for (int i = 0; i < 8; i++) {
			cFree = Board.checkColumnEmpty(i, copiedgrid);
			if (cFree != -1) {
				copiedgrid[i][cFree] = ai;
				if(GoodMoves.checkWinningCondition(copiedgrid) == Ai) {
					return i;
				}
				copiedgrid[i][cFree] = Board.empty;
			}
		}
		return 0;
	}

	private Box[][] copyGrid(Box[][] grid) {
		Box[][] copy = new Box[7][6];
		for (int y = 0; y != 6; y++)
			for (int x = 0; x != 7; x++)
				copy[x][y] = grid[x][y];
		return copy;
	}

}
