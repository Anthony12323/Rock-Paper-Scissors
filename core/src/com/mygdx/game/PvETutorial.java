package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class PvETutorial implements Screen {
    RPS game;

    // Instance Variables
    private SpriteBatch batch;
    private Texture background;
    private Texture startBtn;
    private Rectangle Start;
    private ArrayList<Integer> highscores;

    private int p1Highscores;
    private int p2Highscores;

    // Constructor
    public PvETutorial(RPS game, ArrayList<Integer> scores, int player1, int player2) {
        this.game = game;
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("PvEBackground2.png"));
        Start = new Rectangle(Gdx.graphics.getWidth()/2-150,100,300,100);
        startBtn = new Texture(Gdx.files.internal("Start.png"));
        highscores = scores;
        p1Highscores = player1;
        p2Highscores = player2;
    }

    // renders the buttons and handles input and changes screens accordingly.
    @Override
    public void render(float delta) {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background,0,0);
        batch.draw(startBtn, Start.x, Start.y, Start.width, Start.height);
        batch.end();
        if((Gdx.input.getX()>=Start.x && Gdx.input.getX()<=Start.x+Start.width)&&(Gdx.graphics.getHeight()-Gdx.input.getY()>=Start.y && Gdx.graphics.getHeight()-Gdx.input.getY()<=Start.y+Start.height)) {
            startBtn = new Texture(Gdx.files.internal("Start2.png"));
            if(Gdx.input.isTouched()) {
                game.setScreen(new PvE(game, highscores, p1Highscores, p2Highscores));
            }
        } else {
            startBtn = new Texture(Gdx.files.internal("Start.png"));
        }
    }

    // Unused LibGdx methods
    @Override
    public void show() { }
    @Override
    public void resize(int width, int height) { }
    @Override
    public void pause() { }
    @Override
    public void resume() { }
    @Override
    public void hide() { }

    // disposes of SpriteBatch and images when the game is closed or screen is changed
    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        startBtn.dispose();
    }
}