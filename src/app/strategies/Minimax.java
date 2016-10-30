package app.strategies;

import app.models.Board;
import app.models.player.Player;
import app.models.State;

import java.util.List;

/**
 * Created by cristian on 29.10.2016.
 */
public class Minimax extends Strategy {

    public Minimax(Board board, Player player){
        super(board, player);
    }

    /**
     * Applies strategy, and returns result
     *
     * @return
     */
    protected int applyStrategy(){
        State initialState = new State(board, me);
        int[] result = minimax(2, me, initialState); // depth, max turn

        return result[1];
    }
    /**
     * Recursive mimimax
     *
     * @param depth
     * @param player
     * @param currentState
     * @return
     */
    private int[] minimax(int depth, Player player, State currentState) {
        List<State> nextMoves = currentState.nextMoves();

        // me is maximizing; while opponent is minimizing
        int bestScore = (player == me) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int currentScore;
        int bestCol = -1;

        if (nextMoves.isEmpty() || depth == 0) {
            // board is full, or we reached the depth
            // evaluate using the initial players
            bestScore = currentState.evaluate(me.piece(), opponent.piece());
        } else {
            if (player == me) { // me (computer) is maximizing player
                for (State move : nextMoves) {
                    currentScore = minimax(depth - 1, opponent, move)[0];
                    if (currentScore > bestScore) {
                        bestScore = currentScore;
                        bestCol = move.getCol();
                    }
                }
            } else {  // opponent is minimizing player
                for (State move : nextMoves) {
                    currentScore = minimax(depth - 1, me, move)[0];
                    if (currentScore < bestScore) {
                        bestScore = currentScore;
                        bestCol = move.getCol();
                    }
                }
            }
        }
        return new int[] {bestScore, bestCol};
    }
}
