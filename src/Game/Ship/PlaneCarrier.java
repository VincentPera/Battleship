package Game.Ship;

/**
 * @author Matthieu Le Boucher
 */
public class PlaneCarrier extends Ship {
    public PlaneCarrier(Orientation orientation) {
        this.orientation = orientation;
        this.name = "Plane Carrier";
        this.length = 5;
        this.currentHealth = 3;
        this.range = 2;
        this.name = "Porte-avion";
    }
}
