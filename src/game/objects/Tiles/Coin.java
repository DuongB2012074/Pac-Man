package game.objects.Tiles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class Coin extends Tile{

    protected final double radius;

    protected Coin(int x, int y, double radius) {
        super(x, y);
        this.radius = radius;
    }

    public Coin(int x, int y){
        this(x, y, 0.125);
    }

    @Override
    public void render(Graphics2D g, int tileSize) {
        double centerXOnScreen = getCenterX() * tileSize;
        double centerYOnScreen = getCenterY() * tileSize;
        double radiusOnScreen = radius * tileSize;
        double diameterOnScreen = radiusOnScreen * 2.0;

        g.setColor(Color.yellow);
        //trung tâm của tọa độ trên màn hình - centerOnScreen
        //bán kính trên màn hình - radiusOnScreen
        //đường kính trên màn hình - diameterOnScreen
        g.fill(new Ellipse2D.Double(centerXOnScreen - radiusOnScreen, centerYOnScreen - radiusOnScreen, diameterOnScreen, diameterOnScreen));
    }

    public double getCenterX(){
        return x + 0.5;
    }
    public double getCenterY(){
        return y + 0.5;
    }

    public double getRadius() {
        return radius;
    }
    
    
}
