package Game;

import Game.Board.BattleshipBoard;
import Game.Ship.PlaneCarrier;
import Game.Ship.Ship;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Matthieu Le Boucher
 */
public class Player {
    /**
     * List of the ships owned by the player.
     */
    private ObservableList<Ship> ships;

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
        this.ships = FXCollections.observableArrayList();
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

    public void addShip(Ship ship){
        this.ships.add(ship);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public ObservableList<Ship> getShips() {
        return ships;
    }

    public BattleshipBoard getBoard() {
        return board;
    }
}
