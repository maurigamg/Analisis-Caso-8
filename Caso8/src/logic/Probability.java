package logic;

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
  private Quadrant quadrants[];

  public Probability() {
    filename = null;
    quadrants = new Quadrant[1024];
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
        quadrants[count] = new Quadrant(new BufferedImage(chunkWidth, chunkHeight, image.getType()));

        // draws the image chunk
        Graphics2D gr = quadrants[count++].getImage().createGraphics();
        gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, 
            chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
        gr.dispose();
      }
    }
  }

  /**
   * This method tests each image three times to see if its possibility is going to be reduced
   * @throws IOException 
   */
  private void testAreas() throws IOException {
    int maxTest=(int)(Math.random()*4+2); //total intents among 2 and 5
    for(int position = 0; position != 1024; position++) {
      for(int actualTest=1;actualTest<maxTest;actualTest++) {
        if(!isAppropriate(quadrants[position].getImage(),maxTest)) {
          quadrants[position].updatePossibiliy((float)1/maxTest); //It is reduced the possibility
        }
      }
    }
  }

  /**
   * This probabilistic method determines if into a certain area (small part from an original image) has
   * a 70% of color using random points to test it.
   * @param pImage Small image/area of other image
   * @param pTestArea It corresponds to the area that is going to be tested
   * @return True if the area taken has a 70% of color, false in contrary case
   */
  private boolean isAppropriate(BufferedImage pImage,int pTestArea) {
    int width = pImage.getWidth();
    int height = pImage.getHeight();
    int maxTest = width*height/pTestArea; // Random points used
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
