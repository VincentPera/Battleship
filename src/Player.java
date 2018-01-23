import Game.Ship.PlaneCarrier;
import Game.Ship.Ship;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Matthieu Le Boucher
 */
public class Player {
    /**
     * List of the ships owned by the player.
     */
    private List<Ship> ships;

    /**
     * The player's name.
     */
    private String name;

    public Player(String name) {
        this.name = name;

        this.ships = new ArrayList<>();
        this.ships.add(new PlaneCarrier(Ship.Orientation.VERTICAL));
    }

    /**
     * @return The player's name.
     */
    public String getName() {
        return name;
    }
}
