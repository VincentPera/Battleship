package Game.Ship;

import Game.Ship.Exception.InvalidMoveException;

/**
 * @author Matthieu Le Boucher
 */
public abstract class Ship {
    /**
     * Enumeration of the possible orientations of ships.
     */
    public enum Orientation { VERTICAL, HORIZONTAL }

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
     * The color of the ship in hexadecimal format.
     */
    protected String color = "#353DFF";

    /**
     * Current position of the ship.
     */
    public int x, y;

    public boolean placed = false;

    /**
     * @return The current health of the ship.
     */
    public int getCurrentHealth() {
        return currentHealth;
    }

    public String toString(){
        if(this.getOrientation() == Orientation.HORIZONTAL){
            return "HORIZONTAL";
        }else{
            return "VERTICAL";
        }
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

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public boolean canAttack(int x, int y) {
       /* System.out.println("x : "+x);
        System.out.println("y : "+y);
        System.out.println("OR : "+this.toString());
        System.out.println("my x : "+this.x);
        System.out.println("my y : "+this.y);*/
        if(((this.orientation == Orientation.VERTICAL) && this.x == x)||((this.orientation == Orientation.HORIZONTAL) && this.y == y)){
            //System.out.println("CAS1");                                                                                                     // Case when the target is aligned with our ship
            if(this.getOrientation() == Orientation.VERTICAL){
                return (this.y - this.getRange() <= y && y <= this.y+this.getLength()+this.getRange()-1);
            }else{
                return (this.x - this.getRange() <= x && x <= this.x+this.getLength()+this.getRange());
            }
        }else{
           // System.out.println("CAS2");   // When our target is on our side
            if(this.getOrientation() == Orientation.HORIZONTAL){
               // System.out.println("CAS2/a");
                int maxX = this.x + this.getLength()-1;
                if((this.x <= x)&&(x <= maxX)){                                                                                             // if our target is on the length of the ship
                    int maxY = this.y + this.getRange();
                    int minY = this.y - this.getRange();
                    if((minY <= y)&&(y <= maxY)){                                                                                           // if our target is close enough
                        return true;
                    }
                }
            }else{
               // System.out.println("CAS2/b");// same for VERTICAL case
                int maxY = this.y + this.getLength()-1;
                if((this.y <= y)&&(y <= maxY)){
                    int maxX = this.x + this.getRange();
                    int minX = this.x - this.getRange();
                    if((minX <= x)&&(x <= maxX)){
                        return true;
                    }
                }
            }
        }
        return false;                                                                                                                     // Default case
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

    public String getColor() {
        return color;
    }
    public void decreaseLife() {
        System.out.println("OSCOUR");
        this.currentHealth--;
    }

    public boolean checkDistance(int x, int y){
        if(this.getOrientation() == Orientation.VERTICAL){
            System.out.println("VERTICAL");
            System.out.println("My x : "+this.x);
            System.out.println("To x : "+x);
            System.out.println("My y : "+this.y);
            System.out.println("To y : "+y);
            return (this.x == x && (Math.abs(this.y - y)<= 2));
        }else{
            return (this.y == y && (Math.abs(this.x - x)<= 2));
        }
    }

}
