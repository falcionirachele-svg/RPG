package it.unicam.cs.mpgc.rpg126012.model.application;
import it.unicam.cs.mpgc.rpg126012.model.entity.Enemy;
import it.unicam.cs.mpgc.rpg126012.model.entity.GameCharacter;
import it.unicam.cs.mpgc.rpg126012.model.entity.Move;
import it.unicam.cs.mpgc.rpg126012.model.entity.Player;
import lombok.*;

import java.util.*;

@Getter
public class Battle {

    private final Player player;
    private final Enemy enemy;
    private final Random random;

    private int playerShieldsLeft;
    private int enemyShieldsLeft;
    private boolean playerShieldActive;

    private static final int ENEMY_SHIELD_CHANCE = 30;

    public Battle(Player player, Enemy enemy, Random random) {
        this.player = player;
        this.enemy = enemy;
        this.random = random;
        this.playerShieldsLeft = player.getShields();
        this.enemyShieldsLeft= enemy.getShields();
    }

    private AttackResult attack(GameCharacter attacker, Move move, GameCharacter target) {
        int damage=0;
        boolean hit= random.nextInt(100) < move.getAccuracy();
        boolean blocked = hit && isBlocked(target);
        if(hit && !blocked) {
            damage = Math.max(1, attacker.getAttack()*move.getPower()/100);
            target.takeDamage(damage);
        }
        return new AttackResult(attacker, move, damage, hit, blocked);
    }

    /** Il player attacca con la mossa scelta, poi risponde il nemico. */
    public List<AttackResult> attackTurn(int moveIndex) {
        if(isOver()) throw new IllegalStateException("Battle is over");
        List<AttackResult> results = new ArrayList<>();
        results.add(attack(player, player.chooseMove(moveIndex), enemy));
        if (enemy.isAlive()) {
            results.add(attack(enemy, chooseEnemyMove(), player));
        }
        return results;
    }

    /** Il player si difende, poi attacca il nemico (e viene bloccato). */
    public List<AttackResult> defendTurn() {
        if(isOver()) throw new IllegalStateException("Battle is over");
        if (playerShieldsLeft <= 0) throw new IllegalStateException("No shields left");
        playerShieldsLeft--;
        playerShieldActive = true;
        return List.of(attack(enemy, chooseEnemyMove(), player));
    }

    /** Controlla se il bersaglio para il colpo; in quel caso consuma lo scudo. */
    private boolean isBlocked(GameCharacter target) {
        if (target == player && playerShieldActive) {
            playerShieldActive = false;
            return true;
        }
        if (target == enemy && enemyShieldsLeft > 0 && random.nextInt(100) < ENEMY_SHIELD_CHANCE) {
            enemyShieldsLeft--;
            return true;
        }
        return false;
    }

    private Move chooseEnemyMove() {
        List<Move> moves = enemy.getMoves();
        if (moves.isEmpty()) throw new IllegalStateException("No moves");
        return moves.get(random.nextInt(moves.size()));
    }

    public boolean isOver(){
        return !player.isAlive() || !enemy.isAlive();
    }
    public boolean playerWin(){
        return isOver() && player.isAlive();
    }

    public boolean canDefend()  { return playerShieldsLeft > 0 && !isOver(); }
}
