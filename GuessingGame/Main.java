import java.util.Scanner;

public class Main {

  public Main(){
    boolean PlayingGame = true;

    while (PlayingGame == true){
    int GuessNumber = 0;
    int Guess = -1;

    int RandomNumber = (int)(Math.random() * 51);
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter a number between 0 and 50.");
    
    while (Guess != RandomNumber){
      GuessNumber = GuessNumber + 1;
      String input = scanner.nextLine();
      Guess = Integer.parseInt(input);
      if (Guess < RandomNumber) {
        System.out.println("Your guess is too low.");
      }
      if (Guess > RandomNumber) {
        System.out.println("Your guess is too high.");
      }
    }
    
    System.out.println("You took " + GuessNumber + " tries to get the correct number. Would you like to play again? Type y for yes or n for no.");
    String end = scanner.nextLine();
    if (end.equals ("yes") || end.equals ("y")) {
      PlayingGame = true;
    }
    else {
      PlayingGame = false;
    }
  }
  }
	
  public static void main(String[] args) {
    new Main();
	}
}