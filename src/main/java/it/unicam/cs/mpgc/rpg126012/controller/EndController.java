package it.unicam.cs.mpgc.rpg126012.controller;

import it.unicam.cs.mpgc.rpg126012.model.application.Battle;
import it.unicam.cs.mpgc.rpg126012.view.SceneManager;
import it.unicam.cs.mpgc.rpg126012.view.Sprites;
import javafx.fxml.FXML;

public class EndController {

    private SceneManager sceneManager;

    /**Chiamato automaticamente da SceneBuilder dopo il caricamento dell'FXML. */
    public void init(SceneManager sceneManager) {
        this.sceneManager= sceneManager;
    }

    /**Fine del gioco torno al menu*/
    @FXML
    public void onMenu(){
        sceneManager.showMenu();
    }
}
