//Megan Bishop
//mgb5db

import java.awt.image.WritableRaster;

public class DrawPixel {
	
	public static void xyrgb(int x, int y, int r, int g, int b){
		int[] color = {r, g, b, 255};
		Start.rast.setPixel(x, y, color);
	}

}
