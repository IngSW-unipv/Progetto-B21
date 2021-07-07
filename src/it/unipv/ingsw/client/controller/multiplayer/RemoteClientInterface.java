package it.unipv.ingsw.client.controller.multiplayer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import it.unipv.ingsw.client.model.card.Card;
import it.unipv.ingsw.client.model.player.Team;

public interface RemoteClientInterface extends Remote{
	public String getPlayerName() throws RemoteException;

	public void printMessage(String msg) throws RemoteException;

	public void play() throws RemoteException;

	public void setHand(ArrayList<Card> hand) throws RemoteException;

	public void setCardsOnBoard(ArrayList<Card> board) throws RemoteException;

	public void openGameView() throws RemoteException;

	public void gameHasEnded(ArrayList<Team> teams) throws RemoteException;

	public void disconnect() throws RemoteException;

	public void scopaAlert(String nickname) throws RemoteException;


}
