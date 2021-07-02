package it.unipv.ingsw.client.controller.thread;

import java.awt.Color;

import it.unipv.ingsw.client.controller.Controller;
import it.unipv.ingsw.client.model.game.Game;
import it.unipv.ingsw.client.model.game.cards.Card;
import it.unipv.ingsw.client.model.game.player.types.HumanPlayer;
import it.unipv.ingsw.client.model.game.player.types.Player;
import it.unipv.ingsw.client.model.game.player.types.TypePlayer;

/**
 * Per ogni game abbiamo 4 thread che gestiscono i turni dei giocatori, senza
 * fare differenze tra bot e umani
 * 
 * @param g � il game a cui si riferisce
 * @param p � il player a cui si riferisce
 * 
 */

public class PlayerThread extends Thread {
	private Player p;
	private Game g;
	private Controller controller;
	private int click; // Uso questa variabile per gestire il bug del doppio click durante l'invio
						// della giocata

	public PlayerThread(Game g, Player p, Controller controller) {
		this.p = p;
		this.g = g;
		this.controller = controller;
		this.click = 0;
	}

	public int getClick() {
		return click;
	}

	public void setClick(int click) {
		this.click = click;
	}

	public Player getP() {
		return p;
	}

	/**
	 * Ogni giocatore fa 3 cose: controlla se � il suo turno; gioca una carta;
	 * finisce il turno.
	 */
	public void run() {
		while (true) {
			checkTurn();
			play();
			updateBoard();
			endTurn();
		}
	}

	private synchronized void updateBoard() {

		controller.cardsOnBoardCreator(g.getCardsOnBoard(), controller.getX(), 48);
		controller.getGui().getGame().getGameAdvisor().setForeground(Color.BLACK);

		if (p.typePlayer() == TypePlayer.HUMANPLAYER) {
			deckAction();
		}

		if (p.getCardsListTemp().size() > 1) {
			boardAction();

			try {
				sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (p.isScopa()) {

				controller.scopaAlert(p);
				p.setScopa();

				try {
					sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Se l'indice del giocatore � == al turno, allora tocca a lui
	 */
	public synchronized  boolean checkTurn() {
		while (g.getTurn() != p.getPlayerIndex())
			try {
				notifyAll();
			} catch (Exception e) {
			}

		return true;
	}

	/**
	 * Il giocatore gioca una carta
	 */
	public synchronized  boolean play() {

		if (p.typePlayer() == TypePlayer.HUMANPLAYER) {

			setClick(0);

			try {
				sleep(300);
				controller.personalAdvisor("E' il tuo turno: hai 20 secondi per fare una mossa");
				controller.getGui().getGame().getGameAdvisor().setForeground(Color.BLACK);
				sleep(1000);
				counter(20);
			} catch (InterruptedException e) {

				if (!g.playerActionMonitoring((HumanPlayer) p)) {

					try {
						controller.sendError();
						p.setCardSelected();
						sleep(10000);
					} catch (InterruptedException e1) {

						g.playerActionMonitoring((HumanPlayer) p);
					}
				}

				controller.gameAdvisor("||GIOCATORE " + p.getPlayerIndex() + "|| " + p.getNickname() + " gioca "
						+ ((HumanPlayer) p).getCardPlayed());

				return true;
			}

			g.playerActionMonitoring(p);

			controller.gameAdvisor("||GIOCATORE " + p.getPlayerIndex() + "|| " + p.getNickname() + " gioca "
					+ p.getCardsListTemp().get(p.getCardsListTemp().size() - 1));

			return true;
		}

		else {

			try {
				sleep(500);// 2000
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			g.playerActionMonitoring(p);

			controller.gameAdvisor("||GIOCATORE " + p.getPlayerIndex() + "|| " + p.getNickname() + " gioca "
					+ p.getCardsListTemp().get(p.getCardsListTemp().size() - 1));

			return true;
		}
	}

	/**
	 * Il giocatore finisce il turno, quindi viene incrementato il contatore g.turn
	 * e si passa il controllo al giocatore successivo. Bisogna controllare se la
	 * partita finisce, cio� se il giocatore di indice 4 non ha pi� carte in mano.
	 */
	public synchronized void endTurn() {

		try {
			sleep(1000); // 3000
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (p.getDeck().size() == 0 && p.getPlayerIndex() == 4) {

			g.endGame();
			for (Card s : g.getCardsOnBoard()) {
				controller.getCardsOnBoard().get(s).setVisible(false);
			}

			controller.gameAdvisor("Punti team A: " + g.getTeams().get(0).getTotalPoints() + " | Punti team B: "
					+ g.getTeams().get(1).getTotalPoints());
			try {
				sleep(5000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			p.getCardsListTemp().clear();

			if (controller.verifyGame()) {

				controller.gameAdvisor("PARTITA FINITA!");
			} else {
				controller.gameAdvisor("La partita ricomincia tra 10 secondi.");
				try {
					sleep(2000);
					counter(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				controller.restartGame();
			}
		}

		else {

			p.getCardsListTemp().clear();
			g.nextTurn();
			notifyAll();
		}
	}

	private void deckAction() {

		controller.getDeck().get(((HumanPlayer) p).getCardPlayed()).setVisible(false);
	}

	private void boardAction() {

		for (Card s : p.getCardsListTemp()) {

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
}
