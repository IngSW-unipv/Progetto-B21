package it.unipv.po.view.menuElements.buttons;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import it.unipv.po.view.imagesLoader.ImagesLoader;

public class MultiPlayerButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ImagesLoader loader;
	private BufferedImage buffer;

	public MultiPlayerButton(ImagesLoader loader) {
		super();

		this.loader = loader;

		create();
	}

	private void create() {

		this.buffer = loader.uploadImage("/it/unipv/po/images/button2.png");
		ImageIcon multiPImg = new ImageIcon(buffer);
		setBounds(200, 309, 382, 105);
		setIcon(multiPImg);
		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
		setVisible(true);
	}
}
