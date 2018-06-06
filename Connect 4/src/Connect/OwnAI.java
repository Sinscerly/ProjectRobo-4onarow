package Connect;

import java.util.Random;

public class OwnAI extends AI{

	OwnAI() {
	}

	public int doSet(Box[][] grid, Box whoBegan, Box ai, Box en) {
		Random rand = new Random();
		Box[][] copiedgrid = copyGrid(grid);
		int[] possibleMoves = new int[42];
		int bestPri = 0;
		int bestI = 0;
		int index = 0;
		int cFree;
		int move, doNotMove;
		String Ai, En;
		int[][] priority = fillPri();
		if (ai == Board.red) {
			Ai = "Red";
			En = "Yellow";
		} else {
			Ai = "Yellow";
			En = "Red";
		}
		move = checkWinOrLose(copiedgrid, ai, Ai);
		if(move != -1)
			return move;
		move = checkWinOrLose(copiedgrid, en, En);
		if(move != -1)
			return move;
		doNotMove = checkForward(copiedgrid, ai, en, En);
		for (int i = 0; i < 7; i++) {
			cFree = Board.checkColumnEmpty(i, copiedgrid);
			if (cFree != -1) {
				if (priority[i][cFree] <= 7) {
					index++;
					possibleMoves[index] = i;
				} else {
					if (priority[i][cFree] > bestPri) {
						bestPri = priority[i][cFree];
						bestI = i;
					}
				}
			}
		}
		int pIndex = index;
		if (bestPri > 0 && bestI != doNotMove) {
			return bestI;
		} else {
				while (true) {
					rand.nextInt(index);
					if (possibleMoves[index] != doNotMove && index != 0 || index == 1) {
						System.out.println(possibleMoves[index]);
						return possibleMoves[index];
					}
				}
		}
	}

	private Box[][] copyGrid(Box[][] grid) {
		Box[][] copy = new Box[7][6];
		for (int y = 0; y != 6; y++)
			for (int x = 0; x != 7; x++)
				copy[x][y] = grid[x][y];
		return copy;
	}

	private int[][] fillPri() {
		int[][] priority = new int[7][6];
		for(int y = 0; y < 6; y++) {
			for(int x = 0; x < 7; x++) {
				if(x < 4)
					priority[x][y] = ((x*2 + 1) + (-y + 7));
				else
					priority[x][y] = (((-x + 6)*2 + 1) + (-y + 7));
			}
		}
		priority[2][0] = 13;
		priority[4][0] = 13;
		priority[3][1] = 14;
		return priority;
	}
	
	private int checkWinOrLose(Box[][] copiedgrid, Box ai, String Ai)
	{
		for (int i = 0; i < 7; i++) {
			int cFree = Board.checkColumnEmpty(i, copiedgrid);
			if (cFree != -1) {
				copiedgrid[i][cFree] = ai;
				if (GoodMoves.checkWinningCondition(copiedgrid).contains(Ai)) {
					return i;
				}
				copiedgrid[i][cFree] = Board.empty;
			}
		}
		return -1;
	}
	
	private int checkForward(Box[][] copiedgrid, Box ai, Box en, String En)
	{
		for (int i = 0; i < 7; i++) {
			int cFree = Board.checkColumnEmpty(i, copiedgrid);
			if (cFree != -1) {
				copiedgrid[i][cFree] = ai;
				int move = checkWinOrLose(copiedgrid, en, En);
				System.out.println(i + "" + move);
				if(move != -1) 
					return i;
				copiedgrid[i][cFree] = Board.empty;
			}
		}
		return -1;
	}

}
