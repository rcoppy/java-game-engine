/* 

	GUI container class. Instantiates all GUI components. 

	2/8/15
	Alex Rupp-Coppi

*/ 

package game.model; 

import java.awt.Graphics; 
import java.util.ArrayList; 

public class GUIContainer { 

	private GameWorld world; 
	private ArrayList<GUIComponent> componentList; 

	public GUIContainer(GameWorld world) { 
		this.world = world; 
		

		init(); 
	}

	// call init() whenever room is restarted - updates tag matching 
	public void init() {
		componentList = new ArrayList<GUIComponent>(); 
		GUIComponent playerHealthBar = componentAdd(new GUIEntityHealthBar(48,48,world,(Entity) (world.findInstanceWithLabel("Player")))); 
	}

	public void update() {
		for (int i = 0; i < componentList.size(); i ++) {
			componentList.get(i).update();  
		}
	}

	public void draw(Graphics g) {
		for (int i = 0; i < componentList.size(); i ++) {
			componentList.get(i).draw(g); 
		}
	}

	public GUIComponent componentAdd(GUIComponent c) { 
		componentList.add(c);

		return componentList.get(componentList.size() - 1); // returns object, not index 
	}

	public void componentRemove(GUIComponent c) { 
		int i = componentList.indexOf(c);

		if (i != -1) { // component does in fact exist in list
			componentList.remove(i);
		}
		// else fail silently.	
	}

}