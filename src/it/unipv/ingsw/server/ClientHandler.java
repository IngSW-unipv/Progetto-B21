package it.unipv.ingsw.server;

import it.unipv.ingsw.client.model.multiplayer.client.RemoteClientInterface;
import it.unipv.ingsw.server.utils.RemoteHandlerInterface;

public class ClientHandler implements RemoteHandlerInterface{
	RemoteClientInterface client;
	
	
	public ClientHandler(RemoteClientInterface client) {
		this.client = client;
	}

}
