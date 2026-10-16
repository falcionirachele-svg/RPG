package it.unicam.cs.mpgc.rpg126012.persistence.data;

import java.util.List;

public record GameData(List<MoveData> moves, List<EnemyData> enemies, List<PlayerData> players) {}
