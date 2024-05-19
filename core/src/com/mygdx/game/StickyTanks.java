package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class StickyTanks extends Game {
    public static final float SCR_WIDTH = 1280, SCR_HEIGHT = 720;
    public static final int LAB_WIDTH = 32, LAB_HEIGHT = 18;
    public static final float WIDTH = SCR_WIDTH / LAB_WIDTH, HEIGHT = SCR_HEIGHT / LAB_HEIGHT;
    public OrthographicCamera camera;
    public Vector3 touch;
    public SpriteBatch batch;
    public BitmapFont fontForLevels, fontForTexts, fontForMonitor, fontforMenu;
    ScreenSettings screenSettings;
    ScreenMenu screenMenu;
    ScreenGame screenGame;
    ScreenHangar screenHangar;
    ScreenKeys screenKeys;
    public float masterVolume = 100;
    boolean isArrows = false,isPhone = false,isRocketTankBought = false,isBombTankBought = false, isMusicOn = true;
    public int TankType = 1;
    public int ShowingTankType = 1;
    static int name = MathUtils.random(100000, 999999);
    Sound ButtonClickSound;

    @Override
    public void create() {


        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
        touch = new Vector3();
        generateFont();
        screenGame = new ScreenGame(this);
        screenSettings = new ScreenSettings(this);
        screenMenu = new ScreenMenu(this);
        screenHangar = new ScreenHangar(this);
        screenKeys = new ScreenKeys(this);
        setScreen(screenMenu);

    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    void generateFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Project1FontForTexts.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 45;
        parameter.color = Color.valueOf("5F9EA0");
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 1;
        parameter.characters = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
        fontForLevels = generator.generateFont(parameter);

        FreeTypeFontGenerator generator2 = new FreeTypeFontGenerator(Gdx.files.internal("Project1FontForTexts.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter2.size = 35;
        parameter2.color = Color.BLACK;
        parameter2.borderColor = Color.GRAY;
        parameter2.borderWidth = 2;
        parameter2.characters = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
        fontForTexts = generator2.generateFont(parameter2);

        FreeTypeFontGenerator generator3 = new FreeTypeFontGenerator(Gdx.files.internal("Project1FontForTexts.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter3 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter3.size = 30;
        parameter3.color = Color.valueOf("008000");
        parameter3.borderColor = Color.BLACK;
        parameter3.borderWidth = 4;
        parameter3.characters = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
        fontForMonitor = generator3.generateFont(parameter3);

        FreeTypeFontGenerator generator4 = new FreeTypeFontGenerator(Gdx.files.internal("Project1FontForTexts.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter4 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter4.size = 80;
        parameter4.color = Color.valueOf("8B0000");
        parameter4.borderColor = Color.BLACK;
        parameter4.borderWidth = 1;
        parameter4.shadowOffsetX = 5;
        parameter4.shadowColor = Color.valueOf("006400");
        parameter4.characters = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
        fontforMenu = generator4.generateFont(parameter4);
    }
}






