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
	 * Viene effettuata la connessione al server specificato ed è passato lo stub del client
	 */
	public boolean connect(String hostname) {
		try {
			RemoteClientInterface clientStub = (RemoteClientInterface) UnicastRemoteObject.exportObject(this, 0);
			Registry registry = LocateRegistry.getRegistry(hostname, 6499);
			RemoteServerInterface serverStub = (RemoteServerInterface) registry.lookup("ScoponeServer");
			handler = serverStub.registerClient(clientStub);
			return true;
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/*
	 * Creazione della lobby
	 */
	public void makeLobby() {
		handler.makeLobby();
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
	
	public void nextTurn() {
		handler.nextTurn();
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
	}
	
	public ArrayList<Card> getCardsOnBoard() {
		return board;
	}


	@Override
	public void printMessage(String msg) {
		thread.writeMessage(msg);
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
	}

	@Override
	public void openGameView() {
		//la partita è iniziata, quindi si passa dalla visione della lobby a quella della partita
		
	}

	@Override
	public void openLobbyView() {
		//la partita è finita, quindi si passa dalla visione della partita a quella della lobby
		
	}

	@Override
	public void disconnect() {
		//disconessione dalla partita, si torna al menù
		
	}

/////////////// testing
public static void main(String args[]) {
Player p = new HumanPlayer("Pippo");
Client c = new Client(p);
c.connect("localhost");
System.out.println(c.handler.toString());

}
}
