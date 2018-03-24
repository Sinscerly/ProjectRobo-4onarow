package Connect;


public class GoodMoves {
	public static String checkWin(Box[][] grid) {
		/* Check if there are 4 stones of the same color in one line on the board */
		for(int y = 0; y < 6; y++)
		{
			for(int x = 0; x < 6; x++)
			{
				Box current = grid[x][y];
				if(!current.toString().contains("Empty")) {
					/* Horizontal check 4 on a row */
					if(x < 4) {
						if(current == grid[x+1][y] && current == grid[x+2][y] && current == grid[x+3][y]) {
							return current.toString();
						}
					}
					/* Vertical check 4 on a row */
					if(y < 3) {
						if(current == grid[x][y+1] && current == grid[x][y+2] && current == grid[x][y+3]) {
							return current.toString();
						}
					}
					/* Left under to right upper check 4 on a row */
					if(x < 4 && y < 3) {
						if(current == grid[x+1][y+1] && current == grid[x+2][y+2] && current == grid[x+3][y+3]) {
							return current.toString();
						}
					}
					/* Right upper to left under check 4 on a row */
					if(x > 2 && y < 3) {
						if(current == grid[x-1][y+1] && current == grid[x-2][y+2] && current == grid[x-3][y+3])
							return current.toString();
					}
				}				
			}
		}
		return "Empty";
	}
	public static boolean checkWin(Box[][] grid, boolean MSG) {
		/* Check if there are 4 stone of the same color in one line on the board */
		for(int y = 0; y < 6; y++)
		{
			for(int x = 0; x < 6; x++)
			{
				Box current = grid[x][y];
				if(!current.toString().contains("Empty")) {
					/* Horizontal check 4 on a row */
					if(x < 4) {
						if(current == grid[x+1][y] && current == grid[x+2][y] && current == grid[x+3][y]) {
							return winMSG(current.toString(), MSG);
						}
					}
					/* Vertical check 4 on a row */
					if(y < 3) {
						if(current == grid[x][y+1] && current == grid[x][y+2] && current == grid[x][y+3]) {
							return winMSG(current.toString(), MSG);
						}
					}
					/* Left under to right upper check 4 on a row */
					if(x < 4 && y < 3) {
						if(current == grid[x+1][y+1] && current == grid[x+2][y+2] && current == grid[x+3][y+3]) {
							return winMSG(current.toString(), MSG);
						}
					}
					/* Right upper to left under check 4 on a row */
					if(x > 2 && y < 3) {
						if(current == grid[x-1][y+1] && current == grid[x-2][y+2] && current == grid[x-3][y+3])
							return winMSG(current.toString(), MSG);
					}
				}				
			}
		}
		return false;
	}
	private static boolean winMSG(String aString, boolean MSG) {
		if(aString.contains("Empty"))
			return false;
		else if(MSG == true)
			if(aString.contains("Red"))
				System.out.println("Red has won, you lost");
			else
				System.out.println("Yellow has won, you won");
		return true;
	}
}
