package it.unipv.ingsw.client.model.multiplayer.client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import it.unipv.ingsw.client.model.game.cards.Card;

public interface RemoteClientInterface extends Remote{
	public String getPlayerName() throws RemoteException;

	public void printMessage(String msg);

	public void play();

	public void setHand(ArrayList<Card> hand);

	public void setCardsOnBoard(ArrayList<Card> board);

	public void openGameView();

	public void openLobbyView();

	public void disconnect();
}
