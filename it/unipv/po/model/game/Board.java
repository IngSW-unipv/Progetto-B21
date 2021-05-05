package it.unipv.po.model.game;

import java.util.ArrayList;
import java.util.Random;

import it.unipv.po.model.cards.Card;
import it.unipv.po.model.cards.Suit;
import it.unipv.po.model.player.Team;

/**
 * Questa classe modellizza il tavolo di gioco. Ho dato le funzioni principali al tavolo
 * in quanto si prevede che il tavolo si occupi della distribuzione delle carte ai giocatori
 * e dell'avvio della partita, dopo la chiamata da un controllore esterno. 
 * 
 * @author Giuseppe Lentini
 */
public class Board {

	private ArrayList<Card> cardsOnBoard;
	private ArrayList<Card> deck;
	private ArrayList<Card> shuffledDeck;
	private ArrayList<Team> teams;
	/**
	 * Gli ArrayList tengono conto delle carte sul tavolo, del deck usato in gioco, del deck 
	 * mescolato e dei teams.
	 */
	
	public Board(ArrayList<Team> temp) {
		super();
		this.teams = temp;
		start();
	}

	public ArrayList<Card> getCardsOnBoard() {
		return cardsOnBoard;
	}

	public void setCardsOnBoard(ArrayList<Card> cardsOnBoard) {

		this.cardsOnBoard = cardsOnBoard;
	}
	
	public ArrayList<Team> getTeams() {
		return teams;
	}

	/**
	 * Questo metodo ha il compito di far iniziare la partita. è inserita nel costruttore in quanto
	 * una volta costruita la board si può iniziare a giocare, ed è resa di tipo public per permettere di 
	 * ricominciare la partita fin tanto che un team non vince l'incontro.
	 */
	public void start() {
		
		createDeck();
		shuffle();
		giveCards();
	}
	
	/**
	 * questo metodo crea il deck di gioco. Il primo for distribuisce i semi, il secondo
	 * i valori. Il secondo for parte da 1 per motivi di costruzione del deck.
	 */
	private void createDeck() {
		
		for (Suit s : Suit.values()) {
		
			for (int i = 1; i <= 10; i++) {
				
				Card card = new Card(i, s);
				
				deck.add(card);			
			}
		}	
	}
	
	/**
	 * Questo metodo mescola il deck.
	 */
	private void shuffle() {
		
		Random temp = new Random();
		
		for (int i = 0; i < 40; i++) {
			
			int r = (int) (temp.nextInt(i + 1));
			
			shuffledDeck.add(r, deck.get(i));
		}
	}

	/**
	 * Questa funzione da le carte ai players. si occupa di dividere il deck in 4 subdeck e di assegnarli.
	 */
	private void giveCards() {
				
		int first = 0;
		int second = 9;
		
		for (int i = 0; i == 1; i++) {
			
			for (int j = 0; i == 1; j++) {
				
				ArrayList<Card> part = new ArrayList<Card>(shuffledDeck.subList(first, second));

				teams.get(i).getPlayers().get(j).setDeck(part);
				
				first = first + 10;
				second = second + 10;			
			}
		}
	}
}

