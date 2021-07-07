package it.unipv.ingsw.client.model;


import java.util.ArrayList;
import java.util.Collections;

import it.unipv.ingsw.client.model.card.Card;
import it.unipv.ingsw.client.model.card.Suit;
import it.unipv.ingsw.client.model.player.Team;

/**
 * Questa classe esegue i calcoli relativi alla partita. Oltre a calcolare il final score assegnando i punti in base
 * ai denari, carte e settebello, ha una funzione che ordina le carte in modo da poter calcolare la primiera.
 * 
 * @author Luca Furfaro, Giuseppe Lentini
 */
public class Calculator {

	//__________________METODI__________________
	
	/**
	 * Questo metodo calcola i punteggi finali di una smazzata dei due team passati come argomenti.
	 * @param a : primo team.
	 * @param b : secondo team.
	 */
	public static void finalScore(Team a, Team b) {

		int scoreA = 0;
		int scoreB = 0;
		int numDenariA = 0;
		int numDenariB = 0;

		a.setnCarte(a.getCardsCollected().size());
		b.setnCarte(b.getCardsCollected().size());

		for (Card card : a.getCardsCollected()) {

			if (card.getValue() == 7 && card.getSuit() == Suit.DENARI) {
				// punto settebello
				scoreA += 1;
				a.setSetteBello(true);
			}

			if (card.getSuit() == Suit.DENARI) {
				numDenariA += 1;
			}
		}
		
		a.setnDenari(numDenariA);

		if (a.getCardsCollected().size() > 20) {
			// punto carte
			scoreA += 1;
			a.setCarte(true);
		}
		if (numDenariA > 5) {
			// punto denari
			scoreA += 1;
			a.setDenari(true);
		}

		scoreA += a.getNumScope();

		for (Card card : b.getCardsCollected()) {

			if (card.getValue() == 7 && card.getSuit() == Suit.DENARI) {
				// punto settebello
				scoreB += 1;
				b.setSetteBello(true);
			}

			if (card.getSuit() == Suit.DENARI) {
				numDenariB += 1;
			}
		}
		b.setnDenari(numDenariB);

		if (b.getCardsCollected().size() > 20) {
			// punto carte
			scoreB += 1;
			b.setCarte(true);
		}
		if (numDenariB > 5) {
			// punto denari
			scoreB += 1;
			b.setDenari(true);
		}

		scoreB += b.getNumScope();

		
		a.setPuntiPrimiera(calculatePrimiera(a.getCardsCollected()));
		b.setPuntiPrimiera(calculatePrimiera(b.getCardsCollected()));
		
		
		
		if (a.getPuntiPrimiera() > b.getPuntiPrimiera()) {

			scoreA++;
			a.setPrimiera(true);
		}

		else if (a.getPuntiPrimiera() < b.getPuntiPrimiera()) {

			scoreB++;
			b.setPrimiera(true);
		}

		a.setTotalPoints(scoreA + a.getTotalPoints());
		b.setTotalPoints(scoreB + b.getTotalPoints());

	}
	
	/**
	 * 
	 * Metodo che accetta un array di cards e lo ordina in base al primiera value, per ogni seme. 
	 * 
	 * @param cards : le carte.
	 * @return il valore della primiera.
	 */

	static int calculatePrimiera(ArrayList<Card> cards) {

		int denari = 0;
		int bastoni = 0;
		int coppe = 0;
		int spade = 0;
		int primiera = 0;
		int counter = 0;

		Collections.sort(cards, Collections.reverseOrder());

		for (Card s : cards) {

			if (counter < 4) {

				switch (s.getSuit()) {

				case DENARI:
					if (denari == 0) {
						denari++;
						primiera += s.getPrimieraValue();
						counter++;
						break;

					} else {
						break;
					}
				case BASTONI:
					if (bastoni == 0) {
						bastoni++;
						primiera += s.getPrimieraValue();
						counter++;
						break;

					} else {
						break;
					}
				case COPPE:
					if (coppe == 0) {
						coppe++;
						primiera += s.getPrimieraValue();
						counter++;
						break;
					} else {
						break;
					}

				case SPADE:
					if (spade == 0) {
						spade++;
						primiera += s.getPrimieraValue();
						counter++;
						break;

					} else {
						break;
					}
				}
			}
		}
		return primiera;
	}
}
