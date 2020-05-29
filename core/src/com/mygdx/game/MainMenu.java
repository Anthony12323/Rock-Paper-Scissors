package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class MainMenu implements Screen {
    RPS game;

    // Instance Variables
    private SpriteBatch batch;
    private Texture background;
    private Texture PvPbtn;
    private Texture PvEbtn;
    private Texture Exitbtn;
    private Rectangle[] hitboxes;
    private ArrayList<Integer> highscores;

    private int p1Highscores;
    private int p2Highscores;

    // Constructor
    public MainMenu(RPS game, ArrayList<Integer> scores, int player1, int player2) {
        this.game = game;
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("MainMenu.png"));
        PvPbtn = new Texture(Gdx.files.internal("PvPbtn.png"));
        PvEbtn = new Texture(Gdx.files.internal("PvEbtn.png"));
        Exitbtn = new Texture(Gdx.files.internal("X.png"));
        highscores = scores;
        p1Highscores = player1;
        p2Highscores = player2;
        createHitboxes();
    }

    /* draws the main menu buttons, pvp, pve and exit. And checks if they are pressed, it then acts accordingly to that
       button being pressed. Also implements a system where if the mouse hovers over a button the texture changes.
    */
    @Override
    public void render(float delta) {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0,0);
        renderButtons();
        batch.end();
        if((Gdx.input.getX()>=hitboxes[0].x && Gdx.input.getX()<=hitboxes[0].x+hitboxes[0].width)&& (Gdx.graphics.getHeight()-Gdx.input.getY()>=hitboxes[0].y && Gdx.graphics.getHeight()-Gdx.input.getY()<=hitboxes[0].y+hitboxes[0].height)) {
            PvPbtn = new Texture(Gdx.files.internal("PvP2.png"));
            if(Gdx.input.isTouched()) {
                game.setScreen(new PvPTutorial(game, highscores, p1Highscores, p2Highscores));
            }
        } else if((Gdx.input.getX()>=hitboxes[1].x && Gdx.input.getX()<=hitboxes[1].x+hitboxes[1].width)&&(Gdx.graphics.getHeight()-Gdx.input.getY()>=hitboxes[1].y && Gdx.graphics.getHeight()-Gdx.input.getY()<=hitboxes[1].y+hitboxes[1].height)) {
            PvEbtn = new Texture(Gdx.files.internal("PvE2.png"));
            if(Gdx.input.isTouched()) {
                game.setScreen(new PvETutorial(game, highscores, p1Highscores, p2Highscores));
            }
        } else if((Gdx.input.getX()>=hitboxes[2].x && Gdx.input.getX()<=hitboxes[2].x+hitboxes[2].width)&&(Gdx.graphics.getHeight()-Gdx.input.getY()>=hitboxes[2].y && Gdx.graphics.getHeight()-Gdx.input.getY()<=hitboxes[2].y+hitboxes[2].height)) {
            Exitbtn = new Texture(Gdx.files.internal("X2.png"));
            if(Gdx.input.isTouched()) {
                Gdx.app.exit();
            }
        } else {
            PvPbtn = new Texture(Gdx.files.internal("PvPbtn.png"));
            PvEbtn = new Texture(Gdx.files.internal("PvEbtn.png"));
            Exitbtn = new Texture(Gdx.files.internal("X.png"));
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

    // disposes the SpriteBatch and textures when the game closes or screen changes
    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        PvPbtn.dispose();
        PvEbtn.dispose();
        Exitbtn.dispose();
    }

    // creates the hitboxes for buttons
    public void createHitboxes() {
        hitboxes = new Rectangle[3];
        hitboxes[0] = new Rectangle((Gdx.graphics.getWidth()/4)-100,(Gdx.graphics.getWidth()/4),200,130); // PVP
        hitboxes[1] = new Rectangle((Gdx.graphics.getWidth()/4)+300,(Gdx.graphics.getWidth()/4),200,130); // PVE
        hitboxes[2] = new Rectangle(700,700,100,100);
    }

    // draws the buttons to the screen using their hitboxes
    public void renderButtons() {
        batch.draw(PvPbtn, hitboxes[0].x, hitboxes[0].y, hitboxes[0].width, hitboxes[0].height);
        batch.draw(PvEbtn, hitboxes[1].x, hitboxes[1].y, hitboxes[1].width, hitboxes[1].height);
        batch.draw(Exitbtn, hitboxes[2].x, hitboxes[2].y, hitboxes[2].width, hitboxes[2].height);
    }
}