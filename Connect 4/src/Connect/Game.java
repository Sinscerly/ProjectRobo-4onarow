package Connect;

import java.awt.List;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class Game {
	static Scanner scanner = new Scanner(System.in);
	private int whosTurn = 0;
	Box whoBegan = Board.red;
	private int difficulty1 = 4, difficulty2 = -1;
	public static int AI = 0, player = 1;
	public static boolean seeProgress = false;
	public int sets; // total number of sets of a game..

	Game(int startPlayer) {
		/* startPlayer = 0 Computer starts, = 1 Player starts */
		if (startPlayer == 1) {
			whosTurn = startPlayer;
		}
	}
	//starts the game
	void start() throws InterruptedException, UnsupportedEncodingException, IOException {
		if(difficulty2 == -1)
		{
			pvc();
		}
		else
		{
			cvc();
		}
	}
	void pvc()
	{
		AI miniMaxAI = new AI(difficulty1);
		//change set to move
		int p1_set = 0;
		sets = 0;
		Board.setupBoard();
		Board.printBoard();
		
		while (GoodMoves.checkWin(Board.getBoard(), true) == false && Board.checkFull(Board.getBoard())) {
			if (whosTurn == 0) {
				p1_set = ai_move(miniMaxAI);
				Board.placeMove(p1_set, Board.red);
				Board.printBoard();
				whosTurn = 1;
			} else {
				player = 0;
				System.out.println("");
				System.out.print("Enter a row Yellow: ");
				while (player < 1 || player > 7) // check of het tussen de 1 en
													// 7 is
				{
					player = scanner.nextInt();
					if (player < 1 || player > 7)
						System.out.println("Number out of bounds");
				}
				Board.placeMove(player - 1, Board.yellow);
				whosTurn = 0;
			}
			sets++;
		}
		// what was this???
		if (whosTurn == 1)
			whoBegan = Board.yellow;
		else
			whoBegan = Board.red;
		Board.printBoard();
	}
	void cvc() throws InterruptedException, UnsupportedEncodingException, IOException
	{
		AI miniMaxAI = new AI(difficulty1);
		AI m2 = new AI(difficulty2);
		//change set to move
		int p1_set = 0, p2_set = 0;
		sets = 0;
		Board.setupBoard();
		Board.printBoard();
		
		while (GoodMoves.checkWin(Board.getBoard(), true) == false && Board.checkFull(Board.getBoard())) {
			if (whosTurn == 0) {
				p1_set = ai_move(miniMaxAI);
				Board.placeMove(p1_set, Board.red);
				if(seeProgress){
					Board.printBoard();
					Thread.sleep(1000);
				}
				whosTurn = 1;
			} else {
				p2_set = ai_move(m2);
				Board.placeMove(p2_set, Board.yellow);
				if(seeProgress){
					Board.printBoard();
					Thread.sleep(1000);
				}
				whosTurn = 0;
			}
			sets++;
		}
		// what was this???
		if (whosTurn == 1)
			Main.printString += difficulty1 + " VS " + difficulty2 + ": cpu 1 wins.";
		else
			Main.printString += difficulty1 + " VS " + difficulty2 + ": cpu 2 wins.";
		Board.printBoard();
	}
	public int ai_move(AI smart) {
		//change set to move
		int ai_set = 0;
		ai_set = smart.doSet(Board.getBoard(), whoBegan);
		System.out.print("Computer placed in row: " + (ai_set + 1));
		System.out.println("Took: " + ((smart.end - smart.start) / 1000) + "S.");
		return ai_set;
	}
	public int player_move() {
		player = 0;
		System.out.println("");
		System.out.print("Enter a row Yellow: ");
		while (player < 1 || player > 7) // check of het tussen de 1 en
											// 7 is
		{
			player = scanner.nextInt();
			if (player < 1 || player > 7)
				System.out.println("Number out of bounds");
		}
		//because the player will not think in numbers of 0 to 6 it's made from 1 to 7 so we need to put all the awnsers 1 lower.
		return player - 1;
	}
	public void setDifficulty1(int difficulty) {
		this.difficulty1 = difficulty;
	}
	public void setDifficulty2(int difficulty){
		this.difficulty2 = difficulty;
	}
}
