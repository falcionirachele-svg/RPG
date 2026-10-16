package it.unicam.cs.mpgc.rpg126012;

import it.unicam.cs.mpgc.rpg126012.model.application.GameService;
import it.unicam.cs.mpgc.rpg126012.persistence.DataInitializer;
import it.unicam.cs.mpgc.rpg126012.persistence.EnemyRepo;
import it.unicam.cs.mpgc.rpg126012.persistence.JPAUtil;
import it.unicam.cs.mpgc.rpg126012.persistence.MoveRepo;
import it.unicam.cs.mpgc.rpg126012.persistence.PlayerRepo;
import it.unicam.cs.mpgc.rpg126012.persistence.data.PlayerData;
import it.unicam.cs.mpgc.rpg126012.view.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;
import java.util.Random;

/** Punto di avvio: crea e collega tutti gli oggetti del gioco, poi mostra il menu. */
public class App extends Application {

    private static final String TITLE = "NOME GIOCO";
    private static final double MIN_WIDTH = 600;
    private static final double MIN_HEIGHT = 400;

    private final PlayerRepo playerRepo = new PlayerRepo();
    private final EnemyRepo enemyRepo = new EnemyRepo();
    private final MoveRepo moveRepo = new MoveRepo();
    private List<PlayerData> playersType;

    /** Prima della finestra: prepara il database e legge i personaggi disponibili. */
    @Override
    public void init() {
        DataInitializer initializer = new DataInitializer(enemyRepo, moveRepo);
        initializer.init();                                // popola il DB solo al primo avvio
        playersType = initializer.getPlayerDataList();     // i personaggi servono a ogni avvio
    }

    /** Crea la logica di gioco e il gestore delle schermate, poi mostra il menu. */
    @Override
    public void start(Stage primaryStage) {
        GameService gameService = new GameService(playerRepo, enemyRepo, moveRepo, playersType, new Random());
        SceneManager sceneManager = new SceneManager(primaryStage, gameService);

        sceneManager.showMenu();

        primaryStage.setTitle(TITLE);
        primaryStage.setMinWidth(MIN_WIDTH);
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.show();
    }

    /** Alla chiusura della finestra: chiude il database. */
    @Override
    public void stop() {
        JPAUtil.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
