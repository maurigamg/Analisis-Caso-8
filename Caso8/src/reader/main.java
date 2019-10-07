package reader;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;

public class main
{
	private static String svg = "";
	private static BufferedImage imgs[] = new BufferedImage[16];

	public static void main()
	{
		try
		{
			// the line that reads the image file
			BufferedImage image = ImageIO.read(new File("Images/Cubo.jpg"));
			System.out.println("Founded");
      
			int[][] pixels = new int[image.getWidth()][image.getHeight()];

			for( int i = 0; i < image.getWidth(); i++ ) 
			{
				for( int j = 0; j < image.getHeight(); j++ ) 
				{
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
  
	public static void imageSplit(String fileName) throws IOException
	{
		File file = new File(fileName); 
		FileInputStream fis = new FileInputStream(file);
		BufferedImage image = ImageIO.read(fis); //reading the image file

		int rows = 4;
		int cols = 4;
		
		int chunkWidth = image.getWidth() / cols; // determines the chunk width and height
		int chunkHeight = image.getHeight() / rows;
		int count = 0;
		for (int x = 0; x < rows; x++) 
		{
			for (int y = 0; y < cols; y++) 
			{
				//Initialize the image array with image chunks
				imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());
				System.out.println(imgs[y].getRGB(2, 2));

				// draws the image chunk
				Graphics2D gr = imgs[count++].createGraphics();
				gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
				gr.dispose();
			}
		}
		System.out.println("Splitting done");
	}	
  
	public static void SVGgenerator() throws IOException 
	{
		svg += "<?xml version=\"1.0\" standalone=\"no\"?>\n";
		svg += "<svg width=\"1024\" height=\"1024\" version=\"1.1\"\n";
		svg += "     xmlns=\"http://www.w3.org/2000/svg\">\n";
		svg += "  <desc>Four separate rectangles\n";
		svg += "  </desc>\n";
		
		svg += "  <path vector-effect=\"none\" fill=\"#808000\" d=\"M50,80 L80,50 L50,50 L50,80\"/>\n";
		int k = 1;
		for(int i = 0; i != k; i++) 
		{
			svg += "<path vector-effect=\"none\" fill=\"#808000\" d=\"M50,80 L80,50 L50,50 L50,80\"/>";
			//svg += "  <path vector-effect=\"none\" fill=\"" + color +"\" d=\"M" + 50,80 + " L" + 80,50 + " L" + 50,50 " L" + 50,80 + "/>\n";
		}
		svg += "  <!-- Show outline of viewport using 'rect' element -->\n";
		svg += "  <rect x=\".10cm\" y=\".10cm\" width=\"1024\" height=\"1024\"\n";
		svg += "        fill=\"none\" stroke=\"blue\" stroke-width=\".10cm\" />\n";
		svg += "</svg>\n";
		BufferedWriter writer = new BufferedWriter(new FileWriter("output.svg"));
	    writer.write(svg);
	     
	    writer.close();
	}

	public static void main(String[] args) throws IOException
	{
		//main();
		//imageSplit("Images/Cubo.jpg");
		//SVGgenerator();
	}

}
