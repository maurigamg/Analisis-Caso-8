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
  private ArrayList<Integer> percentages;
  private ArrayList<Short[]> representations;
  
  public Target() {
    colors = new ArrayList<Color>();
    percentages = new ArrayList<Integer>();
    representations = new ArrayList<Short[]>();
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
    percentages.add(pPercetange);
  }
  
  public void setPercentage(ArrayList<Integer> pPercentages) {
    percentages=pPercentages;
  }
  
  public ArrayList<Color> getColors() {
    return colors;
  }
  
  public ArrayList<Integer> getPercentages(){
    return percentages;
  }
  
  public ArrayList<Short[]> getRepresentations(){
    return representations;
  }
  
  public int getPercentageInRange(Short[] pRange) {
    for(int position = 0; position < representations.size(); position++) {
      Short[] representation = representations.get(position);
      if(pRange[0] == representation[0] && pRange[1] == representation[1]) {
        return percentages.get(position);
      }
    }
    return 0;
  }
  
  /**
   * This method establishes the chromosomal representation
   */
  public void establishRepresentation() {
    short actual = -32768;
    int position;
    float floatPercentage;
    short range;
    for(position = 0;position<percentages.size()-1;position++) {
      floatPercentage = (float)percentages.get(position)/100;
      range = (short)(65535*floatPercentage+actual);
      Short[] representation = {actual,range};
      representations.add(representation);
      actual=(short) (range+1);
    }
    floatPercentage = (float)percentages.get(position)/100;
    range = (short)(65535*floatPercentage+actual);
    if(range!=32767) {
      range=32767;
    }
    Short[] representation = {actual,range};
    representations.add(representation);
  }
}
