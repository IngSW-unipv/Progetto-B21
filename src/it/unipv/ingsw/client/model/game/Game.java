package it.unipv.ingsw.client.model.game;

import java.util.ArrayList;
import java.util.Random;

import it.unipv.ingsw.client.model.game.cards.Card;
import it.unipv.ingsw.client.model.game.cards.Suit;
import it.unipv.ingsw.client.model.game.player.team.Team;
import it.unipv.ingsw.client.model.game.player.types.*;

/**
 * Questa classe modellizza il tavolo di gioco. Possiede le funzioni principali
 * in quanto visto come l'ambiente di gioco e gestore della distribuzione delle
 * carte ai giocatori e dell'avvio della partita.
 * 
 * @author gruppo B
 */
public class Game {

	// ________________ATTRIBUTI________________
	private ArrayList<Card> cardsOnBoard;
	private ArrayList<Card> deck;
	private ArrayList<Card> shuffledDeck;
	private ArrayList<Team> teams;
	private ArrayList<Player> players;
	private int turn;
	private int teamIndex;

	/**
	 * Crea una partita.
	 * 
	 * @param players : lista di giocatori che faranno parte della partita.
	 */
	public Game(ArrayList<Player> players) {
		this.players = players;
		initialize();
		makeTeam();
		createDeck();
	}

	// ______________GETTERS & SETTERS______________

	public synchronized ArrayList<Card> getCardsOnBoard() {
		return cardsOnBoard;
	}

