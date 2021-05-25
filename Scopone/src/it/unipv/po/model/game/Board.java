package it.unipv.po.model.game;

import java.util.ArrayList;
import java.util.Random;

import it.unipv.po.model.cards.Card;
import it.unipv.po.model.cards.Suit;
import it.unipv.po.model.player.Player;
import it.unipv.po.model.player.Team;

/**
 * Questa classe modellizza il tavolo di gioco. Ho dato le funzioni principali al tavolo
 * in quanto visto come l'ambiente di gioco e gestore della distribuzione delle carte ai giocatori
 * e dell'avvio della partita.
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
	
	public Board(Player one, Player two, Player three, Player four) {
		super();
		
		teams = new ArrayList<>();
		deck = new ArrayList<Card>();
		shuffledDeck = new ArrayList<Card>();
		cardsOnBoard = new ArrayList<Card>();
		
		makeTeam(one, two, three, four);
		createDeck();
		start(one, two, three, four);
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
	public void start(Player one, Player two, Player three, Player four) {
		
		shuffle();
		giveCards(one, two, three, four);
	}
	
	/**
	 * Questo metodo crea i team e li memorizza.
	 * 
	 * @param one il primo giocatore
	 * @param two il secondo giocatore
	 * @param three il terzo giocatore
	 * @param four il quarto giocatore
	 */
	private void makeTeam(Player one, Player two, Player three, Player four) {
		
		Team a = new Team();
		Team b = new Team();
		
		one.setPlayerIndex(0);
		one.setTeamIndex(0);
		two.setPlayerIndex(1);
		two.setTeamIndex(0);
		
		three.setPlayerIndex(0);
		three.setTeamIndex(1);
		four.setPlayerIndex(1);
		four.setTeamIndex(1);
		
		
		a.getPlayers().add(one);
		a.getPlayers().add(two);
		
		b.getPlayers().add(three);
		b.getPlayers().add(four);
		
		teams.add(a);
		teams.add(b);
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
	private void giveCards(Player one, Player two, Player three, Player four) {
		
		for (int i = 0; i <= 9; i++) {
			
			one.getDeck().add(shuffledDeck.get(i));			
		}
		
		for (int i = 10; i <= 19; i++) {
			
			two.getDeck().add(shuffledDeck.get(i));
			
		}
		
		for (int i = 20; i <= 29; i++) {
	
			three.getDeck().add(shuffledDeck.get(i));
		}

		for (int i = 30; i <= 39; i++) {
	
			four.getDeck().add(shuffledDeck.get(i));
		}
		
	}

	public boolean playerActionMonitoring(Card card, Player player) {

		if (cardsOnBoard.isEmpty()) {
			
			cardsOnBoard.add(card);
			player.getDeck().remove(card);
		}
		
		else if (!cardsOnBoard.isEmpty() && presaSingola(cardsOnBoard, card)) {
				
				ArrayList<Card> temp = new ArrayList<Card>();
				
				for (Card s : cardsOnBoard) {
					
					if (s.getValue() == card.getValue()) {
						
						temp.add(s);
					}
				}
				
				cardsOnBoard.remove(temp.get(0));
				player.getDeck().remove(card);
				teams.get(player.getTeamIndex()).getCardsCollected().add(card);
				teams.get(player.getTeamIndex()).getCardsCollected().add(temp.get(0));
			}
			
			else {
			
				cardsOnBoard.add(card);
				player.getDeck().remove(card);
			}		
		
		return true;
	}
	
	
	private boolean presaSingola(ArrayList<Card> cardsOnBoard, Card card) {
		
		for (Card s : cardsOnBoard) {
			
			if (s.getValue() == card.getValue()) {
		
				return true;
			}
		}
		
		return false;
	}
}

