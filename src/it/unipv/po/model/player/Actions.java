package it.unipv.po.model.player;
import java.util.ArrayList;
import it.unipv.po.model.cards.Card;

public interface Actions {

	Card playCard();
	ArrayList<Card> chooseCards(ArrayList<Card> cardsOnBoard);
}
