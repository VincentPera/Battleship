package Game;

import Game.Board.BattleshipBoard;
import Game.Ship.*;
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
        ships.add(new PlaneCarrier(Ship.Orientation.VERTICAL));
        ships.add(new Cruiser(Ship.Orientation.VERTICAL));
        ships.add(new CounterTorpedoBoat(Ship.Orientation.VERTICAL));
        ships.add(new Submarine(Ship.Orientation.VERTICAL));
        ships.add(new TorpedoBoat(Ship.Orientation.VERTICAL));
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

    public boolean shipIsHit(Ship s, int targetX, int targetY){
        System.out.println("EST CE QUE JE SUIS QTOUCHZ ?");
        if(s.getOrientation() == Ship.Orientation.VERTICAL){
            return s.y <= targetY && s.y+s.getLength()-1>= targetY && s.x == targetX ;
        }else{
            return s.x <= targetX && s.x+s.getLength()-1>= targetX && s.y == targetY ;
        }
    }

    public void killShip(Ship s){
        int toremove;
        for(int i = 0;i<this.ships.size();i++){
           Ship ship  = this.ships.get(i);
           if(ship.getName().equals(s.getName())){
               toremove = i;
               this.ships.remove(toremove);
           }
        }
    }

    public Boolean isShipsListEmpty() {
        return ships.isEmpty();
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

    public boolean allShipsPlaced() {
        int amount = (int) ships.stream().filter(ship -> ship.placed).count();

        return amount == ships.size();
    }
}
