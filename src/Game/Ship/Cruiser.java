package Game.Ship;

/**
 * @author Matthieu Le Boucher
 */
public class Cruiser extends Ship {
    public Cruiser(Orientation orientation) {
        this.orientation = orientation;
        this.name = "Cruiser";
        this.length = 4;
        this.currentHealth = 4;
        this.range = 2;
        this.name = "Croiseur";
    }
}
