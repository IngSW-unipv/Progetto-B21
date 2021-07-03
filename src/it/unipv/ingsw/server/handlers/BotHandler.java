package it.unipv.ingsw.server.handlers;

import java.util.ArrayList;

import it.unipv.ingsw.client.model.game.cards.Card;
import it.unipv.ingsw.client.model.game.player.types.BotPlayer;

public class BotHandler implements Handler{
	private int turnIndex;
	private int teamIndex;
	private BotPlayer player;
	private String nickname;
	private ArrayList<Card> board;
	
	public BotHandler() {
		player = new BotPlayer();
		nickname = "Bot" + (int) (Math.random()*9999);
		board = new ArrayList<Card>();
	}
	
	@Override
	public void setTurnIndex(int i) {
		turnIndex = i;
	}

	@Override
	public void setTeamIndex(int i) {
		teamIndex = i;
	}

	@Override
	public void setHand(ArrayList<Card> hand) {
		player.setDeck(hand);
	}

	@Override
	public void requestMove() {
		player.playCard(board);
	}

	@Override
	public void setCardsOnBoard(ArrayList<Card> board) {
		this.board = board;
		
	}

	@Override
	public int getTurnIndex() {
		return turnIndex;
	}

	@Override
	public int getTeamIndex() {
		return teamIndex;
	}

	@Override
	public void sendMessage(String msg) {}

	@Override
	public String getNickname() {
		return nickname;
	}

}
