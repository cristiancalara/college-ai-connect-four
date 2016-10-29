package app.models;

import java.util.Scanner;

/**
 * HumanPlayer
 */
public class HumanPlayer extends Player {

    public HumanPlayer(String name, char piece){
        super(piece, name);
    }

    /**
     *
     * @return column number to add the tile
     */
    public int move(){
        Scanner input = new Scanner(System.in);
        System.out.print("Player " + getName() + " turn: ");
        return input.nextInt();
    }
}
