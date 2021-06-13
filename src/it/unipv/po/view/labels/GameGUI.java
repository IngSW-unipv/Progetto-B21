package it.unipv.po.view.labels;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import it.unipv.po.view.ImagesLoader;
import it.unipv.po.view.buttons.gameButtons.CardButton;
import it.unipv.po.view.buttons.gameButtons.SendButton;

public class GameGUI extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ImagesLoader loader;
	private BufferedImage buffer;
	private SendButton send;
	private JLabel gameAdvisor;
	private ArrayList<JButton> cards;


	public GameGUI(ImagesLoader loader) {
		super();
		this.loader = loader;
		create();
		game();
	}

	public SendButton getSend() {
		return send;
	}

	public void setSend(SendButton send) {
		this.send = send;
	}

	public JLabel getGameAdvisor() {
		return gameAdvisor;
	}

	public void setGameAdvisor(JLabel gameAdvisor) {
		this.gameAdvisor = gameAdvisor;
	}

	private void create() {
		
		this.buffer = loader.uploadImage("/it/unipv/po/images/table.png");
		ImageIcon game = new ImageIcon(buffer);
		setIcon(game);
		setBounds(0, 0, 800, 500);
		setLayout(null);
		cards = new ArrayList<JButton>();
		setVisible(true);
	}
	
	public void game() {

		// invio
		this.send = new SendButton(loader);
		send.setBounds(750, 309, 30, 113);
		add(send);
		
		// chat
		this.gameAdvisor = new JLabel();
		gameAdvisor.setBounds(0, 440, 800, 20);
		gameAdvisor.setOpaque(true);
		add(gameAdvisor);
	}

public JButton cardsBuilder(int x, int y, String txt) {
		
		JButton card = new CardButton(loader, x, y, txt);
		add(card);

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
	
	public void cardPlayed(JButton card) {
		
		card.setVisible(false);
	}
}
