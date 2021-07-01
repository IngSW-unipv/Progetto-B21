package it.unipv.ingsw.client.model.game.player;

import java.util.ArrayList;

import it.unipv.ingsw.client.model.game.cards.Card;

public interface Actions {

	ArrayList<Card> playCard(ArrayList<Card> cardsOnBoard);
}
