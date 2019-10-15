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
	  public void SVGgenerator(String coordenates) throws IOException {
	    
		String svg = "";
	    svg += "<?xml version=\"1.0\" standalone=\"no\"?>\n";
	    svg += "<svg width=\"3072\" height=\"1024\" version=\"1.1\"\n";
	    svg += "     xmlns=\"http://www.w3.org/2000/svg\">\n";
	    svg += "  <desc>Four separate rectangles\n";
	    svg += "  </desc>\n";

	    svg += coordenates;

	    svg += "  <!-- Show outline of viewport using 'rect' element -->\n";
	    svg += "</svg>\n";
	    BufferedWriter writer = new BufferedWriter(new FileWriter("output.svg"));
	    writer.write(svg);

	    writer.close();
	  }
	}
