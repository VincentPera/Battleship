package Game.Board;

import Game.Ship.Exception.InvalidMoveException;
import Game.Ship.Ship;

import java.util.stream.IntStream;

/**
 * @author Matthieu Le Boucher
 */
public class BattleshipBoard {
    /**
     * The size of the game board.
     */
    private final static int GRID_SIZE = 10;

    /**
     * The grid two-dimensional grid of the board.
     */
    private Ship[][] grid;

    public BattleshipBoard() {
        this.grid = new Ship[GRID_SIZE][GRID_SIZE];
    }

    /**
     * Tries to place a ship at a given position.
     *
     * @param ship  The ship to move.
     * @param x     The x coordinate of the target position.
     * @param y     The y coordinate of the target position.
     * @throws InvalidMoveException
     */
    public void placeShipAt(Ship ship, int x, int y) throws InvalidMoveException {
        boolean isWithinGrid = x >= 0 && x <= GRID_SIZE - ship.getLength()
                && y >= 0 && y <= GRID_SIZE - ship.getLength();

        // Check for collision with another ship.
        boolean doesCollide = false;

        doesCollide = IntStream.range(0, ship.getLength()).anyMatch(
                ship.getOrientation() == Ship.Orientation.HORIZONTAL
                        ? i -> this.grid[x + i][y] != null && this.grid[x + i][y] != ship
                        : i -> this.grid[x][y + i] != null && this.grid[x][y + i] != ship
        );

        if(!isWithinGrid || doesCollide)
            throw new InvalidMoveException();

        // If this is a valid position, place the ship along the row or column with respect to its orientation.
        IntStream.range(0, ship.getLength())
                .forEach(ship.getOrientation() == Ship.Orientation.HORIZONTAL
                        ? i -> this.grid[x + i][y] = ship
                        : i -> this.grid[x][y + i] = ship);
    }
}
