package it.unipv.po.model.menu;

import java.util.ArrayList;

import it.unipv.po.model.game.ScoponeGame;
import it.unipv.po.model.game.player.*;
import it.unipv.po.model.game.player.types.*;
import it.unipv.po.sounds.Music;

public class MainMenu {

	private ScoponeGame scopone;
	private Music music;
	private String txt;

	public MainMenu() {
		super();

		this.music = new Music();
		music.playMusic();
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

	public Music getMusic() {
		return music;
	}

	public ScoponeGame getScopone() {
		return scopone;
	}

	public ScoponeGame singlePlayer() {

		ArrayList<Player> players = new ArrayList<Player>();
		HumanPlayer human = new HumanPlayer(txt);

		players.add(human);
		players.add(new BotPlayer2());
		players.add(new BotPlayer2());
		players.add(new BotPlayer2());

		return this.scopone = new ScoponeGame(players);
	}

	public void multiPlayer() {

	}
}
