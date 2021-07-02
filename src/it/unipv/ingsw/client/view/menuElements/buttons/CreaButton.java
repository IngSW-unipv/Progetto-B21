package it.unipv.ingsw.client.view.menuElements.buttons;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import it.unipv.ingsw.client.view.imagesLoader.ImagesLoader;

public class CreaButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ImagesLoader loader;
	private BufferedImage buffer;

	public CreaButton(ImagesLoader loader) {
		super();

		this.loader = loader;

		create();
	}

	private void create() {

		setBounds(390, 189, 50, 20);
		setOpaque(true);
		setContentAreaFilled(true);
		setBorderPainted(true);
		setVisible(true);
	}
}