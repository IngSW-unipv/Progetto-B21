package it.unipv.po.model.game.player;

import it.unipv.po.model.game.Game;
import it.unipv.po.model.game.player.types.HumanPlayer;

/**
 * Per ogni game abbiamo 4 thread che gestiscono i turni dei giocatori, senza
 * fare differenze tra bot e umani
 * 
 * @param g è il game a cui si riferisce
 * @param p è il player a cui si riferisce
 * 
 */

public class HumanThread extends Thread {
	private HumanPlayer p;
	private Game g;

	public HumanThread(Game g, HumanPlayer p) {
		this.p = p;
		this.g = g;
	}

	/**
	 * Ogni giocatore fa 3 cose: controlla se è il suo turno; gioca una carta;
	 * finisce il turno.
	 */
	public void run() {

		checkTurn();
		play();
		endTurn();
	}
	
	public HumanPlayer getP() {
		return p;
	}

	/**
	 * Se l'indice del giocatore è == al turno, allora tocca a lui
	 */
	synchronized public void checkTurn() {
		while (g.getTurn() != p.getPlayerIndex())
			try {
				notifyAll();
			} catch (Exception e) {
			}
	}

	/**
	 * Il giocatore gioca una carta
	 */
	synchronized public boolean play() {

		try {
			sleep(5000);
		} catch (InterruptedException e) {

			g.playerActionMonitoring(p);

			return true;
		}
		
		if(p.getCardsListTemp().size() == 0) {
			
			g.playerActionMonitoring(p, g.getCardsOnBoard());

			return true;
		}
		return false;
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
