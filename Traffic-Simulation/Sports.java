import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Sports extends Vehicle {

  Image myImage;

  public Sports(int newx, int newy) {
    super(newx, newy);
    width = 40;
    height = 20;
    speed = 12;
    try {
      myImage = ImageIO.read(new File("SportsCar.jpg"));
    } catch (Exception ex){
      ex.printStackTrace();
    }
  }

  public void paintMe(Graphics g) {
    g.drawImage(myImage, x, y, null);
  }

}