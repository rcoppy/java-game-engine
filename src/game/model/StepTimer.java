/* 

	Framerate-dependent step timer.
	Counts down a certain number of steps, then sets a boolean to true. 

	2/6/15
	Alex Rupp-Coppi

*/ 

package game.model; 

public class StepTimer { 

	private boolean isFired = false; 

	// feel free to mess with these vars in parent
	public boolean isActive = false; // call StepTimer.reset() to turn the timer on 
	public int stepCount; 
	public int currentCount; 

	public StepTimer(int stepCount) { 
		this.stepCount = stepCount; 
		this.currentCount = stepCount; 
	}

	public boolean getIsFired() { 
		return isFired; 
	}

	public void update() { 
		
		if (isActive) {
			isFired = false; 

			currentCount --; 

			if (currentCount <= 0) { // at end of timer, set flag to true and reset.
				currentCount = stepCount; 
				isFired = true; 
			}
		}
	}

	public void reset() { 
		isActive = true; 
		isFired = false; 
		currentCount = stepCount; 
	}
}