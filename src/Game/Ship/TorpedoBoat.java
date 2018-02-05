package Game.Ship;

/**
 * @author Matthieu Le Boucher
 */
public class TorpedoBoat extends Ship {
    public TorpedoBoat(Orientation orientation) {
        this.orientation = orientation;
        this.name = "Torpedo Boat";
        this.length = 2;
        this.currentHealth = 3;
        this.range = 5;
        this.name = "Torpilleur";
    }
}
