package it.unicam.cs.mpgc.rpg126012.controller;

import it.unicam.cs.mpgc.rpg126012.model.application.GameService;
import it.unicam.cs.mpgc.rpg126012.model.entity.Player;
import it.unicam.cs.mpgc.rpg126012.persistence.data.PlayerData;
import it.unicam.cs.mpgc.rpg126012.view.SceneManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/** Controller del menu iniziale: nuova partita, carica partita, esci. */
public class MenuController {

    @FXML private Button newGameButton;
    @FXML private HBox newGamePanel;
    @FXML private TextField nameField;
    @FXML private ComboBox<PlayerData> typeCombo;
    @FXML private Button confirmButton;
    @FXML private ComboBox<Player> savesCombo;
    @FXML private Button loadButton;

    private GameService gameService;
    private SceneManager sceneManager;

    /** Chiamato automaticamente da JavaFX dopo il caricamento dell'FXML. */
    @FXML
    public void initialize() {
        // "Inizia" attivo solo con nome scritto E personaggio scelto
        confirmButton.disableProperty().bind(
                nameField.textProperty().isEmpty().or(typeCombo.valueProperty().isNull()));
        // "Carica" attivo solo se è selezionata una partita
        loadButton.disableProperty().bind(savesCombo.valueProperty().isNull());

        typeCombo.setCellFactory(list -> new PlayerTypeCell());
        typeCombo.setButtonCell(new PlayerTypeCell());
        savesCombo.setCellFactory(list -> new PlayerCell());
        savesCombo.setButtonCell(new PlayerCell());
    }

    /** Chiamato dal SceneManager subito dopo il caricamento: riceve i servizi e carica i dati. */
    public void init(GameService gameService, SceneManager sceneManager) {
        this.gameService = gameService;
        this.sceneManager = sceneManager;

        typeCombo.getItems().setAll(gameService.getPlayersType());
        savesCombo.getItems().setAll(gameService.getMatches());
        savesCombo.setDisable(savesCombo.getItems().isEmpty());   // nessun salvataggio: tendina spenta
    }

    /** Clic su "Nuova partita": mostra nome e scelta del personaggio. */
    @FXML
    public void onNewGame() {
        showNewGamePanel(true);
        nameField.requestFocus();
    }

    /** Clic su "Annulla": nasconde il pannello e lo svuota. */
    @FXML
    public void onCancelNewGame() {
        nameField.clear();
        typeCombo.getSelectionModel().clearSelection();
        showNewGamePanel(false);
    }

    /** Clic su "Inizia" (o Invio nel campo): crea la partita e va alla battaglia. */
    @FXML
    public void onConfirmNewGame() {
        String name = nameField.getText().trim();
        PlayerData type = typeCombo.getValue();
        if (name.isEmpty() || type == null) return;

        gameService.newPlayer(name, type);
        sceneManager.showBattle();
    }

    /** Clic su "Carica": riprende la partita scelta e va alla battaglia. */
    @FXML
    public void onLoadGame() {
        Player selected = savesCombo.getValue();
        if (selected == null) return;
        gameService.selectPlayer(selected.getName());
        sceneManager.showBattle();
    }

    @FXML
    public void onExit() {
        Platform.exit();
    }

    private void showNewGamePanel(boolean show) {
        newGamePanel.setVisible(show);
        newGamePanel.setManaged(show);   // se nascosto, non occupa spazio
        newGameButton.setVisible(!show);
        newGameButton.setManaged(!show);
    }

    /** Mostra un salvataggio come "Nome – Lv X". */
    private static class PlayerCell extends ListCell<Player> {
        @Override
        protected void updateItem(Player p, boolean empty) {
            super.updateItem(p, empty);
            setText(empty || p == null ? null : p.getName() + " – Lv " + p.getLevel());
        }
    }

    /** Mostra un tipo di personaggio come "Guerriero (HP 120, ATT 45)". */
    private static class PlayerTypeCell extends ListCell<PlayerData> {
        @Override
        protected void updateItem(PlayerData t, boolean empty) {
            super.updateItem(t, empty);
            setText(empty || t == null ? null
                    : t.type() + " (HP " + t.hp() + ", ATT " + t.attack() + ")");
        }
    }
}
