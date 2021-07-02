package it.unipv.ingsw.server.utils;

import java.rmi.Remote;
import java.util.ArrayList;

import it.unipv.ingsw.client.model.game.cards.Card;

public interface RemoteHandlerInterface extends Remote{

	boolean makeLobby();
	boolean joinLobby(String lobbyCode);
	void startGame();
	void playCard(Card playedCard);
	void removeFromBoard(ArrayList<Card> takenCards);
	void nextTurn();
	
}
