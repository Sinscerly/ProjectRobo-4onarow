package spel;

import java.util.Scanner;

public class Spel {
	static Scanner reader = new Scanner(System.in);
	static Box Yellow = new Box(new Yellow());
	static Box Red = new Box(new Red());
	static Box Empty = new Box(new Empty());
	static Box[][] board = {{Empty,Empty,Empty,Empty,Empty,Empty,Empty},
			{Empty,Empty,Empty,Empty,Empty,Empty,Empty},
			{Empty,Empty,Empty,Empty,Empty,Empty,Empty},
			{Empty,Empty,Empty,Empty,Empty,Empty,Empty},
			{Empty,Empty,Empty,Empty,Empty,Empty,Empty},
			{Empty,Empty,Empty,Empty,Empty,Empty,Empty}};
	
	public static void nextMoveRed()
	{
		int collum = 0;
		
		while(collum < 1 || collum > 7)
		{
			System.out.println("Enter a number 1-7: ");
			collum = reader.nextInt();
			if(collum > 7 || collum < 1)
			{
				System.out.println("Number out of bounds.");
			}
			addRed(collum);
		}
		
	}
	
	public static void nextMoveYellow()
	{
		int collum = 0;
		
		while(collum < 1 || collum > 7)
		{
			System.out.println("Enter a number 1-7: ");
			collum = reader.nextInt();
			if(collum > 7 || collum < 1)
			{
				System.out.println("Number out of bounds.");
			}
			addYellow(collum);
		}
	}
	
	public static void addRed(int collum)
	{
		for(int y = 5; y != -1; y--)
		{
			if(checkIfEmpty(collum - 1, y) == Empty)
			{
				board[y][collum - 1] = Red;
				break;
			}
		}
		printBoard();
		checkWinningCondition(Red);
	}
	
	public static void addYellow(int collum)
	{
		for(int y = 5; y != -1; y--)
		{
			if(checkIfEmpty(collum - 1, y) == Empty)
			{
				board[y][collum - 1] = Yellow;
				break;
			}
		}
		printBoard();
		checkWinningCondition(Yellow);
	}
	
	public static Object checkIfEmpty(int x, int y)
	{
		if(board[y][x] == Empty)
		{
			return Empty;
		}
		return null;
	}
	
	public static void printBoard()
	{
		String row = "";
		for(int y = 0; y != 6; y++)
		{
			for(int x = 0; x != 7; x++)
			{
				if(board[y][x] == Empty) row = row + "| O ";
				else if(board[y][x] == Red) row = row + "| R ";
				else row = row + "| Y ";
			}
			System.out.println(row + "|");
			row = "";
		}
		System.out.println();
	}
	
	public static void checkWinningCondition(Box player)
	{
		boolean win = false;
		win = checkHor(player);
		if(win != true) win = checkVer(player);
		if(win != true) win = checkLD(player);
		if(win != true) win = checkRD(player);
		if(win == true)
		{
			if(player == Red)
				System.out.println("Player red has won");
			else
				System.out.println("Player Yellow has won");
			Main.end = true;
		}
	}
	
	public static boolean checkHor(Box player)
	{
		int i = 0;
		for(int y = 0; y != 6; y++)
		{
			for(int x = 0; x != 7; x++)
			{
				if(board[y][x] == player)
				{
					i++;
				}
				else if(i >= 4)
				{
					return true;
				}
				else
				{
					i = 0;
				}
			}
		}
		return false;
	}
	
	public static boolean checkVer(Box player)
	{
		int i = 0;
		for(int x = 0; x != 7; x++)
		{
			for(int y = 0; y != 6; y++)
			{
				if(board[y][x] == player)
				{
					i++;
				}
				else if(i >= 4)
				{
					return true;
				}
				else
				{
					i = 0;
				}
			}
		}
		return false;
	}
	public static boolean checkRD(Box player)
	{
		int i = 0;
		for(int x = 0; x != 4; x++)
		{
			for(int y = 0; y != 3; y++)
			{
				for(int z = 0; z <= x && z <= 4; z++)
				{
					if(board[y + z][x + z] == player)
					{
						i++;
					}
					else if(i >= 4)
					{
						return true;
					}
					else
					{
						i = 0;
					}
				}
			}
		}
		return false;
	}
	
	public static boolean checkLD(Box player)
	{
		int i = 0;
		for(int x = 0; x != 4; x++)
		{
			for(int y = 5; y != 2; y--)
			{
				for(int z = 0; z != 4; z++)
				{
					if(board[y - z][x + z] == player)
					{
						i++;
					}
					else if(i >= 4)
					{
						return true;
					}
					else
					{
						i = 0;
					}
				}
			}
		}
		return false;
	}
}