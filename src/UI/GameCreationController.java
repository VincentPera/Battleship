package UI;

import Game.Ship.Exception.InvalidAmountOfPlayersException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import Game.Game;

/**
 * @author : Matthieu Le Boucher
 */
public class GameCreationController {
    @FXML
    private TextField player1Name;

    @FXML
    private TextField player2Name;

    @FXML
    private Label errorLabel;

    private BattleshipPlus mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public GameCreationController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.

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
    private void createButtonClicked(ActionEvent f) {
        if (!player1Name.getText().equals("") && !player2Name.getText().equals("")) {
            try {
                Game game = new Game(player1Name.getText(), player2Name.getText());
                mainApp.setGame(game);
                mainApp.showGameBoard();
            } catch (InvalidAmountOfPlayersException e) {
                errorLabel.setText("Nombre de joueurs incompatible avec le jeu " +
                        "(" + Game.PLAYERS_AMOUNT + " joueur(s) attendus).");
            }
        } else
            errorLabel.setText("Veuillez indiquer un nom pour les deux joueurs.");

    }
}
