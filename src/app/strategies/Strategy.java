package app.strategies;

import app.models.Board;
import app.models.player.Player;

/**
 * Created by cristian on 29.10.2016.
 */
public abstract class Strategy {

    protected Board board;
    protected Player me;
    protected Player opponent;

    public Strategy(Board board, Player player){
        this.board = board;
        this.me = player;
        this.opponent = board.getLastPlayer();
    }

    /**
     *
     * @return next move
     */
    public int getMove() {
        int col = applyStrategy();
        return col;
    }


    abstract int applyStrategy();
}
