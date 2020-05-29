package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class PvE implements Screen {
    RPS game;

    // Instance Variables
    Random random = new Random();
    SpriteBatch batch;
    BitmapFont font;
    Texture background;
    Texture Exitbtn;
    Texture Backbtn;
    Texture p1;
    Texture p2;
    Rectangle[] hitboxes;
    private ArrayList<Integer> highscores;

    private int p1Value;
    private int p1Score;
    private int p2Score;
    private int p1Rounds;
    private int p1Highscores;
    private int p2Highscores;
    private int CPUValue = random.nextInt(3)+1;

    private boolean p1Ready;
    private boolean p2Ready;
    private boolean isBlack;
    private boolean P1canClick;

    private float Time;

    // Constructor
    public PvE(RPS game, ArrayList<Integer> scores, int player1, int player2) {
        this.game = game;
        batch = new SpriteBatch();
        font = new BitmapFont();
        background = new Texture(Gdx.files.internal("PvP.png"));
        Exitbtn = new Texture(Gdx.files.internal("X.png"));
        Backbtn = new Texture(Gdx.files.internal("Back.png"));
        p1 = new Texture(Gdx.files.internal("black.jpeg"));
        p2 = new Texture(Gdx.files.internal("black.jpeg"));
        p1Value = 0;
        p1Value = 0;
        p1Score = 0;
        p1Highscores = player1;
        p2Highscores = player2;
        p2Score = 0;
        p1Rounds = 0;
        createhitboxes();
        p1Ready = false;
        p2Ready = false;
        isBlack = true;
        P1canClick = true;
        highscores = scores;
        Time = 0f;
    }

    // creates the hitboxes for all the buttons
    private void createhitboxes() {
        hitboxes = new Rectangle[4];
        hitboxes[0] = new Rectangle(700, 700, 100, 100);
        hitboxes[1] = new Rectangle(50, Gdx.graphics.getHeight() / 2, 300, 300);
        hitboxes[2] = new Rectangle(450, Gdx.graphics.getHeight() / 2, 300, 300);
        hitboxes[3] = new Rectangle(600, 0, 200, 117);
    }

    /* draws buttons and scores to the screen, also handles the logic for determining a winner and keeping score.
       Also implements a timer for the buttons to prevent bugs.
     */
    @Override
    public void render(float delta) {
        Time += delta;
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0);
        renderhitboxes();
        displayScore();
        batch.end();
        checkInput();
        if(p1Ready && p2Ready && isBlack) {
            displayResult(p1Value, CPUValue);
            P1canClick = false;
            Time = 0;
        } else if ((p1Ready == false || p2Ready == false) && Time > 1.2f) {
            isBlack = false;
            p1 = new Texture(Gdx.files.internal("black.jpeg"));
            p2 = new Texture(Gdx.files.internal("black.jpeg"));
            P1canClick = true;
            findWinner();
        } else {
            isBlack = true;
        }
    }

    // render all buttons to the screen with their respective hitboxes
    private void renderhitboxes() {
        batch.draw(Exitbtn, hitboxes[0].x, hitboxes[0].y, hitboxes[0].width, hitboxes[0].height);
        batch.draw(p1, hitboxes[1].x, hitboxes[1].y, hitboxes[1].width, hitboxes[1].height);
        batch.draw(p2, hitboxes[2].x, hitboxes[2].y, hitboxes[2].width, hitboxes[2].height);
        batch.draw(Backbtn, hitboxes[3].x, hitboxes[3].y, hitboxes[3].width, hitboxes[3].height);
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

    // disposes SpriteBatch, fonts and images when game closes or screen changes.
    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        background.dispose();
        Exitbtn.dispose();
        Backbtn.dispose();
        p1.dispose();
        p2.dispose();
    }

    // checks for input on each button and changes variables accordingly.
    private void checkInput() {
        if ((Gdx.input.getX() >= hitboxes[0].x && Gdx.input.getX() <= hitboxes[0].x + hitboxes[0].width) && (Gdx.graphics.getHeight() - Gdx.input.getY() >= hitboxes[0].y && Gdx.graphics.getHeight() - Gdx.input.getY() <= hitboxes[0].y + hitboxes[0].height)) {
            Exitbtn = new Texture(Gdx.files.internal("X2.png"));
            if (Gdx.input.isTouched()) {
                Gdx.app.exit();
            }
        }  else if ((Gdx.input.getX() >= hitboxes[3].x && Gdx.input.getX() <= hitboxes[3].x + hitboxes[3].width) && (Gdx.graphics.getHeight() - Gdx.input.getY() >= hitboxes[3].y && Gdx.graphics.getHeight() - Gdx.input.getY() <= hitboxes[3].y + hitboxes[3].height)) {
            Backbtn = new Texture(Gdx.files.internal("Back2.png"));
            if (Gdx.input.isTouched()) {
                game.setScreen(new MainMenu(game, highscores, p1Highscores, p2Highscores));
            }
        } else {
            Backbtn = new Texture(Gdx.files.internal("Back.png"));
            Exitbtn = new Texture(Gdx.files.internal("X.png"));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Z) && P1canClick) { // rock
            p1Value = 1;
            p1Ready = true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.X) && P1canClick) { // paper
            p1Value = 2;
            p1Ready = true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.C) && P1canClick) { // scissors
            p1Value = 3;
            p1Ready = true;
        } else if (CPUValue == 1) { // rock
            p2Ready = true;
        } else if (CPUValue == 2) { // paper
            p2Ready = true;
        } else if (CPUValue == 3) { // scissors
            p2Ready = true;
        } else if ((Gdx.input.getX() >= hitboxes[3].x && Gdx.input.getX() <= hitboxes[3].x + hitboxes[3].width) && (Gdx.graphics.getHeight() - Gdx.input.getY() >= hitboxes[3].y && Gdx.graphics.getHeight() - Gdx.input.getY() <= hitboxes[3].y + hitboxes[3].height)) {
            Backbtn = new Texture(Gdx.files.internal("Back2.png"));
            if (Gdx.input.isTouched()) {
                game.setScreen(new MainMenu(game, highscores, p1Highscores, p2Highscores));
            }
        }
    }

    // displays the images of each players selections on the screen
    private void displayResult(int pl1, int pl2) {
        if (pl1 == 1) {
            p1 = new Texture(Gdx.files.internal("rock.jpg"));
            p1Ready = false;
        } else if (pl1 == 2) {
            p1 = new Texture(Gdx.files.internal("paper.jpg"));
            p1Ready = false;
        } else if (pl1 == 3) {
            p1 = new Texture(Gdx.files.internal("Scissors.jpg"));
            p1Ready = false;
        }
        if (pl2 == 1) {
            p2 = new Texture(Gdx.files.internal("rock.jpg"));
            p2Ready = false;
        } else if (pl2 == 2) {
            p2 = new Texture(Gdx.files.internal("paper.jpg"));
            p2Ready = false;
        } else if (pl2 == 3) {
            p2 = new Texture(Gdx.files.internal("Scissors.jpg"));
            p2Ready = false;
        }
    }

    // finds the winner of a round and increments the scores accordingly. Also keeps track of high scores and organizes them
    private void findWinner() {
        if(p1Value == 1 && CPUValue == 2) {
            p2Score++;
            p1Value = 0;
            CPUValue = random.nextInt(3)+1;
        } else if(p1Value == 1 && CPUValue == 3) {
            p1Score++;
            p1Value = 0;
            CPUValue = random.nextInt(3)+1;
        } else if(p1Value == 2 && CPUValue == 1) {
            p1Score++;
            p1Value = 0;
            CPUValue = random.nextInt(3)+1;
        } else if(p1Value == 2 && CPUValue == 3) {
            p2Score++;
            p1Value = 0;
            CPUValue = random.nextInt(3)+1;
        } else if(p1Value == 3 && CPUValue == 1) {
            p2Score++;
            p1Value = 0;
            CPUValue = random.nextInt(3)+1;
        } else if(p1Value == 3 && CPUValue == 2) {
            p1Score++;
            p1Value = 0;
            CPUValue = random.nextInt(3)+1;
        } else {
            p1Value = 0;
            CPUValue = random.nextInt(3)+1;
        }
        if(p1Score == 3) {
            p1Score = 0;
            p2Score = 0;
            p1Rounds++;
        } else if(p2Score == 3) {
            if(p1Rounds != 0) {
                highscores.add(0, p1Rounds);
            }
            Collections.sort(highscores);
            Collections.reverse(highscores);
            JOptionPane.showMessageDialog(null, highscores, "Highscores", JOptionPane.INFORMATION_MESSAGE);
            game.setScreen(new MainMenu(game, highscores, p1Highscores, p2Highscores));
        }
    }

    // displays the scores to the screen via Bit Map font.
    private void displayScore() {
        font.setColor(0,0,0,1f);
        font.draw(batch, "Player1 score: " + p1Score + "/3",100,200);
        font.draw(batch, "CPU score: " + p2Score + "/3",400,200);
        font.draw(batch, "Player1 Rounds won: " + p1Rounds,100,100);
    }
}