package game_classes;

import exceptions.ColumnFullException;
import exceptions.InvalidColorException;
import exceptions.InvalidMoveException;
import exceptions.InvalidTokenException;

import java.util.*;

public class Game {
	
	private Player[] players;
    private Board board;

	private int winCondition;
    private static Scanner scanner = new Scanner(System.in);
	private static final Map<String, String> colorMap = new HashMap<>();
	private static final Map<String, String> tokenMap = new HashMap<>();
	private Map<String, Integer> turnHistory;

	static {
		colorMap.put("Black", "\u001B[30m");
		colorMap.put("Red", "\u001B[31m");
		colorMap.put("Green", "\u001B[32m");
		colorMap.put("Yellow", "\u001B[33m");
		colorMap.put("Blue", "\u001B[34m");
		colorMap.put("Magenta", "\u001B[35m");
		colorMap.put("Cyan", "\u001B[36m");
		colorMap.put("White", "\u001B[37m");
		colorMap.put("Bright Black (Gray)", "\u001B[90m");
		colorMap.put("Bright Red", "\u001B[91m");
		colorMap.put("Bright Green", "\u001B[92m");
		colorMap.put("reset", "\u001B[0m");
		tokenMap.put("Circle", "●");
		tokenMap.put("Square", "■");
		tokenMap.put("Triangle", "▲");
		tokenMap.put("Diamond", "◆");
		tokenMap.put("Rectangle", "▭");
		tokenMap.put("Hexagon", "⬣");
		tokenMap.put("Star", "★");
		tokenMap.put("Right Arrow", "➔");
		tokenMap.put("Pentagon", "⬠");
		tokenMap.put("Octagon", "⯁");
	}


    public Game() {
        // Let's default it two players for now. Later, you can improve upon this to allow the game creator to choose how many players are involved.
        this.players = new Player[2];// complete line.
        this.board = new Board();// complete line
    }

    public void setUpGame() {

		String[] tokens = {"Circle", "Square", "Triangle", "Diamond", "Rectangle", "Hexagon", "Star", "Right Arrow",
				"Pentagon", "Octagon"};
		String[] colours = {"Black", "Red", "Green", "Yellow", "Blue", "Magenta", "Cyan", "White", "Bright Black (Gray)",
		"Bright Red", "Bright Green"};
    	boolean cond = true;
    	int numOfPlayers = 0;
//		this.winCondition = 4;
    	
    	while (cond) {
    	
	    	try {
	    		System.out.println("Enter number of players: ");
	    		numOfPlayers = scanner.nextInt();
	    		scanner.nextLine();
	    		if (numOfPlayers < 2 || numOfPlayers > 4) {
	    			System.out.println("Please enter a value between 2 and 4!!");
	    			continue;
	    		} 
	    		cond = false;	    	
	    	} catch (InputMismatchException ex) {
	    		System.out.println("Please input a valid integer!!");
	    		scanner.nextLine();
	    	}
    	}
    	players = new Player[numOfPlayers];

		Set<String> selectedColours = new HashSet<>();
		Set<String> selectedToken = new HashSet<>();
    	
    	for (int i = 0; i < numOfPlayers; i++) {
//    		cond = true;

			// selecting player name
    		do {
    			int check = 0;
    			System.out.println("Enter player " + (i + 1) + "'s name: ");
        		players[i] = new Player(scanner.nextLine(), "" + (i + 1));
        		for (int j = 0; j < i; j++) {
    				if (players[j].getName().equals(players[i].getName())) {
    					System.out.println("Name has already been claimed by another player. Please enter another name!");
    					check = 0;
    					break;
    				}
    				check++;
    			}
    			cond = (check != 0 || i == 0) ? false : true;
    		} while (cond);

			// selecting player token
			// todo implement exception check for invalid input
			do {

				try {
					System.out.println("Enter corresponding number to select player " + (i + 1) + "'s token: ");
					for (int index = 0; index < tokens.length; index++) {
						System.out.println(index + " : " + tokenMap.get(tokens[index]));
					}
					int tokenNumber = scanner.nextInt();
					scanner.nextLine();
					if (tokenNumber < 0 || tokenNumber > 10) {
						throw new InvalidColorException("Please select a number between 0 and 10");
					}
					if (selectedToken.contains(tokens[tokenNumber])) {
						throw new InvalidTokenException("Token has already been claimed by another player. Please select another token!");
					}

					players[i].setToken(tokens[tokenNumber]);
					selectedToken.add(tokens[tokenNumber]);
					cond = false;

				} catch (InputMismatchException ex) {
					System.out.println("Please input a valid integer!!");
					scanner.nextLine();
					cond = true;
				} catch (InvalidTokenException ex) {
					System.out.println(ex.getMessage());
					cond = true;
				} catch (Exception ex) {
					scanner.nextLine();
					System.err.println("An unexpected error occurred: " + ex.getMessage());
					cond = true;
				}
			} while (cond);

			// selecting token colour
			// todo implement exception check for invalid input
			do {
				try {
					System.out.println("Enter corresponding number to select player " + (i + 1) + "'s colour: ");
					for (int index = 0; index < colours.length; index++) {
						System.out.println(index + " : " + colorMap.get(colours[index]) + colours[index] + colorMap.get("reset"));
					}
					int colourNumber = scanner.nextInt();
					scanner.nextLine();
					if (colourNumber < 0 || colourNumber > 10) {
						throw new InvalidColorException("Please select a number between 0 and 10");
					}

					if (selectedColours.contains(colours[colourNumber])) {
						throw new InvalidColorException("Color has already been claimed by another player. Please select another color!");
					}
					players[i].setColour(colours[colourNumber]);
					selectedColours.add(colours[colourNumber]);
					cond = false;

				} catch (InputMismatchException ex) {
					System.out.println("Please input a valid integer!!");
					scanner.nextLine();
					cond = true;
				} catch (InvalidColorException ex) {
					System.out.println(ex.getMessage());
					cond = true;
				} catch (Exception ex) {
					scanner.nextLine();
					System.err.println("An unexpected error occurred: " + ex.getMessage());
					cond = true;
				}
			} while (cond);
		}
    	
        /** add logic to prevent a user from giving a second name that's equal to the first. Allow the user to try as long as the names are not different.*/
		//set up win condition

		do {
			try {
				System.out.println("Please input a win condition between 4 and 10: ");
				this.winCondition = scanner.nextInt();
				scanner.nextLine();
				if (this.winCondition < 4 || this.winCondition > 10) {
					throw new InvalidMoveException("Please select a number between 4 and 10");
				}
				cond = false;
			} catch (InputMismatchException ex) {
				System.out.println("Please input a valid integer!!");
				scanner.nextLine();
				cond = true;
			} catch (InvalidMoveException ex) {
				System.out.println(ex.getMessage());
				cond = true;
			} catch (Exception ex) {
				scanner.nextLine();
				System.err.println("An unexpected error occurred: " + ex.getMessage());
				cond = true;
			}
		} while(cond);

        // set up the board using the appropriate method
        board.boardSetUp(this.winCondition);
        // print the board the using appropriate method
        board.printBoard();
    }

