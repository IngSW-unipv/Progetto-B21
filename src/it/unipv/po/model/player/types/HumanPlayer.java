package it.unipv.po.model.player.types;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import it.unipv.po.model.cards.Card;
import it.unipv.po.model.player.Player;

/**
 * Questa classe permette ad un giocatore umano di effettuare le sue mosse.
 * 
 * @author Paolo Falzone, Giuseppe Lentini
 */

public class HumanPlayer extends Player {

	public HumanPlayer(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<Card> playCard(ArrayList<Card> cardsOnBoard) {

		getTemp().clear();

		int i;
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("\n|CARTE SUL TAVOLO|");
		
		for (int j = 0; j <= cardsOnBoard.size() - 1; j++) {
			System.out.println(j + "| " + cardsOnBoard.get(j).getValue() + " di " + cardsOnBoard.get(j).getSuit());
		}
		System.out.println("\nSeleziona una carta: (specificare l'indice)");

		for (int j = 0; j < getDeck().size(); j++) {

			System.out.println(j + "| " + getDeck().get(j).getValue() + " di " + getDeck().get(j).getSuit());
		}
		try {
			i = Integer.parseInt(keyboard.readLine());
		} catch (IOException e) {
			e.printStackTrace();
			i = 0;
		}

		if (cardsOnBoard.size() != 0) {
			chooseCards(cardsOnBoard);
		}

		System.out.println(
				getNickname() + " gioca la carta " + getDeck().get(i).getValue() + " di " + getDeck().get(i).getSuit());

		getTemp().add(getDeck().get(i));

		return getTemp();
	}

	public ArrayList<Card> chooseCards(ArrayList<Card> cardsOnBoard) {

		String in;
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Selezionare le carte da prendere: (specificare gli indici, concludere con 'x')");

		for (int j = 0; j == cardsOnBoard.size() - 1; j++) {

			System.out.println(j + "| " + cardsOnBoard.get(j).getValue() + " di " + cardsOnBoard.get(j).getSuit());
		}
		try {
			while (((in = keyboard.readLine()).charAt(0) != 'x')) {
				getTemp().add(cardsOnBoard.get(Integer.parseInt(in)));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return getTemp();
	}

}
