/* 

	GUI component base class. Abstract.

	2/8/15
	Alex Rupp-Coppi

*/ 

package game.model; 

import java.awt.Graphics; 
import java.awt.image.BufferedImage;

public abstract class GUIComponent { 
	
	public int x; 
	public int y; 
	protected int xoffset; 
	protected int yoffset; 
	protected int width; 
	protected int height; 
	protected Rect bbox; 

	protected final GameWorld world; 

	public GUIComponent(int x, int y, int xoff, int yoff, int w, int h, GameWorld world) {
		this.x = x; 
		this.y = y; 
		this.xoffset = xoff; 
		this.yoffset = yoff; 
		this.width = w; 
		this.height = h; 

		bbox = new Rect(x, y, width, height, xoffset, yoffset, 1.0, 1.0); 
	
		this.world = world; 

	}

	public void update() {

	}

	public void draw(Graphics g) {

		// example draw code 
		//g.drawImage(Sprites.enemyBaddy[0], bbox.getLeft(), bbox.getTop(), null);

	}

}