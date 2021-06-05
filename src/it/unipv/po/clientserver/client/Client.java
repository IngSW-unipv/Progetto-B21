package it.unipv.po.clientserver.client;

import java.io.IOException;
import java.net.*;
import java.util.*;
import it.unipv.po.clientserver.server.*;
import it.unipv.po.model.player.*;
import it.unipv.po.model.player.types.HumanPlayer;

import java.io.*;

/**
 * Questa classe rappresenta ogni client, ossia ogni persona fisica che si connetterà al server.
 * Il giocatore potrà interagire e comunicare con il server (in realtà con il thread che gestirà questo client)
 * per poter giocare correttamente allo Scopone.
 * @author Vito Avanzato
 *
 */
public class Client {

	private static Socket socket;
	private static Player player;
	private static BufferedReader inputFromThisClient;
	private static BufferedReader inputFromServer;
	private static PrintWriter outputToServer;

	private static boolean isConnected = false;
	
	public static boolean isConnected() {
		
		return isConnected;
	}
	
	public static Socket getSocket() {
		return socket;
	}
	public static void setSocket(Socket socket) {
		Client.socket = socket;
	}
	public static Player getPlayer() {
		return player;
	}
	public static void setPlayer(Player player) {
		Client.player = player;
	}
	
	/**
	 * Con questo metodo il client prova effettivamente a connettersi ad un server con i parametri specificati 
	 * e crea una connessione tramite socket.
	 * @param serverName: il nome del server (host name)
	 * @param serverPort: la porta del server (TCP)
	 */
	public static void connectToServer(String serverName, int serverPort) {
		
		try {
			
			socket = new Socket(serverName, serverPort);
			inputFromThisClient = new BufferedReader(new InputStreamReader((System.in)));
			inputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outputToServer = new PrintWriter(socket.getOutputStream(), true);
			
			System.out.println("Successfully connected to the server.");
			
			isConnected = true;
			
		} catch (UnknownHostException e) {
			System.err.println("Non esiste alcun server all'indirizzo specificato ("+serverName+").");
			
		} catch (IOException e) {
			System.err.println("Qualcosa è andato storto. Riprovare.");
			
		}
		
	}
	public static void main(String[] args) {
		
		Scanner keyboard = new Scanner(System.in);
		String userReply;
		
		System.out.println("Vuoi collegarti a un server? y/n");
		userReply = keyboard.next();
		if(userReply.equalsIgnoreCase("y")) {
			
			System.out.println("A che server vuoi connetterti? Specificare:");
			System.out.print("Porta server: ");
			int serverPort = keyboard.nextInt();
			System.out.print("Nome server:");
			String serverName = keyboard.next();
			
			connectToServer(serverName, serverPort);
			
			if(isConnected) {
				
				System.out.print("Choose a nickname: ");
				userReply = keyboard.next();
				outputToServer.println(userReply);
				setPlayer(new HumanPlayer(userReply));
				
				System.out.println("Hi "+player.getNickname());
				
				//Tutto da rivedere per far avvenire la comunicazione tra client e server correttamente
				while(true) {
					
					try {
						
						while(inputFromServer.readLine() != null) {
							
							String serverMsg = inputFromServer.readLine();
							
							System.out.println("Server: "+serverMsg);
						}
						
						while(inputFromThisClient.readLine() != null) {
							
							String clientMsg = inputFromThisClient.readLine();
							
							outputToServer.println(clientMsg);
							
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		
		keyboard.close();
		
	
		
	}

}
