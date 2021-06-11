package it.unipv.po.model.menu;

import java.util.ArrayList;

import it.unipv.po.model.game.Game;
import it.unipv.po.model.game.player.*;
import it.unipv.po.model.game.player.types.*;
import it.unipv.po.sounds.Music;

public class MainMenu {

	private Game scopone;
	private Music music;

	public MainMenu() {
		super();
		
		this.music = new Music();
		music.playMusic();
	}
	
	public Music getMusic() {
		return music;
	}

	public Game getScopone() {
		return scopone;
	}



	public void singlePlayer() {

		ArrayList<Player> players = new ArrayList<Player>();
		HumanPlayer human = new HumanPlayer("Fabio Secci");
		
		players.add(human);
		players.add(new BotPlayer2());
		players.add(new BotPlayer2());
		players.add(new BotPlayer2());

		this.scopone = new Game(players);
	}

	public void multiPlayer() {

	}
}
