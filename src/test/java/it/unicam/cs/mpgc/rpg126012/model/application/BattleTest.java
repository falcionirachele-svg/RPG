package it.unicam.cs.mpgc.rpg126012.model.application;

import it.unicam.cs.mpgc.rpg126012.model.entity.Enemy;
import it.unicam.cs.mpgc.rpg126012.model.entity.Move;
import it.unicam.cs.mpgc.rpg126012.model.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class BattleTest {

    private Battle newBattle;

    @BeforeEach
    public void setUp() {
        List<Move> mosse= new ArrayList<>();
        mosse.add(new Move("Mosse1", 10, 10));
        Player player= new Player("Giocatore", 20, 30, 2, 5, 1, mosse, "mago" );
        Enemy enemy= new Enemy("Nemico", 20, 30, 1, 4, 1, mosse);
        newBattle= new Battle(player, enemy, new Random());
    }

    @Test
    public void testAttacco(){
        List<AttackResult> result= newBattle.attackTurn(0);
        if(result.get(0).blocked()){
            assertEquals(0, result.get(0).damage());
        }else{
            if(result.get(0).hit()) assertEquals((5 * 10 / 100), result.get(0).damage());
        }
        assertEquals(2, result.size());
    }

    @Test
    public void testDefense(){
        List<AttackResult> results= newBattle.defendTurn();

        assertEquals(0, results.get(0).damage());
        assertEquals(0, results.get(0).damage());
    }

}
