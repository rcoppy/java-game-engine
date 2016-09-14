/* 
	Game world class. Used to be part of PicturePanel/View, but wanted to keep model components as separate from view as possible.

	2/6/15.
	Alex Rupp-Coppi.
	Part of splitting the project into packages, overhauling organization, conforming project to MVC model. 
*/

package game.model; 

import java.awt.Graphics;
import java.awt.image.BufferedImage; 
import java.util.ArrayList; 
import java.util.Random; 

public class GameWorld {
	

	private ArrayList<PhysicsObject> instanceList; // objects that register collisions
	private ArrayList<Effect> effectList; // visual effects only, e.g. explosions

	private int worldWidth; 
	private int worldHeight; 

	public InputHandler input; 
	public GUIContainer gameGUI; 
	public RoomGenerator roomGenerator; 
	public int gameLevel; 
	public Player player; 
	public int playerHealth = 20; // carried over from session to session
	private boolean isGameLost = false; 

	// custom user game setup code (do things like set up enemies, spawn in the player)
	// This is really the only function you should modify in this class.
	public void init() {

		
		// Spawn environment.


		/*instanceCreate(new Wall(256,256,this)).instanceLabel = "testWall"; 

		// Spawn entities. 

		// even though class is player, must be type of superclass to work in array
		PhysicsObject player1 = instanceCreate(new Player((int) (worldWidth*0.75),(int) (worldHeight*0.75),this,0)); // player 0 
		//PhysicsObject player2 = instanceCreate(new Player((int) (worldWidth*0.25),(int) (worldHeight*0.75),this,1)); // player 2
			// player 2 doesn't have an input map set up - would be easy to do, though 

		// spawn enemies
		for (int i = 0; i < 4; i ++) {
			for (int j = 0; j < 4; j ++) {
				PhysicsObject b = instanceCreate(new Baddy(32+128*i,32+128*j,this,player1)); // targeting the player
				b.vel.setMag(1); 
				b.vel.setDir(180+i*90+j*90);
			}
		}*/

		// generate new room 

		if (player != null) {
			playerHealth = player.health; // save old stats
		}

		gameLevel ++; // increase the difficulty

		instanceList = new ArrayList<PhysicsObject>();
		effectList = new ArrayList<Effect>(); 

		roomGenerator.generate(gameLevel);

		player = (Player) (findInstanceWithLabel("Player")); // find the newly generated player

		gameGUI = new GUIContainer(this); // reset the GUI 


	}

	public GameWorld(int worldWidth, int worldHeight) {
		
		instanceList = new ArrayList<PhysicsObject>();
		effectList = new ArrayList<Effect>(); 

		input = new InputHandler(2); // we want a 2-player game, so set up inputHandler with 2 controller vectors

		this.worldWidth = worldWidth; 
		this.worldHeight = worldHeight; 

		roomGenerator = new RoomGenerator(this);
		gameLevel = 0; //  every time player clears room, game level increments 

		

		// initialize the GUI after initializing the game - this way, GUI elements that are tag-dependent won't throw null pointer errors
		//gameGUI = new GUIContainer(this);

		// this should be where your custom setup code goes. (e.g. spawning in the player, enemies.)
		init(); 


		// add introductory splashscreen to the GameGUI, on first startup only
		gameGUI.componentAdd(new GUIExpiringSplashScreen(180,this,Sprites.splashScreenIntro[0])); // display instructions for 3 seconds

	}

	// call from within event loop. (timerActions() in View.).
	public void update() {

		// update instances
		for (int i = 0; i < instanceList.size(); i++) {
			instanceList.get(i).update(); 
		}

		// update effects
		for (int i = 0; i < effectList.size(); i++) {
			effectList.get(i).update(); 
		}

		// input handler debug
		//Vector2D w = input.getPlayerVector(0); 
		//System.out.println("Mag: "+w.getMag()+"\nDir: "+w.getDir() + "\nX: "+w.getX()+"\nY: "+w.getY()); 
		

		// check collisions
		checkCollisions(); 

		// finally, update GUI
		gameGUI.update(); 


		// Check win/lose status of game. 
		if (roomGenerator.enemyCount <= 0) {
			// you won the round!
			init(); 
			// start another round.
		}

		if (player != null) {
			if (player.health <= 0) {
				// player is dead. 
				// YOU LOSE!!!
				isGameLost = true; 

				// add 'you died' splashscreen
				gameGUI.componentAdd(new GUIExpiringSplashScreen(240,this,Sprites.splashScreenIntro[0]));
			}
		}
	}

