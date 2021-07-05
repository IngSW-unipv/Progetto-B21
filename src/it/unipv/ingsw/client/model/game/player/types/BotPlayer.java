package it.unipv.ingsw.client.model.game.player.types;

import java.util.ArrayList;

import it.unipv.ingsw.client.model.game.cards.Card;

/**
 * Questa classe permette l'uso di un'intelligenza artificiale semplice, con il Bot
 * che gioca una carta a caso tra quelle a disposizione.
 * 
 * @author Giuseppe Lentini
 */
public class BotPlayer extends Player {
	
	
	/**
	 * Crea un bot.
	 */
	public BotPlayer() {
		super("Bot" + (int) (Math.random()*9999));
	}
	
	/**
	 * Crea un bot con nome.
	 * @param name : il nome del bot.
	 */
	public BotPlayer(String name) {
		super(name);
	}
	
	//__________________METODI__________________
	
	/**
	 * Metodo che permette al bot di giocare una carta ed effettuare una presa.
	 * @param cardsOnBoard : ArrayList di carte presenti sul tavolo
	 */
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
	
	/**
	 * Metodo privato che permette al bot di effettuare una presa.
	 * 
	 * @param cardsOnBoard : ArrayList di carte presenti sul tavolo
	 * @return un intero per usarlo nello switch della funzione playCard
	 */

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