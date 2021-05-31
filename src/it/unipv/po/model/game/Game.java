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
	private int handNumber; //numero della mano
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
		handNumber=0;
		teams = new ArrayList<>();
		deck = new ArrayList<Card>();
		shuffledDeck = new ArrayList<Card>();
		cardsOnBoard = new ArrayList<Card>();

		makeTeam(one, two, three, four);
		createDeck();
		start(one, two, three, four);
	}

	public void incrementHandNumber() {
		this.handNumber++;
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
	 * Questo metodo ha il compito di far iniziare la partita. � inserita nel
	 * costruttore in quanto una volta costruita la board si pu� iniziare a giocare,
	 * ed � resa di tipo public per permettere di ricominciare la partita fin tanto
	 * che un team non vince l'incontro.
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

	public boolean playerActionMonitoring(Card card, Player player) {

		if (!cardsOnBoard.isEmpty() && presaSingola(card)) {

			ArrayList<Card> temp = new ArrayList<Card>();

			for (Card s : cardsOnBoard) {

				if (s.getValue() == card.getValue()) {

					temp.add(s);
				}
			}

			cardsOnBoard.remove(temp.get(0));
			player.getDeck().remove(card);
			System.out.println(player+" HA PRESO "+temp.get(0));
			System.out.println(cardsOnBoard);
			teams.get(player.getTeamIndex()).getCardsCollected().add(card);
			teams.get(player.getTeamIndex()).getCardsCollected().add(temp.get(0));
			
			checkScopa(player);
			
		}
		else if(!cardsOnBoard.isEmpty() && presaMultipla(card)) {
			ArrayList<Card> temp= player.chooseCards(this.getCardsOnBoard());
			cardsOnBoard.removeAll(temp);
			player.getDeck().remove(card);
			System.out.println(player+" HA PRESO "+temp);
			System.out.println(cardsOnBoard);

			teams.get(player.getTeamIndex()).getCardsCollected().add(card);
			teams.get(player.getTeamIndex()).getCardsCollected().addAll(temp);
			
			checkScopa(player);
		}
		else {
			cardsOnBoard.add(card);
			player.getDeck().remove(card);
		}

		return true;
	}
	
	private void checkScopa(Player player) {
		if(cardsOnBoard.size()==0 && !(handNumber==9 && player.getTeamIndex()==1 && player.getPlayerIndex()==1)) {
			// Se un giocatore prende tutte le carte in tavola fa scopa
			//ad eccezione che ciò avvenga nell'ultima giocata dell'ultima mano di una smazzata (in questo caso il mazziere non può fare scopa).
			System.out.println(player+" HA FATTO SCOPA!");
			player.incrementNumScope();
		}
	}
	
	private boolean presaMultipla(Card card) {
		int indexes[], n=0;
		ArrayList<Card> temp;
		if(card.getValue()>2) { //sul tavolo non potranno mai esserci due carte aventi lo stesso value (dunque non potranno mai esserci due ASSI)
			//controlla la presenza di carte sul tavolo con value strettamente minore di quello della carta giocata (quella passata come parametro di questo metodo)
			indexes=new int[cardsOnBoard.size()];
			for(int i=0;i<cardsOnBoard.size();i++)
			if(getCardsOnBoard().get(i).getValue()<card.getValue()) {
				indexes[n]=i;
				n++;
			}
			if(n<2) 
				return false;
			else { // entra nel blocco se sono presenti sul tavolo almeno 2 carte che rispettano la condizione espressa nel commento precedente
				temp=new ArrayList<Card>(n);
				for(int j=0;j<n;j++)
				temp.add(cardsOnBoard.get(indexes[j]).copy());
				if(n==2 && ((temp.get(0).getValue()+temp.get(1).getValue())==card.getValue()))
					return true;
				if(n==3 && ((temp.get(0).getValue()+temp.get(1).getValue()+temp.get(2).getValue())==card.getValue()))
					return true;
				if(n==4 && ((temp.get(0).getValue()+temp.get(1).getValue()+temp.get(2).getValue()+temp.get(3).getValue())==card.getValue())) //10=4+3+2+1
					return true;
				
				/* per gestire le altre prese multiple
				// 3 <= n < 10
				if(n>3 && card.getValue()>4) {
					if(card.getValue()==5) {//massimo 2 addendi
						
					}
					if(card.getValue()>5 && card.getValue()<10) {//massimo 3 addendi
						
					}
					if(card.getValue()==10) {	//massimo 4 addendi
						
					}
				}
				*/
			}
				
		}
		return false;
	}

	private boolean presaSingola(Card card) {

		for (Card s : cardsOnBoard) {

			if (s.getValue() == card.getValue()) {

				return true;
			}
		}

		return false;
	}
}