/*

	Room generating class. Randomizes everything. 

	2/11/15
	Alex Rupp-Coppi
	2:30 AM 

*/

package game.model; 

import java.util.Random; 
import java.lang.Math; 
import java.awt.image.BufferedImage; 

public class RoomGenerator { 

	private GameWorld world; 
	private int wallWidth; 
	public int enemyCount;
	//private Player player; 

	RoomGenerator(GameWorld world) {
		this.world = world;

		wallWidth = Sprites.wallBrick[0].getWidth(); 
		enemyCount = 0;
		//player = (Player) (world.instanceFindWithTag("Player"));
	}

	public void generate(int dif) { // for difficulty 

		enemyCount = 0; // reset

		Random r = new Random(); 

		/* Algorithm: 

		4. Place player while room still empty.

		1. Generate walls. 
		2. Place chests.
		3. Spawn enemies.
			Number of enemies increases exponentially relative to difficulty.
			Speed of enemies increases.
			Health of enemies increases. 
			Attack of enemies does not increase. 
		

		Place everything on a grid. Room dimensions divided by wall dimensions.

		*/

		// define cell dimensions of world
		int width = world.getWorldWidth()/wallWidth - 2; //  subtracting 2 because there are two boundary walls 
		int height = world.getWorldHeight()/wallWidth - 2; 


		// spawn player while room still empty, in the center of a cell
		Player player = (Player) (world.instanceCreate(new Player((r.nextInt(width-1)+1)*wallWidth+wallWidth/2,(r.nextInt(height-1)+1)*wallWidth+wallWidth/2,world,0))); 

		// spawn border walls
		spawnBorderWalls(r);

		// spawn walls
		for (int i = 0; i < r.nextInt(10)+7; i ++) {
			spawnLWalls(r.nextInt(width)+1,r.nextInt(height)+1,r.nextInt(width/6)+2,r.nextInt(height/6)+2, r.nextInt(2));
		}

		// spawn chests
		for (int i = 0; i < r.nextInt(4)+3; i++) {
			placeChest(r.nextInt(width)+1,r.nextInt(height)+1,r.nextInt(2));
		}

		// spawn enemies
		for (int i = 0; i < 4+3*(dif/2); i++) {
			spawnEnemy(r.nextInt(width)+1,r.nextInt(height)+1,dif,player); 
		}

	}

	// x,y,w,h are in units of cells, not pixels
	public void spawnLWalls(int x, int y, int w, int h, int slope) {
		if (world.checkIsAreaFree(new Rect(x*wallWidth, y*wallWidth, w*wallWidth, h*wallWidth)) == null) {
			
			// make L-shaped clusters of walls; slope = 1 or 0; flips cluster.

			// make a column
			for (int i = 0; i < h; i ++) {
				world.instanceCreate(new Wall(x*wallWidth,(y+i)*wallWidth,world));
			}

			// make a row
			for (int i = 1; i < w; i ++) { // i = 1 prevents overlap with i = 0 column
				world.instanceCreate(new Wall((x+i)*wallWidth,(y+(h*slope))*wallWidth,world)); 
			}
		}
	} 

	// place a single chest, big or small, in a free area.
	public void placeChest(int x, int y, int chestType) {
		int w; 
		int h; 

		if (chestType == 0) { // large chest
			w = Sprites.chestLarge[0].getWidth(); 
			h = Sprites.chestLarge[0].getHeight(); 
		}
		else {
			w = Sprites.chestSmall[0].getWidth(); 
			h = Sprites.chestSmall[0].getHeight(); 
		}
			
		if (world.checkIsAreaFree(new Rect(x*wallWidth,y*wallWidth,w,h)) == null) {
			if (chestType == 0) {
				world.instanceCreate(new ChestLarge(x*wallWidth,y*wallWidth,world));
			}
			else { 
				world.instanceCreate(new ChestSmall(x*wallWidth,y*wallWidth,world));
			}
		}
	}

	// spawn enemies
	public void spawnEnemy(int x, int y, int dif, Player player) { // dif for difficulty 
		if (world.checkIsAreaFree(new Rect(x*wallWidth,y*wallWidth,wallWidth,wallWidth)) == null) {
			Baddy b = (Baddy) (world.instanceCreate(new Baddy(x*wallWidth+wallWidth/2,y*wallWidth+wallWidth/2,world,player))); // new baddy targeting player, spawned in center of a cell
			b.maxHealth = (int) (b.maxHealth + 0.2*(Math.pow(b.maxHealth,dif/2))); // y = 5 + 0.2 * 5^(x/2); exponential function 
			b.health = b.maxHealth; 
			b.speed = (int) (b.speed + 0.2*(Math.pow(b.speed,dif/2)));

			enemyCount ++; 
		}
	}

	public void spawnBorderWalls(Random r) {

		for (int i = 0; i < world.getWorldWidth()/wallWidth+1; i ++) { // account for screen sizes not divisible by wallWidth
			world.instanceCreate(new Wall(i*wallWidth,0,world)); // top row
			world.instanceCreate(new Wall(i*wallWidth,world.getWorldHeight()-wallWidth,world)); // bottom row 
		
			// make random gaps in the walls by iterating forwards
			if (r.nextInt(5) == 0) {
				i++; 
			}
			if (r.nextInt(5) == 1) {
				i += 2; 
			}

		}

		for (int i = 0; i < (world.getWorldHeight()-2*wallWidth)/wallWidth+1; i ++) { // vertical columns, accounting for the two rows already created and non-multiple screensize
			world.instanceCreate(new Wall(0,(1+i)*wallWidth,world)); // left column
			world.instanceCreate(new Wall(world.getWorldWidth()-wallWidth,(1+i)*wallWidth,world)); // right column 
			
			// make random gaps in the walls by iterating forwards
			if (r.nextInt(5) == 0) {
				i++; 
			}
			if (r.nextInt(5) == 1) {
				i += 2; 
			}

		}
	}

}