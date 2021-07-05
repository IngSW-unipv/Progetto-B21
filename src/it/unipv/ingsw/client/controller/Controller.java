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

/**
 * Questa classe rappresenta il controller, che ha il compito di gestire gli
 * eventi esterni e ricevere/mandare comunicazioni sia al model che alla view.
 * 
 *
 */
public class Controller {

	// ________________ATTRIBUTI________________
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
		questionsListener();
	}

	/**
	 * Avvia la partita.
	 */
	public void startGame(JLabel label) {
		this.game = menu.getGame();
		this.player = menu.getPlayer();

		label.setVisible(false);
		gui.game();
		gui.getGame().getBack().addActionListener(exitListener(gui.getGame(), label));
		sendListener();
		deckCreator(30, 309);
	}

	/**
	 * Avvia la modalitï¿½ multiplayer.
	 */
	public void startMultiPlayer() {

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

	/**
	 * Avvia la schermata "CREA LOBBY" della modalitï¿½ multiplayer.
	 */
	public void startCreaLobby() {

		gui.getMultiPlayer().setVisible(false);
		gui.creaLobby();
		creaLobbyStartListener();
		makeLobbyListener();
		creaLobbyNameListener();
		gui.getCreaLobby().getAdvisor().setText("questa ï¿½ una LOBBY"); // schermata da cambiare
		gui.getCreaLobby().getBack().addActionListener(backListener(gui.getCreaLobby(), gui.getMultiPlayer()));
	}

	/**
	 * Avvia la schermata "CREA LOBBY" della modalitï¿½ multiplayer.
	 */
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
					startGame(gui.getMainMenu());
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
				} else {
					menu.clientConnect();
					if(menu.isStatusServer())
						startMultiPlayer();
				}
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
							JOptionPane.showMessageDialog(gui.getMainMenu(), "Nome della lobby giï¿½ esistente",
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
				} else {

					if (!menu.getClient().joinLobby(menu.getNomeLobby())) {
						JOptionPane.showMessageDialog(gui.getMainMenu(), "Errore: non esiste una lobby con quel nome.",
								"Attenzione", JOptionPane.WARNING_MESSAGE);
					}
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

	private ActionListener exitListener(JLabel current, JLabel previus) {

		ActionListener back = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int i = JOptionPane.showOptionDialog(gui.getMainMenu(), "Stai per uscire. Procedere?", "Attenzione",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);

				if (i == JOptionPane.YES_OPTION) {

					menu.closeThreads();
					current.setVisible(false);
					previus.add(gui.getSound());
					previus.setVisible(true);
				}

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
						} catch (Exception ex) {
						}
						((HumanPlayer) player).setCardPlayed(s);
					}

					else if (s.isSelected() == true && player.isCardSelected() == true) {

						player.getCardsListTemp().remove(s);
						player.setCardSelected();
						s.setSelected();
						((HumanPlayer) player).setCardPlayed(null);
						try {
							menu.getSound().playMusic("card_flip.wav");
						} catch (Exception ex) {
						}
						deck.get(s).cardSelected(false);
					}
				}
			};

			deck.get(s).addActionListener(a);
			x += 70;
		}
	}

	public Card getSelectedCard() {
		for (Card s : player.getDeck())
			if (s.isSelected())
				return (s.copy());
		return null;
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
		gui.getGame().getBack().addActionListener(exitListener(gui.getGame(), gui.getMainMenu()));
		sendListener();
		deckCreator(30, 309);
	}

	private void questionsListener() {

		ActionListener quest = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JOptionPane.showMessageDialog(gui.getMainMenu(),
						"In questo gioco, valgono le regole dello scopone Scientifico tradizionali.\n\n"
								+ "Ogni Scopa vale 1 punto, il Sette Bello vale un 1 punto, prendere piï¿½ di \n "
								+ "5 carte di denari vale 1 punto, prendere piï¿½ di 20 carte vale 1 punto, \n"
								+ "la Primiera vale 1 punto.\n\n"
								+ "Il meccanismo per effettuare una presa consiste nel: \n"
								+ "1) Selezionare prima le carte presenti sul tavolo da gioco.\n"
								+ "2) Selezionare la carta con cui si vuole effettuare la presa. \n"
								+ "3) Cliccare il pulsante 'invio'.\n\n"
								+ "Per altre informazioni: github.com/IngSW-unipv/Progetto-B21/wiki\n",
						"Direttive del gioco", JOptionPane.INFORMATION_MESSAGE);
			}
		};
		gui.getMainMenu().getDomanda().addActionListener(quest);
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
						if (!((HumanPlayer) player).hasPlayed()) {

							if (player.isCardSelected()) {
								selectErrorBoard();
							} else {

								if (s.isSelected() == false) {

									player.getCardsListTemp().add(s);
									s.setSelected();
									try {
										menu.getSound().playMusic("card_flip.wav");
									} catch (Exception ex) {
									}
									cardsOnBoard.get(s).cardSelected(s.isSelected());
								}

								else {

									player.getCardsListTemp().remove(s);
									s.setSelected();
									try {
										menu.getSound().playMusic("card_flip.wav");
									} catch (Exception ex) {
									}
									cardsOnBoard.get(s).cardSelected(s.isSelected());
								}
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

	private void selectErrorBoard() {
		JOptionPane.showMessageDialog(gui.getMainMenu(),
				"Prima deseleziona la carta in mano e poi eventualmente quella/e sul tavolo", "Attenzione",
				JOptionPane.WARNING_MESSAGE);
	}

	public void sendError() {
		JOptionPane.showMessageDialog(gui.getMainMenu(), "Mossa non consentita.", "Attenzione",
				JOptionPane.WARNING_MESSAGE);

	}

	public void serverError() {
		JOptionPane.showMessageDialog(gui.getMainMenu(),
				"Il server non risulta essere online.\n" + "Riprovare più tardi.", "Errore",
				JOptionPane.WARNING_MESSAGE);
	}

	public void gameRecap() {
		JOptionPane.showMessageDialog(gui.getMainMenu(), "                           Team A          Team B\n"
				+ "sette bello:          " + game.getTeams().get(0).isSetteBello() + "               "
				+ game.getTeams().get(1).isSetteBello() + "\n" + "denari:                      "
				+ game.getTeams().get(0).getnDenari() + "                     " + game.getTeams().get(1).getnDenari()
				+ "\n" + "numero carte:      " + game.getTeams().get(0).getnCarte() + "                   "
				+ game.getTeams().get(1).getnCarte() + "\n" + "primiera:                "
				+ game.getTeams().get(0).getPuntiPrimiera() + "                  "
				+ game.getTeams().get(1).getPuntiPrimiera() + "\n" + "scope:                       "
				+ game.getTeams().get(0).getNumScope() + "                    " + game.getTeams().get(1).getNumScope()
				+ "\n" + "\n\n" + "punteggio:                " + game.getTeams().get(0).getTotalPoints()
				+ "                   " + game.getTeams().get(1).getTotalPoints() + "\n"

				, "Punteggio", JOptionPane.INFORMATION_MESSAGE);

	}

	public synchronized void scopaAlert(Player player) {
		try {
			menu.getSound().playMusic("applause.wav");
		} catch (Exception ex) {
		}
		gui.getGame().getGameAdvisor().setForeground(Color.RED);
		gameAdvisor(player.getNickname() + " FA SCOPA!");
	}
}