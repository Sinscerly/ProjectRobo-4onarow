package Connect;

public class Board {
	private static final int ROWS = 7, COLUMS = 6; // dimensions of the board
	private static Box[][] board = new Box[ROWS][COLUMS]; // board that consist
															// of boxes
	public static Empty empty = new Empty(); // state of a box in the board
	public static Red red = new Red(); // state of a box in the board
	public static Yellow yellow = new Yellow(); // state of a box in the board

	// Set everything in the grid to empty
	public static void setupBoard() {
		for (int x = 0; x < ROWS; x++)
			for (int y = 0; y < COLUMS; y++)
				board[x][y] = empty;
	}

	// return board
	public static Box[][] getBoard() {
		return board;
	}

	// places a stone in the selected column if there is space else it will give
	// a error message
	public static int placeMove(int column, Box player) {
		int lowest_free = checkColumnEmpty(column, board);
		if (lowest_free != -1) {
			board[column][lowest_free] = player;
		} else {
			System.out.println("Error there is no free row, see row: " + column);
			printBoard();
			return 0;
		}
		return 1;
	}

	// checks if the chosen grid has space in the desired column
	public static int checkColumnEmpty(int index_x, Box[][] grid) {
		for (int y = 0; y < 6; y++) {
			if (grid[index_x][y] == empty) {
				return y;
			}
		}
		return -1;
	}

	// prints the board (for testing purposes only)
	public static void printBoard() {
		System.out.println("");
		System.out.println("| 1   2   3   4   5   6   7 |");
		for (int y = COLUMS - 1; y >= 0; y--) {
			System.out.print("| ");
			for (int x = 0; x < ROWS; x++) {
				if (board[x][y] == empty)
					System.out.print(empty.name + " | ");
				else if (board[x][y] == red)
					System.out.print(red.name + " | ");
				else
					System.out.print(yellow.name + " | ");
			}
			System.out.println();
		}
	}

	// checks if the board is full
	public static boolean checkFull(Box[][] grid) {
		for (int i = 0; i < ROWS; i++) {
			if (checkColumnEmpty(i, grid) != -1)
				return true;
		}
		System.out.println("It's a tie!");
		return false;
	}
}
