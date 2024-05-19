package com.mygdx.game;

import static com.mygdx.game.StickyTanks.SCR_HEIGHT;
import static com.mygdx.game.StickyTanks.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;


public class ScreenMenu implements Screen {
    StickyTanks project1;
    SpriteBatch batch;
    OrthographicCamera camera;
    Vector3 touch;
    BitmapFont font;

    Buttons btnGame;
    Buttons btnExit;
    Buttons btnSettings;
    Buttons btnHangar;
    Sound ButtonClickSound, BackMusic;

    Texture Img;
    boolean isMusicPlaying = false;
    public  ScreenMenu(StickyTanks project1) {
        this.project1 = project1;

        batch = project1.batch;
        camera = project1.camera;
        touch = project1.touch;
        font = project1.fontforMenu;


        btnGame = new Buttons(SCR_WIDTH/6.5f, SCR_HEIGHT*85/100,"Play", font);
        btnExit = new Buttons(SCR_WIDTH/6.5f, SCR_HEIGHT*25/100,"Exit", font);
        btnSettings = new Buttons(SCR_WIDTH/9, SCR_HEIGHT*45/100,"Settings", font);
        btnHangar = new Buttons(SCR_WIDTH/7.5f, SCR_HEIGHT*65/100,"Hangar", font);

        ButtonClickSound = Gdx.audio.newSound(Gdx.files.internal("ButtonClickSound.mp3"));
        BackMusic = Gdx.audio.newSound(Gdx.files.internal("MusicForMenu.mp3"));
       Img = new Texture("MainMenuBackground.jpg");

    }

    @Override
    public void show() {
        isMusicPlaying = false;
    }

    @Override
    public void render(float delta) {
        if(project1.isMusicOn & !isMusicPlaying){
            BackMusic.play(project1.masterVolume/100);
            isMusicPlaying = true;
        }
        if (Gdx.input.justTouched()) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);
            if(btnGame.click(touch.x, touch.y)) {
                ButtonClickSound.play(project1.masterVolume/100);
                project1.setScreen(project1.screenGame);
            }
            if(btnExit.click(touch.x, touch.y)){
                ButtonClickSound.play(project1.masterVolume/100);
                Gdx.app.exit();
            }
            if(btnSettings.click(touch.x, touch.y)){
                ButtonClickSound.play(project1.masterVolume/100);
                project1.setScreen(project1.screenSettings);
            }
            if(btnHangar.click(touch.x, touch.y)){
                ButtonClickSound.play(project1.masterVolume/100);
                project1.setScreen(project1.screenHangar);
            }


        }

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(Img, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        btnGame.font.draw(batch, btnGame.text, btnGame.x, btnGame.y);
        btnSettings.font.draw(batch, btnSettings.text, btnSettings.x, btnSettings.y);
        btnExit.font.draw(batch, btnExit.text, btnExit.x, btnExit.y);
        btnHangar.font.draw(batch,btnHangar.text, btnHangar.x, btnHangar.y);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        BackMusic.stop();
    }

    @Override
    public void dispose(){
    }

}





