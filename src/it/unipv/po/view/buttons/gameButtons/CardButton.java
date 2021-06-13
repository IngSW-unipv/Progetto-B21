package it.unipv.po.view.buttons.gameButtons;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import it.unipv.po.view.ImagesLoader;

public class CardButton extends JButton{

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
		
		this.buffer = loader.uploadImage("/it/unipv/po/images/napoletane/l/" + txt + ".png");
		ImageIcon multiPImg = new ImageIcon(buffer);
		setIcon(multiPImg);
		setBounds(x, y, 65, 113);
		setVisible(true);
	}
	
	
}
