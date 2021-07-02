package it.unipv.ingsw.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import it.unipv.ingsw.client.model.game.player.types.*;
import it.unipv.ingsw.client.model.multiplayer.client.RemoteClientInterface;
import it.unipv.ingsw.server.utils.RemoteHandlerInterface;
import it.unipv.ingsw.server.utils.RemoteServerInterface;

public class ScoponeServer implements RemoteServerInterface {
	Registry registry;
	private ArrayList<Player> players;
	private ArrayList<Lobby> lobbies;
	
	public ScoponeServer() {
		players = new ArrayList<Player>();
		lobbies = new ArrayList<Lobby>();
		try {
			registry = LocateRegistry.createRegistry(6499);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public Registry getRegistry() {
		return registry;
	}
	
	public boolean removeLobby() {
		return true;//sistemare
	}
	
	public void addLobby() {
		//sistemare
	}
	
	public boolean checkLobbyCode() {
		return false;//sistemare
	}

	@Override
	public RemoteHandlerInterface registerClient(RemoteClientInterface client) throws RemoteException {
		Player newPlayer = new HumanPlayer(client.getPlayerName());
		players.add(newPlayer);
		System.out.println("Added player "+newPlayer.getNickname());
		
		RemoteHandlerInterface handler = (RemoteHandlerInterface) UnicastRemoteObject.exportObject(new ClientHandler(client, this), 0);
		return handler;
	}
	
	
	
	public static void main(String args[]) {
		try {
			ScoponeServer server = new ScoponeServer();
			RemoteServerInterface skeleton = (RemoteServerInterface) UnicastRemoteObject.exportObject(server, 0);
			server.getRegistry().rebind("ScoponeServer", skeleton);
			
			System.out.println("Server ready");
		} catch (Exception e) {
            System.err.println("Server exception:");
            e.printStackTrace();
        }
	}

}
