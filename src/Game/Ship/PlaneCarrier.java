package Game.Ship;

/**
 * @author Matthieu Le Boucher
 */
public class PlaneCarrier extends Ship {
    public PlaneCarrier(Orientation orientation) {
        this.orientation = orientation;
        this.length = 5;
        this.currentHealth = 5;
        this.range = 2;
        this.name = "Porte-avion";
    }
}
