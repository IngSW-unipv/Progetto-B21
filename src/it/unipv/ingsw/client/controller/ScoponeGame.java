package it.unipv.ingsw.client.controller;

import java.rmi.RemoteException;
import java.util.ArrayList;

import it.unipv.ingsw.client.controller.multiplayer.Client;
import it.unipv.ingsw.client.controller.multiplayer.ServerIP;
import it.unipv.ingsw.client.controller.thread.MultiplayerThread;
import it.unipv.ingsw.client.controller.thread.PlayerThread;
import it.unipv.ingsw.client.controller.thread.SingleplayerThread;
import it.unipv.ingsw.client.model.Game;
import it.unipv.ingsw.client.model.player.types.*;
import it.unipv.ingsw.client.sounds.Music;
import it.unipv.ingsw.client.view.ScoponeGUI;

/**
 * Questa classe rappresenta il gioco eseguibile.
 * 
 *
 */
public class ScoponeGame {

	public static void main(String[] args) {

		ScoponeGame menu = new ScoponeGame();
		ScoponeGUI gui = new ScoponeGUI();

		if (args.length != 0)
			ServerIP.getInstance().setIp(args[0]);

		Controller controller = new Controller(menu, gui);
		menu.setController(controller);
		gui.setVisible(true);
	}

	//____________ATTRIBUTI____________
	private Game game;
	private String nickname;
	private String nomeLobby;
	private Controller controller;
	private Music sound;
	private HumanPlayer player;
	private Music music;
	private Client client;
	private PlayerThread playerThread;
	private SingleplayerThread[] threads;
	private boolean statusServer;

	//__________COSTRUTTORE__________
	public ScoponeGame() {
		this.music = new Music();
		this.sound = new Music();
		music.playMusic("music.wav");
		statusServer = false;
	}

	//__________GETTERS&SETTERS_________
	public PlayerThread getPlayerThread() {
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

	public boolean isStatusServer() {
		return statusServer;
	}

	/**
	 * Questo metodo avvia la modalità singleplayer.
	 */
	public void singleplayer() {
		player = new HumanPlayer(nickname);
		ArrayList<Player> p = new ArrayList<Player>();
		p.add(player);
		game = new Game(p);
		game.start();
		this.threads = new SingleplayerThread[4];
		for (int i = 0; i < 4; i++) {
			threads[i] = new SingleplayerThread(game, game.getPlayers().get(i), controller);
		}
		this.playerThread = threads[0];
		for (int i = 0; i < 4; i++) {
			((SingleplayerThread) threads[i]).start();
		}
	}

	/**
	 * Questo metodo fa connettere il client con il server.
	 */
	public void clientConnect() {
		player = new HumanPlayer(nickname);
		client = new Client(player, controller);
		this.playerThread = new MultiplayerThread(client, controller);
		client.setMultiplayerThread((MultiplayerThread) playerThread);
		statusServer = false;
		try {
			client.connect(ServerIP.getInstance().getIp());
			statusServer = true;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			controller.serverError();
		}
		if (statusServer)
			((MultiplayerThread) playerThread).start();
	}

	public boolean connectToServer(String hostname) throws RemoteException {
		return client.connect(ServerIP.getInstance().getIp());
	}

	public boolean creaLobby() throws RemoteException {
		return client.makeLobby(nomeLobby);
	}

	public boolean entraLobby(String code) {
		return client.joinLobby(code);
	}

	@SuppressWarnings("deprecation")
	public void closeThreads() {
		try {
			for (Thread s : threads) {
				s.stop();
			}
		} catch (Exception e) {
			((MultiplayerThread) playerThread).stop();
		}
	}

}