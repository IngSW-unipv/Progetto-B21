package it.unipv.po.view.labels;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import it.unipv.po.view.ImagesLoader;
import it.unipv.po.view.buttons.menuButtons.MultiPlayerButton;
import it.unipv.po.view.buttons.menuButtons.SinglePlayerButton;

public class MainMenuGUI extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BufferedImage buffer;
	private ImagesLoader loader;
	private SinglePlayerButton single;
	private MultiPlayerButton multi;

//_______________________COSTRUTTORE_______________________
	public MainMenuGUI(ImagesLoader loader) {
		super();
		this.loader = loader;
		create();
		mainMenu();
	}

//_______________________GETTERS&SETTERS___________________

	public SinglePlayerButton getSingle() {
		return single;
	}

	public void setSingle(SinglePlayerButton single) {
		this.single = single;
	}

	public MultiPlayerButton getMulti() {
		return multi;
	}

	public void setMulti(MultiPlayerButton multi) {
		this.multi = multi;
	}

	// _______________________METODI____________________________

	private void create() {

		this.buffer = loader.uploadImage("/it/unipv/po/images/background.png");
		ImageIcon image = new ImageIcon(buffer);
		setIcon(image);
		setBounds(0, 0, 800, 500);
		setLayout(null);
	}

	public void mainMenu() {

		// singlePlayer
		this.single = new SinglePlayerButton(loader);
		setSingle(single);
		add(single);

		// multiPlayer
		this.multi = new MultiPlayerButton(loader);
		setMulti(multi);
		add(multi);
	}
}
