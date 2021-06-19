package it.unipv.po.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import it.unipv.po.view.imagesLoader.ImagesLoader;
import it.unipv.po.view.labels.GameGUI;
import it.unipv.po.view.labels.MainMenuGUI;
import it.unipv.po.view.menuElements.sound.SoundButton;

public class ScoponeGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MainMenuGUI mainMenu;
	private GameGUI game;
	private boolean mainCreated;
	private ImagesLoader loader;
	private SoundButton sound;

//_____________________COSTRUTTORE__________________
	public ScoponeGUI() {
		super();

		mainCreated = false;
		create();
		mainMenu();
	}

//____________________GETTERS&SETTERS_______________
	public MainMenuGUI getMainMenu() {
		return mainMenu;
	}

	public void setMainMenu(MainMenuGUI mainMenu) {
		this.mainMenu = mainMenu;
	}

	public SoundButton getSound() {
		return sound;
	}

	public void setSound(SoundButton sound) {
		this.sound = sound;
	}

	public boolean isMainCreated() {
		return mainCreated;
	}

	public void setMainCreated(boolean mainCreated) {
		this.mainCreated = mainCreated;
	}

	public GameGUI getGame() {
		return game;
	}

	public void setGame(GameGUI game) {
		this.game = game;
	}

	// ___________________METODI_________________________
	private void create() {

		setTitle("Scopone Scientifico");
		Dimension dim = new Dimension(800, 500);
		setMinimumSize(dim);
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(ScoponeGUI.class.getResource("/it/unipv/po/images/logo.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setVisible(true);
		
		loader = new ImagesLoader();
		this.sound = new SoundButton(loader);
	}

	public void mainMenu() {

		if (mainCreated) {

			getMainMenu().setVisible(true);
			sound.setVisible(true);
			add(sound);
			add(getMainMenu());
			repaint();
		}

		else {

			this.mainMenu = new MainMenuGUI(loader);
			sound.setVisible(true);
			add(sound);
			add(mainMenu);
			setMainCreated(true);
			repaint();
		}
	}

	public GameGUI game() {

		this.game = new GameGUI(loader);
		setGame(game);
		sound.setVisible(true);
		add(sound);
		add(game);
		repaint();
		
		return game;
	}
}
