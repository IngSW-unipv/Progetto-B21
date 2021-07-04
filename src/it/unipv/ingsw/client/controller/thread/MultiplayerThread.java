package it.unipv.ingsw.client.controller.thread;

import java.awt.Color;

import it.unipv.ingsw.client.controller.Controller;
import it.unipv.ingsw.client.model.game.cards.Card;
import it.unipv.ingsw.client.model.game.player.types.HumanPlayer;
import it.unipv.ingsw.client.model.game.player.types.Player;
import it.unipv.ingsw.client.model.multiplayer.client.Client;

public class MultiplayerThread extends Thread implements PlayerThread {
	private Client client;
	private Controller controller;
	private int click; // Uso questa variabile per gestire il bug del doppio click durante l'invio
						// della giocata

	public MultiplayerThread(Client cl, Controller co) {
		client = cl;
		controller = co;
		click = 0;
	}

	public int getClick() {
		return click;
	}

	public void setClick(int click) {
		this.click = click;
	}

	public Player getPlayer() {
		return client.getPlayer();
	}

	/**
	 * Il giocatore gioca una carta
	 */
	public boolean play() {
		setClick(0);
		try {
			sleep(300);
			controller.personalAdvisor("E' il tuo turno: hai 20 secondi per fare una mossa");
			controller.getGui().getGame().getGameAdvisor().setForeground(Color.BLACK);
			sleep(1000);
			counter(20);
		} catch (InterruptedException e) {
			if (!playerActionMonitoring()) {
				try {
					controller.sendError();
					client.getPlayer().setCardSelected();
					sleep(10000);
				} catch (InterruptedException e1) {
					playerActionMonitoring();
				}
			}
			controller.gameAdvisor(
					"||GIOCATORE " + client.getPlayer().getPlayerIndex() + "|| " + client.getPlayer().getNickname()
							+ " gioca " + ((HumanPlayer) client.getPlayer()).getCardPlayed());
			return true;
		}
		playerActionMonitoring();
		controller.gameAdvisor("||GIOCATORE " + client.getPlayer().getPlayerIndex() + "|| "
				+ client.getPlayer().getNickname() + " gioca "
				+ client.getPlayer().getCardsListTemp().get(client.getPlayer().getCardsListTemp().size() - 1));
		return true;
	}

	public void updateBoard() {
		controller.cardsOnBoardCreator(client.getCardsOnBoard(), controller.getX(), 48);
		controller.getGui().getGame().getGameAdvisor().setForeground(Color.BLACK);
		if (controller.getDeck().get(((HumanPlayer) client.getPlayer()).getCardPlayed()) != null)
			deckAction();
		if (client.getPlayer().getCardsListTemp().size() > 1) {
			boardAction();
			try {
				sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (client.getPlayer().isScopa()) {
				controller.scopaAlert(client.getPlayer());
				client.getPlayer().setScopa();
				try {
					sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Il giocatore finisce il turno, quindi viene incrementato il contatore
	 * client.turn e si passa il controllo al giocatore successivo. Bisogna
	 * controllare se la partita finisce, cioè se il giocatore di indice 4 non ha
	 * più carte in mano.
	 */
	public void endTurn() {
		try {
			sleep(1000); // 3000
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		client.getPlayer().getCardsListTemp().clear();
	}

	private void deckAction() {
		controller.getDeck().get(((HumanPlayer) client.getPlayer()).getCardPlayed()).setVisible(false);
	}

	private void boardAction() {
		for (Card s : client.getPlayer().getCardsListTemp()) {
			if (controller.getCardsOnBoard().get(s) != null) {
				try {
					controller.getCardsOnBoard().get(s).setVisible(false);
				} catch (Exception e) {
				}
			}
		}
	}

	private void counter(int t) throws InterruptedException {
		if (t == 20) {
			for (int i = t; i >= 0; i--) {
				controller.personalAdvisor(String.valueOf(i));
				wait(1000);
			}
		} else {
			for (int i = t; i >= 0; i--) {
				controller.gameAdvisor(String.valueOf(i));
				wait(1000);
			}
		}
	}

	public void writeMessage(String str) {
		controller.gameAdvisor(str);
	}

	public boolean playerActionMonitoring() {
		int counter = 0;
		switch (client.getPlayer().getCardsListTemp().size()) {
		case 1: // caso nel cui il giocatore non fa una presa
			for (Card table : client.getCardsOnBoard()) {
				counter += table.getValue();
				if (counter == client.getPlayer().getCardsListTemp().get(0).getValue()) {
					((HumanPlayer) client.getPlayer()).setHavePlayed(false);
					return false;
				} else {
					if (client.getPlayer().getCardsListTemp().get(0).getValue() == table.getValue()) {
						((HumanPlayer) client.getPlayer()).setHavePlayed(false);
						return false;
					}
				}
			}
			client.getCardsOnBoard().addAll(client.getPlayer().getCardsListTemp());
			client.getPlayer().getDeck().removeAll(client.getPlayer().getCardsListTemp());
			((HumanPlayer) client.getPlayer()).setHavePlayed(true);
			return true;

		default: // caso nel cui il giocatore fa una presa
			int temp = 0;
			int valueCardPlayed = client.getPlayer().getCardsListTemp()
					.get(client.getPlayer().getCardsListTemp().size() - 1).getValue();
			for (int i = 0; i < client.getPlayer().getCardsListTemp().size() - 1; i++) {
				temp += client.getPlayer().getCardsListTemp().get(i).getValue();
			}
			if (temp == valueCardPlayed) {
				client.getCardsOnBoard().removeAll(client.getPlayer().getCardsListTemp());
				client.getPlayer().getDeck().remove(
						client.getPlayer().getCardsListTemp().get(client.getPlayer().getCardsListTemp().size() - 1));
				if (client.getCardsOnBoard().isEmpty()) {
					client.getPlayer().setScopa();
				}
				((HumanPlayer) client.getPlayer()).setHavePlayed(true);
				return true;
			} else {
				((HumanPlayer) client.getPlayer()).setHavePlayed(false);
				return false;
			}
		}
	}

	public void setGameViewVisible() {
		controller.startGame();
		updateBoard();
	}

	public void setLobbyViewVisible() {
		controller.startEntraLobby();
	}
}
