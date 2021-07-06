package it.unipv.ingsw.client.model.game.cards;

import java.io.Serializable;

/**
 * Questa classe modellizza la carta.
 * 
 * @author Giuseppe Lentini
 */
public class Card implements Comparable<Card>,Serializable {

	//________________ATTRIBUTI________________
	private static final long serialVersionUID = 1123123123L;
	private int value;
	private Suit suit;
	private int primieraValue;
	private boolean selected;
	
	/**
	 * Crea una carta.
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

	//______________GETTERS & SETTERS______________
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

	//__________________METODI__________________

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
	
	public boolean ciao(Object card) {
		Card x=(Card)card;
		if(value==x.getValue() && suit.equals(x.getSuit()))
			return true;
		return false;
	}
	public Card copy() {
		return new Card(value, suit, primieraValue);
	}
}