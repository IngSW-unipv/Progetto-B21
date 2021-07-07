package it.unipv.ingsw.server.handlers;

import java.util.ArrayList;

import it.unipv.ingsw.client.model.card.Card;
import it.unipv.ingsw.client.model.player.Team;
import it.unipv.ingsw.client.model.player.types.BotPlayer;
import it.unipv.ingsw.server.MultiplayerGame;

public class BotHandler implements Handler{
	private int turnIndex;
	private int teamIndex;
	private MultiplayerGame game;
	private BotPlayer player;
	private String nickname;
	private ArrayList<Card> board;
	
	public BotHandler(MultiplayerGame game) {
		this.game = game;
		player = new BotPlayer();
		nickname = "Bot" + (int) (Math.random()*9999);
		board = new ArrayList<Card>();
	}
	
	public synchronized boolean automaticPlay() {
		switch (player.playCard(board).size()) {
		case 1:
			board.addAll(player.getCardsListTemp());
			player.getDeck().removeAll(player.getCardsListTemp());
			return true;
		default:
			int temp = 0;
			for (int i = 0; i < player.getCardsListTemp().size() - 1; i++) {
				temp += player.getCardsListTemp().get(i).getValue();
			}
			if (temp == player.getCardsListTemp().get(player.getCardsListTemp().size() - 1).getValue()) {
				board.removeAll(player.getCardsListTemp());
				player.getDeck().remove(player.getCardsListTemp().get(player.getCardsListTemp().size() - 1));
				if (board.isEmpty()) {
					player.setScopa();
				}
				return true;
			}
			else {
				automaticPlay();
				return false;
			}
		}
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

		automaticPlay();

		game.play(player.getCardsListTemp().get(player.getCardsListTemp().size() - 1));
		if (player.getCardsListTemp().size() == 1) { //se == true il giocatore non ha effettuato una presa
			player.getCardsListTemp().clear();
		}
		game.remove(player.getCardsListTemp());
		game.setMove();
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public void setCardsOnBoard(ArrayList<Card> board) {
		this.board.clear();
		this.board = (ArrayList<Card>) board.clone();
		
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

	@Override
	public void notifyGameStart() {}

	@Override
	public void notifyGameEnd(ArrayList<Team> teams) {}

}
