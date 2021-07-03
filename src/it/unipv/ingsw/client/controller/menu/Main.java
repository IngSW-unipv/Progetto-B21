package it.unipv.ingsw.client.controller.menu;

import java.rmi.RemoteException;
import java.util.ArrayList;

import it.unipv.ingsw.client.controller.Controller;
import it.unipv.ingsw.client.controller.thread.MultiplayerThread;
import it.unipv.ingsw.client.controller.thread.SingleplayerThread;
import it.unipv.ingsw.client.model.game.Game;
import it.unipv.ingsw.client.model.game.player.types.*;
import it.unipv.ingsw.client.model.multiplayer.client.Client;
import it.unipv.ingsw.client.sounds.Music;
import it.unipv.ingsw.server.Lobby;

public class Main {

	private Game game;
	private Music music;
	private String nickname;
	private String nomeLobby;
	private Controller controller;
	private Music sound;
	private HumanPlayer player;
	private Client client;
	private SingleplayerThread playerThread;
	private SingleplayerThread[] threads;

	public Main() {
		this.music = new Music();
		music.playMusic("music.wav");
	}

	public SingleplayerThread getPlayerThread() {
		return playerThread;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String txt) {
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

	public SingleplayerThread[] getThreads() {
		return threads;
	}

	public void setThreads(SingleplayerThread[] threads) {
		this.threads = threads;
	}

	public Client getClient() {
		return client;
	}

	public String getNomeLobby() {
		return nomeLobby;
	}

	public void setNomeLobby(String nomeLobby) {
		this.nomeLobby = nomeLobby;
	}

	public void singleplayer() {
		player = new HumanPlayer(nickname);
		ArrayList<Player> p = new ArrayList<Player>();
		p.add(player);
		game = new Game(p);
		sound = new Music();
		game.start();
		this.threads = new SingleplayerThread[4];
		for (int i = 0; i < 4; i++) {
			threads[i] = new SingleplayerThread(game, game.getPlayers().get(i), controller);
		}
		this.playerThread = threads[0];
		for (int i = 0; i < 4; i++) {
			threads[i].start();
		}
	}

	public void multiplayer() {
		player = new HumanPlayer(nickname);
		client = new Client(player);
		MultiplayerThread thread = new MultiplayerThread(client, controller);
		client.setMultiplayerThread(thread);
		client.connect("localhost");
	}

	public boolean connectToServer(String hostname) {
		return client.connect(hostname);
	}

	public Lobby creaLobby() throws RemoteException {
		return client.makeLobby(nomeLobby);
	}

	public boolean entraLobby(String code) {
		return client.joinLobby(code);
	}
}
