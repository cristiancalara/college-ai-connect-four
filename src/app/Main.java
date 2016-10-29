package app;

import app.models.HumanPlayer;
import app.models.Player;

public class Main {

    public static void main(String[] args) {
        Player player1 = new HumanPlayer("John", 'X');
        Player player2 = new HumanPlayer("Max", 'O');

        ConnectFour game = new ConnectFour(player1, player2);
        game.play();
    }
}
