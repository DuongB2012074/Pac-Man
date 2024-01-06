package game.objects.creatures;

import java.awt.Color;
import java.awt.Graphics2D;
// import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;

import game.Game;
import game.objects.Tiles.Air;
import game.objects.Tiles.Coin;
import game.objects.creatures.enemy.Enemy;

public class Player extends Creature implements KeyListener{

    public Player(Game game, double centerX, double centerY, double radius, double speed) {
        super(game, centerX, centerY, radius, speed, Color.yellow);
    }

    private void tickCoinCollision() {
        int x = (int) centerX;
        int y = (int) centerY;
        Coin coin = new Coin(x, y);
        if (game.getMap().getTile(x, y) instanceof Coin){
            double dx = coin.getCenterX() - centerX;
            double dy = coin.getCenterY() - centerY;
            double r = coin.getRadius() * radius;

            if(dx * dx + dy * dy < r * r){
                game.getMap().setTile(x, y, new Air(x, y));
                if(game.getMap().coinCount() == 0){
                    game.win();
                }
            }
        }
    }

    @Override
    protected void tickPreferredDirection() {
        for(Enemy enemy : game.getEnemies()){
            enemy.tickPreferredDirection();
        }
    }

    @Override
    public void tick(){
        super.tick();
        tickCoinCollision();
    }


    

    @Override
    public void render(Graphics2D g, int tileSize) {
        double centerXOnScreen = centerX * tileSize;
        double centerYOnScreen = centerY * tileSize;
        double radiusOnScreen = radius * tileSize;
        double diameterOnScreen = radiusOnScreen * 2.0;

        g.setColor(color);
        g.fill(new Ellipse2D.Double(centerXOnScreen - radiusOnScreen, centerYOnScreen - radiusOnScreen, diameterOnScreen, diameterOnScreen));
    }
    //hàm này gọi khi sử dụng 1 nút để nhấn
    @Override
    public void keyTyped(KeyEvent e) {
        //không cần thiết sử dụng
    }
    //gọi khi nhấn 1 nút
    @Override
    public void keyPressed(KeyEvent e) {
        
        if(e.getKeyCode() == KeyEvent.VK_W){
            preferredDirectionX = 0;
            preferredDirectionY = -1;
        }else if(e.getKeyCode() == KeyEvent.VK_A){
            preferredDirectionX = -1;
            preferredDirectionY = 0;
        }else if(e.getKeyCode() == KeyEvent.VK_S){
            preferredDirectionX = 0;
            preferredDirectionY = 1;
        }else if(e.getKeyCode() == KeyEvent.VK_D){
            preferredDirectionX = 1;
            preferredDirectionY = 0;
        }
    }
    //gọi khi thả 1 nút ra
    @Override
    public void keyReleased(KeyEvent e) {
        //không cần thiết sử dụng
    }

    

    

    
    
}
