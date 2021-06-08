package it.unipv.po.model.game.player;

import java.util.ArrayList;

import it.unipv.po.model.game.cards.Card;

public interface Actions {

	ArrayList<Card> playCard(ArrayList<Card> cardsOnBoard);
}
