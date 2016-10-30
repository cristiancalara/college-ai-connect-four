package app.strategies;

import app.models.Board;
import app.models.player.Player;
import app.models.State;

import java.util.List;

/**
 * Created by cristian on 29.10.2016.
 */
public class AlphaBetaPruning extends Strategy {

    public AlphaBetaPruning(Board board, Player player){
        super(board, player);
    }

    /**
     * Applies strategy, and returns result
     *
     * @return
     */
    protected int applyStrategy(){
        State initialState = new State(board, me);
        int[] result = minimaxWithAlphaBetaPruning(2, me, initialState, Integer.MIN_VALUE, Integer.MAX_VALUE); // depth, max turn

        return result[1];
    }
    /**
     * Recursive alpha beta pruning
     *
     * @param depth
     * @param player
     * @param currentState
     * @return
     */
    private int[] minimaxWithAlphaBetaPruning(int depth, Player player, State currentState, int alpha, int beta) {
        List<State> nextMoves = currentState.nextMoves();

        // me is maximizing; while opponent is minimizing
        int currentScore;
        int bestCol = -1;

        if (nextMoves.isEmpty() || depth == 0) {
            // board is full, or we reached the depth
            // evaluate using the initial players
            currentScore = currentState.evaluate(me.piece(), opponent.piece());
            return new int[] {currentScore, bestCol};
        } else {
            if (player == me) {  // me (computer) is maximizing player
                for (State move : nextMoves) {
                    currentScore = minimaxWithAlphaBetaPruning(depth - 1, opponent, move, alpha, beta)[0];
                    if (currentScore > alpha) {
                        alpha = currentScore;
                        bestCol = move.getCol();
                    }

                    // we know that MIN has a better choice, as beta is smaller than alpha
                    // so MIN will never chose this branch
                    if (alpha >= beta) break;  // beta cut-off
                }
            } else {  // opponent is minimizing player
                for (State move : nextMoves) {
                    currentScore = minimaxWithAlphaBetaPruning(depth - 1, me, move, alpha, beta)[0];
                    if (currentScore < beta) {
                        beta = currentScore;
                        bestCol = move.getCol();
                    }

                    // we know that MAX has a better choice, as alpha is bigger than beta
                    // so MAX will never chose this branch
                    if (alpha >= beta) break;   // alpha cut-off
                }
            }

            return new int[] {(player == me) ? alpha : beta, bestCol};
        }
    }
}
