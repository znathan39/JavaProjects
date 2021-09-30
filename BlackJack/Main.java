/*
Authors: Jeffrey Teh and Nathan Zou
Finished January 2021
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
	
  //Creation of ints, booleans, strings, and array list of strings (deck)
  ArrayList<String> deck = new ArrayList<String>();
	int y = 0;
	int e = 0;
	int f = 0;
	int g = 0;
	int b = 0;
	int playerValue = 0;
	int dealerValue = 0;
  int playerWins = 0;
  int dealerWins = 0;
	String playerHand = "";
	String dealerHand = "";
	boolean playing;
  boolean playerState;
  boolean GameOver;
  
  //Main method, contains all of the methods
  public Main() {
	  getFile();
	  start();
	  playing = true;
	  GameOver = false;
	  Game();
  }
 
  //Reset method, resets the game and starts it over
  public void reset() {
	  playerHand = "";
	  playerValue = 0;
	  dealerHand = "";
	  dealerValue = 0;
	  getFile();
	  start();
  }
  
  //Game method (Runs while you're playing)
  public void Game() {
	  while(playing == true) {
		  Scanner sc = new Scanner(System.in);
		  String input = sc.nextLine();
		  if(input.equals("hit")) {//Hit command, see Hit() method
	      playerState = true;
			  hit();
			  System.out.println("Your Hand: " + playerHand + "\n" + "Value: " + (""+playerValue)+ "\n");
			  checkOver();
        CheckTie();
			  CheckWin();
		  }
		  else if(input.equals("quit")) {//Quit command, quits the game
		  	System.out.println("Thanks for Playing!");
			  playing = false;
		  	System.exit(0) ;
		  }
	    else if(input.equals("stand")) {//Stand command, runs the stand method
			  stand();
			  System.out.println("Your Hand: " + playerHand + "\n" + "Value: " + (""+playerValue)+ "\n");
			  System.out.println("Dealer Hand: " + dealerHand+ "\n" + "Value: " + (""+dealerValue));
        CheckTie();
	      CheckWin();
		  }
		  else if(input.equals("help")) {//Help command, runs the help method
			  getHelp();
		  }
	  }
  }
  
  //Checkover method, checks if the game is over by seeing if the value of your cards is over 21. (You Bust)
  public void checkOver() {
	  if(playerValue > 21) {
		  playerState = false;
	  }
  }
  
  //Checkwin method, this checks for all the possible ways of winning the game
  public void CheckWin(){
    //Player wins, player value is greater than dealer value and player value is less than or equal to 21
    if(playerState == false && playerValue > dealerValue && playerValue <= 21){
      playerWins++;
      System.out.println("You have won! Dealer Wins: " + dealerWins + " Player Wins: " + playerWins);
      System.out.println("would you like to play again? Type yes or y if you'd like to do so.");
      GameOver = true;
      Scanner scanner8 = new Scanner(System.in);
	    String result = scanner8.next();
	    if(result.equals("yes") || result.equals("y")) {
	    	playing = true;
	    	reset();
	    	Game();
	    }
	    else {//Quitting the game 
	    	System.out.println("Thanks for Playing! You won a total of: " + playerWins + " times.");
	    	playing = false;
	    }
    }
    //Player win, player value is less than or equal to 21 and dealer value is over 21
    else if(playerState == false && playerValue <= 21 && dealerValue > 21){
      playerWins++;
      System.out.println("You have won! Dealer Wins: " + dealerWins + " Player Wins: " + playerWins);
      System.out.println("would you like to play again? Type yes or y if you'd like to do so.");
      GameOver = true;
      Scanner scanner8 = new Scanner(System.in);
	    String result = scanner8.next();
	    if(result.equals("yes") || result.equals("y")) {
	    	playing = true;
	    	reset();
	    	Game();
	    }
	    else {//Quitting the game
	    	System.out.println("Thanks for Playing! You won a total of: " + playerWins + " times.");
	    	playing = false;
	    }
    }
    //Dealer win, dealer value is greater than player value and dealer value is less than or equal to 21
    else if(playerState == false && dealerValue > playerValue && dealerValue <= 21){
      dealerWins++;
      System.out.println("Dealer Wins! Dealer Wins: " + dealerWins + " Player Wins: " + playerWins);
      System.out.println("would you like to play again? Type yes or y if you'd like to do so.");
      GameOver = true;
      Scanner scanner8 = new Scanner(System.in);
	    String result = scanner8.next();
	    if(result.equals("yes") || result.equals("y")) {
	    	playing = true;
	    	reset();
	    	Game();
	    }
	    else {//Quitting the game
	    	System.out.println("Thanks for Playing! You won a total of: " + playerWins + " times.");
	    	playing = false;
	    }
    }
    //Dealer win, dealer value is less than or equal to 21 and player value is over 21
    else if(playerState == false && dealerValue <= 21 && playerValue > 21){
      dealerWins++;
      System.out.println( "Dealer has won! Dealer Wins: " + dealerWins + " Player Wins: " +playerWins);
      System.out.println("would you like to play again? Type yes or y if you'd like to do so.");
      GameOver = true;
      Scanner scanner8 = new Scanner(System.in);
	    String result = scanner8.next();
	    if(result.equals("yes") || result.equals("y")) {
	    	playing = true;
	    	reset();
	    	Game();
	  	}
	    else {//Quitting the game
	    	System.out.println("Thanks for Playing! You won a total of: " + playerWins + " times.");
	    	playing = false;
	    }
    }
  }
  //CheckTie method, this checks the value of the dealer hand and player hand to check if they are equal
  public void CheckTie() { 
    if(playerState == false){
      if(dealerValue == playerValue){
        System.out.println("That's a tie! Dealer Wins: " + dealerWins
        + " Player Wins: " + playerWins);
        System.out.println("would you like to play again? Type yes or y if you'd like to do so.");
        GameOver = true;
        Scanner scanner8 = new Scanner(System.in);
  	    String result = scanner8.next();
  	    if(result.equals("yes") || result.equals("y")) {
  	  	  playing = true;
  	      reset();
  	  	  Game();
  	    }
  	    else {//Quitting the game
  	  	  System.out.println("Thanks for Playing! You won a total of: " + playerWins + " times.");
  	  	  playing = false;
  	    }
      }
    }
  }
  
  //Starttext method, This prints out the starting text 
  public void startText() {
		String start1 = "Type 'Help' for game help 'Quit' to quit game";
		String start2 = "Type “hit” to draw another card, or “stand” to pass:" + "\n";
		String start = start1 + "\n" + start2;
		System.out.println(start);
  }
  
  //Start method, this starts the game and includes the starting text, this also draws your first two cards
  public void start() {
    shuffleDeck();
	  System.out.println("Welcome to BlackJack!");
    startText();
    String card1 = drawCard();
	  String card2 = drawCard();
    String card3 = drawCard();
		String card4 = drawCard();
		playerValue = getValue(card1) + getValue(card2);
		playerHand = (card1 + " "+ card2); 
		System.out.println("Your Hand: " + playerHand+ "\n" + "Value: " + (""+playerValue));
		dealerValue = getValue(card3) + getValue(card4);
		dealerHand = (card3 + " "+ card4); 
		System.out.println("Dealer Hand: " + dealerHand+ "\n" + "Value: " + (""+dealerValue));
  }

  //Stand method, runs if you type "stand". This means that youre turn is over and the dealer will go next. If you stand you cannot hit again. THe dealer will continue to draw cards as long as the value of their current hand is less than 17.
  public void stand() {
    playerState = false;
    while(dealerValue + y < 17) {
      String temp1 = drawCard();
		  int y = getValue(temp1);
		  dealerValue = dealerValue + y;
		  dealerHand = dealerHand + " " + temp1;
    }
  }
	
  //Hit method, runs if you type "hit". This gives you a new card in your hand, and adds the value of that card into an int, playerValue
  public void hit() {
    if(playerState = true){
		  String temp2 = drawCard();
		  int x = getValue(temp2);
		  playerValue = playerValue + x;
		  playerHand = playerHand + " " + temp2;
    }
	}

  //This method that uses fileScanner to locate and read through the Deck.txt file of cards, and put them into an ArrayList of Strings. 
	public void getFile() {
		try {
			Scanner fileScanner = new Scanner(new File("Deck.txt"));
			while (fileScanner.hasNextLine() == true) {
				deck.add(fileScanner.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}	
	}
  
  //Method that shuffles the deck ArrayList with a command imported through the Collections package.
	public void shuffleDeck() {
		Collections.shuffle(deck);
	}
	
  //This is a method to draw a card from the deck, and put it in the player or dealer's hand. This is done in the string format.
	public String drawCard() {
	int z = (int)(Math.random()*deck.size());
		String temp3 = deck.get(z);
		deck.remove(z);
		return temp3;
	}
  
	//This method gets the value of a card from the string/text stored within the deck. This is done by reading the first digit/Character of the card.
	public int getValue(String s) {
		char c = s.charAt(0);
		if(c== 'k' || c == 'q' || c == 'j' || c == '1') {
			b = 10;
		}
		else if(c == 'a'){
		 b = 1;
		}
		else if(Character.isDigit(c) == true && c != '1') {
			return Integer.parseInt(""+ c);
		}
		return b;
	}
  
	//Method that pulls up a help manual. This is run any time you type "help" into the console.	
	public void getHelp() {
	  System.out.println("This is a simple BlackJack.");
	  System.out.println("In this version, it is you vs the computer");
	  System.out.println("Each participant attempts to beat the dealer");
	  System.out.println("by getting a count as close to 21 as possible,");
	  System.out.println("without going over 21.");
	  System.out.println();
	  System.out.println("If you need help understanding the cards, here is a list:");
	  System.out.println("https://docs.google.com/document/d/1VWKp9cTh2P8_CSCYUiNGCwLpela7sO585GKWXJ6KtsI/edit" + "\n");
    System.out.println("A full user's manual is available here: https://docs.google.com/document/d/1y5WT6B3CTPsQmgI8PUv88INqSmm9txvd7-jC78HqrRU/edit?usp=sharing");
	}

	public static void main(String[] args) {
		new Main();
	}

}
