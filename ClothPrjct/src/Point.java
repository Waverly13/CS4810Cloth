
public class Point {

	private double x, y, z;
	private double[] color = new double[4];
	private Vector surfaceNormal;
	private double xVelocity;
	private double yVelocity;
	private double zVelocity;
	private Vector force;

	
	public Point(double x, double y, double z){
		
		this.x = x;
		this.y = y;
		this.z = z;
		color[0] = 0;
		color[1] = 0;
		color[2] = 0;
		color[3] = 255;
		
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}
	
	public double[] getColor() {
		return color;
	}
	
	public double getRed(){
		return color[0];
	}
	
	public double getGreen(){
		return color[1];
	}
	
	public double getBlue(){
		return color[2];
	}

	public void setColor(double[] color) {
		this.color = color;
	}

	public Vector getSurfaceNormal() {
		return surfaceNormal;
	}

	public void setSurfaceNormal(Vector surfaceNormal) {
		this.surfaceNormal = surfaceNormal;
	}

	public Point sub(Point a){
		double x, y, z;
		x = this.x - a.x;
		y = this.y - a.y;
		z = this.z - a.z;
		
		Point result = new Point(x, y, z);
		return result;
	}
	
	public Vector subVec(Point a){
		double x, y, z;
		x = this.x - a.x;
		y = this.y - a.y;
		z = this.z - a.z;
		
		Vector result = new Vector(x, y, z);
		return result;
	}
	
	public Point mult(Point a){
		
		double x, y, z;
		x = this.x * a.x;
		y = this.y * a.y;
		z = this.z * a.z;
		
		Point result = new Point(x, y, z);
		return result;
	}
	
	public double dot(Point a){	
		return this.x * a.x + this.y * a.y + this.z * a.z;	
	}
	
	public double dot(Vector a){
		
		return this.x * a.getX() + this.y * a.getY() + this.z * a.getZ();	
	}
	
	public Point add(Vector a){

		double x, y, z;
		x = this.x + a.getX();
		y = this.y + a.getY();
		z = this.z + a.getZ();
		
		Point result = new Point(x, y, z);
		return result;
	}
	
	public void printPoint(){
		System.out.println("("+this.x+", "+this.y+", "+this.z+")");
	}
	
	public double getxVelocity() {
		return xVelocity;
	}

	public void setxVelocity(double xVelocity) {
		this.xVelocity = xVelocity;
	}

	public double getyVelocity() {
		return yVelocity;
	}

	public void setyVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}
	
	public double getzVelocity() {
		return zVelocity;
	}

	public void setzVelocity(double zVelocity) {
		this.zVelocity = zVelocity;
	}

	public Vector getForce() {
		return force;
	}

	public void setForce(Vector force) {
		this.force = force;
	}
	

	
}

