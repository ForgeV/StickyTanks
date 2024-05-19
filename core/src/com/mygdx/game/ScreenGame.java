package com.mygdx.game;

import static com.badlogic.gdx.Input.Keys.A;
import static com.badlogic.gdx.Input.Keys.D;
import static com.badlogic.gdx.Input.Keys.DOWN;
import static com.badlogic.gdx.Input.Keys.ESCAPE;
import static com.badlogic.gdx.Input.Keys.G;
import static com.badlogic.gdx.Input.Keys.LEFT;
import static com.badlogic.gdx.Input.Keys.RIGHT;
import static com.badlogic.gdx.Input.Keys.S;
import static com.badlogic.gdx.Input.Keys.UP;
import static com.badlogic.gdx.Input.Keys.W;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScreenGame implements Screen {
    ExecutorService executorService = Executors.newFixedThreadPool(4);
    StickyTanks StickyTanks;

    SpriteBatch batch;
    OrthographicCamera camera;
    Vector3 touch;
    BitmapFont font;


    Texture BackgroundImg, BrickWallImg, PlayerUpYImg, PlayerDownYImg, PlayerLeftXImg, PlayerRightXImg, SteelWallImg, EnemyUpYImg, EnemyDownYImg, EnemyLeftXImg, EnemyRightXImg;
    Texture EnemyBasicAmmoUpYImg, EnemyBasicAmmoDownYImg, EnemyBasicAmmoRightXImg, EnemyBasicAmmoLeftXImg, EnemyBigAmmoUpYImg, EnemyBigAmmoRightXImg, EnemyBigAmmoLeftXImg, EnemyBigAmmoDownYImg, EnemyMachineGunAmmoUpYImg, EnemyMachineGunAmmoRightXImg, EnemyMachineGunAmmoLeftXImg, EnemyMachineGunAmmoDownYImg;
    Texture Explosion1Img, Explosion2Img, Explosion3Img, BigExplosionImg;
    Texture DurabilityBackImg, ItemFrameImg, SettinsImg, TargetedAmmoImg, ButtonBackImg, LeftArrowImg, RightArrowImg, TankAngarFrameImg, SettingsBackImg, MashineGunAmmoPackImg;
    Texture MachineGunAmmoUpYImg, MachineGunAmmoRightXImg, MachineGunAmmoLeftXImg, MachineGunAmmoDownYImg, BigAmmoUpYImg, BigAmmoRightXImg, BigAmmoLeftXImg, BigAmmoDownYImg, BasicProjectileUpYImg, BasicProjectileDownYImg, BasicProjectileLeftXImg, BasicProjectileRightXImg, BasicRocketUpYImg, BasicRocketDownYImg, BasicRocketRightXImg, BasicRocketLeftXImg;
    Texture DestroyedEnemyTankUpYImg, DestroyedEnemyTankDownYImg, DestroyedEnemyTankRightXImg, DestroyedEnemyTankLeftXImg, DestroyedTankUpYImg, DestroyedTankDownYImg, DestroyedTankRightXImg, DestroyedTankLeftXImg;
    Texture BasicBombImg, ClockBombImg, HedgehogImg, NapalmPoolImg, ShockWaveImg, ShockWaveImg2, ShockWaveImg3, ShieldImg, ShieldImg2, ShieldImg3;
    Texture BigRocketUpYImg, BigRocketDownYImg, BigRocketRightXImg, BigRocketLeftXImg, NapalmRocketUpYImg, NapalmRocketDownYImg, NapalmRocketRightXImg, NapalmRocketLeftXImg;
    Texture BossUpYImg, BossDownYImg, BossRightXImg, BossLeftXImg;
    Texture Network1lvlImg, Network0lvlImg, Network2lvlImg, Network3lvlImg, NetworkCounter010Img, NetworkCounter1020Img, NetworkCounter2040Img, NetworkCounter4060Img, NetworkCounter6080Img, NetworkCounter80100Img;
    Texture OrbitalStrikeExplotionImg, OrbitalStrikeMonitorImg, OrbitalStrikeMonitorNonActiveImg, OrbitalStrikeRayImg, BombXImg,SaferImg,BombYImg;
    Texture JoyStickImg;
    Sound ButtonClickSound, ExplosionSound, MashineGunExplosionSound, ShootAmmoSound, ShootMashineGunAmmoSound, TankExplosionSound;
    Sound BasicBombSound, ClockBombSound, HedgehogSound, NapalmExplotionSound, NapalmPoolBurningSound, RocketExplotionSound, RocketShootSound;
    Sound OrbitalStrikeReadySound, OrbitalStrikeShootSound;

    Sound MusicForStages, MusicForBoss,MusicForLose,MusicForWin;

    public static final float SCR_WIDTH = 1280, SCR_HEIGHT = 720;
    public static final int LAB_WIDTH = 32, LAB_HEIGHT = 18;

    public static final float WIDTH = SCR_WIDTH / LAB_WIDTH, HEIGHT = SCR_HEIGHT / LAB_HEIGHT;
    public int[][] BuildMassive = new int[LAB_WIDTH][LAB_HEIGHT + 1];

    Walls[] walls = new Walls[(LAB_HEIGHT * LAB_WIDTH) + 1000];
    BorderWalls[] borderwalls = new BorderWalls[LAB_HEIGHT * 2 + LAB_WIDTH * 2];

    Tank tank;
    Projectile[] basicAmmo;
    Projectile[] enemyBasicAmmo;
    Projectile[] machinegunAmmo;
    Projectile[] bigAmmo;
    Projectile[] enemyMachinegunAmmo;
    Projectile[] enemyBigAmmo;

    Projectile[] basicRocket;
    Projectile[] bigRocket;
    Projectile[] napalmRocket;
    Projectile[] napalmPool;

    Projectile[] basicBomb;
    Projectile[] hedgehog;
    Projectile[] clockBomb;

    Projectile orbitalStrike = new Projectile(SCR_WIDTH * 100, SCR_HEIGHT * 100, 10, WIDTH / 2, WIDTH / 2, 0, 0, false, true, false);
    Projectile shockWave = new Projectile(SCR_WIDTH * 100, SCR_HEIGHT * 100, 0, 1, 1, 0, 0, false, false, false);

    Walls bossShield = new Walls(SCR_WIDTH * 100, SCR_HEIGHT * 100, (SCR_WIDTH / 72) * 1.5f * 1.4F, (SCR_HEIGHT / 40.5F) * 1.5f * 1.4F, -1);
    Projectile shieldBash = new Projectile(SCR_WIDTH * 100, SCR_HEIGHT * 100, (int) 0.5, (SCR_WIDTH / 72) * 1.4F, (SCR_HEIGHT / 40.5F) * 1.4F, 0, 0, false, false, false);
    Enemy[] enemies = new Enemy[10];
    Enemy[] bombs = new Enemy[200];
    Enemy[] safers = new Enemy[200];
    Buttons btnNextStage, btnYouLose, btnYouWin, btnMainMenu, btnRetry, btnResume, btnSettings, btnSound, btnMusic, btnTargetAmmo1, btnTargetAmmo2, btnTargetAmmo3, btnOrbitalStrikeActivation;
    Buttons btnMoveUp, btnMoveDown, btnMoveRight, btnMoveLeft, btnRocketUp, btnRocketDown, btnRocketRight, btnRocketLeft;
    double PlayerHeight = SCR_HEIGHT / 40.5, PlayerWidth = SCR_WIDTH / 72;
    int wallsCounter = 0, borderwallsCounter = 0, hedgehogWallsCounter = LAB_WIDTH * LAB_HEIGHT + 1, sideCounter = 0, basicAmmoCounter = 0, bigAmmoCounter = 0, mashinegunAmmoCounter = 0, basicRocketCounter = 0, bigRocketCounter = 0, napalmRocketCounter = 0, lastSideCounter = 0, clickChecker = 0, basicBombCounter = 0, clockBombCounter = 0, hedgehogCounter = 0;
    int Tics = 0;
    boolean isGameOver = false, isPlayerWin, onPause = false;
    int isGameOverKey = 0;
    int enemyBlocker;
    int gameStage = 3;
    int visualGameStage = 1;
    int drawingEnemiesCounter = 0;
    float enemyDeadX, enemyDeadY;
    int Tics1 = 0;
    int score = 0;
    int networkSignal = 0, orbitalStrikeCoolDown = 0;
    float networkPointY = MathUtils.random(HEIGHT * 2, SCR_HEIGHT - HEIGHT * 2), networkPointX = MathUtils.random(WIDTH * 2, SCR_WIDTH - WIDTH * 2);
    boolean isTankShock = false, isOrbitalStrikeReady = false, maySpawnDown = false, maySpawnUp = false, maySpawnLeft = false, maySpawnRight = false;
    int upKey, downKey, leftKey, rightKey, upRocketKey, downRocketKey, leftRocketKey, rightRocketKey;
    int bombsCounter = 0, safersCounter = 0;
    boolean isMusicForStagesOn = false, isMusicForBossOn = false,isMusicForLoseOn = false,isMusicForWinOn = false;
    public ScreenGame(StickyTanks project1) {
        this.StickyTanks = project1;


        batch = project1.batch;
        camera = project1.camera;
        touch = project1.touch;
        font = project1.fontForLevels;

        BackgroundImg = new Texture("Background1.png");
        BrickWallImg = new Texture("BrickWall.png");
        SteelWallImg = new Texture("SteelWall1.png");

        //PlayerUpYImg = new Texture("TankUpY.png");
        // PlayerDownYImg = new Texture("TankDownY.png");
        //PlayerLeftXImg = new Texture("TankLeftX.png");
        //PlayerRightXImg = new Texture("TankRightX.png");
        JoyStickImg = new Texture("Joystick.png");

        EnemyUpYImg = new Texture("EnemyUpY.png");
        EnemyDownYImg = new Texture("EnemyDownY.png");
        EnemyRightXImg = new Texture("EnemyRightX.png");
        EnemyLeftXImg = new Texture("EnemyLeftX.png");

        DestroyedTankUpYImg = new Texture("DestroyedTankUpY.png");
        DestroyedTankDownYImg = new Texture("DestroyedTankDownY.png");
        DestroyedTankRightXImg = new Texture("DestroyedTankRightX.png");
        DestroyedTankLeftXImg = new Texture("DestroyedTankLeftX.png");

        DestroyedEnemyTankUpYImg = new Texture("DestroyedEnemyTankUpY.png");
        DestroyedEnemyTankDownYImg = new Texture("DestroyedEnemyTankDownY.png");
        DestroyedEnemyTankRightXImg = new Texture("DestroyedEnemyTankRightX.png");
        DestroyedEnemyTankLeftXImg = new Texture("DestroyedEnemyTankLeftX.png");

        BasicProjectileUpYImg = new Texture("BasicProjectileUpY.png");
        BasicProjectileDownYImg = new Texture("BasicProjectileDownY.png");
        BasicProjectileLeftXImg = new Texture("BasicProjectileLeftX.png");
        BasicProjectileRightXImg = new Texture("BasicProjectileRightX.png");
        MachineGunAmmoUpYImg = new Texture("MachineGunAmmoUpY.png");
        MachineGunAmmoRightXImg = new Texture("MachineGunAmmoRightX.png");
        MachineGunAmmoLeftXImg = new Texture("MachineGunAmmoLeftX.png");
        MachineGunAmmoDownYImg = new Texture("MachineGunAmmoDownY.png");
        BigAmmoUpYImg = new Texture("BigAmmoUpY.png");
        BigAmmoRightXImg = new Texture("BigAmmoRightX.png");
        BigAmmoLeftXImg = new Texture("BigAmmoLeftX.png");
        BigAmmoDownYImg = new Texture("BigAmmoDownY.png");

        BasicRocketUpYImg = new Texture("BasicRocketUpY.png");
        BasicRocketDownYImg = new Texture("BasicRocketDownY.png");
        BasicRocketRightXImg = new Texture("BasicRocketRightX.png");
        BasicRocketLeftXImg = new Texture("BasicRocketLeftX.png");
        BigRocketUpYImg = new Texture("BigRocketUpY.png");
        BigRocketDownYImg = new Texture("BigRocketDownY.png");
        BigRocketLeftXImg = new Texture("BigRocketLeftX.png");
        BigRocketRightXImg = new Texture("BigRocketRightX.png");
        NapalmRocketLeftXImg = new Texture("NapalmRocketLeftX.png");
        NapalmRocketRightXImg = new Texture("NapalmRocketRightX.png");
        NapalmRocketUpYImg = new Texture("NapalmRocketUpY.png");
        NapalmRocketDownYImg = new Texture("NapalmRocketDownY.png");

        BasicBombImg = new Texture("BasicBomb.png");
        ClockBombImg = new Texture("ClockBomb.png");
        HedgehogImg = new Texture("Hedgehog2.png");

        EnemyBasicAmmoUpYImg = new Texture("EnemyBasicAmmoUpY.png");
        EnemyBasicAmmoDownYImg = new Texture("EnemyBasicAmmoDownY.png");
        EnemyBasicAmmoLeftXImg = new Texture("EnemyBasicAmmoLeftX.png");
        EnemyBasicAmmoRightXImg = new Texture("EnemyBasicAmmoRightX.png");
        EnemyMachineGunAmmoUpYImg = new Texture("EnemyMachineGunAmmoUpY.png");
        EnemyMachineGunAmmoRightXImg = new Texture("EnemyMachineGunAmmoRightX.png");
        EnemyMachineGunAmmoLeftXImg = new Texture("EnemyMachineGunAmmoLeftX.png");
        EnemyMachineGunAmmoDownYImg = new Texture("EnemyMachineGunAmmoDownY.png");
        EnemyBigAmmoUpYImg = new Texture("EnemyBigAmmoUpY.png");
        EnemyBigAmmoRightXImg = new Texture("EnemyBigAmmoRightX.png");
        EnemyBigAmmoLeftXImg = new Texture("EnemyBigAmmoLeftX.png");
        EnemyBigAmmoDownYImg = new Texture("EnemyBigAmmoDownY.png");

        Explosion1Img = new Texture("Explosion1.png");
        Explosion2Img = new Texture("Explosion2.png");
        Explosion3Img = new Texture("Explosion3.png");
        BigExplosionImg = new Texture("BigExplosion.png");
        NapalmPoolImg = new Texture("NapalmPool.png");

        DurabilityBackImg = new Texture("DurabilityBack.png");
        ItemFrameImg = new Texture("ItemFrame.png");
        SettinsImg = new Texture("Settings.png");
        TargetedAmmoImg = new Texture("TargetedAmmo.png");
        ButtonBackImg = new Texture("ButtonBack.png");
        LeftArrowImg = new Texture("LeftArrow.png");
        RightArrowImg = new Texture("RightArrow.png");
        TankAngarFrameImg = new Texture("TankAngarFrame.png");
        SettingsBackImg = new Texture("SettingsBack.png");
        MashineGunAmmoPackImg = new Texture("MachineGunAmmoPack.png");

        Network0lvlImg = new Texture("Network0lvl.png");
        Network1lvlImg = new Texture("Network1lvl.png");
        Network2lvlImg = new Texture("Network2lvl.png");
        Network3lvlImg = new Texture("Network3lvl.png");
        NetworkCounter010Img = new Texture("NetworkCounter0-10.png");
        NetworkCounter1020Img = new Texture("NetworkCounter10-20.png");
        NetworkCounter2040Img = new Texture("NetworkCounter20-40.png");
        NetworkCounter4060Img = new Texture("NetworkCounter40-60.png");
        NetworkCounter6080Img = new Texture("NetworkCounter60-80.png");
        NetworkCounter80100Img = new Texture("NetworkCounter80-100.png");
        OrbitalStrikeMonitorImg = new Texture("OrbitalStrikeMonitor.png");
        OrbitalStrikeMonitorNonActiveImg = new Texture("OrbitalStrikeMonitorNonActive.png");

        OrbitalStrikeExplotionImg = new Texture("OrbitalStrikeExplotion.png");
        OrbitalStrikeRayImg = new Texture("OrbitalStrikeRay.png");

        BossUpYImg = new Texture("BossUpYImg.png");
        BossDownYImg = new Texture("BossDownYImg.png");
        BossRightXImg = new Texture("BossRightXImg.png");
        BossLeftXImg = new Texture("BossLeftXImg.png");
        ShockWaveImg = new Texture("ShockWave.png");
        ShockWaveImg2 = new Texture("ShockWave2.png");
        ShockWaveImg3 = new Texture("ShockWave3.png");
        ShieldImg = new Texture("Shield.png");
        ShieldImg2 = new Texture("Shield2.png");
        ShieldImg3 = new Texture("Shield3.png");
        BombXImg = new Texture("BombX.png");
        BombYImg = new Texture("BombY.png");
        SaferImg = new Texture("Safer.png");

        btnNextStage = new Buttons(SCR_WIDTH + 100, SCR_HEIGHT + 150, "Next Level", font, true);
        btnYouLose = new Buttons(SCR_WIDTH + 100, SCR_HEIGHT + 150, "You lose", font, true);
        btnYouWin = new Buttons(SCR_WIDTH + 100, SCR_HEIGHT + 150, "You win", font, true);
        btnRetry = new Buttons(SCR_WIDTH + 100, SCR_HEIGHT + 100, "Retry", font, true);
        btnMainMenu = new Buttons(SCR_WIDTH + 100, SCR_HEIGHT + 50, "Main Menu", font, true);
        btnMusic = new Buttons(SCR_WIDTH + 100, SCR_HEIGHT + 50, WIDTH);
        btnSound = new Buttons(SCR_WIDTH + 100, SCR_HEIGHT + 50, WIDTH);
        btnSettings = new Buttons(SCR_WIDTH - WIDTH, SCR_HEIGHT - HEIGHT, WIDTH);
        btnResume = new Buttons(SCR_WIDTH + 100, SCR_HEIGHT + 50, "Resume", font, true);
        btnTargetAmmo1 = new Buttons(2 * WIDTH * 3, 0, WIDTH * 2, HEIGHT * 2);
        btnTargetAmmo2 = new Buttons(2 * WIDTH * 4, 0, WIDTH * 2, HEIGHT * 2);
        btnTargetAmmo3 = new Buttons(2 * WIDTH * 5, 0, WIDTH * 2, HEIGHT * 2);
        btnOrbitalStrikeActivation = new Buttons(SCR_WIDTH * 101 - 2 * WIDTH, HEIGHT * 8, WIDTH * 2, HEIGHT * 2);


        ButtonClickSound = Gdx.audio.newSound(Gdx.files.internal("ButtonClickSound.mp3"));
        ExplosionSound = Gdx.audio.newSound(Gdx.files.internal("ExplotionSound.mp3"));
        MashineGunExplosionSound = Gdx.audio.newSound(Gdx.files.internal("MashineGunExplosionSound.mp3"));
        ShootAmmoSound = Gdx.audio.newSound(Gdx.files.internal("ShootAmmoSound.mp3"));
        ShootMashineGunAmmoSound = Gdx.audio.newSound(Gdx.files.internal("ShootMashineGunAmmoSound.mp3"));
        TankExplosionSound = Gdx.audio.newSound(Gdx.files.internal("TankExplosionSound.mp3"));
        BasicBombSound = Gdx.audio.newSound(Gdx.files.internal("BasicBombSound.mp3"));
        ClockBombSound = Gdx.audio.newSound(Gdx.files.internal("ClockBombSound.mp3"));
        HedgehogSound = Gdx.audio.newSound(Gdx.files.internal("HedgehogSound.mp3"));
        NapalmExplotionSound = Gdx.audio.newSound(Gdx.files.internal("NapalmExplotionSound.mp3"));
        NapalmPoolBurningSound = Gdx.audio.newSound(Gdx.files.internal("NapalmPoolBurningSound.mp3"));
        RocketExplotionSound = Gdx.audio.newSound(Gdx.files.internal("RocketExplotionSound.mp3"));
        RocketShootSound = Gdx.audio.newSound(Gdx.files.internal("RocketShootSound.mp3"));

        OrbitalStrikeReadySound = Gdx.audio.newSound(Gdx.files.internal("OrbitalStrikeReadySound.mp3"));
        OrbitalStrikeShootSound = Gdx.audio.newSound(Gdx.files.internal("OrbitalStrikeShootSound.mp3"));

       // MusicForStages = Gdx.audio.newSound(Gdx.files.internal("MusicForGame.mp3"));
        MusicForStages = Gdx.audio.newSound(Gdx.files.internal("ButtonClickSound.mp3"));
        MusicForBoss = Gdx.audio.newSound(Gdx.files.internal("MusicForBoss2.mp3"));
        MusicForLose = Gdx.audio.newSound(Gdx.files.internal("MusicForLose.mp3"));
        MusicForWin = Gdx.audio.newSound(Gdx.files.internal("MusicForWin.mp3"));

        if (project1.TankType == 1) {
            tank = new Tank(LAB_WIDTH / 2 - 1, 3, (float) PlayerWidth, (float) PlayerHeight, 100);
        }
        if (project1.TankType == 2) {
            tank = new Tank(LAB_WIDTH / 2 - 1, 3, (float) PlayerWidth, (float) PlayerHeight, 75);
        }
        if (project1.TankType == 3) {
            tank = new Tank(LAB_WIDTH / 2 - 1, 3, (float) PlayerWidth, (float) PlayerHeight, 150);
        }
        tank.realX = (LAB_WIDTH / 2 - 1) * WIDTH + tank.width / 2;
        tank.realY = 3 * HEIGHT + tank.height / 2;

        basicAmmo = new Projectile[1000];
        enemyBasicAmmo = new Projectile[1500];
        machinegunAmmo = new Projectile[2000];
        bigAmmo = new Projectile[1000];
        enemyMachinegunAmmo = new Projectile[6000];
        enemyBigAmmo = new Projectile[1500];
        basicRocket = new Projectile[1000];
        bigRocket = new Projectile[1000];
        napalmRocket = new Projectile[1000];
        napalmPool = new Projectile[1000];
        basicBomb = new Projectile[1000];
        clockBomb = new Projectile[1000];
        hedgehog = new Projectile[1000];
        shockWave.antiEnemyDamage = 1;
        shieldBash.antiEnemyDamage = 3;

        enemyBlocker = gameStage * 2;
        for (int i = 0; i < enemies.length - enemyBlocker; i++) {
            enemies[i] = new Enemy(MathUtils.random(2, LAB_WIDTH - 3), 16, (float) PlayerWidth - (float) 0.01, (float) PlayerHeight - (float) 0.01, 4, MathUtils.random(674, 5763), (enemyBasicAmmo.length / (enemies.length * 2)) * i, 50, (enemyMachinegunAmmo.length / (enemies.length * 2)) * i, (enemyBigAmmo.length / (enemies.length * 2)) * i, 1);
            enemies[i].realY -= enemies[i].height;
            enemies[i].realX += enemies[i].width;
        }

        for (int i = 0; i < bombs.length; i++) {
            bombs[i] = new Enemy((int) SCR_WIDTH * 100, (int) SCR_HEIGHT * 100, WIDTH / 3, HEIGHT / 3, 1, MathUtils.random(674, 5763), 0, 20, 0, 0, 7);
        }
        for (int i = 0; i < safers.length; i++) {
            safers[i] = new Enemy((int) SCR_WIDTH * 100, (int) SCR_HEIGHT * 100, WIDTH / 3, HEIGHT / 3, 0, MathUtils.random(674, 5763), 0, 20, 0, 0, 7);
        }

        for (int i = 0; i < LAB_WIDTH; i++) {
            for (int j = 0; j < LAB_HEIGHT; j++) {
                BuildMassive[i][j] = MathUtils.random(0, 1);
                BuildMassive[0][j] = 2;
                BuildMassive[LAB_WIDTH - 1][j] = 2;

            }
            BuildMassive[i][2] = 2;
            BuildMassive[i][LAB_HEIGHT - 1] = 2;
            BuildMassive[i][0] = 3;
            BuildMassive[i][1] = 3;
            BuildMassive[LAB_WIDTH - 1][0] = 2;
            BuildMassive[LAB_WIDTH - 1][1] = 2;
        }
        BuildMassive[12][0] = 2;
        BuildMassive[12][1] = 2;

        for (int k = 0; k < enemies.length - enemyBlocker; k++) {
            if (BuildMassive[enemies[k].x][16] == 1) {
                BuildMassive[enemies[k].x][16] = 0;
            }
        }
        for (int i = 0; i < LAB_WIDTH; i++) {
            for (int j = 0; j < LAB_HEIGHT; j++) {

                if (BuildMassive[i][j] == 1) {
                    walls[wallsCounter] = new Walls(WIDTH * i, HEIGHT * j, WIDTH, HEIGHT, 20);
                    wallsCounter++;

                }

                if (BuildMassive[i][j] == 2) {
                    borderwalls[borderwallsCounter] = new BorderWalls(WIDTH * i, HEIGHT * j, WIDTH, HEIGHT);
                    borderwallsCounter++;
                }
            }
        }
        for (int i = 0; i < wallsCounter; i++) {
            if (walls[i].y <= tank.realY & tank.realY <= walls[i].y + walls[i].height & walls[i].x <= tank.realX & tank.realX <= walls[i].x + walls[i].width) {
                walls[i].y = SCR_HEIGHT + 100;
                walls[i].x = SCR_WIDTH + 100;
            }
        }

        for (int i = 0; i < basicAmmo.length; i++) {
            basicAmmo[i] = new Projectile(SCR_WIDTH + 1000, SCR_HEIGHT + 1000, 2, LAB_WIDTH / 4, LAB_HEIGHT / 2, 3, 0, false, true, false);
            // basicAmmo[i].hit = true;
            basicAmmo[i].antiEnemyDamage = MathUtils.random(1, 10);
        }
        for (int i = 0; i < enemyBasicAmmo.length; i++) {
            enemyBasicAmmo[i] = new Projectile(SCR_WIDTH + 1000, SCR_HEIGHT + 1000, 2, LAB_WIDTH / 4, LAB_HEIGHT / 2, 3, 0, false, false, false);
            enemyBasicAmmo[i].antiEnemyDamage = MathUtils.random(1, 10);
        }

        for (int i = 0; i < machinegunAmmo.length; i++) {
            machinegunAmmo[i] = new Projectile(SCR_WIDTH + 1000, SCR_HEIGHT + 1000, 1, LAB_WIDTH / 5, LAB_HEIGHT / 2, 3, 0, false, true, false);
            machinegunAmmo[i].antiEnemyDamage = MathUtils.random(1, 3);
        }
        for (int i = 0; i < enemyMachinegunAmmo.length; i++) {
            enemyMachinegunAmmo[i] = new Projectile(SCR_WIDTH + 1000, SCR_HEIGHT + 1000, 1, LAB_WIDTH / 5, LAB_HEIGHT / 2, 3, 0, false, false, false);
            enemyMachinegunAmmo[i].antiEnemyDamage = MathUtils.random(1, 3);
        }

        for (int i = 0; i < bigAmmo.length; i++) {
            bigAmmo[i] = new Projectile(SCR_WIDTH + 1000, SCR_HEIGHT + 1000, 5, LAB_WIDTH / 3, LAB_HEIGHT / 2, 3, 0, false, true, false);
            bigAmmo[i].antiEnemyDamage = MathUtils.random(10, 16);
        }
        for (int i = 0; i < enemyBigAmmo.length; i++) {
            enemyBigAmmo[i] = new Projectile(SCR_WIDTH + 1000, SCR_HEIGHT + 1000, 4, LAB_WIDTH / 3, LAB_HEIGHT / 2, 3, 0, false, false, false);
            enemyBigAmmo[i].antiEnemyDamage = MathUtils.random(10, 15);
        }
        for (int i = 0; i < basicRocket.length; i++) {
            basicRocket[i] = new Projectile(SCR_WIDTH + 1000, SCR_HEIGHT + 1000, 4, LAB_WIDTH / 4, LAB_HEIGHT * 0.6F, 3, 0, false, true, false);
            basicRocket[i].antiEnemyDamage = MathUtils.random(1, 7);
            bigRocket[i] = new Projectile(SCR_WIDTH + 1000, SCR_HEIGHT + 1000, 7, LAB_WIDTH / 3, LAB_HEIGHT * 0.6F, 4, 0, false, true, false);
            bigRocket[i].antiEnemyDamage = MathUtils.random(8, 13);
            napalmRocket[i] = new Projectile(SCR_WIDTH + 1000, SCR_HEIGHT + 1000, 1, LAB_WIDTH / 4, LAB_HEIGHT * 0.6F, 3, 0, false, true, false);
            napalmRocket[i].antiEnemyDamage = MathUtils.random(0, 0);
            napalmPool[i] = new Projectile(SCR_WIDTH + 1000, SCR_HEIGHT + 1000, 0, LAB_WIDTH * 2, LAB_WIDTH * 2, 0, 0, false, true, true);
            napalmPool[i].antiEnemyDamage = 0.1f;
        }
        for (int i = 0; i < basicBomb.length; i++) {
            basicBomb[i] = new Projectile(SCR_WIDTH + 1000, SCR_HEIGHT + 1000, 5, LAB_WIDTH / 4, LAB_WIDTH / 4, 0, 0, false, true, false);
            basicBomb[i].antiEnemyDamage = MathUtils.random(7, 15);
            clockBomb[i] = new Projectile(SCR_WIDTH + 1000, SCR_HEIGHT + 1000, 5, LAB_WIDTH * 2F, LAB_WIDTH * 2F, 0, 0, false, true, false);
            clockBomb[i].antiEnemyDamage = MathUtils.random(10, 18);
            clockBomb[i].livTime = 120;
            hedgehog[i] = new Projectile(SCR_WIDTH + 1000, SCR_HEIGHT + 1000, 0, LAB_WIDTH / 3, LAB_WIDTH / 3, 0, 0, false, true, false);
            hedgehog[i].antiEnemyDamage = 1;
            hedgehog[i].livTime = 120;
        }


        loadPlayerData();
    }

    @Override
    public void show() {
        isMusicForStagesOn = false;
        isMusicForBossOn = false;
        isMusicForLoseOn = false;
        isMusicForWinOn = false;
        //  System.out.println(StickyTanks.masterVolume);
        // if (StickyTanks.isBombTankBought & StickyTanks.isRocketTankBought) {
        loadPlayerData();
        if (StickyTanks.TankType == 2) {
            PlayerUpYImg = new Texture("RocketTankUpY.png");
            PlayerDownYImg = new Texture("RocketTankDownY.png");
            PlayerLeftXImg = new Texture("RocketTankLeftX.png");
            PlayerRightXImg = new Texture("RocketTankRightX.png");
            tank.hitpoints = 75;
        }
        if (StickyTanks.TankType == 3) {
            PlayerUpYImg = new Texture("BombTankUpY.png");
            PlayerDownYImg = new Texture("BombTankDownY.png");
            PlayerLeftXImg = new Texture("BombTankLeftX.png");
            PlayerRightXImg = new Texture("BombTankRightX.png");
            tank.hitpoints = 150;
        }
        if (StickyTanks.TankType == 1) {
            PlayerUpYImg = new Texture("TankUpY.png");
            PlayerDownYImg = new Texture("TankDownY.png");
            PlayerLeftXImg = new Texture("TankLeftX.png");
            PlayerRightXImg = new Texture("TankRightX.png");
            tank.hitpoints = 100;
        }
        //  }
        btnMoveLeft = new Buttons(WIDTH * 11223, HEIGHT * 7 / 2 - HEIGHT / 4, WIDTH * 3 / 2);                                                //  batch.draw(JoyStickImg, WIDTH , HEIGHT , WIDTH * 6, HEIGHT * 6);
        btnMoveRight = new Buttons(WIDTH * 11223, HEIGHT * 7 / 2 - HEIGHT / 4, WIDTH * 3 / 2);
        btnMoveUp = new Buttons(WIDTH * 11223, HEIGHT * 5, WIDTH * 3 / 2);
        btnMoveDown = new Buttons(WIDTH * 11223, HEIGHT * 3 / 2, WIDTH * 3 / 2);
        btnRocketLeft = new Buttons(WIDTH * 112234, HEIGHT * 7 / 2 - HEIGHT / 4, WIDTH * 3 / 2);                                                //  batch.draw(JoyStickImg, WIDTH , HEIGHT , WIDTH * 6, HEIGHT * 6);
        btnRocketRight = new Buttons(WIDTH * 11223, HEIGHT * 7 / 2 - HEIGHT / 4, WIDTH * 3 / 2);
        btnRocketUp = new Buttons(WIDTH * 11223, HEIGHT * 5, WIDTH * 3 / 2);
        btnRocketDown = new Buttons(WIDTH * 11223, HEIGHT * 3 / 2, WIDTH * 3 / 2);
        if (StickyTanks.isPhone) {
            btnMoveLeft = new Buttons(WIDTH * 3 / 2 + WIDTH / 4, HEIGHT * 7 / 2 - HEIGHT / 4, WIDTH * 3 / 2);                                                //  batch.draw(JoyStickImg, WIDTH , HEIGHT , WIDTH * 6, HEIGHT * 6);
            btnMoveRight = new Buttons(WIDTH * 9 / 2 + WIDTH / 4, HEIGHT * 7 / 2 - HEIGHT / 4, WIDTH * 3 / 2);
            btnMoveUp = new Buttons(WIDTH * 3 + WIDTH / 4, HEIGHT * 5, WIDTH * 3 / 2);
            btnMoveDown = new Buttons(WIDTH * 3 + WIDTH / 4, HEIGHT * 3 / 2, WIDTH * 3 / 2);    // batch.draw(JoyStickImg, WIDTH * 25, HEIGHT , WIDTH * 6, HEIGHT * 6);
            if (StickyTanks.TankType == 2) {
                btnRocketLeft = new Buttons(WIDTH * 3 / 2 + WIDTH / 4 + WIDTH * 24, HEIGHT * 7 / 2 - HEIGHT / 4, WIDTH * 3 / 2);                                                //  batch.draw(JoyStickImg, WIDTH , HEIGHT , WIDTH * 6, HEIGHT * 6);
                btnRocketRight = new Buttons(WIDTH * 9 / 2 + WIDTH / 4 + WIDTH * 24, HEIGHT * 7 / 2 - HEIGHT / 4, WIDTH * 3 / 2);
                btnRocketUp = new Buttons(WIDTH * 3 + WIDTH / 4 + WIDTH * 24, HEIGHT * 5, WIDTH * 3 / 2);
                btnRocketDown = new Buttons(WIDTH * 3 + WIDTH / 4 + WIDTH * 24, HEIGHT * 3 / 2, WIDTH * 3 / 2);
            }
        }
    }

    @Override
    public void render(float delta) {
        if (!onPause & !isGameOver) {
            Tics++;
            if(!isMusicForStagesOn & visualGameStage != 4 & StickyTanks.isMusicOn){
                MusicForStages.stop();
                MusicForBoss.stop();
                MusicForLose.stop();
                MusicForWin.stop();
                MusicForStages.play(StickyTanks.masterVolume/100);
                isMusicForStagesOn = true;
            }
            if( !isMusicForBossOn&visualGameStage == 4 & StickyTanks.isMusicOn){
                MusicForStages.stop();
                MusicForBoss.stop();
                MusicForLose.stop();
                MusicForWin.stop();
                MusicForBoss.play(StickyTanks.masterVolume/100);
                isMusicForBossOn = true;
            }
        }
        if(isPlayerWin & !isMusicForWinOn & visualGameStage == 4 & isGameOver & StickyTanks.isMusicOn){
            MusicForStages.stop();
            MusicForBoss.stop();
            MusicForLose.stop();
            MusicForWin.stop();
            MusicForWin.play(StickyTanks.masterVolume/100);
            isMusicForWinOn = true;
        }
        if(isGameOver& !isPlayerWin & !isMusicForLoseOn & StickyTanks.isMusicOn){
            MusicForStages.stop();
            MusicForBoss.stop();
            MusicForLose.stop();
            MusicForWin.stop();
            MusicForLose.play(StickyTanks.masterVolume/100);
            isMusicForLoseOn = true;
        }
        for (int i = 0; i < enemies.length - enemyBlocker; i++) {
            if (visualGameStage != 4) {
                System.out.println(enemies[i].hitpoints);
            }
        }
        //касание
        if (Gdx.input.justTouched()) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);
        }
        if (btnMainMenu.click(touch.x, touch.y)) {
            for (int i = 0; i < basicAmmoCounter; i++) {
                basicAmmo[i].reset();
            }
            for (int i = 0; i < mashinegunAmmoCounter; i++) {
                machinegunAmmo[i].reset();
            }
            for (int i = 0; i < bigAmmoCounter; i++) {
                bigAmmo[i].reset();
            }
            basicAmmoCounter = 0;
            mashinegunAmmoCounter = 0;
            bigAmmoCounter = 0;
            basicRocketCounter = 0;
            napalmRocketCounter = 0;
            bigRocketCounter = 0;
            basicBombCounter = 0;
            clockBombCounter = 0;
            hedgehogCounter = 0;

            for (int i = 0; i < basicRocket.length; i++) {
                basicRocket[i].reset();
                bigRocket[i].reset();
                napalmRocket[i].reset();
                napalmPool[i].reset();
                napalmPool[i].livTime = 300;
            }
            for (int i = 0; i < basicBomb.length; i++) {
                basicBomb[i].reset();
                clockBomb[i].reset();
                hedgehog[i].reset();
            }
            isMusicForStagesOn = false;
            isMusicForBossOn = false;
            isMusicForLoseOn = false;
            isMusicForWinOn = false;
            StickyTanks.setScreen(StickyTanks.screenMenu);
            ButtonClickSound.play(StickyTanks.masterVolume / 100);
            bossShield.durability = -1;
            for (int i = 0; i < bombs.length; i++) {
                bombs[i].realX = SCR_WIDTH*100;
                bombs[i].hitpoints = 20;
            }
            for (int i = 0; i < safers.length; i++) {
                safers[i].realX = SCR_WIDTH*100;
                safers[i].hitpoints = 20;
            }
        }
        if (!isGameOver) {
            if (btnSettings.click(touch.x, touch.y) | Gdx.input.isKeyPressed(ESCAPE)) {
                if (!onPause) {
                    ButtonClickSound.play(StickyTanks.masterVolume / 100);
                }
                onPause = true;
            }
        }
        if (btnResume.click(touch.x, touch.y)) {
            if (onPause) {
                ButtonClickSound.play(StickyTanks.masterVolume / 100);
            }
            onPause = false;
        }

        if (btnRetry.click(touch.x, touch.y) | btnMainMenu.click(touch.x, touch.y)) {
            isMusicForStagesOn = false;
            isMusicForBossOn = false;
            isMusicForLoseOn = false;
            isMusicForWinOn = false;
            savePlayerData();
            for (int i = 0; i < bombs.length; i++) {
                bombs[i].realX = SCR_WIDTH*100;
                bombs[i].hitpoints = 20;
            }
            for (int i = 0; i < safers.length; i++) {
                safers[i].realX = SCR_WIDTH*100;
                safers[i].hitpoints = 20;
            }
            score = 0;
            Tics = 0;
            hedgehogWallsCounter = LAB_WIDTH * LAB_HEIGHT + 1;
            bossShield.x = SCR_WIDTH * 100;
            shockWave.reset();
            shieldBash.reset();
            clickChecker = 1;
            ButtonClickSound.play(StickyTanks.masterVolume / 100);
            for (int i = 0; i < basicAmmoCounter; i++) {
                basicAmmo[i].reset();
            }
            for (int i = 0; i < mashinegunAmmoCounter; i++) {
                machinegunAmmo[i].reset();
            }
            for (int i = 0; i < bigAmmoCounter; i++) {
                bigAmmo[i].reset();
            }
            basicAmmoCounter = 0;
            mashinegunAmmoCounter = 0;
            bigAmmoCounter = 0;
            basicRocketCounter = 0;
            napalmRocketCounter = 0;
            bigRocketCounter = 0;
            basicBombCounter = 0;
            clockBombCounter = 0;
            hedgehogCounter = 0;

            for (int i = 0; i < basicRocket.length; i++) {
                basicRocket[i].reset();
                bigRocket[i].reset();
                napalmRocket[i].reset();
                napalmPool[i].reset();
                napalmPool[i].livTime = 300;
            }
            for (int i = 0; i < basicBomb.length; i++) {
                basicBomb[i].reset();
                clockBomb[i].reset();
                hedgehog[i].reset();
            }
            onPause = false;
            gameStage = 3;
            btnRetry.y = SCR_HEIGHT + 50;
            visualGameStage = 1;
            for (int i = 0; i < enemyBasicAmmo.length; i++) {
                enemyBasicAmmo[i].reset();
            }
            for (int i = 0; i < enemyMachinegunAmmo.length; i++) {
                enemyMachinegunAmmo[i].reset();
            }
            for (int i = 0; i < enemyBigAmmo.length; i++) {
                enemyBigAmmo[i].reset();
            }

            isGameOver = false;
            borderwallsCounter = 0;
            isGameOverKey = 0;
            enemyBlocker = gameStage * 2;
            for (int i = 0; i < enemies.length - enemyBlocker; i++) {
                enemies[i] = new Enemy(MathUtils.random(2, LAB_WIDTH - 3), 16, (float) PlayerWidth - (float) 0.01, (float) PlayerHeight - (float) 0.01, 0, MathUtils.random(674, 5763), (enemyBasicAmmo.length / (enemies.length * 2)) * i, 50, (enemyMachinegunAmmo.length / (enemies.length * 2)) * i, (enemyBigAmmo.length / (enemies.length * 2)) * i, 1);
                enemies[i].realY -= enemies[i].height;
                enemies[i].realX += enemies[i].width;
            }
            for (int i = 0; i < wallsCounter; i++) {
                walls[i].durability = 0;
            }
            wallsCounter = 0;
            for (int i = 0; i < LAB_WIDTH; i++) {
                for (int j = 0; j < LAB_HEIGHT; j++) {
                    BuildMassive[i][j] = MathUtils.random(0, 1);
                    BuildMassive[0][j] = 2;
                    BuildMassive[LAB_WIDTH - 1][j] = 2;
                }
                BuildMassive[i][2] = 2;
                BuildMassive[i][LAB_HEIGHT - 1] = 2;
                BuildMassive[i][0] = 3;
                BuildMassive[i][1] = 3;
                BuildMassive[LAB_WIDTH - 1][0] = 2;
                BuildMassive[LAB_WIDTH - 1][1] = 2;
            }
            BuildMassive[12][0] = 2;
            BuildMassive[12][1] = 2;
            for (int i = 0; i < LAB_WIDTH; i++) {
                for (int j = 0; j < LAB_HEIGHT; j++) {
                    if (BuildMassive[i][j] == 1) {
                        walls[wallsCounter] = new Walls(WIDTH * i, HEIGHT * j, WIDTH, HEIGHT, 20);
                        wallsCounter++;
                    }
                    if (BuildMassive[i][j] == 2) {
                        borderwalls[borderwallsCounter] = new BorderWalls(WIDTH * i, HEIGHT * j, WIDTH, HEIGHT);
                        borderwallsCounter++;

                    }
                }
            }
            if (StickyTanks.TankType == 1) {
                tank.hitpoints = 100;
            }
            if (StickyTanks.TankType == 2) {
                tank.hitpoints = 75;
            }
            if (StickyTanks.TankType == 3) {
                tank.hitpoints = 150;
            }

            tank.realX = (LAB_WIDTH / 2 - 1) * WIDTH + tank.width / 2;
            tank.realY = 3 * HEIGHT + tank.height / 2;
            sideCounter = 3;
            for (int i = 0; i < wallsCounter; i++) {
                if (walls[i].y <= tank.realY & tank.realY <= walls[i].y + walls[i].height & walls[i].x <= tank.realX & tank.realX <= walls[i].x + walls[i].width) {
                    walls[i].y = SCR_HEIGHT + 100;
                    walls[i].x = SCR_WIDTH + 100;
                }
            }
        }

        if (isGameOver & isPlayerWin) {
            if (btnNextStage.click(touch.x, touch.y)) {
                Tics = 0;
                if (btnNextStage.y != SCR_HEIGHT + 100) {
                    ButtonClickSound.play(StickyTanks.masterVolume / 100);
                }
                btnNextStage.y = SCR_HEIGHT + 100;
                gameStage--;
                visualGameStage++;
                clickChecker = 1;
                if (gameStage == -1) {
                    isGameOver = true;

                } else {
                    for (int i = 0; i < basicAmmoCounter; i++) {
                        basicAmmo[i].hit = false;
                    }
                    for (int i = 0; i < mashinegunAmmoCounter; i++) {
                        machinegunAmmo[i].hit = false;
                    }
                    for (int i = 0; i < bigRocketCounter; i++) {
                        bigAmmo[i].hit = false;
                    }
                    for (int i = 0; i < basicRocket.length; i++) {
                        basicRocket[i].hit = false;
                        bigRocket[i].hit = false;
                        napalmRocket[i].hit = false;
                        napalmPool[i].reset();
                        napalmPool[i].livTime = 300;
                                            }
                    for (int i = 0; i < basicBomb.length; i++) {
                        basicBomb[i].reset();
                        clockBomb[i].reset();
                        hedgehog[i].reset();

                    }

                    for (int i = 0; i < enemyBasicAmmo.length; i++) {
                        enemyBasicAmmo[i].reset();
                    }
                    for (int i = 0; i < enemyBigAmmo.length; i++) {
                        enemyBigAmmo[i].reset();
                    }
                    for (int i = 0; i < enemyMachinegunAmmo.length; i++) {
                        enemyMachinegunAmmo[i].reset();
                    }


                    isGameOver = false;
                    borderwallsCounter = 0;
                    isGameOverKey = 0;
                    if (gameStage != 0) {
                        enemyBlocker = gameStage * 2;
                        for (int i = 0; i < enemies.length - enemyBlocker; i++) {
                            enemies[i] = new Enemy(MathUtils.random(2, LAB_WIDTH - 3), 16, (float) PlayerWidth - (float) 0.01, (float) PlayerHeight - (float) 0.01, 0, MathUtils.random(674, 5763), (enemyBasicAmmo.length / (enemies.length * 2)) * i, 50, (enemyMachinegunAmmo.length / (enemies.length * 2)) * i, (enemyBigAmmo.length / (enemies.length * 2)) * i, 1);
                            enemies[i].realY -= enemies[i].height;
                            enemies[i].realX += enemies[i].width;
                        }
                    } else {
                        enemyBlocker = 9;
                        for (int i = 0; i < enemies.length - enemyBlocker; i++) {
                            enemies[i] = new Enemy(MathUtils.random(2, LAB_WIDTH - 3), 16, (float) PlayerWidth * 1.5f, (float) PlayerHeight * 1.5f, 0, MathUtils.random(674, 5763), (enemyBasicAmmo.length / (enemies.length * 2)) * i, 200, (enemyMachinegunAmmo.length / (enemies.length * 2)) * i, (enemyBigAmmo.length / (enemies.length * 2)) * i, 1);
                            enemies[i].stepX = enemies[i].width / 30;
                            enemies[i].stepY = enemies[i].height / 30;
                            enemies[i].realY -= enemies[i].height;
                            enemies[i].realX += enemies[i].width;
                        }
                    }

                    for (int i = 0; i < wallsCounter; i++) {
                        walls[i].durability = 0;
                    }
                    wallsCounter = 0;
                    for (int i = LAB_WIDTH * LAB_HEIGHT + 1; i < hedgehogWallsCounter; i++) {
                        walls[i].durability = 0;
                    }
                    hedgehogWallsCounter = LAB_WIDTH * LAB_HEIGHT + 1;
                    for (int i = 0; i < LAB_WIDTH; i++) {
                        for (int j = 0; j < LAB_HEIGHT; j++) {
                            BuildMassive[i][j] = MathUtils.random(0, 1);
                            BuildMassive[0][j] = 2;
                            BuildMassive[LAB_WIDTH - 1][j] = 2;
                        }
                        BuildMassive[i][2] = 2;
                        BuildMassive[i][LAB_HEIGHT - 1] = 2;
                        BuildMassive[i][0] = 3;
                        BuildMassive[i][1] = 3;
                        BuildMassive[LAB_WIDTH - 1][0] = 2;
                        BuildMassive[LAB_WIDTH - 1][1] = 2;
                    }
                    BuildMassive[12][0] = 2;
                    BuildMassive[12][1] = 2;
                    for (int i = 0; i < LAB_WIDTH; i++) {
                        for (int j = 0; j < LAB_HEIGHT; j++) {
                            if (BuildMassive[i][j] == 1) {
                                walls[wallsCounter] = new Walls(WIDTH * i, HEIGHT * j, WIDTH, HEIGHT, 20);
                                wallsCounter++;
                            }
                            if (BuildMassive[i][j] == 2) {
                                borderwalls[borderwallsCounter] = new BorderWalls(WIDTH * i, HEIGHT * j, WIDTH, HEIGHT);
                                borderwallsCounter++;

                            }
                        }
                    }
                    if (visualGameStage == 2) {
                        enemies[MathUtils.random(0, 2)].ID = 2;
                        enemies[MathUtils.random(3, 5)].ID = 2;
                    } else if (visualGameStage == 3) {
                        enemies[MathUtils.random(0, 1)].ID = 2;
                        enemies[MathUtils.random(2, 3)].ID = 2;
                        enemies[MathUtils.random(4, 5)].ID = 3;
                        enemies[MathUtils.random(6, 7)].ID = 3;
                    } else if (visualGameStage == 4) {
                        for (int i = 0; i < enemies.length - enemyBlocker; i++) {
                            enemies[i].ID = 3;
                            for (int j = 0; j < enemies[i].enemyBigAmmoCounter; j++) {
                                enemyBigAmmo[j].antiEnemyDamage += 5;
                            }
                        }
                    }

                    if (StickyTanks.TankType == 1) {
                        tank.hitpoints = 100;
                    }
                    if (StickyTanks.TankType == 2) {
                        tank.hitpoints = 75;
                    }
                    if (StickyTanks.TankType == 3) {
                        tank.hitpoints = 150;
                    }
                    tank.realX = (LAB_WIDTH / 2 - 1) * WIDTH + tank.width / 2;
                    tank.realY = 3 * HEIGHT + tank.height / 2;
                    sideCounter = 3;
                    for (int i = 0; i < wallsCounter; i++) {
                        if (walls[i].y <= tank.realY & tank.realY <= walls[i].y + walls[i].height & walls[i].x <= tank.realX & tank.realX <= walls[i].x + walls[i].width) {
                            walls[i].y = SCR_HEIGHT + 100;
                            walls[i].x = SCR_WIDTH + 100;
                        }
                    }
                }
            }
        }


        if (!onPause) {
            if (!isGameOver) {
                if (!StickyTanks.isArrows) {
                    upKey = W;
                    downKey = S;
                    rightKey = D;
                    leftKey = A;

                    upRocketKey = UP;
                    downRocketKey = DOWN;
                    leftRocketKey = LEFT;
                    rightRocketKey = RIGHT;
                } else {
                    upKey = UP;
                    downKey = DOWN;
                    rightKey = RIGHT;
                    leftKey = LEFT;

                    upRocketKey = W;
                    downRocketKey = S;
                    leftRocketKey = A;
                    rightRocketKey = D;
                }
                //  if (Gdx.input.isKeyPressed(-1)) {
                if ((Gdx.input.isKeyPressed(leftKey) | btnMoveLeft.click(touch.x, touch.y)) & !OverlapWallsLeftX(tank.realX, tank.realY, tank.height, tank.width, -1) & !OverlapBordersLeftX(tank.realX, tank.realY, tank.height, tank.width) & !DamageDealtToEnemy(tank.realX, tank.realY, tank.height, tank.width, 1, -1)) {
                    tank.moveLeftX();
                    sideCounter = 1;
                    for (int i = bigAmmoCounter; i < bigAmmoCounter + 1; i++) {
                        if (!bigAmmo[i].isShooted) {
                            bigAmmo[i].sideCounter = 1;
                        }
                    }
                } else if ((Gdx.input.isKeyPressed(rightKey) | btnMoveRight.click(touch.x, touch.y)) & !OverlapWallsRightX(tank.realX, tank.realY, tank.height, tank.width, -1) & !OverlapBordersRightX(tank.realX, tank.realY, tank.height, tank.width) & !DamageDealtToEnemy(tank.realX, tank.realY, tank.height, tank.width, 1, -1)) {
                    tank.moveRightX();
                    sideCounter = 2;
                    for (int i = bigAmmoCounter; i < bigAmmoCounter + 1; i++) {
                        if (!bigAmmo[i].isShooted) {
                            bigAmmo[i].sideCounter = 2;
                        }
                    }

                } else if ((Gdx.input.isKeyPressed(upKey) | btnMoveUp.click(touch.x, touch.y)) & !OverlapWallsUpY(tank.realX, tank.realY, tank.height, tank.width, -1) & !OverlapBordersUpY(tank.realX, tank.realY, tank.height, tank.width) & !DamageDealtToEnemy(tank.realX, tank.realY, tank.height, tank.width, 1, -1)) {
                    tank.moveUpY();
                    sideCounter = 3;
                    for (int i = bigAmmoCounter; i < bigAmmoCounter + 1; i++) {
                        if (!bigAmmo[i].isShooted) {
                            bigAmmo[i].sideCounter = 3;
                        }
                    }

                } else if ((Gdx.input.isKeyPressed(downKey) | btnMoveDown.click(touch.x, touch.y)) & !OverlapWallsDownY(tank.realX, tank.realY, tank.height, tank.width, -1) & !OverlapBordersDownY(tank.realX, tank.realY, tank.height, tank.width) & !DamageDealtToEnemy(tank.realX, tank.realY, tank.height, tank.width, 1, -1)) {
                    tank.moveDownY();
                    sideCounter = 4;

                    for (int i = bigAmmoCounter; i < bigAmmoCounter + 1; i++) {
                        if (!bigAmmo[i].isShooted) {
                            bigAmmo[i].sideCounter = 4;
                        }
                    }
                }
                if (Gdx.input.isKeyPressed(upRocketKey) | btnRocketUp.click(touch.x, touch.y)) {
                    for (int i = 0; i < basicRocket.length; i++) {
                        basicRocket[i].sideCounter = 3;
                        bigRocket[i].sideCounter = 3;
                        napalmRocket[i].sideCounter = 3;
                    }
                }
                if (Gdx.input.isKeyPressed(rightRocketKey) | btnRocketRight.click(touch.x, touch.y)) {
                    for (int i = 0; i < basicRocket.length; i++) {
                        basicRocket[i].sideCounter = 2;
                        bigRocket[i].sideCounter = 2;
                        napalmRocket[i].sideCounter = 2;
                    }
                }
                if (Gdx.input.isKeyPressed(leftRocketKey) | btnRocketLeft.click(touch.x, touch.y)) {
                    for (int i = 0; i < basicRocket.length; i++) {
                        basicRocket[i].sideCounter = 1;
                        bigRocket[i].sideCounter = 1;
                        napalmRocket[i].sideCounter = 1;
                    }
                }
                if (Gdx.input.isKeyPressed(downRocketKey) | btnRocketDown.click(touch.x, touch.y)) {
                    for (int i = 0; i < basicRocket.length; i++) {
                        basicRocket[i].sideCounter = 4;
                        bigRocket[i].sideCounter = 4;
                        napalmRocket[i].sideCounter = 4;
                    }
                }

                //  }
            }
        }
        if (!onPause) {
            if (Gdx.input.justTouched()) {
               DamageDealtToEnemy(touch.x, touch.y, HEIGHT, WIDTH, 0, 666);
            }
            //снаряды
            if (StickyTanks.TankType == 1) {
                for (int i = 0; i < basicAmmoCounter; i++) {
                    if (!basicAmmo[i].hit) {
                        BulletFlying(basicAmmo[i], i, basicAmmo[i].whose, 1);
                    }
                }
                if (clickChecker == 0 | clickChecker == 1) {
                    if (Gdx.input.justTouched() & touch.y > HEIGHT * 3 & touch.y < SCR_HEIGHT - HEIGHT & touch.x > WIDTH & touch.x < SCR_WIDTH - WIDTH) {
                        if (tank.reloading == 15) {
                            basicAmmoCounter++;
                            tank.reloading = 0;
                            ShootAmmoSound.play(StickyTanks.masterVolume / 200);
                        }
                    }
                    if (tank.reloading < 15) {
                        tank.reloading++;
                    }
                    if (!isGameOver) {
                        BulletShoot(basicAmmo[basicAmmoCounter], 1, 1, -1);
                        basicAmmo[basicAmmoCounter].isShooted = true;
                    }
                }

                for (int i = 0; i < mashinegunAmmoCounter; i++) {
                    if (!machinegunAmmo[i].hit) {
                        BulletFlying(machinegunAmmo[i], i, machinegunAmmo[i].whose, 2);
                    }
                }

                if (clickChecker == 2) {
                    if (tank.reloading == 10) {
                        if (Gdx.input.isTouched() & touch.y > HEIGHT * 3 & touch.y < SCR_HEIGHT - HEIGHT & touch.x > WIDTH & touch.x < SCR_WIDTH - WIDTH) {
                            mashinegunAmmoCounter++;
                            tank.reloading = 0;
                            ShootMashineGunAmmoSound.play(StickyTanks.masterVolume / 200);
                        }
                    }
                    if (tank.reloading < 10) {
                        tank.reloading++;
                    }
                    if (!isGameOver) {
                        BulletShoot(machinegunAmmo[mashinegunAmmoCounter], 2, 1, -1);
                        machinegunAmmo[mashinegunAmmoCounter].isShooted = true;
                    }
                }

                for (int i = 0; i < bigAmmoCounter; i++) {
                    if (!bigAmmo[i].hit) {
                        BulletFlying(bigAmmo[i], i, bigAmmo[i].whose, 3);
                    }
                }

                if (clickChecker == 3) {
                    if (Gdx.input.justTouched() & touch.y > HEIGHT * 3 & touch.y < SCR_HEIGHT - HEIGHT & touch.x > WIDTH & touch.x < SCR_WIDTH - WIDTH) {
                        if (tank.reloading == 50) {
                            bigAmmoCounter++;
                            tank.reloading = 0;
                            ShootAmmoSound.play(StickyTanks.masterVolume / 200);
                        }
                    }
                    if (tank.reloading < 50) {
                        tank.reloading++;
                    }
                    if (!isGameOver) {
                        BulletShoot(bigAmmo[bigAmmoCounter], 3, 1, -1);
                        bigAmmo[bigAmmoCounter].isShooted = true;
                    }
                }
            }
            //System.out.println(basicAmmoCounter);

//ракеты
            if (StickyTanks.TankType == 2) {
                for (Projectile value : basicAmmo) {
                    value.x = SCR_WIDTH * 10;
                }
                for (Projectile projectile : bigAmmo) {
                    projectile.x = SCR_WIDTH * 10;
                }
                for (Projectile projectile : machinegunAmmo) {
                    projectile.x = SCR_WIDTH * 10;
                }
                for (int i = 0; i < basicRocketCounter; i++) {
                    if (!basicRocket[i].hit) {
                        BulletFlying(basicRocket[i], i, basicRocket[i].whose, 4);

                    }
                }
                if (clickChecker == 0 | clickChecker == 1) {
                    if (Gdx.input.justTouched() & touch.y > HEIGHT * 3 & touch.y < SCR_HEIGHT - HEIGHT & touch.x > WIDTH & touch.x < SCR_WIDTH - WIDTH) {
                        if (tank.reloading == 40) {
                            basicRocketCounter++;
                            tank.reloading = 0;
                            RocketShootSound.play(StickyTanks.masterVolume / 200);//вук ракеты
                        }
                    }
                    if (tank.reloading < 40) {
                        tank.reloading++;
                    }
                    if (!isGameOver) {
                        BulletShoot(basicRocket[basicRocketCounter], 4, 1, -1);
                        basicRocket[basicRocketCounter].isShooted = true;

                    }
                }

                for (int i = 0; i < bigRocketCounter; i++) {
                    if (!bigRocket[i].hit) {
                        BulletFlying(bigRocket[i], i, bigRocket[i].whose, 5);
                    }
                }
                if (clickChecker == 2) {
                    if (Gdx.input.justTouched() & touch.y > HEIGHT * 3 & touch.y < SCR_HEIGHT - HEIGHT & touch.x > WIDTH & touch.x < SCR_WIDTH - WIDTH) {
                        if (tank.reloading == 65) {
                            bigRocketCounter++;
                            tank.reloading = 0;
                            RocketShootSound.play(StickyTanks.masterVolume / 200);//вук ракеты
                        }
                    }
                    if (tank.reloading < 65) {
                        tank.reloading++;
                    }
                    if (!isGameOver) {
                        BulletShoot(bigRocket[bigRocketCounter], 5, 1, -1);
                        bigRocket[bigRocketCounter].isShooted = true;
                    }
                }

                for (int i = 0; i < napalmRocketCounter; i++) {
                    if (!napalmRocket[i].hit) {
                        BulletFlying(napalmRocket[i], i, napalmRocket[i].whose, 6);
                    }
                }
                if (clickChecker == 3) {
                    if (Gdx.input.justTouched() & touch.y > HEIGHT * 3 & touch.y < SCR_HEIGHT - HEIGHT & touch.x > WIDTH & touch.x < SCR_WIDTH - WIDTH) {
                        if (tank.reloading == 70) {
                            napalmRocketCounter++;
                            tank.reloading = 0;
                            RocketShootSound.play(StickyTanks.masterVolume / 200);//вук ракеты
                        }
                    }
                    if (tank.reloading < 70) {
                        tank.reloading++;
                    }
                    if (!isGameOver) {
                        BulletShoot(napalmRocket[napalmRocketCounter], 6, 1, -1);
                        napalmRocket[napalmRocketCounter].isShooted = true;
                    }
                }
            }
//мины
            if (StickyTanks.TankType == 3) {
                for (Projectile value : basicAmmo) {
                    value.x = SCR_WIDTH * 10;
                }
                for (Projectile projectile : bigAmmo) {
                    projectile.x = SCR_WIDTH * 10;
                }
                for (Projectile projectile : machinegunAmmo) {
                    projectile.x = SCR_WIDTH * 10;
                }

                for (int i = 0; i < basicBombCounter; i++) {
                    if (!basicBomb[i].hit) {
                        //DamageDealtToEnemy(basicBomb[i].x, basicBomb[i].y, basicBomb[i].height, basicBomb[i].width,i,8 );
                        BulletFlying(basicBomb[i], i, basicBomb[i].whose, 8);
                    }
                }
                if (clickChecker == 0 | clickChecker == 1) {
                    if (Gdx.input.isKeyPressed(G)) {
                        for (int i = 0; i < basicBombCounter; i++) {
                            basicBomb[i].hit = true;
                        }
                    }
                    if (Gdx.input.justTouched() & touch.y > HEIGHT * 3 & touch.y < SCR_HEIGHT - HEIGHT & touch.x > WIDTH & touch.x < SCR_WIDTH - WIDTH) {
                        if (tank.reloading == 20) {
                            basicBombCounter++;
                            tank.reloading = 0;
                            BasicBombSound.play(StickyTanks.masterVolume / 200);
                        }
                    }
                    if (tank.reloading < 20) {
                        tank.reloading++;
                    }
                    if (!isGameOver) {
                        BulletShoot(basicBomb[basicBombCounter], 8, 1, -1);
                        basicBomb[basicBombCounter].isShooted = true;
                    }
                }

                for (int i = 0; i < clockBombCounter; i++) {
                    if (!clockBomb[i].hit) {
                        if (clockBomb[i].livTime == 0 & clockBomb[i].x != SCR_WIDTH + 1000) {
                            clockBomb[i].hit = true;
                            BulletFlying(clockBomb[i], i, clockBomb[i].whose, 9);
                            //  DamageDealtToEnemy(clockBomb[i].x - LAB_WIDTH*5/2F, clockBomb[i].y - LAB_WIDTH*5/2F, LAB_WIDTH*5,LAB_WIDTH*5,i,9 );
                        } else {
                            clockBomb[i].livTime--;
                        }
                        // BulletFlying(clockBomb[i], i, clockBomb[i].whose, 2);
                    }
                }
                if (clickChecker == 2) {
                    if (tank.reloading == 35) {
                        if (Gdx.input.isTouched() & touch.y > HEIGHT * 3 & touch.y < SCR_HEIGHT - HEIGHT & touch.x > WIDTH & touch.x < SCR_WIDTH - WIDTH) {
                            clockBombCounter++;
                            tank.reloading = 0;
                            ClockBombSound.play(StickyTanks.masterVolume / 200);
                        }
                    }
                    if (tank.reloading < 35) {
                        tank.reloading++;
                    }
                    if (!isGameOver) {
                        BulletShoot(clockBomb[clockBombCounter], 9, 1, -1);
                        clockBomb[clockBombCounter].isShooted = true;
                    }
                }

                for (int i = 0; i < hedgehogCounter; i++) {
                    BulletFlying(hedgehog[i], i, hedgehog[i].whose, 10);
                }

                if (clickChecker == 3) {
                    if (Gdx.input.justTouched() & touch.y > HEIGHT * 3 & touch.y < SCR_HEIGHT - HEIGHT & touch.x > WIDTH & touch.x < SCR_WIDTH - WIDTH) {
                        if (tank.reloading == 50) {
                            hedgehogCounter++;
                            tank.reloading = 0;
                            HedgehogSound.play(StickyTanks.masterVolume / 200);
                        }
                    }
                    if (tank.reloading < 50) {
                        tank.reloading++;
                    }
                    if (!isGameOver) {
                        BulletShoot(hedgehog[hedgehogCounter], 10, 1, -1);
                        if (!hedgehog[hedgehogCounter].isShooted & hedgehogCounter != 0) {
                            walls[hedgehogWallsCounter] = new Walls(hedgehog[hedgehogCounter].x, hedgehog[hedgehogCounter].y, hedgehog[hedgehogCounter].width + 5, hedgehog[hedgehogCounter].height + 5, hedgehog[hedgehogCounter].livTime / 2);
                            hedgehogWallsCounter++;
                        }
                        hedgehog[hedgehogCounter].isShooted = true;
                    }
                }
            }
        }
        if (!onPause) {
            for (int i = 0; i < enemies.length - enemyBlocker; i++) {
                if (!enemies[i].isDestroyed) {
                    if (enemies[i].sideCounter == 1 & !OverlapWallsLeftX(enemies[i].realX, enemies[i].realY, enemies[i].height, enemies[i].width, -1) & !OverlapBordersLeftX(enemies[i].realX, enemies[i].realY, enemies[i].height, enemies[i].width) & !DamageDealtToTank(enemies[i].realX, enemies[i].realY, enemies[i].height, enemies[i].width, 1, -1)) {
                        enemies[i].moveLeftX();
                        for (int j = 0; j < safers.length; j++) {
                            safers[j].moveLeftX();
                        }
                    } else if (enemies[i].sideCounter == 2 & !OverlapWallsRightX(enemies[i].realX, enemies[i].realY, enemies[i].height, enemies[i].width, -1) & !OverlapBordersRightX(enemies[i].realX, enemies[i].realY, enemies[i].height, enemies[i].width) & !DamageDealtToTank(enemies[i].realX, enemies[i].realY, enemies[i].height, enemies[i].width, 1, -1)) {
                        enemies[i].moveRightX();
                        for (int j = 0; j < safers.length; j++) {
                            safers[j].moveRightX();
                        }

                    } else if ((enemies[i].sideCounter == 3 | enemies[i].sideCounter == 0) & !OverlapWallsUpY(enemies[i].realX, enemies[i].realY, enemies[i].height, enemies[i].width, -1) & !OverlapBordersUpY(enemies[i].realX, enemies[i].realY, enemies[i].height, enemies[i].width) & !DamageDealtToTank(enemies[i].realX, enemies[i].realY, enemies[i].height, enemies[i].width, 1, -1)) {
                        enemies[i].moveUpY();
                        for (int j = 0; j < safers.length; j++) {
                            safers[j].moveUpY();
                        }

                    } else if (enemies[i].sideCounter == 4 & !OverlapWallsDownY(enemies[i].realX, enemies[i].realY, enemies[i].height, enemies[i].width, -1) & !OverlapBordersDownY(enemies[i].realX, enemies[i].realY, enemies[i].height, enemies[i].width) & !DamageDealtToTank(enemies[i].realX, enemies[i].realY, enemies[i].height, enemies[i].width, 1, -1)) {
                        enemies[i].moveDownY();
                        for (int j = 0; j < safers.length; j++) {
                            safers[j].moveDownY();
                        }
                    }
                }
            }
            for (int i = 0; i < bombs.length; i++) {
                if (!bombs[i].isDestroyed) {
                    if (DamageDealtToTank(bombs[i].realX, bombs[i].realY, bombs[i].height, bombs[i].width, 1, -1)) {
                        bombs[i].isDestroyed = true;
                        tank.hitpoints -= MathUtils.random(12, 20);
                    }
                    if (bombs[i].sideCounter == 1 & !OverlapWallsLeftX(bombs[i].realX, bombs[i].realY, bombs[i].height, bombs[i].width, -1) & !OverlapBordersLeftX(bombs[i].realX, bombs[i].realY, bombs[i].height, bombs[i].width)) {
                        bombs[i].moveLeftX();

                    } else if (bombs[i].sideCounter == 2 & !OverlapWallsRightX(bombs[i].realX, bombs[i].realY, bombs[i].height, bombs[i].width, -1) & !OverlapBordersRightX(bombs[i].realX, bombs[i].realY, bombs[i].height, bombs[i].width)) {
                        bombs[i].moveRightX();

                    } else if ((bombs[i].sideCounter == 3 | bombs[i].sideCounter == 0) & !OverlapWallsUpY(bombs[i].realX, bombs[i].realY, bombs[i].height, bombs[i].width, -1) & !OverlapBordersUpY(bombs[i].realX, bombs[i].realY, bombs[i].height, bombs[i].width)) {
                        bombs[i].moveUpY();


                    } else if (bombs[i].sideCounter == 4 & !OverlapWallsDownY(bombs[i].realX, bombs[i].realY, bombs[i].height, bombs[i].width, -1) & !OverlapBordersDownY(bombs[i].realX, bombs[i].realY, bombs[i].height, bombs[i].width)) {
                        bombs[i].moveDownY();

                    }
                }

            }
        }
        if (!onPause) {
            for (int i = 0; i < enemyBasicAmmo.length; i++) {
                BulletFlying(enemyBasicAmmo[i], i, enemyBasicAmmo[i].whose, 1);
            }
            for (int i = 0; i < enemyMachinegunAmmo.length; i++) {
                BulletFlying(enemyMachinegunAmmo[i], i, enemyMachinegunAmmo[i].whose, 2);
            }
            for (int i = 0; i < enemyBigAmmo.length; i++) {
                BulletFlying(enemyBigAmmo[i], i, enemyBigAmmo[i].whose, 3);
            }

            for (int i = 0; i < enemies.length - enemyBlocker; i++) {
                if (!enemies[i].isDestroyed) {
                    if (enemies[i].ID == 1) {
                        if (enemies[i].reloading < 20) {
                            enemies[i].reloading++;
                        }
                        if (enemies[i].shootTrigger & enemies[i].reloading == 20) {
                            enemies[i].enemyBasicAmmoCounter++;
                            ShootAmmoSound.play(StickyTanks.masterVolume / 200);
                            enemies[i].shootTrigger = false;
                            enemies[i].reloading = 0;
                        }
                        BulletShoot(enemyBasicAmmo[enemies[i].enemyBasicAmmoCounter], 1, 2, i);
                        enemyBasicAmmo[enemies[i].enemyBasicAmmoCounter].isShooted = true;
                    }
                    if (enemies[i].ID == 2) {
                        if (enemies[i].reloading < 10) {
                            enemies[i].reloading++;
                        }
                        if (enemies[i].shootTrigger & enemies[i].reloading == 10) {
                            enemies[i].enemyMachinegunAmmoCounter++;
                            ShootMashineGunAmmoSound.play(StickyTanks.masterVolume / 200);
                            enemies[i].shootTrigger = false;
                            enemies[i].reloading = 0;
                        }
                        BulletShoot(enemyMachinegunAmmo[enemies[i].enemyMachinegunAmmoCounter], 2, 2, i);
                        enemyMachinegunAmmo[enemies[i].enemyMachinegunAmmoCounter].isShooted = true;
                    }
                    if (enemies[i].ID == 3) {
                        if (visualGameStage == 4) {
                            if (enemies[i].reloading < 30) {
                                enemies[i].reloading++;
                            }
                            if (enemies[i].shootTrigger & enemies[i].reloading == 30) {
                                enemies[i].enemyBigAmmoCounter++;
                                ShootAmmoSound.play(StickyTanks.masterVolume / 200);
                                enemies[i].shootTrigger = false;
                                enemies[i].reloading = 0;
                            }
                            BulletShoot(enemyBigAmmo[enemies[i].enemyBigAmmoCounter], 3, 2, i);
                            enemyBigAmmo[enemies[i].enemyBigAmmoCounter].isShooted = true;
                        } else {
                            if (enemies[i].reloading < 40) {
                                enemies[i].reloading++;
                            }
                            if (enemies[i].shootTrigger & enemies[i].reloading == 40) {
                                enemies[i].enemyBigAmmoCounter++;
                                ShootAmmoSound.play(StickyTanks.masterVolume / 200);
                                enemies[i].shootTrigger = false;
                                enemies[i].reloading = 0;
                            }
                            BulletShoot(enemyBigAmmo[enemies[i].enemyBigAmmoCounter], 3, 2, i);
                            enemyBigAmmo[enemies[i].enemyBigAmmoCounter].isShooted = true;
                        }
                    }
                }
            }
        }
        //управление ботами
        if (!onPause) {
            for (int i = 0; i < enemies.length - enemyBlocker; i++) {
                if (!enemies[i].isDestroyed) {
                    enemies[i].tics++;
                    if (enemies[i].tics % 40 == 0 & visualGameStage != 4) {
                        enemies[i].sideCounter = MathUtils.random(1, 4);
                        if (tank.realX < enemies[i].realX & tank.realY < enemies[i].realY) {// у альф не делать других вариантов движения
                            if (MathUtils.random(1, 4) == 1) {
                                enemies[i].sideCounter = 1;
                            } else if (MathUtils.random(1, 4) == 2) {
                                enemies[i].sideCounter = 4;
                            }
                        }
                        if (tank.realX < enemies[i].realX & tank.realY > enemies[i].realY) {
                            if (MathUtils.random(1, 4) == 1) {
                                enemies[i].sideCounter = 1;
                            } else if (MathUtils.random(1, 4) == 2) {
                                enemies[i].sideCounter = 3;
                            }
                        }
                        if (tank.realX > enemies[i].realX & tank.realY < enemies[i].realY) {
                            if (MathUtils.random(1, 4) == 1) {
                                enemies[i].sideCounter = 2;
                            } else if (MathUtils.random(1, 4) == 2) {
                                enemies[i].sideCounter = 4;
                            }
                        }
                        if (tank.realX > enemies[i].realX & tank.realY > enemies[i].realY) {
                            if (MathUtils.random(1, 4) == 1) {
                                enemies[i].sideCounter = 2;
                            } else if (MathUtils.random(1, 4) == 2) {
                                enemies[i].sideCounter = 3;
                            }
                        }

                    } else if (enemies[i].tics % 40 == 0 & visualGameStage == 4) {

                        if (tank.realX < enemies[i].realX & tank.realY < enemies[i].realY) {
                            if (MathUtils.random(1, 2) == 1) {
                                enemies[i].sideCounter = 1;
                            } else {
                                enemies[i].sideCounter = 4;
                            }
                        }
                        if (tank.realX < enemies[i].realX & tank.realY > enemies[i].realY) {
                            if (MathUtils.random(1, 2) == 1) {
                                enemies[i].sideCounter = 1;
                            } else {
                                enemies[i].sideCounter = 3;
                            }
                        }
                        if (tank.realX > enemies[i].realX & tank.realY < enemies[i].realY) {
                            if (MathUtils.random(1, 2) == 1) {
                                enemies[i].sideCounter = 2;
                            } else {
                                enemies[i].sideCounter = 4;
                            }
                        }
                        if (tank.realX > enemies[i].realX & tank.realY > enemies[i].realY) {
                            if (MathUtils.random(1, 2) == 1) {
                                enemies[i].sideCounter = 2;
                            } else {
                                enemies[i].sideCounter = 3;
                            }
                        }
                    }
                    for (int j = 0; j < bombs.length; j++) {
                        bombs[j].tics++;
                        if (bombs[j].tics % 20 == 0) {
                            bombs[j].sideCounter = MathUtils.random(1, 4);
                            if (tank.realX < bombs[j].realX & tank.realY < bombs[j].realY) {
                                if (MathUtils.random(1, 2) == 1) {
                                    bombs[j].sideCounter = 1;
                                } else {
                                    bombs[j].sideCounter = 4;
                                }
                            }
                            if (tank.realX < bombs[j].realX & tank.realY > bombs[j].realY) {
                                if (MathUtils.random(1, 2) == 1) {
                                    bombs[j].sideCounter = 1;
                                } else {
                                    bombs[j].sideCounter = 3;
                                }
                            }
                            if (tank.realX > bombs[j].realX & tank.realY < bombs[j].realY) {
                                if (MathUtils.random(1, 2) == 1) {
                                    bombs[j].sideCounter = 2;
                                } else {
                                    bombs[j].sideCounter = 4;
                                }
                            }
                            if (tank.realX > bombs[j].realX & tank.realY > bombs[j].realY) {
                                if (MathUtils.random(1, 2) == 1) {
                                    bombs[j].sideCounter = 2;
                                } else {
                                    bombs[j].sideCounter = 3;
                                }
                            }
                        }
                    }
                    //управление огнем
                    if (visualGameStage != 4) {
                        if (enemies[i].ID == 1) {
                            if (MathUtils.random(1, 50) == 1) {
                                enemies[i].shootTrigger = true;
                            }
                        }
                        if (enemies[i].ID == 2) {
                            if (MathUtils.random(1, 10) == 1) {
                                enemies[i].shootTrigger = true;
                            }
                        }
                        if (enemies[i].ID == 3) {
                            if (MathUtils.random(1, 50) == 1) {
                                enemies[i].shootTrigger = true;
                            }
                        }
                    } else {
                        if (bossShield.durability == -1) {
                            bossShield.durability = 50;
                        }
                        if (bossShield.durability > 0) {
                            bossShield.x = enemies[i].realX + enemies[i].width / 2 - bossShield.width / 2;
                            bossShield.y = enemies[i].realY + enemies[i].height / 2 - bossShield.height / 2;
                            shieldBash.x = bossShield.x;
                            shieldBash.y = bossShield.y;
                            if (Tics % 60 == 0) {
                                BulletFlying(shieldBash, 0, shieldBash.whose, 5);
                            }
                        }
                        if (Tics % 1200 == 0 & bossShield.durability <= 0) {
                            maySpawnDown = true;
                            maySpawnUp = true;
                            maySpawnLeft = true;
                            maySpawnRight = true;
                            for (int j = 0; j < wallsCounter; j++) {
                                if (walls[j].y <= enemies[i].realY - enemies[i].height / 2 & enemies[i].realY - enemies[i].height / 2 <= walls[j].y + walls[j].height & walls[j].x <= enemies[i].realX + enemies[i].width / 2 & enemies[i].realX + enemies[i].width / 2 <= walls[j].x + walls[j].width & enemies[i].sideCounter == 3) {
                                    maySpawnDown = false;
                                } else if (walls[j].y <= enemies[i].realY + enemies[i].height * 3 / 2 & enemies[i].realY + enemies[i].height * 3 / 2 <= walls[j].y + walls[j].height & walls[j].x <= enemies[i].realX + enemies[i].width / 2 & enemies[i].realX + enemies[i].width / 2 <= walls[j].x + walls[j].width & enemies[i].sideCounter == 4) {
                                    maySpawnUp = false;
                                } else if (walls[j].x <= enemies[i].realX - enemies[i].width / 2 & enemies[i].realX - enemies[i].width / 2 <= walls[j].x + walls[j].width & walls[j].y <= enemies[i].realY + enemies[i].height / 2 & enemies[i].realY + enemies[i].height / 2 <= walls[j].y + walls[j].height & enemies[i].sideCounter == 2) {
                                    maySpawnLeft = false;
                                } else if (walls[j].x <= enemies[i].realX + enemies[i].width * 3 / 2 & enemies[i].realX + enemies[i].width * 3 / 2 <= walls[j].x + walls[j].width & walls[j].y <= enemies[i].realY + enemies[i].height / 2 & enemies[i].realY + enemies[i].height / 2 <= walls[j].y + walls[j].height & enemies[i].sideCounter == 1) {
                                    maySpawnRight = false;
                                }
                            }

                            if (maySpawnUp) {

                                    bombs[bombsCounter].realX = enemies[i].realX + enemies[i].width / 3;
                                    bombs[bombsCounter].realY = enemies[i].realY + enemies[i].height * 4 / 3;
                                    bombsCounter++;
                                    bombs[bombsCounter].realX = enemies[i].realX + enemies[i].width / 3;
                                    bombs[bombsCounter].realY = enemies[i].realY + enemies[i].height * 4 / 3;
                                    bombsCounter++;


                                    safers[safersCounter].realX = MathUtils.random(enemies[i].realX,enemies[i].realX+enemies[i].width);
                                    safers[safersCounter].realY = enemies[i].realY + enemies[i].height - enemies[i].height / 6;
                                    safersCounter++;
                                    safers[safersCounter].realX = MathUtils.random(enemies[i].realX,enemies[i].realX+enemies[i].width);
                                    safers[safersCounter].realY = enemies[i].realY + enemies[i].height - enemies[i].height / 6;
                                    safersCounter++;


                                maySpawnUp = false;
                            } else if (maySpawnDown) {

                                    bombs[bombsCounter].realX = enemies[i].realX + enemies[i].width / 3;
                                    bombs[bombsCounter].realY = enemies[i].realY - enemies[i].height / 3;
                                    bombsCounter++;
                                    bombs[bombsCounter].realX = enemies[i].realX + enemies[i].width / 3;
                                    bombs[bombsCounter].realY = enemies[i].realY - enemies[i].height / 3;
                                    bombsCounter++;


                                    safers[safersCounter].realX = MathUtils.random(enemies[i].realX,enemies[i].realX+enemies[i].width);
                                    safers[safersCounter].realY = enemies[i].realY - enemies[i].height / 6;
                                    safersCounter++;
                                    safers[safersCounter].realX = MathUtils.random(enemies[i].realX,enemies[i].realX+enemies[i].width);
                                    safers[safersCounter].realY = enemies[i].realY - enemies[i].height / 6;
                                    safersCounter++;


                                maySpawnDown = false;
                            } else if (maySpawnRight) {

                                    bombs[bombsCounter].realX = enemies[i].realX + enemies[i].width * 4 / 3;
                                    bombs[bombsCounter].realY = enemies[i].realY + enemies[i].height / 3;
                                    bombsCounter++;
                                    bombs[bombsCounter].realX = enemies[i].realX + enemies[i].width * 4 / 3;
                                    bombs[bombsCounter].realY = enemies[i].realY + enemies[i].height / 3;
                                    bombsCounter++;


                                    safers[safersCounter].realX = enemies[i].realX + enemies[i].width - enemies[i].width / 6;
                                    safers[safersCounter].realY = MathUtils.random(enemies[i].realY,enemies[i].realY+enemies[i].height);
                                    safersCounter++;
                                    safers[safersCounter].realX = enemies[i].realX + enemies[i].width - enemies[i].width / 6;
                                    safers[safersCounter].realY = MathUtils.random(enemies[i].realY,enemies[i].realY+enemies[i].height);
                                    safersCounter++;


                                maySpawnRight = false;
                            }
                        } else if (maySpawnLeft) {

                                bombs[bombsCounter].realX = enemies[i].realX - enemies[i].width / 3;
                                bombs[bombsCounter].realY = enemies[i].realY + enemies[i].height / 3;
                                bombsCounter++;
                                bombs[bombsCounter].realX = enemies[i].realX - enemies[i].width / 3;
                                bombs[bombsCounter].realY = enemies[i].realY + enemies[i].height / 3;
                                bombsCounter++;


                                safers[safersCounter].realX = enemies[i].realX - enemies[i].width / 6;
                                safers[safersCounter].realY = MathUtils.random(enemies[i].realY,enemies[i].realY+enemies[i].height);
                                safersCounter++;
                                safers[safersCounter].realX = enemies[i].realX - enemies[i].width / 6;
                                safers[safersCounter].realY = MathUtils.random(enemies[i].realY,enemies[i].realY+enemies[i].height);
                                safersCounter++;

                            maySpawnLeft = false;
                        }

                        if (enemies[i].realX + enemies[i].width * 3 >= tank.realX & tank.realX >= enemies[i].realX - enemies[i].width * 2 & enemies[i].realY + enemies[i].height * 3 >= tank.realY & tank.realY >= enemies[i].realY - enemies[i].height * 2) {
                            shockWave.isShooted = true;
                        }
                        if (shockWave.isShooted & shockWave.width < enemies[i].width * 5) {
                            shockWave.x = enemies[i].realX + enemies[i].width / 2 - shockWave.width / 2;
                            shockWave.y = enemies[i].realY + enemies[i].height / 2 - shockWave.height / 2;
                            if (DamageDealtToTank(shockWave.x, shockWave.y, shockWave.height, shockWave.width, -1, 4)) {
                                tank.reloading = -80;
                                isTankShock = true;
                            }
                            shockWave.width++;
                            shockWave.height++;
                        } else if (shockWave.width >= enemies[i].width * 5) {
                            shockWave.reset();
                            shockWave.width = 1;
                            shockWave.height = 1;
                        }
                        if (enemies[i].ID == 3) {
                            if (MathUtils.random(1, 50) == 1) {
                                enemies[i].shootTrigger = true;
                            }
                        }
                        if (tank.realX >= networkPointX - SCR_WIDTH / 10 & tank.realX <= networkPointX + SCR_WIDTH / 10 & tank.realY >= networkPointY - SCR_WIDTH / 10 & tank.realY <= networkPointY + SCR_WIDTH / 10) {
                            networkSignal = 3;
                        } else if (tank.realX >= networkPointX - SCR_WIDTH / 5 & tank.realX <= networkPointX + SCR_WIDTH / 5 & tank.realY >= networkPointY - SCR_WIDTH / 5 & tank.realY <= networkPointY + SCR_WIDTH / 5) {
                            networkSignal = 2;
                        } else if (tank.realX >= networkPointX - SCR_WIDTH / 3 & tank.realX <= networkPointX + SCR_WIDTH / 3 & tank.realY >= networkPointY - SCR_WIDTH / 3 & tank.realY <= networkPointY + SCR_WIDTH / 3) {
                            networkSignal = 1;
                        } else {
                            networkSignal = 0;
                        }
                        System.out.println(networkSignal + " " + bossShield.durability);
                        if (Tics % 50 == 0 & bossShield.durability > 0) {
                            orbitalStrikeCoolDown += networkSignal * 4;
                        }
                        if (orbitalStrikeCoolDown >= 100) {
                            orbitalStrikeCoolDown = 100;
                            isOrbitalStrikeReady = true;
                            OrbitalStrikeReadySound.play(StickyTanks.masterVolume / 100);
                        }

                        if (Gdx.input.justTouched() & isOrbitalStrikeReady) {
                            orbitalStrike.x = touch.x;
                            orbitalStrike.y = touch.y;
                            if (DamageDealtToEnemy(orbitalStrike.x, orbitalStrike.y, orbitalStrike.width, orbitalStrike.height, 0, -1)) {
                                bossShield.durability -= 11;
                            }
                            OrbitalStrikeShootSound.play(StickyTanks.masterVolume / 100);
                            isOrbitalStrikeReady = false;
                            orbitalStrike.hit = true;
                            orbitalStrikeCoolDown = 0;
                            networkPointY = MathUtils.random(HEIGHT * 2, SCR_HEIGHT - HEIGHT * 2);
                            networkPointX = MathUtils.random(WIDTH * 2, SCR_WIDTH - WIDTH * 2);
                        }

                    }
                }

            }
        }
        if (isTankShock) {
            tank.stepX = tank.width / 25;
            tank.stepY = tank.height / 25;
        } else {
            tank.stepX = tank.width / 15;
            tank.stepY = tank.height / 15;
        }
        if (Tics % 200 == 0) {
            isTankShock = false;
        }
        //отрисовка
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(SteelWallImg, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        batch.draw(BackgroundImg, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        batch.draw(OrbitalStrikeRayImg, orbitalStrike.x, orbitalStrike.y, orbitalStrike.width * 2, SCR_HEIGHT - orbitalStrike.height);
        batch.draw(OrbitalStrikeExplotionImg, orbitalStrike.x, orbitalStrike.y, orbitalStrike.width * 2, orbitalStrike.height * 2);

        for (int i = 0; i < bombsCounter; i++) {
            if (!bombs[i].isDestroyed) {
                if(bombs[i].sideCounter == 1 | bombs[i].sideCounter == 2) {
                    batch.draw(BombXImg, bombs[i].realX, bombs[i].realY, bombs[i].width, bombs[i].height);
                }
                 else if(bombs[i].sideCounter == 3 | bombs[i].sideCounter == 4) {
                    batch.draw(BombYImg, bombs[i].realX, bombs[i].realY, bombs[i].width, bombs[i].height);
                }
            }
            if (bombs[i].hitpoints <= 0) {
                if (!bombs[i].isDestroyed) {
                    if (MathUtils.random(1, 3) == 1) {
                        batch.draw(Explosion1Img, bombs[i].realX - 13, bombs[i].realY - 3, LAB_WIDTH*2, LAB_HEIGHT*2);
                    } else if (MathUtils.random(1, 3) == 3) {
                        batch.draw(Explosion2Img, bombs[i].realX - 13, bombs[i].realY - 3, LAB_WIDTH*2, LAB_HEIGHT*2);
                    } else {
                        batch.draw(Explosion3Img, bombs[i].realX - 13, bombs[i].realY - 3, LAB_WIDTH*2, LAB_HEIGHT*2);
                    }
                    ExplosionSound.play(StickyTanks.masterVolume / 100);
                }
                bombs[i].isDestroyed = true;
                //   enemies[i].realX += SCR_WIDTH;
                //   enemies[i].realY += SCR_HEIGHT;
            }
        }
        for (int i = 0; i < safersCounter; i++) {
            if(safers[i].hitpoints <= 0){
                safers[i].isDestroyed = true;
            }
            if (!safers[i].isDestroyed) {
                batch.draw(SaferImg, safers[i].realX, safers[i].realY, safers[i].width, safers[i].height);
            }
        }
        for (int i = 0; i < basicBombCounter; i++) {
            batch.draw(BasicBombImg, basicBomb[i].x, basicBomb[i].y, basicBomb[i].width, basicBomb[i].height);

            if (basicBomb[i].hit) {
                ExplosionSound.play(StickyTanks.masterVolume / 200);
                basicBomb[i].hit = false;
                if (MathUtils.random(1, 3) == 1) {
                    batch.draw(Explosion1Img, basicBomb[i].x - 13, basicBomb[i].y - 3, LAB_WIDTH, LAB_HEIGHT);
                } else if (MathUtils.random(1, 3) == 3) {
                    batch.draw(Explosion2Img, basicBomb[i].x - 13, basicBomb[i].y - 3, LAB_WIDTH, LAB_HEIGHT);
                } else {
                    batch.draw(Explosion3Img, basicBomb[i].x - 13, basicBomb[i].y - 3, LAB_WIDTH, LAB_HEIGHT);
                }
                basicBomb[i].x = SCR_WIDTH + 1000;
                basicBomb[i].y = SCR_HEIGHT + 1000;
            }
        }
        for (int i = 0; i < clockBombCounter; i++) {
            batch.draw(ClockBombImg, clockBomb[i].x, clockBomb[i].y, clockBomb[i].width / 8, clockBomb[i].height / 8);
            //  boolean b = clockBomb[i].livTime == 0 & clockBomb[i].x != SCR_WIDTH + 1000;

            if (clockBomb[i].hit) {
                ExplosionSound.play(StickyTanks.masterVolume / 200);
                clockBomb[i].hit = false;
                if (MathUtils.random(1, 3) == 1) {
                    batch.draw(Explosion1Img, clockBomb[i].x - clockBomb[i].width / 2F, clockBomb[i].y - clockBomb[i].width / 2F, clockBomb[i].width, clockBomb[i].height);
                    //DamageDealtToEnemy()
                } else if (MathUtils.random(1, 3) == 3) {
                    batch.draw(Explosion2Img, clockBomb[i].x - clockBomb[i].width / 2F, clockBomb[i].y - clockBomb[i].width / 2F, clockBomb[i].width, clockBomb[i].height);
                } else {
                    batch.draw(Explosion3Img, clockBomb[i].x - clockBomb[i].width / 2F, clockBomb[i].y - clockBomb[i].width / 2F, clockBomb[i].width, clockBomb[i].height);
                }
                clockBomb[i].x = SCR_WIDTH + 1000;
                clockBomb[i].y = SCR_HEIGHT + 1000;
            }
        }
        for (int i = 0; i < hedgehogCounter; i++) {
            // batch.draw(SteelWallImg, hedgehog[i].x, hedgehog[i].y, hedgehog[i].width, hedgehog[i].height);
            // batch.draw(TargetedAmmoImg, walls[hedgehogWallsCounter].x, walls[hedgehogWallsCounter].y, walls[hedgehogWallsCounter].width, walls[hedgehogWallsCounter].height);
            for (int j = LAB_HEIGHT * LAB_WIDTH + 1; j < hedgehogWallsCounter; j++) {
                if (walls[j].durability < 0) {
                    walls[j].x = SCR_WIDTH * 100;
                }
                //  System.out.println(walls[j].durability);
                batch.draw(HedgehogImg, walls[j].x, walls[j].y, walls[j].width, walls[j].height);
            }
            hedgehog[i].x = SCR_WIDTH + 1000;
            hedgehog[i].y = SCR_HEIGHT + 1000;
            if (hedgehog[i].hit) {
                hedgehog[i].x = SCR_WIDTH + 1000;
                hedgehog[i].y = SCR_HEIGHT + 1000;
            }
        }


        for (int i = 0; i < basicRocketCounter; i++) {
            if (basicRocket[i].sideCounter == 1) {
                batch.draw(BasicRocketLeftXImg, basicRocket[i].x, basicRocket[i].y, basicRocket[i].width, basicRocket[i].height);
            }
            if (basicRocket[i].sideCounter == 2) {
                batch.draw(BasicRocketRightXImg, basicRocket[i].x, basicRocket[i].y, basicRocket[i].width, basicRocket[i].height);
            }
            if (basicRocket[i].sideCounter == 3 | basicRocket[i].sideCounter == 0) {
                batch.draw(BasicRocketUpYImg, basicRocket[i].x, basicRocket[i].y, basicRocket[i].width, basicRocket[i].height);
            }
            if (basicRocket[i].sideCounter == 4) {
                batch.draw(BasicRocketDownYImg, basicRocket[i].x, basicRocket[i].y, basicRocket[i].width, basicRocket[i].height);
            }

            if (basicRocket[i].hit) {
                RocketExplotionSound.play(StickyTanks.masterVolume / 200);
                basicRocket[i].hit = false;
                if (basicRocket[i].sideCounter == 1) {
                    if (MathUtils.random(1, 3) == 1) {
                        batch.draw(Explosion1Img, basicRocket[i].x - 13, basicRocket[i].y - 3, LAB_WIDTH, LAB_HEIGHT);
                    } else if (MathUtils.random(1, 3) == 3) {
                        batch.draw(Explosion2Img, basicRocket[i].x - 13, basicRocket[i].y - 3, LAB_WIDTH, LAB_HEIGHT);
                    } else {
                        batch.draw(Explosion3Img, basicRocket[i].x - 13, basicRocket[i].y - 3, LAB_WIDTH, LAB_HEIGHT);
                    }
                    basicRocket[i].x = SCR_WIDTH + 1000;
                    basicRocket[i].y = SCR_HEIGHT + 1000;
                }
                if (basicRocket[i].sideCounter == 2) {
                    if (MathUtils.random(1, 3) == 1) {
                        batch.draw(Explosion1Img, basicRocket[i].x - 10, basicRocket[i].y - 3, LAB_WIDTH, LAB_HEIGHT);
                    } else if (MathUtils.random(1, 3) == 3) {
                        batch.draw(Explosion2Img, basicRocket[i].x - 10, basicRocket[i].y - 3, LAB_WIDTH, LAB_HEIGHT);
                    } else {
                        batch.draw(Explosion3Img, basicRocket[i].x - 10, basicRocket[i].y - 3, LAB_WIDTH, LAB_HEIGHT);
                    }
                    basicRocket[i].x = SCR_WIDTH + 1000;
                    basicRocket[i].y = SCR_HEIGHT + 1000;
                }
                if (basicRocket[i].sideCounter == 3) {
                    if (MathUtils.random(1, 3) == 1) {
                        batch.draw(Explosion1Img, basicRocket[i].x - 10, basicRocket[i].y - 2, LAB_WIDTH, LAB_HEIGHT);
                    } else if (MathUtils.random(1, 3) == 3) {
                        batch.draw(Explosion2Img, basicRocket[i].x - 10, basicRocket[i].y - 2, LAB_WIDTH, LAB_HEIGHT);
                    } else {
                        batch.draw(Explosion3Img, basicRocket[i].x - 10, basicRocket[i].y - 2, LAB_WIDTH, LAB_HEIGHT);
                    }
                    basicRocket[i].x = SCR_WIDTH + 1000;
                    basicRocket[i].y = SCR_HEIGHT + 1000;
                }
                if (basicRocket[i].sideCounter == 4) {
                    if (MathUtils.random(1, 3) == 1) {
                        batch.draw(Explosion1Img, basicRocket[i].x - 10, basicRocket[i].y - 2, LAB_WIDTH, LAB_HEIGHT);
                    } else if (MathUtils.random(1, 3) == 3) {
                        batch.draw(Explosion2Img, basicRocket[i].x - 10, basicRocket[i].y - 2, LAB_WIDTH, LAB_HEIGHT);
                    } else {
                        batch.draw(Explosion3Img, basicRocket[i].x - 10, basicRocket[i].y - 2, LAB_WIDTH, LAB_HEIGHT);
                    }
                    basicRocket[i].x = SCR_WIDTH + 1000;
                    basicRocket[i].y = SCR_HEIGHT + 1000;
                }
            }

        }
        for (int i = 0; i < bigRocketCounter; i++) {
            if (bigRocket[i].sideCounter == 1) {
                batch.draw(BigRocketLeftXImg, bigRocket[i].x, bigRocket[i].y, bigRocket[i].width, bigRocket[i].height);
            }
            if (bigRocket[i].sideCounter == 2) {
                batch.draw(BasicRocketRightXImg, bigRocket[i].x, bigRocket[i].y, bigRocket[i].width, bigRocket[i].height);
            }
            if (bigRocket[i].sideCounter == 3 | bigRocket[i].sideCounter == 0) {
                batch.draw(BigRocketUpYImg, bigRocket[i].x, bigRocket[i].y, bigRocket[i].width, bigRocket[i].height);
            }
            if (bigRocket[i].sideCounter == 4) {
                batch.draw(BasicRocketDownYImg, bigRocket[i].x, bigRocket[i].y, bigRocket[i].width, bigRocket[i].height);
            }

            if (bigRocket[i].hit) {
                RocketExplotionSound.play(StickyTanks.masterVolume / 250);
                bigRocket[i].hit = false;
                if (bigRocket[i].sideCounter == 1) {
                    if (MathUtils.random(1, 3) == 1) {
                        batch.draw(Explosion1Img, bigRocket[i].x - 13, bigRocket[i].y - 3, LAB_WIDTH, LAB_HEIGHT);
                    } else if (MathUtils.random(1, 3) == 3) {
                        batch.draw(Explosion2Img, bigRocket[i].x - 13, bigRocket[i].y - 3, LAB_WIDTH, LAB_HEIGHT);
                    } else {
                        batch.draw(Explosion3Img, bigRocket[i].x - 13, bigRocket[i].y - 3, LAB_WIDTH, LAB_HEIGHT);
                    }
                    bigRocket[i].x = SCR_WIDTH + 1000;
                    bigRocket[i].y = SCR_HEIGHT + 1000;
                }
                if (bigRocket[i].sideCounter == 2) {
                    if (MathUtils.random(1, 3) == 1) {
                        batch.draw(Explosion1Img, bigRocket[i].x - 10, bigRocket[i].y - 3, LAB_WIDTH, LAB_HEIGHT);
                    } else if (MathUtils.random(1, 3) == 3) {
                        batch.draw(Explosion2Img, bigRocket[i].x - 10, bigRocket[i].y - 3, LAB_WIDTH, LAB_HEIGHT);
                    } else {
                        batch.draw(Explosion3Img, bigRocket[i].x - 10, bigRocket[i].y - 3, LAB_WIDTH, LAB_HEIGHT);
                    }
                    bigRocket[i].x = SCR_WIDTH + 1000;
                    bigRocket[i].y = SCR_HEIGHT + 1000;
                }
                if (bigRocket[i].sideCounter == 3) {
                    if (MathUtils.random(1, 3) == 1) {
                        batch.draw(Explosion1Img, bigRocket[i].x - 10, bigRocket[i].y - 2, LAB_WIDTH, LAB_HEIGHT);
                    } else if (MathUtils.random(1, 3) == 3) {
                        batch.draw(Explosion2Img, bigRocket[i].x - 10, bigRocket[i].y - 2, LAB_WIDTH, LAB_HEIGHT);
                    } else {
                        batch.draw(Explosion3Img, bigRocket[i].x - 10, bigRocket[i].y - 2, LAB_WIDTH, LAB_HEIGHT);
                    }
                    bigRocket[i].x = SCR_WIDTH + 1000;
                    bigRocket[i].y = SCR_HEIGHT + 1000;
                }
                if (bigRocket[i].sideCounter == 4) {
                    if (MathUtils.random(1, 3) == 1) {
                        batch.draw(Explosion1Img, bigRocket[i].x - 10, bigRocket[i].y - 2, LAB_WIDTH, LAB_HEIGHT);
                    } else if (MathUtils.random(1, 3) == 3) {
                        batch.draw(Explosion2Img, bigRocket[i].x - 10, bigRocket[i].y - 2, LAB_WIDTH, LAB_HEIGHT);
                    } else {
                        batch.draw(Explosion3Img, bigRocket[i].x - 10, bigRocket[i].y - 2, LAB_WIDTH, LAB_HEIGHT);
                    }
                    bigRocket[i].x = SCR_WIDTH + 1000;
                    bigRocket[i].y = SCR_HEIGHT + 1000;
                }
            }
        }

        for (int i = 0; i < napalmRocketCounter; i++) {
            if (napalmRocket[i].sideCounter == 1) {
                batch.draw(NapalmRocketLeftXImg, napalmRocket[i].x, napalmRocket[i].y, napalmRocket[i].width, napalmRocket[i].height);
            }
            if (napalmRocket[i].sideCounter == 2) {
                batch.draw(NapalmRocketRightXImg, napalmRocket[i].x, napalmRocket[i].y, napalmRocket[i].width, napalmRocket[i].height);
            }
            if (napalmRocket[i].sideCounter == 3) {
                batch.draw(NapalmRocketUpYImg, napalmRocket[i].x, napalmRocket[i].y, napalmRocket[i].width, napalmRocket[i].height);
            }
            if (napalmRocket[i].sideCounter == 4) {
                batch.draw(NapalmRocketDownYImg, napalmRocket[i].x, napalmRocket[i].y, napalmRocket[i].width, napalmRocket[i].height);
            }
            if (napalmPool[i].livTime > 0) {
                if (Tics % 15 == 0) {
                    DamageDealtToEnemy(napalmPool[i].x, napalmPool[i].y, napalmPool[i].height, napalmPool[i].width, i, 7);
                }

                batch.draw(NapalmPoolImg, napalmPool[i].x, napalmPool[i].y, napalmPool[i].width, napalmPool[i].height);
                if (napalmRocket[i].x > SCR_WIDTH & !onPause) {
                    napalmPool[i].livTime--;
                }
            } else if (napalmPool[i].livTime <= 0) {
                napalmPool[i].x = SCR_WIDTH * 100;
                napalmPool[i].y = SCR_HEIGHT * 100;
            }
            if (napalmRocket[i].hit) {
                Tics1 = Tics;
                NapalmExplotionSound.play(StickyTanks.masterVolume / 250);
                NapalmPoolBurningSound.play(StickyTanks.masterVolume / 250);
                napalmRocket[i].hit = false;
                napalmPool[i].x = napalmRocket[i].x - napalmPool[i].width / 2;
                napalmPool[i].y = napalmRocket[i].y - napalmPool[i].height / 2;
                napalmPool[i].isShooted = true;
                if (napalmRocket[i].sideCounter == 1) {
                    if (MathUtils.random(1, 3) == 1) {
                        batch.draw(Explosion1Img, napalmRocket[i].x - 13, napalmRocket[i].y - 3, LAB_WIDTH * 1.5F, LAB_HEIGHT * 1.5F);
                    } else if (MathUtils.random(1, 3) == 3) {
                        batch.draw(Explosion2Img, napalmRocket[i].x - 13, napalmRocket[i].y - 3, LAB_WIDTH * 1.5F, LAB_HEIGHT * 1.5F);
                    } else {
                        batch.draw(Explosion3Img, napalmRocket[i].x - 13, napalmRocket[i].y - 3, LAB_WIDTH * 1.5F, LAB_HEIGHT * 1.5F);
                    }

                    napalmRocket[i].x = SCR_WIDTH + 1000;
                    napalmRocket[i].y = SCR_HEIGHT + 1000;
                }
                if (napalmRocket[i].sideCounter == 2) {
                    if (MathUtils.random(1, 3) == 1) {
                        batch.draw(Explosion1Img, napalmRocket[i].x - 10, napalmRocket[i].y - 3, LAB_WIDTH * 1.5F, LAB_HEIGHT * 1.5F);
                    } else if (MathUtils.random(1, 3) == 3) {
                        batch.draw(Explosion2Img, napalmRocket[i].x - 10, napalmRocket[i].y - 3, LAB_WIDTH * 1.5F, LAB_HEIGHT * 1.5F);
                    } else {
                        batch.draw(Explosion3Img, napalmRocket[i].x - 10, napalmRocket[i].y - 3, LAB_WIDTH * 1.5F, LAB_HEIGHT * 1.5F);
                    }
                    napalmRocket[i].x = SCR_WIDTH + 1000;
                    napalmRocket[i].y = SCR_HEIGHT + 1000;
                }
                if (napalmRocket[i].sideCounter == 3) {
                    if (MathUtils.random(1, 3) == 1) {
                        batch.draw(Explosion1Img, napalmRocket[i].x - 10, napalmRocket[i].y - 2, LAB_WIDTH * 1.5F, LAB_HEIGHT * 1.5F);
                    } else if (MathUtils.random(1, 3) == 3) {
                        batch.draw(Explosion2Img, napalmRocket[i].x - 10, napalmRocket[i].y - 2, LAB_WIDTH * 1.5F, LAB_HEIGHT * 1.5F);
                    } else {
                        batch.draw(Explosion3Img, napalmRocket[i].x - 10, napalmRocket[i].y - 2, LAB_WIDTH * 1.5F, LAB_HEIGHT * 1.5F);
                    }
                    napalmRocket[i].x = SCR_WIDTH + 1000;
                    napalmRocket[i].y = SCR_HEIGHT + 1000;
                }
                if (napalmRocket[i].sideCounter == 4) {
                    if (MathUtils.random(1, 3) == 1) {
                        batch.draw(Explosion1Img, napalmRocket[i].x - 10, napalmRocket[i].y - 2, LAB_WIDTH * 1.5F, LAB_HEIGHT * 1.5F);
                    } else if (MathUtils.random(1, 3) == 3) {
                        batch.draw(Explosion2Img, napalmRocket[i].x - 10, napalmRocket[i].y - 2, LAB_WIDTH * 1.5F, LAB_HEIGHT * 1.5F);
                    } else {
                        batch.draw(Explosion3Img, napalmRocket[i].x - 10, napalmRocket[i].y - 2, LAB_WIDTH * 1.5F, LAB_HEIGHT * 1.5F);
                    }
                    napalmRocket[i].x = SCR_WIDTH + 1000;
                    napalmRocket[i].y = SCR_HEIGHT + 1000;
                }

            }

        }
       /* for (int i = 0; i < napalmRocketCounter; i++) {
            if(!napalmRocket[i].hit) {
                if (napalmTimer[i] > 0) {
                    DamageDealtToEnemy(napalmPool[i].x, napalmPool[i].y, napalmPool[i].height, napalmPool[i].width,i ,7);
                    batch.draw(SteelWallImg, napalmPool[i].x, napalmPool[i].y, napalmPool[i].width, napalmPool[i].height);
                    napalmTimer[i]--;
                } else if (napalmTimer[i]<=0) {
                    napalmPool[i].x = SCR_WIDTH*100;
                    napalmPool[i].y = SCR_HEIGHT*100;
                }
            }
        }*/


        //////

        for (int i = 0; i < basicAmmoCounter; i++) {

            if (basicAmmo[i].sideCounter == 1) {
                batch.draw(BasicProjectileLeftXImg, basicAmmo[i].x, basicAmmo[i].y, basicAmmo[i].width, basicAmmo[i].height);
            }
            if (basicAmmo[i].sideCounter == 2) {
                batch.draw(BasicProjectileRightXImg, basicAmmo[i].x, basicAmmo[i].y, basicAmmo[i].width, basicAmmo[i].height);
            }
            if (basicAmmo[i].sideCounter == 3 | basicAmmo[i].sideCounter == 0) {
                batch.draw(BasicProjectileUpYImg, basicAmmo[i].x, basicAmmo[i].y, basicAmmo[i].width, basicAmmo[i].height);
            }
            if (basicAmmo[i].sideCounter == 4) {
                batch.draw(BasicProjectileDownYImg, basicAmmo[i].x, basicAmmo[i].y, basicAmmo[i].width, basicAmmo[i].height);
            }

            if (basicAmmo[i].hit) {
                ExplosionSound.play(StickyTanks.masterVolume / 300);
                basicAmmo[i].hit = false;
                if (basicAmmo[i].sideCounter == 1) {
                    if (MathUtils.random(1, 3) == 1) {
                        batch.draw(Explosion1Img, basicAmmo[i].x - 13, basicAmmo[i].y - 3, LAB_WIDTH, LAB_HEIGHT);
                    } else if (MathUtils.random(1, 3) == 3) {
                        batch.draw(Explosion2Img, basicAmmo[i].x - 13, basicAmmo[i].y - 3, LAB_WIDTH, LAB_HEIGHT);
                    } else {
                        batch.draw(Explosion3Img, basicAmmo[i].x - 13, basicAmmo[i].y - 3, LAB_WIDTH, LAB_HEIGHT);
                    }
                    basicAmmo[i].x = SCR_WIDTH + 1000;
                    basicAmmo[i].y = SCR_HEIGHT + 1000;
                }
                if (basicAmmo[i].sideCounter == 2) {
                    if (MathUtils.random(1, 3) == 1) {
                        batch.draw(Explosion1Img, basicAmmo[i].x - 10, basicAmmo[i].y - 3, LAB_WIDTH, LAB_HEIGHT);
                    } else if (MathUtils.random(1, 3) == 3) {
                        batch.draw(Explosion2Img, basicAmmo[i].x - 10, basicAmmo[i].y - 3, LAB_WIDTH, LAB_HEIGHT);
                    } else {
                        batch.draw(Explosion3Img, basicAmmo[i].x - 10, basicAmmo[i].y - 3, LAB_WIDTH, LAB_HEIGHT);
                    }
                    basicAmmo[i].x = SCR_WIDTH + 1000;
                    basicAmmo[i].y = SCR_HEIGHT + 1000;
                }
                if (basicAmmo[i].sideCounter == 3) {
                    if (MathUtils.random(1, 3) == 1) {
                        batch.draw(Explosion1Img, basicAmmo[i].x - 10, basicAmmo[i].y - 2, LAB_WIDTH, LAB_HEIGHT);
                    } else if (MathUtils.random(1, 3) == 3) {
                        batch.draw(Explosion2Img, basicAmmo[i].x - 10, basicAmmo[i].y - 2, LAB_WIDTH, LAB_HEIGHT);
                    } else {
                        batch.draw(Explosion3Img, basicAmmo[i].x - 10, basicAmmo[i].y - 2, LAB_WIDTH, LAB_HEIGHT);
                    }
                    basicAmmo[i].x = SCR_WIDTH + 1000;
                    basicAmmo[i].y = SCR_HEIGHT + 1000;
                }
                if (basicAmmo[i].sideCounter == 4) {
                    if (MathUtils.random(1, 3) == 1) {
                        batch.draw(Explosion1Img, basicAmmo[i].x - 10, basicAmmo[i].y - 2, LAB_WIDTH, LAB_HEIGHT);
                    } else if (MathUtils.random(1, 3) == 3) {
                        batch.draw(Explosion2Img, basicAmmo[i].x - 10, basicAmmo[i].y - 2, LAB_WIDTH, LAB_HEIGHT);
                    } else {
                        batch.draw(Explosion3Img, basicAmmo[i].x - 10, basicAmmo[i].y - 2, LAB_WIDTH, LAB_HEIGHT);
                    }
                    basicAmmo[i].x = SCR_WIDTH + 1000;
                    basicAmmo[i].y = SCR_HEIGHT + 1000;
                }
            }
        }


        for (int i = 0; i < mashinegunAmmoCounter; i++) {
            if (machinegunAmmo[i].sideCounter == 1) {
                batch.draw(MachineGunAmmoLeftXImg, machinegunAmmo[i].x, machinegunAmmo[i].y, machinegunAmmo[i].width, machinegunAmmo[i].height);
            }
            if (machinegunAmmo[i].sideCounter == 2) {
                batch.draw(MachineGunAmmoRightXImg, machinegunAmmo[i].x, machinegunAmmo[i].y, machinegunAmmo[i].width, machinegunAmmo[i].height);
            }
            if (machinegunAmmo[i].sideCounter == 3 | machinegunAmmo[i].sideCounter == 0) {
                batch.draw(MachineGunAmmoUpYImg, machinegunAmmo[i].x, machinegunAmmo[i].y, machinegunAmmo[i].width, machinegunAmmo[i].height);
            }
            if (machinegunAmmo[i].sideCounter == 4) {
                batch.draw(MachineGunAmmoDownYImg, machinegunAmmo[i].x, machinegunAmmo[i].y, machinegunAmmo[i].width, machinegunAmmo[i].height);
            }

            if (machinegunAmmo[i].hit) {
                MashineGunExplosionSound.play(StickyTanks.masterVolume / 300);
                machinegunAmmo[i].hit = false;
                if (machinegunAmmo[i].sideCounter == 1) {
                    if (MathUtils.random(1, 3) == 1) {
                        batch.draw(Explosion1Img, machinegunAmmo[i].x - 10, machinegunAmmo[i].y - 3, LAB_WIDTH * 3 / 4, LAB_HEIGHT * 3 / 4);
                    } else if (MathUtils.random(1, 3) == 3) {
                        batch.draw(Explosion2Img, machinegunAmmo[i].x - 10, machinegunAmmo[i].y - 3, LAB_WIDTH * 3 / 4, LAB_HEIGHT * 3 / 4);
                    } else {
                        batch.draw(Explosion3Img, machinegunAmmo[i].x - 10, machinegunAmmo[i].y - 3, LAB_WIDTH * 3 / 4, LAB_HEIGHT * 3 / 4);
                    }
                    machinegunAmmo[i].x = SCR_WIDTH + 1000;
                    machinegunAmmo[i].y = SCR_HEIGHT + 1000;
                }
                if (machinegunAmmo[i].sideCounter == 2) {
                    if (MathUtils.random(1, 3) == 1) {
                        batch.draw(Explosion1Img, machinegunAmmo[i].x - 10, machinegunAmmo[i].y - 3, LAB_WIDTH * 3 / 4, LAB_HEIGHT * 3 / 4);
                    } else if (MathUtils.random(1, 3) == 3) {
                        batch.draw(Explosion2Img, machinegunAmmo[i].x - 10, machinegunAmmo[i].y - 3, LAB_WIDTH * 3 / 4, LAB_HEIGHT * 3 / 4);
                    } else {
                        batch.draw(Explosion3Img, machinegunAmmo[i].x - 10, machinegunAmmo[i].y - 3, LAB_WIDTH * 3 / 4, LAB_HEIGHT * 3 / 4);
                    }
                    machinegunAmmo[i].x = SCR_WIDTH + 1000;
                    machinegunAmmo[i].y = SCR_HEIGHT + 1000;
                }
                if (machinegunAmmo[i].sideCounter == 3) {
                    if (MathUtils.random(1, 3) == 1) {
                        batch.draw(Explosion1Img, machinegunAmmo[i].x - 10, machinegunAmmo[i].y + 2, LAB_WIDTH * 3 / 4, LAB_HEIGHT * 3 / 4);
                    } else if (MathUtils.random(1, 3) == 3) {
                        batch.draw(Explosion2Img, machinegunAmmo[i].x - 10, machinegunAmmo[i].y + 2, LAB_WIDTH * 3 / 4, LAB_HEIGHT * 3 / 4);
                    } else {
                        batch.draw(Explosion3Img, machinegunAmmo[i].x - 10, machinegunAmmo[i].y + 2, LAB_WIDTH * 3 / 4, LAB_HEIGHT * 3 / 4);
                    }
                    machinegunAmmo[i].x = SCR_WIDTH + 1000;
                    machinegunAmmo[i].y = SCR_HEIGHT + 1000;
                }
                if (machinegunAmmo[i].sideCounter == 4) {
                    if (MathUtils.random(1, 3) == 1) {
                        batch.draw(Explosion1Img, machinegunAmmo[i].x - 10, machinegunAmmo[i].y - 4, LAB_WIDTH * 3 / 4, LAB_HEIGHT * 3 / 4);
                    } else if (MathUtils.random(1, 3) == 3) {
                        batch.draw(Explosion2Img, machinegunAmmo[i].x - 10, machinegunAmmo[i].y - 4, LAB_WIDTH * 3 / 4, LAB_HEIGHT * 3 / 4);
                    } else {
                        batch.draw(Explosion3Img, machinegunAmmo[i].x - 10, machinegunAmmo[i].y - 4, LAB_WIDTH * 3 / 4, LAB_HEIGHT * 3 / 4);
                    }
                    machinegunAmmo[i].x = SCR_WIDTH + 1000;
                    machinegunAmmo[i].y = SCR_HEIGHT + 1000;
                }
                machinegunAmmo[i].x = SCR_WIDTH + 1000;
                machinegunAmmo[i].y = SCR_HEIGHT + 1000;
            }

        }

        for (int i = 0; i < bigAmmoCounter; i++) {

            if (bigAmmo[i].sideCounter == 1) {
                batch.draw(BigAmmoLeftXImg, bigAmmo[i].x, bigAmmo[i].y, bigAmmo[i].width, bigAmmo[i].height);
            }
            if (bigAmmo[i].sideCounter == 2) {
                batch.draw(BigAmmoRightXImg, bigAmmo[i].x, bigAmmo[i].y, bigAmmo[i].width, bigAmmo[i].height);
            }
            if (bigAmmo[i].sideCounter == 3 | bigAmmo[i].sideCounter == 0) {
                batch.draw(BigAmmoUpYImg, bigAmmo[i].x, bigAmmo[i].y, bigAmmo[i].width, bigAmmo[i].height);
            }
            if (bigAmmo[i].sideCounter == 4) {
                batch.draw(BigAmmoDownYImg, bigAmmo[i].x, bigAmmo[i].y, bigAmmo[i].width, bigAmmo[i].height);
            }

            if (bigAmmo[i].hit) {
                ExplosionSound.play(StickyTanks.masterVolume / 300);
                bigAmmo[i].hit = false;
                if (bigAmmo[i].sideCounter == 1) {
                    if (MathUtils.random(1, 3) == 1) {
                        batch.draw(Explosion1Img, bigAmmo[i].x - 13, bigAmmo[i].y - 3, LAB_WIDTH * 5 / 4, LAB_HEIGHT * 5 / 4);
                    } else if (MathUtils.random(1, 3) == 3) {
                        batch.draw(Explosion2Img, bigAmmo[i].x - 13, bigAmmo[i].y - 3, LAB_WIDTH * 5 / 4, LAB_HEIGHT * 5 / 4);
                    } else {
                        batch.draw(Explosion3Img, bigAmmo[i].x - 13, bigAmmo[i].y - 3, LAB_WIDTH * 5 / 4, LAB_HEIGHT * 5 / 4);
                    }
                    bigAmmo[i].x = SCR_WIDTH + 1000;
                    bigAmmo[i].y = SCR_HEIGHT + 1000;
                }
                if (bigAmmo[i].sideCounter == 2) {
                    if (MathUtils.random(1, 3) == 1) {
                        batch.draw(Explosion1Img, bigAmmo[i].x - 10, bigAmmo[i].y - 3, LAB_WIDTH * 5 / 4, LAB_HEIGHT * 5 / 4);
                    } else if (MathUtils.random(1, 3) == 3) {
                        batch.draw(Explosion2Img, bigAmmo[i].x - 10, bigAmmo[i].y - 3, LAB_WIDTH * 5 / 4, LAB_HEIGHT * 5 / 4);
                    } else {
                        batch.draw(Explosion3Img, bigAmmo[i].x - 10, bigAmmo[i].y - 3, LAB_WIDTH * 5 / 4, LAB_HEIGHT * 5 / 4);
                    }
                    bigAmmo[i].x = SCR_WIDTH + 1000;
                    bigAmmo[i].y = SCR_HEIGHT + 1000;
                }
                if (bigAmmo[i].sideCounter == 3) {
                    if (MathUtils.random(1, 3) == 1) {
                        batch.draw(Explosion1Img, bigAmmo[i].x - 10, bigAmmo[i].y - 2, LAB_WIDTH * 5 / 4, LAB_HEIGHT * 5 / 4);
                    } else if (MathUtils.random(1, 3) == 3) {
                        batch.draw(Explosion2Img, bigAmmo[i].x - 10, bigAmmo[i].y - 2, LAB_WIDTH * 5 / 4, LAB_HEIGHT * 5 / 4);
                    } else {
                        batch.draw(Explosion3Img, bigAmmo[i].x - 10, bigAmmo[i].y - 2, LAB_WIDTH * 5 / 4, LAB_HEIGHT * 5 / 4);
                    }
                    bigAmmo[i].x = SCR_WIDTH + 1000;
                    bigAmmo[i].y = SCR_HEIGHT + 1000;
                }
                if (bigAmmo[i].sideCounter == 4) {
                    if (MathUtils.random(1, 3) == 1) {
                        batch.draw(Explosion1Img, bigAmmo[i].x - 10, bigAmmo[i].y - 2, LAB_WIDTH * 5 / 4, LAB_HEIGHT * 5 / 4);
                    } else if (MathUtils.random(1, 3) == 3) {
                        batch.draw(Explosion2Img, bigAmmo[i].x - 10, bigAmmo[i].y - 2, LAB_WIDTH * 5 / 4, LAB_HEIGHT * 5 / 4);
                    } else {
                        batch.draw(Explosion3Img, bigAmmo[i].x - 10, bigAmmo[i].y - 2, LAB_WIDTH * 5 / 4, LAB_HEIGHT * 5 / 4);
                    }
                    bigAmmo[i].x = SCR_WIDTH + 1000;
                    bigAmmo[i].y = SCR_HEIGHT + 1000;
                }
            }
        }


        for (int j = 0; j < enemies.length - enemyBlocker; j++) {
            if (MathUtils.random(1, 3) == 1) {
                batch.draw(ShockWaveImg, shockWave.x, shockWave.y, shockWave.width, shockWave.height);
            } else if (MathUtils.random(1, 3) == 3) {
                batch.draw(ShockWaveImg2, shockWave.x, shockWave.y, shockWave.width, shockWave.height);
            } else {
                batch.draw(ShockWaveImg3, shockWave.x, shockWave.y, shockWave.width, shockWave.height);
            }

            if (bossShield.durability > 0) {
                if (MathUtils.random(1, 3) == 1) {
                    batch.draw(ShieldImg, bossShield.x, bossShield.y, bossShield.width, bossShield.height);
                } else if (MathUtils.random(1, 3) == 3) {
                    batch.draw(ShieldImg2, bossShield.x, bossShield.y, bossShield.width, bossShield.height);
                } else {
                    batch.draw(ShieldImg3, bossShield.x, bossShield.y, bossShield.width, bossShield.height);
                }

            }
            for (int i = 0; i < enemies[j].enemyBasicAmmoCounter; i++) {
                if (enemyBasicAmmo[i].sideCounter == 1) {
                    batch.draw(EnemyBasicAmmoLeftXImg, enemyBasicAmmo[i].x, enemyBasicAmmo[i].y, enemyBasicAmmo[i].width, enemyBasicAmmo[i].height);
                }
                if (enemyBasicAmmo[i].sideCounter == 2) {
                    batch.draw(EnemyBasicAmmoRightXImg, enemyBasicAmmo[i].x, enemyBasicAmmo[i].y, enemyBasicAmmo[i].width, enemyBasicAmmo[i].height);
                }
                if (enemyBasicAmmo[i].sideCounter == 3 | enemyBasicAmmo[i].sideCounter == 0) {
                    batch.draw(EnemyBasicAmmoUpYImg, enemyBasicAmmo[i].x, enemyBasicAmmo[i].y, enemyBasicAmmo[i].width, enemyBasicAmmo[i].height);
                }
                if (enemyBasicAmmo[i].sideCounter == 4) {
                    batch.draw(EnemyBasicAmmoDownYImg, enemyBasicAmmo[i].x, enemyBasicAmmo[i].y, enemyBasicAmmo[i].width, enemyBasicAmmo[i].height);
                }

                if (enemyBasicAmmo[i].hit) {
                     ExplosionSound.play(StickyTanks.masterVolume / 300);
                    enemyBasicAmmo[i].hit = false;
                    if (enemyBasicAmmo[i].sideCounter == 1) {
                        if (MathUtils.random(1, 3) == 1) {
                            batch.draw(Explosion1Img, enemyBasicAmmo[i].x - 13, enemyBasicAmmo[i].y - 3, LAB_WIDTH, LAB_HEIGHT);
                        } else if (MathUtils.random(1, 3) == 3) {
                            batch.draw(Explosion2Img, enemyBasicAmmo[i].x - 13, enemyBasicAmmo[i].y - 3, LAB_WIDTH, LAB_HEIGHT);
                        } else {
                            batch.draw(Explosion3Img, enemyBasicAmmo[i].x - 13, enemyBasicAmmo[i].y - 3, LAB_WIDTH, LAB_HEIGHT);
                        }
                        enemyBasicAmmo[i].x = SCR_WIDTH + 1000;
                        enemyBasicAmmo[i].y = SCR_HEIGHT + 1000;
                    }
                    if (enemyBasicAmmo[i].sideCounter == 2) {
                        if (MathUtils.random(1, 3) == 1) {
                            batch.draw(Explosion1Img, enemyBasicAmmo[i].x - 10, enemyBasicAmmo[i].y - 3, LAB_WIDTH, LAB_HEIGHT);
                        } else if (MathUtils.random(1, 3) == 3) {
                            batch.draw(Explosion2Img, enemyBasicAmmo[i].x - 10, enemyBasicAmmo[i].y - 3, LAB_WIDTH, LAB_HEIGHT);
                        } else {
                            batch.draw(Explosion3Img, enemyBasicAmmo[i].x - 10, enemyBasicAmmo[i].y - 3, LAB_WIDTH, LAB_HEIGHT);
                        }
                        enemyBasicAmmo[i].x = SCR_WIDTH + 1000;
                        enemyBasicAmmo[i].y = SCR_HEIGHT + 1000;
                    }
                    if (enemyBasicAmmo[i].sideCounter == 3) {
                        if (MathUtils.random(1, 3) == 1) {
                            batch.draw(Explosion1Img, enemyBasicAmmo[i].x - 10, enemyBasicAmmo[i].y - 2, LAB_WIDTH, LAB_HEIGHT);
                        } else if (MathUtils.random(1, 3) == 3) {
                            batch.draw(Explosion2Img, enemyBasicAmmo[i].x - 10, enemyBasicAmmo[i].y - 2, LAB_WIDTH, LAB_HEIGHT);
                        } else {
                            batch.draw(Explosion3Img, enemyBasicAmmo[i].x - 10, enemyBasicAmmo[i].y - 2, LAB_WIDTH, LAB_HEIGHT);
                        }
                        enemyBasicAmmo[i].x = SCR_WIDTH + 1000;
                        enemyBasicAmmo[i].y = SCR_HEIGHT + 1000;
                    }
                    if (enemyBasicAmmo[i].sideCounter == 4) {
                        if (MathUtils.random(1, 3) == 1) {
                            batch.draw(Explosion1Img, enemyBasicAmmo[i].x - 10, enemyBasicAmmo[i].y - 2, LAB_WIDTH, LAB_HEIGHT);
                        } else if (MathUtils.random(1, 3) == 3) {
                            batch.draw(Explosion2Img, enemyBasicAmmo[i].x - 10, enemyBasicAmmo[i].y - 2, LAB_WIDTH, LAB_HEIGHT);
                        } else {
                            batch.draw(Explosion3Img, enemyBasicAmmo[i].x - 10, enemyBasicAmmo[i].y - 2, LAB_WIDTH, LAB_HEIGHT);
                        }
                        enemyBasicAmmo[i].x = SCR_WIDTH + 1000;
                        enemyBasicAmmo[i].y = SCR_HEIGHT + 1000;
                    }

                }
            }

            for (int i = 0; i < enemies[j].enemyMachinegunAmmoCounter; i++) {
                if (enemyMachinegunAmmo[i].sideCounter == 1) {
                    batch.draw(EnemyMachineGunAmmoLeftXImg, enemyMachinegunAmmo[i].x, enemyMachinegunAmmo[i].y, enemyMachinegunAmmo[i].width, enemyMachinegunAmmo[i].height);
                }
                if (enemyMachinegunAmmo[i].sideCounter == 2) {
                    batch.draw(EnemyMachineGunAmmoRightXImg, enemyMachinegunAmmo[i].x, enemyMachinegunAmmo[i].y, enemyMachinegunAmmo[i].width, enemyMachinegunAmmo[i].height);
                }
                if (enemyMachinegunAmmo[i].sideCounter == 3 | enemyMachinegunAmmo[i].sideCounter == 0) {
                    batch.draw(EnemyMachineGunAmmoUpYImg, enemyMachinegunAmmo[i].x, enemyMachinegunAmmo[i].y, enemyMachinegunAmmo[i].width, enemyMachinegunAmmo[i].height);
                }
                if (enemyMachinegunAmmo[i].sideCounter == 4) {
                    batch.draw(EnemyMachineGunAmmoDownYImg, enemyMachinegunAmmo[i].x, enemyMachinegunAmmo[i].y, enemyMachinegunAmmo[i].width, enemyMachinegunAmmo[i].height);
                }

                if (enemyMachinegunAmmo[i].hit) {
                    MashineGunExplosionSound.play(StickyTanks.masterVolume / 300);
                    enemyMachinegunAmmo[i].hit = false;
                    if (enemyMachinegunAmmo[i].sideCounter == 1) {
                        if (MathUtils.random(1, 3) == 1) {
                            batch.draw(Explosion1Img, enemyMachinegunAmmo[i].x - 10, enemyMachinegunAmmo[i].y - 3, LAB_WIDTH * 3 / 4, LAB_HEIGHT * 3 / 4);
                        } else if (MathUtils.random(1, 3) == 3) {
                            batch.draw(Explosion2Img, enemyMachinegunAmmo[i].x - 10, enemyMachinegunAmmo[i].y - 3, LAB_WIDTH * 3 / 4, LAB_HEIGHT * 3 / 4);
                        } else {
                            batch.draw(Explosion3Img, enemyMachinegunAmmo[i].x - 10, enemyMachinegunAmmo[i].y - 3, LAB_WIDTH * 3 / 4, LAB_HEIGHT * 3 / 4);
                        }
                        enemyMachinegunAmmo[i].x = SCR_WIDTH + 1000;
                        enemyMachinegunAmmo[i].y = SCR_HEIGHT + 1000;
                    }
                    if (enemyMachinegunAmmo[i].sideCounter == 2) {
                        if (MathUtils.random(1, 3) == 1) {
                            batch.draw(Explosion1Img, enemyMachinegunAmmo[i].x - 10, enemyMachinegunAmmo[i].y - 3, LAB_WIDTH * 3 / 4, LAB_HEIGHT * 3 / 4);
                        } else if (MathUtils.random(1, 3) == 3) {
                            batch.draw(Explosion2Img, enemyMachinegunAmmo[i].x - 10, enemyMachinegunAmmo[i].y - 3, LAB_WIDTH * 3 / 4, LAB_HEIGHT * 3 / 4);
                        } else {
                            batch.draw(Explosion3Img, enemyMachinegunAmmo[i].x - 10, enemyMachinegunAmmo[i].y - 3, LAB_WIDTH * 3 / 4, LAB_HEIGHT * 3 / 4);
                        }
                        enemyMachinegunAmmo[i].x = SCR_WIDTH + 1000;
                        enemyMachinegunAmmo[i].y = SCR_HEIGHT + 1000;
                    }
                    if (enemyMachinegunAmmo[i].sideCounter == 3) {
                        if (MathUtils.random(1, 3) == 1) {
                            batch.draw(Explosion1Img, enemyMachinegunAmmo[i].x - 10, enemyMachinegunAmmo[i].y + 2, LAB_WIDTH * 3 / 4, LAB_HEIGHT * 3 / 4);
                        } else if (MathUtils.random(1, 3) == 3) {
                            batch.draw(Explosion2Img, enemyMachinegunAmmo[i].x - 10, enemyMachinegunAmmo[i].y + 2, LAB_WIDTH * 3 / 4, LAB_HEIGHT * 3 / 4);
                        } else {
                            batch.draw(Explosion3Img, enemyMachinegunAmmo[i].x - 10, enemyMachinegunAmmo[i].y + 2, LAB_WIDTH * 3 / 4, LAB_HEIGHT * 3 / 4);
                        }
                        enemyMachinegunAmmo[i].x = SCR_WIDTH + 1000;
                        enemyMachinegunAmmo[i].y = SCR_HEIGHT + 1000;
                    }
                    if (enemyMachinegunAmmo[i].sideCounter == 4) {
                        if (MathUtils.random(1, 3) == 1) {
                            batch.draw(Explosion1Img, enemyMachinegunAmmo[i].x - 10, enemyMachinegunAmmo[i].y - 4, LAB_WIDTH * 3 / 4, LAB_HEIGHT * 3 / 4);
                        } else if (MathUtils.random(1, 3) == 3) {
                            batch.draw(Explosion2Img, enemyMachinegunAmmo[i].x - 10, enemyMachinegunAmmo[i].y - 4, LAB_WIDTH * 3 / 4, LAB_HEIGHT * 3 / 4);
                        } else {
                            batch.draw(Explosion3Img, enemyMachinegunAmmo[i].x - 10, enemyMachinegunAmmo[i].y - 4, LAB_WIDTH * 3 / 4, LAB_HEIGHT * 3 / 4);
                        }
                        enemyMachinegunAmmo[i].x = SCR_WIDTH + 1000;
                        enemyMachinegunAmmo[i].y = SCR_HEIGHT + 1000;
                    }
                }
            }

            for (int i = 0; i < enemies[j].enemyBigAmmoCounter; i++) {
                if (enemyBigAmmo[i].sideCounter == 1) {
                    batch.draw(EnemyBigAmmoLeftXImg, enemyBigAmmo[i].x, enemyBigAmmo[i].y, enemyBigAmmo[i].width, enemyBigAmmo[i].height);
                }
                if (enemyBigAmmo[i].sideCounter == 2) {
                    batch.draw(EnemyBigAmmoRightXImg, enemyBigAmmo[i].x, enemyBigAmmo[i].y, enemyBigAmmo[i].width, enemyBigAmmo[i].height);
                }
                if (enemyBigAmmo[i].sideCounter == 3 | enemyBigAmmo[i].sideCounter == 0) {
                    batch.draw(EnemyBigAmmoUpYImg, enemyBigAmmo[i].x, enemyBigAmmo[i].y, enemyBigAmmo[i].width, enemyBigAmmo[i].height);
                }
                if (enemyBigAmmo[i].sideCounter == 4) {
                    batch.draw(EnemyBigAmmoDownYImg, enemyBigAmmo[i].x, enemyBigAmmo[i].y, enemyBigAmmo[i].width, enemyBigAmmo[i].height);
                }

                if (enemyBigAmmo[i].hit) {
                    ExplosionSound.play(StickyTanks.masterVolume / 300);
                    enemyBigAmmo[i].hit = false;
                    if (enemyBigAmmo[i].sideCounter == 1) {
                        if (MathUtils.random(1, 3) == 1) {
                            batch.draw(Explosion1Img, enemyBigAmmo[i].x - 13, enemyBigAmmo[i].y - 3, LAB_WIDTH * 5 / 4, LAB_HEIGHT * 5 / 4);
                        } else if (MathUtils.random(1, 3) == 3) {
                            batch.draw(Explosion2Img, enemyBigAmmo[i].x - 13, enemyBigAmmo[i].y - 3, LAB_WIDTH * 5 / 4, LAB_HEIGHT * 5 / 4);
                        } else {
                            batch.draw(Explosion3Img, enemyBigAmmo[i].x - 13, enemyBigAmmo[i].y - 3, LAB_WIDTH * 5 / 4, LAB_HEIGHT * 5 / 4);
                        }
                        enemyBigAmmo[i].x = SCR_WIDTH + 1000;
                        enemyBigAmmo[i].y = SCR_HEIGHT + 1000;
                    }
                    if (enemyBigAmmo[i].sideCounter == 2) {
                        if (MathUtils.random(1, 3) == 1) {
                            batch.draw(Explosion1Img, enemyBigAmmo[i].x - 10, enemyBigAmmo[i].y - 3, LAB_WIDTH * 5 / 4, LAB_HEIGHT * 5 / 4);
                        } else if (MathUtils.random(1, 3) == 3) {
                            batch.draw(Explosion2Img, enemyBigAmmo[i].x - 10, enemyBigAmmo[i].y - 3, LAB_WIDTH * 5 / 4, LAB_HEIGHT * 5 / 4);
                        } else {
                            batch.draw(Explosion3Img, enemyBigAmmo[i].x - 10, enemyBigAmmo[i].y - 3, LAB_WIDTH * 5 / 4, LAB_HEIGHT * 5 / 4);
                        }
                        enemyBigAmmo[i].x = SCR_WIDTH + 1000;
                        enemyBigAmmo[i].y = SCR_HEIGHT + 1000;
                    }
                    if (enemyBigAmmo[i].sideCounter == 3) {
                        if (MathUtils.random(1, 3) == 1) {
                            batch.draw(Explosion1Img, enemyBigAmmo[i].x - 10, enemyBigAmmo[i].y - 2, LAB_WIDTH * 5 / 4, LAB_HEIGHT * 5 / 4);
                        } else if (MathUtils.random(1, 3) == 3) {
                            batch.draw(Explosion2Img, enemyBigAmmo[i].x - 10, enemyBigAmmo[i].y - 2, LAB_WIDTH * 5 / 4, LAB_HEIGHT * 5 / 4);
                        } else {
                            batch.draw(Explosion3Img, enemyBigAmmo[i].x - 10, enemyBigAmmo[i].y - 2, LAB_WIDTH * 5 / 4, LAB_HEIGHT * 5 / 4);
                        }
                        enemyBigAmmo[i].x = SCR_WIDTH + 1000;
                        enemyBigAmmo[i].y = SCR_HEIGHT + 1000;
                    }
                    if (enemyBigAmmo[i].sideCounter == 4) {
                        if (MathUtils.random(1, 3) == 1) {
                            batch.draw(Explosion1Img, enemyBigAmmo[i].x - 10, enemyBigAmmo[i].y - 2, LAB_WIDTH * 5 / 4, LAB_HEIGHT * 5 / 4);
                        } else if (MathUtils.random(1, 3) == 3) {
                            batch.draw(Explosion2Img, enemyBigAmmo[i].x - 10, enemyBigAmmo[i].y - 2, LAB_WIDTH * 5 / 4, LAB_HEIGHT * 5 / 4);
                        } else {
                            batch.draw(Explosion3Img, enemyBigAmmo[i].x - 10, enemyBigAmmo[i].y - 2, LAB_WIDTH * 5 / 4, LAB_HEIGHT * 5 / 4);
                        }
                        enemyBigAmmo[i].x = SCR_WIDTH + 1000;
                        enemyBigAmmo[i].y = SCR_HEIGHT + 1000;
                    }
                }
            }


        }
        // System.out.println(tank.hitpoints);
        for (int j = 0; j < wallsCounter; j++) {
            if (walls[j].durability <= 0) {
                if (walls[j].durability < 0) {
                    walls[j].durability = 0;
                }
                walls[j].x = SCR_WIDTH + 100000;
                walls[j].y = SCR_HEIGHT + 100000;
            }
            batch.draw(BrickWallImg, walls[j].x, walls[j].y, walls[j].width, walls[j].height);
        }
        for (int i = 0; i < borderwallsCounter; i++) {
            batch.draw(SteelWallImg, borderwalls[i].x, borderwalls[i].y, borderwalls[i].width, borderwalls[i].height);
        }
        if (tank.hitpoints <= 0) {
            tank.hitpoints = 0;
            batch.draw(BigExplosionImg, tank.realX - 20, tank.realY - 20, LAB_WIDTH * 2, LAB_HEIGHT * 2);
            if (sideCounter == 1) {
                batch.draw(DestroyedTankLeftXImg, tank.realX, tank.realY, tank.width, tank.height);
            }
            if (sideCounter == 2) {
                batch.draw(DestroyedTankRightXImg, tank.realX, tank.realY, tank.width, tank.height);
            }
            if (sideCounter == 3) {
                batch.draw(DestroyedTankUpYImg, tank.realX, tank.realY, tank.width, tank.height);
            }
            if (sideCounter == 4) {
                batch.draw(DestroyedTankDownYImg, tank.realX, tank.realY, tank.width, tank.height);
            }
            if (!isGameOver) {
                TankExplosionSound.play(StickyTanks.masterVolume / 300);
            }
            isGameOver = true;
            tank.realX = SCR_WIDTH + 100;
            tank.realY = SCR_HEIGHT + 100;
            isPlayerWin = false;
            savePlayerData();
            score = 0;
            btnYouLose.y = SCR_HEIGHT * 65 / 100;
            btnYouLose.x = SCR_WIDTH * 43 / 100;
            btnRetry.y = btnYouLose.y - 50;
            btnMainMenu.y = btnRetry.y - 50;
            btnRetry.x = SCR_WIDTH * 45 / 100;
            btnMainMenu.x = SCR_WIDTH * 42 / 100;

            btnYouLose.textureY = btnYouLose.y * 94 / 100;
            btnYouLose.textureX = btnYouLose.x * 91 / 100;

            btnRetry.textureY = btnRetry.y * 93 / 100;
            btnRetry.textureX = btnRetry.x * 95 / 100;

            btnMainMenu.textureY = btnMainMenu.y * 92 / 100;
            btnMainMenu.textureX = btnMainMenu.x * 91 / 100;

            //batch.draw(ButtonBackImg, btnYouLose.textureX, btnYouLose.textureY, btnYouLose.textureWidth, btnYouLose.textureHeight);
            btnYouLose.font.draw(batch, com.mygdx.game.StickyTanks.name+" lose", btnYouLose.x - SCR_WIDTH * 3 / 100, btnYouLose.y + SCR_HEIGHT / 50);

            batch.draw(ButtonBackImg, btnRetry.textureX, btnRetry.textureY, btnRetry.textureWidth, btnRetry.textureHeight);
            btnRetry.font.draw(batch, "Retry", btnRetry.x, btnRetry.y + SCR_HEIGHT / 50);

            batch.draw(ButtonBackImg, btnMainMenu.textureX, btnMainMenu.textureY, btnMainMenu.textureWidth, btnMainMenu.textureHeight);
            btnMainMenu.font.draw(batch, "Main menu", btnMainMenu.x, btnMainMenu.y + SCR_HEIGHT / 50);
        }

        if (sideCounter == 1) {
            batch.draw(PlayerLeftXImg, tank.realX, tank.realY, tank.width, tank.height);
            lastSideCounter = 1;
        }
        if (sideCounter == 2) {
            batch.draw(PlayerRightXImg, tank.realX, tank.realY, tank.width, tank.height);
            lastSideCounter = 2;
        }
        if (sideCounter == 3 | sideCounter == 0) {
            batch.draw(PlayerUpYImg, tank.realX, tank.realY, tank.width, tank.height);
            lastSideCounter = 3;
        }
        if (sideCounter == 4) {
            batch.draw(PlayerDownYImg, tank.realX, tank.realY, tank.width, tank.height);
            lastSideCounter = 4;
        }
        for (int i = 0; i < enemies.length - enemyBlocker; i++) {
            if (enemies[i].isDestroyed) {
                isGameOverKey++;
            }
            if (isGameOverKey == enemies.length - enemyBlocker) {
                isGameOver = true;
                isPlayerWin = true;
                if (gameStage == 0) {
                    savePlayerData();
                    score = 0;
                    //btnMainMenu.y = SCR_WIDTH+50;
                    btnYouWin.y = SCR_HEIGHT * 65 / 100;
                    btnYouWin.textureY = btnYouWin.y * 935 / 1000;
                    btnYouWin.textureX = (SCR_WIDTH * 45 / 100) * 94 / 100;

                    btnMainMenu.y = btnYouWin.y - 50;
                    btnMainMenu.textureY = btnMainMenu.y * 93 / 100;
                    btnMainMenu.x = SCR_WIDTH * 43 / 100;
                    btnMainMenu.textureX = btnMainMenu.x * 92 / 100;


                    // batch.draw(ButtonBackImg, btnYouWin.textureX, btnYouWin.textureY, btnYouWin.textureWidth, btnYouWin.textureHeight);
                    btnYouWin.font.draw(batch, com.mygdx.game.StickyTanks.name+" win", SCR_WIDTH * 42 / 100, btnYouWin.y + SCR_HEIGHT / 50);

                    batch.draw(ButtonBackImg, btnMainMenu.textureX, btnMainMenu.textureY, btnMainMenu.textureWidth, btnMainMenu.textureHeight);
                    btnMainMenu.font.draw(batch, "Main menu", btnMainMenu.x, btnMainMenu.y + SCR_HEIGHT / 50);
                } else {
                    btnNextStage.x = SCR_WIDTH * 43 / 100;
                    btnNextStage.textureX = btnNextStage.x * 87 / 100;
                    btnNextStage.y = SCR_HEIGHT * 65 / 100;
                    btnNextStage.textureY = btnNextStage.y * 94 / 100;

                    batch.draw(ButtonBackImg, btnNextStage.textureX, btnNextStage.textureY, btnNextStage.textureWidth, btnNextStage.textureHeight);
                    btnNextStage.font.draw(batch, "Next stage", SCR_WIDTH * 42 / 100-SCR_HEIGHT/50, btnNextStage.y + SCR_HEIGHT / 50);
                }
            }
        }
        isGameOverKey = 0;
        for (int i = 0; i < enemies.length - enemyBlocker; i++) {
            if (enemies[i].hitpoints <= 0) {
                if (!enemies[i].isDestroyed) {
                    if (enemies[i].ID == 1) {
                        score = score + 10;
                    }
                    if (enemies[i].ID == 2) {
                        score = score + 15;
                    }
                    if (enemies[i].ID == 3) {
                        score = score + 20;
                    }
                    batch.draw(BigExplosionImg, enemies[i].realX - 20, enemies[i].realY - 20, LAB_WIDTH * 2, LAB_HEIGHT * 2);
                    TankExplosionSound.play(StickyTanks.masterVolume / 300);
                }
                enemies[i].isDestroyed = true;
                //   enemies[i].realX += SCR_WIDTH;
                //   enemies[i].realY += SCR_HEIGHT;
            }
            if (enemies[i].sideCounter == 1) {
                if (enemies[i].isDestroyed) {
                    batch.draw(DestroyedEnemyTankLeftXImg, enemies[i].realX, enemies[i].realY, enemies[i].width, enemies[i].height);
                } else if (visualGameStage != 4) {
                    batch.draw(EnemyLeftXImg, enemies[i].realX, enemies[i].realY, enemies[i].width, enemies[i].height);
                } else
                    batch.draw(BossLeftXImg, enemies[i].realX, enemies[i].realY, enemies[i].width, enemies[i].height);
            }

            if (enemies[i].sideCounter == 2) {
                if (enemies[i].isDestroyed) {
                    batch.draw(DestroyedEnemyTankRightXImg, enemies[i].realX, enemies[i].realY, enemies[i].width, enemies[i].height);
                } else if (visualGameStage != 4) {
                    batch.draw(EnemyRightXImg, enemies[i].realX, enemies[i].realY, enemies[i].width, enemies[i].height);
                } else
                    batch.draw(BossRightXImg, enemies[i].realX, enemies[i].realY, enemies[i].width, enemies[i].height);
            }

            if (enemies[i].sideCounter == 3 | enemies[i].sideCounter == 0) {
                if (enemies[i].isDestroyed) {
                    batch.draw(DestroyedEnemyTankUpYImg, enemies[i].realX, enemies[i].realY, enemies[i].width, enemies[i].height);
                } else if (visualGameStage != 4) {
                    batch.draw(EnemyUpYImg, enemies[i].realX, enemies[i].realY, enemies[i].width, enemies[i].height);
                } else
                    batch.draw(BossUpYImg, enemies[i].realX, enemies[i].realY, enemies[i].width, enemies[i].height);
            }
            if (enemies[i].sideCounter == 4) {
                if (enemies[i].isDestroyed) {
                    batch.draw(DestroyedEnemyTankDownYImg, enemies[i].realX, enemies[i].realY, enemies[i].width, enemies[i].height);
                } else if (visualGameStage != 4) {
                    batch.draw(EnemyDownYImg, enemies[i].realX, enemies[i].realY, enemies[i].width, enemies[i].height);
                } else
                    batch.draw(BossDownYImg, enemies[i].realX, enemies[i].realY, enemies[i].width, enemies[i].height);
            }

        }
        // batch.draw(BossImg, 0, SCR_HEIGHT-200, SCR_WIDTH, HEIGHT*5);
        //оформление
        batch.draw(DurabilityBackImg, WIDTH, 0, WIDTH * 9 / 2, HEIGHT * 4);

        for (int i = 0; i < 3; i++) {
            batch.draw(ItemFrameImg, 2 * WIDTH * (i + 3), 0, WIDTH * 2, HEIGHT * 2);
            if (StickyTanks.TankType == 1) {
                if (i == 0) {
                    batch.draw(BasicProjectileUpYImg, 6 * WIDTH + WIDTH / 2, HEIGHT / 2, WIDTH, HEIGHT);
                }
                if (i == 1) {
                    //batch.draw(BasicProjectileUpYImg, 2 * WIDTH * (i + 3) + WIDTH / 2, HEIGHT / 2, WIDTH, HEIGHT);
                    batch.draw(MashineGunAmmoPackImg, 8 * WIDTH + WIDTH / 2, HEIGHT / 2, WIDTH, HEIGHT);
                }
                if (i == 2) {
                    //batch.draw(BasicProjectileUpYImg, 2 * WIDTH * (i + 3) + WIDTH / 2, HEIGHT / 2, WIDTH, HEIGHT);
                    batch.draw(BigAmmoUpYImg, 10 * WIDTH + WIDTH * 5 / 14, HEIGHT / 3, WIDTH * 5 / 4, HEIGHT * 5 / 4);
                }
            }
            if (StickyTanks.TankType == 2) {
                if (i == 0) {
                    batch.draw(BasicRocketUpYImg, 6 * WIDTH + WIDTH / 2, HEIGHT / 2, WIDTH, HEIGHT);
                }
                if (i == 1) {
                    //batch.draw(BasicProjectileUpYImg, 2 * WIDTH * (i + 3) + WIDTH / 2, HEIGHT / 2, WIDTH, HEIGHT);
                    batch.draw(BigRocketUpYImg, 8 * WIDTH + WIDTH / 2, HEIGHT / 2, WIDTH, HEIGHT);
                }
                if (i == 2) {
                    //batch.draw(BasicProjectileUpYImg, 2 * WIDTH * (i + 3) + WIDTH / 2, HEIGHT / 2, WIDTH, HEIGHT);
                    batch.draw(NapalmRocketUpYImg, 10 * WIDTH + WIDTH * 5 / 14, HEIGHT / 3, WIDTH * 5 / 4, HEIGHT * 5 / 4);
                }
            }
            if (StickyTanks.TankType == 3) {
                if (i == 0) {
                    batch.draw(BasicBombImg, 6 * WIDTH + WIDTH / 2, HEIGHT / 2, WIDTH, HEIGHT);
                }
                if (i == 1) {
                    //batch.draw(BasicProjectileUpYImg, 2 * WIDTH * (i + 3) + WIDTH / 2, HEIGHT / 2, WIDTH, HEIGHT);
                    batch.draw(ClockBombImg, 8 * WIDTH + WIDTH / 2, HEIGHT / 2, WIDTH, HEIGHT);
                }
                if (i == 2) {
                    //batch.draw(BasicProjectileUpYImg, 2 * WIDTH * (i + 3) + WIDTH / 2, HEIGHT / 2, WIDTH, HEIGHT);
                    batch.draw(HedgehogImg, 10 * WIDTH + WIDTH * 5 / 14, HEIGHT / 3, WIDTH * 5 / 4, HEIGHT * 5 / 4);
                }
            }


        }
        if (onPause) {
            btnTargetAmmo1.x = SCR_WIDTH + 100;
            btnTargetAmmo2.x = SCR_WIDTH + 100;
            btnTargetAmmo3.x = SCR_WIDTH + 100;

        } else {
            btnTargetAmmo1.x = 2 * WIDTH * 3;
            btnTargetAmmo2.x = 2 * WIDTH * 4;
            btnTargetAmmo3.x = 2 * WIDTH * 5;
        }
        if (clickChecker == 1) {
            batch.draw(TargetedAmmoImg, 2 * WIDTH * 3, btnTargetAmmo1.y, btnTargetAmmo1.width, btnTargetAmmo1.height);
        } else if (clickChecker == 2) {
            batch.draw(TargetedAmmoImg, 2 * WIDTH * 4, btnTargetAmmo2.y, btnTargetAmmo2.width, btnTargetAmmo2.height);
        } else if (clickChecker == 3) {
            batch.draw(TargetedAmmoImg, 2 * WIDTH * 5, btnTargetAmmo3.y, btnTargetAmmo3.width, btnTargetAmmo3.height);
        }
        if (btnTargetAmmo1.click(touch.x, touch.y)) {
            if (clickChecker != 1) {
                ButtonClickSound.play(StickyTanks.masterVolume / 100);
            }
            clickChecker = 1;
            tank.reloading = 0;
        }
        if (btnTargetAmmo2.click(touch.x, touch.y)) {
            if (clickChecker != 2) {
                ButtonClickSound.play(StickyTanks.masterVolume / 100);
            }
            clickChecker = 2;
            tank.reloading = 0;
        }
        if (btnTargetAmmo3.click(touch.x, touch.y)) {
            if (clickChecker != 3) {
                ButtonClickSound.play(StickyTanks.masterVolume / 100);
            }
            clickChecker = 3;
            tank.reloading = 0;
        }


        font.draw(batch, "" + tank.hitpoints, WIDTH * 9 / 4, HEIGHT * 7 / 8 + +SCR_HEIGHT / 50);
        font.draw(batch, "Stage " + visualGameStage, SCR_WIDTH * 9 / 20, SCR_HEIGHT - HEIGHT * 4 / 27);
        font.draw(batch, "Score: " + score, WIDTH / 2, SCR_HEIGHT * 99 / 100);
        if (visualGameStage != 4) {
            for (int i = 0; i < enemies.length - enemyBlocker; i++) {
                if (!enemies[i].isDestroyed) {
                    drawingEnemiesCounter++;
                    batch.draw(EnemyUpYImg, 18 * WIDTH + WIDTH * 6 / 5 * drawingEnemiesCounter, HEIGHT / 2, WIDTH, HEIGHT);
                }
            }
            font.draw(batch, "Enemies: " + drawingEnemiesCounter, 13 * WIDTH + WIDTH / 2, HEIGHT * 5 / 4 + +SCR_HEIGHT / 50);
            drawingEnemiesCounter = 0;
        } else if (bossShield.durability > 0) {
            batch.draw(ButtonBackImg, 13 * WIDTH, 0, WIDTH * 2, HEIGHT * 2);
            batch.draw(ButtonBackImg, 15 * WIDTH, 0, WIDTH * 2, HEIGHT * 2);
            batch.draw(ButtonBackImg, 17 * WIDTH, 0, WIDTH * 2, HEIGHT * 2);
            batch.draw(ButtonBackImg, 19 * WIDTH, 0, WIDTH * 2, HEIGHT * 2);
            batch.draw(ButtonBackImg, 21 * WIDTH, 0, WIDTH * 2, HEIGHT * 2);
            batch.draw(ButtonBackImg, 23 * WIDTH, 0, WIDTH * 2, HEIGHT * 2);
            batch.draw(ButtonBackImg, 25 * WIDTH, 0, WIDTH * 2, HEIGHT * 2);
            batch.draw(ButtonBackImg, 27 * WIDTH, 0, WIDTH * 2, HEIGHT * 2);
            if (networkSignal == 0) {
                batch.draw(Network0lvlImg, 13 * WIDTH, HEIGHT * 1 / 14, WIDTH * 2, HEIGHT * 2);
            }
            if (networkSignal == 1) {
                batch.draw(Network1lvlImg, 13 * WIDTH, HEIGHT * 1 / 14, WIDTH * 2, HEIGHT * 2);
            }
            if (networkSignal == 2) {
                batch.draw(Network2lvlImg, 13 * WIDTH, HEIGHT * 1 / 14, WIDTH * 2, HEIGHT * 2);
            }
            if (networkSignal == 3) {
                batch.draw(Network3lvlImg, 13 * WIDTH, HEIGHT * 1 / 14, WIDTH * 2, HEIGHT * 2);
            }
            if (orbitalStrikeCoolDown < 20) {
                batch.draw(NetworkCounter010Img, (16 * WIDTH) - (WIDTH / 2), 0, WIDTH * 3, HEIGHT * 2.5f);
            }
            if (orbitalStrikeCoolDown >= 20 & orbitalStrikeCoolDown < 40) {
                batch.draw(NetworkCounter1020Img, (16 * WIDTH) - (WIDTH / 2), HEIGHT * 1 / 100, WIDTH * 3, HEIGHT * 2.5f);
            }
            if (orbitalStrikeCoolDown >= 40 & orbitalStrikeCoolDown < 60) {
                batch.draw(NetworkCounter2040Img, (16 * WIDTH) - (WIDTH / 2), HEIGHT * 1 / 100, WIDTH * 3, HEIGHT * 2.5f);
            }
            if (orbitalStrikeCoolDown >= 60 & orbitalStrikeCoolDown < 80) {
                batch.draw(NetworkCounter4060Img, (16 * WIDTH) - (WIDTH / 2), HEIGHT * 1 / 100, WIDTH * 3, HEIGHT * 2.5f);
            }
            if (orbitalStrikeCoolDown >= 80 & orbitalStrikeCoolDown < 100) {
                batch.draw(NetworkCounter6080Img, (16 * WIDTH) - (WIDTH / 2), HEIGHT * 1 / 100, WIDTH * 3, HEIGHT * 2.5f);
            }
            if (orbitalStrikeCoolDown >= 100) {
                batch.draw(NetworkCounter80100Img, (16 * WIDTH) - (WIDTH / 2), HEIGHT * 1 / 100, WIDTH * 3, HEIGHT * 2.5f);
                StickyTanks.fontForMonitor.draw(batch, "Ready to fire", 19 * WIDTH + WIDTH / 2, HEIGHT * 1.5f);
            } else {
                if (networkSignal > 0) {
                    StickyTanks.fontForMonitor.draw(batch, "Taking target dispose...", 19 * WIDTH + WIDTH / 2, HEIGHT * 1.5f);
                } else {
                    StickyTanks.fontForMonitor.draw(batch, "No signal...", 19 * WIDTH + WIDTH / 2, HEIGHT * 1.5f);
                }
            }
            if (orbitalStrike.hit) {
                batch.draw(OrbitalStrikeMonitorImg, SCR_WIDTH - 3 * WIDTH, 0, WIDTH * 2, HEIGHT * 2);
            } else {
                batch.draw(OrbitalStrikeMonitorNonActiveImg, SCR_WIDTH - 3 * WIDTH, 0, WIDTH * 2, HEIGHT * 2);
            }

        } else {
            batch.draw(ButtonBackImg, 13 * WIDTH, 0, WIDTH * 2, HEIGHT * 2);
            batch.draw(ButtonBackImg, 15 * WIDTH, 0, WIDTH * 2, HEIGHT * 2);
            batch.draw(ButtonBackImg, 17 * WIDTH, 0, WIDTH * 2, HEIGHT * 2);
            batch.draw(ButtonBackImg, 19 * WIDTH, 0, WIDTH * 2, HEIGHT * 2);
            batch.draw(ButtonBackImg, 21 * WIDTH, 0, WIDTH * 2, HEIGHT * 2);
            batch.draw(ButtonBackImg, 23 * WIDTH, 0, WIDTH * 2, HEIGHT * 2);
            batch.draw(ButtonBackImg, 25 * WIDTH, 0, WIDTH * 2, HEIGHT * 2);
            batch.draw(ButtonBackImg, 27 * WIDTH, 0, WIDTH * 2, HEIGHT * 2);
            StickyTanks.fontForMonitor.draw(batch, "NO SIGNAL...", 19 * WIDTH + WIDTH / 2, HEIGHT * 1.5f);
            if (orbitalStrike.hit) {
                batch.draw(OrbitalStrikeMonitorImg, SCR_WIDTH - 3 * WIDTH, 0, WIDTH * 2, HEIGHT * 2);
            } else {
                batch.draw(OrbitalStrikeMonitorNonActiveImg, SCR_WIDTH - 3 * WIDTH, 0, WIDTH * 2, HEIGHT * 2);
            }
            int a = 0;
            int b = 0;
            if (!onPause) {
                a = MathUtils.random(0, 3);
                b = MathUtils.random(0, 5);
            }
            if (a == 0) {
                batch.draw(Network0lvlImg, 13 * WIDTH, HEIGHT * 1 / 14, WIDTH * 2, HEIGHT * 2);
            }
            if (a == 1) {
                batch.draw(Network1lvlImg, 13 * WIDTH, HEIGHT * 1 / 14, WIDTH * 2, HEIGHT * 2);
            }
            if (a == 2) {
                batch.draw(Network2lvlImg, 13 * WIDTH, HEIGHT * 1 / 14, WIDTH * 2, HEIGHT * 2);
            }
            if (a == 3) {
                batch.draw(Network3lvlImg, 13 * WIDTH, HEIGHT * 1 / 14, WIDTH * 2, HEIGHT * 2);
            }
            if (b == 0) {
                batch.draw(NetworkCounter010Img, (16 * WIDTH) - (WIDTH / 2), HEIGHT * 1 / 100, WIDTH * 3, HEIGHT * 2.5f);
            }
            if (b == 1) {
                batch.draw(NetworkCounter1020Img, (16 * WIDTH) - (WIDTH / 2), HEIGHT * 1 / 100, WIDTH * 3, HEIGHT * 2.5f);
            }
            if (b == 2) {
                batch.draw(NetworkCounter2040Img, (16 * WIDTH) - (WIDTH / 2), HEIGHT * 1 / 100, WIDTH * 3, HEIGHT * 2.5f);
            }
            if (b == 3) {
                batch.draw(NetworkCounter4060Img, (16 * WIDTH) - (WIDTH / 2), HEIGHT * 1 / 100, WIDTH * 3, HEIGHT * 2.5f);
            }
            if (b == 4) {
                batch.draw(NetworkCounter6080Img, (16 * WIDTH) - (WIDTH / 2), HEIGHT * 1 / 100, WIDTH * 3, HEIGHT * 2.5f);
            }
            if (b == 5) {
                batch.draw(NetworkCounter80100Img, (16 * WIDTH) - (WIDTH / 2), HEIGHT * 1 / 100, WIDTH * 3, HEIGHT * 2.5f);
            }
        }
        orbitalStrike.reset();


        batch.draw(ButtonBackImg, btnSettings.x, btnSettings.y, btnSettings.width, btnSettings.height);
        batch.draw(SettinsImg, btnSettings.x, btnSettings.y, btnSettings.width, btnSettings.height);


        if (onPause) {
            btnRetry.x = SCR_WIDTH * 3 / 10;
            btnRetry.y = SCR_HEIGHT * 6 / 10;
            btnRetry.textureX = btnRetry.x * 92 / 100;
            btnRetry.textureY = btnRetry.y * 93 / 100;
            batch.draw(ButtonBackImg, btnRetry.textureX, btnRetry.textureY, btnRetry.textureWidth, btnRetry.textureHeight);
            btnRetry.font.draw(batch, "Retry", btnRetry.x, btnRetry.y + SCR_HEIGHT / 50);

            btnResume.x = SCR_WIDTH * 6 / 10;
            btnResume.y = SCR_HEIGHT * 6 / 10;
            btnResume.textureX = btnResume.x * 95 / 100;
            btnResume.textureY = btnResume.y * 93 / 100;
            batch.draw(ButtonBackImg, btnResume.textureX, btnResume.textureY, btnResume.textureWidth, btnResume.textureHeight);
            btnResume.font.draw(batch, "Resume", btnResume.x, btnResume.y + SCR_HEIGHT / 50);

            btnMainMenu.x = SCR_WIDTH * 42 / 100;
            btnMainMenu.y = SCR_HEIGHT * 4 / 10;
            btnMainMenu.textureX = btnMainMenu.x * 92 / 100;
            btnMainMenu.textureY = btnMainMenu.y * 90 / 100;
            batch.draw(ButtonBackImg, btnMainMenu.textureX, btnMainMenu.textureY, btnMainMenu.textureWidth, btnMainMenu.textureHeight);
            btnMainMenu.font.draw(batch, "Main menu", btnMainMenu.x, btnMainMenu.y + SCR_HEIGHT / 50);
        }
        if (clickChecker == 0) {
            batch.draw(TargetedAmmoImg, 6 * WIDTH, 0, WIDTH * 2, HEIGHT * 2);
        }
        if (StickyTanks.isPhone) {
            batch.draw(JoyStickImg, WIDTH, HEIGHT, WIDTH * 6, HEIGHT * 6);
            if (StickyTanks.TankType == 2) {
                batch.draw(JoyStickImg, WIDTH * 25, HEIGHT, WIDTH * 6, HEIGHT * 6);
            }
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
        isMusicForStagesOn = false;
        isMusicForBossOn = false;
        isMusicForLoseOn = false;
        isMusicForWinOn = false;
        MusicForBoss.stop();
        MusicForStages.stop();
        MusicForWin.stop();
        MusicForLose.stop();
    }


    float textHeight(String text, BitmapFont font) {
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, text);
        return layout.height;
    }

    float textWidth(String text, BitmapFont font) {
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, text);
        return layout.width;
    }

    public boolean OverlapWallsLeftX(float x, float y, float height, float width, int ID) {
        for (int i = 0; i < wallsCounter; i++) {
            if (walls[i].x <= x - 1.25 & x - 1.25 <= walls[i].x + walls[i].width) {
                if ((walls[i].y <= y & y <= walls[i].y + walls[i].height) | (walls[i].y <= y + height & y + height <= walls[i].y + walls[i].height)) {
                    if (ID == 1) {
                        walls[i].durability -= basicAmmo[0].antiWallDamage;
                    }
                    if (ID == 2) {
                        walls[i].durability -= machinegunAmmo[0].antiWallDamage;
                    }
                    if (ID == 3) {
                        walls[i].durability -= bigAmmo[0].antiWallDamage;
                    }
                    if (ID == 4) {
                        walls[i].durability -= basicRocket[0].antiWallDamage;
                    }
                    if (ID == 5) {
                        walls[i].durability -= bigRocket[0].antiWallDamage;
                    }
                    if (ID == 6) {
                        walls[i].durability -= napalmRocket[0].antiWallDamage;
                    }
                    if (ID == 7) {
                        walls[i].durability -= napalmPool[0].antiWallDamage;
                    }
                    if (ID == 8) {
                        walls[i].durability -= basicBomb[0].antiWallDamage;
                    }
                    if (ID == 9) {
                        walls[i].durability -= clockBomb[0].antiWallDamage;
                    }
                    return true;
                }
            }
        }
        //  if (bossShield.x <= x - 2 & x - 2 <= bossShield.x + bossShield.width) {
        //     if ((bossShield.y <= y & y <= bossShield.y + bossShield.height) | (bossShield.y <= y + height & y + height <= bossShield.y + bossShield.height) | (y <= bossShield.y & bossShield.y <= y + height)) {
        //         return true;
        //     }
        //     }
        for (int i = LAB_WIDTH * LAB_HEIGHT + 1; i < hedgehogWallsCounter; i++) {
            if (walls[i].x <= x - 2 & x - 2 <= walls[i].x + walls[i].width) {
                if ((walls[i].y <= y & y <= walls[i].y + walls[i].height) | (walls[i].y <= y + height & y + height <= walls[i].y + walls[i].height) | (y <= walls[i].y & walls[i].y <= y + height)) {
                    DamageDealtToEnemy(x, y, height, width, i - (LAB_WIDTH * LAB_HEIGHT), 10);
                    if (ID == 1) {
                        walls[i].durability -= basicAmmo[0].antiWallDamage;
                    }
                    if (ID == 2) {
                        walls[i].durability -= machinegunAmmo[0].antiWallDamage;
                    }
                    if (ID == 3) {
                        walls[i].durability -= bigAmmo[0].antiWallDamage;
                    }
                    if (ID == 4) {
                        walls[i].durability -= basicRocket[0].antiWallDamage;
                    }
                    if (ID == 5) {
                        walls[i].durability -= bigRocket[0].antiWallDamage;
                    }
                    if (ID == 6) {
                        walls[i].durability -= napalmRocket[0].antiWallDamage;
                    }
                    if (ID == 7) {
                        walls[i].durability -= napalmPool[0].antiWallDamage;
                    }
                    if (ID == 8) {
                        walls[i].durability -= basicBomb[0].antiWallDamage;
                    }
                    if (ID == 9) {
                        walls[i].durability -= clockBomb[0].antiWallDamage;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private boolean OverlapWallsRightX(float x, float y, float height, float width, int ID) {
        for (int i = 0; i < wallsCounter; i++) {
            if (walls[i].x <= x + width + 1.25 & x + width + 1.25 <= walls[i].x + walls[i].width) {
                if ((walls[i].y <= y & y <= walls[i].y + walls[i].height) | (walls[i].y <= y + height & y + height <= walls[i].y + walls[i].height)) {
                    if (ID == 1) {
                        walls[i].durability -= basicAmmo[0].antiWallDamage;
                    }
                    if (ID == 2) {
                        walls[i].durability -= machinegunAmmo[0].antiWallDamage;
                    }
                    if (ID == 3) {
                        walls[i].durability -= bigAmmo[0].antiWallDamage;
                    }
                    if (ID == 4) {
                        walls[i].durability -= basicRocket[0].antiWallDamage;
                    }
                    if (ID == 5) {
                        walls[i].durability -= bigRocket[0].antiWallDamage;
                    }
                    if (ID == 6) {
                        walls[i].durability -= napalmRocket[0].antiWallDamage;
                    }
                    if (ID == 7) {
                        walls[i].durability -= napalmPool[0].antiWallDamage;
                    }
                    if (ID == 8) {
                        walls[i].durability -= basicBomb[0].antiWallDamage;
                    }
                    if (ID == 9) {
                        walls[i].durability -= clockBomb[0].antiWallDamage;
                    }
                    return true;
                }
            }
        }
        //  if (bossShield.x <= x + width + 2 & x + width + 2 <= bossShield.x + bossShield.width) {
        //     if ((bossShield.y <= y & y <= bossShield.y + bossShield.height) | (bossShield.y <= y + height & y + height <= bossShield.y + bossShield.height) | (y <= bossShield.y & bossShield.y <= y + height)) {
        //          return true;
        //     }
        //     }
        for (int i = LAB_WIDTH * LAB_HEIGHT + 1; i < hedgehogWallsCounter; i++) {
            if (walls[i].x <= x + width + 2 & x + width + 2 <= walls[i].x + walls[i].width) {
                if ((walls[i].y <= y & y <= walls[i].y + walls[i].height) | (walls[i].y <= y + height & y + height <= walls[i].y + walls[i].height) | (y <= walls[i].y & walls[i].y <= y + height)) {
                    DamageDealtToEnemy(x, y, height, width, i - (LAB_WIDTH * LAB_HEIGHT), 10);
                    if (ID == 1) {
                        walls[i].durability -= basicAmmo[0].antiWallDamage;
                    }
                    if (ID == 2) {
                        walls[i].durability -= machinegunAmmo[0].antiWallDamage;
                    }
                    if (ID == 3) {
                        walls[i].durability -= bigAmmo[0].antiWallDamage;
                    }
                    if (ID == 4) {
                        walls[i].durability -= basicRocket[0].antiWallDamage;
                    }
                    if (ID == 5) {
                        walls[i].durability -= bigRocket[0].antiWallDamage;
                    }
                    if (ID == 6) {
                        walls[i].durability -= napalmRocket[0].antiWallDamage;
                    }
                    if (ID == 7) {
                        walls[i].durability -= napalmPool[0].antiWallDamage;
                    }
                    if (ID == 8) {
                        walls[i].durability -= basicBomb[0].antiWallDamage;
                    }
                    if (ID == 9) {
                        walls[i].durability -= clockBomb[0].antiWallDamage;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private boolean OverlapWallsUpY(float x, float y, float height, float width, int ID) {
        for (int i = 0; i < wallsCounter; i++) {
            if (walls[i].y <= y + height + 1.25 & y + height + 1.25 <= walls[i].y + walls[i].height) {
                if ((walls[i].x <= x & x <= walls[i].x + walls[i].width) | (walls[i].x <= x + width & x + width <= walls[i].x + walls[i].width)) {
                    if (ID == 1) {
                        walls[i].durability -= basicAmmo[0].antiWallDamage;
                    }
                    if (ID == 2) {
                        walls[i].durability -= machinegunAmmo[0].antiWallDamage;
                    }
                    if (ID == 3) {
                        walls[i].durability -= bigAmmo[0].antiWallDamage;
                    }
                    if (ID == 4) {
                        walls[i].durability -= basicRocket[0].antiWallDamage;
                    }
                    if (ID == 5) {
                        walls[i].durability -= bigRocket[0].antiWallDamage;
                    }
                    if (ID == 6) {
                        walls[i].durability -= napalmRocket[0].antiWallDamage;
                    }
                    if (ID == 7) {
                        walls[i].durability -= napalmPool[0].antiWallDamage;
                    }
                    if (ID == 8) {
                        walls[i].durability -= basicBomb[0].antiWallDamage;
                    }
                    if (ID == 9) {
                        walls[i].durability -= clockBomb[0].antiWallDamage;
                    }
                    return true;
                }
            }
        }
        //   if (bossShield.y <= y + height + 2 & y + height + 2 <= bossShield.y + bossShield.height) {
        //       if ((bossShield.x <= x & x <= bossShield.x + bossShield.width) | (bossShield.x <= x + width & x + width <= bossShield.x + bossShield.width) | (x <= bossShield.x & bossShield.x <= x + width)) {
        //           return true;
        //       }
        //       }
        for (int i = LAB_WIDTH * LAB_HEIGHT + 1; i < hedgehogWallsCounter; i++) {
            if (walls[i].y <= y + height + 2 & y + height + 2 <= walls[i].y + walls[i].height) {
                if ((walls[i].x <= x & x <= walls[i].x + walls[i].width) | (walls[i].x <= x + width & x + width <= walls[i].x + walls[i].width) | (x <= walls[i].x & walls[i].x <= x + width)) {
                    DamageDealtToEnemy(x, y, height, width, i - (LAB_WIDTH * LAB_HEIGHT), 10);
                    if (ID == 1) {
                        walls[i].durability -= basicAmmo[0].antiWallDamage;
                    }
                    if (ID == 2) {
                        walls[i].durability -= machinegunAmmo[0].antiWallDamage;
                    }
                    if (ID == 3) {
                        walls[i].durability -= bigAmmo[0].antiWallDamage;
                    }
                    if (ID == 4) {
                        walls[i].durability -= basicRocket[0].antiWallDamage;
                    }
                    if (ID == 5) {
                        walls[i].durability -= bigRocket[0].antiWallDamage;
                    }
                    if (ID == 6) {
                        walls[i].durability -= napalmRocket[0].antiWallDamage;
                    }
                    if (ID == 7) {
                        walls[i].durability -= napalmPool[0].antiWallDamage;
                    }
                    if (ID == 8) {
                        walls[i].durability -= basicBomb[0].antiWallDamage;
                    }
                    if (ID == 9) {
                        walls[i].durability -= clockBomb[0].antiWallDamage;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private boolean OverlapWallsDownY(float x, float y, float height, float width, int ID) {
        for (int i = 0; i < wallsCounter; i++) {
            if (walls[i].y <= y - 1.25 & y - 1.25 <= walls[i].y + walls[i].height) {
                if ((walls[i].x <= x & x <= walls[i].x + walls[i].width) | (walls[i].x <= x + width & x + width <= walls[i].x + walls[i].width)) {
                    if (ID == 1) {
                        walls[i].durability -= basicAmmo[0].antiWallDamage;
                    }
                    if (ID == 2) {
                        walls[i].durability -= machinegunAmmo[0].antiWallDamage;
                    }
                    if (ID == 3) {
                        walls[i].durability -= bigAmmo[0].antiWallDamage;
                    }
                    if (ID == 4) {
                        walls[i].durability -= basicRocket[0].antiWallDamage;
                    }
                    if (ID == 5) {
                        walls[i].durability -= bigRocket[0].antiWallDamage;
                    }
                    if (ID == 6) {
                        walls[i].durability -= napalmRocket[0].antiWallDamage;
                    }
                    if (ID == 7) {
                        walls[i].durability -= napalmPool[0].antiWallDamage;
                    }
                    if (ID == 8) {
                        walls[i].durability -= basicBomb[0].antiWallDamage;
                    }
                    if (ID == 9) {
                        walls[i].durability -= clockBomb[0].antiWallDamage;
                    }
                    return true;
                }
            }
        }
        //    if (bossShield.y <= y - 2 & y - 2 <= bossShield.y + bossShield.height) {
        //       if ((bossShield.x <= x & x <= bossShield.x + bossShield.width) | (bossShield.x <= x + width & x + width <= bossShield.x + bossShield.width) | (x <= bossShield.x & bossShield.x <= x + width)) {
        //           return true;
        //       }
        //       }
        for (int i = LAB_WIDTH * LAB_HEIGHT + 1; i < hedgehogWallsCounter; i++) {
            if (walls[i].y <= y - 2 & y - 2 <= walls[i].y + walls[i].height) {
                if ((walls[i].x <= x & x <= walls[i].x + walls[i].width) | (walls[i].x <= x + width & x + width <= walls[i].x + walls[i].width) | (x <= walls[i].x & walls[i].x <= x + width)) {
                    DamageDealtToEnemy(x, y, height, width, i - (LAB_WIDTH * LAB_HEIGHT), 10);
                    if (ID == 1) {
                        walls[i].durability -= basicAmmo[0].antiWallDamage;
                    }
                    if (ID == 2) {
                        walls[i].durability -= machinegunAmmo[0].antiWallDamage;
                    }
                    if (ID == 3) {
                        walls[i].durability -= bigAmmo[0].antiWallDamage;
                    }
                    if (ID == 4) {
                        walls[i].durability -= basicRocket[0].antiWallDamage;
                    }
                    if (ID == 5) {
                        walls[i].durability -= bigRocket[0].antiWallDamage;
                    }
                    if (ID == 6) {
                        walls[i].durability -= napalmRocket[0].antiWallDamage;
                    }
                    if (ID == 7) {
                        walls[i].durability -= napalmPool[0].antiWallDamage;
                    }
                    if (ID == 8) {
                        walls[i].durability -= basicBomb[0].antiWallDamage;
                    }
                    if (ID == 9) {
                        walls[i].durability -= clockBomb[0].antiWallDamage;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public boolean OverlapBordersLeftX(float x, float y, float height, float width) {
        for (int i = 0; i < borderwallsCounter; i++) {
            if (borderwalls[i].x <= x - 1 & x - 1 <= borderwalls[i].x + borderwalls[i].width) {
                if ((borderwalls[i].y <= y & y <= borderwalls[i].y + borderwalls[i].height) | (borderwalls[i].y <= y + height & y + height <= borderwalls[i].y + borderwalls[i].height)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean OverlapBordersRightX(float x, float y, float height, float width) {
        for (int i = 0; i < borderwallsCounter; i++) {
            if (borderwalls[i].x <= x + width + 1 & x + width + 1 <= borderwalls[i].x + borderwalls[i].width) {
                if ((borderwalls[i].y <= y & y <= borderwalls[i].y + borderwalls[i].height) | (borderwalls[i].y <= y + height & y + height <= borderwalls[i].y + borderwalls[i].height)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean OverlapBordersUpY(float x, float y, float height, float width) {
        for (int i = 0; i < borderwallsCounter; i++) {
            if (borderwalls[i].y <= y + height + 1.25 & y + height + 1.25 <= borderwalls[i].y + borderwalls[i].height) {
                if ((borderwalls[i].x <= x & x <= borderwalls[i].x + borderwalls[i].width) | (borderwalls[i].x <= x + width & x + width <= borderwalls[i].x + borderwalls[i].width)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean OverlapBordersDownY(float x, float y, float height, float width) {
        for (int i = 0; i < borderwallsCounter; i++) {
            if (borderwalls[i].y <= y - 1 & y - 1 <= borderwalls[i].y + borderwalls[i].height) {
                if ((borderwalls[i].x <= x & x <= borderwalls[i].x + borderwalls[i].width) | (borderwalls[i].x <= x + width & x + width <= borderwalls[i].x + borderwalls[i].width)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean DamageDealtToEnemy(float x, float y, float height, float width, int bulletNumber, int ID) {
        for (int i = 0; i < enemies.length - enemyBlocker; i++) {
            if (!enemies[i].isDestroyed) {
                if ((enemies[i].realY <= y - 1 & y - 1 <= enemies[i].realY + enemies[i].height) | (y <= enemies[i].realY & enemies[i].realY <= y + width) | (enemies[i].realY <= y + height + 1.25 & y + height + 1.25 <= enemies[i].realY + enemies[i].height) | (y + height <= enemies[i].realY & enemies[i].realY <= y + height) | (enemies[i].realY <= y & y <= enemies[i].realY + enemies[i].height) | (enemies[i].realY <= y + height & y + height <= enemies[i].realY + enemies[i].height) | (y <= enemies[i].realY & enemies[i].realY <= y + height) | (enemies[i].realY <= y & y <= enemies[i].realY + enemies[i].height) | (enemies[i].realY <= y + height & y + height <= enemies[i].realY + enemies[i].height) | (y <= enemies[i].realY & enemies[i].realY <= y + height)) {
                    if ((enemies[i].realX <= x & x <= enemies[i].realX + enemies[i].width) | (enemies[i].realX <= x + width & x + width <= enemies[i].realX + enemies[i].width) | (x <= enemies[i].realX & enemies[i].realX <= x + width) | (enemies[i].realX <= x & x <= enemies[i].realX + enemies[i].width) | (enemies[i].realX <= x + width & x + width <= enemies[i].realX + enemies[i].width) | (x <= enemies[i].realX & enemies[i].realX <= x + width) | (enemies[i].realX <= x + width + 1 & x + width + 1 <= enemies[i].realX + enemies[i].width) | (x + width <= enemies[i].realX & enemies[i].realX <= x + width) | (enemies[i].realX <= x - 1 & x - 1 <= enemies[i].realX + enemies[i].width) | (x <= enemies[i].realX & enemies[i].realX <= x)) {
                        if (bossShield.durability <= 0) {
                            if (ID == 1) {
                                enemies[i].hitpoints -= basicAmmo[bulletNumber].antiEnemyDamage;
                            }
                            if (ID == 2) {
                                enemies[i].hitpoints -= machinegunAmmo[bulletNumber].antiEnemyDamage;
                            }
                            if (ID == 3) {
                                enemies[i].hitpoints -= bigAmmo[bulletNumber].antiEnemyDamage;
                            }
                            if (ID == 4) {
                                enemies[i].hitpoints -= basicRocket[bulletNumber].antiEnemyDamage;
                            }
                            if (ID == 5) {
                                enemies[i].hitpoints -= bigRocket[bulletNumber].antiEnemyDamage;
                            }
                            if (ID == 6) {
                                enemies[i].hitpoints -= napalmRocket[bulletNumber].antiEnemyDamage;
                            }
                            if (ID == 7) {
                                enemies[i].hitpoints -= napalmPool[bulletNumber].antiEnemyDamage;
                            }
                            if (ID == 8) {
                                enemies[i].hitpoints -= basicBomb[bulletNumber].antiEnemyDamage;
                            }
                            if (ID == 9) {
                                enemies[i].hitpoints -= clockBomb[bulletNumber].antiEnemyDamage;
                            }
                            if (ID == 10 & Tics % 60 == 0) {
                                enemies[i].hitpoints -= hedgehog[bulletNumber].antiEnemyDamage;
                            }
                            if (ID == 666) {
                                enemies[i].hitpoints -= 666;
                            }
                        }
                        return true;
                    }
                }
            }
        }


        for (int i = 0; i < bombs.length; i++) {
            if (!bombs[i].isDestroyed) {
                if ((bombs[i].realY <= y - 1 & y - 1 <= bombs[i].realY + bombs[i].height) | (y <= bombs[i].realY & bombs[i].realY <= y + width) | (bombs[i].realY <= y + height + 1.25 & y + height + 1.25 <= bombs[i].realY + bombs[i].height) | (y + height <= bombs[i].realY & bombs[i].realY <= y + height) | (bombs[i].realY <= y & y <= bombs[i].realY + bombs[i].height) | (bombs[i].realY <= y + height & y + height <= bombs[i].realY + bombs[i].height) | (y <= bombs[i].realY & bombs[i].realY <= y + height) | (bombs[i].realY <= y & y <= bombs[i].realY + bombs[i].height) | (bombs[i].realY <= y + height & y + height <= bombs[i].realY + bombs[i].height) | (y <= bombs[i].realY & bombs[i].realY <= y + height)) {
                    if ((bombs[i].realX <= x & x <= bombs[i].realX + bombs[i].width) | (bombs[i].realX <= x + width & x + width <= bombs[i].realX + bombs[i].width) | (x <= bombs[i].realX & bombs[i].realX <= x + width) | (bombs[i].realX <= x & x <= bombs[i].realX + bombs[i].width) | (bombs[i].realX <= x + width & x + width <= bombs[i].realX + bombs[i].width) | (x <= bombs[i].realX & bombs[i].realX <= x + width) | (bombs[i].realX <= x + width + 1 & x + width + 1 <= bombs[i].realX + bombs[i].width) | (x + width <= bombs[i].realX & bombs[i].realX <= x + width) | (bombs[i].realX <= x - 1 & x - 1 <= bombs[i].realX + bombs[i].width) | (x <= bombs[i].realX & bombs[i].realX <= x)) {
                        //if (bossShield.durability <= 0) {
                            if (ID == 1) {
                                bombs[i].hitpoints -= basicAmmo[bulletNumber].antiEnemyDamage;
                            }
                            if (ID == 2) {
                                bombs[i].hitpoints -= machinegunAmmo[bulletNumber].antiEnemyDamage;
                            }
                            if (ID == 3) {
                                bombs[i].hitpoints -= bigAmmo[bulletNumber].antiEnemyDamage;
                            }
                            if (ID == 4) {
                                bombs[i].hitpoints -= basicRocket[bulletNumber].antiEnemyDamage;
                            }
                            if (ID == 5) {
                                bombs[i].hitpoints -= bigRocket[bulletNumber].antiEnemyDamage;
                            }
                            if (ID == 6) {
                                bombs[i].hitpoints -= napalmRocket[bulletNumber].antiEnemyDamage;
                            }
                            if (ID == 7) {
                                bombs[i].hitpoints -= napalmPool[bulletNumber].antiEnemyDamage;
                            }
                            if (ID == 8) {
                                bombs[i].hitpoints -= basicBomb[bulletNumber].antiEnemyDamage;
                            }
                            if (ID == 9) {
                                bombs[i].hitpoints -= clockBomb[bulletNumber].antiEnemyDamage;
                            }
                            if (ID == 10 & Tics % 60 == 0) {
                                bombs[i].hitpoints -= hedgehog[bulletNumber].antiEnemyDamage;
                            }
                            if (ID == 666) {
                                bombs[i].hitpoints -= 666;
                            }
                       // }
                        return true;
                    }
                }
            }
        }


        for (int i = 0; i < safers.length; i++) {
            if (!safers[i].isDestroyed) {
                if ((safers[i].realY <= y - 1 & y - 1 <= safers[i].realY + safers[i].height) | (y <= safers[i].realY & safers[i].realY <= y + width) | (safers[i].realY <= y + height + 1.25 & y + height + 1.25 <= safers[i].realY + safers[i].height) | (y + height <= safers[i].realY & safers[i].realY <= y + height) | (safers[i].realY <= y & y <= safers[i].realY + safers[i].height) | (safers[i].realY <= y + height & y + height <= safers[i].realY + safers[i].height) | (y <= safers[i].realY & safers[i].realY <= y + height) | (safers[i].realY <= y & y <= safers[i].realY + safers[i].height) | (safers[i].realY <= y + height & y + height <= safers[i].realY + safers[i].height) | (y <= safers[i].realY & safers[i].realY <= y + height)) {
                    if ((safers[i].realX <= x & x <= safers[i].realX + safers[i].width) | (safers[i].realX <= x + width & x + width <= safers[i].realX + safers[i].width) | (x <= safers[i].realX & safers[i].realX <= x + width) | (safers[i].realX <= x & x <= safers[i].realX + safers[i].width) | (safers[i].realX <= x + width & x + width <= safers[i].realX + safers[i].width) | (x <= safers[i].realX & safers[i].realX <= x + width) | (safers[i].realX <= x + width + 1 & x + width + 1 <= safers[i].realX + safers[i].width) | (x + width <= safers[i].realX & safers[i].realX <= x + width) | (safers[i].realX <= x - 1 & x - 1 <= safers[i].realX + safers[i].width) | (x <= safers[i].realX & safers[i].realX <= x)) {
                        //if (bossShield.durability <= 0) {
                        if (ID == 1) {
                            safers[i].hitpoints -= basicAmmo[bulletNumber].antiEnemyDamage;
                        }
                        if (ID == 2) {
                            safers[i].hitpoints -= machinegunAmmo[bulletNumber].antiEnemyDamage;
                        }
                        if (ID == 3) {
                            safers[i].hitpoints -= bigAmmo[bulletNumber].antiEnemyDamage;
                        }
                        if (ID == 4) {
                            safers[i].hitpoints -= basicRocket[bulletNumber].antiEnemyDamage;
                        }
                        if (ID == 5) {
                            safers[i].hitpoints -= bigRocket[bulletNumber].antiEnemyDamage;
                        }
                        if (ID == 6) {
                            safers[i].hitpoints -= napalmRocket[bulletNumber].antiEnemyDamage;
                        }
                        if (ID == 7) {
                            safers[i].hitpoints -= napalmPool[bulletNumber].antiEnemyDamage;
                        }
                        if (ID == 8) {
                            safers[i].hitpoints -= basicBomb[bulletNumber].antiEnemyDamage;
                        }
                        if (ID == 9) {
                            safers[i].hitpoints -= clockBomb[bulletNumber].antiEnemyDamage;
                        }
                        if (ID == 10 & Tics % 60 == 0) {
                            safers[i].hitpoints -= hedgehog[bulletNumber].antiEnemyDamage;
                        }
                        if (ID == 666) {
                            safers[i].hitpoints -= 666;
                        }
                        // }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean DamageDealtToTank(float x, float y, float height, float width, int bulletNumber, int ID) {
        if (tank.realY <= y - 1 & y - 1 <= tank.realY + tank.height) {
            if ((tank.realX <= x & x <= tank.realX + tank.width) | (tank.realX <= x + width & x + width <= tank.realX + tank.width) | (x <= tank.realX & tank.realX <= x + width)) {
                if (ID == 1) {
                    tank.hitpoints -= enemyBasicAmmo[bulletNumber].antiEnemyDamage;
                } else if (ID == 2) {
                    tank.hitpoints -= enemyMachinegunAmmo[bulletNumber].antiEnemyDamage;
                } else if (ID == 3) {
                    tank.hitpoints -= enemyBigAmmo[bulletNumber].antiEnemyDamage;
                } else if (ID == 4 & Tics % 10 == 0) {
                    tank.hitpoints -= shockWave.antiEnemyDamage;
                } else if (ID == 5) {
                    tank.hitpoints -= shieldBash.antiEnemyDamage;
                }
                return true;
            }
        }
        if (tank.realY <= y + height + 1.25 & y + height + 1.25 <= tank.realY + tank.height) {
            if ((tank.realX <= x & x <= tank.realX + tank.width) | (tank.realX <= x + width & x + width <= tank.realX + tank.width) | (x <= tank.realX & tank.realX <= x + width)) {
                if (ID == 1) {
                    tank.hitpoints -= enemyBasicAmmo[bulletNumber].antiEnemyDamage;
                } else if (ID == 2) {
                    tank.hitpoints -= enemyMachinegunAmmo[bulletNumber].antiEnemyDamage;
                } else if (ID == 3) {
                    tank.hitpoints -= enemyBigAmmo[bulletNumber].antiEnemyDamage;
                } else if (ID == 4 & Tics % 10 == 0) {
                    tank.hitpoints -= shockWave.antiEnemyDamage;
                } else if (ID == 5) {
                    tank.hitpoints -= shieldBash.antiEnemyDamage;
                }
                return true;
            }
        }
        if (tank.realX <= x + width + 1 & x + width + 1 <= tank.realX + tank.width) {
            if ((tank.realY <= y & y <= tank.realY + tank.height) | (tank.realY <= y + height & y + height <= tank.realY + tank.height) | (y <= tank.realY & tank.realY <= y + height)) {
                if (ID == 1) {
                    tank.hitpoints -= enemyBasicAmmo[bulletNumber].antiEnemyDamage;
                } else if (ID == 2) {
                    tank.hitpoints -= enemyMachinegunAmmo[bulletNumber].antiEnemyDamage;
                } else if (ID == 3) {
                    tank.hitpoints -= enemyBigAmmo[bulletNumber].antiEnemyDamage;
                } else if (ID == 4 & Tics % 10 == 0) {
                    tank.hitpoints -= shockWave.antiEnemyDamage;
                } else if (ID == 5) {
                    tank.hitpoints -= shieldBash.antiEnemyDamage;
                }
                return true;
            }
        }
        if (tank.realX <= x - 1 & x - 1 <= tank.realX + tank.width) {
            if ((tank.realY <= y & y <= tank.realY + tank.height) | (tank.realY <= y + height & y + height <= tank.realY + tank.height) | (y <= tank.realY & tank.realY <= y + height)) {
                if (ID == 1) {
                    tank.hitpoints -= enemyBasicAmmo[bulletNumber].antiEnemyDamage;
                } else if (ID == 2) {
                    tank.hitpoints -= enemyMachinegunAmmo[bulletNumber].antiEnemyDamage;
                } else if (ID == 3) {
                    tank.hitpoints -= enemyBigAmmo[bulletNumber].antiEnemyDamage;
                } else if (ID == 4 & Tics % 10 == 0) {
                    tank.hitpoints -= shockWave.antiEnemyDamage;
                } else if (ID == 5) {
                    tank.hitpoints -= shieldBash.antiEnemyDamage;
                }
                return true;
            }
        }
        return false;
    }

    private void BulletFlying(Projectile ammo, int bulletNumber, boolean whose, int bulletID) {
        if (!isGameOver | !onPause) {
            if (whose) {
                //  if(bulletID == 10 & MathUtils.random(99999) == 5654)
                if (DamageDealtToEnemy(ammo.x, ammo.y, ammo.height, ammo.width, bulletNumber, bulletID)) {
                    // if(bulletID != 7){
                    ammo.hit();
                    // }
                }
            }
            if (!whose) {
                if (DamageDealtToTank(ammo.x, ammo.y, ammo.height, ammo.width, bulletNumber, bulletID)) {
                    ammo.hit();
                }
            }
            if (ammo.sideCounter == 1 & !OverlapWallsLeftX(ammo.x, ammo.y, ammo.height, ammo.width, bulletID) & !OverlapBordersLeftX(ammo.x, ammo.y, ammo.height, ammo.width)) {
                ammo.moveLeftX();

            } else if (OverlapWallsLeftX(ammo.x, ammo.y, ammo.height, ammo.width, bulletID) | OverlapBordersLeftX(ammo.x, ammo.y, ammo.height, ammo.width)) {
                if (bulletID != 7) {
                    ammo.hit();
                }

            } else if (ammo.sideCounter == 2 & !OverlapWallsRightX(ammo.x, ammo.y, ammo.height, ammo.width, bulletID) & !OverlapBordersRightX(ammo.x, ammo.y, ammo.height, ammo.width)) {
                ammo.moveRightX();

            } else if (OverlapWallsRightX(ammo.x, ammo.y, ammo.height, ammo.width, bulletID) | OverlapBordersRightX(ammo.x, ammo.y, ammo.height, ammo.width)) {
                if (bulletID != 7) {
                    ammo.hit();
                }

            } else if ((ammo.sideCounter == 3 | ammo.sideCounter == 0) & !OverlapWallsUpY(ammo.x, ammo.y, ammo.height, ammo.width, bulletID) & !OverlapBordersUpY(ammo.x, ammo.y, ammo.height, ammo.width)) {
                ammo.moveUpY();

            } else if (OverlapWallsUpY(ammo.x, ammo.y, ammo.height, ammo.width, bulletID) | OverlapBordersUpY(ammo.x, ammo.y, ammo.height, ammo.width)) {
                if (bulletID != 7) {
                    ammo.hit();
                }

            } else if (ammo.sideCounter == 4 & !OverlapWallsDownY(ammo.x, ammo.y, ammo.height, ammo.width, bulletID) & !OverlapBordersDownY(ammo.x, ammo.y, ammo.height, ammo.width)) {
                ammo.moveDownY();

            } else if (OverlapWallsDownY(ammo.x, ammo.y, ammo.height, ammo.width, bulletID) | OverlapBordersDownY(ammo.x, ammo.y, ammo.height, ammo.width)) {
                if (bulletID != 7) {
                    ammo.hit();
                }
            }

        }
    }

    private void BulletShoot(Projectile ammo, int bulletID, int ID, int enemyNumber) {
        if (!isGameOver | !onPause) {
            if (ID == 1) {
                if (sideCounter == 1) {
                    ammo.sideCounter = 1;
                    ammo.x = tank.realX * 0.998F;
                    ammo.y = tank.realY + (tank.height - basicAmmo[1].height) / 2;
                } else if (sideCounter == 2) {
                    ammo.sideCounter = 2;
                    ammo.x = tank.realX + tank.width * 1.002F;
                    ammo.y = tank.realY + (tank.height - basicAmmo[1].height) / 2;
                } else if (sideCounter == 3) {
                    ammo.sideCounter = 3;
                    ammo.x = tank.realX + (tank.width - basicAmmo[1].width) / 2;
                    ammo.y = tank.realY + tank.height * 1.002F;
                } else if (sideCounter == 4) {
                    ammo.sideCounter = 4;
                    ammo.x = tank.realX + (tank.width - basicAmmo[1].width) / 2;
                    ammo.y = tank.realY * 0.998F;
                } else if (sideCounter == 0) {
                    ammo.sideCounter = 3;
                    ammo.x = tank.realX + (tank.width - basicAmmo[1].width) / 2;
                    ammo.y = tank.realY + tank.height * 1.002F;
                }
            }
            if (ID == 2) {
                if (enemies[enemyNumber].sideCounter == 1 & !ammo.hit) {
                    ammo.sideCounter = 1;
                    ammo.x = enemies[enemyNumber].realX + enemies[enemyNumber].width / 2;
                    ammo.y = enemies[enemyNumber].realY + (enemies[enemyNumber].height - enemyBasicAmmo[1].height) / 2;
                } else if (enemies[enemyNumber].sideCounter == 2 & !enemyBasicAmmo[enemies[enemyNumber].enemyBasicAmmoCounter].hit) {
                    ammo.sideCounter = 2;
                    ammo.x = enemies[enemyNumber].realX + enemies[enemyNumber].width / 2;
                    ammo.y = enemies[enemyNumber].realY + (enemies[enemyNumber].height - enemyBasicAmmo[1].height) / 2;
                } else if (enemies[enemyNumber].sideCounter == 3 & !ammo.hit) {
                    ammo.sideCounter = 3;
                    ammo.x = enemies[enemyNumber].realX + (enemies[enemyNumber].width - enemyBasicAmmo[1].width) / 2;
                    ammo.y = enemies[enemyNumber].realY + enemies[enemyNumber].height * 2 / 5;
                } else if (enemies[enemyNumber].sideCounter == 4 & !ammo.hit) {
                    ammo.sideCounter = 4;
                    ammo.x = enemies[enemyNumber].realX + (enemies[enemyNumber].width - enemyBasicAmmo[1].width) / 2;
                    ammo.y = enemies[enemyNumber].realY + enemies[enemyNumber].height / 2;
                } else if (enemies[enemyNumber].sideCounter == 0 & !ammo.hit) {
                    ammo.sideCounter = 3;
                    ammo.x = enemies[enemyNumber].realX + (enemies[enemyNumber].width - enemyBasicAmmo[1].width) / 2;
                    ammo.y = enemies[enemyNumber].realY + enemies[enemyNumber].height / 2;
                }
            }
        }
    }

    private void savePlayerData() {
        Preferences playerData = Gdx.app.getPreferences("playerData");
        int score1 = 0;
        if (playerData.contains("Coins")) {
            score1 = playerData.getInteger("Coins");
        }
        playerData.putInteger("Coins", score + score1);
        playerData.flush();
    }

    private void loadPlayerData() {
        Preferences playerData = Gdx.app.getPreferences("playerData");
        if (playerData.contains("Coins")) {
            //  score = playerData.getInteger("Coins");
        }
        if (playerData.contains("RocketTank")) {
            StickyTanks.isRocketTankBought = playerData.getBoolean("RocketTank");
        }
        if (playerData.contains("BombTank")) {
            StickyTanks.isBombTankBought = playerData.getBoolean("BombTank");
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        BackgroundImg.dispose();
        BrickWallImg.dispose();
        PlayerUpYImg.dispose();
        //font.dispose();

    }
}