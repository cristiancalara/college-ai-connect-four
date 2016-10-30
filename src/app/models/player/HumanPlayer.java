package app.models.player;

import app.models.Board;

import java.util.Scanner;

/**
 * Created by cristian on 29.10.2016.
 */
public class HumanPlayer extends Player {

    public HumanPlayer(String name, char piece){
        super(piece, name);
    }

    /**
     * Gets human user move
     *
     * @param board
     */
    public int getMove(Board board){
        Scanner input = new Scanner(System.in);
        return input.nextInt();
    }
}
