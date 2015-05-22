import java.util.Arrays;


public class Triangle extends Obj{
	
	Point A;
	Point B;
	Point C;
	double r;
	double g;
	double b;
	
	public Triangle(Point a1, Point b1, Point c1, double r2, double g2, double b2){
		A = a1;
		B = b1;
		C = c1;
		r = r2;
		g = g2;
		b = b2;
	}

	double[] intersect(double[] origin, double[] d){
		double[] p0 = {A.getX(), A.getY(), A.getZ()};
		double[] p1 = {B.getX(), B.getY(), B.getZ()};
		double[] p2 = {C.getX(), C.getY(), C.getZ()};
		
//		System.out.println(Arrays.toString(p0));
//		System.out.println(Arrays.toString(p1));
//		System.out.println(Arrays.toString(p2));
//		System.out.println(" ");

//		System.out.println(Arrays.toString(Matrix.sub(p0, p1)));
//		System.out.println(Arrays.toString(Matrix.sub(p1, p2)));
//		System.out.println(Arrays.toString(Matrix.cross(Matrix.sub(p0, p1), Matrix.sub(p1, p2))));
		
		double[] n = Matrix.cross(Matrix.sub(p0, p1), Matrix.sub(p1, p2));
		n = Matrix.normalize(n);
		double D = -Matrix.dot(p0, n);
		
		double t = -(Matrix.dot(Start.eye, n) + D);
		double denom = Matrix.dot(d,  n);
		if(denom==0)
			return null;
		t = t/denom;
		
		double[] Q = Matrix.add(Start.eye, Matrix.scalar(t, d));
		
		double[] e0 = Matrix.sub(p2, p1);
		double[] e1 = Matrix.sub(p0, p2);
		double[] e2 = Matrix.sub(p1, p0);
		
		double[] a0 = Matrix.cross(e0, n);
		double[] a1 = Matrix.cross(e1, n);
		double[] a2 = Matrix.cross(e2, n);
		
		double w0 = Matrix.dot(a0, Matrix.sub(Q, p2));
		double w1 = Matrix.dot(a1, Matrix.sub(Q, p0));
		double w2 = Matrix.dot(a2, Matrix.sub(Q, p1));
		
		if(w0>0||w1>0||w2>0)	
			return null;
	
		double dot = Matrix.dot(d, n);
		if(dot>0){
			n = Matrix.scalar(-1,  n);
		}
		
		double[] inter = new double[13];
		inter[0] = Q[0];
		inter[1] = Q[1];
		inter[2] = Q[2];
		inter[3] = r;
		inter[4] = g;
		inter[5] = b;
		inter[6] = n[0];
		inter[7] = n[1];
		inter[8] = n[2];
		//inter[11] = System.identityHashCode(this);
		inter[12] = t;
		
		//System.out.println(Arrays.toString(inter));
		return inter;
		
	}
	
	public void printTri(){
		A.printPoint();
		B.printPoint();
		C.printPoint();
		System.out.println(" ");
	}
	
}
