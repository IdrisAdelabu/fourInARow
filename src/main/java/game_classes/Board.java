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
        this.board = new String[rows][columns];

        for (String[] row : board) {
        	for (int i = 0; i < columns; i++) {
        		row[i] = "-";
        	}
        }
    }

    public void printBoard() {
        String[] colPosition = new String[board[0].length];
        for(int i = 0; i < board[0].length; i++) {
            colPosition[i] = "" + i;
        }
        System.out.println(Arrays.toString(colPosition));
        for (String[] row : board) {
            System.out.println(Arrays.toString(row));
        }
    }

    public boolean columnFull(int col) {
        if (board[0][col].equals("-")) {
            return false;
        }
        return true;
    }

    public boolean boardFull() {
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

			while (rowToAddToken >= 0) {
				if (board[rowToAddToken][colToAddToken].equals("-")) {
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
            for (int row = 0; row <= board.length - winCondition; row++) {
                if (board[row][col].equals(token)) {
                    cond = true;
                    for (int i = 1; i < winCondition; i++) {
                        cond = cond && board[row][col].equals(board[row + i][col]);
                        if (!cond) break;
                    }
                    if (cond) break;
                }
                if (cond) break;
            }
        }
        return cond;
    }

    public boolean checkHorizontal(String token, int winCondition) {
        boolean cond = false;
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col <= board[0].length - winCondition; col++) {
                if (board[row][col].equals(token)) {
                    cond = true;
                    for (int i = 1; i < winCondition; i++) {
                        cond = cond && board[row][col].equals(board[row][col + i]);
                        if (!cond) break;
                    }
                }
                if (cond) break;
            }
            if (cond) break;
        }
        return cond;
    }

    /*public boolean checkHorizontal(String token, int winCondition) {
        boolean cond = false;
        for (int col = 0; col < board[0].length - (winCondition - 1); col++) {

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
    }*/

    public boolean checkLeftDiagonal(String token, int winCondition) {
        boolean cond = false;
        for (int row = 0; row < board.length - (winCondition - 1); row++) {
            for (int col = 0; col < board[0].length - (winCondition - 1); col++) {
                if (board[row][col].equals(token)) {
                    cond = true;
                    for (int i = 1; i < winCondition; i++) {
                        cond = cond && board[row][col].equals(board[row + i][col + i]);
                        if (!cond) break;
                    }
                }
                if (cond) break;
            }
            if (cond) break;
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
                        if (!cond) break;
                    }
                }
                if (cond) break;
            }
            if (cond) break;
        }
        return cond;
    }
    
    public Scanner getScanner() {
    	return this.scanner;
    }

}
