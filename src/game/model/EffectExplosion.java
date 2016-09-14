/* 

	Explosion effect. Extension of Effect class.

	2/11/15
	Alex Rupp-Coppi 
	1:20 AM

*/ 

package game.model; 

public class EffectExplosion extends Effect { 

	EffectExplosion(int x, int y, GameWorld world) {
		super(x,y,Sprites.explosion[0].getWidth()/2,(int) (0.75*Sprites.explosion[0].getHeight()), 1.0, 1.0, world, new Animation(Sprites.explosion, 2));
	}

	EffectExplosion(int x, int y, double xscale, double yscale, GameWorld world) {
		super(x,y,Sprites.explosion[0].getWidth()/2,(int) (0.75*Sprites.explosion[0].getHeight()), xscale, yscale, world, new Animation(Sprites.explosion, 2));
	}

}