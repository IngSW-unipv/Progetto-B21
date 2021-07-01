package it.unipv.ingsw.client.view.gameElements.buttons;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import it.unipv.ingsw.client.view.imagesLoader.ImagesLoader;

public class CardButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ImagesLoader loader;
	private BufferedImage buffer;
	private int x, y;
	private String txt;

	public CardButton(ImagesLoader loader, int x, int y, String txt) {
		super();
		this.loader = loader;
		this.x = x;
		this.y = y;
		this.txt = txt;

		create();
	}

	private void create() {

		this.buffer = loader.uploadImage("/it/unipv/po/images/deckGraphics/" + txt + ".png");
		ImageIcon card = new ImageIcon(buffer);
		setIcon(card);
		setOpaque(true);
		setContentAreaFilled(false);
		setBorderPainted(false);
		setBounds(x, y, 65, 113);
		setVisible(true);
	}

	public void cardSelected(boolean temp) {

		if (temp) {

			setBounds(getBounds().x, getBounds().y - 30, 65, 113);
		}

		else {

			setBounds(getBounds().x, getBounds().y + 30, 65, 113);
		}
	}
}
