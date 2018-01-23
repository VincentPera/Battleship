package Game.Ship;

/**
 * @author Matthieu Le Boucher
 */
public class CounterTorpedoBoat extends Ship {
    public CounterTorpedoBoat(Orientation orientation) {
        this.orientation = orientation;
        this.length = 3;
        this.currentHealth = 3;
        this.range = 2;
    }
}
