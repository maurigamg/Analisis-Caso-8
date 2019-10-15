package logic;

import java.awt.image.BufferedImage;

/**
 * Class that contains the information of a certain part of an image
 * @author Mauricio Gamboa
 * @author Dylan Torres
 * @version 08/10/2019
 *
 */
public class Quadrant {
  private BufferedImage image;
  private float possibility;
  
  /**
   * 
   * @param pImage It corresponds a certain area of an image
   */
  public Quadrant(BufferedImage pImage) {
    image = pImage;
    possibility = 1; //100% possibility of use
  }
  
  public BufferedImage getImage() {
    return image;
  }
  
  public float getPossibility() {
    return possibility;
  }
  
  /**
   * This method decreases the possibility of use of the area
   * @param ppReduction
   */
  public void updatePossibiliy(float pReduction) {
    possibility-=pReduction;
  }
}
