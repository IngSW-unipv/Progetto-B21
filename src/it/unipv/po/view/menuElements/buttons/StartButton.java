package it.unipv.po.view.menuElements.buttons;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import it.unipv.po.view.imagesLoader.ImagesLoader;

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

		this.buffer = loader.uploadImage("/it/unipv/po/images/creaLobby.png");
		ImageIcon multiPImg = new ImageIcon(buffer);
		setBounds(350, 300, 100, 50);
		setIcon(multiPImg);
		setOpaque(true);
		setContentAreaFilled(true);
		setBorderPainted(false);
		setVisible(true);
	}
}
