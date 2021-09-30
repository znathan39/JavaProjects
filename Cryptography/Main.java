/*
Author: Nathan Zou
Thanks to Ehan Masud, Nihal Parthasarathy, Jeffrey Teh for help on this project.
*/
import java.awt.GridLayout; //Importing all the commands, panels, layouts, components, etc.
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.event.*;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.*;
import java.util.Arrays;

public class Main implements ActionListener{

	JFrame frame;
  JPanel panel;
  
  JTextField encryptField = new JTextField();
  JTextField decryptField = new JTextField();
  JTextField scytaleField = new JTextField();
  JTextField caesarField = new JTextField();
  JTextField vigenereField = new JTextField();

  JLabel encryptLabel = new JLabel("Encrypted");
  JLabel decryptLabel = new JLabel("Decrypted");
  JLabel scyLabel = new JLabel("Scytale Row Number");
  JLabel caeLabel = new JLabel("Caesar Shift");
  JLabel vigLabel = new JLabel("Vigenere Keyword");

  JButton encryptButton, decryptButton, confirmButton;

  String scytaleString = "Scytale";
  String caesarString = "Caesar";
  String vigenereString = "Vigenere";

  JRadioButton scytaleButton, caesarButton, vigenereButton;

  int integer, i, j;
  String input;
  String result;
  String addition = "";
  String output;
  char [][] arr;

  public static void main(String[] args) {
    new Main();
  }

