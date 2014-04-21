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
		buffered = new BufferedImage(100, 100,
				BufferedImage.TYPE_4BYTE_ABGR);
		raster = buffered.getRaster();


		Cloth test = new Cloth(1, 1, 0, 5, 5, 0, 1);
		test.printArray();
		for (int i = 0; i < 10; i++){
			System.out.println("\n Updating physics one step... \n");
			test.applyPhysics();
			
		}
		test.printArray();

		//		double[] black = {0, 0, 0, 255};
		//
		//		for (int i = 0; i <= test.sideLength; i++){
		//			for (int k = 0; k <= test.sideLength; k++){
		//				raster.setPixel((int) test.getClothArray()[i][k].getX(), (int) test.getClothArray()[i][k].getY(), black);
		//			}
		//		}


		//		for (int run = 0; run < 100; run++){
		//			System.out.println("\n Updating physics one step... \n");
		//
		//			test.applyPhysics();
		//			test.printArray();
		//		}

		//		double[] red = {255, 0, 0, 255};
		//		
		//		for (int i = 0; i <= test.sideLength; i++){
		//			for (int k = 0; k <= test.sideLength; k++){
		//				raster.setPixel((int) test.getClothArray()[i][k].getX(), (int) test.getClothArray()[i][k].getY(), red);
		//			}
		//		}


		try {
			ImageIO.write(buffered, "png", new File(imageName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
