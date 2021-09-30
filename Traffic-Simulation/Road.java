import java.awt.Graphics;
import java.awt.Color;

import javax.swing.JPanel;

import java.util.ArrayList;

public class Road extends JPanel {

  final int LANE_HEIGHT = 120;
  final int ROAD_WIDTH = 800;
  ArrayList<Vehicle> cars = new ArrayList<Vehicle>();
  int carCount = 0; 

  public Road() {
    super();
  }

  public void addCar(Vehicle v) {
    cars.add(v);
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, getWidth(), getHeight());
    g.setColor(Color.WHITE);
    for (int a = LANE_HEIGHT; a < LANE_HEIGHT * 4; a = a + LANE_HEIGHT) {//Which lane
      for (int b = 0; b < getWidth(); b = b + 40) {//Which line
        g.fillRect(b, a, 30, 5);
      }
    }
    //Draw cars
    for (int a = 0; a < cars.size(); a++) {
      cars.get(a).paintMe(g);
    }

  }

  public void step() {
    for (int a = 0; a < cars.size(); a++) {
      Vehicle v = cars.get(a);
      if (collision(v.getX()+v.getSpeed(), v.getY(), v) == false) {//There's no collision
        v.setX(v.getX() + v.getSpeed());
        if (v.getX() > ROAD_WIDTH) {
          if (collision(0, v.getY(), v) == false) {
            v.setX(0);
            carCount++;
          }
        }
      }
      else {//Car ahead
        if ((v.getY() > 40) && 
            (collision(v.getX(), v.getY() - LANE_HEIGHT, v) == false)) {
          v.setY(v.getY() - LANE_HEIGHT); //Switch lanes
        }
        else if ((v.getY() < 40 + 3*LANE_HEIGHT) && 
                 (collision(v.getX(), v.getY() + LANE_HEIGHT, v) == false)) {
          v.setY(v.getY() + LANE_HEIGHT); //Switch lanes
        }
      }
    }
  }

  public boolean collision(int x, int y, Vehicle v) {//Collision detection
    for (int a = 0; a < cars.size(); a++) {
      Vehicle u = cars.get(a);
      if (y == u.getY()) {//If im in the same lane
        if (u.equals(v) == false) {//If im not checking myself
          if (x < u.getX() + u.getWidth() && //My left side is left of his right 
              x + v.getWidth() > u.getX()) { //My right side is right of his left side
            return true;
          }
        }
      }
    }
    return false;
  }

  public int getCarCount() {
    return carCount;
  }

  public void resetCarCount() {
    carCount = 0;
  }

}