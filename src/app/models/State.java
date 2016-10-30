package app.models;

import app.models.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cristian on 29.10.2016.
 */
public class State {

    private Board board;
    private Player player;


    public State(Board board, Player player){
        this.board = board;
        this.player = player;
    }

    /**
     * The player that made the last move on this board
     * is clearly the opponent
     *
     * @return player that made the last move on this board
     */
    public Player getOpponent(){
        return this.board.getLastPlayer();
    }

    /**
     *
     * @return last move made
     */
    public int getCol(){
        return this.board.getLastCol();
    }

    /**
     * Get all next possible moves
     *
     * @return all next possible moves, for the opponent
     */
    public List<State> nextMoves(){
        List<State> nextMoves = new ArrayList<>();

        if(this.board.hasWinner()){
            return nextMoves;
        }

        for(int i = 0; i < this.board.getWidth(); i++){
            if(this.board.canAddAtColumn(i)){
                // create the new board making our move, at column i
                Board newBoard = new Board(this.board, this.player, i);

                State newState = new State(newBoard, this.getOpponent());
                nextMoves.add(newState);
            }

        }
        return nextMoves;
    }


    /**
     * Heuristic function to evaluate the leaf moves
     *
     * @return a score based on existing streaks
     */
    public int evaluate(char initialMe, char initialOpponent) {
        // player streaks by 4, 3, 2, 1 then
        // opponent streaks by 4, 3, 2, 1
        // last item is a special edge case
        int [] possibleStreaks = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};


        // evaluate vertical lines
        for(int i = 0; i < this.board.getWidth(); i++){
            String line = this.board.verticalLine(i);
            possibleStreaks = possibleStreaks(line, possibleStreaks, initialMe, initialOpponent);
        }

        // evaluate horizontal lines
        for(int i = 0; i < this.board.getHeight(); i++){
            String line = this.board.horizontalLine(i);
            possibleStreaks = possibleStreaks(line, possibleStreaks, initialMe, initialOpponent);
        }

        // evaluate slash lines
        int col = this.board.getWidth() - 1;
        for(int i = 0; i < this.board.getWidth(); i++){
            String line = this.board.slashDiagonal(0, i);
            possibleStreaks = possibleStreaks(line, possibleStreaks, initialMe, initialOpponent);
        }
        for(int i = 0; i < this.board.getHeight(); i++){
            String line = this.board.slashDiagonal(i, this.board.getWidth() - 1);
            possibleStreaks = possibleStreaks(line, possibleStreaks, initialMe, initialOpponent);
        }

        // evaluate backslash lines
        for(int i = 0; i < this.board.getWidth(); i++){
            String line = this.board.backslashDiagonal(0, i);
            possibleStreaks = possibleStreaks(line, possibleStreaks, initialMe, initialOpponent);
        }
        for(int i = 0; i < this.board.getHeight(); i++){
            String line = this.board.slashDiagonal(i, 0);
            possibleStreaks = possibleStreaks(line, possibleStreaks, initialMe, initialOpponent);
        }


        // edge cases to handle win/lost situation
        if(possibleStreaks[0] > 0){
            return 1000;
        } else if(possibleStreaks[4] > 0){
            return -1000;
        }

        // has double ended win game situation
        // e.g.
        // |.|.|.|.|.|.|.|
        // |.|.|.|.|.|.|.|
        // |.|.|.|.|.|.|.|
        // |.|.|.|.|.|.|.|
        // |.|.|O|O|.|.|.|
        // |.|.|X|X|.|.|.|
        if(possibleStreaks[8] > 0){
            return -500;
        }

        // general score function
        // 8*B3 + 4*B2 + B1 - (8*R3 + 4*R2 + R1)
        int myScore = 8 * possibleStreaks[1] + 4 * possibleStreaks[2] + possibleStreaks[3];
        int opponentScore = 18 * possibleStreaks[5] + 4 * possibleStreaks[6] + possibleStreaks[7];

        return myScore - opponentScore;
    }


    /**
     * Returns number of 4,3,2,1 streaks for the players
     *
     * @param line
     * @param currentPossibleStreaks
     * @return
     */
    private int[] possibleStreaks(String line, int[] currentPossibleStreaks, char initialMe, char initialOpponent){
        currentPossibleStreaks[0] += has4Streak(initialMe, line) ? 1 : 0;
        currentPossibleStreaks[1] += has3Streak(initialMe, line) ? 1 : 0;
        currentPossibleStreaks[2] += has2Streak(initialMe, line) ? 1 : 0;
        currentPossibleStreaks[3] += has1Streak(initialMe, line) ? 1 : 0;

        currentPossibleStreaks[4] += has4Streak(initialOpponent, line) ? 1 : 0;
        currentPossibleStreaks[5] += has3Streak(initialOpponent, line) ? 1 : 0;
        currentPossibleStreaks[6] += has2Streak(initialOpponent, line) ? 1 : 0;
        currentPossibleStreaks[7] += has1Streak(initialOpponent, line) ? 1 : 0;

        // edge case
        currentPossibleStreaks[8] += hasDoubleEnded2Streak(initialOpponent, line) ? 1 : 0;

        return currentPossibleStreaks;
    }

    /**
     * Checks for a edge case where user has double ended streak
     * game win possibility
     *
     * @param piece
     * @param line
     * @return
     */
    private boolean hasDoubleEnded2Streak(char piece, String line){
        String possibleStreak1 = String.format("..%c%c.", piece, piece);
        String possibleStreak2 = String.format(".%c%c..", piece, piece);

        return line.contains(possibleStreak1) ||
                line.contains(possibleStreak2);
    };
    /**
     * Returns if user has 4 streak (won)
     *
     * @param piece
     * @param line
     * @return
     */
    private boolean has4Streak(char piece, String line){
        String possibleStreak = String.format("%c%c%c%c", piece, piece, piece, piece);

        return line.contains(possibleStreak);
    }

    /**
     *
     * @param piece
     * @param line
     * @return
     */
    private boolean has3Streak(char piece, String line){
        String possibleStreak1 = String.format(".%c%c%c", piece, piece, piece);
        String possibleStreak2 = String.format("%c%c%c.", piece, piece, piece);

        return line.contains(possibleStreak1) ||
                line.contains(possibleStreak2);
    }

    /**
     *
     * @param piece
     * @param line
     * @return
     */
    private boolean has2Streak(char piece, String line){
        String possibleStreak1 = String.format("..%c%c", piece, piece);
        String possibleStreak2 = String.format("%c%c..", piece, piece);
        String possibleStreak3 = String.format(".%c%c.", piece, piece);

        return line.contains(possibleStreak1) ||
                line.contains(possibleStreak2) ||
                line.contains(possibleStreak3);
    }

    /**
     *
     * @param piece
     * @param line
     * @return
     */
    private boolean has1Streak(char piece, String line){
        String possibleStreak1 = String.format("...%c", piece);
        String possibleStreak2 = String.format("..%c.", piece);
        String possibleStreak3 = String.format(".%c..", piece);
        String possibleStreak4 = String.format("%c...", piece);

        return line.contains(possibleStreak1) ||
                line.contains(possibleStreak2) ||
                line.contains(possibleStreak3) ||
                line.contains(possibleStreak4);
    }
}
