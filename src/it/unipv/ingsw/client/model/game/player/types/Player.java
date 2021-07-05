package it.unipv.ingsw.client.model.game.player.types;

import java.io.Serializable;
import java.util.ArrayList;

import it.unipv.ingsw.client.model.game.cards.Card;
import it.unipv.ingsw.client.model.game.player.Actions;

/**
 * 
 * Classe astratta che definisce il giocatore generico che parteciperà alla partita * 
 * @author Giuseppe Lentini
 */
public abstract class Player implements Actions, Serializable {

	//________________ATTRIBUTI________________
	private static final long serialVersionUID = -3954006175628361968L;
	private String nickname;
	private ArrayList<Card> deck;
	private int teamIndex;
	private int playerIndex;
	private ArrayList<Card> cardsListTemp;
	private boolean isCardSelected;
	private boolean scopa;

	
	/**
	 * Crea un giocatore.
	 * @param nickname : il nome del giocatore.
	 */
	public Player(String nickname) {
		super();
		deck = new ArrayList<Card>();
		cardsListTemp = new ArrayList<Card>();
		this.nickname = nickname;
		isCardSelected = false;
	}

	//______________GETTERS & SETTERS______________
	
	public boolean isCardSelected() {

		return isCardSelected;
	}

	public void setCardSelected() {
		this.isCardSelected = !isCardSelected;
	}

	public ArrayList<Card> getCardsListTemp() {
		return cardsListTemp;
	}

	public void setCardsListTemp(ArrayList<Card> temp) {
		this.cardsListTemp = temp;
	}

	public boolean isScopa() {
		return scopa;
	}

	public void setScopa() {
		this.scopa = !scopa;
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

	public abstract TypePlayer typePlayer();
}
