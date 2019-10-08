package logic;

import storage.SVG;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * @author Mauricio Gamboa
 * @author Dylan Torres
 * @version 07/10/2019
 */
public class Probability {
  private String filename;
  private BufferedImage images[];
  private BufferedImage imagesSvg[];

  public Probability() {
    filename = null;
    images = new BufferedImage[1024];
    imagesSvg = new BufferedImage[1024];
  }

  public void setFilename(String pFilename) {
    filename = pFilename;
  }

  public void startProcess(){
    try {
      splitImage();
      testAreas();
    } catch (IOException e){}
  }
  
  /**
   * This method divides an image into eight smaller images
   * @throws IOException
   */
  private void splitImage() throws IOException {
    File file = new File(filename); 
    FileInputStream fis = new FileInputStream(file);
    BufferedImage image = ImageIO.read(fis); //reading the image file

    int rows = 32;
    int cols = 32;

    int chunkWidth = image.getWidth() / cols; // determines the chunk width and height
    int chunkHeight = image.getHeight() / rows;
    int count = 0;
    for (int x = 0; x < rows; x++) 
    {
      for (int y = 0; y < cols; y++) 
      {
        //Initialize the image array with image chunks
        images[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());

        // draws the image chunk
        Graphics2D gr = images[count++].createGraphics();
        gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, 
            chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
        gr.dispose();
      }
    }
  }
  
  /**
   * This method tests if each image is going to be used
 * @throws IOException 
   */
  private void testAreas() throws IOException {
    for(int i = 0; i != 1024; i++) {
      boolean y=isAppropriate(images[i]);
      if(y == true) {
         imagesSvg[i] = images[i];
         System.out.println(imagesSvg[i].getRGB(10, 10));
      }
    }
    SVG svg = new SVG();
    svg.SVGgenerator(imagesSvg);
  }
  
  /**
   * This probabilistic method determines if into a certain area (small part from an original image) has
   * a 70% of color using random points to test it.
   * @param pImage Small image/area of other image
   * @return True if the area has the 70% of color, false in contrary case
   */
  private boolean isAppropriate(BufferedImage pImage) {
    int width = pImage.getWidth();
    int height = pImage.getHeight();
    int maxTest = width*height/2; // Test is performed on 50% of the area's pixels
    int totalWhites = 0;
    for(int actualTest = 0; actualTest<maxTest; actualTest++) {
      int pointX = (int)(Math.random()*width);
      int pointY = (int)(Math.random()*height);
      Color color = new Color(pImage.getRGB(pointX, pointY));
      if(color.getRed()>=240 && color.getGreen()>=240 && color.getBlue()>=240) {
        totalWhites++;
      }
    }
    return maxTest*0.07>=totalWhites;
  }
}
