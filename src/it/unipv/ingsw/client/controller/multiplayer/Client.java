package it.unipv.ingsw.client.controller.multiplayer;

import java.awt.Color;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import it.unipv.ingsw.client.controller.Controller;
import it.unipv.ingsw.client.controller.thread.MultiplayerThread;
import it.unipv.ingsw.client.model.card.Card;
import it.unipv.ingsw.client.model.player.Team;
import it.unipv.ingsw.client.model.player.types.HumanPlayer;
import it.unipv.ingsw.client.model.player.types.Player;
import it.unipv.ingsw.server.interfaces.RemoteHandlerInterface;
import it.unipv.ingsw.server.interfaces.RemoteServerInterface;

public class Client implements RemoteClientInterface {

	// ________________ATTRIBUTI________________
	private RemoteHandlerInterface handler;
	private MultiplayerThread thread;
	private HumanPlayer player;
	private ArrayList<Card> board;
	private Controller controller;
	private boolean turn;

	/**
	 * Crea un client.
	 * 
	 * @param player : il player a cui fa riferimento il client.
	 * @param player : il controller a cui fa riferimento il client.
	 */
	public Client(HumanPlayer player, Controller controller) {
		this.player = player;
		this.controller = controller;
		this.board = new ArrayList<Card>();
		this.turn = false;
	}

	// ______________GETTERS & SETTERS______________

	public boolean getTurn() {
		return turn;
	}

	public Player getPlayer() {
		return player;
	}

	public void setMultiplayerThread(MultiplayerThread thread) {
		this.thread = thread;
	}
	
	public synchronized void setTurn(boolean t) {
		turn = t;
	}
	

	// __________________METODI__________________



	/**
	 * Viene effettuata la connessione al server specificato.
	 * 
	 * @param hostname : lo stub del client.
	 * @throws RemoteException
	 */
	public boolean connect(String hostname) throws RemoteException {
		try {
			RemoteClientInterface clientStub = (RemoteClientInterface) UnicastRemoteObject.exportObject(this, 0);
			Registry registry = LocateRegistry.getRegistry(hostname, 6499);
			RemoteServerInterface serverStub = (RemoteServerInterface) registry.lookup("ScoponeServer");
			this.handler = serverStub.registerClient(clientStub);
			return true;
		} catch (NotBoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Creazione della lobby.
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
	 * Partecipazione ad una lobby già esistente
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
	public void startGame() {
		try {
			
			handler.startGame();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Viene giocata dalla mano la carta playedCard e vengono prese dalla board le
	 * carte takenCards
	 */
	public void makePlay(Card playedCard, ArrayList<Card> takenCards) {
		board.removeAll(takenCards);
		try {
			handler.playCard(playedCard);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
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

	@SuppressWarnings("unchecked")
	public void setCardsOnBoard(ArrayList<Card> cards) {
		controller.cardsOnBoardCreator(cards, 80, 78);
		controller.getGui().getGame().getGameAdvisor().setForeground(Color.BLACK);
		board.removeAll(cards);
		for (Card s : board) {
			if (controller.getCardsOnBoard().get(s) != null) {
				try {
					controller.getCardsOnBoard().get(s).setVisible(false);
				} catch (Exception e) {
				}
			}
		}
		board = (ArrayList<Card>) cards.clone();
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
		thread.setSeconds(20);
		turn = true;
	}

	@Override
	public void setHand(ArrayList<Card> hand) {
		player.setDeck(hand);
	}

	@Override
	public void openGameView() {
		try {
			controller.startGame(controller.getGui().getCreaLobby());

		} catch (Exception e) {}
		try {
			controller.startGame(controller.getGui().getEntraLobby());

		} catch (Exception e) {
		}
	}

	@Override
	public void gameHasEnded(ArrayList<Team> teams) {
		for (Card s : board) {
			try {
				controller.getCardsOnBoard().get(s).setVisible(false);
			} catch (Exception e) {
			}
		}
		board.clear();
		if (teams != null)
			controller.gameRecap(teams);
		try {
			wait(10000);
		} catch (Exception e ) {}
	}

	@Override
	public void disconnect() {
		try {
			handler.disconnectFromLobby();
		} catch (RemoteException e) {
		}
	}

	@Override
	public void scopaAlert(String nickname) throws RemoteException {
		controller.scopaAlert(new HumanPlayer(nickname));
	}
}