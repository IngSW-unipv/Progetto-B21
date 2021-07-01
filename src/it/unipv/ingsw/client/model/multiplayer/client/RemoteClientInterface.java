package it.unipv.ingsw.client.model.multiplayer.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.unipv.ingsw.server.utils.RemoteHandlerInterface;

public interface RemoteClientInterface extends Remote{
	public String getPlayerName() throws RemoteException;
}
