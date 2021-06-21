package it.unipv.po.controller.thread;

import java.awt.Color;
import it.unipv.po.controller.Controller;
import it.unipv.po.model.game.ScoponeGame;
import it.unipv.po.model.game.cards.Card;
import it.unipv.po.model.game.player.types.HumanPlayer;
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
	private int click; //Uso questa variabile per gestire il bug del doppio click durante l'invio della giocata

	public PlayerThread(ScoponeGame g, Player p, Controller controller) {
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
	 * Ogni giocatore fa 3 cose: controlla se è il suo turno; gioca una carta;
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

		controller.cardsOnBoard(g.getCardsOnBoard(), controller.getX(), 48);
		controller.getGui().getGame().getGameAdvisor().setForeground(Color.BLACK);

		if (p.typePlayer() == TypePlayer.HUMANPLAYER) {

			deckAction();
			if (p.getCardsListTemp().size() > 1) {
				boardAction();

				try {
					sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (p.isScopa()) {

					controller.scopa(p);
					p.setScopa();

					try {
						sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

		else {

			if (p.getCardsListTemp().size() > 1) {
				boardAction();

				try {
					sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (p.isScopa()) {

					controller.scopa(p);
					p.setScopa();
					try {
						sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
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

			setClick(0);

			try {
				sleep(300);
				controller.getGui().getGame().getGameAdvisor().setForeground(Color.GREEN);
				controller.gameAdvisor("E' il tuo turno: hai 20 secondi per fare una mossa");
				controller.getGui().getGame().getGameAdvisor().setForeground(Color.BLACK);
				sleep(19700);
			} catch (InterruptedException e) {

				if (!g.playerActionMonitoring(p)) {

					try {
						controller.sendError();
						p.setCardSelected();
						sleep(10000);
					} catch (InterruptedException e1) {

						g.playerActionMonitoring(p);
					}
				}

				controller.gameAdvisor("||GIOCATORE " + p.getPlayerIndex() + "|| " + p.getNickname() + " gioca "
						+ ((HumanPlayer) p).getCardPlayed());

				return true;
			}

			g.playerActionMonitoring(p, g.getCardsOnBoard());

			controller.gameAdvisor("||GIOCATORE " + p.getPlayerIndex() + "|| " + p.getNickname() + " gioca "
					+ p.getCardsListTemp().get(p.getCardsListTemp().size() - 1));

			return true;
		}

		else {

			try {
				sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			g.playerActionMonitoring(p, g.getCardsOnBoard());

			controller.gameAdvisor("||GIOCATORE " + p.getPlayerIndex() + "|| " + p.getNickname() + " gioca "
					+ p.getCardsListTemp().get(p.getCardsListTemp().size() - 1));

			return true;
		}
	}

	/**
	 * Il giocatore finisce il turno, quindi viene incrementato il contatore g.turn
	 * e si passa il controllo al giocatore successivo. Bisogna controllare se la
	 * partita finisce, cioè se il giocatore di indice 4 non ha più carte in mano.
	 */
	synchronized public void endTurn() {

		try {
			sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (p.getDeck().size() == 0 && p.getPlayerIndex() == 4) {
			g.endGame();
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}

		p.getCardsListTemp().clear();
		g.nextTurn();
		notifyAll();
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
}
