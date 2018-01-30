package Game;

import Game.Board.BattleshipBoard;
import Game.Ship.PlaneCarrier;
import Game.Ship.Ship;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matthieu Le Boucher
 */
public class Player {
    /**
     * List of the ships owned by the player.
     */
    private List<Ship> ships;

    /**
     * The player's name.
     */
    private String name;

    /**
     * The board on which the player is playing.
     */
    private BattleshipBoard board;

    public Player(String name, BattleshipBoard board) {
        this.name = name;
        this.board = board;

        this.ships = new ArrayList<>();
        this.ships.add(new PlaneCarrier(Ship.Orientation.VERTICAL));
    }

    /**
     * @return The player's name.
     */
    public String getName() {
        return name;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public BattleshipBoard getBoard() {
        return board;
    }
}
