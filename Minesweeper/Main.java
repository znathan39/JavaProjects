/*
Author: Nathan Zou
Code from J. Galbraith Minesweeper videos
*/ 
import java.awt.*;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.event.*;
import java.awt.event.ActionListener;

import java.util.*;
import java.util.ArrayList;

public class Main implements ActionListener {

  JFrame frame = new JFrame("Minesweeper");
  JButton reset = new JButton("Reset");
  JButton[][] buttons = new JButton[20][20];
  int[][] counts = new int[20][20];
  Container grid = new Container();
  final int MINE = 10;

  public Main() {
    frame.setSize(1000,700);
    frame.setLayout(new BorderLayout());
    frame.add(reset, BorderLayout.NORTH);
    reset.addActionListener(this);

    //Button grid
    grid.setLayout(new GridLayout(20,20));
    for (int a = 0; a < buttons.length; a++) {
      for (int b = 0; b < buttons[0].length; b++) {
        buttons[a][b] = new JButton();
        buttons[a][b].addActionListener(this);
        grid.add(buttons[a][b]);
      }
    }
    frame.add(grid, BorderLayout.CENTER);
    createRandomMines();

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    new Main();
  }

  public void createRandomMines() {
    //Initialize list of random pairs
    ArrayList<Integer> list = new ArrayList<Integer>();
    for (int x = 0; x < counts.length; x++) {
      for (int y = 0; y < counts[0].length; y++) {
        list.add(x*100+y);
      }
    }
    //Resets counts, picks out 30 mines 
    counts = new int[20][20];
    for (int a = 0; a < 30; a++) {
      int choice = (int)(Math.random() * list.size());
      counts[list.get(choice) / 100][list.get(choice) % 100] = MINE;
      list.remove(choice);
    }
    //Initialize neighbor counts
    for (int x = 0; x < counts.length; x++) {
      for (int y = 0; y < counts[0].length; y++) {
        if (counts[x][y] != MINE) {
          int neighborCount = 0;
          if (x > 0 && y > 0 && counts[x-1][y-1] == MINE) {//Up Left
            neighborCount++;
          }
          if (y > 0 && counts[x][y-1] == MINE) {//Up
            neighborCount++;
          }
          if (x < counts.length - 1 && y > 0 && counts[x+1][y-1] == MINE) {//Up right
            neighborCount++;
          }
          if (x > 0 && counts[x-1][y] == MINE) {//Left
            neighborCount++;
          }
          if (x < counts.length - 1 && counts[x+1][y] == MINE) {//Right
            neighborCount++;
          }
          if (x > 0 && y < counts[0].length - 1 && counts[x-1][y+1] == MINE) {//Down left
            neighborCount++;
          }
          if (y < counts[0].length - 1 && counts[x][y+1] == MINE) {//Down
            neighborCount++;
          }
          if (x < counts.length - 1 && y < counts[0].length - 1 && counts[x+1][y+1] == MINE) {//Down right
            neighborCount++;
          }
          counts[x][y] = neighborCount;
        }
      }
    }
  }

  public void lostGame() {
    for (int x = 0; x < buttons.length; x++) {
      for (int y = 0; y < buttons[0].length; y++){
        if (buttons[x][y].isEnabled()) {
          if (counts[x][y] != MINE) {
            buttons[x][y].setText(counts[x][y] + "");
            buttons[x][y].setEnabled(false);
          }
          else {
            buttons[x][y].setText("X");
            buttons[x][y].setEnabled(false);
          }
        }
      }
    }
  }

