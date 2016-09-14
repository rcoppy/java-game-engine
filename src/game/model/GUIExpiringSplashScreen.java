/* 

	A single-texture splash screen, it will self-destruct at the end of a timer. 

	Extends GUISpritePanel.

	2/11/15
	Alex Rupp-Coppi
	4:49 PM

*/ 

package game.model;

import java.awt.image.BufferedImage; 

public class GUIExpiringSplashScreen extends GUISpritePanel { 

	public StepTimer timer;

	public GUIExpiringSplashScreen(int waitSteps, GameWorld world, BufferedImage sprite) {
		
		super(0,0,0,0, sprite.getWidth(), sprite.getHeight(), world, sprite);

		timer = new StepTimer(waitSteps); 
		timer.reset(); 
	}

	@Override 
	public void update() { 
		//super.update(); 

		timer.update(); 

		if (timer.getIsFired()) {
			world.gameGUI.componentRemove(this); 
		}
	}


}