/* 

	Entity abstract class. 
	Makes event checking easier.

	2/8/15

	Running out of time here. Damn it.

	Alex Rupp-Coppi

*/

package game.model; 

public class Entity extends SimpleCollidingObject { 

	public int health = 10; 
	public int maxHealth = 10; 
	private boolean isDead = false; 

	public Entity(int x, int y, GameWorld world) { 
		super(x,y,world); 
		objectIndex = "Entity"; 
	}

	@Override
	public void update() { 
		super.update(); 

		if (health <= 0) {
			isDead = true; 
			health = 0; 
		}
		else { 
			isDead = false; 
		}

		if (health > maxHealth) {
			health = maxHealth; 
		}
	}

	public boolean getIsDead() { 
		return isDead; 
	}

	// override this method if you want more control over how damage works - e.g. take into account armor, shielding
	public void doDamage(int damage) { 
		health -= damage; 
	}

	public void doHealing(int heal) {
		health += heal; 
	}


}