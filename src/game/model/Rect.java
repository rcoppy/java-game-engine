/*

	Rect class. Useful for rectangular bounding boxes.
	Automatically adjusts boundaries to origin.

	If scaled, will report absolute coordinates - getLeft() will always yield the leftmost xcoord of the Rect. 

	2/5/15
	Alex Rupp-Coppi

*/ 

package game.model; 

public class Rect {
	
	public int x; 
	public int y; 

	public double xscale; 
	public double yscale;

	// want these values to be easily accessible, but not changeable once set
	public final int width; 
	public final int height; 

	public final int xoffset; 
	public final int yoffset;

	// left, right are x values; top, bottom are y values
	private int left; 
	private int top; 
	private int right; 
	private int bottom; 

	public Rect() {
		this.x = 0; 
		this.y = 0;

		this.width = 0; 
		this.height = 0; 

		this.xoffset = 0; 
		this.yoffset = 0; 

		this.left = x-xoffset; 
		this.top = y-yoffset; 
		this.right = (x-xoffset)+width; 
		this.bottom = (y-yoffset)+height;

		this.xscale = 1.0; 
		this.yscale = 1.0; 
	}

	public Rect(int x, int y, int width, int height) {
		this.x = x; 
		this.y = y;

		this.width = width; 
		this.height = height; 

		this.xoffset = 0; 
		this.yoffset = 0; 

		this.left = x-xoffset; 
		this.top = y-yoffset; 
		this.right = (x-xoffset)+width; 
		this.bottom = (y-yoffset)+height;

		this.xscale = 1.0; 
		this.yscale = 1.0; 
	}

	public Rect(int x, int y, int width, int height, int xoffset, int yoffset, double xscale, double yscale) {
		this.x = x; 
		this.y = y;

		this.width = width; 
		this.height = height; 

		this.xoffset = xoffset; 
		this.yoffset = yoffset; 

		this.left = x-xoffset; 
		this.top = y-yoffset; 
		this.right = (x-xoffset)+width; 
		this.bottom = (y-yoffset)+height;

		this.xscale = xscale; 
		this.yscale = yscale; 
	}

	public int getLeft() {
		left = x-xoffset; 
		
		if (xscale < 0) { // shift the rectangle if we're flipped - many hours spent drawing diagrams to figure this out
			left += (int) (xscale*(width-2*xoffset));
		} // even when the object is flipped, we want the rectangle to report the actual leftmost point - which is why we don't simply flip the right one
		// collision detection depends on the point labeled as leftmost actually being the leftmost point

		return left;
	} 

	public int getTop() {
		top = y-yoffset; 
		
		if (yscale < 0) { // shift the rectangle if we're flipped - many hours spent drawing diagrams to figure this out
			top += (int) (yscale*(height-2*yoffset));
		}

		return top;
	}

	public int getRight() {
		right = (x-xoffset)+width; 
		
		if (xscale < 0) {
			right += (int) (xscale*(width-2*xoffset));
		}

		return right;
	}

	public int getBottom() {
		bottom = (y-yoffset)+height;

		if (yscale < 0) {
			bottom += (int) (yscale*(height-2*yoffset));
		}

		return bottom;
	} 

	// check to see if this rectangle is intersecting with another one.
	public boolean getIsIntersecting(Rect other) {

		if (Collider.getIsRectCollided(left, // Collider is a globally accessible static class with collision methods
						  top,
						  right,
						  bottom,

						  other.getLeft(),
						  other.getTop(),
						  other.getRight(),
						  other.getBottom())) {			  
			
			return true; 
		}

		return false;
	}
}