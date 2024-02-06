package game_classes;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Player {
	
	private final String name;
	private final String playerNumber;
	private String colour;
	private String token;
	private static Scanner scanner = new Scanner(System.in);
	
	public Player(String name, String playerNumber) {
		this.name = name;
		this.playerNumber = playerNumber;
	}

	public String getName() {
		return name;
	}

	public String getPlayerNumber() {
		return playerNumber;
	}

	public int makeMove() {
		
		boolean cond = true;
		while (cond) {
			try {
				System.out.println("Make your move. What column do you want to put a token in?");
				int column = Player.scanner.nextInt();
				scanner.nextLine();
				return column;
			} catch (InputMismatchException ex) {
				scanner.nextLine();
	    		System.out.println("Please input a valid integer!!");
	    	}
		}
		return 0;
	}
	
	@Override
	public String toString() {
		return String.format("Player %s is %s", playerNumber, name);
	}
	
	public static Scanner getScanner() {
		return scanner;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getColour() {
		return colour;
	}

	public String getToken() {
		return token;
	}
}
