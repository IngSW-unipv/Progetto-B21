package it.unipv.ingsw.client.view.menuElements.buttons;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import it.unipv.ingsw.client.view.imagesLoader.ImagesLoader;

/**
 * Questa classe rappresenta il pulsante "?" presente nella schermata principale.
 * 
 *
 */
public class QuestionsButton extends JButton{
	private static final long serialVersionUID = 1L;

	private ImagesLoader loader;
	private BufferedImage buffer;

	public QuestionsButton(ImagesLoader loader) {
		super();

		this.loader = loader;

		create();
	}

	private void create() {

		buffer = loader.uploadImage("/it/unipv/ingsw/client/images/domanda.png");
		ImageIcon card = new ImageIcon(buffer);
		setIcon(card);
		setBounds(10, 15, 50, 50);
		setOpaque(true);
		setContentAreaFilled(false);
		setBorderPainted(false);
		setVisible(true);
	}
}