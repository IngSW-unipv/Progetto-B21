package it.unipv.po.model.test;

import it.unipv.po.model.game.Calculator;
import it.unipv.po.model.game.Game;
import it.unipv.po.model.player.BotPlayer1;
import it.unipv.po.model.player.BotPlayer2;
import it.unipv.po.model.player.HumanPlayer;

public class Tester {

	public static void main(String[] args) {
		
		HumanPlayer one = new HumanPlayer("Fabio Secci");
		BotPlayer2 two = new BotPlayer2();
		BotPlayer1 three = new BotPlayer1();
		BotPlayer2 four = new BotPlayer2();
		
				
		

		Game board = new Game(one, two, three, four);
		
		Calculator c = new Calculator(board);
		
		for(int i = 0; i < 10; i++) {
			
		board.playerActionMonitoring(one, board.getCardsOnBoard());
		board.playerActionMonitoring(two, board.getCardsOnBoard());
		board.playerActionMonitoring(three, board.getCardsOnBoard());
		board.playerActionMonitoring(four, board.getCardsOnBoard());
		}
		
		
		c.finalScore();
		
		System.out.println("punti team A: " + board.getTeams().get(0).getTotalPoints());
		System.out.println("punti team B: " + board.getTeams().get(1).getTotalPoints());
	}
}
