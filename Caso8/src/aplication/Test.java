package aplication;

import logic.Probability;

/**
 * 
 * @author Mauricio Gamboa
 * @author Dylan Torres
 * @version 05/10/2019
 *
 */
public class Test {
  public static void main(String[] args) {
    
    String[] images= {"Images/Cubo.jpg","Images/Simba.jpg","Images/Tucan.jpg"};
    
    Probability probability=new Probability();
    for(String image: images) {
      probability.setFilename(image);
      probability.startProcess();
    }
  }
}
