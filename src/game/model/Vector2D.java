// Probably created 1/25/15.

package game.model; 

import java.lang.Math; 

/* 

	TODO: Deal with rounding errors (seems like it always floors)
		  when converting from doubles to ints.

		This vector exists in y-negative up euclidean space so as to line up with the game window. 
		45 degrees is still in the upper-right quadrant, but the y-component will be negative instead of positive. 

	IMPORTANT: x and y are independent variables. They will not update automatically if you change mag and dir.
	mag and dir are dependent on x and y, so they will in fact change. 
	HOWEVER, if you want to force x and y to match dir, call updateVectorComponents(). 

	Example: 
	setMag(1); 
	setDir(315); 
	updateVectorComponents(); // x and y will now correspond with new mag and dir

	I realize this isn't ideal, and if I have time, I'll look for a better solution.  
*/

public class Vector2D { 
	
	// Should these be private? If they're updated by a superclass, dir and mag won't be updated automatically. But dir and mag are forced to be updated whenever you 'get' them, so in practice the issue isn't existent.
	// But it makes me uncomfortable having them public (but it's also more convenient).
	private double x; 
	private double y; 

	private double dir; // overall direction
	private double mag; // overall magnitude 
	// mag and dir never reference each other, only x and y - both entirely dependent on x and y


	// no-args constructor
	public Vector2D() {
		setXY(0,0); 
	}

	// double constructor
	public Vector2D(double x, double y) { 
		setXY(x,y); 
	}

	// int constructor
	public Vector2D(int x, int y) { 
		setXY((double) x, (double) y); 
	}

	// I would put in a mag-dir constructor, but that would conflict with the double X, double Y constructor

	// yields direction between 0 and 360 degrees, based off of velocity vector components.
	public double getDir() {
		// all y-values are flipped, so as to line up with game world (y-negative up euclidean space)
		if (x != 0) {
			this.dir = Math.atan(-y/x) * 180.0/Math.PI;
			//System.out.println("dir output: "+this.dir);
		}
		else if (y >= 0) { // y-positive = up; am I flipping euclidean space the wrong way? 
			this.dir = 270.0; 
			//System.out.println("dir output: y greater than or equal to 0");
		}
		else { 
			this.dir = 90.0; 
			//System.out.println("dir output: y less than 0");
		}

		// account for arctan domain error
		if (x < 0) { // also means if cosine is negative, need to do a half rotation
			dir += 180; 
		}

		// arctan yields between -90 and 90 degrees. If measure is negative, just add another rotation to bring it within 0 to 360.
		if (dir < 0) {
			dir += 360; 
		}

		return dir; // both updates direction and outputs it to the user - called in update() method every step.
	}

	public void setDir(double dir) { // direction measured in degrees, convert to radians for cosine operations
		this.dir = dir; 

		getX(); 
		getY(); 

		// force x and y components to match with new direction, but maintaining old speed
		//updateVecComponents(); 
	}

	public void setDir(int dir) { // direction measured in degrees, convert to radians for cosine operations
		this.dir = (double) dir; 

		getX(); 
		getY(); 

		//getMag();
		// force x and y components to match with new direction, but maintaining old speed
		//updateVecComponents(); 
	}

	public void setMag(double mag) {
		this.mag = mag; 

		getX(); 
		getY(); 

		// need to derive x and y components from new magnitude 
		// same direction, different speed
		//getDir();
		//updateVecComponents();
	}

	public void setMag(int mag) {
		this.mag = (double) mag; 

		getX(); 
		getY(); 

		// need to derive x and y components from new magnitude 
		// same direction, different speed
		//updateVecComponents();
	}

	public double getMag() {
		this.mag = Math.sqrt(x*x + y*y);

		// what's faster?
		// r = sqrt(x^2 + y^2)
		// or r = x/Math.cos(theta) -- danger here is that cos(PI/2) would give 0 - dividing by zero
		
		return mag; 
	}

	public void setMagDir(double mag, double dir) {
		this.mag = mag; 
		this.dir = dir; 

		getX(); 
		getY();
	}

	public void setMagDir(int mag, int dir) {
		this.mag = (double) mag; 
		this.dir = (double) dir; 

		getX(); 
		getY();
	}

	// for situations where you want to force xspeed and yspeed to match mag and dir,
	// instead of the other way around. Not called in update().
	/*public void updateVecComponents() {
		// uses mag and dir to update xspeed and yspeed

		// x = r*cos(theta)
		x = mag * Math.cos(dir*(Math.PI/180.0)); 
		y = -mag * Math.sin(dir*(Math.PI/180.0)); 

		//System.out.println("called from instance of Vector2D: "+x+", "+y);
	}*/

	public void setX(double x) {
		this.x = x; 
		getDir(); 
		getMag(); 
	}

	public void setY(double y) {
		this.y = y; 
		getDir(); 
		getMag(); 
	}

	public void setXY(double x, double y) {
		this.x = x; 
		this.y = y;
		getDir(); 
		getMag(); 
	}

	// int versions
	public void setX(int x) {
		this.x = (double) x; 
		getDir(); 
		getMag(); 
	}

	public void setY(int y) {
		this.y = (double) y; 
		getDir(); 
		getMag(); 
	}

	public void setXY(int x, int y) {
		this.x = (double) x; 
		this.y = (double) y;
		getDir(); 
		getMag(); 
	}


	public double getX() {
		// x = r*cos(theta)
		this.x = mag * Math.cos(dir*(Math.PI/180.0)); 
		return x; 
	}

	public double getY() {
		// x = r*cos(theta)
		this.y = -mag * Math.sin(dir*(Math.PI/180.0));
		return y; 
	}

	// for vector addition (and I guess technically subtraction)
	public void add(Vector2D w) {
		setXY(this.x+w.getX(),this.y+w.getY()); 
	}

	// subtract vector w FROM this vector, this.x - w.x
	public void subtract(Vector2D w) {
		setXY(this.x-w.getX(),this.y-w.getY()); 
	} // should I be returning a new vector instead of directly modifying this one?
}