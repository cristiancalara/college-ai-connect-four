package app.models.player;

import app.models.Board;
import app.strategies.AlphaBetaPruning;
import app.strategies.Minimax;
import app.strategies.Strategy;
import app.strategies.StrategyType;

/**
 * Created by cristian on 29.10.2016.
 */
public class ComputerPlayer extends Player {

    private StrategyType strategyType;

    public ComputerPlayer(String name, char piece, StrategyType strategyType){
        super(piece, name);

        this.strategyType = strategyType;
    }

    /**
     * Gets computer user move using
     * strategy given
     *
     * @param board
     */
    public int getMove(Board board){
        Strategy strategy;
        if(strategyType == StrategyType.MINIMAX){
            strategy = new Minimax(board, this);
        } else {
            strategy = new AlphaBetaPruning(board, this);
        }


        return strategy.getMove();
    }
}
