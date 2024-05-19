package com.mygdx.game;


import static com.mygdx.game.StickyTanks.SCR_HEIGHT;
import static com.mygdx.game.StickyTanks.SCR_WIDTH;
import static com.mygdx.game.StickyTanks.WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class ScreenKeys implements Screen {
    StickyTanks project1;
    SpriteBatch batch;
    OrthographicCamera camera;
    Vector3 touch;
    BitmapFont font;

    Texture Background, ButtonBackImg, JoyStickImg;

    Buttons btnBack;
    Buttons btnChangeDriveKeys, btnChangeGadgetType;

    String upKey = "w", rightKey = "a", leftKey = "d", downKey = "s";
    String upArrow = "upArr", rightArrow = "rightArr", leftArrow = "leftArr", downArrow = "downArr";

    Boolean isMusicPlaying = false;
    Sound BackMusic;

    public ScreenKeys(StickyTanks project1) {
        this.project1 = project1;

        batch = project1.batch;
        camera = project1.camera;
        touch = project1.touch;
        font = project1.fontForLevels;

        ButtonBackImg = new Texture("ButtonBack.png");
        Background = new Texture("SettingsBack.png");
        JoyStickImg = new Texture("Joystick.png");

        project1.ButtonClickSound = Gdx.audio.newSound(Gdx.files.internal("ButtonClickSound.mp3"));

        btnBack = new Buttons(SCR_WIDTH*45/100, SCR_HEIGHT*1/10, "Back",font , true);

        BackMusic = Gdx.audio.newSound(Gdx.files.internal("MusicForHangar.mp3"));

        btnChangeDriveKeys = new Buttons(SCR_WIDTH*1/10, SCR_HEIGHT*7/10,"Change drive keys" , font, true);
        btnChangeGadgetType = new Buttons(SCR_WIDTH*6/10,SCR_HEIGHT*7/10, "Change device type", font, true);
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
        if (Gdx.input.justTouched()) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);
            if (btnBack.click(touch.x, touch.y)) {
                project1.ButtonClickSound.play(project1.masterVolume/100);
                project1.setScreen(project1.screenSettings);
            }
            if (btnChangeDriveKeys.click(touch.x, touch.y)) {
                project1.isArrows = !project1.isArrows;
                project1.ButtonClickSound.play(project1.masterVolume / 100);
            }
            if (btnChangeGadgetType.click(touch.x, touch.y)) {
                project1.isPhone = !project1.isPhone;
                project1.ButtonClickSound.play(project1.masterVolume / 100);
            }
        }
        btnChangeDriveKeys.textureX = btnChangeDriveKeys.x * 93 / 100;
        btnChangeDriveKeys.textureY = btnChangeDriveKeys.y * 94 / 100;
        btnChangeGadgetType.textureX = btnChangeGadgetType.x*96/100;
        btnChangeGadgetType.textureY = btnChangeGadgetType.y*94/100;
        btnBack.textureX = btnBack.x*96/100;
        btnBack.textureY = btnBack.y*60/100;
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(Background, 0,0,SCR_WIDTH,SCR_HEIGHT);
        batch.draw(ButtonBackImg, btnBack.textureX, btnBack.textureY, btnBack.textureWidth, btnBack.textureHeight);
        btnBack.font.draw(batch,btnBack.text, btnBack.x, btnBack.y+SCR_HEIGHT/50);

        batch.draw(ButtonBackImg, btnChangeGadgetType.textureX, btnChangeGadgetType.textureY, btnChangeGadgetType.textureWidth * 75 / 100, btnChangeGadgetType.textureHeight);
        btnChangeGadgetType.font.draw(batch, btnChangeGadgetType.text, btnChangeGadgetType.x, btnChangeGadgetType.y+SCR_HEIGHT/50);

        if (!project1.isPhone) {
            batch.draw(ButtonBackImg, btnChangeDriveKeys.textureX, btnChangeDriveKeys.textureY, btnChangeDriveKeys.textureWidth * 75 / 100, btnChangeDriveKeys.textureHeight);
            btnChangeDriveKeys.font.draw(batch, btnChangeDriveKeys.text, btnChangeDriveKeys.x, btnChangeDriveKeys.y+SCR_HEIGHT/50);
            if (!project1.isArrows) {
                font.draw(batch, "Move up " + upKey, btnChangeDriveKeys.x, SCR_HEIGHT * 6 / 10);
                font.draw(batch, "Move down " + downKey, btnChangeDriveKeys.x, SCR_HEIGHT * 5 / 10);
                font.draw(batch, "Move right " + rightKey, btnChangeDriveKeys.x, SCR_HEIGHT * 4 / 10);
                font.draw(batch, "Move left " + leftKey, btnChangeDriveKeys.x, SCR_HEIGHT * 3 / 10);
                font.draw(batch, "Rocket move up " + upArrow, btnChangeDriveKeys.x*5, SCR_HEIGHT * 6 / 10);
                font.draw(batch, "Rocket move down " + downArrow, btnChangeDriveKeys.x*5, SCR_HEIGHT * 5 / 10);
                font.draw(batch, "Rocket move right " + rightArrow, btnChangeDriveKeys.x*5, SCR_HEIGHT * 4 / 10);
                font.draw(batch, "Rocket move left " + leftArrow, btnChangeDriveKeys.x*5, SCR_HEIGHT * 3 / 10);
                font.draw(batch, "Interaction mouseleft", btnChangeDriveKeys.x, SCR_HEIGHT * 2 / 10);
            } else {
                font.draw(batch, "Move up " + upArrow, btnChangeDriveKeys.x, SCR_HEIGHT * 6 / 10);
                font.draw(batch, "Move down " + downArrow, btnChangeDriveKeys.x, SCR_HEIGHT * 5 / 10);
                font.draw(batch, "Move right " + rightArrow, btnChangeDriveKeys.x, SCR_HEIGHT * 4 / 10);
                font.draw(batch, "Move left " + leftArrow, btnChangeDriveKeys.x, SCR_HEIGHT * 3 / 10);
                font.draw(batch, "Rocket move up " + upKey, btnChangeDriveKeys.x*5, SCR_HEIGHT * 6 / 10);
                font.draw(batch, "Rocket move down " + downKey, btnChangeDriveKeys.x*5, SCR_HEIGHT * 5 / 10);
                font.draw(batch, "Rocket move right " + rightKey, btnChangeDriveKeys.x*5, SCR_HEIGHT * 4 / 10);
                font.draw(batch, "Rocket move left " + leftKey, btnChangeDriveKeys.x*5, SCR_HEIGHT * 3 / 10);
                font.draw(batch, "Interaction mouseleft", btnChangeDriveKeys.x, SCR_HEIGHT * 2 / 10);
            }
        } else{
            font.draw(batch, "Move up leftJSup", btnChangeDriveKeys.x-WIDTH*2, SCR_HEIGHT * 6 / 10);
            font.draw(batch, "Move down leftJSdown", btnChangeDriveKeys.x-WIDTH*2, SCR_HEIGHT * 5 / 10);
            font.draw(batch, "Move right leftJSright", btnChangeDriveKeys.x-WIDTH*2, SCR_HEIGHT * 4 / 10);
            font.draw(batch, "Move left leftJSleft", btnChangeDriveKeys.x-WIDTH*2, SCR_HEIGHT * 3 / 10);
            font.draw(batch, "Rocket move up rightJSup", btnChangeDriveKeys.x*5-WIDTH*3/2, SCR_HEIGHT * 6 / 10);
            font.draw(batch, "Rocket move down rightJSdown", btnChangeDriveKeys.x*5-WIDTH*3/2, SCR_HEIGHT * 5 / 10);
            font.draw(batch, "Rocket move right rightJSright", btnChangeDriveKeys.x*5-WIDTH*3/2, SCR_HEIGHT * 4 / 10);
            font.draw(batch, "Rocket move left rightJSleft", btnChangeDriveKeys.x*5-WIDTH*3/2, SCR_HEIGHT * 3 / 10);
            font.draw(batch, "Interaction touch", btnChangeDriveKeys.x-WIDTH*2, SCR_HEIGHT * 2 / 10);
        }

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
    public void dispose() {

    }
}
