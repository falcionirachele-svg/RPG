package it.unicam.cs.mpgc.rpg126012.persistence.data;


import java.util.List;

public record EnemyData(String name, int level, int hp, int attack, int shields, List<String> moves) {}

