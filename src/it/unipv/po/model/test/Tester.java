package it.unipv.po.model.test;

import java.util.ArrayList;

import it.unipv.po.model.game.Game;
import it.unipv.po.model.game.player.Player;
import it.unipv.po.model.game.player.types.*;
public class Tester {

	public static void main(String[] args) {
	
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new HumanPlayer("Fabio Secci"));
		players.add(new BotPlayer2());
		players.add(new BotPlayer1());
		players.add(new BotPlayer2());

		Game board = new Game(players);
		board.start();
	}
}
