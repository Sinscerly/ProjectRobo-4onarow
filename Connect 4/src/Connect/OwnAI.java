package Connect;

import java.util.Random;

public class OwnAI extends AI {

	OwnAI() {
	}

	public int doSet(Box[][] grid, Box whoBegan, Box ai, Box en) {
		Random rand = new Random();
		Box[][] copiedgrid = copyGrid(grid);
		int[] pMoves = new int[42];
		int bestPri = 0, bestI = 0;
		int index = 0;
		int cFree;
		int move;
		int in = 0;
		int[] placeholder = new int[7];
		int[] doNotMoves;
		String Ai, En;
		int[][] priority = calcPriority();
		if (ai == Board.red) {
			Ai = "Red";
			En = "Yellow";
		} else {
			Ai = "Yellow";
			En = "Red";
		}
		move = checkWinOrLose(copiedgrid, ai, Ai);
		if (move != -1)
			return move;
		move = checkWinOrLose(copiedgrid, en, En);
		if (move != -1)
			return move;
		doNotMoves = checkForward(copiedgrid, ai, en, En);
		for (int i = 0; i < 7; i++) {
			cFree = Board.checkColumnEmpty(i, copiedgrid);
			if (cFree != -1) {
				if (priority[i][cFree] > bestPri) {
					bestPri = priority[i][cFree];
					bestI = i;
				}
			}
		}
		for (int i = 0; i < 7; i++) {
			cFree = Board.checkColumnEmpty(i, copiedgrid);
			if (cFree != -1) {
				if (i != bestI) {
					index++;
					pMoves[index] = i;
				}
			}
		}
		for (int i = 0; i < 7; i++) {
			if (match(doNotMoves, i)) {
				placeholder[in++] = doNotMoves[i];
			}
		}
		int pIndex = index, pIn = in;
		if (index != 0 && checkNPM(copiedgrid, in) != 0 || bestPri != 0 && checkNPM(copiedgrid, in) != 0)
			while (true) {
				in = pIn;
				if (in != 0) {
					index = pIndex;
					if (bestPri > 0 && canPlace(bestI, placeholder, in)) {
						return bestI;
					} else {
						index = rand.nextInt(index + 1);
						if (canPlace(pMoves[index], placeholder, in) && onlyTest(index) && index != 0) {
							return pMoves[index];
						}
					}
				} else {
					if (bestPri > 0) {
						return bestI;
					} else {
						rand.nextInt(index);
						if (index != 0) {
							return pMoves[index];
						}
					}
				}
			}
		return randomMove(copiedgrid);
	}

	private Box[][] copyGrid(Box[][] grid) {
		Box[][] copy = new Box[7][6];
		for (int y = 0; y != 6; y++)
			for (int x = 0; x != 7; x++)
				copy[x][y] = grid[x][y];
		return copy;
	}

	private int[][] calcPriority() {
		int[][] priority = new int[7][6];
		for (int y = 0; y < 6; y++) {
			for (int x = 0; x < 7; x++) {
				if (x < 4)
					priority[x][y] = ((x * 2 + 1) + (-y + 7));
				else
					priority[x][y] = (((-x + 6) * 2 + 1) + (-y + 7));
			}
		}
		priority[2][0] = 13;
		priority[4][0] = 13;
		priority[3][1] = 14;
		return priority;
	}

	private int checkWinOrLose(Box[][] grid, Box ai, String Ai) {
		for (int i = 0; i < 7; i++) {
			int cFree = Board.checkColumnEmpty(i, grid);
			if (cFree != -1) {
				grid[i][cFree] = ai;
				if (GoodMoves.checkWinningCondition(grid).contains(Ai)) {
					return i;
				}
				grid[i][cFree] = Board.empty;
			}
		}
		return -1;
	}

	private int[] checkForward(Box[][] grid, Box ai, Box enemy, String En) {
		Box[][] copiedgrid = copyGrid(grid);
		int[] moves = new int[] { 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7 };
		int index = 0;
		for (int i = 0; i < 7; i++) {
			int cFree = Board.checkColumnEmpty(i, copiedgrid);
			if (cFree != -1) {
				copiedgrid[i][cFree] = ai;
				int move = checkWinOrLose(copiedgrid, enemy, En);
				if (move != -1) {
					moves[index] = i;
					index++;
				}
				copiedgrid = copyGrid(grid);
			}
		}
		return moves;
	}

	private boolean match(int[] array, int i) {
		if (array[i] > -1 && array[i] < 7)
			return true;
		return false;
	}

	private boolean canPlace(int i, int[] place, int index) {
		int pIndex = index;
		while (pIndex > 0) {
			if (i == place[pIndex - 1])
			{
				return false;
			}
			pIndex--;
		}
		return true;
	}

	private int randomMove(Box[][] grid) {
		Random rand = new Random();
		int move = 0;
		int[] moves = new int[7];
		int cFree;
		int index = 0;
		for (int i = 0; i < 7; i++) {
			cFree = Board.checkColumnEmpty(i, grid);
			if (cFree != -1)
				moves[index++] = i;
		}
		move = moves[rand.nextInt(index)];
		return move;
	}

	private int checkNPM(Box[][] grid, int nPMoves) {
		int[] moves = new int[7];
		int index = 0;
		int cFree;
		for (int i = 0; i < 7; i++) {
			cFree = Board.checkColumnEmpty(i, grid);
			if (cFree != -1)
				moves[index++] = i;
		}
		if (index == nPMoves) {
			return 0;
		} else {
			return 1;
		}
	}
	private boolean onlyTest(int i) {
		return true;
	}

}
