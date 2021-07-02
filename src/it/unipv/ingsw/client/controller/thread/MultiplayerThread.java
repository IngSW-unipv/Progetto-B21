package it.unipv.ingsw.client.controller.thread;

import java.awt.Color;

import it.unipv.ingsw.client.controller.Controller;
import it.unipv.ingsw.client.model.game.cards.Card;
import it.unipv.ingsw.client.model.game.player.types.HumanPlayer;
import it.unipv.ingsw.client.model.game.player.types.Player;
import it.unipv.ingsw.client.model.multiplayer.client.Client;

public class MultiplayerThread extends Thread{
	private Client client;
	private Controller controller;
	private Player player;
	private int click; // Uso questa variabile per gestire il bug del doppio click durante l'invio
						// della giocata

	public MultiplayerThread(Client cl, Controller co) {
		client = cl;
		controller = co;
		player = cl.getPlayer();
		click = 0;
	}

	public int getClick() {
		return click;
	}

	public void setClick(int click) {
		this.click = click;
	}

	public Player getPlayer() {
		return player;
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
					player.setCardSelected();
					sleep(10000);
				} catch (InterruptedException e1) {
					playerActionMonitoring();
				}
			}
			controller.gameAdvisor("||GIOCATORE " + player.getPlayerIndex() + "|| " + player.getNickname() + " gioca " + ((HumanPlayer) player).getCardPlayed());
			return true;
			}
		playerActionMonitoring();
		controller.gameAdvisor("||GIOCATORE " + player.getPlayerIndex() + "|| " + player.getNickname() + " gioca " 
								+ player.getCardsListTemp().get(player.getCardsListTemp().size() - 1));
		return true;
	}


	private void updateBoard() {
		controller.cardsOnBoardCreator(client.getCardsOnBoard(), controller.getX(), 48);
		controller.getGui().getGame().getGameAdvisor().setForeground(Color.BLACK);
		deckAction();
		if (player.getCardsListTemp().size() > 1) {
			boardAction();
			try {
				sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (player.isScopa()) {
				controller.scopaAlert(player);
				player.setScopa();
				try {
					sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}



	/**
	 * Il giocatore finisce il turno, quindi viene incrementato il contatore client.turn
	 * e si passa il controllo al giocatore successivo. Bisogna controllare se la
	 * partita finisce, cioè se il giocatore di indice 4 non ha più carte in mano.
	 */
	synchronized public void endTurn() {
		try {
			sleep(1000); // 3000
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		player.getCardsListTemp().clear();
		client.nextTurn();
	}

	private void deckAction() {
		controller.getDeck().get(((HumanPlayer) player).getCardPlayed()).setVisible(false);
	}

	private void boardAction() {
		for (Card s : player.getCardsListTemp()) {
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
		}
		else {
			for (int i = t; i >= 0; i--) {
				controller.gameAdvisor(String.valueOf(i));
				wait(1000);
			}
		}
	}
	
	
	public synchronized boolean playerActionMonitoring() {
		int counter = 0;
		switch (player.getCardsListTemp().size()) {
		case 1: // caso nel cui il giocatore non fa una presa
			for (Card table : client.getCardsOnBoard()) {
				counter += table.getValue();
				if (counter == player.getCardsListTemp().get(0).getValue()) {
					((HumanPlayer) player).setHavePlayed(false);
					return false;
				}
				else {
					if (player.getCardsListTemp().get(0).getValue() == table.getValue()) {
						((HumanPlayer) player).setHavePlayed(false);
						return false;
					}
				}
			}
			client.getCardsOnBoard().addAll(player.getCardsListTemp());
			player.getDeck().removeAll(player.getCardsListTemp());
			((HumanPlayer) player).setHavePlayed(true);
			return true;
			
		default: // caso nel cui il giocatore fa una presa
			int temp = 0;
			int valueCardPlayed = player.getCardsListTemp().get(player.getCardsListTemp().size() - 1).getValue();
			for (int i = 0; i < player.getCardsListTemp().size() - 1; i++) {
				temp += player.getCardsListTemp().get(i).getValue();
			}
			if (temp == valueCardPlayed) {
				client.getCardsOnBoard().removeAll(player.getCardsListTemp());
				player.getDeck().remove(player.getCardsListTemp().get(player.getCardsListTemp().size() - 1));
				if (client.getCardsOnBoard().isEmpty()) {
					player.setScopa();
				}
				((HumanPlayer) player).setHavePlayed(true);
				return true;
			}
			else {
				((HumanPlayer) player).setHavePlayed(false);
				return false;
			}
		}
	}
}
