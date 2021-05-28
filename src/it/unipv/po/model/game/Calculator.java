package it.unipv.po.model.game;

/**
 * Questa classe esegue i calcoli relativi alla partita.
 * 
 * @author Luca Furfaro, Giuseppe Lentini
 */
import java.util.ArrayList;
import java.util.Collections;

import it.unipv.po.model.cards.Card;
import it.unipv.po.model.cards.Suit;

public class Calculator {

	private Game game;

	public Calculator(Game game) {
		this.game = game;
	}

	public void finalScore() {

		int scoreA = 0;
		int scoreB = 0;
		int numDenariA = 0;
		int numDenariB = 0;

		for (Card card : game.getTeams().get(0).getCardsCollected()) {

			if (card.getValue() == 7 && card.getSuit() == Suit.DENARI) {
				// punto settebello
				scoreA += 1;
			}

			if (card.getSuit() == Suit.DENARI) {
				numDenariA += 1;
			}
		}

		if (game.getTeams().get(0).getCardsCollected().size() > 20) {
			// punto carte
			scoreA += 1;
		}
		if (numDenariA > 5) {
			// punto denari
			scoreA += 1;
		}
		game.getTeams().get(0).setNumScope();
		scoreA += game.getTeams().get(0).getNumScope();

		for (Card card : game.getTeams().get(1).getCardsCollected()) {

			if (card.getValue() == 7 && card.getSuit() == Suit.DENARI) {
				// punto settebello
				scoreB += 1;
			}

			if (card.getSuit() == Suit.DENARI) {
				numDenariB += 1;
			}
		}

		if (game.getTeams().get(1).getCardsCollected().size() > 20) {
			// punto carte
			scoreB += 1;
		}
		if (numDenariB > 5) {
			// punto denari
			scoreB += 1;
		}
		
		game.getTeams().get(1).setNumScope();
		scoreB += game.getTeams().get(1).getNumScope();

		if (calculatePrimiera(game.getTeams().get(0).getCardsCollected()) > calculatePrimiera(
				game.getTeams().get(1).getCardsCollected())) {

			scoreA++;
		}

		else if (calculatePrimiera(game.getTeams().get(0).getCardsCollected()) < calculatePrimiera(
				game.getTeams().get(1).getCardsCollected())) {

			scoreB++;
		}

		game.getTeams().get(0).setTotalPoints(scoreA + game.getTeams().get(0).getTotalPoints());
		game.getTeams().get(1).setTotalPoints(scoreB + game.getTeams().get(1).getTotalPoints());

	}

	public int calculatePrimiera(ArrayList<Card> cards) {

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
					}

					else {
						break;
					}
				case BASTONI:
					if (bastoni == 0) {
						bastoni++;
						primiera += s.getPrimieraValue();
						counter++;
					} else {
						break;
					}
				case COPPE:
					if (coppe == 0) {
						coppe++;
						primiera += s.getPrimieraValue();
						counter++;
					} else {
						break;
					}

				case SPADE:
					if (spade == 0) {
						spade++;
						primiera += s.getPrimieraValue();
						counter++;
					} else {
						break;
					}
					break;
				}
			}
		}
		return primiera;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
}
