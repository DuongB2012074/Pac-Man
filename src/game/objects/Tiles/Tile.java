package game.objects.Tiles;

import game.objects.GameObject;

public abstract class Tile extends GameObject{
    protected final int x;
    protected final int y;

    public Tile(int x, int y){
        this.x=x;
        this.y=y;
    }
}
