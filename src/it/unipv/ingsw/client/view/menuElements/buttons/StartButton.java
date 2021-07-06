package it.unipv.ingsw.client.view.menuElements.buttons;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import it.unipv.ingsw.client.view.imagesLoader.ImagesLoader;

/**
 * Questa classe rappresenta il pulsante "CREA LOBBY" presente nella modalità multiplayer.
 * 
 *
 */
public class StartButton extends JButton{

	private static final long serialVersionUID = 1L;
	private ImagesLoader loader;
	private BufferedImage buffer;
	
	public StartButton(ImagesLoader loader) {
		super();

		this.loader = loader;

		create();
	}


	private void create() {

		this.buffer = loader.uploadImage("/it/unipv/ingsw/client/images/start.png");
		ImageIcon multiPImg = new ImageIcon(buffer);
		setIcon(multiPImg);
		setBounds(350, 370, 100, 50);
		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
		setVisible(true);
	}
}
