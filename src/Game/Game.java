package Game;

import Game.Ship.Ship;

/**
 * @author Matthieu Le Boucher
 */
public class Game {
    private static final int PLAYERS_AMOUNT = 2;

    public static void main(String[] args) {
        Player[] players = new Player[PLAYERS_AMOUNT];

        // Todo: setup the game boards.

        // Initialize players list.
        players[0] = new Player("Matt");
        players[1] = new Player("Bot");

        assert players.length == PLAYERS_AMOUNT;

        // Game loop setup.
        boolean gameOver = false;
        int currentPlayerID = 0;

        // Setup turn to allow players to positionate their ships.
        for (int i = 0; i < players.length; i++) {
            for (Ship ship : players[i].getShips()) {

            }
        }

        // Game loop.
        while(!gameOver) {
            // Ask for
            // Update current player ID to next player.
            currentPlayerID = (currentPlayerID + 1) % PLAYERS_AMOUNT;
        }
    }
}
