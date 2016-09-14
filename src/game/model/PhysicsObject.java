// Probably created around 1/25/15.

package game.model; 

import java.lang.Math; 
import javax.swing.*; 

// from ImagePanel tutorial 
// tutorial: http://stackoverflow.com/questions/299495/how-to-add-an-image-to-a-jpanel
import java.awt.Graphics;
import java.awt.Color; 
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
/*import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;*/

/* 

	Commit 2/5/15: 'sprite' var removed, now only animations taken - maybe not as efficient, but cleaner code/fewer dangerous scenarios

	TODO: Collision masks?
		Stretch sprite to fill mask?

		bboxUpdate function to make a new rect when animation changes

		Should animation class itself contain a bbox?

		add gravity? 
		Boolean isGravityEnabled
		Vector2D gravity
		double maxGravSpeed - how fast can you fall?
	
		Separate animation class to handle loading of images?
		Sprite filepath string?

		isPhysicsEnabled - will perform 'physics' collisions (modified GM code)

		add a tag variable for keeping track of specific instances
			(e.g. tag.equals("Player"))

*/ 


public abstract class PhysicsObject { 
	
	public int x; 
	public int y; 

	public double xscale; // x and y scaling of image - usually -1 to 1 
	public double yscale; 

	public Vector2D vel; // velocity 

	private int xstart; 
	private int ystart;

	private int xprevious;
	private int yprevious;

	//protected BufferedImage sprite; < redundant
	protected Animation animation; 

	protected final GameWorld world; // by passing the parent picture panel to the instance, the instance can call super functions like instaceDestroy from within itself

	protected String objectIndex; // string name of object, can be used for collision comparisons
	public String instanceLabel; 

	protected Rect bbox; // rectangular bounding box

	public boolean isDebugMode = false; 

	public PhysicsObject(int x, int y, GameWorld world) { 
		this.x = x;
		this.y = y; 

		this.xscale = 1;
		this.yscale = 1; 

		this.xstart = x;
		this.ystart = y;

		this.xprevious = x;
		this.yprevious = y;

		this.world = world; 

		vel = new Vector2D(0,0); // start at rest   

		objectIndex = "PhysicsObject";
		instanceLabel = "null";

		// actual loading of animation should be handled in subclass
       	animation = null; // subclass needs to set the animation
       	// threw out the sprite variable altogether. with the animation, it was redundant, and there were certain situations with nullpointer errors.

       	bbox = new Rect(); //  rectangle with width, height of zero - basically null

	}

	// Method called every step. 
	public void update() { // should be called by whatever's driving the event loop

		// update animation, sprite if animation exists
		if (animation != null) {
			if (animation.getStrip().length > 1) { // only update the animation if it actually contains multiple frames
				animation.update();
			}
		}

		this.xprevious = x;
		this.yprevious = y;

		// update position
		x += (int) vel.getX(); 
		y += (int) vel.getY();

		bbox.x = x; 
		bbox.y = y; 

		bbox.xscale = xscale; 
		bbox.yscale = yscale; 

		// Example: how to destroy yourself
		/*if (x < 40) {
			world.instanceDestroy(this); // kill yourself if beyond a boundary
		}*/
		// hopefully Java's garbage collector gets rid of all the destroyed instances 
	}

	// override this method if you need to; called every step by whomever handles drawing. 
	public void draw(Graphics g) { 
		
		// debug: draw bounding box
		if (isDebugMode) {
			g.setColor(Color.blue); 
			g.drawRect(bbox.getLeft(),bbox.getTop(),bbox.width,bbox.height); 
		}

		if (animation != null)  {
			if (xscale != 1 || yscale != 1) {
				// image is being scaled, do fancy drawing
				Graphics2D g2d = (Graphics2D) g;
				BufferedImage sprite = animation.getFrame();
				
				int xx = 0; // offsets based on image scaling  
				int yy = 0; 

				if (xscale < 0) { // gotta compensate for 'negative width'
					xx = (int) (-1*xscale*sprite.getWidth());
				}
				if (yscale < 0) {
					yy = (int) (-1*yscale*sprite.getHeight());
				}

				g2d.drawImage(sprite, (int) (x + -1*xscale*bbox.xoffset), (int) (y + -1*yscale*bbox.yoffset), (int) (xscale*sprite.getWidth()), (int) (yscale*sprite.getHeight()), null); // see javadoc for more info on the parameters            		
				
				// debug
				//System.out.println("drew "+objectIndex+" fancy");
			}
			else { 
				// not doing anything to the image, draw it normally
				g.drawImage(animation.getFrame(), bbox.getLeft(), bbox.getTop(), null); // see javadoc for more info on the parameters            		
				
				// debug
				//System.out.println("drew "+objectIndex+" normal");
			}
			
		}

		// debug: draw origin
		if (isDebugMode) {
			g.setColor(Color.red); 
			g.drawRect(bbox.x-3,bbox.y-3,6,6); 
		}
	}

	// override this method and put it in proper code for collision event with other instances
	// this method will be triggered by the collision checker in the event loop
	public void eventCollision(PhysicsObject other) {
		// debug to see if collisions are actually registering correctly
		//System.out.println("Coll. btwn. "+this.objectIndex+", "+other.objectIndex); 
	}

	// event triggered upon object's destruction. Uses: e.g. spawn an explosion on death.
	public void eventDestroy() {
		// do something on death 
	}

	public int getStartX() { 
		return xstart;
	}

	public int getStartY() { 
		return ystart; 
	}

	public int getXPrevious() { 
		return xprevious;
	}

	public int getYPrevious() { 
		return yprevious; 
	}

	public String getObjectIndex() {
		return objectIndex; 
	}

	public boolean getIsOutsideWorld() { 
		boolean flag = true; 

		if (Collider.getIsRectCollided(bbox.getLeft(),bbox.getTop(),bbox.getRight(),bbox.getBottom(),0,0,world.getWorldWidth(),world.getWorldHeight())) {
			flag = false; 
			// debug
			//System.out.println(this.getObjectIndex()+" in the room");
		}
		else { 
			//System.out.println(this.getObjectIndex()+" outside the room at "+x+","+y);
		}

		// debug 
		//System.out.println(world.getWorldWidth()+","+world.getWorldHeight()); 

		return flag;
	}

	public Animation getAnimation() {
		return animation;
		// use animation.strip == Sprite.foo for image comparisons
	} 

	public void setAnimation(Animation animation, boolean shouldUpdateBox) {
		this.animation = animation;

		if (shouldUpdateBox) {
			int w = animation.getFrame().getWidth(); 
			int h = animation.getFrame().getHeight();

			bbox = new Rect(x,y,w,h,w/2,h/2,1.0,1.0); // will create a new bounding box the size of the sprite with origin at the sprite center
		} 
	}

	// has an extra set of parameters - new offset if you want it 
	public void setAnimation(Animation animation, boolean shouldUpdateBox, int xoff, int yoff, double xscale, double yscale) {
		this.animation = animation;

		if (shouldUpdateBox) {
			int w = animation.getFrame().getWidth(); 
			int h = animation.getFrame().getHeight();

			bbox = new Rect(x,y,w,h,xoff,yoff,xscale,yscale); // will create a new bounding box the size of the sprite with origin at the sprite center
		} 
	}

	public Rect getBbox() {
		return bbox; 
	}
}