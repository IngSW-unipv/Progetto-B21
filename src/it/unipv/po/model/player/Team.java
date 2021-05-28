package it.unipv.po.model.player;

import java.util.ArrayList;

import it.unipv.po.model.cards.Card;


/**
 * 
 * @author Giuseppe Lentini
 */
public class Team {

	private ArrayList<Player> players;
	private ArrayList<Card> cardsCollected;
	private int numScope;
	private int totalPoints;

	public Team() {
		super();

		players = new ArrayList<Player>();
		cardsCollected = new ArrayList<Card>();
		numScope = 0;
	}

	public int getNumScope() {
		return numScope;
	}

	public void setNumScope(int numScope) {
		this.numScope = numScope;
	}
	public void setNumScope() {
		this.numScope=players.get(0).getNumScope()+players.get(1).getNumScope();
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


	public int getTotalPoints() {
		return totalPoints;
	}


	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}

}
