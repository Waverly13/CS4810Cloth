//Megan Bishop
//mgb5db

//this version is to simply take in a bunch of input files, draw all the images to 
//a new folder, and then condense that folder into an animation (turn group of .jpg's
//into a .gif)

//it also has the bare bones of a cloth class...it doesn't contain anything, just 
//some notes on how it might work



import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;


public class Start {
	
	//BufferedImage buff;
	static WritableRaster rast;
	ArrayList<String> result = new ArrayList<String>();
		
	String filename;
	static int width;
	static int height;
	static ArrayList<Light> lights = new ArrayList<Light>();
	static ArrayList<Obj> objects = new ArrayList<Obj>();
	
	static double[] eye = {0, 0, 0};
	static double[] forward = {0, 0, -1};
	static double[] right = {1, 0, 0};
	static double[] up = {0, 1, 0};
	
	static double time = 10;
	static double t = 0;
	
	
	public static void main(String[] args){
		Start hw = new Start();
		//creating a folder to put all of the created images in
		File dir = new File("cloth_output");
		dir.mkdir();
		
		//loop through all input files
		for(int i=0; i<args.length; i++){
			String filename = args[i];
			System.out.println(filename);
			boolean end = hw.parse(filename);
			if(end == false)
				System.exit(0);
			
			BufferedImage buff = hw.png();
			
			hw.command();
			while(t <= time){
				Obj.intersections(objects);
				hw.drawImage(buff);
				for(int j=0; j<5; j++){
					t += 0.5;
					((Cloth) objects.get(2)).applyPhysics();
				}
			}
		}
		
	}
	
	
	//---------------------------------------------------------------------------------------------
	//reads the file, splits it into individual lines, then checks to make sure it is a valid command
	//and then splits it into individual pieces and puts them in the 'results' array it then checks
	//for valid pixel dimensions for the image
	public boolean parse(String filename){
		Scanner scanr = null;
		try {
			File data = new File(filename);
			scanr = new Scanner(data);
		} 
		catch (FileNotFoundException e) {
			System.out.println("Sorry, that file can not be found.");
			return false;
		}
		
		String[] split;
		result.clear();
		result = new ArrayList<String>();
		String line;
		while (scanr.hasNext()) {
			line = scanr.nextLine();
			if (line.length() != 0) {
				
				split = line.split("\\s+");
				String start = split[0];
				//System.out.println(start);
				if (!start.equals("png")
						&&!start.equals("eye")
						&&!start.equals("forward")
						&&!start.equals("up")
						&&!start.equals("sun")
						&&!start.equals("bulb")
						&&!start.equals("sphere")
						&&!start.equals("plane")
						&&!start.equals("cloth")
					)
				{
					//System.out.println("delete line");
					for(int j=0; j<split.length; j++){
						String fix = split[j].trim();
					}
				}
				else{
					//System.out.println("add line");
					for(int j=0; j<split.length; j++){
						String fix = split[j].trim();
						result.add(fix);
					}
				}				
			}
		}
		
		String png = result.get(0);
		int w = Integer.parseInt(result.get(1));
		int h = Integer.parseInt(result.get(2));
		if(!png.equals("png") || w<1 || h<1){
			scanr.close();
			return false;
		}
		scanr.close();
		return true;
	}
	
