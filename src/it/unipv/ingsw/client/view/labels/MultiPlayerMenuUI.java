package it.unipv.ingsw.client.view.labels;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import it.unipv.ingsw.client.view.imagesLoader.ImagesLoader;
import it.unipv.ingsw.client.view.menuElements.buttons.*;

/**
 * Questa classe rappresenta l'etichetta principale della modalità multiplayer.
 *
 *
 */
public class MultiPlayerMenuUI extends JLabel {

	private static final long serialVersionUID = 1L;
	private BufferedImage buffer;
	private ImagesLoader loader;
	private CreaLobbyButton creaLobby;
	private EntraLobbyButton entraLobby;
	private BackButton back;

//_______________________COSTRUTTORE_______________________
	public MultiPlayerMenuUI(ImagesLoader loader) {
		super();
		this.loader = loader;
		create();
		mainMenu();
	}

//_______________________GETTERS&SETTERS___________________

	public CreaLobbyButton getCreaLobby() {
		return creaLobby;
	}

	public void setSingle(CreaLobbyButton creaLobby) {
		this.creaLobby = creaLobby;
	}

	public EntraLobbyButton getEntraLobby() {
		return entraLobby;
	}

	public void setEntraLobby(EntraLobbyButton entraLobby) {
		this.entraLobby = entraLobby;
	}
	
	public void setCreaLobby(CreaLobbyButton creaLobby) {
		this.creaLobby = creaLobby;
	}	
	
	public BackButton getBack() {
		return back;
	}

	public void setBack(BackButton back) {
		this.back = back;
	}
	
	// _______________________METODI____________________________

	private void create() {

		this.buffer = loader.uploadImage("/it/unipv/ingsw/client/images/background.png");
		ImageIcon image = new ImageIcon(buffer);
		setIcon(image);
		setBounds(0, 0, 800, 500);
		setLayout(null);
	}

	public void mainMenu() {

		// creaLobby
		this.creaLobby = new CreaLobbyButton(loader);
		setCreaLobby(creaLobby);
		add(creaLobby);

		// multiPlayer
		this.entraLobby = new EntraLobbyButton(loader);
		setEntraLobby(entraLobby);
		add(entraLobby);
		
		this.back = new BackButton(loader);
		setBack(back);
		add(back);
	}
}
