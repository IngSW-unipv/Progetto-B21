package it.unipv.po.model.game;

import java.util.ArrayList;

import java.util.Random;
import it.unipv.po.model.cards.Card;
import it.unipv.po.model.cards.Suit;
import it.unipv.po.model.player.Player;
import it.unipv.po.model.player.PlayerThread;
import it.unipv.po.model.player.Team;

/**
 * Questa classe modellizza il tavolo di gioco. Ho dato le funzioni principali
 * al tavolo in quanto visto come l'ambiente di gioco e gestore della
 * distribuzione delle carte ai giocatori e dell'avvio della partita.
 * 
 * @author gruppo B
 */
public class Game {

	private ArrayList<Card> cardsOnBoard;
	private ArrayList<Card> deck;
	private ArrayList<Card> shuffledDeck;
	private ArrayList<Team> teams;
	private ArrayList<Player> players;
	private int turn;
	private int teamIndex;

	public int getTeamIndex() {
		return teamIndex;
	}

	/**
	 * Gli ArrayList tengono conto delle carte sul tavolo, del deck usato in gioco,
	 * del deck mescolato e dei teams.
	 */

	public Game(ArrayList<Player> players) {
		
		teams = new ArrayList<Team>();
		deck = new ArrayList<Card>();
		shuffledDeck = new ArrayList<Card>();
		cardsOnBoard = new ArrayList<Card>();
		this.players = players;
		setTeamIndex(0);

		makeTeam();
		createDeck();
		start();
	}

	synchronized public ArrayList<Card> getCardsOnBoard() {
		return cardsOnBoard;
	}

	synchronized public void setCardsOnBoard(ArrayList<Card> cardsOnBoard) {

		this.cardsOnBoard = cardsOnBoard;
	}

	synchronized public ArrayList<Team> getTeams() {
		return teams;
	}
	
	/**
	 * Questo metodo crea i team e li memorizza.
	 * 
	 */
	private void makeTeam() {

		Team a = new Team();
		Team b = new Team();

		players.get(0).setPlayerIndex(1);
		players.get(0).setTeamIndex(0);
		players.get(1).setPlayerIndex(2);
		players.get(1).setTeamIndex(1);

		players.get(2).setPlayerIndex(3);
		players.get(2).setTeamIndex(0);
		players.get(3).setPlayerIndex(4);
		players.get(3).setTeamIndex(1);

		a.getPlayers().add(players.get(0));
		a.getPlayers().add(players.get(1));

		b.getPlayers().add(players.get(2));
		b.getPlayers().add(players.get(3));

		teams.add(a);
		teams.add(b);
	}

	/**
	 * questo metodo crea il deck di gioco. Il primo for distribuisce i semi, il
	 * secondo i valori. Il secondo for parte da 1 per motivi di costruzione del
	 * deck.
	 */
	private void createDeck() {

		for (Suit s : Suit.values()) {

			for (int i = 1; i <= 10; i++) {

				int temp = 0;

				switch (i) {

				case 7:
					temp = 21;
					break;
				case 6:
					temp = 18;
					break;
				case 1:
					temp = 16;
					break;
				case 5:
					temp = 15;
					break;
				case 4:
					temp = 14;
					break;
				case 3:
					temp = 13;
					break;
				case 2:
					temp = 12;
					break;
				default:
					temp = 10;
					break;
				}

				Card card = new Card(i, s, temp);

				deck.add(card);
			}
		}
	}

	
	/**
	 * Questo metodo ha il compito di far iniziare la partita. 
	 * Si mischiano le carte, si danno ai giocatori, si inizializza il turno, e si startano i thread dei giocatori.
	 */
	public void start() {
		
		shuffle();
		giveCards();
		
		turn = 1;
		
		for (int i = 0; i < 4; i++) {
	    	PlayerThread tr = new PlayerThread(this, players.get(i));
	        tr.start();
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
	 * Questa funzione da le carte ai players. si occupa di dividere il deck in 4
	 * subdeck e di assegnarli.
	 */
	private void giveCards() {

		for (int i = 0; i <= 9; i++) {

			players.get(0).getDeck().add(shuffledDeck.get(i));
		}

		for (int i = 10; i <= 19; i++) {

			players.get(1).getDeck().add(shuffledDeck.get(i));

		}

		for (int i = 20; i <= 29; i++) {

			players.get(2).getDeck().add(shuffledDeck.get(i));
		}

		for (int i = 30; i <= 39; i++) {

			players.get(3).getDeck().add(shuffledDeck.get(i));
		}

	}
	
	
	/*
	 * (Per thread)
	 * Questo metodo fa fare un azione ad un giocatore bot o umano
	 * 
	 */
	synchronized public void playerActionMonitoring(Player player, ArrayList<Card> cardsOnBoard) {


		switch (player.playCard(cardsOnBoard).size()) {

		case 1:
			cardsOnBoard.addAll(player.getCardsListTemp());
			player.getDeck().removeAll(player.getCardsListTemp());
			setTeamIndex(player.getTeamIndex());
			
			break;

		default:

			int temp = 0;

			for (int i = 0; i < player.getCardsListTemp().size() - 1; i++) {

				temp += player.getCardsListTemp().get(i).getValue();
			}

			if (temp == player.getCardsListTemp().get(player.getCardsListTemp().size()-1).getValue()) {

				System.out.println("|CROUPIER| mossa valida");
				getTeams().get(player.getTeamIndex()).getCardsCollected().addAll(player.getCardsListTemp());
				cardsOnBoard.removeAll(player.getCardsListTemp());
				player.getDeck().remove(player.getCardsListTemp().get(player.getCardsListTemp().size() - 1));
				setTeamIndex(player.getTeamIndex());
				
				if (cardsOnBoard.isEmpty()) {

					teams.get(player.getTeamIndex()).scopa();
					System.out.println("|CROUPIER| SCOPA!");
				}
			}

			else {

				System.out.println("|CROUPIER| mossa non valida");
				playerActionMonitoring(player, cardsOnBoard);
			}

			break;
		}
	}
	
	/*
	 * (Per thread)
	 * Restituisce il valore del turno corrente.
	 * 
	 */
	synchronized public int getTurn() {
		return turn;
	}
	
	/*
	 * (Per thread)
	 * Aggiorna il contatore del turno.
	 * 
	 */
	synchronized public void nextTurn() {
		turn++;
		if (turn == 5) turn = 1;
	} 
	
	/*
	 * (Per thread)
	 * La partita finisce. Vengono calcolati i punti finali e mostrati all'utente.
	 * turn viene messo a zero così i thread non possono attivarsi finchè non inizia una nuova partita.
	 * 
	 */
	synchronized public void endGame() {
		
		teams.get(getTeamIndex()).getCardsCollected().addAll(cardsOnBoard);
		cardsOnBoard.clear();
		Calculator.finalScore(this);
		System.out.println("punti team A: " + getTeams().get(0).getTotalPoints());
		System.out.println("punti team B: " + getTeams().get(1).getTotalPoints());
	}


	public void setTeamIndex(int teamIndex) {
		this.teamIndex = teamIndex;
	}
	
}
