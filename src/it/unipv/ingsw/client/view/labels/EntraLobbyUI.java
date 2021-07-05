package it.unipv.ingsw.client.view.labels;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import it.unipv.ingsw.client.view.imagesLoader.ImagesLoader;
import it.unipv.ingsw.client.view.menuElements.buttons.BackButton;
import it.unipv.ingsw.client.view.menuElements.buttons.CreaButton;
import it.unipv.ingsw.client.view.menuElements.text.TextArea;

/**
 * Questa classe rappresenta l'etichetta principale della sezione di accesso ad una lobby nella modalità multiplayer.
 *
 *
 */
public class EntraLobbyUI extends JLabel {

	private static final long serialVersionUID = 1L;
	private BufferedImage buffer;
	private ImagesLoader loader;
	private BackButton back;
	private JLabel advisor;
	private JLabel players;
	private CreaButton crea;
	private TextArea nomeLobby;

//__________________COSTRUTTORE___________________

	public EntraLobbyUI(ImagesLoader loader) {
		super();
		this.loader = loader;

		create();
	}

//________________GETTERS&SETTERS_________________

	public BackButton getBack() {
		return back;
	}

	public void setBack(BackButton back) {
		this.back = back;
	}

	public JLabel getAdvisor() {
		return advisor;
	}

	public void setAdvisor(JLabel advisor) {
		this.advisor = advisor;
	}

	public JLabel getPlayers() {
		return players;
	}

	public void setPlayers(JLabel players) {
		this.players = players;
	}

	public CreaButton getCrea() {
		return crea;
	}

	public TextArea getNomeLobby() {
		return nomeLobby;
	}

	// ______________________METODI______________________
	private void create() {

		this.buffer = loader.uploadImage("/it/unipv/ingsw/client/images/background.png");
		ImageIcon image = new ImageIcon(buffer);
		setIcon(image);
		setBounds(0, 0, 800, 500);
		setLayout(null);

		this.back = new BackButton(loader);
		add(back);

		// advisor
		this.advisor = new JLabel();
		advisor.setBounds(0, 442, 800, 20);
		advisor.setOpaque(true);
		add(advisor);

		// players
		this.players = new JLabel();
		players.setBounds(250, 250, 300, 100);
		players.setOpaque(true);
		add(players);

		// crea
		this.crea = new CreaButton(loader);
		add(crea);

		// nomeLobby
		this.nomeLobby = new TextArea(250, 190, 120, 20);
		add(nomeLobby);
	}
}