	// drawing backgrounds
	public void drawBackground(Graphics g) {
		// not enough time to write an actual background class, sorry
		// 2:11 AM, 2/11/15

		BufferedImage back = Sprites.wallBrown[0];
		int w = back.getWidth(); 
		int h = back.getHeight(); 

		for (int i = 0; i < getWorldWidth()/w+1; i ++) {
			for (int j = 0; j < getWorldHeight()/h+1; j++) {
				g.drawImage(back,i*w,j*h,null); 
			}
		}
	}

	// call from within paintComponent() in View.
	public void drawInstances(Graphics g) {
		for (int i = 0; i < instanceList.size(); i ++) {
			instanceList.get(i).draw(g); 
		}
	}

	public void drawEffects(Graphics g) {
		for (int i = 0; i < effectList.size(); i ++) {
			effectList.get(i).draw(g); 
		}
	}

	public void drawGUI(Graphics g) {
		gameGUI.draw(g); 
	}

	// add a new object to the global instance list. 
	public PhysicsObject instanceCreate(PhysicsObject inst) {
		instanceList.add(inst);

		return instanceList.get(instanceList.size() - 1); // returns the OBJECT just added, not its index
	}

	public void instanceDestroy(PhysicsObject inst) { 
		int i = instanceList.indexOf(inst);

		if (i != -1) { // instance does in fact exist in list
			instanceList.get(i).eventDestroy(); // let object perform onDeath functions
			instanceList.remove(i); // then actually get rid of it 
		}
		// else fail silently.	
	}

	// add a new effect to the global effect list. 
	public Effect effectCreate(Effect inst) {
		effectList.add(inst);

		return effectList.get(effectList.size() - 1); // returns the OBJECT just added, not its index
	}

	public void effectDestroy(Effect inst) { 
		int i = effectList.indexOf(inst);

		if (i != -1) { // instance does in fact exist in list
			effectList.remove(i);
		}
		// else fail silently.	
	}

	public void checkCollisions() { 
		PhysicsObject inst1; 
		PhysicsObject inst2;

		// this isn't the most efficient method, but it's easiest to implement
		// (based on code I wrote at a summer camp in 2013)
		for (int i = 0; i < instanceList.size(); i++) {
			for (int j = i+1; j < instanceList.size(); j++) { // only check instances above the current one - otherwise we'd repeat checks
				if (j < instanceList.size()) { // make sure we're not out  of bounds
					
					inst1 = instanceList.get(j);
					inst2 = instanceList.get(i);

					// is the first instance's bounding box intersecting with the second one?
					if (inst1.getBbox().getIsIntersecting(inst2.getBbox())) {			  
						
						// mutual collision 
						inst1.eventCollision(inst2);
						inst2.eventCollision(inst1);

						// should I only be firing one collision event at a time?
						// unforeseen sideeffects, e.g. inst1 deletes inst2 before inst2 can process?
						// -> nullpointer exception?
					}
				}
			}
		}
	}

	// check to see if a bounded area (represented by a Rect) is occupied.
	// slow function - loops through every single instance in room. Use sparingly.
	// returns a PhysicsObject, but you can still use it like a boolean:
	// instead of if (checkIsRectFree) {blah;}, use if (checkIsRectFree == null) {blah;}
	// if area is occupied, function will return the instance that occupied it.
	public PhysicsObject checkIsAreaFree(Rect area) { 
		PhysicsObject inst; 

		// this isn't the most efficient method, but it's easiest to implement
		// (based on code I wrote at a summer camp in 2013)
		for (int i = 0; i < instanceList.size(); i++) {
					
			inst = instanceList.get(i);

			// is the first instance's bounding box intersecting with the second one?
			if (inst.getBbox().getIsIntersecting(area)) {			  

				// instance is occupying area, so return it
				return inst;
			}
		}

		return null; // no occupation
	}

	// This is a slow check. Don't use it too often. 
	// Loop through all game instances looking for one with a specific 'instanceLabel.' (e.g. a tag.) Returns that instance.
	// Useful for: 
	// 		Matching GUIs to specific instances (e.g. display only player health) 
	// 		Find an instance to target based on its label name 
	public PhysicsObject findInstanceWithLabel(String label) { 
		PhysicsObject inst; 

		for (int i = 0; i < instanceList.size(); i++) {
					
			inst = instanceList.get(i);

			if (inst.instanceLabel.equals(label)) {			  

				// if instanceLabel of inst matches passed label, we've found our object!
				return inst;
			}
		}

		return null; // no instance found with the desired label
	}

	public int getWorldWidth() { 
		return worldWidth; 
	}

	public int getWorldHeight() { 
		return worldHeight; 
	}

	// I discourage you from using these. Only if a window resize happens.
	public void setWorldWidth(int w) { 
		this.worldWidth = w; 
	}

	public void setWorldHeight(int h) { 
		this.worldHeight = h; 
	}

	public boolean getIsGameLost() {
		return isGameLost; 
	}
}