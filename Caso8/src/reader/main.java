package reader;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class main {
	public static void check() {
		try {
			BufferedImage image = ImageIO.read(new File("/reader/Images/Cubo.jpg"));
			
			int[][] pixels = new int[1024][1024];
			for( int i = 0; i < 1024; i++ ) {
			    for( int j = 0; j < 1024; j++ ) {
			        pixels[i][j] = image.getRGB( i, j );
			        Color mycolor = new Color(image.getRGB(i, j));
			        //System.out.print(mycolor);
			        //System.out.print(mycolor.getBlue());
			        //System.out.print(mycolor.getGreen());
			        //System.out.print(mycolor.getRed());
			        
			    }
			    System.out.println("");
			}
			
		}
		catch(IOException e) {
			
		}
	}
	public static void main(String[] args ) {
		check();
	}
	
}
