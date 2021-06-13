package it.unipv.po.view.buttons.gameButtons;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import it.unipv.po.view.ImagesLoader;

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

		this.buffer = loader.uploadImage("/it/unipv/po/images/invio.png");
		ImageIcon multiPImg = new ImageIcon(buffer);
		setBounds(200, 309, 382, 105);
		setIcon(multiPImg);
		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
		setVisible(true);
	}
}
