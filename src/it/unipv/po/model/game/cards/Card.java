package it.unipv.po.model.game.cards;

/**
 * Questa classe modellizza la carta.
 * 
 * @author Giuseppe Lentini
 */
public class Card implements Comparable<Card> {

	private int value;
	private Suit suit;
	private int primieraValue;
	private boolean selected;

	/**
	 * 
	 * @param value indica il valore della carta
	 * @param suit  indica il seme della carta
	 */
	public Card(int value, Suit suit, int primieraValue) {
		super();
		this.value = value;
		this.suit = suit;
		this.primieraValue = primieraValue;
		this.selected = false;
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

	public int getPrimieraValue() {
		return primieraValue;
	}

	public void setPrimieraValue(int primieraValue) {
		this.primieraValue = primieraValue;
	}
	

	public boolean isSelected() {
		return selected;
	}

	public void setSelected() {
		this.selected = !selected;
	}

	@Override
	public int compareTo(Card o) {

		if (getPrimieraValue() < o.getPrimieraValue()) {
			return -1;
		}

		if (getPrimieraValue() > o.getPrimieraValue()) {
			return 1;
		}

		return 0;
	}

	public String toString() {

		String s = super.toString();
		s = ""+getValue() + getSuit();

		return s;
	}
}