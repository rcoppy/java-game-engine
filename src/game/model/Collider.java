/* 

	Static collider class. 
	Contains useful generic collision methods that can be called from anywhere.

	2/6/15.
	Alex Rupp-Coppi

*/

package game.model; 

import java.lang.Math; 

public class Collider {
	
	public Collider() {

	}

	// box collision for 2 separate rectangles - returns boolean
	public static boolean getIsRectCollided(int x1, int y1, int x2, int y2, int u1, int v1, int u2, int v2) {

		// This method comes from: https://www.youtube.com/watch?v=ghqD3e37R7E
		// 3rd method I've tried.
		//return !(x2 <= u1 || u2 <= x1 || y2 <= v1 || v2 <= y1); 


		boolean isCollided = false;

		// if either endpoint (u1 or u2) is within bounds x1,x2, axis has a collision.
		// accomplished with lots of white board drawings.
		if ((x1 <= u1 && u1 <= x2) || (x1 <= u2 && u2 <= x2)) {
			// x-axis collision satisfied. Proceed to y check.
			if ((y1 <= v1 && v1 <= y2) || (y1 <= v2 && v2 <= y2)) {
				// y-axis collision satisfied. Both x and y axes satisfied. 
				// full-on collision. 

				isCollided = true; 
			}
		}

		// u1 or u2 depending on parameters given may actually be the bounding set of points, not x1, y1. 
		// So perform a check using u1, u2 as the reference and x1,x2 as the test points.
		// if either endpoint (x1 or x2) is within bounds u1,u2, axis has a collision.
		// accomplished with lots of white board drawings.
		else if ((u1 <= x1 && x1 <= u2) || (u1 <= x2 && x2 <= u2)) {
			// x-axis collision satisfied. Proceed to y check.
			if ((v1 <= y1 && y1 <= v2) || (v1 <= y2 && y2 <= v2)) {
				// y-axis collision satisfied. Both x and y axes satisfied. 
				// full-on collision. 

				isCollided = true; 
			}
		}

		return isCollided;
	}

	public static boolean getIsCircleCollided(int x1, int y1, int r1, int x2, int y2, int r2) {
		if (Math.sqrt(((x2-x1)*(x2-x1))+((y2-y1)*(y2-y1))) < (r1+r2)) {
			return true;
		}

		return false; 
	}
}


/* Old box collision method - doesn't work if one object is completely contained within another.
// only 1 of these needs to be satisfied - 2 overlapping corners = a collision
if ((u1 <= x2 && v1 <= y2) && (u1 >= x1 && v1 >= y1)) {
	isCollided = true; 
}
else if ((u2 >= x1 && v2 >= y1) && (u2 <= x2 && v2 <= y2)) {
	isCollided = true;
}
else if ((u2 >= x1 && v2 <= y2) && (u2 <= x2 && v2 >= y1)) {
	isCollided = true;
}
else if ((u1 <= x2 && v2 >= y1) && (u1 >= x1 && v2 <= y2)) {
	isCollided = true;
}

//System.out.println("Collision check ended, result "+isCollided);
*/