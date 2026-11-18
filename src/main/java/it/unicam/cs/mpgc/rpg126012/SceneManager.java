package it.unicam.cs.mpgc.rpg126012;

import it.unicam.cs.mpgc.rpg126012.controller.BattleController;
import it.unicam.cs.mpgc.rpg126012.controller.EndController;
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
        FXMLLoader loader = load("/view/SchermataIniziale.fxml");
        MenuController controller = loader.getController();
        controller.init(gameService, this);
    }

    /** Avvia una nuova battaglia e mostra la schermata di combattimento.
     * Se il gioco è terminato mostra direttamente schermata finale del gioco */
    public void showBattle() {
        Battle battle= gameService.startBattle();
        if(gameService.getEndGame()){
            showEnd();
            return;
        }
        FXMLLoader loader= load("/view/Combattimento.fxml");
        BattleController controller = loader.getController();
        controller.init(battle, this);
    }

    /** Chiamato dal controller per terminare la battaglia. */
    public void onBattleFinished() {
        gameService.endBattle();
        showResult();
    }

    /**Mostra la schermata dei risultati del combattimento*/
    public void showResult() {
        FXMLLoader loader = load("/view/Risultato.fxml");
        ResultController controller = loader.getController();
        controller.init(gameService.getBattleResult(), this );
    }

    /**Mostra schermata finale*/
    public void showEnd(){
        FXMLLoader loader = load("/view/SchermataFinale.fxml");
        EndController controller = loader.getController();
        controller.init(this);
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
