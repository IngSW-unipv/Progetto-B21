package it.unipv.po.model.test;

import it.unipv.po.model.game.Board;
import it.unipv.po.model.player.BotPlayer;

public class Tester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		BotPlayer one = new BotPlayer();
		BotPlayer two = new BotPlayer();
		BotPlayer three = new BotPlayer();
		BotPlayer four = new BotPlayer();

		Board board = new Board(one, two, three, four);
		
		board.playerActionMonitoring(one.playCard(), one);
		board.playerActionMonitoring(three.playCard(), three);
		board.playerActionMonitoring(two.playCard(), two);
		board.playerActionMonitoring(four.playCard(), four);
		
		board.playerActionMonitoring(one.playCard(), one);
		board.playerActionMonitoring(three.playCard(), three);
		board.playerActionMonitoring(two.playCard(), two);
		board.playerActionMonitoring(four.playCard(), four);
		
		System.out.println(board.getCardsOnBoard().size());

	}

}
