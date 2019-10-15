package logic;

/**
 * 
 * @author Mauricio Gamboa
 * @author Dylan Torres
 * @version 12/10/2019
 *
 */
public class Polygon {
  public static final int POLYGON_SIZE = 32;
  private short chromosome;
  private int[] firstPoint;
  private int[] secondPoint;
  private int[] thirdPoint;
  
  public Polygon(short pChromosome,int[] pFirstPoint,int[] pSecondPoint,int[] pThirdPoint) {
    chromosome = pChromosome;
    firstPoint = pFirstPoint;
    secondPoint = pSecondPoint;
    thirdPoint = pThirdPoint;
  }
  
  public short getChromosome() {
    return chromosome;
  }
  
  public int[] getFirtsPoint() {
    return firstPoint;
  }
  
  public int[] getSecondPoint() {
    return secondPoint;
  }
  
  public int[] getThirdPoint() {
    return thirdPoint;
  }
}
