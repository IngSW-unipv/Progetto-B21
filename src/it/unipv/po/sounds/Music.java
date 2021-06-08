package it.unipv.po.sounds;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Music {

	private boolean isMusicOn;
	Clip hit;

	public Music() {
		this.isMusicOn = true;
	}
	
	public void setMusicOn(boolean isMusicOn) {
		this.isMusicOn = isMusicOn;
	}



	public boolean isMusicOn() {
		return isMusicOn;
	}

	public Clip getHit() {
		return hit;
	}

	public void playMusic() {

		try {
			AudioInputStream audio = AudioSystem.getAudioInputStream(new File("it/unipv/po/sounds/music.wav"));
			this.hit = AudioSystem.getClip();
			hit.open(audio);
			hit.start();
		} catch (Exception ex) {
			System.out.println("Error with playing sound.");
			ex.printStackTrace();
		}
	}
}
