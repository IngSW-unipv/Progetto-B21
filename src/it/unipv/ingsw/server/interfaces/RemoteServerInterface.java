package it.unipv.ingsw.server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.unipv.ingsw.client.controller.multiplayer.RemoteClientInterface;


public interface RemoteServerInterface extends Remote {

	RemoteHandlerInterface registerClient(RemoteClientInterface client) throws RemoteException;
	    
}
