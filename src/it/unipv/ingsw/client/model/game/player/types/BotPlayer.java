package it.unipv.ingsw.client.model.game.player.types;

import java.util.ArrayList;

import it.unipv.ingsw.client.model.game.cards.Card;

/**
 * Questa classe permette l'uso di intelligenza artificiale semplice, con il Bot
 * che gioca una carta a caso tra quelle a disposizione.
 * 
 * @author Giuseppe Lentini
 */
public class BotPlayer extends Player {

	public BotPlayer() {
		super("bot");
	}

	public ArrayList<Card> playCard(ArrayList<Card> cardsOnBoard) {

		getCardsListTemp().clear();

		switch (makePresa(cardsOnBoard)) {

		case 1:

			return getCardsListTemp();

		case 0:

			getCardsListTemp().add(getDeck().get(0));

			return getCardsListTemp();
		}

		return null;
	}

	private int makePresa(ArrayList<Card> cardsOnBoard) {

		if (cardsOnBoard.isEmpty()) {

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