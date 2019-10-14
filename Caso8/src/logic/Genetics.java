package logic;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * 
 * @author Mauricio
 * @author Dylan Torres
 * @version 12/10/2019
 *
 */
public class Genetics {
  private final String FILENAMES[] = {"Images/Cubo.jpg","Images/Simba.jpg","Images/Tucan.jpg"};
  private String svg = "";
  
  public Genetics() {}
  
  public void startProcess() {
    for(String filename: FILENAMES) {
      Probability probability = new Probability(filename);
      try {
        probability.startProcess();
        implementProbability(probability.getQuadrants());
        return;
      } catch (IOException e) {}
    }
    createSVG();
  }
  
  /**
   * 
   * @param pQuadrants
   */
  private void implementProbability(Quadrant[][] pQuadrants) {
    for(int row = 0; row < 32; row++) {
      for(int column = 0; column < 32; column++) {
        float possibility=(float)(Math.random()*1);
        Quadrant quadrant = pQuadrants[row][column];
        if(quadrant.getPossibility()>possibility) {
          useQuadrant(quadrant.getImage(),row,column);
          Target target = fixTarget(defineTarget(quadrant.getImage()));          
        }
      }
    }
  }
  
  /**
   * 
   * @param pSector
   * @param pRow
   * @param pColumn
   */
  private void useQuadrant(BufferedImage pSector,int pRow,int pColumn) {
    
  }
  
  /**
   * 
   * @param pSector
   */
  private Target defineTarget(BufferedImage pSector) {
    ArrayList<Color> colors = new ArrayList<Color>();
    ArrayList<Integer> totalColor = new ArrayList<Integer>();
    /*
    int width = pSector.getWidth();
    int height = pSector.getHeight();
    int maxPoints = width * height / 2;
    for(int actualPoint = 0; actualPoint < maxPoints;actualPoint++) {
      int pointX = (int) (Math.random() * width);
      int pointY = (int) (Math.random() * height);
      
    }*/
    for(int pointX = 0; pointX < pSector.getWidth(); pointX++) {
      for(int pointY = 0; pointY < pSector.getHeight(); pointY++) {
        if(colors.size()!=0) {
          for(int actualPosition = 0; actualPosition < colors.size(); actualPosition++) {
            Color color = new Color(pSector.getRGB(pointX, pointY));
            Color colorInArray= colors.get(actualPosition);
            int rDifference = Math.abs(colorInArray.getRed()-color.getRed());
            int gDifference = Math.abs(colorInArray.getGreen()-color.getGreen());
            int bDifference = Math.abs(colorInArray.getBlue()-color.getBlue());
            
            if(rDifference <= 40 && gDifference <= 40 && bDifference <= 40) {
              totalColor.set(actualPosition, totalColor.get(actualPosition)+1);
              break;
            }else if(actualPosition == colors.size()-1) {
              colors.add(color);
              totalColor.add(1);
            }
          }
        }else {
          colors.add(new Color(pSector.getRGB(pointX, pointY)));
          totalColor.add(1);
        }
      }
    }
    return defineTargetAuxiliar(colors,totalColor,pSector.getWidth()*pSector.getHeight());
    
  }
  
  /**
   * 
   * @param pColors
   * @param pTotals
   * @param pPixels
   */
  private Target defineTargetAuxiliar(ArrayList<Color> pColors, ArrayList<Integer> pTotals, int pPixels) {
    Target target = new Target();
    for(int actualPosition = 0; actualPosition < pTotals.size(); actualPosition++) {
      int total = pTotals.get(actualPosition);
      int possibility = (100*total)/pPixels;
      if(possibility > 0) {
        target.addColor(pColors.get(actualPosition));
        target.addPercetage(possibility);
      }
    }
    return target;
  }
  
  /**
   * 
   * @param pTarget
   */
  private Target fixTarget(Target pTarget) {
    ArrayList<Integer> percentages = pTarget.getPercentages();
    int suma=0;
    for(Integer percentage: percentages) {
      suma+=percentage;
    }
    int rest = 100 - suma;
    if(rest>0 && percentages.size()!=0) {
      int position = (int)(Math.random()*percentages.size());
      percentages.set(position, percentages.get(position)+rest);
      pTarget.setPercentage(percentages);
    }
    return pTarget;
  }
  
  private void createSVG(){
    
  }
}
