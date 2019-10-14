package logic;

import java.awt.Color;
import java.util.ArrayList;

/**
 * 
 * @author EXTREME
 * @author EXTREME
 * @version 12/10/2019
 *
 */
public class Target {
  private ArrayList<Color> colors;
  private ArrayList<Integer> percetanges;
  
  public Target() {
    colors = new ArrayList<Color>();
    percetanges = new ArrayList<Integer>();
  }
  
  /**
   * 
   * @param pColor
   */
  public void addColor(Color pColor) {
    colors.add(pColor);
  }
  
  /**
   * 
   * @param pPercetange
   */
  public void addPercetage(int pPercetange) {
    percetanges.add(pPercetange);
  }
  
  public void setPercentage(ArrayList<Integer> pPercentages) {
    percetanges=pPercentages;
  }
  
  public ArrayList<Color> getColors() {
    return colors;
  }
  
  public ArrayList<Integer> getPercentages(){
    return percetanges;
  }
}
