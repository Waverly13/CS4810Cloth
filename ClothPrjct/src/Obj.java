//Megan Bishop
//mgb5db

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Obj {
	
	static ArrayList<double[]> model = new ArrayList<>();
	
	public static int[] colorConvert(double r, double g, double b){
		if(r < 0)
			r = 0;
		if(g < 0)
			g = 0;
		if(b < 0)
			b = 0;
		
		if(r > 1)
			r = 1;
		if(g > 1)
			g = 1;
		if(b > 1)
			b = 1;
		
		int ra = (int)Math.round(r*255);
		int ga = (int)Math.round(g*255);
		int ba = (int)Math.round(b*255);
		int[] result = {ra, ga, ba};
		return result;
	}
	
	abstract double[] intersect(double[] origin, double[] d);
	
	public static void intersections(ArrayList<Obj> objects){	
		model.clear();
		
		for(int j=0; j<Start.width; j++){
			for(int k=0; k<Start.height; k++){
				//convert raster coordinate to 3d space
				double s = ((double)(2*j-Start.width))/Math.max(Start.width, Start.height);
				double t = ((double)(Start.height-2*k))/Math.max(Start.width, Start.height);
				//System.out.println(j+" "+k+" "+s+" "+t);
				//find distance vector
				double[] d = Matrix.add(Start.forward, Matrix.add(Matrix.scalar(s, Start.right), 
						Matrix.scalar(t, Start.up)));
				d = Matrix.normalize(d);
				//for each shape in 'objects', call its intersection
				//intersections return the location of the intersection, the normal, and the color
				//maintain the current closest point
				double dist = Double.POSITIVE_INFINITY;
				double[] interF = new double[13];
				for(int i=0; i<Start.objects.size(); i++){
					//System.out.println(i);
					Obj thing = Start.objects.get(i);
					double[] inter = thing.intersect(Start.eye, d);
					//System.out.println(Arrays.toString(inter));
					if(inter == null)
						continue;
					else{
						double newdist = inter[12];
						//System.out.println(dist+" "+newdist);
						if(newdist<dist){
							dist = newdist;
							inter[9] = j;
							inter[10] = k;
							interF = inter;
						}
					}	
				}
				model.add(interF);
			}
		}
		//done going through objects, now have list of arrays containing the intersection
		//points, their rgb values, and the normals
		
		//now going to bounce light rays off all these points
		System.out.println("going to light");
		if(Start.lights.isEmpty()){
			while(!model.isEmpty()){
				double[] point = model.get(0);
				model.remove(0);
				int j = (int)point[9];
				int k = (int)point[10];
				//int[] fiCol = colorConvert(point[3], point[4], point[5]);
				//DrawPixel.xyrgb(j, k, fiCol[0], fiCol[1], fiCol[2]);
				DrawPixel.xyrgb(j, k, 0, 0, 0);
			}
		}
		else{
			//for each item in model
			//pass to each light source in 'lights'
			for(int i=0; i<model.size(); i++){
				double[] point = model.get(i);
				double[] color = {0, 0, 0};
				double[] newcolor = new double[3];
				for(int j=0; j<Start.lights.size(); j++){
					Light now = Start.lights.get(j);
					//System.out.println(now.getCommand()+" "+now.getX()+" "+now.getY()+" "+now.getZ());
					
					String type = now.getCommand();
					if(type.equals("sun")){
						newcolor = Sun(point, now);
						//System.out.println(Arrays.toString(newcolor));
						if(newcolor != null){
							color = Matrix.add(color, newcolor);
						}
						//System.out.println("final color"+Arrays.toString(color));
					}
					else{
						newcolor = Bulb(point, now);
						if(newcolor != null){
							color = Matrix.add(color, newcolor);
						}
					}
				}
				//have rotated through all lights on this points
				int j = (int)point[9];
				int k = (int)point[10];
				
				int[] fiCol = colorConvert(color[0], color[1], color[2]);
				DrawPixel.xyrgb(j, k, fiCol[0], fiCol[1], fiCol[2]);
				
			}
		}
		
	}
	
	//------------------------------------------------------------------------------------
	
	public static double[] Sun(double[] point, Light sun){
		
		//normalize direction of light and get point norm and point coordinates
		double[] d = {sun.getX(), sun.getY(), sun.getZ()};
		
		//System.out.println("sun"+Arrays.toString(d));
		
		d = Matrix.normalize(d);
		double[] pnorm = {point[6], point[7], point[8]};
		
	
		
		double dot = Matrix.dot(pnorm, d);
		if(dot<0)
			return null;
		
		for(int q=0; q<Start.objects.size(); q++){
			Obj compare = Start.objects.get(q);
			
			if(System.identityHashCode(compare) == point[11]){
				//System.out.println("hash codes match");
				continue;
			}
			else{
				double[] inter = compare.intersect(point, d);
				//System.out.println("hash codes do not match");
				if(inter != null){
					return null;
				}
			}
		}
		//System.out.println("lighting");
		//didn't hit anything, light
		//System.out.println("light "+point[9]+" "+point[10]);
		double[] pcolor = {point[3], point[4], point[5]};
		double[] lcolor = {sun.getR(), sun.getG(), sun.getB()};
	
		//System.out.println(dot);
		//if(dot<0)
		double[] mult = Matrix.mult(pcolor, lcolor);
		double[] color = Matrix.scalar(dot, mult);
		
		//System.out.println(Arrays.toString(color));
		//double[] red = {1, 0, 0};
		return color;
		
	}
	
	//----------------------------------------------------------------------------------------
	
	public static double[] Bulb(double[] point, Light bulb){
		//normalize direction of light and get point norm and point coordinates
		double[] d = {bulb.getX()-point[0], bulb.getY()-point[1], bulb.getZ()-point[2]};
		//System.out.println(X+" "+Y+" "+Z);
		//System.out.println("bulb "+bulb.getX()+" "+bulb.getY()+" "+bulb.getZ());
		//System.out.println(" ");
		d = Matrix.normalize(d);
		double[] pnorm = {point[6], point[7], point[8]};
		
		
		double dot = Matrix.dot(pnorm, d);
		if(dot<0)
			return null;

		//point coord
		//double[] p = {point[0], point[1], point[2]};
		double px = point[0];
		double py = point[1];
		double pz = point[2];
		
		
		for(int q=0; q<Start.objects.size(); q++){
			Obj compare = Start.objects.get(q);
			
			if(System.identityHashCode(compare) == point[11]){
				//System.out.println("hash codes match");
				continue;
			}
			else{
				double[] inter = compare.intersect(point, d);
				//System.out.println("hash codes do not match");
				if((inter != null)){
					//distance from point to light
					double pld = Math.sqrt(Math.pow(px-bulb.getX(), 2)+Math.pow(py-bulb.getY(), 2)+
							Math.pow(pz-bulb.getZ(), 2));
					//distance from point to intersection
					double pid = Math.sqrt(Math.pow(px-inter[0], 2)+Math.pow(py-inter[1], 2)+
							Math.pow(pz-inter[2], 2));
					//if light is closer than interection, still light it
					if(pld < pid)
						continue;
					else
						return null;
				}
			}
		}
		//System.out.println("lighting");
		//didn't hit anything, light
		//System.out.println("light "+point[9]+" "+point[10]);
		double[] pcolor = {point[3], point[4], point[5]};
		double[] lcolor = {bulb.getR(), bulb.getG(), bulb.getB()};
	
		//System.out.println(dot);
		//if(dot<0)
		double[] mult = Matrix.mult(pcolor, lcolor);
		double[] color = Matrix.scalar(dot, mult);
		
		//System.out.println(Arrays.toString(color));
		//double[] red = {1, 0, 0};
		return color;
	}
	
	

}
