package com.javarush.games.racer;

public class FinishLine extends GameObject {

    private boolean isVisible = false;

    public FinishLine(){
        super(RacerGame.ROADSIDE_WIDTH,
                -1 * ShapeMatrix.FINISH_LINE.length,
                    ShapeMatrix.FINISH_LINE);
    }

    public void show(){
        isVisible = true;
    }

    public void move(int boost) {
        if(isVisible == false){
        } else {
            this.y = this.y + boost;
        }
    }

    public boolean isCrossed(PlayerCar playerCar) {
        boolean condition;
        if (playerCar.y < this.y) {
            condition = true;
        }
        else {
            condition = false;
        }
        return condition;
    }


}
