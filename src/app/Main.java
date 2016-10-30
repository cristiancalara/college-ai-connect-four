package app;

import app.models.player.ComputerPlayer;
import app.models.player.HumanPlayer;
import app.models.player.Player;
import app.strategies.StrategyType;

public class Main {

    public static void main(String[] args) {
        Player player1 = new HumanPlayer("Human", 'X');
        Player player2 = new ComputerPlayer("Computer", 'O', StrategyType.ALPHABETAPRUNING);

        ConnectFour game = new ConnectFour(player1, player2);
        game.play();
    }
}
