import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SUV extends Vehicle {

  Image myImage;

  public SUV(int newx, int newy) {
    super(newx, newy);
    width = 60;
    height = 30;
    speed = 8;
    try {
      myImage = ImageIO.read(new File("SUV.jpg"));
    } catch (Exception ex){
      ex.printStackTrace();
    }
  }

  public void paintMe(Graphics g) {
    g.drawImage(myImage, x, y, null);
  }

}