package it.unipv.ingsw.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

import it.unipv.ingsw.client.model.multiplayer.client.RemoteClientInterface;
import it.unipv.ingsw.server.handlers.ClientHandler;
import it.unipv.ingsw.server.utils.RemoteHandlerInterface;
import it.unipv.ingsw.server.utils.RemoteServerInterface;

public class ScoponeServer implements RemoteServerInterface {
	private Registry registry;
	private ArrayList<ClientHandler> players;
	public HashMap<String,Lobby> lobbies;
	
	public ScoponeServer() {
		players = new ArrayList<ClientHandler>();
		lobbies = new HashMap<String,Lobby>();
		try {
			registry = LocateRegistry.createRegistry(6499);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public Registry getRegistry() {
		return registry;
	}
	
	public boolean removeLobby(String code) {
		boolean bool = lobbies.containsKey(code);
		lobbies.remove(code);
		return bool;
	}
	
	public void addLobby(Lobby lobby) {
		lobbies.put(lobby.getCode(), lobby);
	}
	
	public boolean checkLobbyCode(String code) {
		return lobbies.containsKey(code);
	}

	@Override
	public RemoteHandlerInterface registerClient(RemoteClientInterface client) throws RemoteException {
		ClientHandler newPlayer = new ClientHandler(client, this);
		players.add(newPlayer);
		System.out.println("Added player "+newPlayer.getNickname());
		RemoteHandlerInterface handler = (RemoteHandlerInterface) UnicastRemoteObject.exportObject(newPlayer, 0);
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
