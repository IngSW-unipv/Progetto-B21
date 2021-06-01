package it.unipv.po.model.test;

import java.util.ArrayList;

import it.unipv.po.model.game.Calculator;
import it.unipv.po.model.game.Game;
import it.unipv.po.model.player.Player;
import it.unipv.po.model.player.types.BotPlayer1;
import it.unipv.po.model.player.types.BotPlayer2;
import it.unipv.po.model.player.types.HumanPlayer;

public class Tester {

	public static void main(String[] args) {
	
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new HumanPlayer("Fabio Secci"));
		players.add(new BotPlayer2());
		players.add(new BotPlayer1());
		players.add(new BotPlayer2());

		Game board = new Game(players);
		
		Calculator c = new Calculator(board);
		
		
		
		// da sostituire con l'utilizzo di thread -----v
		for(int i = 0; i < 10; i++) {	
		board.playerActionMonitoring(players.get(0), board.getCardsOnBoard());
		board.playerActionMonitoring(players.get(1), board.getCardsOnBoard());
		board.playerActionMonitoring(players.get(2), board.getCardsOnBoard());
		board.playerActionMonitoring(players.get(3), board.getCardsOnBoard());
		}
		c.finalScore();
		System.out.println("punti team A: " + board.getTeams().get(0).getTotalPoints());
		System.out.println("punti team B: " + board.getTeams().get(1).getTotalPoints());
		//-----^
		
	}
}
