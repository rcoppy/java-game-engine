/*

	Effects abstract class. For stuff like explosions. 
	Placed above PhysicsObjects in the draw order.
	Collisions not checked with them.
	Self-destruct upon animation completion.

	2/11/15
	Alex Rupp-Coppi
	1:02 AM

*/ 

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

public class Effect { // a bare-bones, non-moving version of physics object
	
	public int x; 
	public int y; 

	public double xscale; // x and y scaling of image - usually -1 to 1 
	public double yscale; 

	//protected BufferedImage sprite; < redundant
	protected Animation animation; 

	protected final GameWorld world; // by passing the parent picture panel to the instance, the instance can call super functions like instaceDestroy from within itself

	protected Rect bbox; // rectangular bounding box

	public boolean isDebugMode = false; 

	public Effect(int x, int y, GameWorld world, Animation animation) { 
		this.x = x;
		this.y = y; 

		this.xscale = 1;
		this.yscale = 1; 

		this.world = world;  

       	bbox = new Rect(); //  rectangle with width, height of zero - basically null

       	// by default xoff and yoff are half of the sprite
       	setAnimation(animation, true, animation.getFrame().getWidth()/2, animation.getFrame().getHeight()/2, 1, 1); 
	}

	public Effect(int x, int y, int xoff, int yoff, double xscale, double yscale, GameWorld world, Animation animation) { 
		this.x = x;
		this.y = y; 

		this.xscale = xscale;
		this.yscale = yscale; 

		this.world = world;  

       	bbox = new Rect(); //  rectangle with width, height of zero - basically null

       	setAnimation(animation, true, xoff, yoff, xscale, yscale); 
	}	


	// Method called every step. 
	public void update() { // should be called by whatever's driving the event loop

		// optimization - kill yourself if not visible
		if (getIsOutsideWorld()) {
			world.effectDestroy(this);
		}

		// update animation, sprite if animation exists
		if (animation != null) {
			
			int length = animation.getStrip().length; 

			if (length > 1) { // only update the animation if it actually contains multiple frames
				animation.update();
			}

			if (animation.index >= length-1) {
				world.effectDestroy(this); // if animation over, destroy 
			} 
		}

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