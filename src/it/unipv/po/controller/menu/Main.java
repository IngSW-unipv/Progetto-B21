package it.unipv.po.controller.menu;

import java.io.IOException;
import java.util.ArrayList;
import it.unipv.po.controller.Controller;
import it.unipv.po.controller.thread.PlayerThread;
import it.unipv.po.model.clientserver.client.Client;
import it.unipv.po.model.clientserver.server.MainServer;
import it.unipv.po.model.game.ScoponeGame;
import it.unipv.po.model.game.player.types.*;
import it.unipv.po.sounds.Music;

public class Main {

	private ScoponeGame scopone;
	private Music music;
	private String txt;
	private Controller controller;
	private PlayerThread thread;
	private Music sound;
	private ArrayList<Player> players;
	private MainServer server;
	private ArrayList<PlayerThread> threads;

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

	public ScoponeGame getScopone() {
		return scopone;
	}

	public void setScopone(ScoponeGame scopone) {
		this.scopone = scopone;
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
	
	public ArrayList<PlayerThread> getThreads() {
		return threads;
	}

	public void setThreads(ArrayList<PlayerThread> threads) {
		this.threads = threads;
	}

	public ScoponeGame singlePlayer() {

		this.players = new ArrayList<Player>();
		this.threads = new ArrayList<PlayerThread>();
		HumanPlayer human = new HumanPlayer(txt);

		players.add(human);
		players.add(new BotPlayer());
		players.add(new BotPlayer());
		players.add(new BotPlayer());

		this.scopone = new ScoponeGame(players);
		this.sound = new Music();

		PlayerThread t1 = new PlayerThread(scopone, players.get(0), controller);
		setThread(t1);
		PlayerThread t2 = new PlayerThread(scopone, players.get(1), controller);
		PlayerThread t3 = new PlayerThread(scopone, players.get(2), controller);
		PlayerThread t4 = new PlayerThread(scopone, players.get(3), controller);
		t1.start();
		t2.start();
		t3.start();
		t4.start();

		threads.add(t1);
		threads.add(t2);
		threads.add(t3);
		threads.add(t4);

		return scopone;
	}

	public MainServer creaLobby() {

		try {
			this.server = new MainServer();
			server.start();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Client client = new Client(controller, server.getHostName());
		client.start();
			
		return server;
	}

	public void entraLobby() {

	}
}
