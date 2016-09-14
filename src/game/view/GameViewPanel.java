/* 

	Modified from PicturePanel. Handles less. 

	2/6/15.
	Alex Rupp-Coppi

*/


package game.view; 

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.lang.Math;
import java.util.ArrayList; 

import game.model.*; 
import game.controller.*; 

/* 
	TODO: 

	Implement deltaTime
		stop motion/animation stuttering

	Implement collision layers/groups?
	Create a GameObject class that is the parent of PhysicsObject?
	
	PhysicsObject: 
		taking a non-zero origin into account when drawing/scaling

*/ 

public class GameViewPanel extends JPanel {
	
	private Color backgroundcolor;
	private int dimx, dimy; // window width, height
	
	public Timer timer;
	public GameWorld world; 

	public final int fps = 60;  

	public GameViewPanel(Color c) {

		generalSetup(backgroundcolor);

		// NEEDS to be called after GameWorld is initialized (inside of generalSetup), b/c otherwise methods called from within setup timer try to access variables that haven't initialized yet.
		// NP reference exceptions await if you call it after.
		setupTimer();
	}

	public GameViewPanel() {
		generalSetup(Color.white);

		// NEEDS to be called after GameWorld is initialized (inside of generalSetup), b/c otherwise methods called from within setup timer try to access variables that haven't initialized yet.
		// NP reference exceptions await if you call it after.
		setupTimer();
	}

	private void generalSetup(Color c) {
		backgroundcolor = c;
		
		// set resolution to 16:9
		dimx = 960; //1366;
		dimy = 540; //768;
		setPreferredSize(new Dimension(dimx,dimy));
		

		// since frame.pack() has yet to be called,
		// getWidth() and getHeight() will have
		// default values of 0 at this point
		// so I set them manually  

		world = new GameWorld(dimx,dimy); // all the logic, instances are contained here - super important.

	}

	private void setupTimer() {
		int delay = 1000/fps; // 17 milliseconds/frame, or just under 60 FPS
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timerActions();
				timer.restart();
			}
		};
		timer = new Timer(delay, taskPerformer);
		timer.setInitialDelay(delay);
		timer.start();
	}

	// using a dynamic timestep - didn't have enough time to implement
	/*// based on: http://gamedev.stackexchange.com/questions/56956/how-do-i-make-a-game-tick-method
	private void startGameTick() { 
		double time_passed = 0;
	    double delta_time = 0;

	    double time_per_timestep = 1000.0/fps; 

	    boolean shouldRun = true; 

	    while (shouldRun) { // keep running

	        // update game logic based on time passed
	        updateDynamicStep(delta_time);

	        // update game logic once for every tick passed
	        while (time_passed >= time_per_timestep) {
	            updateFixedStep();
	            time_passed -= time_per_timestep;
	            // You might limit the number of iterations here (like 10)
	            // to not get stuck due to processing taking too long (and time adding up)
	        }

	        // draw screen content
	        drawStuff(delta_time);

	        // update timing
	        delta_time = getTimePassedAndResetTimer();
	        time_passed += delta_time;
	    }
	}

	// dynamic step update
	public void updateDynamicStep(double delta_time) {

	}*/

	// EVENT LOOP
	public void timerActions() {
		
		repaint();

		world.update(); 

		if (world.getIsGameLost()) {
			reset();
		}
	}

	public void reset() {
		generalSetup(backgroundcolor);
		repaint();
	}

	// this is overriding the default method
	// that exists for a JPanel
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// to treat a Graphics object like a 2D Graphics object:
		// Graphics2D g2 = (Graphics2D) g;

		// get the width and height of the panel your are drawing in
		// could also use dimx/dimy since those variables
		// are declared above

		// should dimx/dimy be updated, or should window width/height remain constant?
		//dimx = getWidth();
		//dimy = getHeight();

		setBackground(backgroundcolor);

		world.drawBackground(g);
		// will iterate through instance list
		world.drawInstances(g);
		world.drawEffects(g); 
		world.drawGUI(g); 

	}

	public int getWindowWidth() {
		return getWidth(); 
	}
	
	public int getWindowHeight() {
		return getHeight(); 
	}

}