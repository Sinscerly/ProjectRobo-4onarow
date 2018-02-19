package Connect;

public class board {

	private static int width = 7, height = 6;
	private static char[][] board = new char[width][height];
	
	public board()
	{		
		for(int x = 0; x < width; x++)
		{
			for(int y = 0; y < height; y++)
			{
				board[x][y] = 'o';
			}
		}
	}
	public int place_move(int column, char player)
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
		print_board();
		return 1;
	}
	private static int check_empty(int index_x)
	{
		for(int i = 0; i < 6; i++)
		{
			if(board[index_x][i] == 'o') { return i;} 
		}
		return 6;
	}
	private static void print_board()
	{
		System.out.println("");
		for(int y = height -1; y >= 0; y--)
		{
			System.out.print("| ");
			for(int x = 0; x < width; x++)
			{
				System.out.print(board[x][y] + " | ");
			}
			System.out.println("");
		}
		
	}
}
