package it.unipv.ingsw.client.model.test;

import java.util.ArrayList;

import it.unipv.ingsw.client.model.game.Game;
import it.unipv.ingsw.client.model.game.player.types.*;
public class ModelTester {

	public static void main(String[] args) {
	
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new BotPlayer());
		players.add(new BotPlayer());
		players.add(new BotPlayer());
		players.add(new BotPlayer());

		Game board = new Game(players);
		board.start();
	}
}
