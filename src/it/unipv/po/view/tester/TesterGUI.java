package it.unipv.po.view.tester;

import it.unipv.po.controller.Controller;
import it.unipv.po.model.menu.MainMenu;
import it.unipv.po.view.ScoponeGUI;
public class TesterGUI {

	public static void main(String[] args) {

		MainMenu menu = new MainMenu();
		ScoponeGUI gui = new ScoponeGUI();

		@SuppressWarnings("unused")
		Controller controller = new Controller(menu, gui);
		gui.setVisible(true);
	}

}
