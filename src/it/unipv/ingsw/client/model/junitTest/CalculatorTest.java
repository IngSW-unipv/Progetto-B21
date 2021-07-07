package it.unipv.ingsw.client.model.junitTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import it.unipv.ingsw.client.model.Calculator;
import it.unipv.ingsw.client.model.card.Card;
import it.unipv.ingsw.client.model.card.Suit;
/**

 * @author Paolo Falzone
 */
public class CalculatorTest {

	@Test
	public void testCalculatePrimiera() {
		ArrayList<Card> cards=new ArrayList<Card>();
		Card c1, c2;
		c1=new Card(7, Suit.DENARI, 21);
		c2=new Card(10, Suit.BASTONI, 10);
		cards.add(c1);
		cards.add(c2);
		assertEquals(31, Calculator.calculatePrimiera(cards));
	}

}
