package it.unipv.po.model.player;

import java.util.ArrayList;

import it.unipv.po.model.cards.Card;

/**
 * Questa classe permette l'uso di intelligenza artificiale semplice, con il Bot
 * che gioca una carta a caso tra quelle a disposizione.
 * 
 * @author Giuseppe Lentini
 */
public class BotPlayer1 extends Player {

	public BotPlayer1() {
		super("Bot presa singola ");
	}

	public ArrayList<Card> playCard(ArrayList<Card> cardsOnBoard) {

		getTemp().clear();

		switch (makePresa(cardsOnBoard)) {

		case 1:

			return getTemp();

		case 0:

			int i = 0;
			
			System.out.println(getName() + getPlayerIndex() + "| " + "DEPOSITO(NO PRESA): Gioco la carta "
					+ getDeck().get(i).getValue() + " di " + getDeck().get(i).getSuit());
			getTemp().add(getDeck().get(i));
			
			return getTemp();
		}

		return null;
	}

	private int makePresa(ArrayList<Card> cardsOnBoard) {

		if (cardsOnBoard.isEmpty()) {

			int i = 0;

			System.out.println(getName() + getPlayerIndex() + "| " + "DEPOSITO: Gioco la carta "
					+ getDeck().get(i).getValue() + " di " + getDeck().get(i).getSuit());

			getTemp().add(getDeck().get(i));

			return 1;
		}

		else {

			for (Card deck : getDeck()) {

				for (Card table : cardsOnBoard) {

					if (deck.getValue() == table.getValue()) {

						getTemp().add(table);
						getTemp().add(deck);

						System.out.println(getName() + getPlayerIndex() + "| " + "PRESA SINGOLA: Gioco la carta " + deck.getValue() + " di " + deck.getSuit());
				
						return 1;
					}

					else {

						int counter = 0;
						ArrayList<Card> cardList = new ArrayList<Card>();

						counter += table.getValue();
						cardList.add(table);

						if (counter == deck.getValue()) {

							getTemp().addAll(cardList);
							getTemp().add(deck);

							System.out.println("PRESA MULTIPLA");
							return 1;
						}
					}
				}
			}
		}
		return 0;
	}
}
