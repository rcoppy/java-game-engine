package game.controller; 

// this idea came from:
// http://stackoverflow.com/questions/6887296/how-to-make-an-image-move-while-listening-to-a-keypress-in-java/6887354#6887354

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

import game.model.GameWorld; 
import game.view.GameViewPanel; 

public class KeyActionController extends AbstractAction {
   private GameViewPanel view;
   private String command;

   public KeyActionController(GameViewPanel v, String c) {
      view = v;
      command = c;
   }

   public void actionPerformed(ActionEvent e) {
      
      // controls for player 1 
      
      // stutter workaround by detecting when keys are released

      // keys are pressed down - initialize movement
      if(command.equals("UP_PRESSED")) {
         view.world.input.moveUp(0); 
      } else if(command.equals("UP_RELEASED")) {
         //System.out.println("up key released");
         view.world.input.stopUp(0); 
      }

      if(command.equals("DOWN_PRESSED")) {
         view.world.input.moveDown(0); 
      } else if(command.equals("DOWN_RELEASED")) {
         view.world.input.stopDown(0); 
      }

      if(command.equals("LEFT_PRESSED")) {
         view.world.input.moveLeft(0); 
      } else if(command.equals("LEFT_RELEASED")) {
         view.world.input.stopLeft(0); 
      }

      if(command.equals("RIGHT_PRESSED")) {
         view.world.input.moveRight(0); 
      } else if(command.equals("RIGHT_RELEASED")) {
         view.world.input.stopRight(0); 
      }   

      if(command.equals("ACTION_PRESSED")) {
         view.world.input.setActionKey(true); 
      } else if(command.equals("ACTION_RELEASED")) {
         view.world.input.setActionKey(false); 
      }   

      if(command.equals("SHOOT_PRESSED")) {
         view.world.input.setShootKey(true); 
      } else if(command.equals("SHOOT_RELEASED")) {
         view.world.input.setShootKey(false); 

      }   

      // debug
      System.out.println(command); 


   }
}