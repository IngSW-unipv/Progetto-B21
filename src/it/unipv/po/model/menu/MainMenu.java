package it.unipv.po.model.menu;

import java.util.ArrayList;

import it.unipv.po.model.game.ScoponeGame;
import it.unipv.po.model.game.player.*;
import it.unipv.po.model.game.player.types.*;
import it.unipv.po.sounds.Music;

public class MainMenu {

	private ScoponeGame scopone;
	private Music music;

	public MainMenu() {
		super();

		this.music = new Music();
		music.playMusic();
	}

	public Music getMusic() {
		return music;
	}

	public ScoponeGame getScopone() {
		return scopone;
	}

	public void singlePlayer() {

		ArrayList<Player> players = new ArrayList<Player>();
		HumanPlayer human = new HumanPlayer("Fabio Secci");

		players.add(human);
		players.add(new BotPlayer2());
		players.add(new BotPlayer2());
		players.add(new BotPlayer2());

		this.scopone = new ScoponeGame(players);
	}

	public void multiPlayer() {

	}
}
