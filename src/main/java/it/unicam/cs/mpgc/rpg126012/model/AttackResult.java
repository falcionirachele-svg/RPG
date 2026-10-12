package it.unicam.cs.mpgc.rpg126012.model;

public record AttackResult(GameCharacter attacker, Move move, int damage, boolean hit) {
}
