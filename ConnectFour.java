package csst8116.Project3;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConnectFour {
	
	private static Scanner input = new Scanner(System.in);
	
	private static char[][] board = new char[6][7];
	
	private static char playerLetter = 'w';
	private static int movesPlayed = 0; //	keep track of moves played for tie game
	
	public static void initBoard() {							// Fills up the board with dashes
        for (int r = 0; r < 6; r++) {
        	for (int c = 0; c < 7; c++) {
            	board[r][c] = '-';
            }
        }
    }
	
	public static void displayBoard() {							// Displays board as 6 row and 7 columns, with numbers indicating the column on top for player move
		System.out.println("1 2 3 4 5 6 7");
		for (int i = 0; i<board.length; i++) {
			for (int j = 0; j<board[i].length; j++) {
				System.out.print(board[i][j]);
				System.out.print(" ");
		    }
			System.out.println();
		}    
	}
	
	public static void setup() {								// Housekeeping before a new game
		playerLetter = 'w';
		movesPlayed = 0;
		initBoard();
		displayBoard();
		System.out.println("WELCOME TO ALEXANDRE'S CONNECT 4!");
		System.out.println("White (w) goes first, Black (b) goes second.");
	}
	
	// Return int of row that is available, used in modifyBoard() below
	public static int getFreeRow (int column, char color) {		
		int arrayColumn = column - 1;
			for (int i = board.length -1; i>-1; i--) {			// Start loop at the end of the array to place the move as low as possible
				char charAtPos = board[i][arrayColumn];
				if (charAtPos == 'b' || charAtPos == 'w') {
					continue;									// Continue loop if position already has a move
				} 
				if (charAtPos == '-') {
					return i;									// Return index of available row
				} 
			}
		return -1;
	}
	
	// Flip letter from w to b or vice versa.
	 public static void flipLetter() {
		 if (playerLetter == 'w') {
				playerLetter = 'b';
			} else {
				playerLetter = 'w';
			}
	 }

	// Modify the board with player move at free row from getFreeRow() above
	public static void modifyBoard (int move) {					
		int freeRow = getFreeRow(move, playerLetter);
		
		if (freeRow > -1) {										// 
			board[freeRow][move-1] = playerLetter; 				// Modify board with move char
			movesPlayed += 1;
			displayBoard();
			System.out.printf("Player %s entered: %d%n" , playerLetter, move);	 //	print input
			flipLetter();
		} else {
			System.out.printf("Player %s entered: %d%n" , playerLetter, move);	 //	print input
			System.out.println("Column is full! Choose another number.");
		}
	}
	
	// Check if there is a tie, return true or false.
	 public static boolean tieCheck() {
		 if (movesPlayed == 42) {
				System.out.println("Tie game.");
				System.out.println();
				return true;
		 }
		 return false;
	 }
	 
	 // Check if there is a win, return true or false.
	 public static boolean winCheck() {
		// iterate over the whole board
		    for (int r = 0; r < board.length; r++) {
		        for (int c = 0; c < board[0].length; c++) {
		            char token = board[r][c];
		            if (token == '-') continue; // skip empty cells

		            // ---- Horizontal check ----
		            if (c + 3 < board[0].length &&
		                token == board[r][c + 1] &&
		                token == board[r][c + 2] &&
		                token == board[r][c + 3]) {
		                System.out.println("Player " + token + " wins horizontally!");
		                return true;
		            }

		            // ---- Vertical check ----
		            if (r + 3 < board.length &&
		                token == board[r + 1][c] &&
		                token == board[r + 2][c] &&
		                token == board[r + 3][c]) {
		                System.out.println("Player " + token + " wins vertically!");
		                return true;
		            }

		            // ---- Diagonal check (down-right) ----
		            if (r + 3 < board.length && c + 3 < board[0].length &&
		                token == board[r + 1][c + 1] &&
		                token == board[r + 2][c + 2] &&
		                token == board[r + 3][c + 3]) {
		                System.out.println("Player " + token + " wins diagonally!");
		                return true;
		            }

		            // ---- Diagonal check (down-left) ----
		            if (r + 3 < board.length && c - 3 >= 0 &&
		                token == board[r + 1][c - 1] &&
		                token == board[r + 2][c - 2] &&
		                token == board[r + 3][c - 3]) {
		                System.out.println("Player " + token + " wins diagonally!");
		                return true;
		            }
		        }
		    }
		    return false; // no winner found
	 }
	 
	 // Ask player to play again, return string answer.
	 public static String playAgain(){
		System.out.println("Play again? (y/n)");
 		String playAgain = input.next();
 		while (!(playAgain.equals("y") || playAgain.equals("n"))) {
 			System.out.println("Only enter y or n.");
 			System.out.println("Play again? (y/n");
 			input.next();
 		}
 		return playAgain;
	 }
	 
	// MAIN METHOD //
	public static void main(String[] args) {
    	//SETUP LOOP//
    	while (true) {    
    		setup();																		// Set or reset to new game state
    		while (true) {	 
				System.out.println("Enter number between 1-7:");  										
        		try {
        			//get player input as int
        			String line = input.nextLine().trim();  								// Attempts to get int from input. Along with try catch prevents input with spaces to be accepted and let a player make multiple moves at once
        			int move = Integer.parseInt(line); 										// Move number is used to select a column
        			System.out.println();
        	
        			if (move < 1 || move > 7) {												// Refuse full numbers not between 1 - 7
        			    System.out.println("Invalid number. Please choose a number between 1-7.");
        			    continue;
        			}
        			
        			modifyBoard (move);														// Modify board if there is a valid move in the chosen column. If there isn't, check modifyBoard() to see how it will remain the same player's move
        			
        			if (winCheck()) {														// Check if there's a win, break if true
        				break;
        			}
        			if (tieCheck()) {														// Check if there's a tie, break if true
        				break;
        			}
        			
        		} catch (Exception e) {
        		    System.out.println("Enter a number between 1-7");						// Doesn't consume input when it's an ArrayOutOfBoundsException
        		    if (e instanceof InputMismatchException) {
        		        input.next(); 														// Only consume when it's an input mismatch, like a single letter or character
        		    }
        		}
        	}
    		// 
    		if (playAgain().equals("y")) {													// Call playAgain() to get player input as string that is only "y" or "n"
    			input.nextLine();															// If "y", clear the last input which avoids unwanted input being entered after setup()
    			continue;																	// Retart initial loop and run setup()
    		} else {
    			break;																		// If "n", break and end program
    		}
    	}
	}
}