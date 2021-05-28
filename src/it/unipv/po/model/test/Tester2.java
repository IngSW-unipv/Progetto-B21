package it.unipv.po.model.test;

import it.unipv.po.model.game.Calculator;
import it.unipv.po.model.game.Game;
import it.unipv.po.model.player.BotPlayer;
import it.unipv.po.model.player.HumanPlayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class Tester2 {

	public static void main(String[] args) {
		BufferedReader keyboard=new BufferedReader(new InputStreamReader(System.in));
		String nome;
		System.out.println("Come ti chiami?");
		try {
			nome=keyboard.readLine();
		}catch(IOException e) {
			e.printStackTrace();
			nome="Anonimo";
		}
		//TEAM A 
		//HumanPlayer one = new HumanPlayer(nome);
		BotPlayer one = new BotPlayer(nome);
		BotPlayer two = new BotPlayer("BOT A2");
		//TEAM B
		BotPlayer three = new BotPlayer("BOT B1");
		BotPlayer four = new BotPlayer("BOT B2");
		
				
		

		Game board = new Game(one, two, three, four);
		
		Calculator c = new Calculator(board);
		
		//PRIMA SMAZZATA
		for(int i=0;i<10;i++) {
			System.out.println("La tua mano:\n"+one.getDeck());
			
			//
			System.out.println("La mano di BOT B1:\n"+three.getDeck());
			System.out.println("La mano di BOT A2:\n"+two.getDeck());
			System.out.println("La mano di BOT B2:\n"+four.getDeck());
			
			
			board.playerActionMonitoring(one.playCard(), one);
		
			board.playerActionMonitoring(three.playCard(), three);
			board.playerActionMonitoring(two.playCard(), two);
			board.playerActionMonitoring(four.playCard(), four);
		
			System.out.println("Ci sono "+board.getCardsOnBoard().size()+" carte sul tavolo");
			System.out.println(board.getCardsOnBoard());
		}		
		c.finalScore();
		
		System.out.println("punteggio team A: " + board.getTeams().get(0).getTotalPoints());
		System.out.println("punteggio team B: " + board.getTeams().get(1).getTotalPoints());		
		System.out.println(c.calculatePrimiera(board.getTeams().get(0).getCardsCollected()));
		System.out.println(c.calculatePrimiera(board.getTeams().get(1).getCardsCollected()));
		
		//azzerare numScope per ogni player al termine di ogni smazzata
	}

}
