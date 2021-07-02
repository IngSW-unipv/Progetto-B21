package it.unipv.ingsw.client.model.multiplayer;

import java.util.ArrayList;

import it.unipv.ingsw.client.model.game.Game;
import it.unipv.ingsw.client.model.game.player.types.Player;

public class Lobby extends Thread{
	private ArrayList<Player> players;
	private Game game;
	private String code;
	
	public Lobby(Player p1) {
		game = null;
		players = new ArrayList<Player>();
		players.add(p1);
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Game getGame() {
		return game;
	}
	
	public boolean addPlayer(Player player) {
		if (players.size() >= 4)
			return false;
		return players.add(player);
	}
	
	public boolean removePlayer(Player player) {
		return players.remove(player);
	}
	
	public boolean startGame() {
		if (game != null) {
			game = new Game(players);
			game.start();
			return true;
		}
		return false;
	}
	
	public boolean endGame() {
		//metodo di game che fa finire la partita
		return true;
	}
}
