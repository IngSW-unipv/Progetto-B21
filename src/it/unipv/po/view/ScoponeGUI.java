package it.unipv.po.view;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Toolkit;

public class ScoponeGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BufferedImage background, singleP, multiP, sound;
	private ImagesLoader loader;
	private JButton singlePlayerButton, multiPlayerButton, soundButton;
	private JLabel backgroundLabel;

	/* __________________COSTRUTTORE______________ */
	public ScoponeGUI() {
		super();
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(ScoponeGUI.class.getResource("/it/unipv/po/images/logo.png")));

		mainMenu();
	}

	/* __________________GETTERS & SETTERS________ */
	public JButton getSinglePlayerButton() {
		return singlePlayerButton;
	}

	public void setSinglePlayerButton(JButton singlePlayerButton) {
		this.singlePlayerButton = singlePlayerButton;
	}

	public JButton getMultiPlayerButton() {
		return multiPlayerButton;
	}

	public void setMultiPlayerButton(JButton multiPlayerButton) {
		this.multiPlayerButton = multiPlayerButton;
	}

	public JButton getSoundButton() {
		return soundButton;
	}

	public void setSoundButton(JButton soundButton) {
		this.soundButton = soundButton;
	}

	public JLabel getBackgroundLabel() {
		return backgroundLabel;
	}

	public void setBackgroundLabel(JLabel backgroundLabel) {
		this.backgroundLabel = backgroundLabel;
	}

	/* _________________METODI_____________________ */
	private void mainMenu() {

		this.loader = new ImagesLoader();

		// background
		this.background = loader.uploadImage("/it/unipv/po/images/background.png");
		ImageIcon image = new ImageIcon(background);
		JLabel backgroundlabel = new JLabel("", image, JLabel.CENTER);
		backgroundlabel.setBounds(0, 0, 800, 500);
		backgroundlabel.setLayout(null);

		// singlePlayer
		this.singlePlayerButton = new JButton();
		this.singleP = loader.uploadImage("/it/unipv/po/images/button1.png");
		ImageIcon singlePImg = new ImageIcon(singleP);
		singlePlayerButton.setBounds(200, 189, 382, 105);
		singlePlayerButton.setIcon(singlePImg);
		singlePlayerButton.setOpaque(false);
		singlePlayerButton.setContentAreaFilled(false);
		singlePlayerButton.setBorderPainted(false);
		backgroundlabel.add(singlePlayerButton);

		// multiPlayer
		this.multiPlayerButton = new JButton();
		this.multiP = loader.uploadImage("/it/unipv/po/images/button2.png");
		ImageIcon multiPImg = new ImageIcon(multiP);
		multiPlayerButton.setIcon(multiPImg);
		multiPlayerButton.setBounds(200, 309, 382, 105);
		multiPlayerButton.setOpaque(false);
		multiPlayerButton.setContentAreaFilled(false);
		multiPlayerButton.setBorderPainted(false);
		backgroundlabel.add(multiPlayerButton);

		// sound
		this.soundButton = new JButton();
		this.sound = loader.uploadImage("/it/unipv/po/images/audioON.png");
		ImageIcon soundSet = new ImageIcon(sound);
		soundButton.setIcon(soundSet);
		soundButton.setBounds(720, 15, 50, 50);
		soundButton.setContentAreaFilled(false);
		soundButton.setBorderPainted(false);
		backgroundlabel.add(soundButton);

		// mainMenu
		setTitle("Scopone Scientifico");
		Dimension dim = new Dimension(800, 500);
		setMinimumSize(dim);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		getContentPane().add(backgroundlabel);
	}

	public void soundRefresh(int i) {

		switch (i) {
		case 0:
			this.sound = loader.uploadImage("/it/unipv/po/images/audioON.png");
			ImageIcon soundSet = new ImageIcon(sound);
			soundButton.setIcon(soundSet);
			break;
		case 1:
			this.sound = loader.uploadImage("/it/unipv/po/images/audioOFF.png");
			ImageIcon soundSet2 = new ImageIcon(sound);
			soundButton.setIcon(soundSet2);
		}
	}
	
	public void gameGraphics() {
		
		// background
		this.background = loader.uploadImage("/it/unipv/po/images/background.png");
		ImageIcon image = new ImageIcon(background);
		JLabel backgroundlabel = new JLabel("", image, JLabel.CENTER);
		backgroundlabel.setBounds(0, 0, 800, 500);
		backgroundlabel.setLayout(null);
		
	}
}
