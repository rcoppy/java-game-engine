/* 

	Entity-specific health bar. Shows health of entity instance passed to it. Extends GUI stats bar.

	2/10/15
	Alex Rupp-Coppi

*/ 

package game.model; 

import java.awt.Graphics; 
import java.awt.image.BufferedImage;
import java.awt.Color; 

public class GUIEntityHealthBar extends GUIStatsBar { 

	public Entity entity; // the object the bar will show stats for
	public GUISpritePanel healthSprite; 
	public boolean shouldDrawSprite = true; 

	public Color colorCritical = Color.orange; // color of bar if it drops below half 
	public Color colorNormal = Color.green; // color above half

	public GUIEntityHealthBar(int x, int y, GameWorld world, Entity entity) {
		super(x, y, entity.maxHealth, world); 
		this.currentVal = entity.health;  		
		this.entity = entity; 
		int h = (int) (height * 1.3); // make the heart slightly bigger than the bar
		healthSprite = new GUISpritePanel(bbox.getLeft()-h-4,bbox.getTop()+height-h,0,0,h,h,world,Sprites.heart[1]); 
		// panel sprite has square dimensions - heightxheight of statsbar
	}

	@Override 
	public void update() { 
		super.update();
		currentVal = entity.health; 
		maxVal = entity.maxHealth; 

		// update heart sprite, statsbar color based on health
		if (currentVal < maxVal/2) {
			healthSprite.sprite = Sprites.heart[0];
			colorBar = colorCritical;
		}
		else { 
			healthSprite.sprite = Sprites.heart[1];
			colorBar = colorNormal; 
		}
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g); 

		healthSprite.draw(g); 
	}

}