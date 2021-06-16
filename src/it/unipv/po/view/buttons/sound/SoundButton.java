package it.unipv.po.view.buttons.sound;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import it.unipv.po.view.imagesLoader.ImagesLoader;

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

		this.buffer = loader.uploadImage("/it/unipv/po/images/audioON.png");
		ImageIcon soundSet = new ImageIcon(buffer);
		setIcon(soundSet);
		setBounds(720, 15, 50, 50);
		setContentAreaFilled(false);
		setBorderPainted(false);
		setVisible(true);
	}

	public void soundRefresh(int i) {

		switch (i) {
		case 0:
			this.buffer = loader.uploadImage("/it/unipv/po/images/audioON.png");
			ImageIcon soundSet = new ImageIcon(buffer);
			setIcon(soundSet);
			break;
		case 1:
			this.buffer = loader.uploadImage("/it/unipv/po/images/audioOFF.png");
			ImageIcon soundSet2 = new ImageIcon(buffer);
			setIcon(soundSet2);
		}
	}
}
