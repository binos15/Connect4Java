package csst8116.Project3;

public class ConnectFour {
	
	public static char[][] board = {
			{'-', '-', '-', '-', '-', '-', '-'}, 
			{'-', '-', '-', '-', '-', '-', '-'}, 
			{'-', '-', '-', '-', '-', '-', '-'}, 
			{'-', '-', '-', '-', '-', '-', '-'},
			{'-', '-', '-', '-', '-', '-', '-'},
			{'-', '-', '-', '-', '-', '-', '-'}	};
	
	public static void displayBoard() {
		System.out.println("1 2 3 4 5 6 7");
		for (int i = 0; i<board.length; i++) {
			for (int j = 0; j<board[i].length; j++) {
				System.out.print(board[i][j]);
				System.out.print(" ");
		    }
			System.out.println();
		}    
	}
	
	public static void makeMove (int column, char color) {
		int arrayColumn = column - 1;
		try {
			for (int i = board.length -1; i>-2; i--) {
				char charAtPos = board[i][arrayColumn];
				if (charAtPos == 'b' || charAtPos == 'w') {
					continue;
				} 
				if (charAtPos == '-') {
					board[i][arrayColumn] = color;
					i = -2;
				} 
			}
		} catch (ArrayIndexOutOfBoundsException e){
			System.out.println("Column is full!");
		}
	}

	public static void main(String[] args) {
		
		makeMove(3, 'w');
		makeMove(3, 'w');
		makeMove(3, 'w');
		makeMove(3, 'w');
		makeMove(3, 'w');
		makeMove(3, 'w');
		makeMove(3, 'w');
		makeMove(3, 'w');

		displayBoard();
	}
}
