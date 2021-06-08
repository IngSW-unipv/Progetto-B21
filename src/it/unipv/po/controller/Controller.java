package it.unipv.po.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import it.unipv.po.model.menu.MainMenu;
import it.unipv.po.view.ScoponeGUI;

public class Controller {

	private MainMenu menu;
	private ScoponeGUI gui;

	public Controller(MainMenu menu, ScoponeGUI gui) {
		super();
		this.menu = menu;
		this.gui = gui;

		start();
	}

	private void start() {

		ActionListener singlePlayer = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				menu.singlePlayer();
			}
		};

		ActionListener multiPlayer = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				menu.multiPlayer();
			}
		};

		ActionListener sound = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (menu.getMusic().isMusicOn()) {
					
					menu.getMusic().getHit().stop();
					menu.getMusic().setMusicOn(false);
					gui.soundRefresh(1);
				}
				
				else {
					menu.getMusic().getHit().start();
					menu.getMusic().setMusicOn(true);
					gui.soundRefresh(0);
				}
			}
		};

		gui.getMultiPlayerButton().addActionListener(multiPlayer);
		gui.getSinglePlayerButton().addActionListener(singlePlayer);
		gui.getSoundButton().addActionListener(sound);

	}
}
