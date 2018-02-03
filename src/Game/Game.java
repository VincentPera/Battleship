package Game;

import Game.Board.BattleshipBoard;
import Game.Ship.Exception.InvalidAmountOfPlayersException;
import Game.Ship.Ship;

/**
 * @author Matthieu Le Boucher
 */
public class Game {
    public static final int BOARD_SIZE = 10;
    public static final int PLAYERS_AMOUNT = 2;

    private boolean gameOver = false;

    public Game(String... playerNames) throws InvalidAmountOfPlayersException {
        if(playerNames.length != PLAYERS_AMOUNT)
            throw new InvalidAmountOfPlayersException();


        Player[] players = new Player[PLAYERS_AMOUNT];

        // Initialize players list.
        for (int i = 0; i < PLAYERS_AMOUNT; i++) {
            players[i] = new Player(playerNames[i], new BattleshipBoard(BOARD_SIZE));
        }

        // Game loop setup.
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
