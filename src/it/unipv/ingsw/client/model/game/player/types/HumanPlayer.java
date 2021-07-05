package it.unipv.ingsw.client.model.game.player.types;

import java.util.ArrayList;

import it.unipv.ingsw.client.model.game.cards.Card;

/**
 * Questa classe rappresenta un giocatore umano e permette di effettuare le sue mosse.
 * 
 * 
 * 
 */

public class HumanPlayer extends Player {

	//________________ATTRIBUTI________________
	private static final long serialVersionUID = 1L;
	private Card cardPlayed;
	private boolean havePlayed;
	
	/**
	 * Crea un giocatore umano.
	 * @param name : il nome del giocatore umano.
	 */
	public HumanPlayer(String name) {
		super(name);
		havePlayed = false;
		cardPlayed = null;

	}

	//______________GETTERS & SETTERS______________

	public Card getCardPlayed() {
		return cardPlayed;
	}

	public void setCardPlayed(Card cardPlayed) {
		this.cardPlayed = cardPlayed;
	}
	
	public boolean hasPlayed() {
		return havePlayed;
	}

	public void setHavePlayed(boolean havePlayed) {
		this.havePlayed = havePlayed;
	}	

	//__________________METODI__________________
	
	
	/**
	 * Questo metodo serve nel caso in cui il giocatore umano
	 * non faccia mosse, utilizzando la stessa logica di un BotPlayer.
	 * @param cardsOnBoard : ArrayList di carte presenti sul tavolo
	 */
	public ArrayList<Card> playCard(ArrayList<Card> cardsOnBoard) {

		getCardsListTemp().clear();

		switch (makePresa(cardsOnBoard)) {

		case 1:

			return getCardsListTemp();

		case 0:

			getCardsListTemp().add(getDeck().get(0));
			setCardPlayed(getDeck().get(0));

			return getCardsListTemp();
		}

		return null;
	}

	/**
	 * Metodo privato che permette al bot di effettuare una presa.
	 * 
	 * @param cardsOnBoard : ArrayList di carte presenti sul tavolo
	 * @return un intero per usarlo nello switch della funzione playCard
	 */
	
	private int makePresa(ArrayList<Card> cardsOnBoard) {

		if (cardsOnBoard.isEmpty()) {

			setCardPlayed(getDeck().get(0));
			getCardsListTemp().add(getDeck().get(0));

			return 1;
		}

		else {

			for (Card deck : getDeck()) {

				int counter = 0;
				ArrayList<Card> cardList = new ArrayList<Card>();

				for (Card table : cardsOnBoard) {

					counter += table.getValue();
					cardList.add(table);

					if (counter == deck.getValue() && cardList.size() != 1) {

						getCardsListTemp().addAll(cardList);
						getCardsListTemp().add(deck);
						setCardPlayed(deck);

						return 1;
					}

					else {

						if (deck.getValue() == table.getValue()) {

							getCardsListTemp().add(table);
							getCardsListTemp().add(deck);
							setCardPlayed(deck);

							return 1;
						}
					}
				}
			}
		}
		return 0;
	}

	@Override
	public TypePlayer typePlayer() {
		// TODO Auto-generated method stub
		return TypePlayer.HUMANPLAYER;
	}
}
