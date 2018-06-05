package tests;


public class Algoritm {

	public static void main(String[] args) {
		int possible4 = 69;
		int[][] priority = new int[7][6];
		for(int y = 0; y < 6; y++) {
			for(int x = 0; x < 7; x++) {
				if(x < 4)
					priority[x][y] = ((x*2 + 1) + (-y + 7));
				else
					priority[x][y] = (((-x + 6)*2 + 1) + (-y + 7));
			}
		}
		printBoard(priority);
	}
	
	public static void printBoard(int[][] board) {
		System.out.println("");
		System.out.println("| 1   2   3   4   5   6   7 |");
		for (int y = 0; y < 6; y++) {
			System.out.print("| ");
			for (int x = 0; x < 7; x++) {
				System.out.print(board[x][y] + " | ");
			}
			System.out.println();
		}
	}
	
	
}
