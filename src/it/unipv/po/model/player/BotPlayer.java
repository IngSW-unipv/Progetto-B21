package it.unipv.po.model.player;

import it.unipv.po.model.cards.Card;

import java.util.ArrayList;
import java.util.Random;
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
	
	public BotPlayer(String name) {
		super(name);
	}

	@Override
	public Card playCard() {		
		Random random=new Random();
		//int i = 0;
		int i=random.nextInt(getDeck().size());
		
		//System.out.println("Gioco la carta " + getDeck().get(i).getValue() + " di " + getDeck().get(i).getSuit());
		System.out.println(this+" gioca la carta " + getDeck().get(i).toString());


		return getDeck().get(i);		
	}

	@Override
	public ArrayList<Card> chooseCards(ArrayList<Card> cardsOnBoard) {
		// TODO Auto-generated method stub
		return null;
	}
}
