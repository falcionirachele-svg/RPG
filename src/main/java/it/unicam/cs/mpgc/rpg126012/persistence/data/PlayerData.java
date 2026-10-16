package it.unicam.cs.mpgc.rpg126012.persistence.data;

import java.util.List;

public record PlayerData(String type, int hp, int maxhp, int attack, int shields, List<String> moves) {}
