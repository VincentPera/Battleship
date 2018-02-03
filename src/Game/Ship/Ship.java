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
     * The name of the ship.
     */
    protected String name;

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
        if(((this.orientation == Orientation.VERTICAL) && this.x == x)||((this.orientation == Orientation.HORIZONTAL) && this.y == y)){     // Case when the target is aligned with our ship
            int alignedDistance = this.orientation == Orientation.VERTICAL ? Math.abs(this.y - y) : Math.abs(this.x - x);
            return (alignedDistance<=this.getRange());
        }else{                                                                                                                              // When our target is on our side
            if(this.getOrientation() == Orientation.HORIZONTAL){
                int maxX = this.x + this.getLength();
                if((this.x <= x)&&(x <= maxX)){                                                                                             // if our target is on the length of the ship
                    int maxY = this.y + this.getRange();
                    int minY = this.y - this.getRange();
                    if((minY <= y)&&(y <= maxY)){                                                                                           // if our target is close enough
                        return true;
                    }
                }
            }else{                                                                                                                          // same for VERTICAL case
                int maxY = this.y + this.getLength();
                if((this.y <= y)&&(y <= maxY)){
                    int maxX = this.x + this.getRange();
                    int minX = this.x - this.getRange();
                    if((minX <= x)&&(x <= maxX)){
                        return true;
                    }
                }
            }
        }
        return false;                                                                                                                       // Default case
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

    public String getName() {
        return name;
    }
}
