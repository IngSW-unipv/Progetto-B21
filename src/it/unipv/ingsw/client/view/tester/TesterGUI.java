package it.unipv.ingsw.client.view.tester;

import it.unipv.ingsw.client.controller.Controller;
import it.unipv.ingsw.client.controller.menu.Main;
import it.unipv.ingsw.client.view.ScoponeGUI;
public class TesterGUI {

	public static void main(String[] args) {

		Main menu = new Main();
		ScoponeGUI gui = new ScoponeGUI();

		Controller controller = new Controller(menu, gui);
		menu.setController(controller);
		gui.setVisible(true);
	}
}
