package it.unipv.ingsw.client.view.labels;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import it.unipv.ingsw.client.view.gameElements.buttons.*;
import it.unipv.ingsw.client.view.imagesLoader.ImagesLoader;

public class GameUI extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ImagesLoader loader;
	private BufferedImage buffer;
	private SendButton send;
	private JLabel gameAdvisor;
	private JLabel teamAdvisor;

	public GameUI(ImagesLoader loader) {
		super();
		this.loader = loader;
		create();
		game();
	}

	public SendButton getSend() {
		return send;
	}

	public void setSend(SendButton send) {
		this.send = send;
	}

	public JLabel getGameAdvisor() {
		return gameAdvisor;
	}

	public void setGameAdvisor(JLabel gameAdvisor) {
		this.gameAdvisor = gameAdvisor;
	}

	public JLabel getTeamAdvisor() {
		return teamAdvisor;
	}

	public void setTeamAdvisor(JLabel teamAdvisor) {
		this.teamAdvisor = teamAdvisor;
	}

	private void create() {

		this.buffer = loader.uploadImage("/it/unipv/po/images/table.png");
		ImageIcon game = new ImageIcon(buffer);
		setIcon(game);
		setBounds(0, 0, 800, 500);
		setLayout(null);
		setVisible(true);

	}

	public void game() {

		// invio
		this.send = new SendButton(loader);
		add(send);

		// chat
		this.gameAdvisor = new JLabel();
		gameAdvisor.setBounds(0, 442, 800, 20);
		gameAdvisor.setOpaque(true);
		add(gameAdvisor);
	}

	public CardButton cardsBuilder(int x, int y, String txt) {

		CardButton card = new CardButton(loader, x, y, txt);
		add(card);

		return card;
	}
}
