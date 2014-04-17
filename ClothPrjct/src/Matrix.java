//Megan Bishop
//mgb5db

public class Matrix {
	
	public static double dot(double[] a, double[] b){
		return a[0]*b[0]+a[1]*b[1]+a[2]*b[2];
	}
	
	public static double[] scalar(double x, double[] a){
		double[] result = new double[3];
		result[0] = x*a[0];
		result[1] = x*a[1];
		result[2] = x*a[2];
		return result;
	}
	
	public static double[] add(double[] a, double[] b){
		double[] result = new double[3];
		result[0] = a[0]+b[0];
		result[1] = a[1]+b[1];
		result[2] = a[2]+b[2];
		return result;
	}
	
	public static double[] sub(double[] a, double[] b){
		double[] result = new double[3];
		result[0] = a[0]-b[0];
		result[1] = a[1]-b[1];
		result[2] = a[2]-b[2];
		return result;
	}
	
	public static double[] normalize(double[] a){
		double amag = Math.sqrt(Math.pow(a[0], 2)+Math.pow(a[1], 2)+Math.pow(a[2], 2));
		double[] result = {a[0]/amag, a[1]/amag, a[2]/amag};
		return result;
	}
	
	public static double[] cross(double[] a, double[] b){
		double[] result = new double[3];
		result[0] = a[1]*b[2]-a[2]*b[1];
		result[1] = a[2]*b[0]-a[0]*b[2];
		result[2] = a[0]*b[1]-a[1]*b[0];
		return result;
	}
	
	public static double mag(double[] a){
		double amag = Math.sqrt(Math.pow(a[0], 2)+Math.pow(a[1], 2)+Math.pow(a[2], 2));
		return amag;
	}
	
	public static double[] mult(double[] a, double[] b){
		double[] mult = {a[0]*b[0], a[1]*b[1], a[2]*b[2]};
		return mult;
	}
	
	public static double[] div(double[] a, double[] b){
		double[] t = {a[0]/b[0], a[1]/b[1], a[2]/b[2]};
		return t;
	}
	
}
