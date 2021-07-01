package it.unipv.ingsw.client.model.multiplayer.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import it.unipv.ingsw.client.model.game.cards.Card;
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
	
	/*
	 * Viene effettuata la connessione al server specificato ed è passato lo stub del client
	 */
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
	
	/*
	 * Creazione della lobby
	 */
	public boolean makeLobby() {
		return handler.makeLobby();
	}
	
	/*
	 * Partecipazione ad una lobby già esistente
	 */
	public boolean joinLobby(String lobbyCode) {
		return handler.joinLobby(lobbyCode);
	}
	
	/*
	 * Si fa iniziare la partita
	 */
	public void startGame() {
		handler.startGame();
	}
	
	/*
	 * Viene giocata dalla mano la carta playedCard e vengono prese dalla board le carte takenCards
	 */
	public void makePlay(Card playedCard, ArrayList<Card> takenCards) {
		handler.playCard(playedCard);
		handler.removeFromBoard(takenCards);
	}
	
	/*
	 * E' passato all'handler il nome del giocatore
	 */
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
