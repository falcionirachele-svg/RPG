package it.unicam.cs.mpgc.rpg126012.view;

import it.unicam.cs.mpgc.rpg126012.model.application.Battle;
import it.unicam.cs.mpgc.rpg126012.model.application.GameService;
import it.unicam.cs.mpgc.rpg126012.model.entity.Enemy;
import it.unicam.cs.mpgc.rpg126012.model.entity.GameCharacter;
import it.unicam.cs.mpgc.rpg126012.model.entity.Move;
import it.unicam.cs.mpgc.rpg126012.model.entity.Player;
import it.unicam.cs.mpgc.rpg126012.controller.BattleController;
import it.unicam.cs.mpgc.rpg126012.persistence.EnemyRepo;
import it.unicam.cs.mpgc.rpg126012.persistence.MoveRepo;
import it.unicam.cs.mpgc.rpg126012.persistence.PlayerRepo;
import it.unicam.cs.mpgc.rpg126012.persistence.data.PlayerData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Anteprima della schermata di combattimento con dati finti (senza database).
 * Sta nei test, quindi non finisce nel gioco: serve solo per provare la grafica.
 */
public class BattlePreview extends Application {

    private final PlayerRepo playerRepo = new PlayerRepo();
    private final EnemyRepo enemyRepo = new EnemyRepo();
    private final MoveRepo moveRepo = new MoveRepo();
    private final List<PlayerData> playerData = new ArrayList<>();
    @Override
    public void start(Stage stage) throws Exception {
        Player player = new Player();
        setStats(player, "Ash", 3, 100, 50, 2,
                new Move("Tackle", 40, 100), new Move("Quick Attack", 30, 100),
                new Move("Ember", 45, 95), new Move("Hyper Beam", 150, 60));

        Enemy enemy = new Enemy();
        setStats(enemy, "Wolf", 2, 90, 45, 1,
                new Move("Bite", 60, 90), new Move("Quick Attack", 30, 100));

        GameService gameService= new GameService(playerRepo, enemyRepo, moveRepo, playerData,new Random());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Combattimento.fxml"));
        Scene scene = new Scene(loader.load());
        BattleController controller = loader.getController();
        controller.init(new Battle(player, enemy, new Random()), new SceneManager(stage, gameService ));

        stage.setTitle("Anteprima combattimento");
        stage.setScene(scene);
        stage.setMinWidth(700);
        stage.setMinHeight(480);
        stage.show();
    }

    private static void setStats(GameCharacter c, String name, int level, int hp, int attack,
                                 int shields, Move... moves) {
        c.setName(name);
        c.setLevel(level);
        c.setMaxHp(hp);
        c.setHp(hp);
        c.setAttack(attack);
        c.setShields(shields);
        c.setMoves(new ArrayList<>(List.of(moves)));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
