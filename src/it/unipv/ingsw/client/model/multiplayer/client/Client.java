package it.unipv.ingsw.client.model.multiplayer.client;

import java.awt.Color;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import it.unipv.ingsw.client.controller.Controller;
import it.unipv.ingsw.client.controller.thread.MultiplayerThread;
import it.unipv.ingsw.client.model.game.cards.Card;
import it.unipv.ingsw.client.model.game.player.types.HumanPlayer;
import it.unipv.ingsw.client.model.game.player.types.Player;
import it.unipv.ingsw.server.utils.RemoteHandlerInterface;
import it.unipv.ingsw.server.utils.RemoteServerInterface;

public class Client implements RemoteClientInterface{
	private RemoteHandlerInterface handler;
	private MultiplayerThread thread;
	private HumanPlayer player;
	private ArrayList<Card> board;
	private Controller controller;
	private boolean turn = false;
	
	public Client(HumanPlayer player, Controller controller) {
		this.player = player;
		this.controller = controller;
		this.board = new ArrayList<Card>();
	}
	
	public synchronized void changeTurn() {
		turn = !turn;
	}
	
	public boolean getTurn() {
		return turn;
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
	public boolean makeLobby(String txt) {
		try {	
			return handler.makeNewLobby(txt);
		} catch (RemoteException e) {
		e.printStackTrace();
		}
		return false;
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
			e.printStackTrace();
		}
		takenCards.add(playedCard);
		try {
			handler.removeFromBoard(takenCards);
		} catch (RemoteException e) {
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
		controller.cardsOnBoardCreator(cards, controller.getX(), 48);
		controller.getGui().getGame().getGameAdvisor().setForeground(Color.BLACK);
		board.removeAll(cards);
		for (Card s : board) {
			if (controller.getCardsOnBoard().get(s) != null) {
				try {
					controller.getCardsOnBoard().get(s).setVisible(false);
				} catch (Exception e) {}
			}
		}
		board = cards;
	}
	
	public ArrayList<Card> getCardsOnBoard() {
		return board;
	}


	@Override
	public void printMessage(String msg) {
		controller.gameAdvisor(msg);
	}

	@Override
	public synchronized void play() {
		changeTurn();
		thread.notifyAll();
	}

	@Override
	public void setHand(ArrayList<Card> hand) {
		player.setDeck(hand);
	}

	@Override
	public void openGameView() {
		controller.startGame();
	}

	@Override
	public void openLobbyView(String lobbyCode) {
		for (Card s : board) {
			controller.getCardsOnBoard().get(s).setVisible(false);
		}
		controller.startEntraLobby();
	}

	@Override
	public void disconnect() {

		@SuppressWarnings("unused")
		int i = 0;
	}
	

}
