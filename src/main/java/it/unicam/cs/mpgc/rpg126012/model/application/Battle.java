package it.unicam.cs.mpgc.rpg126012.model.application;
import it.unicam.cs.mpgc.rpg126012.model.entity.Enemy;
import it.unicam.cs.mpgc.rpg126012.model.entity.GameCharacter;
import it.unicam.cs.mpgc.rpg126012.model.entity.Move;
import it.unicam.cs.mpgc.rpg126012.model.entity.Player;
import lombok.*;

import java.util.*;

@Getter
@AllArgsConstructor
public class Battle {

    private final Player player;
    private final Enemy enemy;
    private final Random random;

    private AttackResult attack(GameCharacter attacker, Move move, GameCharacter target) {
        int damage=0;
        boolean hit = random.nextInt(100) < move.getAccuracy();
        if(hit) {
            damage = Math.max(1, attacker.getAttack()*move.getPower()/100);
            target.takeDamage(damage);
        }
        return new AttackResult(attacker, move, damage, hit);
    }

    public List<AttackResult> playTurn(int playerChoose){
        if(isOver()) throw new IllegalStateException("Battle is over");
        Move playerMove = player.chooseMove(playerChoose);
        List<AttackResult> attackResults= new ArrayList<>();
        attackResults.add(attack(player, playerMove, enemy));
        if(enemy.isAlive()){
            attackResults.add(attack(enemy, chooseEnemyMove(), player));
        }
        return attackResults;
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
}
