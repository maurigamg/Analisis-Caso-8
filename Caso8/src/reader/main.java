package reader;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class main
{

  public main()
  {
    try
    {
      // the line that reads the image file
      BufferedImage image = ImageIO.read(new File("C://Users//Dylan//Desktop//Prog//image1.jpeg"));
      System.out.println("Founded");
      //System.out.println(image.getData());
      //System.out.println(image.getColorModel());
      //System.out.println(image.getGraphics());
      //System.out.println(image.getRGB(500, 500));
      
      int[][] pixels = new int[1024][1024];

      for( int i = 0; i < 1024; i++ ) {
          for( int j = 0; j < 1024; j++ ) {
              pixels[i][j] = image.getRGB( i, j );
      		  System.out.print(pixels[i][j]);
          }
          System.out.println("");
      }	  
    } 
    catch (IOException e)
    {
      // log the exception
      // re-throw if desired
    }
  }

  public static void main(String[] args)
  {
    new main();
  }

}
