import java.util.Arrays;
import java.util.ArrayList;

public class Cloth extends Obj{

	//a cloth is always square, and therefore defined by two diagonal points
	//going to assume the cloth is flat at the start- easier for now when creating initial obj
	//2D array of points, contains the position of the point
	//all points have equal mass (global factor for the physics)
	static Point[][] clothArray;
	static int sideLength;
	int xMax, yMax, xMin, yMin;
	double wind;
	double diff;

	static ArrayList<Triangle> triangles = new ArrayList<Triangle>();
	
	public Cloth(double x1, double y1, double z1, double x2, double y2, double z2, double wind){
		this.wind = wind;
		double s1 = (x1*Math.max(Start.width, Start.height)+Start.width)/2;
		double s2 = (x2*Math.max(Start.width, Start.height)+Start.width)/2;
//		double t1 = (y1*Math.max(Start.width, Start.height)-Start.height)/(-2);
//		double t2 = (y2*Math.max(Start.width, Start.height)-Start.height)/(-2);
		//System.out.println(s1+" "+s2);
//		System.out.println(t1+" "+t2);
		sideLength = (int) Math.abs(s2-s1);
		sideLength = 6;
		clothArray = new Point[sideLength+1][sideLength+1];
		
		diff = Math.abs(x1-x2)/sideLength;
		System.out.println(diff);
		
		double x = x1;
		double z = z1;
		for(int i=0; i<=sideLength; i++){
			for(int j=0; j<=sideLength; j++){
				//clothArray[i][j] = new Point(x, y, -1);
				clothArray[i][j] = new Point (x,y1, z);
				z += diff;
			}
			x += diff;
			z = z1;
		}
	}

	public void triangulate(){
		
		for(int i=0; i < sideLength; i++){
			for(int j=0; j<sideLength; j++){
				//get points for triangles
				Point A = clothArray[i][j];
				Point B = clothArray[i+1][j];
				Point C = clothArray[i][j+1];
				Point D = clothArray[i+1][j+1];
				
				if(i==0&&j==0){
					double[] p0 = {A.getX(), A.getY(), A.getZ()};
					double[] p1 = {B.getX(), B.getY(), B.getZ()};
					double[] p2 = {C.getX(), C.getY(), C.getZ()};
					double[] p3 = {D.getX(), D.getY(), D.getZ()};
					
//					System.out.println(Arrays.toString(p0));
//					System.out.println(Arrays.toString(p1));
//					System.out.println(Arrays.toString(p2));
//					System.out.println(Arrays.toString(p3));
//					System.out.println(" ");
				}
				
				//System.out.println(A.getX()+" "+A.getY()+" "+A.getZ());
				//System.out.println("making tri");
				
				//convert to triangles
				Triangle tri1 = new Triangle(A, B, C, 0, 1, 0);
				Triangle tri2 = new Triangle(C, B, D, 0, 1, 0);
				triangles.add(tri1);
				triangles.add(tri2);
				
			}
		}
		
		
	}
	
	double[] intersect(double[] origin, double[] d){
		//System.out.println("side Length"+sideLength);

		if(triangles.size() == 0)
			triangulate();
		double dist = Double.POSITIVE_INFINITY;
		double[] interF = null;
		for(int i=0; i<triangles.size(); i++){
			Triangle now = triangles.get(i);
			double[] inter = now.intersect(Start.eye, d);
			if(inter == null)
				continue;
			else{
				double newdist = inter[12];
				if(newdist<dist){
					dist = newdist;
					interF = inter;
				}
			}
		}
		if(interF != null){
			interF[11] = System.identityHashCode(this);
			//System.out.println(Arrays.toString(interF));
		}
		return interF;
	}
	
	void applyPhysics(){
        Point update[][] = new Point[(int)sideLength + 1][(int) sideLength + 1];
        Vector gravity = new Vector(0, 0.002, 0);
        Vector movement, spring, force;
        double length, forceScalar, normalLength;
        normalLength = 1;

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
                        force = force.multScalar(.0002); //some other small constant
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
                        force = force.multScalar(.0002); //some other small constant
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
                        force = force.multScalar(.0002); //some other small constant
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
                        force = force.multScalar(.0002); //some other small constant
                        movement = movement.add(force);
                    }
                }
                
                update[x][y] = clothArray[x][y];
                if (wind <= 1){
                update[x][y].setxVelocity(update[x][y].getxVelocity()*(1-wind) + movement.getX());
                update[x][y].setyVelocity(update[x][y].getyVelocity()*(1-wind) + movement.getY());
                update[x][y].setzVelocity(update[x][y].getzVelocity()*(1-wind) + movement.getZ());
                update[x][y].setX(clothArray[x][y].getX() + update[x][y].getxVelocity());
                update[x][y].setY(clothArray[x][y].getY() + update[x][y].getyVelocity());
                update[x][y].setZ(clothArray[x][y].getZ() + update[x][y].getzVelocity());
                }
                else{
                	update[x][y].setxVelocity(update[x][y].getxVelocity()*wind + movement.getX());
                    update[x][y].setyVelocity(update[x][y].getyVelocity()*wind + movement.getY());
                    update[x][y].setzVelocity(update[x][y].getzVelocity()*wind + movement.getZ());
                    update[x][y].setX(clothArray[x][y].getX() - update[x][y].getxVelocity());
                    update[x][y].setY(clothArray[x][y].getY() - update[x][y].getyVelocity());
                    update[x][y].setZ(clothArray[x][y].getZ() - update[x][y].getzVelocity());
                }
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

	public double getWind() {
		return wind;
	}

	public void setWind(double wind) {
		this.wind = wind;
	}
	
	public Point[][] copyArray(Point[][] update){
		for(int i=0; i<update.length; i++)
			  for(int j=0; j<update[i].length; j++)
			    clothArray[i][j] = update[i][j];
		
		return clothArray;
	}

}
