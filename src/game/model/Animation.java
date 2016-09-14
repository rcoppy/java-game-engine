/* 

	Class that handles cycling through a sprite.
	Call getFrame() to get its current image.

	Animation speed is framerate dependent. If you double the game's framerate, animation speed will also double. (I'm too lazy to figure out how to compensate, sorry.)

	TODO: Implement deltaTime.

	2/4/15
	Alex Rupp-Coppi


*/ 

package game.model; 

import java.awt.image.BufferedImage;

public class Animation { 
		
	private	BufferedImage[] strip; // array of images that this class will cycle through
	public int index; // position in array/image in strip  
	public int waitTime; // number of steps to wait between each update: e.g. if waitTime = 2, two clock cycles have to go by before image updates (if game is at 60 FPS, sprite will appear to be 30 FPS)
	private int currentTime;
	public boolean isActive = true; // set to false, animation freezes

	public Animation(BufferedImage[] strip) {
		this.strip = strip;
		index = 0;
		waitTime = 1; 
		currentTime = waitTime;
	}

	public Animation(BufferedImage[] strip, int waitTime) {
		this.strip = strip;
		index = 0;
		this.waitTime = waitTime; 
		currentTime = waitTime;
	}

	public void update() {
		if (isActive && waitTime != 0) {
			currentTime --; 

			if (currentTime <= 0) { // if waitTime has elapsed
				currentTime = waitTime; // reset the counter
				index ++; // increment the image index
				if (index >= strip.length) {
					index = 0; // reset the image index if out of bounds
				}
			}
		}
	}

	// returns current image of the animation
	public BufferedImage getFrame() { 
		return strip[index]; 
	}

	// returns strip - useful for sprite comparisons
	public BufferedImage[] getStrip() {
		return strip; 
	}

}