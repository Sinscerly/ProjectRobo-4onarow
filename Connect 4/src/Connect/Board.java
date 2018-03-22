package Connect;

public class Board {
enum move{
	;
	int co_x; //x coördinaat van de zet.
	int co_y; //y coördinaat van de zet.
}
	private static final int ROWS = 7, COLUMS = 6; 	//dimensions of the grid
	private static Object[][] board = new Object[ROWS][COLUMS];
	private static Object[][] copiedBoard = new Object[ROWS][COLUMS];
	public static Empty empty = new Empty();										//state of a box in the board
	public static Red red = new Red();												//state of a box in the board
	public static Yellow yellow = new Yellow();									//state of a box in the board
	public move last_move;
	
	public Board()
	{		
		for(int x = 0; x < ROWS; x++)
		{
			for(int y = 0; y < COLUMS; y++)
			{
				board[x][y] = empty;
				
			}
		}
	}
	public static Object[][] getCopiedBoard() {
		copiedBoard = board;
		return copiedBoard;
	}
	public static Object[][] getBoard() {
		return board;
	}
	public int place_move(int column, Object player) {
		//the x tells wich player does the move.
		int lowest_free = check_empty(column);
		if(lowest_free != -1)
		{
			board[column][lowest_free] = player;
			//last_move.co_x = column;
			//last_move.co_y = lowest_free;
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
		int[] moves = new int[ROWS];
		for(int x = 0; x < 7; x++) {
			moves[x] = check_empty(x);
		}
		return moves;
	}
	public static int check_empty(int index_x)
	{
		for(int y = 0; y < 6; y++)
		{
			if(board[index_x][y] == empty) { return y;} 
		}
		return -1;
	}
	public static int check_empty(int index_x, Object[][] grid)
	{
		for(int y = 0; y < 6; y++)
		{
			if(grid[index_x][y] == empty) { return y;} 
		}
		return -1;
	}
	/* Deze kan nog versimpeld worden met check_empty */
	public static boolean is_empty(int index_x)
	{
		for(int y = 0; y < 6; y++) {
			if(board[index_x][y] == empty) {return true;}
		}
		return false;
	}
	public static boolean is_empty(int index_x, int index_y)
	{
		if(board[index_x][index_y] == empty) {return true;}
		return false;
	}
	public static void print_board()
	{
		System.out.println("");
		System.out.println("| 1   2   3   4   5   6   7 |");
		for(int y = COLUMS -1; y >= 0; y--)
		{
			System.out.print("| ");
			for(int x = 0; x < ROWS; x++)
			{
				if(board[x][y] == empty) System.out.print(empty.name + " | ");
				else if(board[x][y] == red) System.out.print(red.name + " | ");
				else System.out.print(yellow.name + " | ");
			}
			System.out.println();
		}
	}
	public static Object[][] add(Object[][] grid, int x, int y, Object color) {
		copiedBoard[x][y] = color;
		return copiedBoard;
	}
	public static void remove(Object[][] grid, int x, int y) {
		copiedBoard[x][y] = empty;
	}
	public boolean check_not_full() {
		for(int i = 0; i < COLUMS; i++) {
			if(check_empty(i) != -1) { return true; }
		}
		return false;
	}
	public static boolean check_not_full(Object[][] grid)
	{
		for(int i = 0; i < COLUMS; i++) {
			if(check_empty(i, grid) != -1) return true;
		}
		return false;
	}
	public static boolean check_if_can_place(Object[][] grid, int place)
	{
		if(check_empty(place, grid) != -1) return true;
		return false;
	}
}
