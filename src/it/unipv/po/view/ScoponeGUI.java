package it.unipv.po.view;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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

	private BufferedImage buffer;
	private ImagesLoader loader;
	private JButton singlePlayerButton, multiPlayerButton, soundButton, send;
	private JLabel backgroundLabel;
	private ArrayList<JButton> cards;

	/* __________________COSTRUTTORE______________ */
	public ScoponeGUI() {
		super();
		this.loader = new ImagesLoader();

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

	public JButton getSend() {
		return send;
	}

	public void setSend(JButton send) {
		this.send = send;
	}

	public ArrayList<JButton> getCards() {
		return cards;
	}

	/* _________________METODI_____________________ */
	private void mainMenu() {

		// background
		this.buffer = loader.uploadImage("/it/unipv/po/images/background.png");
		ImageIcon image = new ImageIcon(buffer);
		JLabel backgroundLabel = new JLabel("", image, JLabel.CENTER);
		setBackgroundLabel(backgroundLabel);
		backgroundLabel.setBounds(0, 0, 800, 500);
		backgroundLabel.setLayout(null);

		// singlePlayer
		this.singlePlayerButton = new JButton();
		this.buffer = loader.uploadImage("/it/unipv/po/images/button1.png");
		ImageIcon singlePImg = new ImageIcon(buffer);
		setSinglePlayerButton(singlePlayerButton);
		singlePlayerButton.setBounds(200, 189, 382, 105);
		singlePlayerButton.setIcon(singlePImg);
		singlePlayerButton.setOpaque(false);
		singlePlayerButton.setContentAreaFilled(false);
		singlePlayerButton.setBorderPainted(false);
		singlePlayerButton.setVisible(true);
		backgroundLabel.add(singlePlayerButton);

		// multiPlayer
		this.multiPlayerButton = new JButton();
		this.buffer = loader.uploadImage("/it/unipv/po/images/button2.png");
		ImageIcon multiPImg = new ImageIcon(buffer);
		setMultiPlayerButton(multiPlayerButton);
		multiPlayerButton.setIcon(multiPImg);
		multiPlayerButton.setBounds(200, 309, 382, 105);
		multiPlayerButton.setOpaque(false);
		multiPlayerButton.setContentAreaFilled(false);
		multiPlayerButton.setBorderPainted(false);
		multiPlayerButton.setVisible(true);
		backgroundLabel.add(multiPlayerButton);

		// sound
		this.soundButton = new JButton();
		this.buffer = loader.uploadImage("/it/unipv/po/images/audioON.png");
		ImageIcon soundSet = new ImageIcon(buffer);
		setSoundButton(soundButton);
		soundButton.setIcon(soundSet);
		soundButton.setBounds(720, 15, 50, 50);
		soundButton.setContentAreaFilled(false);
		soundButton.setBorderPainted(false);
		backgroundLabel.add(soundButton);

		// main Frame
		setTitle("Scopone Scientifico");
		Dimension dim = new Dimension(800, 500);
		setMinimumSize(dim);
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(ScoponeGUI.class.getResource("/it/unipv/po/images/logo.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		getContentPane().add(backgroundLabel);
	}

	public void soundRefresh(int i) {

		switch (i) {
		case 0:
			this.buffer = loader.uploadImage("/it/unipv/po/images/audioON.png");
			ImageIcon soundSet = new ImageIcon(buffer);
			soundButton.setIcon(soundSet);
			break;
		case 1:
			this.buffer = loader.uploadImage("/it/unipv/po/images/audioOFF.png");
			ImageIcon soundSet2 = new ImageIcon(buffer);
			soundButton.setIcon(soundSet2);
		}
	}

	public synchronized void gameGraphics() {

		getSinglePlayerButton().setVisible(false);
		getMultiPlayerButton().setVisible(false);

		cards = new ArrayList<JButton>();
		// background
		this.buffer = loader.uploadImage("/it/unipv/po/images/table.png");
		ImageIcon game = new ImageIcon(buffer);
		getBackgroundLabel().setIcon(game);

		// invio
		JButton send = new JButton();
		send.setBounds(750, 309, 30, 113);
		setSend(send);
		getBackgroundLabel().add(send);

	}

	public JButton cardsBuilder(int x, int y, String txt) {
		
		JButton card = new JButton();
		this.buffer = loader.uploadImage("/it/unipv/po/images/napoletane/l/" + txt + ".png");
		ImageIcon multiPImg = new ImageIcon(buffer);
		card.setIcon(multiPImg);
		card.setBounds(x, y, 65, 113);
		getBackgroundLabel().add(card);

		cards.add(card);
		
		return card;
	}

	public void cardSelected(boolean temp, JButton card) {
		
		if(temp) {
			
			card.setBounds(card.getBounds().x, card.getBounds().y - 30, 65, 113);
		}
		
		else {
			
			card.setBounds(card.getBounds().x, card.getBounds().y + 30, 65, 113);
		}
	}
}