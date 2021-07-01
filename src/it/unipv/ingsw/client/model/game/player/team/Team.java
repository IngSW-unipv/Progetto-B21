package it.unipv.ingsw.client.model.game.player.team;

import java.util.ArrayList;

import it.unipv.ingsw.client.model.game.cards.Card;
import it.unipv.ingsw.client.model.game.player.types.Player;


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

	public void scopa() {
		numScope++;
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
