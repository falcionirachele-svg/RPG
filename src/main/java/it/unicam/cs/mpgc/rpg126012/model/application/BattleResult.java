package it.unicam.cs.mpgc.rpg126012.model.application;

import it.unicam.cs.mpgc.rpg126012.model.entity.Enemy;
import it.unicam.cs.mpgc.rpg126012.model.entity.Player;
import lombok.*;

@Getter
@Setter
public class BattleResult {
    private final Battle battle;
    private final Player player;
    private final Enemy enemy;
    private final boolean playerWins;
    private final int hpPlayer;
    private final int hpEnemy;

    public BattleResult (Battle battle, int hpPlayer, int hpEnemy){
        this.battle = battle;
        this.player = battle.getPlayer();
        this.enemy = battle.getEnemy();
        this.playerWins = battle.playerWin();
        this.hpPlayer = hpPlayer;
        this.hpEnemy = hpEnemy;
    }

    public String getEnemyName(){
        return enemy.getName();
    }

    public String getPlayerType(){
        return player.getType();
    }


}
