package com.mygdx.game;

import static com.badlogic.gdx.Input.Keys.ESCAPE;
import static com.mygdx.game.StickyTanks.HEIGHT;
import static com.mygdx.game.StickyTanks.SCR_HEIGHT;
import static com.mygdx.game.StickyTanks.SCR_WIDTH;
import static com.mygdx.game.StickyTanks.WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class ScreenHangar implements Screen {
    StickyTanks project1;
    SpriteBatch batch;
    OrthographicCamera camera;
    Vector3 touch;
    BitmapFont font,textFont;

    Buttons btnChooseRight, btnChooseLeft, btnBack, btnBuy, btnChoose;

    Sound ButtonClickSound,ButtonChooseSound;

    Texture BackGroundImg, ButtonBackImg, LeftArrow, RightArrow, TankAngarFrameImg, TankUpYImg;

    float summonX, summonY;
    int coins = 0;
    int rocketPrice = 150, bombPrice = 150;

    Boolean isMusicPlaying = false;
    Sound BackMusic;

    public ScreenHangar(StickyTanks project1) {
        this.project1 = project1;

        batch = project1.batch;
        camera = project1.camera;
        touch = project1.touch;
        font = project1.fontForLevels;
        textFont = project1.fontForTexts;

        ButtonClickSound = Gdx.audio.newSound(Gdx.files.internal("ButtonClickSound.mp3"));
        ButtonChooseSound = Gdx.audio.newSound(Gdx.files.internal("ChooseSound.mp3"));
        btnChooseLeft = new Buttons((SCR_WIDTH / 3) - SCR_WIDTH / 15 - SCR_WIDTH/5, SCR_HEIGHT*0.53F, SCR_HEIGHT / 10);
        btnChooseRight = new Buttons((SCR_WIDTH / 3) + (SCR_WIDTH / 3) + SCR_WIDTH / 15 - SCR_WIDTH*0.32F, SCR_HEIGHT*0.53F, SCR_HEIGHT / 10);

        btnBuy = new Buttons(SCR_WIDTH / 3 - SCR_WIDTH*0.095F, SCR_HEIGHT / 3-SCR_HEIGHT/12, "Buy",font,true);
        btnChoose = new Buttons(SCR_WIDTH / 3 - SCR_WIDTH*0.12F, SCR_HEIGHT / 3-SCR_HEIGHT/12, "Choose",font,true);
        btnBack = new Buttons(SCR_WIDTH*85/100,HEIGHT*2,"Back",font,true);
        BackGroundImg = new Texture("AngarBack.png");
        ButtonBackImg = new Texture("ButtonBack.png");
        LeftArrow = new Texture("LeftArrow.png");
        RightArrow = new Texture("RightArrow.png");
        TankAngarFrameImg = new Texture("TankAngarFrame.png");

        btnBuy.textureX = btnBuy.x*945/1000 - SCR_WIDTH/28.5F;
        btnBuy.textureY = btnBuy.y*83/100-SCR_HEIGHT/50;
        btnBuy.textureWidth+=SCR_WIDTH/13;

        BackMusic = Gdx.audio.newSound(Gdx.files.internal("MusicForHangar.mp3"));
    }

    @Override
    public void show() {
        isMusicPlaying = false;
        loadPlayerData();
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
            if(btnBuy.click(touch.x, touch.y)){
                if((project1.ShowingTankType == 2 & !project1.isRocketTankBought) | (project1.ShowingTankType == 3 & !project1.isBombTankBought)) {
                    ButtonClickSound.play(project1.masterVolume / 100);
                }
                    if(project1.ShowingTankType == 2 & !project1.isRocketTankBought){
                        if(coins >= rocketPrice){
                            coins = coins - rocketPrice;
                            project1.isRocketTankBought = true;
                        }
                    }
                if(project1.ShowingTankType == 3 & !project1.isBombTankBought){
                    if(coins >= bombPrice){
                        coins = coins - bombPrice;
                        project1.isBombTankBought = true;
                    }
                }
            }
            if(project1.ShowingTankType == 1){
                if(btnChoose.click(touch.x,touch.y)) {
                    ButtonChooseSound.play(project1.masterVolume / 100);
                    project1.TankType = 1;
                }
            }
            if(project1.ShowingTankType == 2 & project1.isRocketTankBought){
                if(btnChoose.click(touch.x,touch.y)) {
                    ButtonChooseSound.play(project1.masterVolume / 100);
                    project1.TankType = 2;
                }
            }
            if(project1.ShowingTankType == 3 & project1.isBombTankBought){
                if(btnChoose.click(touch.x,touch.y)) {
                    ButtonChooseSound.play(project1.masterVolume / 100);
                    project1.TankType = 3;
                }
            }

            if (btnChooseLeft.click(touch.x, touch.y)) {
                ButtonClickSound.play(project1.masterVolume / 100);
                project1.ShowingTankType -= 1;
            }
            if (btnChooseRight.click(touch.x, touch.y)) {
                ButtonClickSound.play(project1.masterVolume / 100);
                project1.ShowingTankType += 1;
            }
        }
        if (Gdx.input.isKeyPressed(ESCAPE) | btnBack.click(touch.x, touch.y)) {
            project1.ButtonClickSound.play(project1.masterVolume/100);
            project1.setScreen(project1.screenMenu);
        }
        if (project1.ShowingTankType == 0) {
            project1.ShowingTankType = 3;
        }
        if (project1.ShowingTankType == 4) {
            project1.ShowingTankType = 1;
        }
        System.out.println(project1.TankType);////////


        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(BackGroundImg, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        font.draw(batch, "Choose Tank Type", SCR_WIDTH / 3 - SCR_WIDTH*0.215F, SCR_HEIGHT * 9 / 10);
        font.draw(batch, "Sticky Coins "+coins, SCR_WIDTH - 9*WIDTH, SCR_HEIGHT * 96 / 100);
        batch.draw(TankAngarFrameImg, SCR_WIDTH / 3 - SCR_WIDTH/5, SCR_HEIGHT / 3, SCR_WIDTH *0.27F, SCR_WIDTH * 0.27F);

        btnBack.textureX = btnBack.x * 98/100;
        btnBack.textureY = btnBack.y * 50/100;
        batch.draw(ButtonBackImg,btnBack.textureX,btnBack.textureY, btnBack.textureWidth,btnBack.textureHeight);
        font.draw(batch,btnBack.text, btnBack.x,btnBack.y);

        batch.draw(LeftArrow, btnChooseLeft.x, btnChooseLeft.y, btnChooseLeft.width, btnChooseLeft.height);
        batch.draw(RightArrow, btnChooseRight.x, btnChooseRight.y, btnChooseRight.width, btnChooseRight.height);
            batch.draw(ButtonBackImg, btnBuy.textureX, btnBuy.textureY, btnBuy.textureWidth, btnBuy.textureHeight);
            if (project1.ShowingTankType == 2 & !project1.isRocketTankBought) {
                btnBuy.font.draw(batch, btnBuy.text, btnBuy.x, btnBuy.y);
            }
            if (project1.ShowingTankType == 3 & !project1.isBombTankBought) {
                btnBuy.font.draw(batch, btnBuy.text, btnBuy.x, btnBuy.y);
            }
            if (project1.ShowingTankType == 2 & project1.isRocketTankBought) {
                btnChoose.font.draw(batch, btnChoose.text, btnChoose.x, btnBuy.y);
            }
            if (project1.ShowingTankType == 3 & project1.isBombTankBought) {
                btnChoose.font.draw(batch, btnChoose.text, btnChoose.x, btnBuy.y);
            }
            if (project1.ShowingTankType == 1) {
                btnChoose.font.draw(batch, btnChoose.text, btnChoose.x, btnBuy.y);
            }
        if (project1.ShowingTankType == 1) {
            TankUpYImg = new Texture("TankUpY.png");
            font.draw(batch, "Jambo", SCR_WIDTH / 3 - SCR_WIDTH*0.115F, SCR_HEIGHT / 3-SCR_HEIGHT/100);
            textFont.draw(batch, "Multi-functional tank with triocalibre gun.", SCR_WIDTH*0.45F, SCR_HEIGHT *0.75f);
            textFont.draw(batch, "Has an average hp reserve, no specialty.", SCR_WIDTH*0.45F, SCR_HEIGHT *0.7f);
            textFont.draw(batch, "Pluses:", SCR_WIDTH*0.45F, SCR_HEIGHT *0.65f);
            textFont.draw(batch, " -Universal", SCR_WIDTH*0.45F, SCR_HEIGHT *0.6f);
            textFont.draw(batch, "Minuses:", SCR_WIDTH*0.45F, SCR_HEIGHT *0.55f);
            textFont.draw(batch, " -Weak ammunition", SCR_WIDTH*0.45F, SCR_HEIGHT *0.5f);
        }
        if (project1.ShowingTankType == 2) {

            TankUpYImg = new Texture("RocketTankUpY.png");
            font.draw(batch, "Hydra", SCR_WIDTH / 3 - SCR_WIDTH*0.115F, SCR_HEIGHT / 3-SCR_HEIGHT/100);
            textFont.draw(batch, "Sniper tank, who has guided missiles. Has", SCR_WIDTH*0.45F, SCR_HEIGHT *0.75f);
            textFont.draw(batch, "low hp reserve and high fire-rate. ", SCR_WIDTH*0.45F, SCR_HEIGHT *0.7f);
            textFont.draw(batch, "Pluses:", SCR_WIDTH*0.45F, SCR_HEIGHT *0.65f);
            textFont.draw(batch, " -Guided missiles", SCR_WIDTH*0.45F, SCR_HEIGHT *0.6f);
            textFont.draw(batch, " -High damage", SCR_WIDTH*0.45F, SCR_HEIGHT *0.55f);
            textFont.draw(batch, " -Fast projectiles", SCR_WIDTH*0.45F, SCR_HEIGHT *0.5f);
            textFont.draw(batch, "Minuses:", SCR_WIDTH*0.45F, SCR_HEIGHT *0.45f);
            textFont.draw(batch, " -Low hp", SCR_WIDTH*0.45F, SCR_HEIGHT *0.4f);
            textFont.draw(batch, " -High fire-rate", SCR_WIDTH*0.45F, SCR_HEIGHT *0.35f);
            textFont.draw(batch, "Price: 150 SC", SCR_WIDTH*0.45F, SCR_HEIGHT *0.28f);
        }
        if (project1.ShowingTankType == 3) {

            TankUpYImg = new Texture("BombTankUpY.png");
            font.draw(batch, "Brummbar", SCR_WIDTH / 3 - SCR_WIDTH*0.147F, SCR_HEIGHT / 3-SCR_HEIGHT/100);
            textFont.draw(batch, "Big tank with lots of hp. Designed to int-", SCR_WIDTH*0.45F, SCR_HEIGHT *0.75f);
            textFont.draw(batch, "roduce a defensive battle, by planting bombs.  ", SCR_WIDTH*0.45F, SCR_HEIGHT *0.7f);
            textFont.draw(batch, "Pluses:", SCR_WIDTH*0.45F, SCR_HEIGHT *0.65f);
            textFont.draw(batch, " -High hp", SCR_WIDTH*0.45F, SCR_HEIGHT *0.6f);
            textFont.draw(batch, " -Easy defense strategy", SCR_WIDTH*0.45F, SCR_HEIGHT *0.55f);
            textFont.draw(batch, "Minuses:", SCR_WIDTH*0.45F, SCR_HEIGHT *0.5f);
            textFont.draw(batch, " -Can't engage in long-range combat", SCR_WIDTH*0.45F, SCR_HEIGHT *0.45f);
            textFont.draw(batch, " -Difficult to cause damage", SCR_WIDTH*0.45F, SCR_HEIGHT *0.4f);
            textFont.draw(batch, "Price: 150 SC", SCR_WIDTH*0.45F, SCR_HEIGHT *0.33f);
        }
        summonX = (SCR_WIDTH / 3) + (SCR_WIDTH / 20) - SCR_WIDTH/5 - SCR_WIDTH/510 + SCR_WIDTH/210;
        summonY = (SCR_HEIGHT / 3) + (SCR_HEIGHT / 11);
        batch.draw(TankUpYImg, summonX, summonY, SCR_WIDTH / 6, SCR_WIDTH / 6);
        summonX = (SCR_WIDTH / 3) + (SCR_WIDTH / 20) + 10000;
        summonY = (SCR_HEIGHT / 3) + (SCR_HEIGHT / 20) + 10000;



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
        savePlayerData();
        BackMusic.pause();
    }

    @Override
    public void dispose() {

    }
    private void savePlayerData() {
        Preferences playerData = Gdx.app.getPreferences("playerData");
        playerData.putBoolean("RocketTank", project1.isRocketTankBought);
        playerData.putBoolean("BombTank", project1.isBombTankBought);
        playerData.flush();
    }
    private void loadPlayerData(){
        Preferences playerData = Gdx.app.getPreferences("playerData");
        if(playerData.contains("Coins")){
             coins = playerData.getInteger("Coins");
        }
        if(playerData.contains("RocketTank")){
            project1.isRocketTankBought = playerData.getBoolean("RocketTank");
        }
        if(playerData.contains("BombTank")){
            project1.isBombTankBought = playerData.getBoolean("BombTank");
        }
    }
}