	public synchronized void setCardsOnBoard(ArrayList<Card> cardsOnBoard) {

		this.cardsOnBoard = cardsOnBoard;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public int getTeamIndex() {
		return teamIndex;
	}

	public void setTeamIndex(int teamIndex) {
		this.teamIndex = teamIndex;
	}

	public synchronized ArrayList<Team> getTeams() {
		return teams;
	}

	public synchronized int getTurn() {
		return turn;
	}

	// __________________METODI__________________

	/**
	 * Questo metodo ha il compito di inizializzare i vari componenti che verranno
	 * utilizzati nella partita.
	 * 
	 */

	private void initialize() {

		if (players.size() < 4) {
			for (int i = 4 - players.size(); i > 0; i--) {
				this.players.add(new BotPlayer(((i % 2 == 0) ? "botA" : "botB")));
			}
		}

		cardsOnBoard = new ArrayList<Card>();
		teams = new ArrayList<Team>();
		deck = new ArrayList<Card>();
		shuffledDeck = new ArrayList<Card>();
		turn = 0;

	}

	/**
	 * Questo metodo fa iniziare la partita.
	 */
	public void start() {

		shuffle();
		giveCards();
		nextTurn();
	}

	/**
	 * Questo metodo crea due team e li memorizza.
	 * 
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
		a.getPlayers().add(players.get(2));

		b.getPlayers().add(players.get(1));
		b.getPlayers().add(players.get(3));

		teams.add(a);
		teams.add(b);
	}

	/**
	 * Questo metodo crea il deck di gioco.
	 */
	private void createDeck() {

		for (Suit s : Suit.values()) { // Distribuisce i semi

			for (int i = 1; i <= 10; i++) { // Distribuisce i valori. Parte da 1 per motivi di costruzione del deck.

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

		shuffledDeck.clear();
		Random temp = new Random();

		for (int i = 0; i < 40; i++) {

			int r = (int) (temp.nextInt(i + 1));

			shuffledDeck.add(r, deck.get(i));
		}
	}

	/**
	 * Questo metodo dà le carte ai players. Si occupa di dividere il deck in 4
	 * subdeck e di assegnarli ai quattro giocatori.
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

	/**
	 * (Per thread) Questo metodo fa eseguire un azione ad un giocatore bot o umano.
	 * 
	 * @param player : il bot o il giocatore umano che deve eseguire l'azione.
	 */
	public synchronized boolean playerActionMonitoring(Player player) {

		switch (player.playCard(cardsOnBoard).size()) {

		case 1:
			cardsOnBoard.addAll(player.getCardsListTemp());
			player.getDeck().removeAll(player.getCardsListTemp());
			return true;

		default:

			int temp = 0;

			for (int i = 0; i < player.getCardsListTemp().size() - 1; i++) {

				temp += player.getCardsListTemp().get(i).getValue();
			}

			if (temp == player.getCardsListTemp().get(player.getCardsListTemp().size() - 1).getValue()) {

				getTeams().get(player.getTeamIndex()).getCardsCollected().addAll(player.getCardsListTemp());
				cardsOnBoard.removeAll(player.getCardsListTemp());
				player.getDeck().remove(player.getCardsListTemp().get(player.getCardsListTemp().size() - 1));
				setTeamIndex(player.getTeamIndex());

				if (cardsOnBoard.isEmpty()) {

					teams.get(player.getTeamIndex()).scopa();
					player.setScopa();
				}

				return true;
			}
		}
		return false;
	}

	/**
	 * Questo metodo permette di monitorare la giocata di un giocatore umano che
	 * interagisce tramite la GUI.
	 * 
	 * @param player : il giocatore umano che effettua la giocata.
	 */
	public synchronized boolean playerActionMonitoring(HumanPlayer player) {

		int counter = 0;

		switch (player.getCardsListTemp().size()) {

		case 1: // caso nel cui il giocatore non fa una presa

			for (Card table : getCardsOnBoard()) {

				counter += table.getValue();

				if (counter == player.getCardsListTemp().get(0).getValue()) {

					return false;
				}

				else {

					if (player.getCardsListTemp().get(0).getValue() == table.getValue()) {

						return false;
					}
				}
			}

			getCardsOnBoard().addAll(player.getCardsListTemp());
			player.getDeck().removeAll(player.getCardsListTemp());

			return true;

		default: // caso nel cui il giocatore fa una presa

			if (player.getCardsListTemp().size() > 2)
				for (Card s : cardsOnBoard) {

					if (player.getCardsListTemp().get(player.getCardsListTemp().size() - 1).getValue() == s.getValue())
						return false;
				}

			int temp = 0;
			int valueCardPlayed = player.getCardsListTemp().get(player.getCardsListTemp().size() - 1).getValue();

			for (int i = 0; i < player.getCardsListTemp().size() - 1; i++) {

				temp += player.getCardsListTemp().get(i).getValue();
			}

			if (temp == valueCardPlayed) {

				getTeams().get(player.getTeamIndex()).getCardsCollected().addAll(player.getCardsListTemp());
				getCardsOnBoard().removeAll(player.getCardsListTemp());
				player.getDeck().remove(player.getCardsListTemp().get(player.getCardsListTemp().size() - 1));
				setTeamIndex(player.getTeamIndex());

				if (getCardsOnBoard().isEmpty()) {

					teams.get(player.getTeamIndex()).scopa();
					player.setScopa();
				}

				return true;
			}

			else {

				return false;
			}
		}
	}

	/**
	 * (Per thread) Aggiorna il contatore del turno.
	 * 
	 */
	public synchronized void nextTurn() {
		turn++;
		if (turn == 5)
			turn = 1;
	}

	/**
	 * (Per thread) La partita finisce. Vengono calcolati i punti finali e mostrati
	 * all'utente. turn viene messo a zero così i thread non possono attivarsi
	 * finché non inizia una nuova partita.
	 * 
	 */
	public synchronized void endGame() {

		turn = 0;
		for (Team s : teams)
			s.restart();
		changeIndex();
	}

	public void calcolate() {
		teams.get(getTeamIndex()).getCardsCollected().addAll(cardsOnBoard);
		cardsOnBoard.clear();
		Calculator.finalScore(getTeams().get(0), getTeams().get(1));
	}

	/**
	 * Cambia l'indice del giocatore.
	 */
	private synchronized void changeIndex() {
		for (int i = 3; i >= 0; i--) {
			int t = getPlayers().get(i).getPlayerIndex() - 1;
			if (t == 0)
				t = 4;
			getPlayers().get(i).setPlayerIndex(t);
		}
	}
}
