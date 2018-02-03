package UI;

import Game.Ship.Exception.InvalidMoveException;
import Game.Ship.Ship;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author : Matthieu Le Boucher
 */
public class GameBoardController {
    @FXML
    private Label errorLabel;

    @FXML
    private Label currentPlayer;

    @FXML
    private GridPane gameBoard;

    @FXML
    private ListView<Ship> shipsList;

    private BattleshipPlus mainApp;

    private String shipPlacementColor = "#00ff00";

    private String emptyCellColor = "#ffffff";

    private Ship shipBeingMoved;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public GameBoardController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        shipsList.setCellFactory(param -> new ListCell<Ship>() {
            @Override
            protected void updateItem(Ship ship, boolean empty) {
                super.updateItem(ship, empty);

                if (empty || ship == null) {
                    setText(null);
                } else {
                    setText(ship.getName() + " (" + ship.getLength() + " cases)");

                }
            }
        });

        shipsList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    shipBeingMoved = newValue;
                    System.out.println("Now moving " + shipBeingMoved.getName());
                });
    }

    public void setUpBoard() {
        System.out.println(2);
        for (int i = 0; i < mainApp.getGame().getBoardSize(); i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            RowConstraints rowConstraints = new RowConstraints();
            columnConstraints.setPercentWidth(100.0 / (mainApp.getGame().getBoardSize() + 1));
            columnConstraints.setHalignment(HPos.CENTER);

            rowConstraints.setPercentHeight(100.0 / (mainApp.getGame().getBoardSize() + 1));
            rowConstraints.setValignment(VPos.CENTER);
            gameBoard.getColumnConstraints().add(columnConstraints);
            gameBoard.getRowConstraints().add(rowConstraints);
        }

        for (int i = 1; i < mainApp.getGame().getBoardSize(); i++) {
            gameBoard.add(new Label(Integer.toString(i)), i, 0);
            gameBoard.add(new Label(Character.toString((char) ((char) 64 + i))), 0, i);
        }

        refreshGameBoard();
    }

    private void refreshGameBoard() {
        currentPlayer.setText(mainApp.getGame().getCurrentPlayer().getName());

        for (int i = 1; i < mainApp.getGame().getBoardSize(); i++) {
            for (int j = 1; j < mainApp.getGame().getBoardSize(); j++) {
                addPane(i, j);
            }
        }

        shipsList.setItems(mainApp.getGame().getCurrentPlayer().getShips());


    }

    private void addPane(int x, int y) {
        Pane pane = new Pane();

        AtomicBoolean errorOccurred = new AtomicBoolean(false);

        Ship shipHere = mainApp.getGame().getCurrentPlayer().getBoard().getShipAt(x, y);
        if(shipHere != null) {
            pane.setStyle("-fx-background-color: " + shipHere.getColor());

            if(shipHere.x + 1 == x && shipHere.y + 1 == y) {
                // We're at the head of the ship: darken its color.
                pane.setStyle("-fx-background-color: #"
                        + Integer.toHexString(
                                (int) (Long.parseLong(shipHere.getColor().substring(1), 16) & 0xfefefe) >> 1));
            }
        } else {
            pane.setStyle("-fx-background-color: " + emptyCellColor);
        }

        pane.setOnMouseEntered(e -> {
            try {
                if(shipBeingMoved != null) {
                    // If a ship is being placed.
                    mainApp.getGame().getCurrentPlayer().getBoard().placeShipAt(shipBeingMoved, x - 1, y - 1);

                    refreshGameBoard();
                }
            } catch (InvalidMoveException e1) {
                errorLabel.setText(shipBeingMoved.getName() + " ne peut pas être placé ici.");
                errorOccurred.set(true);
            }
        });

        pane.setOnMousePressed(e -> {
            if(shipBeingMoved == null) {
                shipBeingMoved = shipHere;
            } else
                shipBeingMoved = null;
        });

        pane.setOnScroll((ScrollEvent e) -> {
            if(shipBeingMoved != null) {
                try {
                    if (e.getDeltaY() < 0)
                        shipBeingMoved.setOrientation(Ship.Orientation.VERTICAL);
                    else
                        shipBeingMoved.setOrientation(Ship.Orientation.HORIZONTAL);

                    mainApp.getGame().getCurrentPlayer().getBoard().placeShipAt(shipBeingMoved, x - 1, y - 1);
                } catch (InvalidMoveException e1) {
                    errorLabel.setText(shipBeingMoved.getName() + " ne peut pas être réorienté ici.");
                    errorOccurred.set(true);
                }
            }
        });

        if(!errorOccurred.get())
            errorLabel.setText("");

        gameBoard.add(pane, x, y);
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(BattleshipPlus mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void mouseEntered(MouseEvent e) {
        Node source = (Node)e.getSource() ;
        Integer colIndex = GridPane.getColumnIndex(source);
        Integer rowIndex = GridPane.getRowIndex(source);
        System.out.printf("Mouse entered cell [%d, %d]%n", colIndex.intValue(), rowIndex.intValue());
    }
}
