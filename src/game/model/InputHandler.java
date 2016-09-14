/* 

	Interface between game world, game objects, and KeyActionController.

	2/6/15.
	Alex Rupp-Coppi.

*/

package game.model; 

public class InputHandler { 

	private Vector2D[] playerVectors; // each player has an individual control vector, i.e. an analog stick; axis values are modified by KeyActionController in response to keypresses

	private int playerCount; 

	private boolean isShootKeyDown; 
	private boolean isActionKeyDown; 

	public InputHandler(int playerCount) { // how many players in this game? e.g. 1-4

		this.playerVectors = new Vector2D[playerCount]; 
		this.playerCount = playerCount; 

		isShootKeyDown = false; 
		isActionKeyDown = false; 

		for (int i = 0; i < playerCount; i ++) {
			playerVectors[i] = new Vector2D(); 
		}

		/*
	
		Debug

		playerVectors[0].x = 0.0;
		playerVectors[0].y = -1.0; 
		playerVectors[0].setMag(1.0); // normalize the vector - prevent magnitudes of e.g. 1.41

		System.out.println("Test Mag: "+playerVectors[0].getMag()); 
		System.out.println("Test Dir: "+playerVectors[0].getDir()); 
		*/
	}

	public void update() { 
		// blah fuck i don't know what i'm doing 
	}

	// you'll probably never need this.
	public void resetVectors() { 
		for (int i = 0; i < playerVectors.length; i ++) {
			playerVectors[i].setXY(0,0);  
		}
	}

	// call this from an instance trying to use this input (e.g. player: player.vel.x = InputHandler.getPlayerVector(0).x)
	public Vector2D getPlayerVector(int playerNumber) {
		return playerVectors[playerNumber]; 
	}

	// Input functions - write more if you need to (e.g. 'fireGun()')
	public void moveUp(int playerNumber) { // player is pushing 'analog stick' upward 
		playerVectors[playerNumber].setY(-1.0); 
		playerVectors[playerNumber].setMag(1.0); // normalize the vector - prevent magnitudes of e.g. 1.41
	}

	public void moveDown(int playerNumber) { // player is pushing 'analog stick' upward 
		playerVectors[playerNumber].setY(1.0); 
		playerVectors[playerNumber].setMag(1.0); // normalize the vector - prevent magnitudes of e.g. 1.41
	}

	public void moveRight(int playerNumber) { // player is pushing 'analog stick' upward 
		playerVectors[playerNumber].setX(1.0); 
		playerVectors[playerNumber].setMag(1.0); // normalize the vector - prevent magnitudes of e.g. 1.41
	}

	public void moveLeft(int playerNumber) { // player is pushing 'analog stick' upward 
		playerVectors[playerNumber].setX(-1.0); 
		playerVectors[playerNumber].setMag(1.0); // normalize the vector - prevent magnitudes of e.g. 1.41
	}

	// key release functions - write more if you need to (e.g. 'fireGun()')
	public void stopUp(int playerNumber) { // player is pushing 'analog stick' upward 
		if (playerVectors[playerNumber].getY() < 0) {
			playerVectors[playerNumber].setY(0.0);
		} 
		//playerVectors[playerNumber].setMag(1.0); // normalize the vector - prevent magnitudes of e.g. 1.41
		//System.out.println("right motion killed"); 
	}

	public void stopDown(int playerNumber) { // player is pushing 'analog stick' upward 
		if (playerVectors[playerNumber].getY() > 0) {
			playerVectors[playerNumber].setY(0.0);
		}  
		//playerVectors[playerNumber].setMag(1.0); // normalize the vector - prevent magnitudes of e.g. 1.41
	}

	public void stopRight(int playerNumber) { // player is pushing 'analog stick' upward 
		if (playerVectors[playerNumber].getX() > 0) {
			playerVectors[playerNumber].setX(0.0);
		} 
		//playerVectors[playerNumber].setMag(1.0); // normalize the vector - prevent magnitudes of e.g. 1.41
	}

	public void stopLeft(int playerNumber) { // player is pushing 'analog stick' upward 
		if (playerVectors[playerNumber].getX() < 0) {
			playerVectors[playerNumber].setX(0.0);
		} 
		//playerVectors[playerNumber].setMag(1.0); // normalize the vector - prevent magnitudes of e.g. 1.41
	}

	// non-movement related
	public boolean getIsShootKeyDown() { 
		return isShootKeyDown; 
	}

	public boolean getIsActionKeyDown() { 
		return isActionKeyDown; 
	}

	public void setShootKey(boolean isDown) {
		isShootKeyDown = isDown; 
	}

	public void setActionKey(boolean isDown) {
		isActionKeyDown = isDown; 
	}
}