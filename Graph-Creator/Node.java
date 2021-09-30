public class Node {

  int x;
  int y;
  String label;
  boolean highlighted;

  public Node(int newx, int newy, String newLabel) {
    x = newx;
    y = newy;
    label = newLabel;
    highlighted = false;
  }

  //Getters and setters for Node class
  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }
  
  public boolean getHighlighted() {
    return highlighted;
  }

  public void setHighlighted(boolean highlighted) {
    this.highlighted = highlighted;
  }
  
}