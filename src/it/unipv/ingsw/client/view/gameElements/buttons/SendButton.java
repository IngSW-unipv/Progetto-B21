package it.unipv.ingsw.client.view.gameElements.buttons;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import it.unipv.ingsw.client.view.imagesLoader.ImagesLoader;

public class SendButton extends JButton{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImagesLoader loader;
	private BufferedImage buffer;
	
	public SendButton(ImagesLoader loader) {
		super();

		this.loader = loader;

		create();
	}


	private void create() {

		this.buffer = loader.uploadImage("/it/unipv/ingsw/client/images/invio.png");
		ImageIcon multiPImg = new ImageIcon(buffer);
		setBounds(750, 309, 30, 113);
		setIcon(multiPImg);
		setOpaque(true);
		setContentAreaFilled(true);
		setBorderPainted(false);
		setVisible(true);
	}
}
