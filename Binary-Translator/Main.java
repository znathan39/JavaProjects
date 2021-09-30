import java.io.File;
import java.io.IOException;
import java.util.Scanner;

//Worked together with Erik Wang from period 4

class Main {
  
  public Main() {
    System.out.println("Please enter \"file\" to enter a file or \"input\" to use the console. For console input, type the number you want to use after typing \"input\" "); //Start prompt
    Scanner scanner = new Scanner(System.in);
    String input = scanner.nextLine();
    String NumberInput = "";
   
    if (input.equals("file")) { //inputting from a file
      try {
        System.out.println("Enter your file name");
        input = scanner.nextLine();
        Scanner FileScanner = new Scanner(new File(input));
        NumberInput = FileScanner.nextLine();
      } catch (IOException ex) {
        System.out.println("File not found.");
        scanner.close();
        System.exit(1);
      }
    }
   
    else {//inputting from the console
      NumberInput = scanner.nextLine();
    }

    System.out.println("If you are translating from decimal to binary, type \"DTB\"");
    System.out.println("If you are translating from binary to decimal, type \"BTD\"");
    input = scanner.nextLine();
    
    if (input.equals("DTB")) {//Decimal to binary
      String answer = "";
      int number = Integer.parseInt(NumberInput);
      while (number > 0) {
        if (number % 2 == 1) {
          answer = "1" + answer;
        }
        else {
          answer = "0" + answer;
        }
        number = number / 2;
      }
      System.out.println("Your answer is: ");
      System.out.println(answer); //End prompt
    }
    
    else {//Binary to decimal
      String swap = "";
      int length = NumberInput.length();
        for (int b = length - 1; b >= 0; b--) {
          swap = swap + NumberInput.charAt(b);
        }
      int Answer = 0;     
        for (int a = swap.length() - 1; a >= 0; a--) {
          if (swap.charAt(a) == '1') {
            Answer = Answer + (int)(Math.pow(2, a));
        }
      }
      System.out.println("Your answer is: ");
      System.out.println(Answer); //End prompt
    }
    scanner.close();
  }
  
  public static void main(String[] args) {
    new Main();
  }
}