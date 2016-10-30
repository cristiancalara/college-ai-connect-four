package app.models;

import app.models.player.Player;

import java.util.Arrays;

/**
 * Created by cristian on 29.10.2016.
 */
public class Board {
    private final char emptyPiece = '.';
    private final int width = 7;
    private final int height = 6;


    private char[][] grid;
    private int lastCol = -1;
    private int lastTop = -1;
    private Player lastPlayer = null;


    public Board() {
        this.grid = new char[this.height][];

        for (int h = 0; h < this.height; h++) {
            Arrays.fill(this.grid[h] = new char[this.width], emptyPiece);
        }
    }

    /**
     * Used to duplicate a board, and make
     * a additional move
     *
     * @param board
     * @param player
     * @param moveCol
     */
    public Board(Board board, Player player, int moveCol) {
        this.grid = new char[this.height][this.width];

        char[][] oldGrid = board.getGrid();
        for(int i = 0; i < this.height; i++){
            for(int j = 0; j < this.width; j++){
                this.grid[i][j] = oldGrid[i][j];
            }
        }

        this.lastCol = board.getLastCol();
        this.lastTop = board.getLastTop();

        this.addPiece(moveCol, player);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public char[][] getGrid() {
        return grid;
    }

    public int getLastCol() {
        return lastCol;
    }

    public int getLastTop() {
        return lastTop;
    }

    public Player getLastPlayer() {
        return lastPlayer;
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
     * @param col - player's choice for the column
     * @param player - the player that made the move
     * @return true if we could add a piece to the board, false otherwise
     */
    public boolean addPiece(int col, Player player) {
        if (!(0 <= col && col < this.width) || isFull()) {
            return false;
        }

        // try to find a empty space in the column given
        // and update last state
        for (int h = this.height - 1; h >= 0; h--) {
            if (this.grid[h][col] == emptyPiece) {
                this.lastCol = col;
                this.lastTop = h;
                this.lastPlayer = player;

                this.grid[this.lastTop][this.lastCol] = player.piece();

                return true;
            }
        }


        return false;
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

    /**
     * Checks to see if the last piece added made
     * a streak, vertically, horizontally, or diagonally
     *
     * @return true if the last piece added made
     * a streak, false otherwise
     */
    public boolean hasWinner() {
        if (this.lastCol == -1) {
            return false;
        }

        char piece = this.grid[this.lastTop][this.lastCol];
        String streak = String.format("%c%c%c%c", piece, piece, piece, piece);
        return this.horizontalLine(this.lastTop).contains(streak) ||
                this.verticalLine(this.lastCol).contains(streak) ||
                this.slashDiagonal(this.lastTop, this.lastCol).contains(streak) ||
                this.backslashDiagonal(this.lastTop, this.lastCol).contains(streak);
    }


    /**
     * The contents of the row containing the last played piece.
     */
    public String horizontalLine(int row) {
        return new String(this.grid[row]);
    }

    /**
     * The contents of the column containing the last played piece.
     */
    public String verticalLine(int col) {
        StringBuilder sb = new StringBuilder(this.height);
        for (int h = 0; h < this.height; h++) {
            sb.append(this.grid[h][col]);
        }
        return sb.toString();
    }

    /**
     * The contents of the "/" diagonal containing the last played piece
     * (coordinates have a constant sum).
     */
    public String slashDiagonal(int row, int col) {
        StringBuilder sb = new StringBuilder(this.height);
        for (int h = 0; h < this.height; h++) {
            int w = col + row - h;
            if (0 <= w && w < this.width) {
                sb.append(this.grid[h][w]);
            }
        }
        return sb.toString();
    }

    /**
     * Checks for a streak like "\" for the
     * given piece
     *
     * @param row
     * @param col
     * @return true, if we have a streak
     */
    public String backslashDiagonal(int row, int col) {
        StringBuilder sb = new StringBuilder(this.height);
        for (int h = 0; h < this.height; h++) {
            int w = col - row + h;
            if (0 <= w && w < this.width) {
                sb.append(this.grid[h][w]);
            }
        }
        return sb.toString();
    }

    /**
     * Checks if we can add a piece to the
     * given column
     *
     * @param col
     * @return
     */
    public boolean canAddAtColumn(int col){
        return isColumnValid(col) && !isColumnFull(col);
    }

    /**
     *
     * @param col
     * @return true if column is full, false otherwise
     */
    public boolean isColumnFull(int col) {
        for (int i = 0; i < height; i++) {
            if (grid[i][col] == emptyPiece) {
                return false;
            }
        }

        return true;
    }

    /**
     *
     * @param col
     * @return true if column is valid, false otherwise
     */
    public boolean isColumnValid(int col){
        if (!(0 <= col && col < this.width)) {
            return false;
        }

        return true;
    }
}
