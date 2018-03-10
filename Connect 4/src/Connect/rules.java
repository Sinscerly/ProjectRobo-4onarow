package Connect;


public class rules {
	public static boolean checkWin(Object[][] grid, boolean MSG) {
		for(int y = 0; y < 6; y++)
		{
			for(int x = 0; x < 6; x++)
			{
				Object current = grid[x][y];
				String aString = grid[x][y].toString();
				if(!aString.contains("Empty")) {
					if(x < 4) {
						if(current == grid[x+1][y] && current == grid[x+2][y] && current == grid[x+3][y]) {
							return winMSG(aString, MSG);
						}
					}
					if(y < 3) {
						if(current == grid[x][y+1] && current == grid[x][y+2] && current == grid[x][y+3]) {
							return winMSG(aString, MSG);
						}
					}
					if(x < 4 && y < 3) {
						if(current == grid[x+1][y+1] && current == grid[x+2][y+2] && current == grid[x+3][y+3]) {
							return winMSG(aString, MSG);
						}
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
