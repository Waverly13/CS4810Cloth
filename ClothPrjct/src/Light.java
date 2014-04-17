import java.text.DecimalFormat;
import java.util.Arrays;


public class Light {
	
	String Type;
	double X = 0;
	double Y = 0;
	double Z = 0;
	double R = 0;
	double G = 0;
	double B = 0;

	public Light(String type, double x, double y, double z, double r, double g, double b){
		Type = type;
		X = x;
		Y = y;
		Z = z;
		R = r;
		G = g;
		B = b;
	}
	public String getCommand(){
		return Type;
	}
	public double getX(){
		return X;
	}
	public double getY(){
		return Y;
	}
	public double getZ(){
		return Z;
	}
	public double getR(){
		return R;
	}
	public double getG(){
		return G;
	}
	public double getB(){
		return B;
	}
	
	
}
