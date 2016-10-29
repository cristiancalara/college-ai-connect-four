package app.models;

/**
 * Player
 */
public abstract class Player {

    private final String name;
    private final char piece;

    public Player(char piece, String name){
        this.piece = piece;
        this.name = name;
    }

    public abstract int move();

    public char piece(){
        return this.piece;
    }

    public String getName(){
        return this.name;
    }
}
