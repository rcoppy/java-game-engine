/* 

	Enemy class. 
	Extends physics object. 

	1/25/15.

	Alex Rupp-Coppi

*/ 

package game.model; 

import java.util.Random; 

public class Baddy extends Entity {
	
	//private BufferedImage sprite; 
	public PhysicsObject target; // object baddy will chase
	
	public int damage = 1; // do 1 damage 
	public int speed = 2; // starting speed - will be set higher as game goes on 

	private boolean canAttack = true; 
	private StepTimer attackTimer;  

	public Baddy(int x, int y, GameWorld world, PhysicsObject target) {
		super(x,y,world); 

		//objectIndex = "Baddy";

		System.out.println(getObjectIndex()+" spawned");

		// load sprite and animation 
		setAnimation(new Animation(Sprites.enemyBaseballPlayer, 2), true);
		// automatically sets up bbox
		
		this.target = target; 

		health = 5; 
		maxHealth = 5; 

		// attack cool down
		attackTimer = new StepTimer(30); // timer starts turned off; 30 steps = 0.5 seconds (I should really have a timestep/fps variable)
	}

	@Override
	public void eventCollision(PhysicsObject other) {
		// extends the spacing collision of SimpleCollidingObject
		super.eventCollision(other);

		// attack the player
		if (canAttack) {
			if (other instanceof Player) {
				Entity e = (Entity) other; 
				e.doDamage(damage); 
				canAttack = false; 
				attackTimer.reset(); 
			}
		}
	}

	@Override
	public void eventDestroy() {
		super.eventDestroy(); 

		// spawn in an explosion effect
		world.effectCreate(new EffectExplosion(x,y,xscale,yscale,world));

		// spawn in heart pickup 
		
		Random r = new Random(); 

		// 1 in 5 chance of a heart drop 
		if (r.nextInt(5) == 0) {
			world.instanceCreate(new HeartPickup(x,y,world)); 
		}

		world.roomGenerator.enemyCount --; // decrease total number of enemies; necessary to keep track of win
	}

	@Override
	public void update() {

		attackTimer.update(); 
		if (attackTimer.getIsFired()) {
			canAttack = true; 
			attackTimer.isActive = false; 
		}

		// chase target; flip to face target.
		
		// old method 

		/*if (x < target.x) {
			vel.setX(1); 
			xscale = 1; 
		}
		else if (x > target.x) {
			vel.setX(-1);
			xscale = -1; 
		}

		if (y < target.y) {
			vel.setY(1);
		}
		else if (y > target.y) {
			vel.setY(-1);
		}*/

		// new method is more fun
		vel = new Vector2D(target.x-x,target.y-y);
		vel.setMag(speed); 

		if (vel.getX() < 0) {
			xscale = -1; 
		}
		else { 
			xscale = 1; 
		}

		// shoot straight for the player 

		/*// wrap around room edges
		if (x < -32) {x = 400;}
		else if (x > 400) {x = -32;}

		if (y < 32) {y = 400;}
		else if (y > 400) {y = -32;}*/ 

		// apply updates to xprevious last
		super.update();

		// do death checks after hp update has happened in parent
		if (getIsDead()) {
			world.instanceDestroy(this); 
		}
	}
}