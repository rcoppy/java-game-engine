/*

	Child of PhysicsObject that implements a really inefficient (but functional way) of spacing out colliding objects.
	Collision method based on code from my 2013 GM game Dungeon Mage; that code is based on a tutorial I lost the link to.

	2/6/15. 
	Alex Rupp-Coppi

*/

package game.model; 

import java.lang.Math; 

public abstract class SimpleCollidingObject extends PhysicsObject { 

	SimpleCollidingObject(int x, int y, GameWorld world) {
		super(x, y, world);
	}

	@Override
	public void eventCollision(PhysicsObject other) {
		super.eventCollision(other); 

		//if (other.objectIndex.equals("Wall")) {

		
		///stop enemies from bunching - increment them away from each other a little bit each step
		/*int xoff = x-other.x; 
		int yoff = y-other.y; 

		if (world.checkIsAreaFree(new Rect(x+xoff,y+yoff,1,1)) == null) { // testing a point instead of rectangle; null means it's free
		    
		    /*if (world.checkIsAreaFree(new Rect(other.x+xoff, other.y+yoff, 1, 1)) == null) { 
		        other.x -= xoff/4; 
		        other.y -= yoff/4;  

		        x += xoff/4; 
		    	y += yoff/4;  
		    }*/
		    //else { 
		    //	x -= xoff;
		  // 	 	y -= yoff; 
		    //}
		//}

		// The faster way of doing what I'm trying to do, but what I'm too tired to wrap my mind around: 
		// http://hamaluik.com/posts/simple-aabb-collision-using-the-minkowski-difference/

		Rect bbox2 = other.getBbox(); 

		/*//if (Math.abs(x-other.x) > Math.abs(y-other.y)) { // don't need to adjust both x and y - do it based on which has greater distance
			if (x > other.x) {
				x = other.x - (int) ((bbox.xscale*(bbox.width - bbox.xoffset) + bbox2.xscale*bbox2.xoffset)); // this can really only be explained with a diagram, sorry; Basically I'm moving one bbox out of another.
			}
			else { 
				x = other.x + (int) ((bbox.xscale*(bbox2.width - bbox2.xoffset) + bbox.xscale*bbox.xoffset)); // this can really only be explained with a diagram, sorry; Basically I'm moving one bbox out of another.
			}			
		//}
		//else { 
		/*	if (y < other.y) {
				y = other.y - (int) ((bbox.yscale*(bbox.width - bbox.yoffset) + bbox2.yscale*bbox2.yoffset)); // this can really only be explained with a diagram, sorry; Basically I'm moving one bbox out of another.
			}
			else { 
				y = other.y + (int) ((bbox.yscale*(bbox2.width - bbox2.yoffset) + bbox.yscale*bbox.yoffset)); // this can really only be explained with a diagram, sorry; Basically I'm moving one bbox out of another.
			}*/
		//}

		//x = getXPrevious(); 
		//y = getYPrevious(); */
		
		Vector2D raycast = new Vector2D(((bbox.getLeft()+bbox.getRight())/2)-((bbox2.getLeft()+bbox2.getRight())/2),((bbox.getTop()+bbox.getBottom())/2)-((bbox2.getTop()+bbox2.getBottom())/2)); 

		// make a ray. if it falls in 2 of 4 quadrants, determine whether or not we should correct the x or y position. Origin and endpoints of ray must be center of object bounding boxes.

		if (other.objectIndex.equals("Wall")) {
			if ((raycast.getDir() > 225 && raycast.getDir() < 315) || (raycast.getDir() > 45 && raycast.getDir() < 135)) {
				if (y < other.y) { y = bbox2.getTop() - bbox.height + bbox.yoffset; }
				else if (y > other.y) { y = bbox2.getBottom() + bbox.yoffset; }
			}
			else { 
				if (x < other.x) { x = bbox2.getLeft() - bbox.width + bbox.xoffset - 1; } // +/- 1 stops from getting stuck. Don't know why I don't need this for y check. 
				else if (x > other.x) { x = bbox2.getRight() + bbox.xoffset + 1; }
			}
		}
		else if (other.objectIndex.equals("Entity")) {
			
			int xoff = (int) raycast.getX(); 
			int yoff = (int) raycast.getY(); 

			if (world.checkIsAreaFree(new Rect(bbox.getLeft()+xoff, bbox.getTop()+yoff, (int) (bbox.xscale*bbox.width), (int) (bbox.yscale*bbox.height))) == null) { 
		        x += xoff/10 * (int) (vel.getMag()); 
		        y += yoff/10 * (int) (vel.getMag()); 
		    }
		}
		// So much time spent on 11 lines of code. #*$
	//}

	}

}