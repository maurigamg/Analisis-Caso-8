package storage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This is the class used to create a svg file
 * @author Mauricio Gamboa
 * @author Dyaln Torres
 * @version 07/10/2019
 *
 */
public class SVG {
  
  public SVG() {}

  /**
   * This method creates a svg file
   * @throws IOException
   */
  public void SVGgenerator(String pCoordenates, int pWidth) throws IOException {

    String svg = "";
    svg += "<?xml version=\"1.0\" standalone=\"no\"?>\n";
    svg += "<svg width=\""+pWidth+"\" height=\"1024\" version=\"1.1\"\n";
    svg += "     xmlns=\"http://www.w3.org/2000/svg\">\n";
    svg += "  <desc>Four separate rectangles\n";
    svg += "  </desc>\n";

    svg += pCoordenates;

    svg += "  <!-- Show outline of viewport using 'rect' element -->\n";
    svg += "</svg>\n";
    BufferedWriter writer = new BufferedWriter(new FileWriter("output.svg"));
    writer.write(svg);

    writer.close();
  }
}
