package csst8116.Project3;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConnectFour {
	
	private static char[][] board = new char[6][7];
	
	private static char colorLetter = 'w';
	private static int movesPlayed = 0; //	keep track of moves played for tie game
	
	public static void initBoard() {							// Fills up the board with dashes.
        for (int r = 0; r < 6; r++) {
        	for (int c = 0; c < 7; c++) {
            	board[r][c] = '-';
            }
        }
        
		/*
		 * for (int r = 0; r < 6; r++) { for (int c = 6; c < 7; c++) { board[r][c] =
		 * '-'; } }
		 */
    }
	
	public static void displayBoard() {							// Displays board as 6 row and 7 columns, with numbers indicating the column on top for player move.
		System.out.println("1 2 3 4 5 6 7");
		for (int i = 0; i<board.length; i++) {
			for (int j = 0; j<board[i].length; j++) {
				System.out.print(board[i][j]);
				System.out.print(" ");
		    }
			System.out.println();
		}    
	}
	
	public static void setup() {	
		initBoard();	//	set board to - on start or for new game
		displayBoard();
		System.out.println("WELCOME TO ALEXANDRE'S CONNECT 4!");
		System.out.println("White (w) goes first, Black (b) goes second.");
	}
	
	public static int getIntInput() {
		
		return 0;
	}
	
	public static int getFreeColumn (int column, char color) {		// Modify the board with player move.
		int arrayColumn = column - 1;
			for (int i = board.length -1; i>-1; i--) {			// Start loop at the end of the array to place the move as low as possible. Loop ends at -2 so that i gets to -1 and triggers the exception. 
				char charAtPos = board[i][arrayColumn];
				if (charAtPos == 'b' || charAtPos == 'w') {
					continue;									// Continue loop if position already has a move.
				} 
				if (charAtPos == '-') {
					return i;
				} 
			}
		return -1;
	}
	
	 public static void flipLetter() {
		 if (colorLetter == 'w') {
				colorLetter = 'b';
			} else {
				colorLetter = 'w';
			}
	 }

	public static void modifyBoard (int move) {
		int freeRow = getFreeColumn(move, colorLetter);
		if (freeRow > -1) {
			board[freeRow][move-1] = colorLetter; // Modify board with move char.
			movesPlayed += 1;
			flipLetter();
			//System.out.println(movesPlayed);
		} else {
			System.out.println("Column is full! Choose another number.");
		}
	}
	
	 public static boolean tieCheck() {
		 if (movesPlayed == 42) {
				System.out.println("Tie game.");
				System.out.println();
				return true;
		 }
		 return false;
	 }
	 
	 public static boolean winCheck() {
		 return false;
	 }

	public static void main(String[] args) {
    	try (Scanner input = new Scanner(System.in)) {	//	create scanner
        	//SETUP LOOP//
        	while (true) {    // repeat while true
        		setup();
        		while (true) {	 //	repeat while true)
    				System.out.println("Enter number between 1-7:");  										
            		try {
            			//get player input as int
            			String line = input.nextLine().trim();  								// Attempts to get int from input. Along with try catch prevents input with spaces to be accepted and let a player make multiple moves at once.
            			int move = Integer.parseInt(line); 										 
            			System.out.println();
            			
            	
            			if (move < 1 || move > 7) {												// Only take full numbers between 1 - 7.
            			    System.out.println("Invalid number. Please choose a number between 1-7.");
            			    continue;
            			}
            			modifyBoard (move);
            			displayBoard();
            			System.out.printf("Player %s entered: %d%n" , colorLetter, move);	 //	print input
            			
            			if (winCheck()) {
            				break;
            			}
            			if (tieCheck()) {
            				break;
            			}
            		} catch (Exception e) {
            		    System.out.println("Enter a number between 1-7");	// Doesn't consume when it's an ArrayOutOfBoundsException.
            		    if (e instanceof InputMismatchException) {
            		        input.next(); 									// Only consume when it's an input mismatch, like a single letter or character.
            		    }
            		}
            	}
        		System.out.println("Play again? (y/n)");
        		String playAgain = input.next();
        		while (!(playAgain.equals("y") || playAgain.equals("n"))) {
        			System.out.println("Only enter y or n.");
        			System.out.println("Play again? (y/n");
        			input.next();
        		}
        		if (playAgain.equals("y")) {
        			movesPlayed = 0;
        			input.nextLine();
        			continue;
        		} else {
        			break;
        		}
        	}
        }
	}
}
