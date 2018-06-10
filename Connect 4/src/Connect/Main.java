package Connect;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class Main {

	static int diffCpu1, diffCpu2;
	public static int Ai1, Ai2;
	public static String printString = "";
	public static boolean print = false;
	static String AiRed = "1(Red)", AiYellow = "2(Yellow)";

	public static void main(String[] args)
			throws InterruptedException, UnsupportedEncodingException, FileNotFoundException, IOException {
		String input;
		while (true) {
			System.out.println("What type of game do you want?");
			System.out.println("a: player VS player.");
			System.out.println("b: player VS cpu.");
			System.out.println("c: cpu VS cpu.");
			System.out.println("d: cpu VS cpu all diff.");
			System.out.println("x: exit.");
			System.out.print("Your anwser: ");
			input = Game.scanner.next();
			if (input.equals("a")) {
				pvp();
			} else if (input.equals("b")) {
				pvc();
			} else if (input.equals("c")) {
				cvc();
			} else if (input.equals("d")) {
				print = true;
				if(all())
					cvcad(true);
				else
					cvcad(false);
				print = false;
			} else if (input.equals("x")) {
				break;
			}
		}
	}

	private static void pvp() throws InterruptedException {
		Game startGame = new Game();
		startGame.pvp();
	}

	private static void pvc() throws InterruptedException {
		Game startGame = new Game();
		System.out.println("Do you want to play a game?");
		diffCpu1 = scanDiff(AiRed);
		startGame.pvc(diffCpu1);
	}

	private static void cvc() throws InterruptedException {
		System.out.println("Do you want to see the progress?");
		System.out.println("y/n?");
		Game startGame = new Game();
		if (Game.scanner.next().equals("y"))
			Game.seeProgress = true;
		else
			Game.seeProgress = false;
		whichAI();
		if (Ai1 == 1)
			diffCpu1 = scanDiff(AiRed);
		if (Ai2 == 1)
			diffCpu2 = scanDiff(AiYellow);
		AI AI1, AI2;
		if (Ai1 == 1) {
			AI1 = new MiniMax(diffCpu1);
		} else {
			AI1 = new OwnAI();
		}
		if (Ai2 == 1) {
			AI2 = new MiniMax(diffCpu2);
		} else {
			AI2 = new OwnAI();
		}
		startGame.cvc(AI1, AI2);
	}

	private static void cvcad(boolean all)
			throws InterruptedException, UnsupportedEncodingException, FileNotFoundException, IOException {
		if(!all) {
			whichAI();
			startcvcad();
		}
		else {
			Ai1 = 1; Ai2 = 1;
			startcvcad();
			Ai1 = 1; Ai2 = 2;
			startcvcad();
			Ai1 = 2; Ai2 = 1;
			startcvcad();
			Ai1 = 2; Ai2 = 2;
			startcvcad();
		}
		
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("wins.txt"), "utf-8"))) {
			writer.write(printString);
		}
	}

	static void startcvcad() throws InterruptedException {
		AI AI1, AI2;
		if (Ai1 == 1 && Ai2 == 1) {
			printString += "Game MiniMax vs MiniMax. \n \n";
			for (int i = 1; i < 9; i++) {
				diffCpu1 = i;
				for (int j = 1; j < 9; j++) {
					long start = System.currentTimeMillis();
					diffCpu2 = j;
					Game startGame = new Game();
					AI1 = new MiniMax(diffCpu1);
					AI2 = new MiniMax(diffCpu2);
					startGame.cvcad(diffCpu1, diffCpu2, AI1, AI2);
					long end = System.currentTimeMillis();
					System.out.println("Total time = " + ((end - start) / 1000) + "S.");
					printString += " Time it took: " + ((end - start) / 1000) + "S.\n";
				}
			}
		} else if (Ai1 == 1 && Ai2 == 2 || Ai1 == 2 && Ai2 == 1) {
			for (int i = 1; i < 9; i++) {
				diffCpu1 = i;
				long start = System.currentTimeMillis();
				Game startGame = new Game();
				if(Ai1 == 1) {
					printString += "Game MiniMax vs OwnAI. \n \n";
					AI1 = new MiniMax(diffCpu1);
					AI2 = new OwnAI();
				} else {
					printString += "Game OwnAI vs MiniMax. \n \n";
					AI1 = new OwnAI();
					AI2 = new MiniMax(diffCpu1);
				}
				startGame.cvcad(diffCpu1, diffCpu2, AI1, AI2);
				long end = System.currentTimeMillis();
				System.out.println("Total time = " + ((end - start) / 1000) + "S.");
				printString += " Time it took: " + ((end - start) / 1000) + "S.\n";
			}
		} else if (Ai1 == 2 && Ai2 == 2) {
			printString += "Game OwnAI vs OwnAI. \n \n";
				long start = System.currentTimeMillis();
				Game startGame = new Game();
				AI1 = new OwnAI();
				AI2 = new OwnAI();
				startGame.cvcad(diffCpu1, diffCpu2, AI1, AI2);
				long end = System.currentTimeMillis();
				System.out.println("Total time = " + ((end - start) / 1000) + "S.");
				printString += " Time it took: " + ((end - start) / 1000) + "S.\n";
		}
	}
	
	static void printDiff(String player) {
		System.out.println("At what difficulty does cpu " + player + " play? \n" + "0: Monkey \n" + "1: Very Easy \n"
				+ "2: Easy \n" + "3: Medium \n" + "4: Hard \n" + "5: Very Hard \n" + "6: Extreem \n" + "7: Legend \n"
				+ "8: Jeroen killer");
	}

	static int scanInt() {
		return Game.scanner.nextInt();
	}

	static int scanDiff(String cpu) {
		int i = -1;
		while (0 > i || i > 8) {
			printDiff(cpu);
			i = scanInt();
		}
		return i;
	}

	static int scanAi() {
		int i = 0;
		while (1 > i || i > 2) {
			i = scanInt();
		}
		return i;
	}

	static void whichAI() {
		System.out.println("Which Ai vs which Ai?");
		System.out.println("Ai 1: MiniMax or our own made ai?");
		System.out.println("1/2?");
		Ai1 = scanAi();
		System.out.println("Ai 2: MiniMax or our own made ai?");
		System.out.println("1/2?");
		Ai2 = scanAi();
	}
	
	static boolean all() {
		System.out.println("Do you want to play out all the possibilities or just one?");
		System.out.println("y/n");
		if(Game.scanner.next().equals("y"))
			return true;
		else
			return false;
	}

}
