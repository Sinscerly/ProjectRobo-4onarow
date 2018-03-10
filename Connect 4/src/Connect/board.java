package Connect;

public class board {

	private static int width = 7, height = 6;
	private static Object[][] board = new Object[width][height];
	public static Empty empty = new Empty();										//state of a box in the board
	public static Red red = new Red();												//state of a box in the board
	public static Yellow yellow = new Yellow();									//state of a box in the board
	
	public board()
	{		
		for(int x = 0; x < width; x++)
		{
			for(int y = 0; y < height; y++)
			{
				board[x][y] = empty;
				
			}
		}
	}
	public static Object[][] getBoard()
	{
		return board;
	}
	public int place_move(int column, Object player)
	{
		//the x tells wich player does the move.
		int lowest_free = check_empty(column-1);
		if(lowest_free != 6)
		{
			board[column-1][lowest_free] = player;
		}
		else
		{
			System.out.println("Error there is no free row, see row: "+ column);
			print_board();
			return 0;
		}
		return 1;
	}
	public static int[] free_slots() {
		int[] moves = new int[width];
		for(int x = 0; x < 7; x++) {
			moves[x] = check_empty(x);
		}
		return moves;
	}
	private static int check_empty(int index_x)
	{
		for(int y = 0; y < 6; y++)
		{
			if(board[index_x][y] == empty) { return y;} 
		}
		return 6;
	}
	public static boolean is_empty(int index_x)
	{
		for(int y = 0; y < 6; y++) {
			if(board[index_x][y] == empty) {return true;}
		}
		return false;
	}
	public static void print_board()
	{
		System.out.println("");
		System.out.println("| 1   2   3   4   5   6   7 |");
		for(int y = height -1; y >= 0; y--)
		{
			System.out.print("| ");
			for(int x = 0; x < width; x++)
			{
				if(board[x][y] == empty) System.out.print(empty.name + " | ");
				else if(board[x][y] == red) System.out.print(red.name + " | ");
				else System.out.print(yellow.name + " | ");
			}
			System.out.println();
		}
	}
	public static Object[][] add(Object[][] grid, int x, int y) {
		grid[x][y] = red;
		return grid;
	}
}
