package it.unipv.po.model.clientserver.client;

import java.io.IOException;
import java.net.*;

import java.io.*;

/**
 * Semplice client che permette la scambio di messaggi con un server
 * @author Vito Avanzato
 *
 */
public class Client extends Thread{

	//parametri di connessione
	private Socket socket;
	private int serverPort = 0;
	private String serverName = null;
	private boolean isConnected = false;
	
	//parametri di I/O
	private BufferedReader clientInput;
	private BufferedReader serverOutput;
	private PrintWriter clientOutput;
	private String serverMsg;
	private String clientMsg;

	/**
	 * Costruttore
	 */
	public Client() {
		this.serverPort = 0;
		this.serverName = null;
	}
	
	/**
	 * Come prima cosa viene aperta la connessione con il server, poi si gestisce lo scambio di 
	 * messaggi String da client a server. Alla fine la connessione viene chiusa
	 */
	public void run() {
		
		clientInput = new BufferedReader(new InputStreamReader((System.in)));
		
		connectToServer();
		
		while(isConnected) {
			try {
				getMessageFromServer();
				sendMessageToServer();
			} catch (Exception e) {}
		}
		
		try {
			socket.close();
		} catch (IOException e) {}
		
	}
	
	/**
	 * Con questo metodo il client prova effettivamente a connettersi ad un server con i parametri specificati  
	 * e crea una connessione tramite socket.
	 */
	private void connectToServer() {
		//inserimento parametri da tastiera
		while(!isConnected) { 
			try {
				System.out.println("A che server vuoi connetterti?");
				System.out.print("Porta server (inserire 8989): ");
				serverPort = Integer.parseInt(clientInput.readLine());
				System.out.print("Nome server (inserire localhost):");
				serverName = clientInput.readLine();
			} catch (NumberFormatException e1) {	
				System.out.println("La porta deve essere un numero intero");
			} catch (IOException e1) {
				System.out.println("Errore");
			}
			
			//connessione effettiva
			try {
				socket = new Socket(serverName, serverPort);
				serverOutput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				clientOutput = new PrintWriter(socket.getOutputStream(), true);
				System.out.println("Connessione al server riuscita.");
				isConnected = true;
			} catch (UnknownHostException e) {
				System.err.println("Non esiste alcun server all'indirizzo ("+serverName+").");
			} catch (IOException e) {
				System.err.println("Errore. Riprovare.");
			}
		}
		
	}
	
	/**
	 * Se c'è, viene mostrato in console il messaggio inviato dal server
	 */
	private void getMessageFromServer() throws Exception {
		serverMsg = serverOutput.readLine();
		if (serverMsg != null) 
			System.out.println("Server: "+serverMsg);
	}
	
	/**
	 * Si prende input da tastiera e lo si invia al server.
	 * In caso l'input sia "close" si chiude la connessione
	 */
	private void sendMessageToServer() throws Exception {
		clientMsg = clientInput.readLine();
		if (clientMsg != null) {
			if (clientMsg == "close") {
				try {
					socket.close();
				} catch (IOException e) {}
			}
			clientOutput.println(clientMsg);
		}
	}


}
