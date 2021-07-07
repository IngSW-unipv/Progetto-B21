package it.unipv.ingsw.server;

import java.util.ArrayList;

import it.unipv.ingsw.server.handlers.ClientHandler;

/**
 * Questa classe rappresenta una lobby del gioco.
 * 
 *
 */
public class Lobby extends Thread{

	private ArrayList<ClientHandler> players;
	private MultiplayerGame game;
	private String code;
	
	public Lobby(ClientHandler p1, String txt) {
		game = null;
		players = new ArrayList<ClientHandler>();
		players.add(p1);
		code = txt;
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
		System.out.println("+Lobby"+toString()+": " + "added player " + player.getNickname());
		return players.add(player);
	}
	
	public synchronized boolean removePlayer(ClientHandler player) {
		game.interrupt();
		game = null;
		System.out.println("+Lobby"+toString()+": " + "removed player "+ player.getNickname());
		return players.remove(player);
	}
	
	/**
	 * Inizia la partita.
	 * @return
	 */
	public synchronized boolean startGame() {
		
		if (game == null) {
			game = new MultiplayerGame(players);
			for (ClientHandler p : players) {
				p.setGame(game);
			}
			System.out.println("+Lobby"+toString()+": " + "game starting");
			try {
				sleep(1000);
			} catch (InterruptedException e) {}
			game.start();
			return true;
		}
		return false;
	}
	
	
}
