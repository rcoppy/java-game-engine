/* 

	Player class. 
	Extends physics object. 

	1/25/15.

	Alex Rupp-Coppi

*/ 

package game.model; 

import java.lang.Math; 
import javax.swing.*; 

// from ImagePanel tutorial 
// tutorial: http://stackoverflow.com/questions/299495/how-to-add-an-image-to-a-jpanel
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import game.view.*; 

public class Player extends Entity {
	
	public final int playerNumber; // e.g. player 1, player 2, etc. 
	public int playerSpeed; 

	// gun stuff
	private StepTimer bulletTimer; 
	private boolean canShoot = true; 
	public int bulletDamage = 10; 
	public int bulletSpeed = 10; 

	public Player(int x, int y, GameWorld world, int playerNumber) {
		super(x,y,world); 

		instanceLabel = "Player"; // lets the GUI component find this

		this.playerNumber = playerNumber; 
		this.playerSpeed = 4; // rate player should be traveling at per step

		// load image - this should be handled in an animation subclass
		setAnimation(new Animation(Sprites.player, 1), true); // will automatically make a centered bounding box

		bulletTimer = new StepTimer(5); // fire a bullet every 5 frames 
		//bulletTimer.isActive = false;  -  this happens by default now

		maxHealth = 20; 
		health = maxHealth; 


	}

	@Override
	public void eventCollision(PhysicsObject other) {
		
		super.eventCollision(other);

		/*if (other.objectIndex.equals("Baddy")) {
			// you've been caught, reset!
			x = this.getStartX(); 
			y = this.getStartY();

			// switch animations up 
			if (animation.getStrip() != Sprites.enemyBaseballPlayer) { // avoid constantly resetting, and therefore seemingly freezing the sprite
				//  using .getStrip() potentially has problems if animation = null
				//setAnimation(new Animation(Sprites.enemyBaseballPlayer,2), true); 
			}

			world.instanceCreate(new DebugInstance(other.x,other.y,world)); 
		}*/

		System.out.println("Collided with "+other.instanceLabel+", "+other.objectIndex); 

	}

	@Override
	public void update() {
		
		bulletTimer.update(); 
		if (bulletTimer.getIsFired()) {
			canShoot = true; // bullet recharge complete
			bulletTimer.isActive = false; // stop timer
		}

		// player input
		
		// movement
		Vector2D w = world.input.getPlayerVector(playerNumber); // can't just set vel equal to player vector b/c then it's just a reference, not a separate vector (I think - I'm not good at explaining things)
		
		vel.setXY(w.getX(),w.getY());
		if (Math.round(w.getMag()) != 0) {
			vel.setMag(playerSpeed); // magnitude of InputHandler vectors is always 1, so we want to multiply by our own desired magnitude
		}
		else { 
			vel.setMag(0); // constant magnitude b/c before when we multiplied vec.mag by w.mag we had stuttering problems
		}
		//System.out.println("Mag: "+vel.getMag()+"\nDir: "+vel.getDir() + "\nX: "+vel.getX()+"\nY: "+vel.getY()); 

		// firing bullets
		if (world.input.getIsShootKeyDown()) {
			if (canShoot) {
				bulletTimer.reset(); // restarts timer, counting down
				canShoot = false; 
				PhysicsObject bullet = world.instanceCreate(new Bullet(x,y,world,this,bulletDamage)); // pass self to bullet as parent
				if (vel.getMag() == 0) { 
					if (xscale < 0) { 
						bullet.vel.setDir(180);
					}
					else { 
						bullet.vel.setDir(0); 
					}  
				}
				else { 
					bullet.vel.setDir(vel.getDir());
				}
				bullet.vel.setMag(bulletSpeed); 
			}
		}



		// flip sprite according to direction 
		if ((int) vel.getX() < 0) {
			xscale = -1; 
		}
		else if ((int) vel.getX() > 0) { // round vel.getX to an int b/c sometimes is a number like 0.00000123 ... might as well be 0. 
			xscale = 1; 
		} // do nothing if vel.getX() == 0; no point in updating.

		// wrap around room edges
		if (bbox.getLeft() < -bbox.width) {x = world.getWorldWidth()+bbox.xoffset;}
		else if (bbox.getLeft() > world.getWorldWidth()) {x = -bbox.width+bbox.xoffset;}

		if (bbox.getTop() < -bbox.height) {y = world.getWorldHeight()+bbox.yoffset;}
		else if (bbox.getTop() > world.getWorldHeight()) {y = -bbox.height+bbox.yoffset;}
	 
		// apply updates to xprevious last
		super.update();

		// do health check after entity has updated it 
		if (getIsDead()) {
			System.out.println("Player died."); 
		}
	}

}