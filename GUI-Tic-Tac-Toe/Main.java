import java.awt.BorderLayout; //Importing all AWT and AWT Event commands
import java.awt.GridLayout;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame; //Importing all Java Swing commands
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Main implements ActionListener {

  JFrame frame = new JFrame();
  JButton[][] button = new JButton[3][3];
  int[][] board = new int[3][3];
  final int BLANK = 0;
  final int X_MOVE = 1;
  final int O_MOVE = 2;
  final int X_TURN = 0;
  final int O_TURN = 1;
  int turn = X_TURN;
  Container center = new Container(); //Creation of all containers, labels, text prompts
  JLabel xLabel = new JLabel("X Wins: 0");
  JLabel oLabel = new JLabel("O Wins: 0");
  JButton xChangeName = new JButton("Change X's name.");
  JButton oChangeName = new JButton("Change O's name.");
  JTextField xChangeField = new JTextField();
  JTextField oChangeField = new JTextField();
  Container north = new Container();
  String xPlayerName = "X";
  String oPlayerName = "O";
  int xwins = 0;
  int owins = 0;

  public Main() {
    frame.setSize(400, 400);
    
    //Center grid container
    frame.setLayout(new BorderLayout());
    center.setLayout(new GridLayout(3,3));
    for (int i = 0; i < button.length; i++) {
      for (int j = 0; j < button[0].length; j++) {
        button[j][i] = new JButton(j+ "," +i);
        center.add(button[j][i]);
        button[j][i].addActionListener(this);
      }
    }
    frame.add(center, BorderLayout.CENTER);

    //North container
    north.setLayout(new GridLayout(3,2));
    north.add(xLabel);
    north.add(oLabel);
    north.add(xChangeName);
    xChangeName.addActionListener(this);
    north.add(oChangeName);
    oChangeName.addActionListener(this);
    north.add(xChangeField);
    north.add(oChangeField);
    frame.add(north, BorderLayout.NORTH);

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //When closing window, closes the whole program
    frame.setVisible(true);

  }

  public static void main(String[] args) {
    new Main();
  }

  public void actionPerformed(ActionEvent event) {
    JButton current;
    boolean gridButton = false;
    for (int i = 0; i < button.length; i++) {
      for (int j = 0; j < button[0].length; j++) {
        if (event.getSource().equals(button[j][i])) {
          gridButton = true;
          current = button [j][i];
          if (board[j][i] == BLANK) { //Turn system
            if (turn == X_TURN) {
              current.setText("X");
              board[j][i] = X_MOVE;
              current.setEnabled(false);
              turn = O_TURN;
            }
            else {
              current.setText("O");
              board[j][i] = O_MOVE;
              current.setEnabled(false);
              turn = X_TURN;
            }
            //Check for win/tie
            if (checkWin(X_MOVE) == true) {
              //X Wins
              xwins++;
              xLabel.setText(xPlayerName + " wins: " + xwins);
              clearBoard();
              //ungraying buttons
              current = button[0][0];
              current.setEnabled(true);
              current = button[0][1];
              current.setEnabled(true);
              current = button[0][2];
              current.setEnabled(true);
              current = button[1][0];
              current.setEnabled(true);
              current = button[1][1];
              current.setEnabled(true);
              current = button[1][2];
              current.setEnabled(true);
              current = button[2][0];
              current.setEnabled(true);
              current = button[2][1];
              current.setEnabled(true);
              current = button[2][2];
              current.setEnabled(true);
            }
            else if (checkWin(O_MOVE) == true) {
              //O Wins
              owins++;
              oLabel.setText(oPlayerName + " wins: " + owins);
              clearBoard();
              //ungraying buttons
              current = button[0][0];
              current.setEnabled(true);
              current = button[0][1];
              current.setEnabled(true);
              current = button[0][2];
              current.setEnabled(true);
              current = button[1][0];
              current.setEnabled(true);
              current = button[1][1];
              current.setEnabled(true);
              current = button[1][2];
              current.setEnabled(true);
              current = button[2][0];
              current.setEnabled(true);
              current = button[2][1];
              current.setEnabled(true);
              current = button[2][2];
              current.setEnabled(true);
            }
            else if (checkTie() == true) {
              //Tie
              clearBoard();
              //ungraying buttons
              current = button[0][0];
              current.setEnabled(true);
              current = button[0][1];
              current.setEnabled(true);
              current = button[0][2];
              current.setEnabled(true);
              current = button[1][0];
              current.setEnabled(true);
              current = button[1][1];
              current.setEnabled(true);
              current = button[1][2];
              current.setEnabled(true);
              current = button[2][0];
              current.setEnabled(true);
              current = button[2][1];
              current.setEnabled(true);
              current = button[2][2];
              current.setEnabled(true);
            }
          }
        }
      }
    }
    
    if (gridButton == false) {
      if (event.getSource().equals(xChangeName) == true) {
        xPlayerName = xChangeField.getText();
        xLabel.setText(xPlayerName + " wins: " + xwins);
      }
      else if (event.getSource().equals(oChangeName) == true) {
        oPlayerName = oChangeField.getText();
        oLabel.setText(oPlayerName + " wins: " + owins);
      }
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

  public void clearBoard() { //clearboard method
    for (int a = 0; a < board.length; a++) {
      for (int b = 0; b < board[0].length; b++) {
        board[a][b] = BLANK;
        button[a][b].setText("");
      }
    }
    turn = X_TURN;
  }

}