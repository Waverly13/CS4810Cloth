import java.util.Arrays;


public class Plane extends Obj{
	
	String triangle;
	double A;
	static double B;
	double C;
	static double D;
	double r;
	double g;
	double b;
	
	public Plane(){
		
	}
	
	public Plane(double a1, double b1, double c1, double d1, 
			double R1, double G1, double B1){
		//triangle = plane;
		A = a1;
		B = b1;
		C = c1;
		D = d1;
		r = R1;
		g = G1;
		b = B1;
	}
	
	double[] intersect(double[] origin, double[] d){
		//System.out.println("drawing plane");
		//trace from eye to plane
		//Ax+By+Cz+D=0
		
		//plane normal
		double[] n = {A, B, C};
		double[] nNorm = Matrix.normalize(n);
		
		double[] P = new double[3];
		double x;
		if(D<0){
			//System.out.println(Arrays.toString(n));
			//denominator of t equation
			double denom = Matrix.dot(n, d);
			//System.out.println(denom);
			if(denom <= 0)
				return null;
			//find x to solve intersection
			x = (-D-Matrix.dot(origin, n))/denom;
			if(x < 0)
				return null;
			P = Matrix.add(origin, Matrix.scalar(x, d));
		}
		else{
			//System.out.println(Arrays.toString(n));
			//denominator of t equation
			double denom = -Matrix.dot(n, d);
			//System.out.println(denom);
			if(denom <= 0)
				return null;
			//find x to solve intersection
			x = (Matrix.dot(origin, n)+D)/denom;
			if(x < 0)
				return null;
			P = Matrix.add(origin, Matrix.scalar(x, d));
		}
		
		double dot = Matrix.dot(d, nNorm);
		if(dot>0){
			nNorm = Matrix.scalar(-1,  nNorm);
		}
		
		double[] result = new double[13];
		result[0] = P[0];
		result[1] = P[1];
		result[2] = P[2];
		result[3] = r;
		result[4] = g;
		result[5] = b;
		result[6] = nNorm[0];
		result[7] = nNorm[1];
		result[8] = nNorm[2];
		result[11] = System.identityHashCode(this);
		result[12] = x;
		
		return result;
	}

	public static double getB() {
		return B;
	}

	public static double getD() {
		return D;
	}
	
}
