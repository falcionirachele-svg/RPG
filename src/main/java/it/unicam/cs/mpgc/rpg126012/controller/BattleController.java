package it.unicam.cs.mpgc.rpg126012.controller;


import it.unicam.cs.mpgc.rpg126012.model.application.AttackResult;
import it.unicam.cs.mpgc.rpg126012.model.application.Battle;
import it.unicam.cs.mpgc.rpg126012.model.entity.GameCharacter;
import it.unicam.cs.mpgc.rpg126012.model.entity.Move;
import it.unicam.cs.mpgc.rpg126012.view.Sprites;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.List;
import java.util.function.Consumer;


/**Controller della battaglia: durante uno scontro tra un giocatore e un avversario.*/
public class BattleController {
    @FXML private Label enemyNameLabel;
    @FXML private ProgressBar enemyHpBar;
    @FXML private Label enemyHpLabel;
    @FXML private Label enemyShieldsLabel;
    @FXML private ImageView enemyImage;
    @FXML private Label playerNameLabel;
    @FXML private ProgressBar playerHpBar;
    @FXML private Label playerHpLabel;
    @FXML private Label playerShieldsLabel;
    @FXML private ImageView playerImage;
    @FXML private TextArea logArea;
    @FXML private GridPane movesGrid;
    @FXML private Button shieldButton;

    private Battle battle;
    private Consumer<Boolean> onBattleEnd;
    private static final Duration END_DELAY = Duration.seconds(1.5);


    /**Chiamato automaticamente da SceneBuilder dopo il caricamento dell'FXML. */
    public void init(Battle battle, Consumer<Boolean> onBattleEnd) {
        this.battle = battle;
        this.onBattleEnd = onBattleEnd;

        enemyImage.setImage(Sprites.load(battle.getEnemy().getName()));
        playerImage.setImage(Sprites.load(battle.getPlayer().getType()));

        createMoveButtons();
        refresh();
        log("Affronta " + battle.getEnemy().getName() + "!");
    }

    /** Un bottone per ogni mossa del giocatore, su due colonne. */
    private void createMoveButtons() {
        movesGrid.getChildren().clear();
        List<Move> moves = battle.getPlayer().getMoves();
        for (int i = 0; i < moves.size(); i++) {
            Move move = moves.get(i);
            Button button = new Button(move.getName());
            button.setMaxWidth(Double.MAX_VALUE);
            button.setTooltip(new Tooltip("Potenza " + move.getPower() + " - Precisione " + move.getAccuracy() + "%"));
            int index = i;
            button.setOnAction(e -> onMove(index));
            movesGrid.add(button, i % 2, i / 2);
        }
    }

    /** Clic su una mossa. */
    private void onMove(int moveIndex) {
        showResults(battle.attackTurn(moveIndex));
    }

    /** Scrive i messaggi del turno, aggiorna i dati e controlla se lo scontro è finito. */
    private void showResults(List<AttackResult> results) {
        results.forEach(r -> log(describe(r)));
        refresh();
        if (battle.isOver()) {
            endBattle();
        }
    }
    /** Aggiorna nomi, barre HP, scudi e stato del bottone Scudo. */
    private void refresh() {
        updateInfo(battle.getPlayer(), playerNameLabel, playerHpBar, playerHpLabel,
                playerShieldsLabel, battle.getPlayerShieldsLeft());
        updateInfo(battle.getEnemy(), enemyNameLabel, enemyHpBar, enemyHpLabel,
                enemyShieldsLabel, battle.getEnemyShieldsLeft());

        shieldButton.setText("Scudo (" + battle.getPlayerShieldsLeft() + ")");
        shieldButton.setDisable(!battle.canDefend());
    }

    private void updateInfo(GameCharacter c, Label name, ProgressBar bar, Label hp,
                            Label shields, int shieldsLeft) {
        name.setText(c.getName() + "  Lv " + c.getLevel());
        double ratio = c.getMaxHp() == 0 ? 0 : (double) c.getHp() / c.getMaxHp();
        bar.setProgress(ratio);
        bar.setStyle("-fx-accent: " + hpColor(ratio) + ";");
        hp.setText("HP " + c.getHp() + "/" + c.getMaxHp());
        shields.setText("Scudi: " + shieldsLeft);
    }

    private String hpColor(double ratio) {
        if (ratio > 0.5) return "#3cb043";
        if (ratio > 0.2) return "#f2c94c";
        return "#e03c31";
    }

    /** Trasforma il risultato di un attacco in un messaggio. */
    private String describe(AttackResult r) {
        String text = r.attacker().getName() + " usa " + r.move().getName();
        if (!r.hit())    return text + ", ma manca il bersaglio!";
        if (r.blocked()) return text + ", ma il colpo viene parato da uno scudo!";
        return text + ": " + r.damage() + " danni!";
    }

    /** Fine scontro: blocca i comandi, scrive l'esito e dopo una pausa avvisa il SceneManager. */
    private void endBattle() {
        movesGrid.setDisable(true);
        shieldButton.setDisable(true);
        boolean won = battle.playerWin();
        log(won ? "Hai vinto la battaglia!" : "Sei stato sconfitto...");

        if (onBattleEnd != null) {
            PauseTransition pause = new PauseTransition(END_DELAY);
            pause.setOnFinished(e -> onBattleEnd.accept(won));
            pause.play();
        }
    }

    private void log(String message) {
        logArea.appendText(message + "\n");
    }

    /** Clic su "Scudo" (collegato nell'FXML con onAction="#onShield"). */
    @FXML
    private void onShield() {
        log(battle.getPlayer().getName() + " alza lo scudo!");
        showResults(battle.defendTurn());
    }

}
