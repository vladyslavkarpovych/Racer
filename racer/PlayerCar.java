package com.javarush.games.racer;

import com.javarush.games.racer.road.RoadManager;

public class PlayerCar extends GameObject {

    private Direction direction;
    private static int playerCarHeight = ShapeMatrix.PLAYER.length;
    public int speed = 1;

    public PlayerCar() {
        super(RacerGame.WIDTH / 2 + 2,
                RacerGame.HEIGHT - playerCarHeight - 1,
                    ShapeMatrix.PLAYER);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection(){
        return direction;
    }

    public void move(){
        if(direction == Direction.LEFT) {
            x--;
        } if(direction == Direction.RIGHT) {
            x++;
        } if(x > RoadManager.RIGHT_BORDER - width) {
            x = RoadManager.RIGHT_BORDER;
        } if(x < RoadManager.LEFT_BORDER) {
            x = RoadManager.LEFT_BORDER;
        }
    }

    public void stop(){
        matrix = ShapeMatrix.PLAYER_DEAD;
    }

}
