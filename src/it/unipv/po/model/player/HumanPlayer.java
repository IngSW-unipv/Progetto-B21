package it.unipv.po.model.player;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import it.unipv.po.model.cards.Card;
/**
 * Questa classe permette ad un giocatore umano di effettuare le sue mosse.
 * 
 * @author Paolo Falzone
 */

public class HumanPlayer extends Player {

	public HumanPlayer(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Card playCard() {
		int i;
		BufferedReader keyboard=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Seleziona una carta: (specificare l'indice)");
		try {
			i=Integer.parseInt(keyboard.readLine());
		}catch(IOException e) {
			e.printStackTrace();
			i=0;
		}
		System.out.println(this+" gioca la carta " + getDeck().get(i).toString());
		return getDeck().get(i);
	}

	@Override
	public ArrayList<Card> chooseCards(ArrayList<Card> cardsOnBoard){
		ArrayList<Card> cards=new ArrayList<Card>();
		String in;
		BufferedReader keyboard=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Selezionare le carte da prendere: (specificare gli indici, concludere con '#')");
		try {
		while((in=keyboard.readLine()).charAt(0)!='#') {
			cards.add(cardsOnBoard.get(Integer.parseInt(in)));
		}
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return cards;
	}

}
