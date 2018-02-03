package UI;

import Game.Ship.Ship;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

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

    private String shipColor = "#353DFF";

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
                    setText(ship.getClass().getName() + " (" + ship.getLength() + " cases)");
                }
            }
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

    private void addPane(int colIndex, int rowIndex) {
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: " + shipColor);

        pane.setOnMouseEntered(e -> {
            System.out.printf("Mouse enetered cell [%d, %d]%n", colIndex, rowIndex);
        });
        gameBoard.add(pane, colIndex, rowIndex);
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
