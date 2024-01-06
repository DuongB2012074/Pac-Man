package game;
import java.awt.Graphics2D;

import game.objects.GameObject;
import game.objects.Tiles.Air;
import game.objects.Tiles.Block;
import game.objects.Tiles.Coin;
import game.objects.Tiles.Tile;

public class GameMap extends GameObject{
    private static final int[][] DEFAULT_MAP = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 1, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 1},
        {1, 2, 1, 1, 1, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 1, 1, 1, 2, 1},
        {1, 2, 1, 2, 2, 2, 1, 2, 2, 2, 1, 0, 2, 2, 2, 0, 0, 2, 2, 2, 1, 2, 2, 2, 1, 2, 1},
        {1, 2, 1, 2, 1, 2, 1, 2, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 2, 1, 2, 1, 2, 1, 2, 1},
        {1, 2, 2, 2, 1, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 1, 2, 2, 2, 1},
        {1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1},
        {1, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 1},
        {1, 2, 1, 2, 1, 2, 1, 2, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 2, 1, 2, 1, 2, 1, 2, 1},
        {1, 2, 1, 2, 1, 2, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 2, 1, 2, 1, 2, 1},
        {1, 2, 1, 2, 1, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 1, 2, 1, 2, 1},
        {1, 2, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 2, 1},
        {1, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 1},
        {1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 1, 1, 1, 2, 1, 2, 1, 2, 1, 2, 1},
        {1, 2, 1, 2, 2, 2, 1, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 1, 2, 2, 2, 1, 2, 1},
        {1, 2, 1, 1, 1, 2, 1, 2, 1, 2, 1, 2, 1, 1, 1, 2, 1, 2, 1, 2, 1, 2, 1, 1, 1, 2, 1},
        {1, 2, 2, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2, 2, 2, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    private final int tileSize;

    private final Tile[][] tiles;

    public GameMap(int tileSize){
        this.tileSize = tileSize;
        tiles = new Tile[DEFAULT_MAP.length][DEFAULT_MAP[0].length];
        reset();
        
    }

    public void reset() {
        for (int y=0; y < getHeight(); y++){
            for (int x=0; x < getWidth(); x++){
                int tileID =DEFAULT_MAP[y][x];
                // tiles[y][x] = switch (DEFAULT_MAP[y][x]){
                //     case 1 -> new Block(x, y);
                //     case 2 -> new Coin(x, y);
                //     default -> new Air(x, y);  
                // };
                //bảng hiển thị các hình ảnh map trên cửa sổ: 0 là đường đi, 1 là tường, 2 là đường đi có xu
                if (tileID == 0){
                    tiles[y][x] = new Air(x, y);
                }else if (tileID == 1){
                    tiles[y][x] = new Block(x, y);
                }else if (tileID == 2){
                    tiles[y][x] = new Coin(x, y);
                }
            }
        }
    }

    @Override
    public void render(Graphics2D g, int tileSize) {
        for (Tile[] row : tiles){
            for (Tile tile : row){
                tile.render(g, tileSize);
            }
        }
    }

    public int coinCount() {
        int sum = 0;
        
        for(Tile[] row : tiles){
            for(Tile tile : row ){
                if(tile instanceof Coin){
                    sum++;
                }
            }
        }

        return sum;
    }

    public boolean isFree(int x, int y){
        if (x < 0 || y < 0 || x >= getWidth() || y >= getHeight()){
            return false;
        }

        return !(tiles[y][x] instanceof Block);
    }

    public int getWidth(){
        return tiles[0].length;
    }
    public int getHeight(){
        return tiles.length;
    }
    public int getTileSize() {
        return tileSize;
    }
    public Tile getTile(int x, int y) {
        return tiles[y][x];
    }
    public void setTile(int x, int y, Tile tile){
        tiles[y][x] = tile;
    }
    
    

   

}
