package storage;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * @author Mauricio Gamboa
 * @author Dyaln Torres
 * @version 07/10/2019
 *
 */
public class SVG {
  
  /**
   * This method creates a svg file
   * @throws IOException
   */
  public void SVGgenerator(BufferedImage images[]) throws IOException {
    String svg = "";
    int value = 32;
    int contador = 0;
    svg += "<?xml version=\"1.0\" standalone=\"no\"?>\n";
    svg += "<svg width=\"1024\" height=\"1024\" version=\"1.1\"\n";
    svg += "     xmlns=\"http://www.w3.org/2000/svg\">\n";
    svg += "  <desc>Four separate rectangles\n";
    svg += "  </desc>\n";

    //int k = 1; // Change to array size
    for(int i = 0; i != 32; i++) 
    {
    	for(int j = 0; j != 32; j++) 
        {
    	   //svg += "<path vector-effect=\"none\" fill=\"#808000\" d=\"M50,80 L80,50 L50,50 L50,80 L80,80\"/>";
    	   if(images[contador] != null) {
    		   //System.out.println(images[()*()]);
    		   svg += "  <path vector-effect=\"none\" fill=\"#" + 808000 +"\" d=\"M" + value*j + "," + value*i + " L" + value*(j+1) + "," + value*i + " L" + value*(j+1) + "," + value*(i+1) + 
        		   " L" + value*j + "," + value*(i+1) + " L" + value*j + "," + value*i + "\"/>\n";
    	   }
    	   contador++;
        }
    }
    svg += "  <!-- Show outline of viewport using 'rect' element -->\n";
    svg += "  <rect x=\".10cm\" y=\".10cm\" width=\"1024\" height=\"1024\"\n";
    svg += "        fill=\"none\" stroke=\"blue\" stroke-width=\".10cm\" />\n";
    svg += "</svg>\n";
    BufferedWriter writer = new BufferedWriter(new FileWriter("output.svg"));
    writer.write(svg);

    writer.close();
  }
}
