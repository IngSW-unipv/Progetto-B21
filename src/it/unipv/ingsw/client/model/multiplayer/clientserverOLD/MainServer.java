package it.unipv.ingsw.client.model.multiplayer.clientserverOLD;

import java.net.*;
import java.io.*;
import java.util.*;

import it.unipv.ingsw.client.controller.Controller;
import it.unipv.ingsw.client.model.game.*;
import it.unipv.ingsw.client.model.game.player.types.BotPlayer;
import it.unipv.ingsw.client.model.game.player.types.Player;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Questa classe rappresenta il server principale al quale i vari client(i
 * giocatori) dovranno connettersi per poter avviare e giocare una partita
 * multiplayer. Sarà anche responsabile della creazione dei vari thread che
 * gestiranno ciascun client connesso e del loro avviamento.
 * 
 * @author Vito Avanzato
 *
 */

public class MainServer extends Thread {

	private String hostName;
	private ServerSocket server = null;
	private Socket clientSocket = null;
	private final int porta = 6789;
	private int numberOfConnectedClients;
	private Game scopone;
	private ArrayList<Player> players; // Per memorizzare tutti i giocatori attualmente connessi al server
	private Controller controller;
	private ObjectInputStream is;
	private ObjectOutputStream os;

//________________________COSTRUTTORE________________________

	public MainServer() throws IOException {

		this.server = new ServerSocket(porta);
		server.getInetAddress();
		hostName = InetAddress.getLocalHost().getHostAddress();
		this.players = new ArrayList<Player>();
	}

//________________________GETTERS&SETTERS_____________________

	public String getHostName() {
		return hostName;
	}
	

	
	// _______________________METODI______________________________
	/**
	 * Avvia il server, aspetta che qualcuno si connetta, avvia il thread per
	 * gestire ciascun client e periodicamente informa di quante persone sono
	 * collegate e della connessione avvenuta con qualche client
	 */
	public void run() {

		try {
			this.clientSocket = server.accept();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			numberOfConnectedClients++;

			if (numberOfConnectedClients == 1) {

				System.out.println("At this time (" + dtf.format(now) + ") there is only " + numberOfConnectedClients
						+ " connected clients.\n");

			} else {

				System.out.println("At this time (" + dtf.format(now) + ") there are " + numberOfConnectedClients
						+ " connected clients.\n");

			}

			if (numberOfConnectedClients == 4) {

				startGame();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public synchronized Game createGame() {

		if (numberOfConnectedClients < 4) {
			for (int i = 0; i <= 3 - numberOfConnectedClients; i++) {

				BotPlayer bot = new BotPlayer();
				players.add(bot);
			}
		}

		this.scopone = new Game(players);

		return scopone;
	}

	public synchronized void startGame() {
		
		try {
			addPlayer();
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		try {
			this.os = new ObjectOutputStream(clientSocket.getOutputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			Message message = new Message();
			message.setStart(true);
			os.writeObject(message);
			os.flush();
			
			createGame();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private synchronized void addPlayer() throws ClassNotFoundException, IOException {

		this.is = new ObjectInputStream(clientSocket.getInputStream());
		
		boolean temp = true;

		while (temp) {
			if (((Message) is.readObject()).getPlayer() != null)
				players.add(((Message) is.readObject()).getPlayer());

			if (players.size() == 4) {
				temp = false;
			}
		}
	}
}
