package UI;

import Game.Player;
import Game.Ship.Exception.InvalidMoveException;
import Game.Ship.Ship;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
    private Label infoLabel;

    @FXML
    private Label currentPlayer;

    @FXML
    private GridPane gameBoard;

    @FXML
    private ListView<Ship> shipsList;

    @FXML
    private Button endTurnButton;

    @FXML
    private Button fireButton;

    private BattleshipPlus mainApp;

    private String shipPlacementColor = "#00ff00";

    private String fireFailColor = "#ff0000";

    private String fireSuccessColor = "#00ff00";

    private String targetCellColor = "#ff0f0f";

    private String emptyCellColor = "#ffffff";

    private Ship shipBeingMoved;

    private Boolean shipsCanMove = true;

    private Ship firingShip;

    private int targetX, targetY;

    private boolean isInFiringProcess = false;

    private boolean isTargetPositionSet = false;

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
        fireButton.setText("Planifier un tir !");

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
                if(isInFiringProcess && firingShip != null) {
                    if(firingShip.canAttack(x, y)) {
                        infoLabel.setText(firingShip.getName() + " vise en\n(" + x + ", " + (char) ((char) 64 + y) + ")");
                    } else
                        infoLabel.setText("Cible hors de portée de votre " + firingShip.getName());
                } else if(shipBeingMoved != null && shipsCanMove) {
                    // If a ship is being placed.
                    mainApp.getGame().getCurrentPlayer().getBoard().placeShipAt(shipBeingMoved, x-1, y-1 );
                    refreshGameBoard();
                }


            } catch (InvalidMoveException e1) {
                errorLabel.setText(shipBeingMoved.getName() + " ne peut pas être placé ici.");
                errorOccurred.set(true);
            }
        });

        pane.setOnMousePressed(e -> {
            if(isInFiringProcess) {
                if(firingShip != null) {
                    if(firingShip.canAttack(x, y)) {
                        targetX = x;
                        targetY = y;
                        isTargetPositionSet = true;
                        fireButton.setText("Tirer en (" + x + ", " + (char) ((char) 64 + y) + ")");
                    } else
                        infoLabel.setText("Cible hors de portée de votre " + firingShip.getName());
                } else {
                    firingShip = shipHere;

                    if(firingShip != null)
                        infoLabel.setText(firingShip.getName() + " sélectionné. Pointez une cible !");
                }
            } else {
                if (shipBeingMoved == null)
                    shipBeingMoved = shipHere;
                else
                    shipBeingMoved = null;
            }
        });

        pane.setOnScroll((ScrollEvent e) -> {
            if(shipBeingMoved != null) {
                try {
                    if (e.getDeltaY() < 0){
                        shipBeingMoved.setOrientation(Ship.Orientation.VERTICAL);
                        System.out.println("VERTICAL");
                    }else{
                        shipBeingMoved.setOrientation(Ship.Orientation.HORIZONTAL);
                        System.out.println("HORIZONTAL");
                    }



                    mainApp.getGame().getCurrentPlayer().getBoard().placeShipAt(shipBeingMoved, x - 1, y - 1);
                } catch (InvalidMoveException e1) {
                    errorLabel.setText(shipBeingMoved.getName() + " ne peut pas être réorienté ici.");
                    errorOccurred.set(true);
                }
            }
        });

        if(!errorOccurred.get())
            errorLabel.setText("");

        if(endTurnButton.isDisabled() && mainApp.getGame().getCurrentPlayer().allShipsPlaced()) {
            endTurnButton.setDisable(false);
        }
        if(fireButton.isDisabled()&&mainApp.getGame().gameIsReady()){
            fireButton.setDisable(false);
        }

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

    @FXML
    private void endTurnButtonClicked(ActionEvent f) {
        if(!mainApp.getGame().getCurrentPlayer().allShipsPlaced()){
            errorLabel.setText("Vous devez placer tous vos navires.");
        }else{
            mainApp.getGame().changePlayer();
            refreshGameBoard();
        }

    }

    @FXML
    private void endFireButtonClicked(ActionEvent f) {
        shipsCanMove = false;
        isInFiringProcess = true;
        infoLabel.setText("Choisissez un navire pour tirer !");
        if(isTargetPositionSet) {
            boolean hitSomething = false;
            fireButton.setDisable(true);
            fireButton.setText("Tir effectué !");
            System.out.println("allo ?");
            Player other = mainApp.getGame().getOtherPlayer();
            for (Ship s: other.getShips()
                 ) {
                if(other.shipIsHit(s, targetX, targetY)){
                    hitSomething = true;
                    System.out.println("OUIII");
                    s.decreaseLife();
                    infoLabel.setText(s.getName()+" ennemi touché !");
                    if(s.getCurrentHealth() == 0){
                        other.killShip(s);
                        mainApp.getGame().getOtherPlayer().getBoard().cleanGridFromShip(s);
                        if(!other.isShipsListEmpty()){
                            infoLabel.setText(s.getName()+" ennemi touché et coulé !");
                        }else{
                            infoLabel.setText("Le denrier navire adverse a été coulé ! VICTOIRE !");
                        }
                    }
                }
            }
            if(!hitSomething){
                shipsCanMove = true;
            }
            isInFiringProcess = false;
            firingShip = null;
            isTargetPositionSet = false;
            fireButton.setDisable(true);
        }

    }
}
