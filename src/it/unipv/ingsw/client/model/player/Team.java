package it.unipv.ingsw.client.model.player;

import java.io.Serializable;
import java.util.ArrayList;

import it.unipv.ingsw.client.model.card.Card;
import it.unipv.ingsw.client.model.player.types.Player;

/**
 * Classe che rappresenta un team.
 * 
 * @author Giuseppe Lentini
 */
public class Team implements Serializable{

	private static final long serialVersionUID = -8856358148127602182L;
	// ________________ATTRIBUTI________________
	private ArrayList<Player> players;
	private ArrayList<Card> cardsCollected;
	private int numScope;
	private int totalPoints;
	private boolean setteBello;
	private boolean carte;
	private int nCarte;
	private boolean denari;
	private int nDenari;
	private boolean primiera;
	private int puntiPrimiera;
	private int puntiSmazzata;

	/**
	 * Crea un team.
	 */
	public Team() {
		players = new ArrayList<Player>();
		cardsCollected = new ArrayList<Card>();
		numScope = 0;
		nDenari = 0;
		puntiPrimiera = 0;
		setteBello = false;
		denari = false;
		primiera = false;
	}

	// ______________GETTERS & SETTERS______________

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

	public boolean isSetteBello() {
		return setteBello;
	}

	public void setSetteBello(boolean setteBello) {
		this.setteBello = setteBello;
	}

	public boolean isCarte() {
		return carte;
	}

	public void setCarte(boolean carte) {
		this.carte = carte;
	}

	public int getnCarte() {
		return nCarte;
	}

	public void setnCarte(int nCarte) {
		this.nCarte = nCarte;
	}

	public boolean isDenari() {
		return denari;
	}

	public void setDenari(boolean denari) {
		this.denari = denari;
	}

	public int getnDenari() {
		return nDenari;
	}

	public void setnDenari(int nDenari) {
		this.nDenari = nDenari;
	}

	public boolean isPrimiera() {
		return primiera;
	}

	public void setPrimiera(boolean primiera) {
		this.primiera = primiera;
	}

	public int getPuntiPrimiera() {
		return puntiPrimiera;
	}

	public void setPuntiPrimiera(int puntiPrimiera) {
		this.puntiPrimiera = puntiPrimiera;
	}

	public void setNumScope(int numScope) {
		this.numScope = numScope;
	}
	
	public int getPuntiSmazzata() {
		return puntiSmazzata;
	}

	public void setPuntiSmazzata(int puntiSmazzata) {
		this.puntiSmazzata = puntiSmazzata;
	}

	public void restart() {

		cardsCollected.clear();
		numScope = 0;
		nDenari = 0;
		puntiPrimiera = 0;
		setteBello = false;
		denari = false;
		primiera = false;
	}
}
