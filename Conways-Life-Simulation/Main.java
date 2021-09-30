/*
This program is an implementation of Conway's Life Simulation.

Author: Nathan Zou
*/
import java.awt.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JButton;

public class Main implements MouseListener, ActionListener, Runnable {
  
  //Variables and objects
  boolean[][] cells = new boolean[25][25];
  JFrame frame = new JFrame("Life Simulation");
  LifePanel panel = new LifePanel(cells);
  Container south = new Container();
  JButton step = new JButton("Step");
  JButton start = new JButton("Start");
  JButton stop = new JButton("Stop");
  boolean running = false;

  //Constructor
  public Main() {
    frame.setSize(600,600);
    frame.setLayout(new BorderLayout());
    frame.add(panel, BorderLayout.CENTER);
    panel.addMouseListener(this);

    //south container
    south.setLayout(new GridLayout(1,3));
    south.add(step);
    step.addActionListener(this);
    south.add(start);
    start.addActionListener(this);
    south.add(stop);
    stop.addActionListener(this);
    frame.add(south, BorderLayout.SOUTH);

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
  
  public static void main(String[] args) {
    new Main();
  }

  //All MouseListener events
  public void mouseClicked(MouseEvent event) {
  }
  
  public void mouseEntered(MouseEvent event) {
  }

  public void mouseExited(MouseEvent event) {
  }

  public void mousePressed(MouseEvent event) {
  }

  public void mouseReleased(MouseEvent event) {
    System.out.println(event.getX() + "," + event.getY());
    double width = (double)panel.getWidth() / cells[0].length;
    double height = (double)panel.getHeight() / cells.length;
    int column = Math.min(cells[0].length - 1,(int)(event.getX() / width));
    int row = Math.min(cells.length - 1,(int)(event.getY() / height));
    System.out.println(column + "," + row);
    cells[row][column] = !cells[row][column];
    frame.repaint();
  }

  public void actionPerformed(ActionEvent event) {
    if (event.getSource().equals(step)) {//step button: goes next step
      step();
    }
    if (event.getSource().equals(start)) {//start button: starts auto stepping
      System.out.println("Start");
      if (running == false) {
        running = true;
        Thread t = new Thread(this);
        t.start();
      }
    }
    if (event.getSource().equals(stop)) {//stop button: stops auto stepping
      System.out.println("Stop");
      running = false;
    }
  }

  public void run() {
    while (running == true) {
      step();
      try {
        Thread.sleep(500);
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  /*
    row-1,column-1    row-1,column    row-1,column+1
    row,column-1      row,column      row,column+1
    row+1,column-1    row+1,column    row+1,column+1
  */
  public void step() {
    boolean[][] nextCells = new boolean[cells.length][cells[0].length];
    
    for (int row = 0; row < cells.length; row++) {//checking for neighbors
      for (int column = 0; column < cells[0].length; column++) {
        int neighborCount = 0;
        if (row > 0 && column > 0 && cells[row - 1][column - 1] == true) {//up left
          neighborCount++;
        }
        if (row > 0 && cells[row - 1][column] == true) {//up
          neighborCount++;
        }
        if (row > 0 && column < cells[0].length - 1 && cells[row - 1][column + 1] == true) {//up right
          neighborCount++;
        }
        if (column > 0 && cells[row][column - 1] == true) {//left 
          neighborCount++;
        }
        if (column < cells[0].length - 1 && cells[row][column + 1] == true) {//right
          neighborCount++;
        }
        if (row < cells.length - 1 && column > 0 && cells[row + 1][column - 1] == true) {//down left
          neighborCount++;
        }
        if (row < cells.length - 1 && cells[row + 1][column] == true) {//down
          neighborCount++;
        }
        if (row < cells.length - 1 && column < cells[0].length - 1 && cells[row + 1][column + 1] == true) {//down right
          neighborCount++;
        }

        //rules of Life
        if (cells[row][column] == true) {//alive
          if (neighborCount == 2 || neighborCount == 3) {
            nextCells[row][column] = true; //alive next time
          }
          else {
            nextCells[row][column] = false; //dead next time
          }
        }
        else { //dead
          if (neighborCount == 3) {
            nextCells[row][column] = true; //alive next time
          }
          else {
            nextCells[row][column] = false; //dead next time
          }
        }
      }
    }
    cells = nextCells;
    panel.setCells(nextCells);
    frame.repaint();
  }

}