package game.objects.creatures;

import java.awt.*;

import game.Game;
import game.GameMap;
import game.objects.GameObject;

public abstract class Creature extends GameObject{
    protected final Game game;
    protected double centerX;
    protected double centerY;
    protected final double radius;
    protected final double speed;
    protected Color color;

    //hướng đi tọa độ X
    protected int preferredDirectionX;
    protected int movingDirectionX;
    //hướng đi tọa độ Y
    protected int preferredDirectionY;
    protected int movingDirectionY;
    //giá trị ban đầu để thực hiện việc reset
    private final double initialX;
    private final double initialY;
    
    public Creature(Game game, double centerX, double centerY, double radius, double speed, Color color){
        this.game = game;
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.speed = speed;
        this.color = color;

        initialX = centerX;
        initialY = centerY;
    }

    public void reset() {
        centerX = initialX;
        centerY = initialY;
        preferredDirectionX = 0;
        preferredDirectionY = 0;
        movingDirectionX = 0;
        movingDirectionY = 0;

    }

    private void tickmovingDirection(){
        if (movingDirectionX == 0 && movingDirectionY == 0){
            movingDirectionX = preferredDirectionX;
            movingDirectionY = preferredDirectionY;
        }else if(movingDirectionX != 0 && preferredDirectionX != 0){
            movingDirectionX = preferredDirectionX;
        }else if(movingDirectionY != 0 && preferredDirectionY != 0){
            movingDirectionY = preferredDirectionY;
        }
    }

    private void snapX(){
        centerX = (int) centerX + 0.5;
        movingDirectionX = 0;
    }
    private void snapY(){
        centerY = (int) centerY + 0.5;
        movingDirectionY = 0;
    }

    //hàm va chạm tường
    private void tickWallCollision(){
        GameMap map = game.getMap();

        if (movingDirectionX == 1 && !map.isFree((int)(centerX + 0.5), (int)centerY) 
            || movingDirectionX == -1 && !map.isFree((int)(centerX - 0.5), (int)centerY)){
                snapX();
        }else if (movingDirectionY == 1 && !map.isFree((int)centerX, (int)(centerY + 0.5)) 
            || movingDirectionY == -1 && !map.isFree((int)centerX, (int)(centerY - 0.5))){
                snapY();
            }
    }

    protected abstract void tickPreferredDirection();

    private void tickTurn(boolean crossedCenterX, boolean crossedCenterY) {
        boolean turnXtoY = crossedCenterX && movingDirectionX != 0 && preferredDirectionY != 0 && game.getMap().isFree((int)centerX, (int)(centerY + preferredDirectionY));
        boolean turnYtoX = crossedCenterY && movingDirectionY != 0 && preferredDirectionX != 0 && game.getMap().isFree((int)(centerX + preferredDirectionX), (int)(centerY));
        
        if(turnXtoY){
            snapX();
            movingDirectionY = preferredDirectionY;
        }else if (turnYtoX) {
            snapY();
            movingDirectionX = preferredDirectionX;
        }
    }

    public void tick(){
        tickmovingDirection();

        double newX = centerX + movingDirectionX * speed;
        double newY = centerY + movingDirectionY * speed;

        boolean crossedCenterX = Math.abs((centerX - 0.5) % 1.0 - (newX - 0.5) % 1.0) > 0.5;
        boolean crossedCenterY = Math.abs((centerY - 0.5) % 1.0 - (newY - 0.5) % 1.0) > 0.5;

        centerX = newX;
        centerY = newY;

        if(crossedCenterX || crossedCenterY){
            tickPreferredDirection();
            tickTurn(crossedCenterX, crossedCenterY);
        }

        
        tickWallCollision();
    }

    

    public double getCenterX(){
        return centerX;
    }
    public double getCenterY(){
        return centerY;
    }
    public double getRadius(){
        return radius;
    }

    public int getMovingDirectionX() {
        return movingDirectionX;
    }

    public int getMovingDirectionY() {
        return movingDirectionY;
    }
}
