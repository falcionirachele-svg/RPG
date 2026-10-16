package it.unicam.cs.mpgc.rpg126012.view;

import it.unicam.cs.mpgc.rpg126012.controller.MenuController;
import it.unicam.cs.mpgc.rpg126012.model.application.GameService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.UncheckedIOException;

public class SceneManager {
    private static final double START_WIDTH = 800;
    private static final double START_HEIGHT = 533;
    private final Stage stage;
    private final GameService gameService;

    public SceneManager(Stage stage, GameService gameService) {
        this.stage = stage;
        this.gameService = gameService;
    }

    /** Mostra il menu iniziale. */
    public void showMenu() {
        FXMLLoader loader = load("/SchermataIniziale.fxml");
        MenuController controller = loader.getController();
        controller.init(gameService, this);
    }

    /** Avvia una nuova battaglia e mostra la schermata di combattimento. */
    public void showBattle() {
        //TODO
    }

    /** Carica un FXML e lo mette nella finestra, mantenendo la dimensione attuale. */
    private FXMLLoader load(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            if (stage.getScene() == null) {
                stage.setScene(new Scene(root, START_WIDTH, START_HEIGHT));
            } else {
                stage.getScene().setRoot(root);
            }
            return loader;
        } catch (IOException e) {
            throw new UncheckedIOException("Impossibile caricare " + fxml, e);
        }
    }

}
