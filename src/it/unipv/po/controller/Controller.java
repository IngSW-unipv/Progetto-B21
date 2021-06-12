package it.unipv.po.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;

import it.unipv.po.model.game.cards.Card;
import it.unipv.po.model.game.player.types.HumanPlayer;
import it.unipv.po.model.menu.MainMenu;
import it.unipv.po.view.ScoponeGUI;

public class Controller {

	private MainMenu menu;
	private ScoponeGUI gui;
	private HashMap<Card, JButton> map;

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
				deck(30, 309);
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

	private void deck(int x, int y) {

		this.map = new HashMap<Card, JButton>();

		for (Card s : menu.getScopone().getHuman().getP().getDeck()) {

			map.put(s, gui.cardsBuilder(x, y, s.toString()));

			ActionListener a = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					if (menu.getScopone().getHuman().getP().isCardSelected()) {

						menu.getScopone().getHuman().getP().getCardsListTemp().add(s);
						menu.getScopone().getHuman().getP().setCardSelected();
						gui.cardSelected(true, map.get(s));
						menu.getScopone().getHuman().getP().setCardPlayed(s);
						System.out.println(s.toString());
					}

					else {

						menu.getScopone().getHuman().getP().getCardsListTemp().remove(s);
						menu.getScopone().getHuman().getP().setCardSelected();
						menu.getScopone().getHuman().getP().setCardPlayed(null);
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

				menu.getScopone().getHuman().interrupt();
				cardPlayed();
				gameAdvisor(menu.getScopone().getHuman().getP().getNickname() + " gioca " + menu.getScopone().getHuman().getP().getCardPlayed());
			}
		};
		gui.getSend().addActionListener(send);
	}

	private void cardPlayed() {

		gui.cardPlayed(map.get(((HumanPlayer) menu.getScopone().getHuman().getP()).getCardPlayed()));
	}
	
	private synchronized void gameAdvisor(String txt) {
		
		gui.getChat().setText(txt);
	}
}