package it.unipv.po.model.game.player.types;

import java.util.ArrayList;
import it.unipv.po.model.game.cards.Card;
import it.unipv.po.model.game.player.Player;

/**
 * Questa classe permette ad un giocatore umano di effettuare le sue mosse.
 * 
 * @author Paolo Falzone, Giuseppe Lentini
 */

//____________________COSTRUTTORE_________________
public class HumanPlayer extends Player {

	private Card cardPlayed;

	public HumanPlayer(String name) {
		super(name);

	}

//__________________GETTERS & SETTERS______________

	public Card getCardPlayed() {
		return cardPlayed;
	}

	public void setCardPlayed(Card cardPlayed) {
		this.cardPlayed = cardPlayed;
	}

//__________________METODI__________________________

	/**
	 * Questa è la stessa funzione del botPlayer. serve nel caso il giocatore umano
	 * non fa mosse.
	 */
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

			setCardPlayed(getDeck().get(i));
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
						setCardPlayed(deck);

						System.out.println("PRESA MULTIPLA");
						return 1;
					}

					else {

						if (deck.getValue() == table.getValue()) {

							getCardsListTemp().add(table);
							getCardsListTemp().add(deck);
							setCardPlayed(deck);

							System.out.println(getNickname() + getPlayerIndex() + "| "
									+ "PRESA SINGOLA: Gioco la carta " + deck.getValue() + " di " + deck.getSuit());

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
