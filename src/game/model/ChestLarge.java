/* 

	Large chest. Contains an item. Unlocked with a key.

	2/11/15
	Alex Rupp-Coppi
	2:25 AM

*/

package game.model; 

import java.util.ArrayList; 
import java.util.Random; 

public class ChestLarge extends PhysicsObject { 

	//public ArrayList<Pickup> lootList; 
	private int health = 4;

	public ChestLarge(int x, int y, GameWorld world) {
		super(x,y,world);
		setAnimation(new Animation(Sprites.chestLarge, 0), true, 0,0,1.0,1.0); // xoffset of 0, yoffset of 0, xscale of 1, yscale of 1 
		objectIndex = "Wall";

		//lootList.add(new HeartPickup(x,y,world))
	}

	@Override 
	public void eventCollision(PhysicsObject other) {
		
		super.eventCollision(other);

		if (other.objectIndex.equals("Bullet")) {
			health --; 

			if (health <= 0) {
				dropLoot(); 
				world.instanceDestroy(this);
			}
		}
	}

	public void dropLoot() {
		Random r = new Random(); 
		for (int i = 0; i < r.nextInt(3)+1; i ++) {
			world.instanceCreate(new HeartPickup(x,y,world));
		}
	}

}