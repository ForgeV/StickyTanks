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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;


public class ScreenSettings implements Screen {
    StickyTanks project1;
    SpriteBatch batch;
    OrthographicCamera camera;
    Vector3 touch;
    BitmapFont font;

    Texture Background, ButtonBackImg, LeftArrow, RightArrow;

    Buttons btnSound, btnMusic, btnEditName, btnBack, btnSoundMin, btnSoundPlus;
    Buttons btnControl;
    Boolean isMusicPlaying = false;
    Sound BackMusic;

    public ScreenSettings(StickyTanks project1) {
        this.project1 = project1;

        batch = project1.batch;
        camera = project1.camera;
        touch = project1.touch;
        font = project1.fontForLevels;

        project1.masterVolume = 1f;

        BackMusic = Gdx.audio.newSound(Gdx.files.internal("MusicForHangar.mp3"));

        ButtonBackImg = new Texture("ButtonBack.png");
         Background = new Texture("SettingsBack.png");
         LeftArrow = new Texture("LeftArrow.png");
        RightArrow = new Texture("RightArrow.png");

        project1.ButtonClickSound = Gdx.audio.newSound(Gdx.files.internal("ButtonClickSound.mp3"));
        btnControl = new Buttons(SCR_WIDTH*6/10, SCR_HEIGHT*7/10,"Controls" , font, true);
        btnSound = new Buttons(SCR_WIDTH*2/10, SCR_HEIGHT*7/10, "Sound",font , true);
        btnMusic = new Buttons(SCR_WIDTH*2/10, SCR_HEIGHT*6/10, "Music",font , true);
        btnEditName = new Buttons(SCR_WIDTH*2/10, SCR_HEIGHT*5/10, "Name",font , true);
        btnBack = new Buttons(SCR_WIDTH*45/100, SCR_HEIGHT*1/10, "Back",font , true);
        btnSoundMin =  new Buttons(SCR_WIDTH*35/100, SCR_HEIGHT*66/100, font.getLineHeight());
        btnSoundPlus =  new Buttons(SCR_WIDTH*45/100, SCR_HEIGHT*66/100, font.getLineHeight());

    }

    @Override
    public void show() {
        isMusicPlaying = false;
    }

    @Override
    public void render(float delta) {
        if(project1.isMusicOn & !isMusicPlaying){
            BackMusic.play(project1.masterVolume/200);
            isMusicPlaying = true;
        }
        btnControl.textureX = btnControl.x * 93 / 100;
        btnControl.textureY = btnControl.y * 94 / 100;
        if (btnControl.click(touch.x, touch.y)) {
            project1.ButtonClickSound.play(project1.masterVolume / 100);
            project1.setScreen(project1.screenKeys);
        }

        if (Gdx.input.justTouched()) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);


            if (btnSound.click(touch.x, touch.y)) {
                project1.ButtonClickSound.play(project1.masterVolume / 100);

            }

            if (btnMusic.click(touch.x, touch.y)) {
                project1.isMusicOn = !project1.isMusicOn;
                project1.ButtonClickSound.play(project1.masterVolume / 100);
            }

            if (btnEditName.click(touch.x, touch.y)) {
                StickyTanks.name = MathUtils.random(100000, 999999);
                project1.ButtonClickSound.play(project1.masterVolume / 100);
            }
            if (btnBack.click(touch.x, touch.y)) {
                project1.setScreen(project1.screenMenu);
                project1.ButtonClickSound.play(project1.masterVolume / 100);
            }
        }
        if (Gdx.input.isTouched()) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);
            if (btnSoundPlus.click(touch.x, touch.y)) {
                if (project1.masterVolume < 100) {
                    project1.masterVolume += 1;
                }
            }
            if (btnSoundMin.click(touch.x, touch.y)) {
                if (project1.masterVolume > 0) {
                    project1.masterVolume -= 1;
                }
            }
        }

        btnSound.textureX = btnSound.x * 91 / 100;
        btnSound.textureY = btnSound.y * 94 / 100;

        btnMusic.textureX = btnMusic.x * 91 / 100;
        btnMusic.textureY = btnMusic.y * 93 / 100;

        btnEditName.textureX = btnEditName.x * 91 / 100;
        btnEditName.textureY = btnEditName.y * 92 / 100;

        btnBack.textureX = btnBack.x * 96 / 100;
        btnBack.textureY = btnBack.y * 60 / 100;


        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(Background, 0, 0, SCR_WIDTH, SCR_HEIGHT);

        batch.draw(ButtonBackImg, btnBack.textureX, btnBack.textureY, btnBack.textureWidth, btnBack.textureHeight);
        btnBack.font.draw(batch, btnBack.text, btnBack.x, btnBack.y + SCR_HEIGHT / 50);

        batch.draw(ButtonBackImg, btnSound.textureX, btnSound.textureY, btnSound.textureWidth, btnSound.textureHeight);
        btnSound.font.draw(batch, btnSound.text, btnSound.x, btnSound.y + SCR_HEIGHT / 50);

        batch.draw(ButtonBackImg, btnMusic.textureX, btnMusic.textureY, btnMusic.textureWidth, btnMusic.textureHeight);
        btnMusic.font.draw(batch, btnMusic.text+"  "+project1.isMusicOn, btnMusic.x, btnMusic.y + SCR_HEIGHT / 50);

        batch.draw(ButtonBackImg, btnEditName.textureX, btnEditName.textureY, btnEditName.textureWidth, btnEditName.textureHeight);
        btnEditName.font.draw(batch, btnEditName.text, btnEditName.x, btnEditName.y + SCR_HEIGHT / 50);
        font.draw(batch, StickyTanks.name+"", btnEditName.x * 15 / 10, btnEditName.y + SCR_HEIGHT / 50);

        batch.draw(LeftArrow, btnSoundMin.x, btnSoundMin.y, btnSoundMin.width, btnSoundMin.height);
        batch.draw(RightArrow, btnSoundPlus.x, btnSoundPlus.y, btnSoundPlus.width, btnSoundPlus.height);
        font.draw(batch, "" + (int) project1.masterVolume, SCR_WIDTH * 39 / 100, SCR_HEIGHT * 70 / 100 + SCR_HEIGHT / 50);
        batch.draw(ButtonBackImg, btnControl.textureX, btnControl.textureY, btnControl.textureWidth, btnControl.textureHeight);
        btnControl.font.draw(batch, btnControl.text, btnControl.x, btnControl.y + SCR_HEIGHT / 50);

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





