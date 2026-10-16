package it.unicam.cs.mpgc.rpg126012.controller;

import it.unicam.cs.mpgc.rpg126012.model.entity.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class MenuController {

    @FXML private Button newGameButton;
    @FXML private HBox newGamePanel;
    @FXML private TextField nameField;
    @FXML private Button confirmButton;
    @FXML private ComboBox<Player> savesCombo;
    @FXML private Button loadButton;

    /** Chiamato automaticamente da JavaFX dopo il caricamento dell'FXML. */
    @FXML
    private void initialize() {
        confirmButton.disableProperty().bind(nameField.textProperty().isEmpty());
        loadButton.disableProperty().bind(savesCombo.valueProperty().isNull());
        savesCombo.setCellFactory(list -> new PlayerCell());
        savesCombo.setButtonCell(new PlayerCell());
    }

    /** Clic su "Nuova partita": mostra il campo per il nome. */
    @FXML
    private void onNewGame() {
        showNewGamePanel(true);
        nameField.requestFocus();
    }

    /** Clic su "Annulla": nasconde il campo e lo svuota. */
    @FXML
    private void onCancelNewGame() {
        nameField.clear();
        showNewGamePanel(false);
    }

    /** Clic su "Inizia" (o Invio nel campo): crea la partita. */
    @FXML
    private void onConfirmNewGame() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) return;
        // TODO: gameService.newGame(name); sceneManager.showBattle();
        System.out.println("Nuova partita per: " + name);
    }

    @FXML
    private void onLoadGame() {
        // TODO: gameService.loadGame(savesCombo.getValue().getId()); sceneManager.showBattle();
    }

    @FXML
    private void onExit() {
        Platform.exit();
    }

    private void showNewGamePanel(boolean show) {
        newGamePanel.setVisible(show);
        newGamePanel.setManaged(show);
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
}
