package it.unipv.ingsw.client.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import it.unipv.ingsw.client.view.imagesLoader.ImagesLoader;
import it.unipv.ingsw.client.view.labels.CreaLobbyUI;
import it.unipv.ingsw.client.view.labels.EntraLobbyUI;
import it.unipv.ingsw.client.view.labels.GameUI;
import it.unipv.ingsw.client.view.labels.MainMenuUI;
import it.unipv.ingsw.client.view.labels.MultiPlayerMenuUI;
import it.unipv.ingsw.client.view.menuElements.sound.SoundButton;

public class ScoponeGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private MainMenuUI mainMenu;
	private GameUI game;
	private MultiPlayerMenuUI multiPlayer;
	private CreaLobbyUI creaLobby;
	private EntraLobbyUI entraLobby;
	private boolean mainCreated;
	private boolean multiPlayerCreated;
	private boolean creaLobbyCreated;
	private boolean entraLobbyCreated;
	private ImagesLoader loader;
	private SoundButton sound;

//_____________________COSTRUTTORE__________________
	public ScoponeGUI() {
		super();

		this.mainCreated = false;
		this.multiPlayerCreated = false;
		this.creaLobbyCreated = false;
		this.entraLobbyCreated = false;
		create();
		mainMenu();
	}

//____________________GETTERS&SETTERS_______________
	public MainMenuUI getMainMenu() {
		return mainMenu;
	}

	public void setMainMenu(MainMenuUI mainMenu) {
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

	public GameUI getGame() {
		return game;
	}

	public void setGame(GameUI game) {
		this.game = game;
	}

	public MultiPlayerMenuUI getMultiPlayer() {
		return multiPlayer;
	}

	public void setMultiPlayer(MultiPlayerMenuUI multiPlayer) {
		this.multiPlayer = multiPlayer;
	}

	public boolean isMultiPlayerCreated() {
		return multiPlayerCreated;
	}

	public void setMultiPlayerCreated(boolean multiPlayerCreated) {
		this.multiPlayerCreated = multiPlayerCreated;
	}

	public CreaLobbyUI getCreaLobby() {
		return creaLobby;
	}

	public void setCreaLobby(CreaLobbyUI creaLobby) {
		this.creaLobby = creaLobby;
	}

	public EntraLobbyUI getEntraLobby() {
		return entraLobby;
	}

	public void setEntraLobby(EntraLobbyUI entraLobby) {
		this.entraLobby = entraLobby;
	}

	// ________________________METODI_______________________
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

			this.mainMenu = new MainMenuUI(loader);
			sound.setVisible(true);
			add(sound);
			add(mainMenu);
			setMainCreated(true);
			repaint();
		}
	}

	public GameUI game() {

		if (getGame() == null) {
			this.game = new GameUI(loader);
			setGame(game);
			sound.setVisible(true);
			add(sound);
			add(game);
			repaint();
		}

		else {

			remove(game);
			this.game = new GameUI(loader);
			setGame(game);
			sound.setVisible(true);
			add(sound);
			add(game);
			repaint();
		}
		return game;
	}

	public MultiPlayerMenuUI multiPlayer() {

		if (multiPlayerCreated) {

			getMultiPlayer().setVisible(true);
			sound.setVisible(true);
			add(sound);
			add(getMultiPlayer());
			repaint();
		}

		else {
			this.multiPlayer = new MultiPlayerMenuUI(loader);
			setMultiPlayer(multiPlayer);
			add(sound);
			add(multiPlayer);
			repaint();
		}

		return multiPlayer;
	}

	public CreaLobbyUI creaLobby() {

		if (creaLobbyCreated) {

			getCreaLobby().setVisible(true);
			sound.setVisible(true);
			add(sound);
			add(getCreaLobby());
			repaint();
		}

		else {
			this.creaLobby = new CreaLobbyUI(loader);
			setCreaLobby(creaLobby);
			add(sound);
			add(creaLobby);
			repaint();
		}
		return creaLobby;
	}

	public EntraLobbyUI entraLobby() {

		if (entraLobbyCreated) {

			getEntraLobby().setVisible(true);
			sound.setVisible(true);
			add(sound);
			add(getEntraLobby());
			repaint();
		}

		else {
			this.entraLobby = new EntraLobbyUI(loader);
			setEntraLobby(entraLobby);
			add(sound);
			add(entraLobby);
			repaint();
		}
		return entraLobby;
	}
}
