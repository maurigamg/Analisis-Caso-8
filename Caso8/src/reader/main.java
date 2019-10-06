package reader;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;

public class main
{

  public static void main()
  {
    try
    {
      // the line that reads the image file
      BufferedImage image = ImageIO.read(new File("Direccion de la imagen"));
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
  
  public static void imageSplit() throws IOException{
	  File file = new File("Direccion de la imagen"); // I have bear.jpg in my working directory
      FileInputStream fis = new FileInputStream(file);
      BufferedImage image = ImageIO.read(fis); //reading the image file

      int rows = 4; //You should decide the values for rows and cols variables
      int cols = 4;
      int chunks = rows * cols;

      int chunkWidth = image.getWidth() / cols; // determines the chunk width and height
      int chunkHeight = image.getHeight() / rows;
      int count = 0;
      BufferedImage imgs[] = new BufferedImage[chunks]; //Image array to hold image chunks
      for (int x = 0; x < rows; x++) {
          for (int y = 0; y < cols; y++) {
              //Initialize the image array with image chunks
              imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());

              // draws the image chunk
              Graphics2D gr = imgs[count++].createGraphics();
              gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
              gr.dispose();
          }
      }
      System.out.println("Splitting done");

      //writing mini images into image files
      for (int i = 0; i < imgs.length; i++) {
          ImageIO.write(imgs[i], "jpg", new File("img" + i + ".jpg"));
      }
      System.out.println("Mini images created");
  }

  public static void main(String[] args) throws IOException
  {
    main();
    imageSplit();
  }

}
