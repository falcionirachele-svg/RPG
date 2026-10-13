package it.unicam.cs.mpgc.rpg126012.persistence;

import it.unicam.cs.mpgc.rpg126012.model.entity.Player;

public class PlayerRepo extends Repository<Player, Long> {
    public PlayerRepo() {
        super(Player.class);
    }

}
