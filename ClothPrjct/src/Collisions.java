
public class Collisions {

	public static void collide_sphere(){
		for(int i=0; i<=Cloth.sideLength; i++){
			for(int j=0; j<=Cloth.sideLength; j++){
				Point[][] cloth = Cloth.clothArray;
				Point point = cloth[i][j];
				double x = point.getX();
				double y = point.getY();
				double z = point.getZ();
				double radius = Sphere.getRadius();
				double cx = Sphere.getCx();
				double cy = Sphere.getCy();
				double cz = Sphere.getCz();
				double dist = Math.sqrt(((x-cx)*(x-cx))+((y-cy)*(y-cy))+((z-cz)*(z-cz)));
				if (dist < radius){
					Vector force = new Vector(x-cx,y-cy,z-cz);
					force = (force.multScalar(1/dist)).multScalar(radius);
					point.setX(cx+force.getX());
					point.setY(cy+force.getY());
					point.setZ(cz+force.getZ());
					
					point.setxVelocity(0);
					point.setyVelocity(0);
					point.setzVelocity(0);
					
					Cloth.clothArray[i][j] = point;
					System.out.println("SPHERE INTERSECTION");
				}
			}
		}
		//find distance to center
		//adjust force vector
		//change point
		
	}
	
	public static boolean collide_plane(){
		boolean collided = false;
		for(int i=0; i<=Cloth.sideLength; i++){
			for(int j=0; j<=Cloth.sideLength; j++){
				Point[][] cloth = Cloth.clothArray;
				Point point = cloth[i][j];
				double y = point.getY();
				/*if (j == Cloth.sideLength -1)
					System.out.println(y);*/
				double b = Plane.getB();
				double d = Plane.getD();
				if(y > -b/d){
					//System.out.println("below plane "+y+", "+(-b/d));
					point.setY((-b/d)-.05);
					
					point.setxVelocity(0);
					point.setyVelocity(0);
					point.setzVelocity(0);
					Cloth.clothArray[i][j] = point;
					double clothx = Cloth.clothArray[i][j].getX();
					double clothy = Cloth.clothArray[i][j].getY();
					double clothz = Cloth.clothArray[i][j].getZ();
					//System.out.println("new point: (" + clothx + ", " + clothy + ", " + clothz + ")");
					collided = true;
				}
			}
		}
		
		return collided;
	}
	
}
