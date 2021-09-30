/*
Author: Nathan Zou
Code from J. Galbraith Traffic Simulation videos 
Images used are found from Jeffrey Teh
Date: 1/1/2021
*/

//Main will act as Traffic from the videos 

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main implements ActionListener, Runnable {

  JFrame frame = new JFrame("Traffic Simulation");
  Road road = new Road();
  //South container
  JButton start = new JButton("Start");
  JButton stop = new JButton("Stop");
  JLabel throughput = new JLabel("Throughput: 0");
  Container south = new Container();
  //West container
  JButton semi = new JButton("Add Semi");
  JButton suv = new JButton("Add SUV");
  JButton sports = new JButton("Add Sports");
  Container west = new Container();
  boolean running = false;
  int carCount = 0;
  long startTime = 0;

  public Main() {
    //Setting up the frame
    frame.setSize(1000, 550);
    frame.setLayout(new BorderLayout());
    frame.add(road, BorderLayout.CENTER);

    south.setLayout(new GridLayout(1,3));
    south.add(start);
    start.addActionListener(this);
    south.add(stop);
    stop.addActionListener(this);
    south.add(throughput);
    frame.add(south, BorderLayout.SOUTH);

    west.setLayout(new GridLayout(3,1));
    west.add(semi);
    semi.addActionListener(this);
    west.add(suv);
    suv.addActionListener(this);
    west.add(sports);
    sports.addActionListener(this);
    frame.add(west, BorderLayout.WEST);

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);

    frame.repaint();
  }

  public static void main(String[] args) {
    new Main();
  }

  public void actionPerformed(ActionEvent event) {
    //Start Button
    if (event.getSource().equals(start)) {
      if (running == false) {
        running = true;
        road.resetCarCount();
        startTime = System.currentTimeMillis();
        Thread t = new Thread(this);
        t.start();
      }
    }
    //Stop Button
    if (event.getSource().equals(stop)) {
      running = false;
    }
    //Semi adding button
    if (event.getSource().equals(semi)) {//Adding Semi to simulation
      Semi semi = new Semi(0, 30);
      road.addCar(semi);
      for (int x = 0; x < road.ROAD_WIDTH; x = x + 20) {
        for (int y = 40; y < 480; y = y + 120) {
          semi.setX(x);
          semi.setY(y);
          if (road.collision(x, y, semi) == false) {
            frame.repaint();
            return;
          }
        }
      }
    }
    //SUV adding button
    if (event.getSource().equals(suv)) {//Adding SUV to simulation
      SUV suv = new SUV(0, 30);
      road.addCar(suv);
      for (int x = 0; x < road.ROAD_WIDTH; x = x + 20) {
        for (int y = 40; y < 480; y = y + 120) {
          suv.setX(x);
          suv.setY(y);
          if (road.collision(x, y, suv) == false) {
            frame.repaint();
            return;
          }
        }
      }
    }
    //Sports adding button
    if (event.getSource().equals(sports)) {//Adding sports to simulation
      Sports sports = new Sports(0, 30);
      road.addCar(sports);
      for (int x = 0; x < road.ROAD_WIDTH; x = x + 20) {
        for (int y = 40; y < 480; y = y + 120) {
          sports.setX(x);
          sports.setY(y);
          if (road.collision(x, y, sports) == false) {
            frame.repaint();
            return;
          }
        }
      }
    }
  }

  public void run() {
    while (running == true) {
      road.step();
      carCount = road.getCarCount();
      double throughputCalc = carCount / (1000 * (double)(System.currentTimeMillis() - startTime));
      throughput.setText("Throughput:" + throughputCalc);
      frame.repaint();
      try {
        Thread.sleep(100);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

}

//Traffic Sim Vid 7, 0:57