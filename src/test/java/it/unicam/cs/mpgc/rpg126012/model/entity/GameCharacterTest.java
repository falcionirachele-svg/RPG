package it.unicam.cs.mpgc.rpg126012.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameCharacterTest {

    private GameCharacter character;

    @BeforeEach
    void setUp() {
        character = new Player();
        character.setMaxHp(100);
        character.setHp(100);
        character.setLevel(1);
    }

    @Test
    void testDamage() {
        character.takeDamage(30);
        assertEquals(70, character.getHp());
    }

    @Test
    void testHeal() {
        character.takeDamage(10);
        character.heal(50);
        assertEquals(100, character.getHp());
    }

    @Test
    void testLevelUp() {
        character.takeDamage(40);
        character.levelUp();
        assertEquals(2, character.getLevel());
        assertEquals(110, character.getMaxHp());
        assertEquals(110, character.getHp());
    }
}
