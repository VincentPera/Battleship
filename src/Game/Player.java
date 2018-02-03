package Game;

import Game.Board.BattleshipBoard;
import Game.Ship.PlaneCarrier;
import Game.Ship.Ship;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matthieu Le Boucher
 */
public class Player {
    /**
     * List of the ships owned by the player.
     */
    private List<Ship> ships;

    /**
     * The player's name.
     */
    private StringProperty name;

    /**
     * The board on which the player is playing.
     */
    private BattleshipBoard board;

    public Player(String name, BattleshipBoard board) {
        this.name = new SimpleStringProperty(name);
        this.board = board;

        this.ships = new ArrayList<>();
        this.ships.add(new PlaneCarrier(Ship.Orientation.VERTICAL));
    }

    /**
     * @return The player's name.
     */
    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public List<Ship> getShips() {
        return ships;
    }

    public BattleshipBoard getBoard() {
        return board;
    }
}
