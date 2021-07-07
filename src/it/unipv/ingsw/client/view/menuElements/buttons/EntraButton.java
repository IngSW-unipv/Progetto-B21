package it.unipv.ingsw.client.view.menuElements.buttons;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import it.unipv.ingsw.client.view.imagesLoader.ImagesLoader;

public class EntraButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ImagesLoader loader;
	private BufferedImage buffer;

	public EntraButton(ImagesLoader loader) {
		super();

		this.loader = loader;

		create();
	}

	private void create() {

		this.buffer = loader.uploadImage("/it/unipv/ingsw/client/images/entra.png");
		ImageIcon crea = new ImageIcon(buffer);
		setIcon(crea);
		setBounds(505, 189, 60, 20);
		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
		setVisible(true);
	}
}