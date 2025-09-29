package csst8116.Project3;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConnectFour {
	
	private static Scanner input = new Scanner(System.in);
	
	private static char[][] board = new char[6][7];
	
	private static char playerLetter = 'w';  					//	Letter that represents the token color, w for white, b for black, changed with flipLetter()
	private static int movesPlayed = 0; 						//	Keep track of moves played for tie game

	// MAIN METHOD //
	public static void main(String[] args) {
    	//SETUP LOOP//
    	while (true) {    
    		setup();						// Set or reset to new game state
    		while (true) {	 
				System.out.println("Enter number between 1-7:");  										
        		try {
        			//get player input as int
        			String line = input.nextLine().trim();  // Attempts to get int from input. Along with try catch prevents input with spaces to be accepted and let a player make multiple moves at once
        			int move = Integer.parseInt(line); 		// Move number is used to select a column
        			System.out.println();
        	
        			if (move < 1 || move > 7) {				// Refuse full numbers not between 1 - 7
        			    System.out.println("Invalid number. Please choose a number between 1-7.");
        			    continue;
        			}
        			modifyBoard (move);		// Modify board if there is a valid move in the chosen column. If there isn't, check modifyBoard() to see how it will remain the same player's move
        			
        			if (winCheck()) {		// Check if there's a win, break to replay below if true
        				break;
        			}
        			if (tieCheck()) {		// Check if there's a tie, break to replay below if true
        				break;
        			}
        		} catch (Exception e) {
        		    System.out.println("Enter a number between 1-7");		// Doesn't consume input when it's an ArrayOutOfBoundsException
        		    if (e instanceof InputMismatchException) {
        		        input.next(); 										// Only consume when it's an input mismatch, like a single letter or character
        		    }
        		}
        	}
    		// REPLAY
    		if (playAgain().equals("y")) {		// Call playAgain() to get player input as string that is only "y" or "n"
    			input.nextLine();				// If "y", clear the last input which avoids unwanted input being entered after setup()
    			continue;						// Retart initial loop and run setup()
    		} else {
    			break;							// If "n", break and end program
    		}
    	}
	}
	

	
	public static void initBoard() {							// Fills up the board with dashes
        for (int r = 0; r < 6; r++) {
        	for (int c = 0; c < 7; c++) {
            	board[r][c] = '-';
            }
        }
    }
	
	public static void displayBoard() {							// Displays board as 6 row and 7 columns, with numbers indicating the column on top for player move
		System.out.println("1 2 3 4 5 6 7");
		for (int r = 0; r<board.length; r++) {
			for (int c = 0; c<board[r].length; c++) {
				System.out.print(board[r][c]);
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
		int c = column - 1;
			for (int r = board.length -1; r>-1; r--) {			// Start loop at the end of the array to place the move as low as possible
				char charAtPos = board[r][c];					// Character at current position
				if (charAtPos == 'b' || charAtPos == 'w') {
					continue;									// Continue loop if position already has a move
				} 
				if (charAtPos == '-') {
					return r;									// Return index of available row
				} 
			}
		return -1;												// Return -1 to modifyBoard()
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
		int r = getFreeRow(move, playerLetter);			// Row number from getFreeRow()
		
		if (r > -1) {										// If free space found in column
			board[r][move-1] = playerLetter; 				// Modify board with move char
			movesPlayed += 1;
			displayBoard();					
			System.out.printf("Player %s entered: %d%n" , playerLetter, move);	 
			flipLetter();
		} else {
			System.out.printf("Player %s entered: %d%n" , playerLetter, move);	 //	If it did not find free space in column
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
	 
	 
	 public static boolean winCheck() {					// Check if there is a win, return true or false.
		    for (int r = 0; r < board.length; r++) {	// Iterate over the whole board
		        for (int c = 0; c < board[0].length; c++) {
		            char charAtPos = board[r][c];
		            if (charAtPos == '-') continue; 	// Skip empty cells

		           
		            if (c + 3 < board[0].length &&		 // Horizontal check 
		                charAtPos == board[r][c + 1] &&
		                charAtPos == board[r][c + 2] &&
		                charAtPos == board[r][c + 3]) {
		                System.out.println("Player " + charAtPos + " wins horizontally!");
		                return true;
		            }
		            
		            if (r + 3 < board.length &&			 // Vertical check
		                charAtPos == board[r + 1][c] &&
		                charAtPos == board[r + 2][c] &&
		                charAtPos == board[r + 3][c]) {
		                System.out.println("Player " + charAtPos + " wins vertically!");
		                return true;
		            }
		            
		            if (r + 3 < board.length && c + 3 < board[0].length &&		//  Diagonal check (down-right)
		                charAtPos == board[r + 1][c + 1] &&
		                charAtPos == board[r + 2][c + 2] &&
		                charAtPos == board[r + 3][c + 3]) {
		                System.out.println("Player " + charAtPos + " wins diagonally!");
		                return true;
		            }

		            
		            if (r + 3 < board.length && c - 3 >= 0 &&	// Diagonal check (down-left)
		                charAtPos == board[r + 1][c - 1] &&
		                charAtPos == board[r + 2][c - 2] &&
		                charAtPos == board[r + 3][c - 3]) {
		                System.out.println("Player " + charAtPos + " wins diagonally!");
		                return true;
		            }
		        }
		    }
		    return false; // No winner found
	 }
	 
	 // Ask player to play again, return string answer
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
}