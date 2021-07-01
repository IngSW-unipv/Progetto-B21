package it.unipv.ingsw.client.controller.menu;

import java.io.IOException;
import java.util.ArrayList;

import it.unipv.ingsw.client.controller.Controller;
import it.unipv.ingsw.client.controller.thread.PlayerThread;
import it.unipv.ingsw.client.model.game.Game;
import it.unipv.ingsw.client.model.game.player.types.*;
import it.unipv.ingsw.client.model.multiplayer.clientserverOLD.ClientOld;
import it.unipv.ingsw.client.model.multiplayer.clientserverOLD.MainServer;
import it.unipv.ingsw.client.sounds.Music;

public class Main {

	private Game game;
	private Music music;
	private String txt;
	private Controller controller;
	private PlayerThread thread;
	private Music sound;
	private ArrayList<Player> players;
	private MainServer server;

	public Main() {
		super();

		this.music = new Music();
		music.playMusic("music.wav");
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
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

	public PlayerThread getThread() {
		return thread;
	}

	public void setThread(PlayerThread thread) {
		this.thread = thread;
	}

	public Music getSound() {
		return sound;
	}

	public void setSound(Music click) {
		this.sound = click;
	}

	public Game singlePlayer() {

		this.players = new ArrayList<Player>();
		HumanPlayer human = new HumanPlayer(txt);

		players.add(human);
		players.add(new BotPlayer());
		players.add(new BotPlayer());
		players.add(new BotPlayer());

		this.game = new Game(players);
		this.sound = new Music();

		PlayerThread t1 = new PlayerThread(game, players.get(0), controller);
		setThread(t1);
		PlayerThread t2 = new PlayerThread(game, players.get(1), controller);
		PlayerThread t3 = new PlayerThread(game, players.get(2), controller);
		PlayerThread t4 = new PlayerThread(game, players.get(3), controller);
		t1.start();
		t2.start();
		t3.start();
		t4.start();

		return game;
	}

	public MainServer creaLobby() {

		try {
			this.server = new MainServer();
			server.start();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ClientOld client = new ClientOld(controller, server.getHostName());
		client.start();
			
		return server;
	}

	public void entraLobby() {

	}
}
