package logic;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import storage.SVG;

/**
 * 
 * @author Mauricio
 * @author Dylan Torres
 * @version 12/10/2019
 *
 */
public class Genetics {
  private final String FILENAMES[] = {"Images/Tucan.jpg","Images/Cubo.jpg","Images/Simba.jpg"};
  private String svg;
  private int picture;

  public Genetics() {
    svg = "";
    picture = 0;
  }
  
  /**
   * It starts the algorithm
   */
  public void startProcess() {
    for(String filename: FILENAMES) {
      Probability probability = new Probability(filename);
      try {
        probability.startProcess();
        implementProbability(probability.getQuadrants());
        picture++;
      } catch (IOException e) {}
    }
    createSVG();
  }

  /**
   * This method works the quadrants with a certain probability of colors
   * @param pQuadrants
   */
  private void implementProbability(Quadrant[][] pQuadrants) {
    float possibility=(float)(Math.random()*1);
    for(int row = 0; row < 32; row++) {
      for(int column = 0; column < 32; column++) {
        Quadrant quadrant = pQuadrants[row][column];
        if(quadrant.getPossibility()>possibility) {
          Target target = fixTarget(defineTarget(quadrant.getImage()));
          target.establishRepresentation();
          ArrayList<Polygon> finalPopulation= generatePopulation(target,row,column);
          savePopulation(finalPopulation, target);
        }
      }
    }
  }
  
  /**
   * This method identifies the range where a chromosome is located
   * @param representations All representations of a target
   * @param chromosome Chromosome to identify his representation
   * @return The representation (range) of the chromosome
   */
  private Short[] representationRange(ArrayList<Short[]> representations, Short chromosome) {
    for(Short[] representation: representations) {
      if(chromosome >= representation[0] && chromosome <= representation[1]) {
        return representation;
      }
    }
    return null;
  }
  
  /**
   * This method calculates the percentage of individuals in a certain range into a population
   * @param population
   * @param range Range to find
   * @return The percentage of individuals of the range
   */
  private int averageIndividual(ArrayList<Polygon> population,Short[] range) {
    int totals = 0;
    for(Polygon individual: population) {
      short chromosome = individual.getChromosome();
      if(chromosome >= range[0] && chromosome <= range[1]) {
        totals += 1;
      }
    }
    return (int)(100*((float)totals/population.size()));
  }
  
  /**
   * This method generates new individuals of a population
   * @param population
   * @param pRow
   * @param pColumn
   * @return The new population
   */
  private ArrayList<Polygon> startReproduction(ArrayList<Polygon> population,int pRow, int pColumn){
    int totalChildren = population.size()/2;
    if(totalChildren == 0) {
      totalChildren = 1;
    }
    for(int children = 0; children<totalChildren; children++) {
      Polygon father1 = population.get((int)(Math.random()*population.size()));
      Polygon father2 = population.get((int)(Math.random()*population.size()));
      short chromosomeFather1 = father1.getChromosome();
      short chromosomeFather2 = father2.getChromosome();

      chromosomeFather1 =(short)(chromosomeFather1>>8);
      chromosomeFather1 =(short)(chromosomeFather1<<8);

      chromosomeFather2 =(short)(chromosomeFather2<<8);
      chromosomeFather2 =(short)(chromosomeFather2>>8);

      short newChromosome = (short) (chromosomeFather1 | chromosomeFather2);

      int[] firstPoint= {(int)(Math.random()*Polygon.POLYGON_SIZE)+pRow*32,(int)(Math.random()*Polygon.POLYGON_SIZE)+pColumn*32+1024*picture};
      int[] secondPoint= {(int)(Math.random()*Polygon.POLYGON_SIZE)+pRow*32,(int)(Math.random()*Polygon.POLYGON_SIZE)+pColumn*32+1024*picture};
      int[] thirdPoint= {(int)(Math.random()*Polygon.POLYGON_SIZE)+pRow*32,(int)(Math.random()*Polygon.POLYGON_SIZE)+pColumn*32+1024*picture};

      int mutation = (int)(Math.random()*100+1);
      if(father1==father2 || mutation <= 5) {
        short bitPosition = (short) (1 << (int)(Math.random()*16+1));

        if((newChromosome & bitPosition) == 0){
          newChromosome |=bitPosition;
        }else {
          newChromosome &= ~bitPosition;
        }

      }

      population.add(new Polygon(newChromosome,firstPoint,secondPoint,thirdPoint));
    }

    return population;
  }
  
  /**
   * This method generates a population until reaching the target
   * @param pTarget Target of the quadrant
   * @param pRow
   * @param pColumn
   * @return The ideal population
   */
  private ArrayList<Polygon> generatePopulation(Target pTarget,int pRow,int pColumn){
    ArrayList<Polygon> finalPopulation = establishPopulation(pTarget, pRow, pColumn);;
    ArrayList<Short[]> representations = pTarget.getRepresentations();
    while(!isObjetiveComplete(finalPopulation,pTarget)) {
      ArrayList<Polygon> newPopulation = establishPopulation(pTarget,pRow,pColumn);;
      for(Polygon individual: finalPopulation) {
        Short[] range = representationRange(representations,individual.getChromosome());
        if(averageIndividual(finalPopulation, range) < pTarget.getPercentageInRange(range)) {
          newPopulation.add(individual);
        }
      }

      finalPopulation = startReproduction(newPopulation,pRow,pColumn);
    }
    return finalPopulation;
  }
  
