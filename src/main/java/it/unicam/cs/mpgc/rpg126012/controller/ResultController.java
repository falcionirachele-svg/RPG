package it.unicam.cs.mpgc.rpg126012.controller;
import it.unicam.cs.mpgc.rpg126012.model.application.BattleResult;
import it.unicam.cs.mpgc.rpg126012.model.entity.GameCharacter;
import it.unicam.cs.mpgc.rpg126012.view.SceneManager;
import it.unicam.cs.mpgc.rpg126012.view.Sprites;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

public class ResultController {
    @FXML private Button exitButton;
    @FXML private ImageView playerImage;
    @FXML private Label playerStats;
    @FXML private ImageView enemyImage;
    @FXML private Label enemyStats;

    private SceneManager sceneManager;

    /**Chiamato automaticamente da SceneBuilder dopo il caricamento dell'FXML. */
    public void init(BattleResult battleResult, SceneManager sceneManager){
        this.sceneManager = sceneManager;
        playerImage.setImage(Sprites.load(battleResult.getPlayerType()));
        enemyImage.setImage(Sprites.load(battleResult.getEnemyName()));
        playerStats.setText(getStats(battleResult.getPlayer()));
        enemyStats.setText(getStats(battleResult.getEnemy()));

    }

    private String getStats(GameCharacter character) {
        return String.format("%s: %d/%d", character.getName(), character.getHp(), character.getMaxHp());
    }

    /**Cliccando il bottone ritorna al menu*/
    @FXML
    private void onExit(){
        sceneManager.showMenu();
    }

}
