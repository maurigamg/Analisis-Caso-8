<<<<<<< HEAD
package logic;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * 
 * @author Mauricio Gamboa
 * @author Dylan Torres
 * @version 5/10/2019
 *
 */
public class Probability {
  private String fileName;
  private BufferedImage image;
  
  /**
   * Constructor
   */
  public Probability() {
    fileName=null;
    image=null;
  }
  
  public void setFilename(String pFileName) {
    fileName=pFileName;
  }
  
  /**
   * This method reads the filename and calls performAreaDivision if it can read the image
   */
  public void startProcess() {
    try {
      image=ImageIO.read(new File(fileName));
      performAreaDivision(image.getWidth(),0); //Like the image is 1024x1024, we input a 1024, 0 is the start position
    }catch (IOException e) {}
  }
  
  /**
   * This a probabilistic recursive method that divides an area in sixteen little areas (squares)
   * and test each one to know if contains the color percentage necessary (70% non white)
   * @param pSize It corresponds to the width/height of the image, both should have the same size
   * @param pInitialPoint It corresponds to the initial point of an area
   */
  private void performAreaDivision(int pSize,int pInitialPoint) {
    if(pSize>16) {
      int sizeLittleArea=pSize/4;
      for(int initialX=pInitialPoint;initialX<pSize;initialX+=sizeLittleArea) {
        for(int initialY=pInitialPoint;initialY<pSize;initialY+=sizeLittleArea) {
          if(!isAppropriate(initialX, initialY, sizeLittleArea)) {
            performAreaDivision(sizeLittleArea, initialX);
          }
        }
      }
    }
  }
  
  /**
   * This method allows to know if an area has the color percentage necessary
   * @param pInitialX Minimum point x of the area
   * @param pInitialY Minimum point y of the area
   * @param pSize Area size
   * @return True if the 70% of the random points have a different color of white or similar, false in contrary case
   */
  private boolean isAppropriate(int pInitialX,int pInitialY,int pSize) {
    int maxTest=pSize*pSize/2;
    int totalWhites=0;
    for(int currentTest=0;currentTest<maxTest;currentTest++) {
      
      int pointX=(int)(Math.random()*(pSize)+pInitialX);
      int pointY=(int)(Math.random()*(pSize)+pInitialY);
      Color color = new Color(image.getRGB(pointX, pointY));
      
      if(color.getRed()>=240 && color.getGreen()>=240 && color.getBlue()>=240) {
        totalWhites++;
      }
    }
    return maxTest*0.07>=totalWhites;
  }
}
=======
package logic;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * 
 * @author Mauricio Gamboa
 * @author Dylan Torres
 * @version 5/10/2019
 *
 */
public class Probability {
  private String fileName;
  private BufferedImage image;
  
  /**
   * Constructor
   */
  public Probability() {
    fileName=null;
    image=null;
  }
  
  public void setFilename(String pFileName) {
    fileName=pFileName;
  }
  
  /**
   * This method reads the filename and calls performAreaDivision if it can read the image
   */
  public void startProcess() {
    try {
      image=ImageIO.read(new File(fileName));
      performAreaDivision(image.getWidth(),0); //Like the image is 1024x1024, we input a 1024, 0 is the start position
    }catch (IOException e) {}
  }
  
  /**
   * This a probabilistic recursive method that divides an area in sixteen little areas (squares)
   * and test each one to know if contains the color percentage necessary (70% non white)
   * @param pSize It corresponds to the width/height of the image, both should have the same size
   * @param pInitialPoint It corresponds to the initial point of an area
   */
  private void performAreaDivision(int pSize,int pInitialPoint) {
    if(pSize>16) {
      int sizeLittleArea=pSize/4;
      for(int initialX=pInitialPoint;initialX<pSize;initialX+=sizeLittleArea) {
        for(int initialY=pInitialPoint;initialY<pSize;initialY+=sizeLittleArea) {
          if(!isAppropriate(initialX, initialY, sizeLittleArea)) {
            performAreaDivision(sizeLittleArea, initialX);
          }
        }
      }
    }
  }
  
  /**
   * This method allows to know if an area has the color percentage necessary
   * @param pInitialX Minimum point x of the area
   * @param pInitialY Minimum point y of the area
   * @param pSize Area size
   * @return True if the 70% of the random points have a different color of white or similar, false in contrary case
   */
  private boolean isAppropriate(int pInitialX,int pInitialY,int pSize) {
    int maxTest=pSize*pSize/2;
    int totalWhites=0;
    for(int currentTest=0;currentTest<maxTest;currentTest++) {
      
      int pointX=(int)(Math.random()*(pSize)+pInitialX);
      int pointY=(int)(Math.random()*(pSize)+pInitialY);
      Color color = new Color(image.getRGB(pointX, pointY));
      
      if(color.getRed()>=240 && color.getGreen()>=240 && color.getBlue()>=240) {
        totalWhites++;
      }
    }
    return maxTest*0.07>=totalWhites;
  }
}
>>>>>>> 1c7ab2f3cad4cb698ec847cf53c490e2eb79f553
