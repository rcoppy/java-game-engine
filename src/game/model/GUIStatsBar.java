/* 

	Stats bar. (Basically a health bar.) Extends GUI component.

	2/8/15
	Alex Rupp-Coppi

*/ 

package game.model; 

import java.awt.Graphics; 
import java.awt.Color; 

public class GUIStatsBar extends GUIComponent { 

	public int minVal = 0; 
	public int maxVal; 
	public int currentVal; 

	public Color colorBack = Color.red; // bar background - set this to 'null' if you don't want the bar to have a background
	public Color colorBar = Color.green; // actual bar color
	public Color colorOutline = Color.black; // color of outline

	public boolean shouldShowOutline = true; 

	public GUIStatsBar(int x, int y, int maxVal, GameWorld world) {
		super(x, y, 0, 0, 256, 16, world);
		this.maxVal = maxVal; 
		this.currentVal = maxVal; 
	}

	public GUIStatsBar(int x, int y, int maxVal, int minVal, int currentVal, GameWorld world) {
		super(x, y, 0, 0, 256, 16, world); 
		this.maxVal = maxVal; 
		this.minVal = minVal; 
		this.currentVal = currentVal; 
	}

	@Override 
	public void update() { 
		super.update();
	}

	@Override
	public void draw(Graphics g) {

		if (colorBack != null) {
			g.setColor(colorBack); 
			g.fillRect(bbox.getLeft(),bbox.getTop(),width,height); 
		}

		// healthbar width 
		int w = 0; 
		
		if (currentVal > minVal) {
			w = (int) ((double) currentVal/maxVal * width);	
		}
		
		if (currentVal >= maxVal) {
			w = width; 
		} 

		g.setColor(colorBar); 
		g.fillRect(bbox.getLeft(),bbox.getTop(),w,height); 

		// draw outline
		g.setColor(colorOutline);
		g.drawRect(bbox.getLeft(),bbox.getTop(),width,height);
	}

}