package it.unipv.po.view.labels;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import it.unipv.po.view.imagesLoader.ImagesLoader;
import it.unipv.po.view.menuElements.buttons.BackButton;
import it.unipv.po.view.menuElements.buttons.StartButton;

public class CreaLobbyUI extends JLabel {

	private static final long serialVersionUID = 1L;
	private BufferedImage buffer;
	private ImagesLoader loader;
	private StartButton start;
	private BackButton back;
	private JLabel advisor;

//__________________COSTRUTTORE___________________

	public CreaLobbyUI(ImagesLoader loader) {
		super();
		this.loader = loader;

		create();
	}

//________________GETTERS&SETTERS_________________
	public StartButton getStart() {
		return start;
	}

	public void setStart(StartButton start) {
		this.start = start;
	}

	public BackButton getBack() {
		return back;
	}

	public void setBack(BackButton back) {
		this.back = back;
	}

	public JLabel getAdvisor() {
		return advisor;
	}

	public void setAdvisor(JLabel advisor) {
		this.advisor = advisor;
	}

//______________________METODI______________________
	private void create() {

		this.buffer = loader.uploadImage("/it/unipv/po/images/background.png");
		ImageIcon image = new ImageIcon(buffer);
		setIcon(image);
		setBounds(0, 0, 800, 500);
		setLayout(null);

		this.start = new StartButton(loader);
		setStart(start);
		add(start);

		this.back = new BackButton(loader);
		setBack(back);
		add(back);

		// advisor
		this.advisor = new JLabel();
		advisor.setBounds(0, 442, 800, 20);
		advisor.setOpaque(true);
		setAdvisor(advisor);
		add(advisor);
	}
}
