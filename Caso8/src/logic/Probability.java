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
  private Quadrant quadrants[][];

  /**
   * 
   * @param pFilename
   */
  public Probability(String pFilename) {
    filename = pFilename;
    quadrants = new Quadrant[32][32];
  }
  
  public Quadrant[][] getQuadrants() {
    return quadrants;
  }
  
  public void startProcess() throws IOException{
    splitImage();
    testAreas();
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
        quadrants[x][y] = new Quadrant(new BufferedImage(chunkWidth, chunkHeight, image.getType()));

        // draws the image chunk
        Graphics2D gr = quadrants[x][y].getImage().createGraphics();
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
    for(int row = 0; row < 32; row++) {
      for(int column = 0; column < 32; column++) {
        for(int actualTest=1;actualTest<maxTest;actualTest++) {
          if(!isAppropriate(quadrants[row][column].getImage(),maxTest)) {
            quadrants[row][column].updatePossibiliy((float)1/maxTest); //It is reduced the possibility
          }
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
