package it.unipv.ingsw.client.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import it.unipv.ingsw.client.controller.menu.Main;
import it.unipv.ingsw.client.model.game.Game;
import it.unipv.ingsw.client.model.game.cards.Card;
import it.unipv.ingsw.client.model.game.player.team.Team;
import it.unipv.ingsw.client.model.game.player.types.HumanPlayer;
import it.unipv.ingsw.client.model.game.player.types.Player;
import it.unipv.ingsw.client.view.ScoponeGUI;
import it.unipv.ingsw.client.view.gameElements.buttons.CardButton;

public class Controller {

	private Main menu;
	private ScoponeGUI gui;
	private Game game;
	private Player player;
	private HashMap<Card, CardButton> deck;
	private HashMap<Card, CardButton> cardsOnBoard;
	private int x;
	private Team winner;

//___________________COSTRUTTORE_______________________
	public Controller(Main menu, ScoponeGUI gui) {
		super();
		this.menu = menu;
		this.gui = gui;
		this.cardsOnBoard = new HashMap<Card, CardButton>();

		x = 80;

		startMainMenu();
	}

//__________________GETTERS&SETTERS____________________
	public synchronized int getX() {
		return x;
	}

	public synchronized void setX(int x) {
		this.x = x;
	}

	public HashMap<Card, CardButton> getCardsOnBoard() {
		return cardsOnBoard;
	}

	public HashMap<Card, CardButton> getDeck() {
		return deck;
	}

	public ScoponeGUI getGui() {
		return gui;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Main getMenu() {
		return menu;
	}

	public void setHuman(Player human) {
		this.player = human;
	}

	public Team getWinner() {
		return winner;
	}

	// _______________________METODI___________________________
	private void startMainMenu() {

		nicknameListener();
		singlePlayerListener();
		multiPlayerListener();
		soundListener();
	}

	public void startGame() {
		this.game = menu.getGame();
		this.player = menu.getPlayer();
		gui.getMainMenu().setVisible(false);
		
		
		try {
			gui.getCreaLobby().setVisible(false);
		} catch (Exception e) {
		}
		try {
		gui.getEntraLobby().setVisible(false);
		} catch (Exception e) {
		}
		gui.game();
		gui.getGame().getBack().addActionListener(backListener(gui.getGame(), gui.getMainMenu()));
		sendListener();
		deckCreator(30, 309);
	}

	public void startMultiPlayer() {

		menu.multiplayer();
		gui.getMainMenu().setVisible(false);
		try {
			gui.getCreaLobby().setVisible(false);
		} catch (Exception e) {
		}
		try {
		gui.getEntraLobby().setVisible(false);
		} catch (Exception e) {
		}
		gui.multiPlayer();
		creaLobbyListener();
		entraLobbyListener();
		gui.getMultiPlayer().getBack().addActionListener(backListener(gui.getMultiPlayer(), gui.getMainMenu()));
	}

	public void startCreaLobby() {

		gui.getMultiPlayer().setVisible(false);
		gui.creaLobby();
		creaLobbyStartListener();
		makeLobbyListener();
		creaLobbyNameListener();
		gui.getCreaLobby().getAdvisor().setText("questa � una LOBBY"); // schermata da cambiare
		gui.getCreaLobby().getBack().addActionListener(backListener(gui.getCreaLobby(), gui.getMultiPlayer()));
	}

	public void startEntraLobby() {

		gui.getMultiPlayer().setVisible(false);
		gui.entraLobby();
		entraLobbyNameListener();
		joinLobbyListener();
		gui.getEntraLobby().getBack().addActionListener(backListener(gui.getCreaLobby(), gui.getMultiPlayer()));
	}

	private void nicknameListener() {

		TextListener nickname = new TextListener() {

			@Override
			public void textValueChanged(TextEvent e) {

				menu.setNickname(gui.getMainMenu().getNickname().getText());
			}
		};

		gui.getMainMenu().getNickname().addTextListener(nickname);
	}

	private void creaLobbyNameListener() {

		TextListener lobby = new TextListener() {

			@Override
			public void textValueChanged(TextEvent e) {

				menu.setNomeLobby(gui.getCreaLobby().getNomeLobby().getText());
			}
		};

		gui.getCreaLobby().getNomeLobby().addTextListener(lobby);
	}
	
	private void entraLobbyNameListener() {

		TextListener lobby = new TextListener() {

			@Override
			public void textValueChanged(TextEvent e) {

				menu.setNomeLobby(gui.getEntraLobby().getNomeLobby().getText());
			}
		};

		gui.getEntraLobby().getNomeLobby().addTextListener(lobby);
	}

	private void singlePlayerListener() {

		ActionListener singlePlayer = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (menu.getNickname() == null || menu.getNickname().length() == 0) {
					// gui.getMainMenu().getNickname().setText("INSERISCILO!!!!");
					JOptionPane.showMessageDialog(gui.getMainMenu(),
							"Specificare il proprio username prima di continuare!", "Attenzione",
							JOptionPane.WARNING_MESSAGE);
				} else {
					menu.singleplayer();
					startGame();
				}
			}
		};