  /**
   * This method verifies if the population reached the target
   * @param pPopulation Population to test
   * @param pTarget Target to verify the population
   * @return true if the population reached the target, false in contrary case
   */
  private boolean isObjetiveComplete(ArrayList<Polygon> pPopulation,Target pTarget) {
    for(int position = 0; position < pTarget.getPercentages().size(); position++) {
      int totals = 0;
      Short[] range = pTarget.getRepresentations().get(position);
      for(Polygon individual: pPopulation) {
        short chromosome = individual.getChromosome();
        if(chromosome >= range[0] && chromosome <= range[1] ) {
          totals++;
        }
      }
      int percentage = (int)(100*((float)totals/pPopulation.size()));
      if(Math.abs(percentage-pTarget.getPercentages().get(position)) > 4) {
        return false;
      }
    }

    return true;
  }

  /**
   * 
   * @param This method defines the target of a quadrant
   * @return The defined target
   */
  private Target defineTarget(BufferedImage pSector) {
    ArrayList<Color> colors = new ArrayList<Color>();
    ArrayList<Integer> totalColor = new ArrayList<Integer>();
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
   * This method defines the target with the values obtained of a quadrant
   * @param pColors Colors found in the quadrant
   * @param pTotals Total of each color in the image
   * @param pPixels Total of pixels
   * @return
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
   * This method fixes the percentages of a target
   * @param pTarget
   * @return The target fixed
   */
  private Target fixTarget(Target pTarget) {
    ArrayList<Integer> percentages = pTarget.getPercentages();
    int sum=0;
    for(Integer percentage: percentages) {
      sum+=percentage;
    }
    int rest = 100 - sum;
    if(rest>0 && percentages.size()!=0) {
      int position = (int)(Math.random()*percentages.size());
      percentages.set(position, percentages.get(position)+rest);
      pTarget.setPercentage(percentages);
    }
    return pTarget;
  }

  /**
   * This method creates a foreign population
   * @param pTarget
   * @param pRow The row where the quadrant is located
   * @param pColumn The column where the quadrant is located
   * @return A foreign population
   */
  private ArrayList<Polygon> establishPopulation(Target pTarget,int pRow,int pColumn){
    ArrayList<Polygon> population = new ArrayList<Polygon>();
    ArrayList<Short[]> representations = pTarget.getRepresentations();
    for(Short[] representation: representations) {
      int[] firstPoint = {(int)(Math.random()*Polygon.POLYGON_SIZE)+pRow*32,(int)(Math.random()*32)+pColumn*Polygon.POLYGON_SIZE+1024*picture};
      int[] secondPoint = {(int)(Math.random()*Polygon.POLYGON_SIZE)+pRow*32,(int)(Math.random()*32)+pColumn*Polygon.POLYGON_SIZE+1024*picture};
      int[] thirdPoint = {(int)(Math.random()*Polygon.POLYGON_SIZE)+pRow*32,(int)(Math.random()*32)+pColumn*Polygon.POLYGON_SIZE+1024*picture};
      short chromosome = (short)(Math.random()*(representation[1]-representation[0]+1)+representation[0]);
      population.add(new Polygon(chromosome,firstPoint,secondPoint,thirdPoint));
    }
    return population;
  }
  
  /**
   * This method save the polygons that accomplished the target
   * @param pPopulation Population that reaches the target
   * @param pTarget The target of a quadrant
   */
  private void savePopulation(ArrayList<Polygon> pPopulation, Target pTarget) {
    ArrayList<Color> colors = pTarget.getColors();
    ArrayList<Short[]> representations = pTarget.getRepresentations();

    for(Polygon individual: pPopulation) {
      String color = "";
      int[] firstPoint = individual.getFirtsPoint();
      int[] secondPoint = individual.getSecondPoint();
      int[] thirdPoint = individual.getThirdPoint();
      int chromosome = individual.getChromosome();

      for(int index = 0; index < representations.size(); index++) {
        Short[] range = representations.get(index);
        if(chromosome >= range[0] && chromosome <= range[1]) {
          color = Integer.toHexString(colors.get(index).getRed()) + Integer.toHexString(colors.get(index).getGreen()) + Integer.toHexString(colors.get(index).getBlue());
          break;  
        }
      }
      if(color.equals("") == false) {
        svg += "  <path vector-effect=\"none\" fill=\"#" + color +"\" d=\"M" + firstPoint[1] + "," + firstPoint[0] + " L" + secondPoint[1] +
            "," + secondPoint[0] + " L" + thirdPoint[1] + "," + thirdPoint[0] + " L" + firstPoint[1] + "," + firstPoint[0] + "\"/>\n";
      }
    }
  }
  
  /**
   * This method sends the information necessary to create the svg file
   */
  private void createSVG(){
    SVG SVGimage = new SVG();
    try {
      SVGimage.SVGgenerator(svg,1024*picture);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
