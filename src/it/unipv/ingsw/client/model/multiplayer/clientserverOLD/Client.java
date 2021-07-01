package it.unipv.ingsw.client.model.multiplayer.clientserverOLD;

import java.io.IOException;

import java.io.ObjectInputStream;
import java.net.*;

import it.unipv.ingsw.client.controller.Controller;

/**
 * Semplice client che permette la scambio di messaggi con un server
 * 
 * @author Vito Avanzato
 *
 */
public class Client extends Thread {

	private Socket socket;
	private Controller controller;
	private int serverPort = 6789;
	private String serverName = null;
	private boolean isConnected = false;
	private ObjectInputStream ois;

//_____________________COSTRUTTORE____________________
	public Client(Controller controller, String serverName) {

		this.controller = controller;
		this.serverName = serverName;
	}

	/**
	 * Come prima cosa viene aperta la connessione con il server, poi si gestisce lo
	 * scambio di messaggi String da client a server. Alla fine la connessione viene
	 * chiusa
	 */
	public void run() {

		connectToServer();
		
		try {
			startFromServer();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Con questo metodo il client prova effettivamente a connettersi ad un server
	 * con i parametri specificati e crea una connessione tramite socket.
	 */
	private void connectToServer() {
		while (!isConnected) {
			try {
				this.socket = new Socket(serverName, serverPort);
				if (socket.isConnected())
					System.out.println("Connessione al server riuscita.");
				this.ois = new ObjectInputStream(socket.getInputStream());
				isConnected = true;
			} catch (UnknownHostException e) {
				controller.getGui().getEntraLobby().getAdvisor()
						.setText("Non esiste alcun server all'indirizzo (" + serverName + ").");
			} catch (IOException e) {
				controller.getGui().getEntraLobby().getAdvisor().setText("Errore. Riprovare.");

			}
		}
	}

	private void startFromServer() throws ClassNotFoundException, IOException {

		ClientThreadHandler clientThread = new ClientThreadHandler(socket);
		clientThread.start();
		clientThread.setController(controller);
		
		
		while (((Message) ois.readObject()).isStart() != false) {

			if (controller.getGui().getEntraLobby() != null)
				controller.getGui().getEntraLobby().setVisible(false);

			if (controller.getGui().getCreaLobby() != null)
				controller.getGui().getCreaLobby().setVisible(false);

			controller.getGui().game();
			controller.sendListener();
			controller.deckCreator(30, 309);
		}
	}
}
