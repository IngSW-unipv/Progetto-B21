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
		System.out.println("+"+toString()+": " + "aggiunto il giocatore " + player.getNickname());
		return players.add(player);
	}
	
	public synchronized boolean removePlayer(ClientHandler player) {
		game.interrupt();
		game = null;
		System.out.println("+"+toString()+": " + "rimosso il giocatore "+ player.getNickname());
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
			System.out.println("+"+toString()+": " + "inizio partita");
			try {
				sleep(1000);
			} catch (InterruptedException e) {}
			game.start();
			return true;
		}
		return false;
	}
	
	
}
