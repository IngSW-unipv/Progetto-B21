package it.unipv.ingsw.client.view.tester;

import it.unipv.ingsw.client.controller.Controller;
import it.unipv.ingsw.client.controller.ScoponeGame;
import it.unipv.ingsw.client.view.ScoponeGUI;
public class TesterGUI {

	
	public static void main(String[] args) {

		ScoponeGame menu = new ScoponeGame();
		ScoponeGUI gui = ScoponeGUI.getGUI();

		Controller controller = new Controller(menu, gui);
		menu.setController(controller);
		gui.setVisible(true);
	}
}
