package Connect;

import java.util.Random;

public class AI {

	AI() {
	}

	int doSet(Box[][] grid, Box ai, Box en) {
		Random rand = new Random();
		Box[][] copiedgrid = copyGrid(grid);
		int[] possibleMoves = new int[42];
		int bestPri = 0;
		int index = 0;
		int cFree;
		String Ai, En;
		if (ai == Board.red) {
			Ai = "Red";
			En = "Yellow";
		}
		else {
			Ai = "Yellow";
			En = "Red";
		}
		for (int i = 0; i < 7; i++) {
			cFree = Board.checkColumnEmpty(i, copiedgrid);
			if (cFree != -1) {
				copiedgrid[i][cFree] = ai;
				if(GoodMoves.checkWinningCondition(copiedgrid) == Ai) {
					return i;
				}
				copiedgrid[i][cFree] = Board.empty;
			}
		}
		for (int i = 0; i < 7; i++) {
			cFree = Board.checkColumnEmpty(i, copiedgrid);
			if (cFree != -1) {
				copiedgrid[i][cFree] = en;
				if(GoodMoves.checkWinningCondition(copiedgrid) == En) {
					return i;
				}
				copiedgrid[i][cFree] = Board.empty;
			}
		}
		int[][] priority = new int[7][6];
		for(int y = 0; y < 6; y++) {
			for(int x = 0; x < 7; x++) {
				if(x < 5)
					priority[x][y] = ((x + 1) + (-y + 7));
				else
					priority[x][y] = (((-x + 6) + 1) + (-y + 7));
			}
		}
		for (int i = 0; i < 7; i++) {
			cFree = Board.checkColumnEmpty(i, copiedgrid);
			if (cFree != -1) {
				if(priority[i][cFree] <= 7) {
					possibleMoves[index] = i;
					index++;
				}
				else {
					
				}
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
