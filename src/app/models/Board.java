package app.models;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Board
 */
public class Board {
    private final char emptyPiece = '.';
    private final int width = 7;
    private final int height = 6;


    private final char[][] grid;
    private int lastCol = -1, lastTop = -1;
    private Player lastPlayer = null;


    public Board() {
        this.grid = new char[this.height][];

        for (int h = 0; h < this.height; h++) {
            Arrays.fill(this.grid[h] = new char[this.width], emptyPiece);
        }
    }

    /**
     * @return formatted board
     */
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                builder.append("|");
                builder.append(grid[i][j]);
            }
            builder.append("|");
            builder.append("\n");
        }

        return builder.toString();
    }

    /**
     * Add a piece to the column specified
     * by the player
     *
     * @param player
     */
    public void addPiece(Player player) {
        do {

            int col = player.move();
            if (!(0 <= col && col < this.width)) {
                System.out.println("Column must be between 0 and " +
                        (this.width - 1));
                continue;
            }

            // try to find a empty space in the column given
            // and update last state
            for (int h = this.height - 1; h >= 0; h--) {
                if (this.grid[h][col] == emptyPiece) {
                    this.lastCol = col;
                    this.lastTop = h;
                    this.lastPlayer = player;

                    this.grid[this.lastTop][this.lastCol] = player.piece();

                    System.out.println("Updated board ");
                    System.out.print(this);
                    System.out.println("=================");

                    return;
                }
            }

            System.out.println("Column " + col + " is full.");
        } while (true);
    }


    /**
     * Returns the player that made the last move, if the
     * board has a winner
     *
     * @return winner
     */
    public Player getWinner() {
        if (this.hasWinner()) {
            return this.lastPlayer;
        }

        return null;
    }

    /**
     * @return true if board is full, false otherwise
     */
    public boolean isFull() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j] == emptyPiece) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean hasWinner() {
        if (this.lastCol == -1) {
            return false;
        }

        char piece = this.grid[this.lastTop][this.lastCol];
        String streak = String.format("%c%c%c%c", piece, piece, piece, piece);
        return this.horizontal().contains(streak) ||
                this.vertical().contains(streak) ||
                this.slashDiagonal().contains(streak) ||
                this.backslashDiagonal().contains(streak);
    }

    /**
     * The contents of the row containing the last played piece.
     */
    private String horizontal() {
        return new String(this.grid[this.lastTop]);
    }

    /**
     * The contents of the column containing the last played piece.
     */
    private String vertical() {
        StringBuilder sb = new StringBuilder(this.height);
        for (int h = 0; h < this.height; h++) {
            sb.append(this.grid[h][this.lastCol]);
        }
        return sb.toString();
    }

    /**
     * The contents of the "/" diagonal containing the last played piece
     * (coordinates have a constant sum).
     */
    private String slashDiagonal() {
        StringBuilder sb = new StringBuilder(this.height);
        for (int h = 0; h < this.height; h++) {
            int w = this.lastCol + this.lastTop - h;
            if (0 <= w && w < this.width) {
                sb.append(this.grid[h][w]);
            }
        }
        return sb.toString();
    }

    /**
     * The contents of the "\" diagonal containing the last played piece
     * (coordinates have a constant difference).
     */
    private String backslashDiagonal() {
        StringBuilder sb = new StringBuilder(this.height);
        for (int h = 0; h < this.height; h++) {
            int w = this.lastCol - this.lastTop + h;
            if (0 <= w && w < this.width) {
                sb.append(this.grid[h][w]);
            }
        }
        return sb.toString();
    }
}
