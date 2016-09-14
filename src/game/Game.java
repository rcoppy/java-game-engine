/* 

	Modified from RectangleMover. Handles less.

	2/6/15.
	Alex Rupp-Coppi.

*/


// In-class lab: 
//
// Take this program one step further. When I hit the 
// w key, I want the amount the rectangle will move by
// each time I hit an arrow key to increase by one.
// When I hit the s key, I want the amount the rectangle
// moves to decrease by 1. Don't let the movement values 
// ever get greater than 20, or less than 1.

package game; 

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

import game.model.*; 
import game.view.*; 
import game.controller.*; 

public class Game implements ActionListener {
	private JFrame frame;
	private JButton resetButton;
	private GameViewPanel gameView;

	public static void main(String[] args) {
		new Game();
	}

	// from stack overflow:
	// http://stackoverflow.com/questions/17984912/java-key-bindings-not-working
	// also java tutorial:
	// http://docs.oracle.com/javase/tutorial/uiswing/misc/keybinding/html#

	// key event on numpad is different!
	// see field summary:
	// http://docs.oracle.com/javase/6/docs/api/java/awt/event/KeyEvent.html
	public void setupInputMaps(GameViewPanel gameView) {
		/* you get key stutters with this method.
		// upkey
		gameView.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,0),"upkey");
		gameView.getActionMap().put("upkey", new KeyActionController(gameView,"UP"));

		// downkey
		gameView.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,0),"downkey");
		gameView.getActionMap().put("downkey", new KeyActionController(gameView,"DOWN"));

		// leftkey
		gameView.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,0),"leftkey");
		gameView.getActionMap().put("leftkey", new KeyActionController(gameView,"LEFT"));

		// rightkey
		gameView.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,0),"rightkey");
		gameView.getActionMap().put("rightkey", new KeyActionController(gameView,"RIGHT"));
		*/ 

		// from: http://docs.oracle.com/javase/tutorial/uiswing/misc/keybinding.html#eg
		
		// left key
		gameView.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "leftpressed"); 
		gameView.getInputMap().put(KeyStroke.getKeyStroke("released LEFT"), "leftreleased");
		gameView.getActionMap().put("leftpressed", new KeyActionController(gameView, "LEFT_PRESSED"));
		gameView.getActionMap().put("leftreleased", new KeyActionController(gameView, "LEFT_RELEASED"));		
		//KeyActionController is a javax.swing.Action objects

		// right key
		gameView.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "rightpressed");
		gameView.getInputMap().put(KeyStroke.getKeyStroke("released RIGHT"), "rightreleased");
		gameView.getActionMap().put("rightpressed", new KeyActionController(gameView, "RIGHT_PRESSED"));
		gameView.getActionMap().put("rightreleased", new KeyActionController(gameView, "RIGHT_RELEASED"));	

		// up key
		gameView.getInputMap().put(KeyStroke.getKeyStroke("UP"), "uppressed");
		gameView.getInputMap().put(KeyStroke.getKeyStroke("released UP"), "upreleased");
		gameView.getActionMap().put("uppressed", new KeyActionController(gameView, "UP_PRESSED"));
		gameView.getActionMap().put("upreleased", new KeyActionController(gameView, "UP_RELEASED"));	

		// down key
		gameView.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "downpressed");
		gameView.getInputMap().put(KeyStroke.getKeyStroke("released DOWN"), "downreleased");
		gameView.getActionMap().put("downpressed", new KeyActionController(gameView, "DOWN_PRESSED"));
		gameView.getActionMap().put("downreleased", new KeyActionController(gameView, "DOWN_RELEASED"));	

		// action key
		gameView.getInputMap().put(KeyStroke.getKeyStroke("X"), "actionpressed");
		gameView.getInputMap().put(KeyStroke.getKeyStroke("released X"), "actionreleased");
		gameView.getActionMap().put("actionpressed", new KeyActionController(gameView, "ACTION_PRESSED"));
		gameView.getActionMap().put("actionreleased", new KeyActionController(gameView, "ACTION_RELEASED"));	

		// shoot key
		gameView.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "shootpressed");
		gameView.getInputMap().put(KeyStroke.getKeyStroke("released SPACE"), "shootreleased");
		gameView.getActionMap().put("shootpressed", new KeyActionController(gameView, "SHOOT_PRESSED"));
		gameView.getActionMap().put("shootreleased", new KeyActionController(gameView, "SHOOT_RELEASED"));	
	}

	public void actionPerformed(ActionEvent e) {
		// since there is only one button
		// that could trigger a call to actionPerformed
		// we need not complete any checks in this program
		gameView.reset();

		// clicking on the button takes the focus
		// away from the panel; request the focus
		// for the panel again so we can use arrow keys
		gameView.requestFocus();
	}

	public Game() {
		gameView = new GameViewPanel();
		//resetButton = new JButton("Reiniciar");
		setupInputMaps(gameView);

		// allow the gameView panel to gain focus
		gameView.setFocusable(true);

		//resetButton.addActionListener(this);
		frame = new JFrame("Just... Keep... Shooting!!!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//frame.getContentPane().add(resetButton, BorderLayout.NORTH);
		frame.getContentPane().add(gameView, BorderLayout.CENTER);

		frame.pack();
		frame.setVisible(true);

		// have the gameView request focus when the program
		// starts so you can use the arrow keys
		gameView.requestFocus();
	}
}