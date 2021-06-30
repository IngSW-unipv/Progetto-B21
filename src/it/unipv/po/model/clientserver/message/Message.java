package it.unipv.po.model.clientserver.message;

import java.io.Serializable;
import java.util.ArrayList;

import it.unipv.po.model.game.ScoponeGame;
import it.unipv.po.model.game.cards.Card;
import it.unipv.po.model.game.player.types.Player;

public class Message implements Serializable {


	private static final long serialVersionUID = -1435898470838100309L;
	private boolean start;
	private ArrayList<Card> cards;
	private Player player;
	private ScoponeGame scopone;

	public Message() {
		super();
		this.start = false;
		this.cards = new ArrayList<Card>();
		this.player = null;
		this.scopone = null;
		
	}

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public ArrayList<Card> getCards() {
		return cards;
	}

	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setScopone(ScoponeGame scopone) {
		this.scopone = scopone;
	}
}
