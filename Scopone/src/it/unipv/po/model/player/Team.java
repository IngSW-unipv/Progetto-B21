package it.unipv.po.model.player;

import java.util.ArrayList;

import it.unipv.po.model.cards.Card;
import it.unipv.po.model.cards.Suit;
/**
 * 
 * @author Giuseppe Lentini
 */
public class Team {

	private int totalPoints;
	private ArrayList<Player> players;
	private ArrayList<Card> cardsCollected;
	private int numScope;
	
	public Team() {
		super();
		
		totalPoints = 0;
		numScope = 0;
		players = new ArrayList<Player>();
		cardsCollected = new ArrayList<Card>();
	}

	public int getNumScope() {
		return numScope;
	}

	public void setNumScope(int numScope) {
		this.numScope = numScope;
	}

	public int getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public ArrayList<Card> getCardsCollected() {
		return cardsCollected;
	}

	public void setCardsCollected(ArrayList<Card> cardsCollected) {
		this.cardsCollected = cardsCollected;
	}
	
	public static int finalScore(ArrayList<Card> cardsCollected, int numScope) {
		
		int score = 0;
		int numDenari = 0;
		int numSette = 0;
		
		for (Card card : cardsCollected) {
			
			if (card.getValue() == 7 && card.getSuit() == Suit.DENARI) {
				// punto settebello
				score += 1;
			}
			
			if (card.getSuit() == Suit.DENARI) {
				numDenari += 1;
			}
			
			if (card.getValue() == 7) {
				numSette += 1;
			}
		}
		
		if (cardsCollected.size() > 20) {
			// punto carte
			score += 1;
		}
		if (numDenari > 5) {
			// punto denari
			score += 1;
		}
		if (numSette == 4) {
			// punto primiera
			score += 1;
		}

		return score + numScope;
	}
}
