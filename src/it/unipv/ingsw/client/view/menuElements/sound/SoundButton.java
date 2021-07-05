package it.unipv.ingsw.client.view.menuElements.sound;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import it.unipv.ingsw.client.view.imagesLoader.ImagesLoader;

/**
 * Questa classe rappresenta il bottone del suono, attraverso il quale è possibile silenziare/attivare la musica del gioco.
 * 
 *
 */
public class SoundButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImagesLoader loader;
	private BufferedImage buffer;

	public SoundButton(ImagesLoader loader) {
		super();
		this.loader = loader;

		create();
	}

	private void create() {

		this.buffer = loader.uploadImage("/it/unipv/ingsw/client/images/audioON.png");
		ImageIcon soundSet = new ImageIcon(buffer);
		setIcon(soundSet);
		setBounds(730, 15, 50, 50);
		setContentAreaFilled(false);
		setBorderPainted(false);
		setVisible(true);
	}

	public void soundRefresh(int i) {

		switch (i) {
		case 0:
			this.buffer = loader.uploadImage("/it/unipv/ingsw/client/images/audioON.png");
			ImageIcon soundSet = new ImageIcon(buffer);
			setIcon(soundSet);
			break;
		case 1:
			this.buffer = loader.uploadImage("/it/unipv/ingsw/client/images/audioOFF.png");
			ImageIcon soundSet2 = new ImageIcon(buffer);
			setIcon(soundSet2);
			break;
		}
	}
}
