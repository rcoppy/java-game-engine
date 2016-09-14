/* 

	Bullet class. Extends physicsobject.

	2/6/15.
	Alex Rupp-Coppi

*/ 

package game.model; 

public class Bullet extends PhysicsObject {
	
	PhysicsObject parent; 
	public final int damage; 

	public Bullet(int x, int y, GameWorld world, PhysicsObject parent, int damage) {
		super(x,y,world); 

		this.parent = parent; 
		this.damage = damage; // how much damage will inflict on enemy

		objectIndex = "Bullet";

		setAnimation(new Animation(Sprites.fireballSmall, 2), true);
	}

	@Override
	public void eventCollision(PhysicsObject other) {
		super.eventCollision(other); 

		if (other != parent && !other.objectIndex.equals("Pickup")) { // can't collide with creator, or pickups 
			
			// code for inflicting damage on enemies
			// other.hp -= damage; 

			/*if (other instanceof Baddy) {
				world.instanceDestroy(other); 
			}*/
			
			if (other.objectIndex.equals("Entity")) {
				Entity e = (Entity) other; 
				e.doDamage(damage); 
			}

			world.instanceDestroy(this); // explode on death

		}
	}

	@Override
	public void eventDestroy() {
		super.eventDestroy();

		//spawn a teeny tiny explosion
		world.effectCreate(new EffectExplosion(x,y,0.35,0.35,world));
	}

	@Override 
	public void update() { 
		super.update(); 

		// destroy bullets when off-screen to prevent memory leak
		if (getIsOutsideWorld()) { 
			world.instanceDestroy(this); 
		}
	}
}