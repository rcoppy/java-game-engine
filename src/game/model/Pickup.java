/* 

	Pickup abstract object. Keys, bombs, and hearts all extend it. 
	Something dropped by an enemy, chest.

	2/11/15
	Alex Rupp-Coppi
	1:40 AM

*/

package game.model; 

import java.util.Random; 

public abstract class Pickup extends SimpleCollidingObject { 

	public Pickup(int x, int y, GameWorld world) {
		super(x,y,world);
		setAnimation(new Animation(Sprites.key, 1), true); 
		objectIndex = "Pickup";

		// give it a random direction and speed on spawn 
		Random r = new Random(); 
		vel.setDir(r.nextInt(360));
		vel.setMag(r.nextInt(4)+2); 
	}

	@Override 
	public void eventCollision(PhysicsObject other) { 
		// only collide with walls - don't run the superclass's code for colliding with entities
		if (other.objectIndex.equals("Wall")) {
			super.eventCollision(other);
			vel.setDir(vel.getDir()+180); // reverse direction
		}
	}

	@Override 
	public void update() { 
		super.update(); 
		
		vel.setMag(vel.getMag() * 0.75); // gradually decelerate
		
		if (vel.getMag() <= 0.1) {
			vel.setMag(0);
		} 
	}

}