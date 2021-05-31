package it.unipv.po.model.player;

import java.util.ArrayList;

import it.unipv.po.model.cards.Card;

public interface Actions {

	ArrayList<Card> playCard(ArrayList<Card> cardsOnBoard);
}
