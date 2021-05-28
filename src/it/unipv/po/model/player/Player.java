package it.unipv.po.model.player;

import java.util.ArrayList;

import it.unipv.po.model.cards.Card;
/**
 * 
 * @author Giuseppe Lentini
 */
public abstract class Player implements Actions{

	private String name;
	private ArrayList<Card> deck;
	private int teamIndex;
	private int playerIndex;
	private int numScope;
	public Player(String name) {
		super();
		deck = new ArrayList<Card>();
		this.name = name;
		this.numScope=0;
	}
	
	public int getNumScope() {
		return numScope;
	}

	public void setNumScope(int numScope) {
		this.numScope = numScope;
	}
	public void incrementNumScope() {
		this.numScope++;
	}

	public String toString() {
		return name;
	}

	public int getTeamIndex() {
		return teamIndex;
	}

	public void setTeamIndex(int teamIndex) {
		this.teamIndex = teamIndex;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Card> getDeck() {
		return deck;
	}

	public void setDeck(ArrayList<Card> deck) {

		this.deck = deck;
	}
}
