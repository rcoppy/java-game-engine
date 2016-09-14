/* 

	Wall object. Doesn't do much. Just sits there.

	2/8/15
	Alex Rupp-Coppi

*/

package game.model; 

public class Wall extends PhysicsObject { 

	public Wall(int x, int y, GameWorld world) {
		super(x,y,world);
		setAnimation(new Animation(Sprites.wallBrick, 1), true, 0,0,1.0,1.0); // xoffset of 0, yoffset of 0, xscale of 1, yscale of 1 
		objectIndex = "Wall";
	}

}