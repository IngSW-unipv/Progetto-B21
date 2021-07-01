package it.unipv.ingsw.client.model.multiplayer.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteClientInterface extends Remote{
	public String getPlayerName() throws RemoteException;
}
