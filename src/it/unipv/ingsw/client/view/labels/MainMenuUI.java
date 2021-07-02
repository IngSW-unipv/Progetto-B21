package it.unipv.ingsw.client.view.labels;


import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import it.unipv.ingsw.client.view.imagesLoader.ImagesLoader;
import it.unipv.ingsw.client.view.menuElements.buttons.MultiPlayerButton;
import it.unipv.ingsw.client.view.menuElements.buttons.SinglePlayerButton;
import it.unipv.ingsw.client.view.menuElements.text.TextArea;

public class MainMenuUI extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BufferedImage buffer;
	private ImagesLoader loader;
	private SinglePlayerButton single;
	private MultiPlayerButton multi;
	private TextArea nickname;

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
	
	public TextArea getNickname() {
		return nickname;
	}

	public void setNickname(TextArea nickname) {
		this.nickname = nickname;
	}

	// _______________________METODI____________________________

	private void create() {

		this.buffer = loader.uploadImage("/it/unipv/ingsw/client/images/background_2.png");
		ImageIcon image = new ImageIcon(buffer);
		setIcon(image);
		setBounds(0, 0, 800, 500);
		setLayout(null);
	}

	public void mainMenu() {

		// singlePlayer
		this.single = new SinglePlayerButton(loader);
		add(single);

		// multiPlayer
		this.multi = new MultiPlayerButton(loader);
		add(multi);
		
		//nickname
		this.nickname = new TextArea(345, 170, 110, 20);
		//nickname.setText("inserire nickname");
		add(nickname);
	}
}