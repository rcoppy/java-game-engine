/* 

	Imports all game sprites statically. (Don't ever actually instantiate this class.) 
	Other objects should reference sprites stored here instead of initializing them in their individual constructors. 
	Stores all sprites (even 1-frame animations) as BufferedImage[] arrays.
	Can be referenced by other classes.
	
	Performing sprite comparisons (technically you're comparing the image array of an animation): 
		if (animation.getStrip() == Sprites.enemyBaddy) { 
			blah(); 
		}	

	<!-- This is now obsolete. --> 
		If you don't want to use an animation, set your instance sprite to BufferedImage[index] directly. 
	<-- -->

	2/4/15, Alex Rupp-Coppi.

*/ 

package game.model; 

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Sprites { 
	
	// For creating/properly formatting new sprite sheets, I suggest using GameMaker's built-in sprite editor.
	// You can export .PNG strips with no whitespace/padding between frames where to divvy up the image you just need to divide by the number of subimages.
	// Makes life simpler.

	// everything stored as a BufferedImage array, even if only length of 1 - 
	// this way you don't accidentally refer to the wrong datatype when setting a sprite


	// loadSpriteSheet() method: 
	// explicit form: give width, height, rows, and columns - need to know more information
	// enemyBaseball = loadSpriteSheet("strip_baseball_player.png",21,27,1,5);  

	// ambiguous (?) form, just provide filepath and number of subimages - if image is entirely in one row and no separation between subimages (e.g. exports from GameMaker): 
	// enemyBaseball = loadSpriteSheet("strip_baseball_player.png",5); -- 5 = number of subimages to divide into

	public static final String imgPath = System.getProperty("user.dir") + "\\assets\\img\\";

	// Websites used: 
	// http://www.spriters-resource.com/
	// http://tsgk.captainn.net/

	// characters - combination of Final Fantasy and Yoshi's Island
	public static final BufferedImage[] player = loadSpriteSheet(imgPath + "spr_player.png",1); // just one image - will still be stored as an array; 
	public static final BufferedImage[] enemyBaseballPlayer = loadSpriteSheet(imgPath + "strip_baseball_player.png",5);  
	public static final BufferedImage[] enemyBaddy = loadSpriteSheet(imgPath + "spr_baddy.png",1);
	
	// effects - combination of Legend of Zelda, Super Mario World, and Sonic the Hedgehog sprites
	public static final BufferedImage[] fireballSmall = loadSpriteSheet(imgPath + "strip_fireball_small.png",2);
	public static final BufferedImage[] explosion = loadSpriteSheet(imgPath + "strip_sonicexplosion_48x64_8_strip8.png",8);
	public static final BufferedImage[] bomb = loadSpriteSheet(imgPath + "spr_zeldabomb_13x14_1.png",1);
	
	// items - combination of Legend of Zelda sprites and Minecraft textures
	public static final BufferedImage[] chestLarge = loadSpriteSheet(imgPath + "spr_zeldachest_large_64x24_2.png",2);
	public static final BufferedImage[] chestSmall = loadSpriteSheet(imgPath + "spr_zeldachest_small_32x16_2.png",2);
	public static final BufferedImage[] heart = loadSpriteSheet(imgPath + "spr_zeldahearts_28x13_2.png",2);
	public static final BufferedImage[] key = loadSpriteSheet(imgPath + "spr_zeldakeys_28x16_2.png",2);	
	public static final BufferedImage[] keyhole = loadSpriteSheet(imgPath + "spr_keyhole.png",1);
	public static final BufferedImage[] emerald = loadSpriteSheet(imgPath + "spr_emerald.png",1);
	
	// walls (I made in 2013 for my game Dungeon Mage)
	public static final BufferedImage[] wallBrown = loadSpriteSheet(imgPath + "spr_wall.png",1);
	public static final BufferedImage[] wallCobble = loadSpriteSheet(imgPath + "spr_wall_cobble.png",1);
	public static final BufferedImage[] wallBrick = loadSpriteSheet(imgPath + "spr_wall_brick.png",1);

	// splashscreens - made using MS Word, Paint.Net, and cooltext.com
	public static final BufferedImage[] splashScreenIntro = loadSpriteSheet(imgPath + "splashscreen_intro.png",1);
	public static final BufferedImage[] splashScreenDeath = loadSpriteSheet(imgPath + "splashscreen_death.png",1);

	public Sprites() { 

	}

	// read a spritesheet, store the bits in an image array - assumes entire image is in one row, gets width by dividing total width by user inputted number of subimages
	public static BufferedImage[] loadSpriteSheet(String filepath, int imgNum) {

		// modified from stackoverflow example: 
		// http://stackoverflow.com/questions/621835/how-to-extract-part-of-this-image-in-java 

		BufferedImage bigImg; 
		BufferedImage[] sprites = new BufferedImage[0];

		try { 
			bigImg = ImageIO.read(new File(filepath));
			// The above line throws an checked IOException which must be caught.
		

			int width = bigImg.getWidth()/imgNum; 
			int height = bigImg.getHeight(); 
			int cols = imgNum;

			sprites = new BufferedImage[imgNum];

			
		    for (int j = 0; j < imgNum; j++)
		    {
		        sprites[j] = bigImg.getSubimage(
		            j * width,
		            0, //  only one row
		            width,
		            height
		        );
		    }
	    }
		catch (IOException ex) {
			System.out.println("Couldn't load "+filepath+" for spritesheet parsing. Game aborted.");
			System.exit(1);
		}

		System.out.println(filepath+" loaded with "+sprites.length+" frames"); 

		return sprites;
		
	}

	// read a spritesheet, store the bits in an image array
	public static BufferedImage[] loadSpriteSheet(String filepath, int width, int height, int rows, int cols) {

		// modified from stackoverflow example: 
		// http://stackoverflow.com/questions/621835/how-to-extract-part-of-this-image-in-java 

		BufferedImage bigImg; // for some reason using bigImg throws the error I've define this variable before in the other version of this method - here's a quickfix 
		BufferedImage[] sprites = new BufferedImage[1]; 

		try {
			bigImg = ImageIO.read(new File(filepath));
			// The above line throws an checked IOException which must be caught.
		
			sprites = new BufferedImage[rows * cols];

			for (int i = 0; i < rows; i++)
			{
			    for (int j = 0; j < cols; j++)
			    {
			        sprites[(i * cols) + j] = bigImg.getSubimage(
			            j * width,
			            i * height,
			            width,
			            height
			        );
			    }
			}

		}
		catch (IOException ex) {
			System.out.println("Couldn't load "+filepath+" for spritesheet parsing. Game aborted.");
			System.exit(1); // crash the program. Resources should must be able to load.
		}

		System.out.println(filepath+" loaded with "+sprites.length+" frames"); 

		return sprites;
		
	}
}