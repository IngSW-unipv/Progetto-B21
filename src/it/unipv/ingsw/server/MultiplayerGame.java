package it.unipv.ingsw.server;

import java.util.ArrayList;
import java.util.Random;

import it.unipv.ingsw.client.model.game.Calculator;
import it.unipv.ingsw.client.model.game.cards.Card;
import it.unipv.ingsw.client.model.game.cards.Suit;
import it.unipv.ingsw.client.model.game.player.team.Team;
import it.unipv.ingsw.server.handlers.BotHandler;
import it.unipv.ingsw.server.handlers.ClientHandler;
import it.unipv.ingsw.server.handlers.Handler;

public class MultiplayerGame extends Thread{

	private ArrayList<Card> board;
	private ArrayList<Card> deck;
	private ArrayList<Card> shuffledDeck;
	private ArrayList<Team> teams;
	private ArrayList<Handler> players;
	private int turn;
	@SuppressWarnings("unused")
	private boolean move;
	
	public MultiplayerGame(ArrayList<ClientHandler> p) {
		initialize(p);
		System.out.println("@"+toString()+": " + "correctly initialized");
		makeTeam();
		System.out.println("@"+toString()+": " + "teams created");
		createDeck();
		System.out.println("@"+toString()+": " + "deck created");
	}

	private void initialize(ArrayList<ClientHandler> p) {
		this.players = new ArrayList<Handler>();
		for (ClientHandler ch : p) {
			players.add(ch);
		}
		if (players.size() < 4) {
			for (int i = 4-players.size(); i > 0; i--) {
				players.add(new BotHandler());
			}
		}
		board = new ArrayList<Card>();
		teams = new ArrayList<Team>();
		deck = new ArrayList<Card>();
		shuffledDeck = new ArrayList<Card>();
		move = false;
		turn = -2;
	}
	
	private void makeTeam() {
		Team team0 = new Team();
		Team team1 = new Team();

		players.get(0).setTurnIndex(1);
		players.get(0).setTeamIndex(0);
		players.get(1).setTurnIndex(2);
		players.get(1).setTeamIndex(1);
		players.get(2).setTurnIndex(3);
		players.get(2).setTeamIndex(0);
		players.get(3).setTurnIndex(4);
		players.get(3).setTeamIndex(1);		
		
		teams.add(team0);
		teams.add(team1);
	}
	
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

	public void run() {
		if (turn == -2) {
			teams.get(0).getCardsCollected().clear();
			teams.get(1).getCardsCollected().clear();
			nextTurn();
		}
		while (turn != -2) {
			if (turn == -1) {
				shuffle();
				giveCards();
				nextTurn();
			} else {
				System.out.println("@"+toString()+": " + "turn " + turn);
				for (Handler p : players) {
					if (p.getTurnIndex() == (turn % 4)) {
						players.get(turn % 4).requestMove();
					}	
				}
				for (int i = 30; i > 0; i--) {
					if (move == true)
						break;
					try {
						sleep(1000);
					} catch (InterruptedException e) {}
				}
				//vengono usati i metodi play e remove da client remoti
				if (move == false) {
					interruptGame();
				}
				move = false;
				try {
					sleep(3000);
				} catch (InterruptedException e) {}
				nextTurn();
			}
		}
	}
	
	private void shuffle() {
		shuffledDeck.clear();
		Random temp = new Random();
		for (int i = 0; i < 40; i++) {
			int r = (int) (temp.nextInt(i + 1));
			shuffledDeck.add(r, deck.get(i));
		}
	}
	
	private void giveCards() {
		ArrayList<Card> hand = new ArrayList<Card>();
		for (int j = 0; j < 4; j++) {
			hand.clear();
			for (int i = 10*j; i < 10*(j+1); i++) {
				hand.add(shuffledDeck.get(i));
			}
			players.get(j).setHand(hand);
		}
	}
	
	private synchronized void nextTurn() {
		turn++;
		if (turn == 40) {
			turn = -1;
			endRound();
		}
	}
	
	

	public synchronized void play(Card playedCard) {
		board.add(playedCard);
		notifyBoardChange();
		for (Handler p : players) {
			if (p.getTurnIndex() != (turn % 4)) {
				p.sendMessage("||GIOCATORE " + turn%4 + "|| " + players.get(turn%4).getNickname() + " gioca " + playedCard);
				System.out.println("@"+toString()+": " + "||GIOCATORE " + turn%4 + "|| " + players.get(turn%4).getNickname() + " gioca " + playedCard);
			}
		}
	}

	private void notifyBoardChange() {
		for (Handler p : players) {
			if (p.getTurnIndex() != (turn % 4)) {
				p.setCardsOnBoard(board);
			}
		}
	}

	public synchronized void remove(ArrayList<Card> takenCards) {
		board.removeAll(takenCards);
		notifyBoardChange();
		for (Handler p : players) {
			if (p.getTurnIndex() == (turn % 4)) {
				teams.get(p.getTeamIndex()).getCardsCollected().addAll(takenCards);
			}	
		}
	}
	
	public synchronized void setMove() {
		move = true;
	}
	
	public synchronized void endRound() {
		System.out.println("@"+toString()+": " + "fine round");
		for (Handler p : players) {
			if (p.getTurnIndex() == (turn % 4)) {
				teams.get(p.getTeamIndex()).getCardsCollected().addAll(board);
			}	
		}
		board.clear();
		notifyBoardChange();
		for (Handler p : players) {
			p.sendMessage("Round terminato");
		}
		Calculator.finalScore(teams.get(0), teams.get(1));
		try {
			sleep(10000);
		} catch (InterruptedException e) {}
		if ((teams.get(0).getTotalPoints() >= 21 || teams.get(1).getTotalPoints() >= 21) && teams.get(0).getTotalPoints() != teams.get(1).getTotalPoints()) {
			endGame();
		}
		changeIndex();
	}
	
	private synchronized void changeIndex() {
		for (int i = 3; i >= 0; i--) {
			int t = players.get(i).getTurnIndex() - 1;
			if (t == 0)
				t = 4;
			players.get(i).setTurnIndex(t);
		}
	}
	
	public synchronized void endGame() {
		System.out.println("@"+toString()+": " + "fine partita");
		int win = 0;
		if (teams.get(0).getTotalPoints() < teams.get(1).getTotalPoints())
			win = 1;
		for (Handler p : players) {
			if (p.getTeamIndex() == win) {
				p.sendMessage("Hai vinto con" + teams.get(win).getTotalPoints() + " vs " + teams.get(1-win).getTotalPoints() + " punti.");
			} else {
				p.sendMessage("Hai perso con" + teams.get(1-win).getTotalPoints() + " vs " + teams.get(win).getTotalPoints() + " punti.");
			}
		}
		turn = -3;
	}
	
	public synchronized void interruptGame() {
		turn = -3;
		System.out.println("@"+toString()+": game interrupted" );
	}

}
