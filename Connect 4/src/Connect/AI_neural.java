package Connect;

import java.util.Random;

public class AI_neural {

	// 42 inputs to 7 hidden values to 1 out.

	// 42 inputs
	Box[][] inGrid = new Box[7][6];
	// 7 hidden values
	int[] hV = new int[7];
	// 1 out
	int out;

	AI_neural() {

	}

	public int guess() {
		Random rand = new Random();
		int i = rand.nextInt(6);
		return i;
	}

	public void setInputs(Box[][] board) {
		for (int y = 0; y != 6; y++)
			for (int x = 0; x != 7; x++)
				inGrid[x][y] = board[x][y];
	}

	public void calcHiddenNodes() {
		//vertical 2
		for(int i = 0; i!=7;i++)
		{
			if(this.inGrid[i][row] == this.inGrid[i][row + 1] && this.inGrid[i][row + 2] == Board.empty)
			{
				this.hV[0]++;
			}
		}

	}

	public void calcOut() {
		
	}
}
