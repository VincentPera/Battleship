package Game.Ship;

import Game.Ship.Exception.InvalidMoveException;

/**
 * @author Matthieu Le Boucher
 */
public abstract class Ship {
    /**
     * Enumeration of the possible orientations of ships.
     */
    public enum Orientation { VERTICAL, HORIZONTAL };

    /**
     * Length of the ship.

     */
    protected int length;

    /**
     * Current health of the ship.
     */
    protected int currentHealth;

    /**
     * Range of the ship.
     */
    protected int range;

    /**
     * Orientation of the ship.
     */
    protected Orientation orientation;

    /**
     * Current position of the ship.
     */
    public int x, y;

    /**
     * @return The current health of the ship.
     */
    public int getCurrentHealth() {
        return currentHealth;
    }

    /**
     * @return The range of the ship.
     */
    public int getRange() {
        return range;
    }

    /**
     * @return The length of the ship.
     */
    public int getLength() {
        return length;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public boolean canAttack(int x, int y) {
        // Todo: implement this logic.
        return false;
    }

    /**
     * Attempts to move the ship the to the target cell. The ship can only move in line depending on its orientation,
     * and to a maximum distance of two cells.
     *
     * @param x
     * @param y
     * @throws InvalidMoveException If the movement does not respect the rules.
     */
    public void move(int x, int y) throws InvalidMoveException {
        boolean movingInLine =
                (this.orientation == Orientation.VERTICAL && this.x == x)
                        || (this.orientation == Orientation.HORIZONTAL && this.y == y);

        // Compute the step between current and target positions.
        int distance = this.orientation == Orientation.VERTICAL
                ? Math.abs(this.y - y) : Math.abs(this.x - x);

        if(!movingInLine || distance > 2)
            throw new InvalidMoveException();

        // If the movement is valid, change the current position to target position.
        this.x = x;
        this.y = y;
    }
}
