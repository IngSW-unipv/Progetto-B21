package it.unipv.po.view.tester;

import it.unipv.po.controller.Controller;
import it.unipv.po.controller.menu.Main;
import it.unipv.po.view.ScoponeGUI;
public class TesterGUI {

	public static void main(String[] args) {

		Main menu = new Main();
		ScoponeGUI gui = new ScoponeGUI();

		Controller controller = new Controller(menu, gui);
		menu.setController(controller);
		gui.setVisible(true);
	}

}