	//--------------------------------------------------------------------------------------------------
	//takes the array 'result' of all the pieces from the files and determines the commands and
	//the appropriate action, taking the request data, creating the objects, and storing them in
	//their appropriate arrays
	public void command(){
		
		lights.clear();
		objects.clear();
		while(!result.isEmpty()){
			String instruction = result.get(0);
			System.out.println(instruction);
			
			if(instruction.equals("eye")){
				double x = Double.parseDouble(result.get(1));
				double y = Double.parseDouble(result.get(2));
				double z = Double.parseDouble(result.get(3));
				result.subList(0, 4).clear();
				eye[0]=x;
				eye[1]=y;
				eye[2]=z;
			}
			
			else if(instruction.equals("forward")){
				double x = Double.parseDouble(result.get(1));
				double y = Double.parseDouble(result.get(2));
				double z = Double.parseDouble(result.get(3));
				result.subList(0, 4).clear();
				forward[0]=x;
				forward[1]=y;
				forward[2]=z;
				right = Matrix.normalize(Matrix.cross(forward, up));
				up = Matrix.normalize(Matrix.cross(right, forward));
			}
			
			else if(instruction.equals("up")){
				double x = Double.parseDouble(result.get(1));
				double y = Double.parseDouble(result.get(2));
				double z = Double.parseDouble(result.get(3));
				result.subList(0, 4).clear();
				double[] newup = {x, y, z};
				right = Matrix.normalize(Matrix.cross(forward, newup));
				up = Matrix.normalize(Matrix.cross(right, forward));
			}
			
			else if(instruction.equals("sun")){
				double x = Double.parseDouble(result.get(1));
				double y = Double.parseDouble(result.get(2));
				double z = Double.parseDouble(result.get(3));
				double r = Double.parseDouble(result.get(4));
				double g = Double.parseDouble(result.get(5));
				double b = Double.parseDouble(result.get(6));
				result.subList(0, 7).clear();
				Light sun = new Light("sun", x, y, z, r, g, b);
				lights.add(sun);
			}
			
			else if(instruction.equals("bulb")){
				double x = Double.parseDouble(result.get(1));
				double y = Double.parseDouble(result.get(2));
				double z = Double.parseDouble(result.get(3));
				double r = Double.parseDouble(result.get(4));
				double g = Double.parseDouble(result.get(5));
				double b = Double.parseDouble(result.get(6));
				result.subList(0, 7).clear();
				Light bulb = new Light("bulb", x, y, z, r, g, b);
				lights.add(bulb);
			}
			
			else if(instruction.equals("sphere")){
				double x = Double.parseDouble(result.get(1));
				double y = Double.parseDouble(result.get(2));
				double z = Double.parseDouble(result.get(3));
				double radius = Double.parseDouble(result.get(4));
				double r = Double.parseDouble(result.get(5));
				double g = Double.parseDouble(result.get(6));
				double b = Double.parseDouble(result.get(7));
				result.subList(0, 8).clear();
				Sphere sphere1 = new Sphere("sphere", x, y, z, radius, r, g, b);
				objects.add(sphere1);
			}
			
			else if(instruction.equals("plane")){
				double A = Double.parseDouble(result.get(1));
				double B = Double.parseDouble(result.get(2));
				double C = Double.parseDouble(result.get(3));
				double D = Double.parseDouble(result.get(4));
				double r = Double.parseDouble(result.get(5));
				double g = Double.parseDouble(result.get(6));
				double b = Double.parseDouble(result.get(7));
				result.subList(0, 8).clear();
				Plane plane1 = new Plane(A, B, C, D, r, g, b);
				objects.add(plane1);
			}
			
			else if(instruction.equals("cloth")){
				double x1 = Double.parseDouble(result.get(1));
				double y1 = Double.parseDouble(result.get(2));
				double z1 = Double.parseDouble(result.get(3));
				double x2 = Double.parseDouble(result.get(4));
				double y2 = Double.parseDouble(result.get(5));
				double z2 = Double.parseDouble(result.get(6));
				double m = Double.parseDouble(result.get(7));
				result.subList(0, 8).clear();
				Cloth cloth = new Cloth(x1, y1, z1, x2, y2, z2, m);
				objects.add(cloth);
			}
			
			else 
				result.remove(0);
		}
		
	}
	
	//----------------------------------------------------------------------------------
	//creates the image buffer and the raster
	public BufferedImage png(){
		
		width = Integer.parseInt(result.get(1));
		height = Integer.parseInt(result.get(2));
		filename = result.get(3);
		result.subList(0, 4).clear();
		
		//System.out.println("png");
		BufferedImage buff = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		
		rast = buff.getRaster();
		int[] fill= {255, 255, 255, 255};
		
		for(int i = 0; i<width; i++){
			for(int j = 0; j<height; j++){
				rast.setPixel(i, j, fill);
			}
		}
		return buff;
		//command();
	}
	
	//------------------------------------------------------------------------------------
	//takes the buffer and converts it to the final png
	public void drawImage(BufferedImage buff){
		
		String dirName = this.getClass().getClassLoader().getResource("").getPath();
		String[] split;
		split = dirName.split("/");
		dirName = "";

		for(int i = 1; i<split.length-1; i++){
			dirName = dirName+split[i]+"/";
		}
		dirName = dirName+"cloth_output";
		System.out.println(dirName);
		
		File dirFinal = new File(dirName, filename+t+".png");
		
		try {
			ImageIO.write(buff, "png", dirFinal);
		} 
		catch (IOException e) {
			System.out.println("The file could not be created.");
		}
		System.out.println("Done");
	}
	
	//convert the folder of jpegs into a gif

	
}