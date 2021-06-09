package it.unipv.po.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;

import it.unipv.po.model.game.cards.Card;
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

				gui.gameGraphics();
				menu.singlePlayer();
				deck();
				send();
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

	private void deck() {

		HashMap<Card, JButton> map = new HashMap<Card, JButton>();

		int x = 30;
		int y = 309;

		for (Card s : menu.getPlayer().getDeck()) {

			map.put(s, gui.cardsBuilder(x, y, s.toString()));

			ActionListener a = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					if (menu.getPlayer().isCardSelected()) {

						menu.getPlayer().getCardsListTemp().remove(s);
						menu.getPlayer().setCardSelected();
						gui.cardSelected(true, map.get(s));
						System.out.println(s.toString());
					}

					else {

						menu.getPlayer().getCardsListTemp().add(s);
						menu.getPlayer().setCardSelected();
						gui.cardSelected(false, map.get(s));
					}
				}
			};

			map.get(s).addActionListener(a);
			x += 70;
		}
	}

	private void send() {

		ActionListener send = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (menu.getScopone().playerActionMonitoring(menu.getPlayer(), menu.getScopone().getCardsOnBoard())) {

				}
			}
		};
		gui.getSend().addActionListener(send);
	}
}