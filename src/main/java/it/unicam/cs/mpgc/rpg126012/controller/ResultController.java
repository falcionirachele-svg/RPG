package it.unicam.cs.mpgc.rpg126012.controller;
import it.unicam.cs.mpgc.rpg126012.model.application.BattleResult;
import it.unicam.cs.mpgc.rpg126012.model.entity.Enemy;
import it.unicam.cs.mpgc.rpg126012.model.entity.GameCharacter;
import it.unicam.cs.mpgc.rpg126012.ui.SceneManager;
import it.unicam.cs.mpgc.rpg126012.ui.Sprites;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

public class ResultController {

    @FXML private ImageView playerImage;
    @FXML private Label playerStats;
    @FXML private ImageView enemyImage;
    @FXML private Label enemyStats;
    @FXML private Label winner;
    @FXML private Button continueButton;

    private SceneManager sceneManager;

    /**Chiamato automaticamente da SceneBuilder dopo il caricamento dell'FXML. */
    public void init(BattleResult battleResult, SceneManager sceneManager){
        this.sceneManager = sceneManager;
        playerImage.setImage(Sprites.load(battleResult.getPlayerType()));
        enemyImage.setImage(Sprites.load(battleResult.getEnemyName()));
        playerStats.setText(getStats(battleResult.getPlayer(), battleResult));
        enemyStats.setText(getStats(battleResult.getEnemy(), battleResult));
        if(battleResult.isPlayerWins()){
            continueButton.setVisible(true);
            continueButton.setManaged(true);
            winner.setText("Hai vinto!");
        } else {
            continueButton.setVisible(false);
            continueButton.setManaged(false);
            winner.setText("Hai perso!");
        }

    }

    private String getStats(GameCharacter character, BattleResult battleResult) {
        if(character instanceof Enemy) {
            return String.format("%s: %d/%d", character.getName(), battleResult.getHpEnemy(), character.getMaxHp());

        }
        return String.format("%s: %d/%d", character.getName(), battleResult.getHpPlayer(), character.getMaxHp());
    }

    /**Cliccando il bottone ritorna al menu*/
    @FXML
    private void onExit(){
        sceneManager.showMenu();
    }

    /**Cliccando il bottone avvia una nuova battaglia*/
    @FXML
    private void onContinue(){
        sceneManager.showBattle();
    }



}
