import java.util.Arrays;

public class Cloth extends Obj{

	//a cloth is always square, and therefore defined by two diagonal points
	//going to assume the cloth is flat at the start- easier for now when creating initial obj
	//2D array of points, contains the position of the point
	//all points have equal mass (global factor for the physics)
	Point[][] clothArray;
	int sideLength;
	int xMax, yMax, xMin, yMin;
	double mass;

	public Cloth(){

	}

	//takes in x1, y1, z1, x2, y2, z2, mass; assumes (x1, y1) is top left and (x2, y2) is bottom right
	public Cloth(double x1, double y1, double z1, double x2, double y2, double z2, double mass){

		sideLength = (int) (x2-x1);
		clothArray = new Point[sideLength + 1][sideLength + 1];
		xMin = (int) x1;
		yMin = (int) y1;
		xMax = (int) x2;
		yMax = (int) y2;
		
		for (int x = 0; x <= sideLength; x++){
			for (int y = 0; y <= sideLength; y++){
				clothArray[x][y] = new Point(xMin+x, yMin+y, 0);
			}
		}

		//establish the four corners
		//create 2d grid of points
		//deal with spring defs

	}

	double[] intersect(double[] origin, double[] d){
		//loop through all the points and apply physics
		//probably do this five times per frame, start with 5 frames a second, probably a 10 second animation

		double[] result = new double[13];
		return result;
	}

	void applyPhysics(){
		Point update[][] = new Point[(int)sideLength + 1][(int) sideLength + 1];
		Vector gravity = new Vector(0, 0, .02);
		Vector movement, spring, force;
		double length, forceScalar, normalLength;
		normalLength = 1; //some constant

		for (int x = 0; x <= this.sideLength; x++){
			for (int y = 0; y <= this.sideLength; y++){

				movement = gravity;

				//horizontal line through point covering 4 neighbors; coveredNeighbors = 4
				for (int k = x - 2; k < x + 2; k++){
					if (k >= 0 && k <= sideLength && y >= 0 && y <= sideLength && k != x){
						spring = this.clothArray[k][y].subVec(this.clothArray[x][y]);
						length = spring.getMag();
						forceScalar = (length - normalLength)/normalLength;
						spring = spring.multScalar(1/length);
						force = spring.multScalar(forceScalar);
						force = force.multScalar(.002); //some other small constant
						movement = movement.add(force);
					}
				}

				//vertical line through point covering 4 neighbors; coveredNeighbors = 8
				for (int k = y - 2; k < y + 2; k++){
					if (k <= sideLength && k >= 0 && x >= 0 && x <= sideLength && k != y){
						spring = this.clothArray[x][k].subVec(this.clothArray[x][y]);
						length = spring.getMag();
						forceScalar = (length - normalLength)/normalLength;
						spring = spring.multScalar(1/length);
						force = spring.multScalar(forceScalar);
						force = force.multScalar(.02); //some other small constant
						movement = movement.add(force);
					}
				}

				//horizontal line at one y value above point covering 2 neighbors; coveredNeighbors = 10
				for (int k = x - 1; k < x + 1; k++){
					if (k <= sideLength && k >= 0 && y - 1 >= 0 && y - 1 <= sideLength && k != x){
						spring = this.clothArray[k][y - 1].subVec(this.clothArray[x][y]);
						length = spring.getMag();
						forceScalar = (length - normalLength)/normalLength;
						spring = spring.multScalar(1/length);
						force = spring.multScalar(forceScalar);
						force = force.multScalar(.02); //some other small constant
						movement = movement.add(force);
					}
				}

				//horizontal line at one y value below point covering 2 neighbors; coveredNeighbors = 12
				for (int k = x - 1; k < x + 1; k++){
					if (k <= sideLength && k >= 0 && y + 1 >= 0 && y + 1 <= sideLength && k  != x){
						spring = this.clothArray[k][y + 1].subVec(this.clothArray[x][y]);
						length = spring.getMag();
						forceScalar = (length - normalLength)/normalLength;
						spring = spring.multScalar(1/length);
						force = spring.multScalar(forceScalar);
						force = force.multScalar(.02); //some other small constant
						movement = movement.add(force);
					}
				}
				update[x][y] = clothArray[x][y].add(movement);
			}
		}

		copyArray(update);
	}
	
	
	void printArray(){
		for (int x = 0; x <= sideLength; x++){
			for (int y = 0; y <= sideLength; y++){
				System.out.println("( " + clothArray[x][y].getX() + ", " + clothArray[x][y].getY() + ", " + 
						clothArray[x][y].getZ() + " )");
			}
		}
	}

	public Point[][] getClothArray() {
		return clothArray;
	}

	public void setClothArray(Point[][] clothArray) {
		this.clothArray = clothArray;
	}

	public int getSideLength() {
		return sideLength;
	}

	public void setSideLength(int sideLength) {
		this.sideLength = sideLength;
	}

	public int getxMax() {
		return xMax;
	}

	public void setxMax(int xMax) {
		this.xMax = xMax;
	}

	public int getyMax() {
		return yMax;
	}

	public void setyMax(int yMax) {
		this.yMax = yMax;
	}

	public int getxMin() {
		return xMin;
	}

	public void setxMin(int xMin) {
		this.xMin = xMin;
	}

	public int getyMin() {
		return yMin;
	}

	public void setyMin(int yMin) {
		this.yMin = yMin;
	}

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}
	
	public Point[][] copyArray(Point[][] update){
		for(int i=0; i<update.length; i++)
			  for(int j=0; j<update[i].length; j++)
			    clothArray[i][j] = update[i][j];
		
		return clothArray;
	}

}
