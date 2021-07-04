package it.unipv.ingsw.server.utils;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import it.unipv.ingsw.client.model.game.cards.Card;

public interface RemoteHandlerInterface extends Remote{

	boolean makeNewLobby(String txt) throws RemoteException;
	boolean joinLobby(String lobbyCode) throws RemoteException;
	void startGame() throws RemoteException;
	void playCard(Card playedCard) throws RemoteException;
	void removeFromBoard(ArrayList<Card> takenCards) throws RemoteException;
}
