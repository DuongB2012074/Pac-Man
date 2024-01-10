package game;

import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

import game.objects.creatures.Player;
import game.objects.creatures.enemy.ChasingEnemy;
import game.objects.creatures.enemy.CuttingEnemy;
import game.objects.creatures.enemy.Enemy;
import game.objects.creatures.enemy.RandomEnemy;

public class Game extends JFrame{

    private final Display display;
    private final GameMap map;
    private final Player player;

    private boolean won;

    private final Enemy[] enemies;


    public Game(){
        super("Pac-Man");

        display = new Display(this);
        map = new GameMap(40);
        player = new Player(this, 13.5, 10.5, 0.375, 0.07);
        addKeyListener(player);

        enemies = new Enemy[]{
            new ChasingEnemy(this, player, 12.5, 8.5, 0.375, 0.06, Color.red),
            new CuttingEnemy(this, player, 13.5, 8.5, 0.375, 0.065, Color.green),
            new RandomEnemy(this, player, 14.5, 8.5, 0.375, 0.07, Color.pink)
        };

        setSize(1080, 760);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);

        startGameLoop();
    }
    private void startGameLoop(){
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            tick();
            display.repaint();
        }, 0L, 1000L / 60L, TimeUnit.MILLISECONDS);   
    }

    private void reset(){
        won = false;
        map.reset();
        player.reset();
        for(Enemy enemy : enemies){
            enemy.reset();
        }
    }

    public void win() {
        won = true;
    }

    public void lose() {
        JOptionPane.showMessageDialog(null, "No, You lose!");
        reset();
    }

    private void tick(){
        if(won){
            JOptionPane.showMessageDialog(null, "Yes, You won!");
            reset();
        }
        player.tick();
        for(Enemy enemy : enemies){
            enemy.tick();
        }
    }

    //hàm vẽ nền cho cửa sổ game
    public void render(Graphics2D g2) {
        g2.setColor(Color.black);
        g2.fillRect(0, 0, getWidth(), getHeight());

        int tileSize = map.getTileSize();

        map.render(g2, tileSize);
        player.render(g2, tileSize);
        for(Enemy enemy : enemies){
            enemy.render(g2, tileSize);
        }
    }

    public GameMap getMap(){
        return map;
    }

    public Enemy[] getEnemies(){
        return enemies;
    }

    public static void main(String[] args) {
        new Game();
    }
    
    

    
}
