package it.unipv.po.model.test;

import it.unipv.po.model.game.Calculator;
import it.unipv.po.model.game.Game;
import it.unipv.po.model.player.BotPlayer;

public class Tester {

	public static void main(String[] args) {

		BotPlayer one = new BotPlayer();
		BotPlayer two = new BotPlayer();
		BotPlayer three = new BotPlayer();
		BotPlayer four = new BotPlayer();
		
				
		

		Game board = new Game(one, two, three, four);
		
		Calculator c = new Calculator(board);
		
		board.playerActionMonitoring(one.playCard(), one);
		board.playerActionMonitoring(three.playCard(), three);
		board.playerActionMonitoring(two.playCard(), two);
		board.playerActionMonitoring(four.playCard(), four);
		
		System.out.println(board.getCardsOnBoard().size());

		board.playerActionMonitoring(one.playCard(), one);
		board.playerActionMonitoring(three.playCard(), three);
		board.playerActionMonitoring(two.playCard(), two);
		board.playerActionMonitoring(four.playCard(), four);
		
		board.playerActionMonitoring(one.playCard(), one);
		board.playerActionMonitoring(three.playCard(), three);
		board.playerActionMonitoring(two.playCard(), two);
		board.playerActionMonitoring(four.playCard(), four);
		
		board.playerActionMonitoring(one.playCard(), one);
		board.playerActionMonitoring(three.playCard(), three);
		board.playerActionMonitoring(two.playCard(), two);
		board.playerActionMonitoring(four.playCard(), four);
		
		board.playerActionMonitoring(one.playCard(), one);
		board.playerActionMonitoring(three.playCard(), three);
		board.playerActionMonitoring(two.playCard(), two);
		board.playerActionMonitoring(four.playCard(), four);
		
		board.playerActionMonitoring(one.playCard(), one);
		board.playerActionMonitoring(three.playCard(), three);
		board.playerActionMonitoring(two.playCard(), two);
		board.playerActionMonitoring(four.playCard(), four);
		
		board.playerActionMonitoring(one.playCard(), one);
		board.playerActionMonitoring(three.playCard(), three);
		board.playerActionMonitoring(two.playCard(), two);
		board.playerActionMonitoring(four.playCard(), four);
		
		board.playerActionMonitoring(one.playCard(), one);
		board.playerActionMonitoring(three.playCard(), three);
		board.playerActionMonitoring(two.playCard(), two);
		board.playerActionMonitoring(four.playCard(), four);
		
		board.playerActionMonitoring(one.playCard(), one);
		board.playerActionMonitoring(three.playCard(), three);
		board.playerActionMonitoring(two.playCard(), two);
		board.playerActionMonitoring(four.playCard(), four);
		
		board.playerActionMonitoring(one.playCard(), one);
		board.playerActionMonitoring(three.playCard(), three);
		board.playerActionMonitoring(two.playCard(), two);
		board.playerActionMonitoring(four.playCard(), four);
		
		System.out.println(board.getCardsOnBoard().size());
		
		
		/*System.out.println(c.BubbleSort(board.getTeams().get(0).getCardsCollected()).get(0).getValue());
		System.out.println(c.BubbleSort(board.getTeams().get(0).getCardsCollected()).get(0).getSuit());
		System.out.println(c.BubbleSort(board.getTeams().get(0).getCardsCollected()).get(1).getValue());
		System.out.println(c.BubbleSort(board.getTeams().get(0).getCardsCollected()).get(1).getSuit());
		System.out.println(c.BubbleSort(board.getTeams().get(0).getCardsCollected()).get(2).getValue());
		System.out.println(c.BubbleSort(board.getTeams().get(0).getCardsCollected()).get(2).getSuit());
		System.out.println(c.BubbleSort(board.getTeams().get(0).getCardsCollected()).get(3).getValue());
		System.out.println(c.BubbleSort(board.getTeams().get(0).getCardsCollected()).get(3).getSuit());*/
		
		c.finalScore();
		
		System.out.println("punteggio team A: " + board.getTeams().get(0).getTotalPoints());
		System.out.println("punteggio team B: " + board.getTeams().get(1).getTotalPoints());
		
		System.out.println(c.calculatePrimiera(board.getTeams().get(0).getCardsCollected()));
		System.out.println(c.calculatePrimiera(board.getTeams().get(1).getCardsCollected()));		

	}

}
