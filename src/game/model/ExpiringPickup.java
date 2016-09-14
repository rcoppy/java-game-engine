/* 

	Pickup that expires at end of a StepTimer.
	Extends Pickup. 

	2/11/15
	Alex Rupp-Coppi 
	2:02 AM 

*/ 

package game.model; 

public abstract class ExpiringPickup extends Pickup { 

	public StepTimer timer; 

	public ExpiringPickup(int x, int y, GameWorld world) {
		super(x,y,world);

		timer = new StepTimer(60*15); // 60 FPS * 15 = 15 seconds before disappearing
		timer.reset(); 
	}

	public ExpiringPickup(int x, int y, GameWorld world, int steps) {
		super(x,y,world);

		timer = new StepTimer(steps); 
		timer.reset();
	}

	@Override 
	public void update() {
		super.update(); 

		timer.update(); 

		if (timer.getIsFired()) {
			world.instanceDestroy(this); // self-destruct 
		}
	}

}