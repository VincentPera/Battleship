package Game.Ship;

/**
 * @author : Matthieu Le Boucher
 */
public class Submarine extends Ship {
    public Submarine(Orientation orientation) {
        this.orientation = orientation;
        this.length = 3;
        this.currentHealth = 3;
        this.range = 4;
    }
}
