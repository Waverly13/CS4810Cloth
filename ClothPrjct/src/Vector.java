
public class Vector {
	private double x, y, z;
	private double mag;
	
	public Vector(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
		this.mag = Math.sqrt(x*x + y*y + z*z);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
		this.mag = Math.sqrt(x*x + y*y + z*z);
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
		this.mag = Math.sqrt(x*x + y*y + z*z);
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
		this.mag = Math.sqrt(x*x + y*y + z*z);
	}

	public double getMag() {
		return mag;
	}

	public void setMag(double mag) {
		this.mag = mag;
	}
	
	public double dot(Vector a){
		
		return this.x * a.x + this.y * a.y + this.z * a.z;	
	}
	
	public double dot(Point a){
		
		return this.x * a.getX() + this.y * a.getY() + this.z * a.getZ();	
	}
	
	public Vector cross(Vector a){
		
		double x, y, z;
		x = this.y * a.z - this.z * a.y;
		y = this.z * a.x - this.x * a.z;
		z = this.x * a.y - this.y * a.x;	
		
		Vector result = new Vector(x, y, z);
		return result;	
	}
	
	public Vector normalize(){
		
		double x = this.x / this.mag;
		double y = this.y / this.mag;
		double z = this.z / this.mag;
		
		Vector result = new Vector(x, y, z);
		return result;
	}
	
	public Vector multScalar(double n){
		
		double x = this.x * n;
		double y = this.y * n;
		double z = this.z * n;
		
		Vector result = new Vector(x, y, z);
		return result;	
	}
	
	public Vector add(Vector a){
		
		double x = this.x + a.x;
		double y = this.y + a.y;
		double z = this.z + a.z;
		
		Vector result = new Vector(x, y, z);
		return result;
	}
	
	public Point add(Point a){
		
		double x = this.x + a.getX();
		double y = this.y + a.getY();
		double z = this.z + a.getZ();
		
		Point result = new Point(x, y, z);
		return result;
	}
	
	public Vector sub(Vector a){
		
		double x = this.x - a.x;
		double y = this.y - a.y;
		double z = this.z - a.z;
		
		Vector result = new Vector(x, y, z);
		return result;
	}
	
	public Vector sub(Point a){
		
		double x = this.x - a.getX();
		double y = this.y - a.getY();
		double z = this.z - a.getZ();
		
		Vector result = new Vector(x, y, z);
		return result;
	}
	
}