package game_classes;

import exceptions.ColumnFullException;
import exceptions.InvalidColumnsException;
import exceptions.InvalidMoveException;
import exceptions.InvalidRowsException;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;



public class Board {
	
	private String[][] board;
	private Scanner scanner = new Scanner(System.in);
	
	
    // add instance variables

    public void boardSetUp(int winCondition) {
        System.out.println("------ Board Set up -------");
        
        
        boolean cond = true;
        int rows = 0, columns = 0;
        
        while (cond) {
        	try {
        		System.out.println("Number of rows: ");
        		rows = scanner.nextInt();
        		if (rows < winCondition || rows > 10) {
        			throw new InvalidRowsException("Please select an integer between " + winCondition + " and 10");
        		}
        		cond = false;
        	} catch (InvalidRowsException ex) {
        		System.out.println(ex.getMessage());
        	} catch (InputMismatchException ex) {
        		scanner.nextLine();
        		System.out.println("Please input an integer");
        	} catch (Exception ex) {
    			System.err.println("An unexpected error occurred: " + ex.getMessage());
    		}
        }
        
        cond = true;
        
        while (cond) {
        	try {
        		System.out.println("Number of columns: ");
        		columns = scanner.nextInt();
        		if (columns < winCondition || columns > 10) {
        			throw new InvalidColumnsException("Please select an integer between " + winCondition + " and 10");
        		}
        		cond = false;
        	} catch (InvalidColumnsException ex) {
        		System.out.println(ex.getMessage());
        	} catch (InputMismatchException ex) {
        		scanner.nextLine();
        		System.out.println("Please input an integer");
        	} catch (Exception ex) {
    			System.err.println("An unexpected error occurred: " + ex.getMessage());
    		}
        }
        scanner.nextLine();
        // receive column value
        this.board = new String[rows][columns];// initialize a row by column array;

        // initialize empty board with dashes (-)
        for (String[] row : board) {
            // fill up each row of the board with dashes
        	for (int i = 0; i < columns; i++) {
        		row[i] = "-";
        	}
        }
    }

    public void printBoard() {
        for (String[] row : board) {
            System.out.println(Arrays.toString(row));
        }
    }

    public boolean columnFull(int col) {
        if (board[0][col].equals("-")) {// check if the column is full by just checking the 0'th row's value)
            return false;
        }
        return true;
    }

    public boolean boardFull() {
        // True understanding this code.
        for (int i = 0; i < this.board[0].length; i++) {
            if (!columnFull(i)) {
                return false;
            }
        }
        return true;
    }

	public boolean addToken(int colToAddToken, String token) {

		try {
			if (colToAddToken < 0 || colToAddToken >= board[0].length || columnFull(colToAddToken)) {

				throw new InvalidMoveException("Please select a valid column between 0 (inclusive) and " + (board[0].length - 1)
						+ " (inclusive)");
			}
			if (columnFull(colToAddToken)) {
				throw new ColumnFullException("Selected column is full!!");
			}
			int rowToAddToken = board.length - 1;

			while (rowToAddToken >= 0) {// what condition should be here to allow you to keep searching for the right
										// row level of the board to place the token? )
				if (board[rowToAddToken][colToAddToken].equals("-")) {
					// You now know the right row and column to place the token. Place it and then
					// return true.
					board[rowToAddToken][colToAddToken] = token;
					System.out.println("Token set successfully");
					return true;
				} else {
					rowToAddToken -= 1;
				}
			}
		} catch (ColumnFullException | InvalidMoveException ex) {
			System.out.println(ex.getMessage());
			return false;
		} catch (Exception ex) {
			System.err.println("An unexpected error occurred: " + ex.getMessage());
			return false;
		}

		return false;
	}

    public boolean removeToken(int colToAddToken, String token) {
        int rowToAddToken = 0;

        while (rowToAddToken < board.length) {
            if (board[rowToAddToken][colToAddToken].equals(token)) {
                // You now know the right row and column to place the token. Place it and then
                // return true.
                board[rowToAddToken][colToAddToken] = "-";
                System.out.println("Token removed successfully");
                return true;
            } else {
                rowToAddToken += 1;
            }
        }
        return false;
    }

    public boolean checkIfPlayerIsTheWinner(String token, int winCondition) {

        return checkHorizontal(token, winCondition) || checkVertical(token, winCondition)
                || checkLeftDiagonal(token, winCondition) || checkRightDiagonal(token, winCondition);
    }

    public boolean checkVertical(String token, int winCondition) {
        boolean cond = false;
        for (int col = 0; col < board[0].length; col++) {
            for (int row = 0; row < board.length - (winCondition - 1); row++) {
                if (board[row][col].equals(token)) {
                    cond = true;
                    for (int i = 1; i < winCondition; i++) {
                        cond = cond && board[row][col].equals(board[row + i][col]);
                    }
                }
            }
        }
        return cond;
    }

    public boolean checkHorizontal(String token, int winCondition) {
        boolean cond = false;
        for (int col = 0; col < board[0].length - (winCondition - 1); col++) {
            // try implementing this by being inspired by the checkVertical logic. Note avoid off by 1 errors. Also remember that you are now checking across columns within each row this time.
            for (String[] strings : board) {
                if (strings[col].equals(token)) {
                    cond = true;
                    for (int i = 1; i < winCondition; i++) {
                        cond = cond && strings[col].equals(strings[col + i]);
                    }
                }
            }
        }
        return cond;
    }

    public boolean checkLeftDiagonal(String token, int winCondition) {
        boolean cond = false;
        for (int row = 0; row < board.length - (winCondition - 1); row++) {
            for (int col = 0; col < board[0].length - (winCondition - 1); col++) {
                if (board[row][col].equals(token)) {
                    cond = true;
                    for (int i = 1; i < winCondition; i++) {
                        cond = cond && board[row][col].equals(board[row + i][col + i]);
                    }
                }
            }
        }
        return cond;
    }


    public boolean checkRightDiagonal(String token, int winCondition) {
        boolean cond = false;
        for (int row = 0; row < board.length - (winCondition - 1); row++) {
            for (int col = board[0].length - 1; col >= (winCondition - 1); col--) {
                if (board[row][col].equals(token)) {
                    cond = true;
                    for (int i = 1; i < winCondition; i++) {
                        cond = cond && board[row][col].equals(board[row + i][col - i]);
                    }
                }
            }
        }
        return cond;
    }
    
    public Scanner getScanner() {
    	return this.scanner;
    }

}
