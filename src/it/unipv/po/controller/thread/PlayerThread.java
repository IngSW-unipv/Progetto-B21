package it.unipv.po.controller.thread;

import it.unipv.po.controller.Controller;
import it.unipv.po.model.game.ScoponeGame;
import it.unipv.po.model.game.player.types.Player;
import it.unipv.po.model.game.player.types.TypePlayer;

/**
 * Per ogni game abbiamo 4 thread che gestiscono i turni dei giocatori, senza
 * fare differenze tra bot e umani
 * 
 * @param g è il game a cui si riferisce
 * @param p è il player a cui si riferisce
 * 
 */

public class PlayerThread extends Thread {
	private Player p;
	private ScoponeGame g;
	private Controller controller;


	public PlayerThread(ScoponeGame g, Player p, Controller controller) {
		this.p = p;
		this.g = g;
		this.controller = controller;
	}

	public Player getP() {
		return p;
	}

	/**
	 * Ogni giocatore fa 3 cose: controlla se è il suo turno; gioca una carta;
	 * finisce il turno.
	 */
	public void run() {
		while (true) {
			checkTurn();
			updateBoard();
			play();
			endTurn();
		}
	}

	private synchronized void updateBoard() {
		
		controller.cardsOnBoard(g.getCardsOnBoard(), controller.getX(), 40);
	}

	/**
	 * Se l'indice del giocatore è == al turno, allora tocca a lui
	 */
	synchronized public boolean checkTurn() {
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
	synchronized public boolean play() {

		if (p.typePlayer() == TypePlayer.HUMANPLAYER) {

			try {
				sleep(10000);
			} catch (InterruptedException e) {

				if (!g.playerActionMonitoring(p)) {

					try {
						sleep(10000);
					} catch (InterruptedException e1) {

						g.playerActionMonitoring(p);
					}
				}

				return true;
			}

			g.playerActionMonitoring(p, g.getCardsOnBoard());

			return true;
		}

		else {

			try {
				sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			g.playerActionMonitoring(p, g.getCardsOnBoard());

			return true;
		}
	}

	/**
	 * Il giocatore finisce il turno, quindi viene incrementato il contatore g.turn
	 * e si passa il controllo al giocatore successivo. Bisogna controllare se la
	 * partita finisce, cioè se il giocatore di indice 4 non ha più carte in mano.
	 */
	synchronized public void endTurn() {
		if (p.getDeck().size() == 0 && p.getPlayerIndex() == 4) {
			g.endGame();
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		g.nextTurn();
		notifyAll();
	}
}
