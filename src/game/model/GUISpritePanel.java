/* 

	Image panel GUI class. Display buffered images in the GUI. Extends GUI component.

	2/11/15
	Alex Rupp-Coppi
	12:35 AM
*/ 

package game.model; 

import java.awt.Graphics; 
import java.awt.image.BufferedImage;

public class GUISpritePanel extends GUIComponent { 
	
	public BufferedImage sprite; 

	public GUISpritePanel(int x, int y, int xoff, int yoff, int w, int h, GameWorld world, BufferedImage sprite) {
		super(x, y, xoff, yoff, w, h, world); 
		this.sprite = sprite; 
	}

	@Override
	public void draw(Graphics g) {
 
		g.drawImage(sprite, bbox.getLeft(), bbox.getTop(), bbox.width, bbox.height, null);

	}

}