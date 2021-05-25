package it.unipv.po.model.cards;

/**
 *  Questa classe modellizza la carta.
 *  
 * @author Giuseppe Lentini
 */
public class Card {

	private int value;
	private Suit suit;
	/**
	 * 
	 * @param value indica il valore della carta
	 * @param suit indica il seme della carta
	 */
	public Card(int value, Suit suit) {
		super();
		this.value = value;
		this.suit = suit;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Suit getSuit() {
		return suit;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}
}
