package it.unipv.ingsw.server.handlers;

import java.util.ArrayList;

import it.unipv.ingsw.client.model.game.cards.Card;
import it.unipv.ingsw.client.model.game.player.team.Team;

public interface Handler {

	void setTurnIndex(int i);

	void setTeamIndex(int i);
	
	void setHand(ArrayList<Card> hand);

	void requestMove();

	void setCardsOnBoard(ArrayList<Card> board);

	int getTurnIndex();
	
	int getTeamIndex();

	void sendMessage(String msg);
	
	String getNickname();

	void notifyGameStart();
	
	void notifyGameEnd(ArrayList<Team> teams);
}
