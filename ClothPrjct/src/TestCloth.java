import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class TestCloth {

	/**
	 * @param args
	 */
	public static BufferedImage buffered;
	public static WritableRaster raster;

	public static void main(String[] args) {

		String imageName = "TestCloth.png";
		buffered = new BufferedImage(300, 300,
				BufferedImage.TYPE_4BYTE_ABGR);
		raster = buffered.getRaster();


		Cloth test = new Cloth(1, 1, 0, 20, 20, 0, .95);
		test.triangulate();
		test.printArray();
		
		double[] black = new double[4];
		for (int b = 0; b < 3; b++){
			black[b] = 0;
		}
		black[3] = 255;

		for (int i = 0; i <= test.sideLength; i++){
			for (int k = 0; k <= test.sideLength; k++){
				raster.setPixel((int) test.getClothArray()[i][k].getX(), (int) test.getClothArray()[i][k].getY(), black);
			}
		}


		for (int run = 0; run < 15; run++){
			//System.out.println("\n Updating physics one step... \n");

			test.applyPhysics();
		}
		
		test.printArray();

		double[] red = new double[4];
		for (int b = 0; b < 3; b++){
			red[b] = 0;
		}
		red[0] = 255;
		red[3] = 255;

		for (int i = 0; i <= test.sideLength; i++){
			for (int k = 0; k <= test.sideLength; k++){
				raster.setPixel((int) Math.round(test.getClothArray()[i][k].getX()), (int) Math.round(test.getClothArray()[i][k].getY()), red);
			}
		}


		try {
			ImageIO.write(buffered, "png", new File(imageName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
