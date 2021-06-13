package it.unipv.po.view.buttons.menuButtons;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import it.unipv.po.view.ImagesLoader;

public class SinglePlayerButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ImagesLoader loader;
	private BufferedImage buffer;

	public SinglePlayerButton(ImagesLoader loader) {
		super();

		this.loader = loader;

		create();
	}

	private void create() {

		this.buffer = loader.uploadImage("/it/unipv/po/images/button1.png");
		ImageIcon singlePImg = new ImageIcon(buffer);
		setBounds(200, 189, 382, 105);
		setIcon(singlePImg);
		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
		setVisible(true);
	}
}
