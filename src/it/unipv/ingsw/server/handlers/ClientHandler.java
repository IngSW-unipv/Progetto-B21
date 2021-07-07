package it.unipv.ingsw.server.handlers;

import java.rmi.RemoteException;
import java.util.ArrayList;

import it.unipv.ingsw.client.controller.multiplayer.RemoteClientInterface;
import it.unipv.ingsw.client.model.card.Card;
import it.unipv.ingsw.client.model.player.Team;
import it.unipv.ingsw.server.Lobby;
import it.unipv.ingsw.server.MultiplayerGame;
import it.unipv.ingsw.server.ScoponeServer;
import it.unipv.ingsw.server.interfaces.RemoteHandlerInterface;

public class ClientHandler implements RemoteHandlerInterface, Handler {
	private RemoteClientInterface client;
	private ScoponeServer server;
	private String lobbyCode;
	private MultiplayerGame game;
	private int turnIndex;
	private int teamIndex;
	
	public ClientHandler(RemoteClientInterface client, ScoponeServer server) {
		this.client = client;
		this.server = server;
	}
	
	
	public String getNickname() {
		try {
			return client.getPlayerName();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void setGame(MultiplayerGame game) {
		this.game = game;
	}
	
	@Override
	public void setTurnIndex(int i) {
		turnIndex = i;
	}
	
	public int getTurnIndex() {
		return turnIndex;
	}


	@Override
	public void setTeamIndex(int i) {
		teamIndex = i;
	
	}

	public int getTeamIndex() {
		return teamIndex;
	}
	
	public void sendMessage(String msg) {
		try {
			client.printMessage(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void requestMove() {
		try {
			client.play();
		} catch (Exception e) {
		}
	}
	
	public void setHand(ArrayList<Card> hand) {
		try {
			client.setHand(hand);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void setCardsOnBoard(ArrayList<Card> board) {
		try {
			client.setCardsOnBoard(board);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void notifyGameStart() {
		try {
			client.openGameView();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void notifyGameEnd(ArrayList<Team> teams) {
		try {
			client.gameHasEnded(teams);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void disconnectFromLobby() {
		server.lobbies.get(lobbyCode).removePlayer((ClientHandler) client);
	}

	@Override
	public boolean makeNewLobby(String txt) {
		boolean create;
		Lobby lobby = new Lobby(this, txt);
		create = server.addLobby(lobby);
		if (create == true)
			this.lobbyCode = lobby.getCode();
		return create;
	}


	@Override
	public boolean joinLobby(String code) {
		if (server.checkLobbyCode(code)) {
			server.lobbies.get(code).addPlayer(this);
			lobbyCode = code;
			System.out.println("new player in lobby " + this.getNickname());
			return true;
		}
		return false;
	}


	@Override
	public void startGame() {
		server.lobbies.get(lobbyCode).startGame();
	}


	@Override
	public void playCard(Card playedCard) {
		game.play(playedCard);		
	}


	@Override
	public void removeFromBoard(ArrayList<Card> takenCards) {
		game.remove(takenCards);
		game.setMove();
	}


	@Override
	public void sendScopaAlert(String nickname) {
		try {
			client.scopaAlert(nickname);
		} catch (RemoteException e) {}
	}
}
