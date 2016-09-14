/* 

	Heart pickup. 
	Restores a certain amount of player health on contact, then self-destructs.

	Extends Pickup. 

	2/11/15
	Alex Rupp-Coppi 
	1:46 AM 

*/ 

package game.model; 

public class HeartPickup extends ExpiringPickup { 

	public int hpAmount = 2; // will restore 5 health 

	public HeartPickup(int x, int y, GameWorld world) {
		super(x,y,world);

		setAnimation(new Animation(Sprites.heart,15), true); // animation oscillates every 5 frames
	}

	public HeartPickup(int x, int y, GameWorld world, int hp) {
		super(x,y,world);

		this.hpAmount = hp; 

		setAnimation(new Animation(Sprites.heart,5), true); // animation oscillates every 5 frames
	}

	@Override 
	public void eventCollision(PhysicsObject other) {

		// heal player on contact, then kill self
		if (other instanceof Player) {
			
			Player p = (Player) other; // is of type PhysicsObject, so need to cast 

			if (p.health < p.maxHealth) {
				p.doHealing(hpAmount); 
				world.instanceDestroy(this);
			}
		} 

		// check for self-destruction after performing player check
		super.eventCollision(other);
	}

}