package it.unipv.ingsw.client.model.game.player.types;

import java.util.ArrayList;

import it.unipv.ingsw.client.model.game.cards.Card;
import it.unipv.ingsw.client.model.game.cards.Suit;

/**
 * Questa classe permette l'uso di un'intelligenza artificiale semplice, con il
 * Bot che gioca una carta a caso tra quelle a disposizione.
 * 
 * @author Giuseppe Lentini
 */
public class BotPlayer extends Player {

	private Card cardPlayed;

	/**
	 * Crea un bot.
	 */
	public BotPlayer() {
		super("Bot" + (int) (Math.random() * 9999));
	}

	/**
	 * Crea un bot con nome.
	 * 
	 * @param name : il nome del bot.
	 */
	public BotPlayer(String name) {
		super(name);
	}

	public Card getCardPlayed() {
		return cardPlayed;
	}

	// __________________METODI__________________

	/**
	 * Metodo che permette al bot di giocare una carta ed effettuare una presa.
	 * 
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

						return getCardsListTemp();
					}
				}

				for (Card t : getDeck()) {
					if (t.getValue() != 7 && t.getSuit() != Suit.DENARI) {

						getCardsListTemp().add(t);
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
							cardPlayed = s;

							return 1;
						}
					}

					else if (s.getValue() != 7 && s.getSuit() != Suit.DENARI) {

						getCardsListTemp().add(s);
						cardPlayed = s;

						return 1;
					}
				} else {
					getCardsListTemp().add(s);
					cardPlayed = s;

					return 1;
				}
			}

			getCardsListTemp().add(getDeck().get(0));
			return 1;

		} else {
			
			for (Card deck : getDeck()) { // presa singola

				for (Card table : cardsOnBoard) {

					if (deck.getValue() == table.getValue()) {

						getCardsListTemp().add(table);
						getCardsListTemp().add(deck);

						return 1;
					}
				}
			}
			
			for (Card deck : getDeck()) { //prese con 2 carte sul tavolo

				for (Card table : cardsOnBoard) {

					for (Card table2 : cardsOnBoard) {

						if ((table != table2) &&  table.getValue() + table2.getValue() == deck.getValue()) {

							getCardsListTemp().add(table);
							getCardsListTemp().add(table2);
							getCardsListTemp().add(deck);

							return 1;
						}
					}
				}

			}
			
			ArrayList<Card> cardList = new ArrayList<Card>();
			
			for (Card deck : getDeck()) { //prese con più di 2 carte sul tavolo

				int counter = 0;
				cardList.clear();

				for (Card table : cardsOnBoard) {

					counter += table.getValue();
					cardList.add(table);

					if (counter == deck.getValue() && cardList.size() != 1) {

						getCardsListTemp().addAll(cardList);
						getCardsListTemp().add(deck);

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
		return TypePlayer.BOTPLAYER;
	}
}