    public void printWinner(Player player) {
        System.out.println(player.getName() + " is the winner");
    }

    public void playerTurn(Player currentPlayer) {
        int col = currentPlayer.makeMove();
        while (!board.addToken(col, colorMap.get(currentPlayer.getColour()) + tokenMap.get(currentPlayer.getToken())
				+ colorMap.get("reset"))) {
           // call board method to add token.
        	col = currentPlayer.makeMove();
        }
		turnHistory.put(currentPlayer.getName(), col);
        // print board
        board.printBoard();
    }

	public void undoTurn(Player currentPlayer) {
		board.removeToken(turnHistory.get(currentPlayer.getName()), colorMap.get(currentPlayer.getColour())
				+ tokenMap.get(currentPlayer.getToken()) + colorMap.get("reset"));
		board.printBoard();
	}

	public void play() {
		boolean cond = true;

		while (cond) {
			turnHistory = new HashMap<>();
			boolean noWinner = true;
			this.setUpGame();
			int currentPlayerIndex = 0;

			while (noWinner) {
				if (board.boardFull()) {// provide condition)
					System.out.println("Board is now full. Game Ends.");
					break;
				}

				Player currentPlayer = players[currentPlayerIndex];
				// Override default tostring for Player class
				System.out.println("It is player " + currentPlayer.getPlayerNumber() + "'s turn. " + currentPlayer);
				boolean undo;
				do {
					playerTurn(currentPlayer);
					System.out.println("Enter N to undo last move. Enter any other key to continue");
					undo = scanner.nextLine().equals("N");
					if (undo) {
						undoTurn(currentPlayer);
					}
				} while (undo);
				if (board.checkIfPlayerIsTheWinner(colorMap.get(currentPlayer.getColour()) + tokenMap.get(currentPlayer.getToken())
						+ colorMap.get("reset"), this.winCondition)) {
					printWinner(currentPlayer);
					noWinner = false;
				} else {
					// reassign the variable to allow the game to continue. Note the index would wrap back to the first player if we are at the end.
					currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
				}
			}
			System.out.println("Enter Y to play again. Enter any other key to exit");
			cond = scanner.nextLine().equals("Y");
		}
		
		scanner.close();
		board.getScanner().close();
		Player.getScanner().close();
	}
}
