package it.unipv.ingsw.client.view.menuElements.text;

import java.awt.Color;
import java.awt.TextField;

public class TextArea extends TextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	private int l;
	private int a;

	public TextArea(int x, int y, int l, int a) {
		super();
		this.x = x;
		this.y = y;
		this.l = l;
		this.a = a;
		
		create();
	}

	private void create() {
		
		setBackground(Color.WHITE);
		setBounds(x, y, l, a);
		setVisible(true);
	}
}