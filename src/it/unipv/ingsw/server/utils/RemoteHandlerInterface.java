package it.unipv.ingsw.server.utils;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import it.unipv.ingsw.client.model.game.cards.Card;
import it.unipv.ingsw.server.Lobby;

public interface RemoteHandlerInterface extends Remote{

	Lobby makeLobby(String txt) throws RemoteException;
	boolean joinLobby(String lobbyCode) throws RemoteException;
	void startGame() throws RemoteException;
	void playCard(Card playedCard) throws RemoteException;
	void removeFromBoard(ArrayList<Card> takenCards) throws RemoteException;
}
