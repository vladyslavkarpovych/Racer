package com.javarush.games.racer;

import com.javarush.engine.cell.*;
import com.javarush.games.racer.road.RoadManager;
import com.javarush.games.racer.road.RoadObject;

public class RacerGame extends Game {
    //ширина игрового поля
    public static final int WIDTH = 64;
    //высота игрового поля
    public static final int HEIGHT = 64;
    //разделительная полоса по центру дороги
    public static final int CENTER_X = WIDTH / 2;
    //обочина
    public static final int ROADSIDE_WIDTH = 14;

    private RoadMarking roadMarking;
    private PlayerCar player;
    private RoadManager roadManager;
    private boolean isGameStopped;
    private FinishLine finishLine;
    private static final int RACE_GOAL_CARS_COUNT = 40;
    private ProgressBar progressBar;
    private int score;

    @Override
    public void initialize() {
        showGrid(false);
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    //старт новой игры
    private void createGame() {
        roadMarking = new RoadMarking();
        player = new PlayerCar();
        roadManager = new RoadManager();
        finishLine = new FinishLine();
        progressBar = new ProgressBar(RACE_GOAL_CARS_COUNT);
        drawScene();
        setTurnTimer(40);
        isGameStopped = false;
        score = 3600;
    }

    //отрисовка игровых обьетов
    private void drawScene() {
        drawField();
        roadMarking.draw(this);
        player.draw(this);
        roadManager.draw(this);
        finishLine.draw(this);
        progressBar.draw(this);
    }

    //отрисовка фона игрового поля
    private void drawField() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (x == CENTER_X)
                    setCellColor(CENTER_X, y, Color.WHITE);
                else if (x >= ROADSIDE_WIDTH && x < (WIDTH - ROADSIDE_WIDTH))
                    setCellColor(x, y, Color.BLACK);
                else
                    setCellColor(x, y, Color.GREEN);
            }
        }
    }

    @Override
    public void setCellColor(int x, int y, Color color) {
        if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
            super.setCellColor(x, y, color);
        }
    }

    private void moveAll() {
        roadMarking.move(player.speed);
        player.move();
        roadManager.move(player.speed);
        finishLine.move(player.speed);
        progressBar.move(roadManager.getPassedCarsCount());
    }

    @Override
    public void onTurn(int step) {
        score = score - 5;
        setScore(score);

        if (roadManager.getPassedCarsCount() >= RACE_GOAL_CARS_COUNT) {
            finishLine.show();
        }
        if (finishLine.isCrossed(player)) {
            win();
            drawScene();
            return;
        }
        if (roadManager.checkCrush(player)) {
            gameOver();
            drawScene();
        } else {
            moveAll();
            roadManager.generateNewRoadObjects(this);
            drawScene();
        }

    }

    @Override
    public void onKeyPress(Key key) {
        if (key == Key.RIGHT) {
            player.setDirection(Direction.RIGHT);
        }
        if (key == Key.LEFT) {
            player.setDirection(Direction.LEFT);
        }
        if (key == Key.UP) {
            player.speed = 2;
        }
        if (key == Key.SPACE && isGameStopped == true) {
            createGame();
        }
    }

    @Override
    public void onKeyReleased(Key key) {
        if (key == Key.RIGHT && player.getDirection() == Direction.RIGHT) {
            player.setDirection(Direction.NONE);
        }
        if (key == Key.LEFT && player.getDirection() == Direction.LEFT) {
            player.setDirection(Direction.NONE);
        }
        if (key == Key.UP) {
            player.speed = 1;
        }
    }

    private void gameOver() {
        isGameStopped = true;
        showMessageDialog(Color.BLACK, "Game Over", Color.WHITE, 100);
        stopTurnTimer();
        player.stop();
    }

    private void win() {
        isGameStopped = true;
        showMessageDialog(Color.BLACK, "You win!", Color.WHITE, 100);
        stopTurnTimer();
    }
}
