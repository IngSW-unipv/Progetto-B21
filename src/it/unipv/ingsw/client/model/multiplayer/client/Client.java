package it.unipv.ingsw.client.model.multiplayer.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import it.unipv.ingsw.client.model.game.player.types.HumanPlayer;

import it.unipv.ingsw.client.model.game.player.types.Player;
import it.unipv.ingsw.server.utils.RemoteHandlerInterface;
import it.unipv.ingsw.server.utils.RemoteServerInterface;

public class Client implements RemoteClientInterface{
	private RemoteHandlerInterface handler;
	private Player player;
	
	public Client(Player player) {
		this.player = player;
	}
	
	public void connect(String hostname) {
		try {
			RemoteClientInterface clientStub = (RemoteClientInterface) UnicastRemoteObject.exportObject(this, 0);
			Registry registry = LocateRegistry.getRegistry(hostname, 6499);
			RemoteServerInterface serverStub = (RemoteServerInterface) registry.lookup("ScoponeServer");
			handler = serverStub.registerClient(clientStub);
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String getPlayerName() throws RemoteException {
		return player.getNickname();
	}

/////////////// testing
	public static void main(String args[]) {
		Player p = new HumanPlayer("Pippo");
		Client c = new Client(p);
		c.connect("localhost");
		System.out.println(c.handler.toString());
		
	}
}
