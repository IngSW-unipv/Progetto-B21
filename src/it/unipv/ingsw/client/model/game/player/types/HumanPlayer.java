package it.unipv.ingsw.client.model.game.player.types;

import java.util.ArrayList;

import it.unipv.ingsw.client.model.game.cards.Card;
import it.unipv.ingsw.client.model.game.cards.Suit;

/**
 * Questa classe rappresenta un giocatore umano e permette di effettuare le sue mosse.
 * 
 * 
 * 
 */

public class HumanPlayer extends Player {

	//________________ATTRIBUTI________________
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

			if (getDeck().size() > 1) {

				for (Card s : getDeck()) {

					if (s.getValue() > 3 && s.getValue() != 7 && s.getSuit() != Suit.DENARI) {

						getCardsListTemp().add(s);
						setCardPlayed(s);

						return getCardsListTemp();
					}
				}

				for (Card t : getDeck()) {
					if (t.getValue() != 7 && t.getSuit() != Suit.DENARI) {

						getCardsListTemp().add(t);
						setCardPlayed(t);
						return getCardsListTemp();
					}
				}
			}

			getCardsListTemp().add(getDeck().get(0));
			return getCardsListTemp(); // siamo arrivati all'ultima carta del deck
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

			for (Card s : getDeck()) {

				if (getDeck().size() > 1) {

					if (s.getValue() != 7 && s.getSuit() != Suit.DENARI) {

						if (s.getSuit() != Suit.DENARI) {

							getCardsListTemp().add(s);
							setCardPlayed(s);

							return 1;
						}
					}

					else if (s.getValue() != 7 && s.getSuit() != Suit.DENARI) {

						getCardsListTemp().add(s);
						setCardPlayed(s);
						return 1;
					}
				} else {
					getCardsListTemp().add(s);
					setCardPlayed(s);
					return 1;
				}
			}

			getCardsListTemp().add(getDeck().get(0));
			setCardPlayed(getDeck().get(0));

			return 1;

		} else {

			ArrayList<Card> cardList = new ArrayList<Card>();
			for (Card deck : getDeck()) {

				int counter = 0;
				cardList.clear();

				for (Card table : cardsOnBoard) {

					counter += table.getValue();
					cardList.add(table);

					if (counter == deck.getValue() && cardList.size() != 1) {

						getCardsListTemp().addAll(cardList);
						getCardsListTemp().add(deck);
						setCardPlayed(deck);

						return 1;
					}
				}
			}

			for (Card deck2 : getDeck()) {

				for (Card table2 : cardsOnBoard) {

					if (deck2.getValue() == table2.getValue()) {

						getCardsListTemp().add(table2);
						getCardsListTemp().add(deck2);
						setCardPlayed(deck2);
						return 1;
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
