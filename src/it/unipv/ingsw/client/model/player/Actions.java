package it.unipv.ingsw.client.model.player;

import java.util.ArrayList;

import it.unipv.ingsw.client.model.card.Card;

/**
 * Interfaccia che definisce il contratto tra tutti i player (human e bot) ovvero l'azione di giocare una carta.
 * 
 * @author lucaf
 *
 */

public interface Actions {

	ArrayList<Card> playCard(ArrayList<Card> cardsOnBoard);
}
