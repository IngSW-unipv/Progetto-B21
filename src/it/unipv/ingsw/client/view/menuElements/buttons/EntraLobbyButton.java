package it.unipv.ingsw.client.view.menuElements.buttons;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import it.unipv.ingsw.client.view.imagesLoader.ImagesLoader;

public class EntraLobbyButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ImagesLoader loader;
	private BufferedImage buffer;

	public EntraLobbyButton(ImagesLoader loader) {
		super();

		this.loader = loader;

		create();
	}

	private void create() {

		this.buffer = loader.uploadImage("/it/unipv/ingsw/client/images/entraLobby.png");
		ImageIcon entraLobby = new ImageIcon(buffer);
		setBounds(200, 309, 382, 105);
		setIcon(entraLobby);
		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
		setVisible(true);
	}
}