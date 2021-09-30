import java.util.Scanner;

class Main {
  
  int[][] board = new int[3][3];
  final int BLANK = 0;
  final int X_MOVE = 1;
  final int O_MOVE = 2;
  final int X_TURN = 0;
  final int O_TURN = 1;
  int turn = X_TURN;
  int X_WINS = 0;
  int O_WINS = 0;

  Scanner scanner;
  String input = "";

  public Main() {
    scanner = new Scanner(System.in);
    boolean stillPlaying = true;
    while (stillPlaying == true) { //Loop for when playing the game

      while (checkWin(X_MOVE) == false && checkWin(O_MOVE) == false && checkTie () == false) {
      printBoard();
      input = scanner.nextLine();
      
      if (input.length() != 2) {
        System.out.println("Enter a letter followed by a number.");
      } //error message for wrong formatting
      else if (input.charAt(0) != 'a' && 
               input.charAt(0) != 'b' && 
               input.charAt(0) != 'c' ) {
               System.out.println("Row must be an a, b, or c"); 
              }
      else if (input.charAt(1) != '1' && 
               input.charAt(1) != '2' && 
               input.charAt(1) != '3' ) {
               System.out.println("Column must be a 1, 2, or 3");
              }
      
      else { //Checking for turn
        int row = input.charAt(0) - 'a';
        int column = input.charAt(1) - '1';
        if (board[row][column] == BLANK) { 
          if (turn == X_TURN) {
            board[row][column] = X_MOVE;
            turn = O_TURN;
          }
          else {
            board[row][column] = O_MOVE;
            turn = X_TURN;
          }
        }
        else {
          System.out.println("There is a piece there!"); //error message for aready having a move there
        }
      }
      }
      printBoard();
      if (checkWin(X_MOVE) == true) { //X wins
        X_WINS = X_WINS + 1;
        System.out.println("X Wins!");
        }
      
      else if (checkWin(O_MOVE) == true) { //O wins
        O_WINS = O_WINS + 1;
        System.out.println("O Wins!");
      }
      
      else if (checkTie() == true) { //Tie 
        System.out.println("There was a tie between X and O.");
      } 

      System.out.println("X has won " + X_WINS + " times. O has won " + O_WINS + " times."); //Win counter
      System.out.println("Would you like to play again? Type \"y\" or \"n\" (y for yes, n for no)");
      String input = scanner.nextLine();
        if (input.equals ("yes") || input.equals ("y")) {
          stillPlaying = true;
          board[0][0] = BLANK; //resets board
          board[0][1] = BLANK;
          board[0][2] = BLANK;
          board[1][0] = BLANK;
          board[1][1] = BLANK;
          board[1][2] = BLANK;
          board[2][0] = BLANK;
          board[2][1] = BLANK;
          board[2][2] = BLANK;
        }
        else {
          stillPlaying = false;
        }
    }
  }
  
  public static void main(String[] args) {
    new Main();
  }
  
  public void printBoard() { //
    System.out.println("  \t1\t2\t3");
    for (int row = 0; row < board.length; row++) {
      String output = (char)('a'+row) + "\t";
      for (int column = 0; column < board[0].length; column++) {
        if (board[row][column] == BLANK) {
          output += " \t";
        }
        else if (board[row][column] == X_MOVE) {
          output += "X\t";
        }
        else if (board[row][column] == O_MOVE) {
          output += "O\t";
        }
      }
      System.out.println(output);
    }
  }

  public boolean checkWin(int player) { //all possible win combinations
    if (board[0][0] == player && board[0][1] == player && board[0][2] == player) { //Top row
      return true;
    }
    if (board[1][0] == player && board[1][1] == player && board[1][2] == player) { //Middle row
      return true;
    }
    if (board[2][0] == player && board[2][1] == player && board[2][2] == player) { //Bottom row
      return true;
    }
    if (board[0][0] == player && board[1][1] == player && board[2][2] == player) { //Left to right diagonal
      return true;
    }
    if (board[0][2] == player && board[1][1] == player && board[2][0] == player) { //Right to left diagonal
      return true;
    }
    if (board[0][0] == player && board[1][0] == player && board[2][0] == player) { //Left column
      return true;
    }
    if (board[0][1] == player && board[1][1] == player && board[2][1] == player) { //Middle column
      return true;
    }
    if (board[0][2] == player && board[1][2] == player && board[2][2] == player) { //Right column
      return true;
    }
    return false;
  }

  public boolean checkTie() { //Checks if board is full, if full = tie 
    for (int row = 0; row < board.length; row++) {
      for (int column = 0; column < board[0].length; column++) {
        if (board[row][column] == BLANK) {
          return false;
        }
      }
    }
    return true;
  }

}

//GET TO TIE
//b2 a2 c2 b1 b3 c3 a1 a3 c1