package Game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * @author Matthieu Le Boucher
 */
public class BattleshipPlus extends Application {


    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Battleship+");

        File directory = new File("./resources/views/GameCreatorScene.fxml");
        System.out.println(directory.getAbsolutePath());

        initRootLayout();

        showGameCreatorScene();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(BattleshipPlus.class.getResource("/views/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showGameCreatorScene() {

        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(BattleshipPlus.class.getResource("/views/GameCreatorScene.fxml"));
            AnchorPane gameCreator = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(gameCreator);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
