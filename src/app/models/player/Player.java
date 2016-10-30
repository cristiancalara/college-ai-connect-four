package app.models.player;

import app.models.Board;

/**
 * Created by cristian on 29.10.2016.
 */
public abstract class Player {

    private final String name;
    private final char piece;

    public Player(char piece, String name){
        this.piece = piece;
        this.name = name;
    }

    /**
     * Prints player name, and tries
     * to add a new piece on the board
     *
     * @param board
     * @return the move made
     */
    public int move(Board board, Player opponent){
        System.out.print("Player " + getName() + " turn: ");

        int col;
        boolean added;
        do {
            col = getMove(board);
            added = false;

            // print feedback messages
            if (!board.isColumnValid(col)) {
                System.out.println("Column must be between 0 and " + (board.getWidth() - 1));
                continue;
            }
            if (board.isColumnFull(col)) {
                System.out.println("Column " + col + " is full.");
                continue;
            }

            added = board.addPiece(col, this);
        } while (!added);

        return col;
    }

    public char piece(){
        return this.piece;
    }

    public String getName(){
        return this.name;
    }

    public abstract int getMove(Board board);
}
