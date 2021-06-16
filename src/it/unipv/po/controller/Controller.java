package it.unipv.po.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import it.unipv.po.model.game.ScoponeGame;
import it.unipv.po.model.game.cards.Card;
import it.unipv.po.model.game.player.Player;
import it.unipv.po.model.game.player.types.HumanPlayer;
import it.unipv.po.model.menu.MainMenu;
import it.unipv.po.view.ScoponeGUI;

public class Controller {

	private MainMenu menu;
	private ScoponeGUI gui;
	private ScoponeGame game;
	private Player human;
	private HashMap<Card, JButton> deck;
	private HashMap<Card, JButton> cardsOnBoard;

//___________________CONTROLLER______________________
	public Controller(MainMenu menu, ScoponeGUI gui) {
		super();
		this.menu = menu;
		this.gui = gui;

		start();
	}

//___________________METODI__________________________
	private void start() {

		TextListener nickname = new TextListener() {

			@Override
			public void textValueChanged(TextEvent e) {

				menu.setTxt(gui.getMainMenu().getNickname().getText());
			}
		};

		gui.getMainMenu().getNickname().addTextListener(nickname);

		ActionListener singlePlayer = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (menu.getTxt() == null) {
					gui.getMainMenu().getNickname().setText("INSERISCILO!!!!");
				} else
					singlePlayer();
			}
		};

		gui.getMainMenu().getSingle().addActionListener(singlePlayer);

		ActionListener multiPlayer = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (menu.getTxt() == null) {
					gui.getMainMenu().getNickname().setText("INSERISCILO!!!!");
				} else
					menu.multiPlayer();
			}
		};

		gui.getMainMenu().getMulti().addActionListener(multiPlayer);

		ActionListener sound = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (menu.getMusic().isMusicOn()) {

					menu.getMusic().getHit().stop();
					menu.getMusic().setMusicOn(false);
					gui.getSound().soundRefresh(1);
				}

				else {
					menu.getMusic().getHit().start();
					menu.getMusic().setMusicOn(true);
					gui.getSound().soundRefresh(0);
				}
			}
		};

		gui.getSound().addActionListener(sound);

	}

	private void singlePlayer() {

		this.game = menu.singlePlayer();
		this.human = game.getHuman().getP();
		gui.getMainMenu().setVisible(false);
		gui.game();
		send();
		deck(30, 309);
		
	}

	private void send() {

		ActionListener send = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (game.getCardsOnBoard().size() == 0) {

					game.getHuman().interrupt();
					cardPlayed();
					gameAdvisor(human.getNickname() + " gioca " + ((HumanPlayer) human).getCardPlayed());
					human.setCardSelected();
					game.setHavePlayed(false);
				}

				else {

					game.getHuman().interrupt();

					if (game.isHavePlayed()) {
						cardPlayed();
						gameAdvisor(human.getNickname() + " gioca " + ((HumanPlayer) human).getCardPlayed());
						human.setCardSelected();
					}

					else {
						gameAdvisor("ERRORE: PRESA POSSIBILE");
					}
				}
			}
		};
		gui.getGame().getSend().addActionListener(send);
	}

	private void deck(int x, int y) {

		this.deck = new HashMap<Card, JButton>();

		for (Card s : human.getDeck()) {

			deck.put(s, gui.getGame().cardsBuilder(x, y, s.toString()));

			ActionListener a = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					if (s.isSelected() == false && human.isCardSelected() == false) {

						human.getCardsListTemp().add(s);
						human.setCardSelected();
						s.setSelected();
						gui.getGame().cardSelected(true, deck.get(s));
						((HumanPlayer) human).setCardPlayed(s);
						System.out.println(s.toString());
					}

					else if (s.isSelected() == true && human.isCardSelected() == true){

						human.getCardsListTemp().remove(s);
						human.setCardSelected();
						s.setSelected();
						((HumanPlayer) human).setCardPlayed(null);
						gui.getGame().cardSelected(false, deck.get(s));
					}
				}
			};

			deck.get(s).addActionListener(a);
			x += 70;
		}
	}

	private synchronized void cardPlayed() {

		gui.getGame().cardPlayed(deck.get(((HumanPlayer) human).getCardPlayed()));
	}

	private synchronized void gameAdvisor(String txt) {

		gui.getGame().getGameAdvisor().setText(txt);
	}

	private void updateBoard() {
		
		ArrayList<Card> temp = new ArrayList<Card>();
		temp.addAll(game.getCardsOnBoard());
		
		TimerTask task = new  TimerTask() {
			
			@Override
			public void run() {

				cardsOnBoard(temp, 39, 100);
			}
		};
		
		Timer timer = new Timer();
		timer.schedule(task, Date.from(Instant.now()), 1);
	}
	
	private void cardsOnBoard(ArrayList<Card> temp, int x, int y) {

		this.cardsOnBoard = new HashMap<Card, JButton>();
		
		for (Card s : temp) {
			
			cardsOnBoard.put(s, gui.getGame().cardsBuilder(x, y, s.toString()));

			ActionListener a = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
				}
			};

			cardsOnBoard.get(s).addActionListener(a);

			x += 70;
		}
	}
}