  public Main() {
    //Public Main, creates window, radiobuttons, actionlisteners, creating and filling the layout
    JFrame frame = new JFrame(("Main Window"));
    frame.setSize(500, 500);
    frame.setLayout(new GridLayout());
    JPanel panel = new JPanel(new GridLayout(0,3)); 

    encryptButton = new JButton("Encrypt");
    decryptButton = new JButton("Decrypt");
    scytaleButton = new JRadioButton(scytaleString);
    scytaleButton.setMnemonic(KeyEvent.VK_S);
    scytaleButton.setActionCommand(scytaleString);
    caesarButton = new JRadioButton(caesarString);
    caesarButton.setMnemonic(KeyEvent.VK_C);
    caesarButton.setActionCommand(caesarString);
    vigenereButton = new JRadioButton(vigenereString);
    vigenereButton.setMnemonic(KeyEvent.VK_V);
    vigenereButton.setActionCommand(vigenereString);

    ButtonGroup bGroup = new ButtonGroup();
    bGroup.add(scytaleButton);
    bGroup.add(caesarButton);
    bGroup.add(vigenereButton);

    vigenereButton.addActionListener(this);
    caesarButton.addActionListener(this);
    scytaleButton.addActionListener(this);
    encryptButton.addActionListener(this);
    decryptButton.addActionListener(this);

    //Adding components to panel, had help from Jeffrey for layout
    panel.add(encryptButton); //Row 1 
    panel.add(new JLabel(""));
    panel.add(decryptButton);
    panel.add(encryptLabel); //Row 2
    panel.add(encryptField);
    panel.add(new JLabel(""));
    panel.add(decryptLabel); //Row 3
    panel.add(decryptField);
    panel.add(new JLabel(""));
    panel.add(scytaleButton); //Row 4
    panel.add(caesarButton);
    panel.add(vigenereButton);
    panel.add(scyLabel); //Row 5 
    panel.add(scytaleField);
    panel.add(new JLabel(""));
    panel.add(caeLabel); //Row 6
    panel.add(caesarField);
    panel.add(new JLabel(""));
    panel.add(vigLabel); //Row 7
    panel.add(vigenereField);
    
    frame.add(panel);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public void actionPerformed(ActionEvent event) {
    //Encryption
    if (event.getSource().equals(encryptButton)) {
      
      //Caesar Cipher Encryption
      if (caesarButton.isSelected() == true) {
        input = decryptField.getText(); //Reading in the input and shift 
        int shift = Integer.parseInt(caesarField.getText());
        input = input.toUpperCase();
        input = input.replaceAll("[^A-Z]", ""); //Retrieved from: https://www.geeksforgeeks.org/how-to-remove-all-non-alphanumeric-characters-from-a-string-in-java/
        //Iterating over the string and shifting the letters
        for (int i = 0; i < input.length(); i++) {
          for (int j = 0; j <= shift; j++) {
            integer = input.charAt(i) + j;
            if (integer > 'Z') {//Checks if the shift, shifts letters past Z
              integer = (char)(integer + 'A' - 'Z' - 1);
            }
          }
          result = Character.toString(integer);
          addition += result;
        }
        encryptField.setText(addition); //Changes the text in the encrypted field
        addition = "";
      }
      
      //Scytale Cipher Encryption
      if (scytaleButton.isSelected() == true) { //Got a lot of help from Ehan and Nihal 
        String input =  decryptField.getText();
        String rows = scytaleField.getText();
        int row = Integer.parseInt(rows);
        //Formatting with regex to clean up input
        input = input.toUpperCase();
        input = input.replaceAll("\\s", ""); 
	      input = input.replaceAll("[^A-Z]", "");
        
        int columns = input.length() / row;
        int mod = input.length() %row;

        //Add a column if mod doesn't = 0
        if (mod != 0) {
          columns++;
        }

        //creation of array 
        char[][] arr = new char[columns][row];

        //filling in empty space with '@'
        if (mod != 0) {
          for (int i = mod; i < row; i++) {
            arr[columns - 1][i] = '@';
          }
        }

        int currentChar = 0;
        //using currentChar to store characters by writing over input
        for (int i = 0; i < row; i++) {
          for (int j = 0; j < columns; j++) {
            if (currentChar < input.length()) {
              if (arr[j][i] != '@') {
	              arr[j][i] = input.charAt(currentChar);
	              currentChar++;
	            }
            }
          }
        }

        //adding the final chars to the result
        result = "";
        for (int i = 0; i < columns; i++) {
          for (int j = 0; j < row; j++){
            result += arr[i][j];
          }
        }
        encryptField.setText(result);
        result = "";
      }

      //Vigenere Cipher Encryption
      if (vigenereButton.isSelected() == true) {
        //Code done with help from Jeffrey and: https://javahungry.blogspot.com/2019/12/vigenere-cipher-in-java.html
        String input = decryptField.getText();
        String keyword = vigenereField.getText();
        //More code from, cleans up keyword and input with regular expressions: https://www.geeksforgeeks.org/how-to-remove-all-non-alphanumeric-characters-from-a-string-in-java/
        input = input.toUpperCase();
        keyword = keyword.toUpperCase();
        input = input.replaceAll("\\s", "");
        keyword = keyword.replaceAll("\\s", "");
        input = input.replaceAll("[^A-Z]", "");
        keyword = keyword.replaceAll("[^A-Z]", "");
        
        char characters[] = input.toCharArray();
        int textLength = input.length();

        //Creates new char arrays for key and result
        char key[] = new char[textLength];
        char result[] = new char[textLength];

        //Generates a key from the keyword
        for (int i = 0, j = 0; i < textLength; i++ , j++) {
          if (j == keyword.length()) {
            j = 0;
          }
          key[i] = keyword.charAt(j);
        }

        //Encryption via iteration over the text and changing the values by the key
        for (i = 0; i < textLength; i++) {
          result[i] = (char) (((characters[i] + key[i]) % 26) + 'A');
          encryptField.setText(String.valueOf(result));
        }
      }
    }
    
    //Decryption
    if (event.getSource().equals(decryptButton)) {
      
      //Caesar Cipher Decryption
      if(caesarButton.isSelected() == true) {
        String input2 = encryptField.getText();
        int shift = Integer.parseInt(caesarField.getText());
        input2 = input2.toUpperCase(); //Cleans up input with regular expressions
        input2 = input2.replaceAll("[^A-Z]", ""); //Code taken from (again): https://www.geeksforgeeks.org/how-to-remove-all-non-alphanumeric-characters-from-a-string-in-java/
        //Similar to before, just subtracting instead 
        for (int i = 0; i < input2.length(); i++) {
          for (int j = 0; j <= shift; j++) {
            integer = input2.charAt(i) - j;
            if (integer < 'A') {
              integer = (char)(integer - 'A' + 'Z' + 1);
            }
          }
          result = Character.toString(integer);
          addition += result;
        }
        //Setting text field to the result
        decryptField.setText(addition);
        addition = "";
      }

      //Scytale Cipher Decryption
      if (scytaleButton.isSelected() == true) {//Help recieved on this from Ehan and Nihal
        String input = encryptField.getText();
        String rows = scytaleField.getText();
        int row = Integer.parseInt(rows);
        //Clean up input with more regular expressions
        input = input.toUpperCase();
        input = input.replaceAll("[^A-Z]", ""); //Code once again retrieved from: https://www.geeksforgeeks.org/how-to-remove-all-non-alphanumeric-characters-from-a-string-in-java/

        //Following code is almost the same as encryption
        int columns = input.length() / row;
        int mod = input.length() % row;

        if (mod != 0) {
          columns++;
        }
        //creation of char array
        char[][] arr = new char[columns][row];

        //filling in blank spaces with '@' if needed
        if (mod != 0) {
          for (int i = mod; i < row; i++) {
            arr[columns - 1][i] = '@';
          }
        }

        int currentChar = 0;
        //Iterating over dimensions to get chars
        for (int i = 0; i < row; i++) {
          for (int j = 0; j < columns; j++) {
            if (currentChar < input.length()) {
              if (arr[i][j] != '@') {
                arr[i][j] = input.charAt(currentChar);
                currentChar++;
              }
            }
          }
        }
        result = "";

        //getting the result by adding to the string
        for (int i = 0; i < columns; i++) {
          for (int j = 0; j < row; j++) {
            result += arr[j][i]; //arr[j][i] switched from arr[i][j] in encryption
          }
        }
        decryptField.setText(result); //Setting text field to show result
        result = "";
      }

      //Vigenere Cipher Decryption 
      if (vigenereButton.isSelected() == true) {
        //More help from Jeffrey and: https://javahungry.blogspot.com/2019/12/vigenere-cipher-in-java.html
        String input = encryptField.getText();
        String keyword = vigenereField.getText();
        input = input.toUpperCase();
        keyword = keyword.toUpperCase();
        input = input.replaceAll("\\s", "");
        char msg[] = input.toCharArray();
        int msgLength = msg.length;

        //Creating new char arrays
        char key[] = new char[msgLength];
        char decryptedMsg[] = new char[msgLength];

        for (int i = 0, j = 0; i < msgLength; i++, j++) {
          if (j == keyword.length()) {
            j = 0;
          }
          key[i] = keyword.charAt(j);
        }
        for (i = 0; i < msgLength; i++) {
          decryptedMsg[i] = (char)((((msg[i] - key[i]) + 26) % 26) + 'A');
          decryptField.setText(String.valueOf(decryptedMsg));
        }
      }
    }
  }
}