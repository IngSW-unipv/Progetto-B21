package it.unipv.ingsw.server.handlers;

import java.util.ArrayList;

import it.unipv.ingsw.client.model.game.cards.Card;
import it.unipv.ingsw.client.model.game.player.types.BotPlayer;
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
		ArrayList<Card> taken = new ArrayList<Card>();
		ArrayList<Card> played = new ArrayList<Card>();
		taken.addAll(board);
		played.addAll(player.getDeck());
		automaticPlay();
		taken.removeAll(board);
		played.removeAll(player.getDeck());

		game.play(played.get(0));
		game.remove(taken);
		game.setMove();
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

	@Override
	public void notifyGameStart() {}

}
