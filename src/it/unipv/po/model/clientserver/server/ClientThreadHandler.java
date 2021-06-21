package it.unipv.po.model.clientserver.server;

import java.net.*;
import java.io.*;

import it.unipv.po.model.game.player.types.HumanPlayer;
import it.unipv.po.model.game.player.types.Player;

/**
 * Questa classe rappresenta il server nei confronti di un unico client E' un
 * thread che viene creato e avviato ogni qualvolta un client si connette al
 * server e dovrebbe gestire l'input dal cliente e inviargli messaggi.
 * 
 * @author Vito Avanzato
 *
 */
public class ClientThreadHandler extends Thread {

	private BufferedReader inputFromClient;
	private BufferedReader inputFromThisClientHandler;
	private PrintWriter outputToClient;
	private Socket socket;
	private MainServer mainServer;
	private Player player;

	public Player getPlayer() {

		return player;

	}

	public void sendAMessage(String message) {

		outputToClient.println(message);

	}

	public MainServer getMainServer() {
		return mainServer;
	}

	private void setPlayer(Player player) {

		this.player = player;

	}

	public ClientThreadHandler(Socket socket, MainServer server) {

		this.setSocket(socket);
		this.mainServer = server;
	}

	public Socket getSocket() {
		return socket;
	}

	private void setSocket(Socket socket) {
		this.socket = socket;
	}

	public String getInfo() {

		return "////////INFO " + this.getName() + "////////\nASSOCIATED PLAYER: " + this.player.getNickname() + "\n";
	}

	/**
	 * Quando si avvia, il thread crea i collegamenti TCP con il client che si è
	 * connesso correttamente al server e ci comunica
	 */
	@Override
	public void run() {

		try {

			inputFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outputToClient = new PrintWriter(socket.getOutputStream(), true);
			inputFromThisClientHandler = new BufferedReader(new InputStreamReader(System.in));

			while (true) {

				// Da rivedere, perchè non riesco a far arrivarel'esecuzione qui
				while (inputFromClient.readLine() != null) {

					this.setPlayer(new HumanPlayer(inputFromClient.readLine()));
					mainServer.addPlayer(player);
					System.out.println("Player creato e aggiunto correttamente al Server.");

				}

				socket.close();
				inputFromClient.close();
				outputToClient.close();
				inputFromThisClientHandler.close();

				this.interrupt();
				mainServer.removeThreadHandler(this);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}
}
