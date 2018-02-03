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
    private int gridSize = 10;

    /**
     * The grid two-dimensional grid of the board.
     */
    private Ship[][] grid;

    public BattleshipBoard(int gridSize) {
        this.gridSize = gridSize;
        this.grid = new Ship[gridSize][gridSize];
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
        boolean isWithinGrid = x >= 0 && x <= gridSize - ship.getLength()
                && y >= 0 && y <= gridSize - ship.getLength();

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
        cleanGridFromShip(ship);
        IntStream.range(0, ship.getLength()).forEach(
                ship.getOrientation() == Ship.Orientation.HORIZONTAL
                        ? i -> this.grid[x + i][y] = ship
                        : i -> this.grid[x][y + i] = ship
        );

        // Update the ship's position.
        ship.x = x;
        ship.y = y;
    }

    private void cleanGridFromShip(Ship ship) {
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                if(grid[x][y] == ship)
                    grid[x][y] = null;
            }
        }
    }

    /**
     * Returns the ship living at (x, y) if it exists.
     *
     * @param x     Assumed to be within [1, gridSize]
     * @param y     Assumed to be within [1, gridSize]
     * @return
     */
    public Ship getShipAt(int x, int y) {
        return grid[x - 1][y - 1];
    }
}
