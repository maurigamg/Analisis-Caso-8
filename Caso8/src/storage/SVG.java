package storage;

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
  public static void SVGgenerator(int color, int value0, int value1) throws IOException {
    String svg = "";
    svg += "<?xml version=\"1.0\" standalone=\"no\"?>\n";
    svg += "<svg width=\"1024\" height=\"1024\" version=\"1.1\"\n";
    svg += "     xmlns=\"http://www.w3.org/2000/svg\">\n";
    svg += "  <desc>Four separate rectangles\n";
    svg += "  </desc>\n";

    int k = 1; // Change to array size
    for(int i = 0; i != k; i++) 
    {
      svg += "<path vector-effect=\"none\" fill=\"#808000\" d=\"M50,80 L80,50 L50,50 L50,80 L80,80\"/>";
      svg += "  <path vector-effect=\"none\" fill=\"" + color +"\" d=\"M" + value0 + "," + value1 + " L" + value1 + "," + value0 + " L" + value0 + "," + value0 + 
    		  " L" + value0 + "," + value1 + " L" + value1 + "," + value1 + "/>\n";
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
