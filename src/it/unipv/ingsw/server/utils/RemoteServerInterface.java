package it.unipv.ingsw.server.utils;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.unipv.ingsw.client.model.multiplayer.client.RemoteClientInterface;


public interface RemoteServerInterface extends Remote {

	RemoteHandlerInterface registerClient(RemoteClientInterface client) throws RemoteException;
	    
}
