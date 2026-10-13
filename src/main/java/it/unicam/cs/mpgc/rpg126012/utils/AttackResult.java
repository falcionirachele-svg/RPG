package it.unicam.cs.mpgc.rpg126012.utils;

import it.unicam.cs.mpgc.rpg126012.model.entity.GameCharacter;
import it.unicam.cs.mpgc.rpg126012.model.entity.Move;

public record AttackResult(GameCharacter attacker, Move move, int damage, boolean hit) {
}
