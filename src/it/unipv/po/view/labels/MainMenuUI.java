package it.unipv.po.view.labels;

import java.awt.Color;
import java.awt.TextField;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import it.unipv.po.view.imagesLoader.ImagesLoader;
import it.unipv.po.view.menuElements.buttons.MultiPlayerButton;
import it.unipv.po.view.menuElements.buttons.SinglePlayerButton;

public class MainMenuUI extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BufferedImage buffer;
	private ImagesLoader loader;
	private SinglePlayerButton single;
	private MultiPlayerButton multi;
	private TextField nickname;

//_______________________COSTRUTTORE_______________________
	public MainMenuUI(ImagesLoader loader) {
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
	
	public TextField getNickname() {
		return nickname;
	}

	public void setNickname(TextField nickname) {
		this.nickname = nickname;
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
		
		//nickname
		this.nickname = new TextField();
		nickname.setBackground(Color.WHITE);
		nickname.setBounds(345, 170, 110, 20);
		nickname.setText("inserire nickname");
		nickname.setVisible(true);
		add(nickname);
	}
}
