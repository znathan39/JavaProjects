/*
Author: Nathan Zou
Code from J. Galbraith Farkle videos
*/

//Single one or five, you get points (100, 50)
//Three of a kind, you get 100 times dice value (ones give 1000)
//Four of a kind, you get double what you would get got triples

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.*;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main implements ActionListener {

  JFrame frame = new JFrame();
  Container diceContainer = new Container();
  JButton[] diceButtons = new JButton[6];
  ImageIcon[] imageIcons = new ImageIcon[6];
  int[] buttonState = new int[6];
  int[] dieValue = new int[6];
  final int HOT_DIE = 0;
  final int SCORE_DIE = 1;
  final int LOCKED_DIE = 2;
  Container buttonContainer = new Container();
  JButton rollButton = new JButton("Roll");
  JButton scoreButton = new JButton("Score");
  JButton stopButton = new JButton("Stop");
  Container labelContainer = new Container();
  JLabel currentScoreLBL = new JLabel("Current Score: 0");
  JLabel totalScoreLBL = new JLabel("Total Score: 0");
  JLabel currentRoundLBL = new JLabel("Current Round: 0");
  int currentScore = 0;
  int totalScore = 0;
  int currentRound = 1;

  public Main() {
    //Defines and fills the image icon array
    frame.setSize(600,600);
    imageIcons[0] = new ImageIcon("./img/1.jpg");
    imageIcons[1] = new ImageIcon("./img/2.jpg");
    imageIcons[2] = new ImageIcon("./img/3.jpg");
    imageIcons[3] = new ImageIcon("./img/4.jpg");
    imageIcons[4] = new ImageIcon("./img/5.jpg");
    imageIcons[5] = new ImageIcon("./img/6.jpg");
    //Layout for diceContainer
    diceContainer.setLayout(new GridLayout(2,3));
    //Filling in the diceButtons array, sets the color
    for (int a = 0; a < diceButtons.length; a++) {
      diceButtons[a] = new JButton();
      diceButtons[a].setIcon(imageIcons[a]);
      diceButtons[a].setEnabled(false);
      diceButtons[a].addActionListener(this);
      diceButtons[a].setBackground(Color.LIGHT_GRAY);
      diceContainer.add(diceButtons[a]);
    }
    //Adding action listeners to the buttons, sets the starting layout, enabling what needs to be enabled and what's not
    buttonContainer.setLayout(new GridLayout(1,3));
    buttonContainer.add(rollButton);
    rollButton.addActionListener(this);
    buttonContainer.add(scoreButton);
    scoreButton.addActionListener(this);
    scoreButton.setEnabled(false);
    buttonContainer.add(stopButton);
    stopButton.addActionListener(this);
    stopButton.setEnabled(false);
    labelContainer.setLayout(new GridLayout(3,1));
    labelContainer.add(currentScoreLBL);
    labelContainer.add(totalScoreLBL);
    labelContainer.add(currentRoundLBL);

    frame.setLayout(new BorderLayout());
    frame.add(diceContainer, BorderLayout.CENTER);
    frame.add(buttonContainer, BorderLayout.NORTH);
    frame.add(labelContainer, BorderLayout.SOUTH);

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);

  }

  public static void main(String[] args) {
    new Main();
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource().equals(rollButton)) {
      //Roll button
      for (int a = 0; a < diceButtons.length; a++) {
        if (buttonState[a] == HOT_DIE) {
          int choice = (int)(Math.random() * 6);
          dieValue[a] = choice;
          diceButtons[a].setIcon(imageIcons[choice]);
          diceButtons[a].setEnabled(true);
          rollButton.setEnabled(false);
          scoreButton.setEnabled(true);
          stopButton.setEnabled(true);
        }
      }
    }
    else if (e.getSource().equals(scoreButton)) {
      //Score button
      int[] valueCount = new int[7];
      for (int a = 0; a < diceButtons.length; a++) {
        if (buttonState[a] == SCORE_DIE) {
          valueCount[dieValue[a] + 1]++;
        }
      }
      //The invalid dies 
      if ((valueCount[2] > 0 && valueCount[2] < 3) || 
          (valueCount[3] > 0 && valueCount[3] < 3) ||
          (valueCount[4] > 0 && valueCount[4] < 3) ||
          (valueCount[6] > 0 && valueCount[6] < 3) ) {
        //Invalid die selection
        JOptionPane.showMessageDialog(frame, "Invalid die selected");
      }
      else if (valueCount[1] == 0 && valueCount[2] == 0 &&
               valueCount[3] == 0 && valueCount[4] == 0 && 
               valueCount[5] == 0 && valueCount[6] == 0){
        Object[] options = {"Yes" , "No"};
        int dialogueChoice = JOptionPane.showOptionDialog(frame, "Forfeit Score?", "Forfeit Score?", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
        if (dialogueChoice == JOptionPane.YES_OPTION) {
          currentScore = 0;
          currentRound++;
          currentScoreLBL.setText("Current Score: " + currentScore);
          currentRoundLBL.setText("Current Round: " + currentRound);
          resetDice();
        }
      }
      else {//Scoring
        //Triples Scoring
        if (valueCount[1] >= 3) {
          currentScore += (valueCount[1] - 2) * 1000;
        }
        if (valueCount[2] >= 3) {
          currentScore += (valueCount[2] - 2) * 200;
        }
        if (valueCount[3] >= 3) {
          currentScore += (valueCount[3] - 2) * 300;
        }
        if (valueCount[4] >= 3) {
          currentScore += (valueCount[4] - 2) * 400;
        }
        if (valueCount[5] >= 3) {
          currentScore += (valueCount[5] - 2) * 500;
        }
        if (valueCount[6] >= 3) {
          currentScore += (valueCount[6] - 2) * 600;
        }
        //Single One or Five scoring
        if (valueCount[1] < 3) {
          currentScore += valueCount[1] * 100;
        }
        if (valueCount[5] < 3) {
          currentScore += valueCount[5] * 50;
        }
        currentScoreLBL.setText("Current Score: " + currentScore);
        for (int a = 0; a < diceButtons.length; a++) {
          if (buttonState[a] == SCORE_DIE) {
            buttonState[a] = LOCKED_DIE;
            diceButtons[a].setBackground(Color.BLUE);
          }
          diceButtons[a].setEnabled(false);
        }
        //If all 6 die are locked, resets all dice
        int lockedCount = 0;
        for (int a = 0; a < diceButtons.length; a++) {
          if (buttonState[a] == LOCKED_DIE) {
            lockedCount++;
          }
        }
        if (lockedCount == 6) {
          for (int a = 0; a < diceButtons.length; a++) {
            buttonState[a] = HOT_DIE;
            diceButtons[a].setBackground(Color.LIGHT_GRAY);
          }
        }
        rollButton.setEnabled(true);
        scoreButton.setEnabled(false);
        stopButton.setEnabled(true);
      }

    }
    else if (e.getSource().equals(stopButton)) {
      //Stop button
      totalScore += currentScore;
      currentScore = 0;
      currentScoreLBL.setText("Current Score: " + currentScore);
      totalScoreLBL.setText("Total Score: " + totalScore);
      currentRound++;
      currentRoundLBL.setText("Current Round: " + currentRound);
      resetDice();
    }
    else {//Playing the game
      for (int a = 0; a < diceButtons.length; a++) {
        if (e.getSource().equals(diceButtons[a])) {
          if (buttonState[a] == HOT_DIE) {
            diceButtons[a].setBackground(Color.RED);
            buttonState[a] = SCORE_DIE;
          }
          else {
            diceButtons[a].setBackground(Color.LIGHT_GRAY);
            buttonState[a] = HOT_DIE;
          }
        }
      }
    }

  }

  public void resetDice() {//Method for resetting all of the dice
    for (int a = 0; a < diceButtons.length; a++) {
      diceButtons[a].setEnabled(false);
      buttonState[a] = HOT_DIE;
      diceButtons[a].setBackground(Color.LIGHT_GRAY);
    }
    rollButton.setEnabled(true);
    scoreButton.setEnabled(false);
    stopButton.setEnabled(false);
  }

}