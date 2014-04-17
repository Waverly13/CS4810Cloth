import java.util.Arrays;


public class Sphere extends Obj{

	String circle;
	double cx;
	double cy;
	double cz;
	double radius;
	double r;
	double g;
	double b;
	
	public Sphere(){
		
	}
	
	public Sphere(String sphere, double Cx, double Cy, double Cz, double Radius, 
			double R, double G, double B){
		circle = sphere;
		cx = Cx;
		cy = Cy;
		cz = Cz;
		radius = Radius;
		r = R;
		g = G;
		b = B;
	}
	
	public String getType(){
		return circle;
	}


	
	double[] intersect(double[] origin, double[] d){
		//System.out.println("drawing sphere");
		//trace from eye to sphere
		double[] O = {origin[0], origin[1], origin[2]};
		double[] m = {O[0]-cx, O[1]-cy, O[2]-cz};
		
		double a = Math.pow(m[0], 2)+Math.pow(m[1], 2)+Math.pow(m[2], 2);
		double h = Math.pow(d[0], 2)+Math.pow(d[1], 2)+Math.pow(d[2], 2);
		double c = m[0]*d[0]+m[1]*d[1]+m[2]*d[2];
		
		double desc = Math.pow(2*c, 2)-4*h*(a-Math.pow(radius, 2));
		if(desc<0)
			return null;
		
		double x1 = (-2*c-Math.sqrt(desc))/(2*h);
		double x2 = (-2*c+Math.sqrt(desc))/(2*h);
		
		double x = x1;
		if(x1<0){
			if(x2<0){
				return null;
			}
			else{
				x = x2;
			}
		}		
		
		double[] P = {O[0]+x*d[0], O[1]+x*d[1], O[2]+x*d[2]}; 
		
		double[] norm = {P[0]-cx, P[1]-cy, P[2]-cz};
		
		double dot = Matrix.dot(d, norm);
		if(dot>0){
			norm = Matrix.scalar(-1,  norm);
		}
		
		//System.out.println(Arrays.toString(norm));
		norm = Matrix.normalize(norm);
		//add to model
		double[] result = new double[13];
		//result[0] = current[0];
		//result[1] = current[1];
		//result[2] = current[2];
		result[0] = P[0];
		result[1] = P[1];
		result[2] = P[2];
		result[3] = r;
		result[4] = g;
		result[5] = b; 
		result[6] = norm[0];
		result[7] = norm[1];
		result[8] = norm[2];
		result[11] = System.identityHashCode(this);
		result[12] = x;
		
		return result;
	}
	
	
}
