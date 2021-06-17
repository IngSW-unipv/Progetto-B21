package it.unipv.po.model.clientserver.server;

import java.net.*;
import java.io.*;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import it.unipv.po.model.game.*;
import it.unipv.po.model.game.player.*;
import it.unipv.po.model.game.player.types.Player;
import it.unipv.po.model.clientserver.client.*;
/**
 * Questa classe rappresenta il server principale al quale i vari client(i giocatori) dovranno connettersi
 * per poter avviare e giocare una partita multiplayer.
 * Sarà anche responsabile della creazione dei vari thread che gestiranno ciascun client connesso e del loro avviamento.
 * @author Vito Avanzato
 *
 */

public class MainServer {

	//TCP 
	private final int portNumber;
	private final String hostName;
	private ServerSocket serverSocket;
	
	private int numberOfConnectedClients;
	
	private ArrayList<ClientThreadHandler> clientHandlers; //Per memorizzare tutti i thread ed eventualmente ricevere informazioni
	private ArrayList<ScoponeGame> games; //Per memorizzare più game
	private ArrayList<Player> gamePlayers; //Per memorizzare tutti i giocatori attualmente connessi al server
	
	public ArrayList<Player> getGamePlayers(){
		
		return gamePlayers;
		
	}
	public ArrayList<ScoponeGame> getGames() {
		
		return games;
		
	}
	public ServerSocket getServerSocket() {
		
		return serverSocket;
		
	}
	public ArrayList<ClientThreadHandler> getClientHandlers() {
		return clientHandlers;
	}

	public int getPortNumber() {
		return portNumber;
	}

	public String getHostName() {
		return hostName;
	}

	public Client getClientFromIndex(ArrayList<Client> connectedClients, int index) {
		
		return connectedClients.get(index);
		
	}
	
	/**
	 * Metodo per la creazione del server
	 * Automaticamente trova e imposta la porta del server ad una porta TCP libera ed utilizzabile ed anche
	 * l'hostName, ossia il nome del server (corrisponde all'indirizzo IP del PC su cui è stato avviato questo main.
	 * 
	 * @throws IOException
	 */
	public MainServer() throws IOException {
		
		try 
			  (final DatagramSocket socket=new DatagramSocket())
			{
			  socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
			  hostName = socket.getLocalAddress().getHostAddress();
			  portNumber = socket.getLocalPort();
			  
			  serverSocket = new ServerSocket(portNumber);
			  
			  clientHandlers = new ArrayList<ClientThreadHandler>();
			  numberOfConnectedClients = 0;
			  games = new ArrayList<ScoponeGame>();
			  
			}

	}
	
	public void writeMessage(String message) {
		
		System.out.println(message);
		
	}
	void addPlayer(Player player) {
		
		gamePlayers.add(player);
		
	}
	
	void removeThreadHandler(ClientThreadHandler thread) {
		
		clientHandlers.remove(thread);
		
	}
	
	/**
	 * Avvia il server, aspetta che qualcuno si connetta, avvia il thread per gestire ciascun client e periodicamente
	 * informa di quante persone sono collegate e della connessione avvenuta con qualche client
	 */
	public void startServer() {
		
		//MainServer server;
		gamePlayers = new ArrayList<Player>();
		
		try {
			//server = new MainServer();
			System.out.println("Server host name: "+hostName);
			System.out.println("Server port number: "+portNumber);
			
			while(true) {
				
				
				
				if(numberOfConnectedClients == 0) {
					
					System.out.println("Waiting for clients to connect to the server at this port:"+portNumber);
					
				} else {
					
					System.out.println("Waiting for other clients to connect to the server...");
					
				}
				
				Socket socket = serverSocket.accept();
				
				
				ClientThreadHandler clientHandler = new ClientThreadHandler(socket, this);
				clientHandlers.add(clientHandler);
				//numberOfConnectedClients++;
				
				
				System.out.println("A Client just connected to this server!");
				numberOfConnectedClients = clientHandlers.size();
				
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			    LocalDateTime now = LocalDateTime.now();
				
			    
				if(numberOfConnectedClients == 1) {
					
					System.out.println("At this time ("+dtf.format(now)+") there is only "+numberOfConnectedClients+" connected clients.\n");
					
				} else {
					
					System.out.println("At this time ("+dtf.format(now)+") there are "+numberOfConnectedClients+" connected clients.\n");
					
				}
				
				
				
				clientHandler.start();
				/*
				while(clientHandler.getInputFromClient() == null) {
					
					
				}
				
				
				if(numberOfConnectedClients == 4) {
					

						
					sendToEveryone("4 Clients successfully connected! A game is about to start...");
						
					Game game = new Game(gamePlayers);
					
					
				} else {
						
					clientHandler.setOutputToClient(null);
					sendToEveryone("4 Clients are needed to start a game...");
						
				} */
			}
		}
		 catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 	}
		
		}
	
	public void createGame() {
		
		//Game game = new Game();
	}
	
	public void sendToEveryone(String message) {
		
		for(ClientThreadHandler aClient : clientHandlers) {
			
			aClient.sendAMessage(message);
			
		}
	}
	
	public String getInfo() {
		
		return "////////INFO SERVER////////\nHOST NAME: "+this.getHostName()+"\nPORT: "+this.getPortNumber()+"\nCONNECTED CLIENTS: "
		+this.numberOfConnectedClients+"\n";
	}
	
	public void writeThreadInfos() {
		
		for(ClientThreadHandler aThread : clientHandlers) {
			
			System.out.println(aThread.getInfo());
		}
	}
	public static void main(String[] args) {
		
		try {
			
			MainServer server = new MainServer();
			server.startServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
	
	

			
	


