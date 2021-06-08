package it.unipv.po.model.game.player.types;

import java.util.ArrayList;

import it.unipv.po.model.game.cards.Card;
import it.unipv.po.model.game.player.Player;

/**
 * Questa classe permette l'uso di intelligenza artificiale semplice, con il Bot
 * che gioca una carta a caso tra quelle a disposizione.
 * 
 * @author Giuseppe Lentini
 */
public class BotPlayer2 extends Player {

	public BotPlayer2() {
		super("Bot presa multipla");
	}

	public ArrayList<Card> playCard(ArrayList<Card> cardsOnBoard) {

		getCardsListTemp().clear();

		switch (makePresa(cardsOnBoard)) {

		case 1:

			return getCardsListTemp();

		case 0:

			int i = 0;

			System.out.println(getNickname() + getPlayerIndex() + "| " + "DEPOSITO(NO PRESA): Gioco la carta "
					+ getDeck().get(i).getValue() + " di " + getDeck().get(i).getSuit());
			getCardsListTemp().add(getDeck().get(i));

			return getCardsListTemp();
		}

		return null;
	}

	private int makePresa(ArrayList<Card> cardsOnBoard) {

		if (cardsOnBoard.isEmpty()) {

			int i = 0;

			System.out.println(getNickname() + getPlayerIndex() + "| " + "DEPOSITO: Gioco la carta "
					+ getDeck().get(i).getValue() + " di " + getDeck().get(i).getSuit());

			getCardsListTemp().add(getDeck().get(i));

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

						System.out.println("PRESA MULTIPLA");
						return 1;
					}

					else {

						if (deck.getValue() == table.getValue()) {

							getCardsListTemp().add(table);
							getCardsListTemp().add(deck);

							System.out.println(getNickname() + getPlayerIndex() + "| " + "PRESA SINGOLA: Gioco la carta "
									+ deck.getValue() + " di " + deck.getSuit());

							return 1;
						}
					}
				}
			}
		}
		return 0;
	}
}