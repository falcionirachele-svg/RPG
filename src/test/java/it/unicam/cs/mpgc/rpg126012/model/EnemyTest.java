package it.unicam.cs.mpgc.rpg126012.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EnemyTest {

    private Enemy enemy;
    private Player player;

    @BeforeEach
    void setUp() {
        enemy = new Enemy();
        enemy.setName("Wolf");
        enemy.setMaxHp(100);
        enemy.setHp(100);
        enemy.setLevel(1);
        enemy.setAttack(50);

        player = new Player();
        player.setName("Ash");
        player.setMaxHp(100);
        player.setHp(90);
        player.setLevel(1);
    }

    @Test
    void testAttackAccuracy() {
        Move punch = new Move("Punch", 10, 100);
        enemy.setMoves(new ArrayList<>(List.of(punch)));

        AttackResult result = enemy.attack(player);

        assertTrue(result.hit());
        assertSame(punch, result.move());
        assertEquals(5, result.damage());
        assertEquals(85, player.getHp());
    }

    @Test
    void testZeroAccuracy() {
        enemy.setMoves(new ArrayList<>(List.of(new Move("Miss", 50, 0))));

        AttackResult result = enemy.attack(player);

        assertFalse(result.hit());
        assertEquals(0, result.damage());
        assertEquals(90, player.getHp());
    }
}
