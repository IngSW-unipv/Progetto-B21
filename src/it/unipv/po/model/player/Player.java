package it.unipv.po.model.player;

import java.util.ArrayList;

import it.unipv.po.model.cards.Card;
/**
 * 
 * @author Giuseppe Lentini
 */
public abstract class Player implements Actions{

	private String nickname;
	private ArrayList<Card> deck;
	private int teamIndex;
	private int playerIndex;
	private ArrayList<Card> temp;
	
	public Player(String nickname) {
		super();
		deck = new ArrayList<Card>();
		temp = new ArrayList<Card>();
		this.nickname = nickname;
	}

	public ArrayList<Card> getTemp() {
		return temp;
	}



	public void setTemp(ArrayList<Card> temp) {
		this.temp = temp;
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public ArrayList<Card> getDeck() {
		return deck;
	}

	public void setDeck(ArrayList<Card> deck) {

		this.deck = deck;
	}
}
