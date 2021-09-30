import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Semi extends Vehicle {

  Image myImage;

  public Semi(int newx, int newy) {
    super(newx, newy);
    width = 100;
    height = 40;
    speed = 5;
    try {
      myImage = ImageIO.read(new File("SemiTruck.png"));
    } catch (Exception ex){
      ex.printStackTrace();
    }
  }

  public void paintMe(Graphics g) {
    g.drawImage(myImage, x, y, null);
  }

}