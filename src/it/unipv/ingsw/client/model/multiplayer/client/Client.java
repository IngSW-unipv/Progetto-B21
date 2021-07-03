package it.unipv.ingsw.client.model.multiplayer.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import it.unipv.ingsw.client.controller.thread.MultiplayerThread;
import it.unipv.ingsw.client.model.game.cards.Card;
import it.unipv.ingsw.client.model.game.player.types.HumanPlayer;

import it.unipv.ingsw.client.model.game.player.types.Player;
import it.unipv.ingsw.server.Lobby;
import it.unipv.ingsw.server.utils.RemoteHandlerInterface;
import it.unipv.ingsw.server.utils.RemoteServerInterface;

public class Client implements RemoteClientInterface{
	private RemoteHandlerInterface handler;
	private MultiplayerThread thread;
	private Player player;
	private ArrayList<Card> board;
	
	
	public Client(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setMultiplayerThread(MultiplayerThread thread) {
		this.thread = thread;
	}

	/*
	 * Viene effettuata la connessione al server specificato ed � passato lo stub del client
	 */
	public boolean connect(String hostname) {
		try {
			RemoteClientInterface clientStub = (RemoteClientInterface) UnicastRemoteObject.exportObject(this, 0);
			Registry registry = LocateRegistry.getRegistry(hostname, 6499);
			RemoteServerInterface serverStub = (RemoteServerInterface) registry.lookup("ScoponeServer");
			this.handler = serverStub.registerClient(clientStub);
			return true;
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/*
	 * Creazione della lobby
	 */
	public Lobby makeLobby(String txt) throws RemoteException {
			return handler.makeLobby(txt);
	}
	
	/*
	 * Partecipazione ad una lobby gi� esistente
	 */
	public boolean joinLobby(String lobbyCode) {
		try {
			return handler.joinLobby(lobbyCode);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	/*
	 * Si fa iniziare la partita
	 */
	public void startGame()  {
		try {
			handler.startGame();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * Viene giocata dalla mano la carta playedCard e vengono prese dalla board le carte takenCards
	 */
	public void makePlay(Card playedCard, ArrayList<Card> takenCards) {
		try {
			handler.playCard(playedCard);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		takenCards.add(playedCard);
		try {
			handler.removeFromBoard(takenCards);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * E' passato all'handler il nome del giocatore
	 */
	@Override
	public String getPlayerName() throws RemoteException {
		return player.getNickname();
	}
	
	public void setCardsOnBoard(ArrayList<Card> cards) {
		board = cards;
		thread.updateBoard();
	}
	
	public ArrayList<Card> getCardsOnBoard() {
		return board;
	}


	@Override
	public void printMessage(String msg) {
		thread.writeMessage(msg);
		//refresh the view
	}

	@Override
	public void play() {
		ArrayList<Card> taken = board;
		ArrayList<Card> played = player.getDeck();
		thread.play();
		thread.updateBoard();
		taken.removeAll(board);
		played.removeAll(played);
		makePlay(played.get(0), taken);
		thread.endTurn();
	}

	@Override
	public void setHand(ArrayList<Card> hand) {
		player.setDeck(hand);
		thread.updateBoard();
	}

	@Override
	public void openGameView() {

		@SuppressWarnings("unused")
		int i = 0;
	}

	@Override
	public void openLobbyView() {

		@SuppressWarnings("unused")
		int i = 0;
	}

	@Override
	public void disconnect() {

		@SuppressWarnings("unused")
		int i = 0;
	}

/////////////// testing
public static void main(String args[]) {
Player p = new HumanPlayer("Pippo");
Client c = new Client(p);
c.connect("localhost");
System.out.println(c.handler.toString());

}
}
