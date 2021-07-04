package it.unipv.ingsw.client.model.game.player.types;

import java.util.ArrayList;

import it.unipv.ingsw.client.model.game.cards.Card;

/**
 * Questa classe permette ad un giocatore umano di effettuare le sue mosse.
 * 
 * 
 * 
 */

//____________________COSTRUTTORE_________________
public class HumanPlayer extends Player {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Card cardPlayed;
	private boolean havePlayed;

	public HumanPlayer(String name) {
		super(name);
		havePlayed = false;
		cardPlayed = null;

	}

//__________________GETTERS & SETTERS______________

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

//__________________METODI__________________________



	/**
	 * Questa è la stessa funzione del botPlayer. Serve nel caso in cui il giocatore umano
	 * non faccia mosse.
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
	 * 
	 * 
	 * @param cardsOnBoard arraylist di carte presenti sul tavolo
	 * @return torna un intero per usarlo nello switch della funzione playCard
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
