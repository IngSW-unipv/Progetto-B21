package it.unipv.po.model.game;

import java.util.ArrayList;
import java.util.Random;
import it.unipv.po.model.cards.Card;
import it.unipv.po.model.cards.Suit;
import it.unipv.po.model.player.Player;
import it.unipv.po.model.player.Team;

/**
 * Questa classe modellizza il tavolo di gioco. Ho dato le funzioni principali
 * al tavolo in quanto visto come l'ambiente di gioco e gestore della
 * distribuzione delle carte ai giocatori e dell'avvio della partita.
 * 
 * @author Giuseppe Lentini
 */
public class Game {

	private ArrayList<Card> cardsOnBoard;
	private ArrayList<Card> deck;
	private ArrayList<Card> shuffledDeck;
	private ArrayList<Team> teams;

	/**
	 * Gli ArrayList tengono conto delle carte sul tavolo, del deck usato in gioco,
	 * del deck mescolato e dei teams.
	 */

	public Game(Player one, Player two, Player three, Player four) {
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
	 * Questo metodo ha il compito di far iniziare la partita. ï¿½ inserita nel
	 * costruttore in quanto una volta costruita la board si puï¿½ iniziare a
	 * giocare, ed ï¿½ resa di tipo public per permettere di ricominciare la partita
	 * fin tanto che un team non vince l'incontro.
	 */
	public void start(Player one, Player two, Player three, Player four) {

		shuffle();
		giveCards(one, two, three, four);
	}

	/**
	 * Questo metodo crea i team e li memorizza.
	 * 
	 * @param one   il primo giocatore
	 * @param two   il secondo giocatore
	 * @param three il terzo giocatore
	 * @param four  il quarto giocatore
	 */
	private void makeTeam(Player one, Player two, Player three, Player four) {

		Team a = new Team();
		Team b = new Team();

		one.setPlayerIndex(1);
		one.setTeamIndex(0);
		two.setPlayerIndex(2);
		two.setTeamIndex(1);

		three.setPlayerIndex(3);
		three.setTeamIndex(0);
		four.setPlayerIndex(4);
		four.setTeamIndex(1);

		a.getPlayers().add(one);
		a.getPlayers().add(two);

		b.getPlayers().add(three);
		b.getPlayers().add(four);

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

	public void playerActionMonitoring(Player player, ArrayList<Card> cardsOnBoard) {

		player.playCard(cardsOnBoard);

		switch (player.getTemp().size()) {

		case 1:
			cardsOnBoard.addAll(player.getTemp());
			player.getDeck().removeAll(player.getTemp());
			break;
			
		case 2:
			
			if(player.getTemp().get(0).getValue() == player.getTemp().get(1).getValue()) {
				
				cardsOnBoard.remove(player.getTemp().get(0));
				player.getDeck().remove(player.getTemp().get(1));
				getTeams().get(player.getTeamIndex()).getCardsCollected().addAll(player.getTemp());
				
				if(cardsOnBoard.isEmpty()) {
					
					teams.get(player.getTeamIndex()).scopa();
				}
				
				break;
			}
			
			else { //questo else è da testare con intelligenza umana, è il caso in cui si scelgono carte sbagliate
				
				System.out.println("mossa non valida!");
				playerActionMonitoring(player, cardsOnBoard);
				break;
			}
			
		default:

			int count = 0;

			for (int i = 0; i < player.getTemp().size() - 1; i++) {

				count += player.getTemp().get(i).getValue();
			}
			
			if (count == player.getTemp().get(player.getTemp().size() - 1).getValue()) {

				System.out.println("|CROUPIER: mossa valida|");
				getTeams().get(player.getTeamIndex()).getCardsCollected().addAll(player.getTemp());
				cardsOnBoard.removeAll(player.getTemp());
			}

			else {

				System.out.println("|CROUPIER: mossa non valida|");
				playerActionMonitoring(player, cardsOnBoard);
			}
			
			break;
		}
	}
}
