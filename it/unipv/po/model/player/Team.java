package it.unipv.po.model.player;

import java.util.ArrayList;

import it.unipv.po.model.cards.Card;
/**
 * 
 * @author Giuseppe Lentini
 */
public class Team {

	private int totalPoints;
	private ArrayList<Player> players;
	private ArrayList<Card> cardsCollected;
	
	public Team() {
		super();
		
		totalPoints = 0;
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
}
