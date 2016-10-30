package app;

import app.models.Board;
import app.models.player.Player;

/**
 * Created by cristian on 29.10.2016.
 */
public class ConnectFour {

    private Player currentPlayer;
    private Player player1;
    private Player player2;
    private Board board;

    public ConnectFour(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;

        this.currentPlayer = player1;

        this.board = new Board();
    }


    /**
     * Runs the game, and prints the
     * result
     */
    public void play(){
        int col;

        while (!board.hasWinner() && !board.isFull()){
            col = currentPlayer.move(board, currentPlayerOpponent());

            System.out.println("\nAdd piece on column number: " + col);
            System.out.println("Updated board ");
            System.out.print(board);
            System.out.println("================");

            changeTurn();
        }

        printOutcome();
    }

    /**
     * Prints game outcome
     */
    private void printOutcome() {
        System.out.println("Final board:");
        System.out.println(board);
        if(board.hasWinner()){
            Player winner = board.getWinner();
            System.out.println("Winner is: " + winner.getName());
        } else {
            System.out.println("It's a TIE.");
        }
    }

    /**
     * Changes the current player
     */
    private void changeTurn(){
        currentPlayer = currentPlayer == player1 ? player2 : player1;
    }

    /**
     * Returns other player
     *
     * @return player
     */
    private Player currentPlayerOpponent(){
        return currentPlayer == player1 ? player2 : player1;
    }
}
