package it.unipv.ingsw.client.controller.menu;

import java.util.ArrayList;

import it.unipv.ingsw.client.controller.Controller;
import it.unipv.ingsw.client.controller.thread.PlayerThread;
import it.unipv.ingsw.client.model.game.Game;
import it.unipv.ingsw.client.model.game.player.types.*;
import it.unipv.ingsw.client.model.multiplayer.Lobby;
import it.unipv.ingsw.client.model.multiplayer.client.Client;
import it.unipv.ingsw.client.sounds.Music;

public class Main {

	private Game game;
	private Music music;
	private String nickname;
	private Controller controller;
	private Music sound;
	private Lobby lobby;
	private HumanPlayer player;
	private Client client;
	private PlayerThread playerThread;

	public Main() {
		this.music = new Music();
		music.playMusic("music.wav");
	}
	
	public PlayerThread getPlayerThread() {
		return playerThread;
	}
	
	public String getTxt() {
		return nickname;
	}

	public void setTxt(String txt) {
		this.nickname = txt;
	}
	
	public Player getPlayer() {
		return player;
	}

	public Music getMusic() {
		return music;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game scopone) {
		this.game = scopone;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public Music getSound() {
		return sound;
	}

	public void setSound(Music click) {
		this.sound = click;
	}

	public void singleplayer() {
		player = new HumanPlayer(nickname);
		ArrayList<Player> p = new ArrayList<Player>();
		p.add(player);
		game = new Game(p);
		sound = new Music();
		game.start();
		PlayerThread[] threads = new PlayerThread[4];
		for (int i = 0; i < 4; i++) {
			threads[i] = new PlayerThread(game, game.getPlayers().get(i), controller);
		}
		this.playerThread = threads[0];
			for (int i = 0; i < 4; i++) {
			threads[i].start();
		}
			
		
	}
	
	public void multiplayer() {
		client = new Client(player);
	}
	
	public boolean connectToServer(String hostname) {
		return client.connect(hostname);
	}
	
	public Lobby creaLobby() {
		lobby = new Lobby(player);
		Client client = new Client(player);
		return lobby;
	}

	public void entraLobby() {
		
	}


}