		gui.getMainMenu().getSingle().addActionListener(singlePlayer);
	}

	private void multiPlayerListener() {

		ActionListener multiPlayer = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (menu.getNickname() == null || menu.getNickname().length() == 0) {
					// gui.getMainMenu().getNickname().setText("INSERISCILO!!!!");
					JOptionPane.showMessageDialog(gui.getMainMenu(),
							"Specificare il proprio username prima di continuare!", "Attenzione",
							JOptionPane.WARNING_MESSAGE);
				} else
					startMultiPlayer();
			}
		};

		gui.getMainMenu().getMulti().addActionListener(multiPlayer);
	}

	private void creaLobbyListener() {

		ActionListener creaLobby = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				startCreaLobby();
			}
		};

		gui.getMultiPlayer().getCreaLobby().addActionListener(creaLobby);
	}

	private void creaLobbyStartListener() {

		ActionListener start = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (menu.getNomeLobby() == null || menu.getNomeLobby().length() == 0) {
					JOptionPane.showMessageDialog(gui.getMainMenu(),
							"Specificare il nome della lobby prima di continuare!", "Attenzione",
							JOptionPane.WARNING_MESSAGE);
				} else {
					menu.getClient().startGame();
				}
			}
		};

		gui.getCreaLobby().getStart().addActionListener(start);
	}

	private void makeLobbyListener() {

		ActionListener makeLobby = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (menu.getNomeLobby() == null || menu.getNomeLobby().length() == 0) {
					JOptionPane.showMessageDialog(gui.getMainMenu(),
							"Specificare il nome della lobby prima di continuare!", "Attenzione",
							JOptionPane.WARNING_MESSAGE);
				} else
					try {
						if (!menu.creaLobby()) {
							JOptionPane.showMessageDialog(gui.getMainMenu(), "Nome della lobby gi� esistente",
									"Attenzione", JOptionPane.WARNING_MESSAGE);
						}
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
			}
		};

		gui.getCreaLobby().getCrea().addActionListener(makeLobby);
	}

	private void joinLobbyListener() {

		ActionListener joinLobby = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (menu.getNomeLobby() == null || menu.getNomeLobby().length() == 0) {
					JOptionPane.showMessageDialog(gui.getMainMenu(),
							"Specificare il nome della lobby prima di continuare!", "Attenzione",
							JOptionPane.WARNING_MESSAGE);
			}
				else {
					
					menu.getClient().joinLobby(menu.getNomeLobby());
				}
		}
		
	};
		gui.getEntraLobby().getCrea().addActionListener(joinLobby);
	}

	private void entraLobbyListener() {

		ActionListener entraLobby = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				startEntraLobby();
			}
		};

		gui.getMultiPlayer().getEntraLobby().addActionListener(entraLobby);
	}

	private void soundListener() {

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

	private ActionListener backListener(JLabel current, JLabel previus) {

		ActionListener back = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				current.setVisible(false);
				previus.add(gui.getSound());
				previus.setVisible(true);
			}
		};

		return back;
	}

	public void sendListener() {

		ActionListener send = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (player.getCardsListTemp().size() != 0 && menu.getPlayerThread().getClick() == 0) {

					menu.getPlayerThread().interrupt();
					player.setCardSelected();
					if (((HumanPlayer) player).hasPlayed())
						menu.getPlayerThread().setClick(1);
				}
			}
		};
		gui.getGame().getSend().addActionListener(send);
	}

	public void deckCreator(int x, int y) {

		this.deck = new HashMap<Card, CardButton>();

		for (Card s : player.getDeck()) {

			deck.put(s, gui.getGame().cardsBuilder(x, y, s.toString()));
			deck.get(s).setVisible(true);
			if (s.isSelected()) {
				s.setSelected();
			}
			deck.get(s).repaint();

			ActionListener a = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					if (s.isSelected() == false && player.isCardSelected() == false) {

						player.getCardsListTemp().add(s);
						player.setCardSelected();
						s.setSelected();
						deck.get(s).cardSelected(true);
						try {
							menu.getSound().playMusic("card_flip.wav");
						} catch (Exception ex) {} 
						((HumanPlayer) player).setCardPlayed(s);
					}

					else if (s.isSelected() == true && player.isCardSelected() == true) {

						player.getCardsListTemp().remove(s);
						player.setCardSelected();
						s.setSelected();
						((HumanPlayer) player).setCardPlayed(null);
						try {
							menu.getSound().playMusic("card_flip.wav");
						} catch (Exception ex) {}
						deck.get(s).cardSelected(false);
					}
				}
			};

			deck.get(s).addActionListener(a);
			x += 70;
		}
	}

	public boolean verifyGame() {

		int a = game.getTeams().get(0).getTotalPoints();
		int b = game.getTeams().get(1).getTotalPoints();

		if (a > b && a >= 21) {
			winner = game.getTeams().get(0);

			return true;
		} else if (b > a && b >= 21) {

			winner = game.getTeams().get(1);
			return true;
		} else {

			return false;
		}
	}

	public void restartGame() {

		cardsOnBoard.clear();
		setX(80);
		game.start();
		gui.game().repaint();
		sendListener();
		deckCreator(30, 309);
	}

	public synchronized void cardsOnBoardCreator(ArrayList<Card> temp, int x, int y) {

		for (Card s : temp) {

			if (cardsOnBoard.get(s) == null) {

				if (s.isSelected())
					s.setSelected();

				cardsOnBoard.put(s, gui.getGame().cardsBuilder(x, y, s.toString()));
				cardsOnBoard.get(s).setVisible(true);
				cardsOnBoard.get(s).repaint();

				ActionListener a = new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						if (player.isCardSelected()) {
							selectError();
						}

						else {

							if (s.isSelected() == false) {

								player.getCardsListTemp().add(s);
								s.setSelected();
								try {
									menu.getSound().playMusic("card_flip.wav");
								} catch (Exception ex) {}
								cardsOnBoard.get(s).cardSelected(s.isSelected());
							}

							else {

								player.getCardsListTemp().remove(s);
								s.setSelected();
								try {
									menu.getSound().playMusic("card_flip.wav");
								} catch (Exception ex) {}
								cardsOnBoard.get(s).cardSelected(s.isSelected());
							}
						}
					}
				};

				cardsOnBoard.get(s).addActionListener(a);
				gui.getGame().repaint();

				setX(x += 70);
				if (getX() > 680) {
					setX(80);
				}
			}
		}
	}

	public synchronized void gameAdvisor(String txt) {

		gui.getGame().getGameAdvisor().setText(txt);
	}

	public void personalAdvisor(String txt) {

		gui.getGame().getGameAdvisor().setText(txt);
	}

	private void selectError() {
		JOptionPane.showMessageDialog(gui.getMainMenu(),
				"Selezionare prima la carta da prendere", "Attenzione", //in caso di tentativo di presa SINGOLA nell'ordine di selezione sbagliato
				JOptionPane.WARNING_MESSAGE);
		/*gui.getGame().getGameAdvisor().setForeground(Color.RED);
		gameAdvisor("ERRORE: selezionare prima la carta da prendere");*/
	}

	public void sendError() {
		JOptionPane.showMessageDialog(gui.getMainMenu(),
				"Mossa non consentita.", "Attenzione", //in caso di mossa sbagliata o tentativo di presa MULTIPLA selezionando una carta che ""attacca""
				JOptionPane.WARNING_MESSAGE);
		/*gui.getGame().getGameAdvisor().setForeground(Color.RED);
		gameAdvisor(
				"ERRORE: mossa non consentita. Selezionare prima la carta da prendere. Hai 10 secondi per fare una mossa.");*/
		
	}

	public synchronized void scopaAlert(Player player) {
		try {
			menu.getSound().playMusic("applause.wav");
		} catch (Exception ex) {}
		gui.getGame().getGameAdvisor().setForeground(Color.RED);
		gameAdvisor(player.getNickname() + " FA SCOPA!");
	}
}