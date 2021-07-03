package it.unipv.ingsw.server.handlers;

import java.rmi.RemoteException;
import java.util.ArrayList;

import it.unipv.ingsw.client.model.game.cards.Card;
import it.unipv.ingsw.client.model.multiplayer.client.RemoteClientInterface;
import it.unipv.ingsw.server.Lobby;
import it.unipv.ingsw.server.MultiplayerGame;
import it.unipv.ingsw.server.ScoponeServer;
import it.unipv.ingsw.server.utils.RemoteHandlerInterface;

public class ClientHandler implements RemoteHandlerInterface, Handler{
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
		client.printMessage(msg);
	}
	
	public void requestMove() {
		client.play();
	}
	
	public void setHand(ArrayList<Card> hand) {
		client.setHand(hand);
	}
	
	public void setCardsOnBoard(ArrayList<Card> board) {
		client.setCardsOnBoard(board);
	}
	
	public void notifyGameStart() {
		client.openGameView();
	}
	
	public void notifyGameEnd() {
		client.openLobbyView();
	}
	
	public void disconnectFromLobby() {
		client.disconnect();
	}

	@Override
	public String makeLobby() {
		Lobby lobby = new Lobby(this);
		lobbyCode = lobby.getCode();
		server.lobbies.put(lobbyCode,lobby);
		return lobbyCode;
	}


	@Override
	public boolean joinLobby(String code) {
		if (server.checkLobbyCode(code)) {
			server.lobbies.get(code).addPlayer(this);
			lobbyCode = code;
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
		
	}





}
