package it.unipv.po.model.player;

import it.unipv.po.model.cards.Card;

/**
 * Questa classe permette l'uso di intelligenza artificiale semplice, con il Bot che
 * gioca una carta a caso tra quelle a disposizione.
 * 
 * @author Giuseppe Lentini 
 */
public class BotPlayer extends Player{

	public BotPlayer() {
		super("Bot");
	}

	@Override
	public Card playCard() {		
		
		int i = 0;
		
		System.out.println("Gioco la carta " + getDeck().get(i).getValue() + " di " + getDeck().get(i).getSuit());

		return getDeck().get(i);		
	}
}
