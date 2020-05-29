package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class RPS extends Game {
	SpriteBatch batch;

	// Instance variables
	private int p1Highscores = 0;
	private int p2Highscores = 0;

	ArrayList<Integer> highscores = new ArrayList<>();
	Music music;

	// sets default values such as music and sets the screen to MainMenu. Acts as a constructor for the game
    public void create () {
		batch = new SpriteBatch();
		music = Gdx.audio.newMusic(Gdx.files.internal("Jazz.mp3"));
		music.setLooping(true);
		music.setVolume(0.4f);
		music.play();
		this.setScreen(new MainMenu(this, highscores, p1Highscores, p2Highscores));
	}

	// run the Game classes render method
	public void render() {
		super.render();
	}

	// dispose of the SpriteBatch when the game is closed or screen is changed
	public void dispose() {
		batch.dispose();
	}
}