package it.unipv.ingsw.server;

import java.io.Serializable;
import java.util.ArrayList;

import it.unipv.ingsw.server.handlers.ClientHandler;

public class Lobby extends Thread implements Serializable{

	private static final long serialVersionUID = 2723652664412273354L;
	private ArrayList<ClientHandler> players;
	private MultiplayerGame game;
	private String code;
	
	public Lobby(ClientHandler p1, String txt) {
		game = null;
		players = new ArrayList<ClientHandler>();
		players.add(p1);
		code = txt;
		
		System.out.println("lobby created");
	}

	public ArrayList<ClientHandler> getPlayers() {
		return players;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public MultiplayerGame getGame() {
		return game;
	}
	
	public synchronized boolean addPlayer(ClientHandler player) {
		if (players.size() >= 4)
			return false;
		return players.add(player);
	}
	
	public synchronized boolean removePlayer(ClientHandler player) {
		return players.remove(player);
	}
	
	public synchronized boolean startGame() {
		if (game == null) {
			game = new MultiplayerGame(players);
			for (ClientHandler p : players) {
				p.setGame(game);
			}
			for (ClientHandler p : players) {
				p.notifyGameStart();
			}
			System.out.println("Starting game" + game.toString());
			try {
				sleep(1000);
			} catch (InterruptedException e) {}
			game.start();
			return true;
		}
		return false;
	}
	
	public synchronized boolean endGame() {
		game.endGame();
		game = null;
		for (ClientHandler p : players) {
			p.notifyGameEnd(code);
		}
		for (ClientHandler p : players) {
			p.setGame(game);
		}
		return true;
	}
	
}
