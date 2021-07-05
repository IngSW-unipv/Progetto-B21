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
public class BotPlayer2 extends Player {

	/**
	 * Crea un bot.
	 */
	public BotPlayer2() {
		super("Bot" + (int) (Math.random() * 9999));
	}

	/**
	 * Crea un bot con nome.
	 * 
	 * @param name : il nome del bot.
	 */

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

			for (Card s : getDeck()) {

				if (getDeck().size() > 1) {

					if (s.getValue() != 7 && s.getSuit() != Suit.DENARI) {

						if (s.getSuit() != Suit.DENARI) {

							getCardsListTemp().add(s);

							return getCardsListTemp();
						}

						else {
							getCardsListTemp().add(s);
							return getCardsListTemp();
						}
					}

					else {
						getCardsListTemp().add(s);

						return getCardsListTemp();
					}
				}
			}

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
						}

						else
							getCardsListTemp().add(s);

					}

					else
						getCardsListTemp().add(s);
				}
			}

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

						return 1;
					}

					else {

						if (deck.getValue() == table.getValue()) {

							getCardsListTemp().add(table);
							getCardsListTemp().add(deck);

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
		return TypePlayer.BOTPLAYER;
	}
}