  public void clearZeros(ArrayList<Integer> toClear) {
    if (toClear.size() == 0) {
      return;
    }
    else {
      int x = toClear.get(0) / 100;
      int y = toClear.get(0) % 100;
      toClear.remove(0);
      if (counts[x][y] == 0) {
        if (x > 0 && y > 0 && buttons[x-1][y-1].isEnabled()) {//Up left
          buttons[x-1][y-1].setText(counts[x-1][y-1] + "");
          buttons[x-1][y-1].setEnabled(false);
          if (counts[x-1][y-1] == 0) {
            toClear.add((x-1) * 100 + (y-1));
          }
        }
        if (y > 0 && buttons[x][y-1].isEnabled()) {//Up
          buttons[x][y-1].setText(counts[x][y-1] + "");
          buttons[x][y-1].setEnabled(false);
          if (counts[x][y-1] == 0) {
            toClear.add(x * 100 + (y-1));
          }
        }
        if (x < counts.length - 1 && y > 0 && buttons[x+1][y-1].isEnabled()) {//Up right
          buttons[x+1][y-1].setText(counts[x+1][y-1] + "");
          buttons[x+1][y-1].setEnabled(false);
          if (counts[x+1][y-1] == 0) {
            toClear.add((x+1) * 100 + (y-1));
          }
        }
        if (x > 0 && buttons[x-1][y].isEnabled()) {//Left
          buttons[x-1][y].setText(counts[x-1][y] + "");
          buttons[x-1][y].setEnabled(false);
          if (counts[x-1][y] == 0) {
            toClear.add((x-1) * 100 + y);
          }
        }
        if (x < counts.length - 1 && buttons[x+1][y].isEnabled()) {//Right
          buttons[x+1][y].setText(counts[x+1][y] + "");
          buttons[x+1][y].setEnabled(false);
          if (counts[x+1][y] == 0) {
            toClear.add((x+1) * 100 + y);
          }
        }
        if (x > 0 && y < counts[0].length - 1 && buttons[x-1][y+1].isEnabled()) {//Down left
          buttons[x-1][y+1].setText(counts[x-1][y+1] + "");
          buttons[x-1][y+1].setEnabled(false);
          if (counts[x-1][y+1] == 0) {
            toClear.add((x-1) * 100 + (y+1));
          }
        }
        if (y < counts[0].length - 1 && buttons[x][y+1].isEnabled()) {//Down
          buttons[x][y+1].setText(counts[x][y+1] + "");
          buttons[x][y+1].setEnabled(false);
          if (counts[x][y+1] == 0) {
            toClear.add(x * 100 + (y+1));
          }
        }
        if (x < counts.length - 1 && y < counts[0].length - 1 && buttons[x+1][y+1].isEnabled()) {//Down right
          buttons[x+1][y+1].setText(counts[x+1][y+1] + "");
          buttons[x+1][y+1].setEnabled(false);
          if (counts[x+1][y+1] == 0) {
            toClear.add((x+1) * 100 + (y+1));
          }
        }
      }
      clearZeros(toClear);
    }
  }

  public void checkWin() {
    boolean won = true;
    for (int x = 0; x < counts.length; x++) {
      for (int y = 0; y < counts[0].length; y++) {
        if (counts[x][y] != MINE && buttons[x][y].isEnabled() == true) {
          won = false;
        }
      }
    }
    if (won == true) {
      JOptionPane.showMessageDialog(frame, "You Win!");
    }
  }

  public void actionPerformed(ActionEvent event) {
    if (event.getSource().equals(reset)) {
      //Reset the board
      for (int x = 0; x < buttons.length; x++) {
        for (int y = 0; y < buttons[0].length; y++) {
          buttons[x][y].setEnabled(true);
          buttons[x][y].setText("");
        }
      }
      createRandomMines();
    }
    else {
      for (int x = 0; x < buttons.length; x++) {
        for (int y = 0; y < buttons[0].length; y++) {
          if (event.getSource().equals(buttons[x][y])) {
            if (counts[x][y] == MINE) {
              buttons[x][y].setForeground(Color.red);
              buttons[x][y].setText("X");
              lostGame();
            }
            else if (counts[x][y] == 0) {
              buttons[x][y].setText(counts[x][y] + "");
              buttons[x][y].setEnabled(false);
              ArrayList<Integer> toClear = new ArrayList<Integer>();
              toClear.add(x*100+y);
              clearZeros(toClear);
              checkWin();
            }
            else {
              buttons[x][y].setText(counts[x][y] + "");
              buttons[x][y].setEnabled(false);
              checkWin();
            }
          }
        }
      }
    }
  }

}
