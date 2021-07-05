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

public class SingleplayerThread extends Thread implements PlayerThread {
	private Player p;
	private Game g;
	private Controller controller;
	private int click; // Uso questa variabile per gestire il bug del doppio click durante l'invio
						// della giocata
	
	private int seconds = 20;

	public SingleplayerThread(Game g, Player p, Controller controller) {
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
	public synchronized boolean checkTurn() {
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
	public synchronized boolean play() {

		if (p.typePlayer() == TypePlayer.HUMANPLAYER) {
			//init
			((HumanPlayer)p).setHavePlayed(false);
			setClick(0);

			try {
				sleep(300);
				controller.personalAdvisor("E' il tuo turno: hai "+seconds+" secondi per fare una mossa");
				controller.getGui().getGame().getGameAdvisor().setForeground(Color.BLACK);
				sleep(1000);
				counter(seconds);
			} catch (InterruptedException e) { //se l'utente preme il pulsante INVIO

				if (!g.playerActionMonitoring((HumanPlayer) p)) { //e se sbaglia

					p.setCardSelected();
					controller.sendError();
					p.getCardsListTemp().clear();
					play();
				}
				//se invece va tutto bene
				controller.gameAdvisor("||GIOCATORE " + p.getPlayerIndex() + "|| " + p.getNickname() + " gioca "
						+ ((HumanPlayer) p).getCardPlayed());

				seconds = 20;
				return true;
			}
			//se lo HumanPlayer lascia scadere il proprio tempo, si comporterà come un BotPlayer
			if(!((HumanPlayer)p).hasPlayed()) {
				Card selected=controller.getSelectedCard();
				g.playerActionMonitoring((Player)p);
				if(selected!=null && selected.equals(p.getCardsListTemp().get(p.getCardsListTemp().size() - 1)))
					p.setCardSelected();
			controller.gameAdvisor("||GIOCATORE " + p.getPlayerIndex() + "|| " + p.getNickname() + " gioca "
					+ p.getCardsListTemp().get(p.getCardsListTemp().size() - 1));
			}

			seconds = 20;
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
				controller.gameAdvisor("PARTITA FINITA! vincono: " + controller.getWinner().getPlayers().get(0).getNickname() + " e " +
				controller.getWinner().getPlayers().get(1).getNickname()+" del team "+((controller.getWinner().getPlayers().get(0).getTeamIndex()==0)?"A":"B"));
			} else {
				controller.gameAdvisor("La partita ricomincia tra "+10+" secondi.");
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

		if (t == seconds) {

			for (int i = t; i >= 0; i--) {

				seconds = i;
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
