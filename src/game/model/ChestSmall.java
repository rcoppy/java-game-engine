/* 

	Small chest. Contains an item. Unlocked with a key.

	Extends large chest.

	2/11/15
	Alex Rupp-Coppi
	3:01 AM

*/

package game.model; 

import java.util.Random; 

public class ChestSmall extends ChestLarge { 

	//public ArrayList<Pickup> lootList; 
	private int health = 2;

	public ChestSmall(int x, int y, GameWorld world) {
		super(x,y,world);
		setAnimation(new Animation(Sprites.chestSmall, 0), true, 0,0,1.0,1.0); // xoffset of 0, yoffset of 0, xscale of 1, yscale of 1 
		objectIndex = "Wall";

		//lootList.add(new HeartPickup(x,y,world))
	}

	@Override 
	public void eventCollision(PhysicsObject other) {
		
		super.eventCollision(other);

	}

	public void dropLoot() {
		Random r = new Random(); 
		for (int i = 0; i < r.nextInt(1)+1; i ++) { // yields fewer hearts
			world.instanceCreate(new HeartPickup(x,y,world));
		}
	}

}