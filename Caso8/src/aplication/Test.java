package aplication;

import logic.Probability;

public class Test {
  public static void main(String[] args) {
    Probability probability = new Probability();
    probability.setFilename("Images/Cubo.jpg");
    probability.startProcess();
  }
}
