package it.unipv.po.model.test;

import java.util.ArrayList;

import it.unipv.po.model.game.ScoponeGame;
import it.unipv.po.model.game.player.types.*;
public class ModelTester {

	public static void main(String[] args) {
	
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new BotPlayer());
		players.add(new BotPlayer());
		players.add(new BotPlayer());
		players.add(new BotPlayer());

		ScoponeGame board = new ScoponeGame(players);
		board.start();
	}
}
