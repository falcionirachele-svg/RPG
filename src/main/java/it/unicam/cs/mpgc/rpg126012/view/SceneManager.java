package it.unicam.cs.mpgc.rpg126012.view;

import it.unicam.cs.mpgc.rpg126012.controller.BattleController;
import it.unicam.cs.mpgc.rpg126012.controller.MenuController;
import it.unicam.cs.mpgc.rpg126012.controller.ResultController;
import it.unicam.cs.mpgc.rpg126012.model.application.Battle;
import it.unicam.cs.mpgc.rpg126012.model.application.GameService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.UncheckedIOException;

/*Classe che collega UI ai controller*/
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
        FXMLLoader loader= load("/Combattimento.fxml");
        Battle battle= gameService.startBattle();
        BattleController controller = loader.getController();
        controller.init(battle, this);
    }

    /** Chiamato dal controller per terminare la battaglia. */
    public void onBattleFinished() {
        gameService.endBattle();
        showResult();
    }

    /**Mostra la schermata dei risultati del combattimento
     * Per ora il pulsante exit torna alla schermata home
     */
    public void showResult() {
        FXMLLoader loader = load("/Risultato.fxml");
        ResultController controller = loader.getController();
        controller.init(gameService.getBattleResult(), this );
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
