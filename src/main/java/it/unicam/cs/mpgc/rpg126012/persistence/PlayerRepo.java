package it.unicam.cs.mpgc.rpg126012.persistence;

import it.unicam.cs.mpgc.rpg126012.model.entity.Enemy;
import it.unicam.cs.mpgc.rpg126012.model.entity.Player;

import java.util.List;
import java.util.Optional;

public class PlayerRepo extends Repository<Player, Long> {
    public PlayerRepo() {
        super(Player.class);
    }

    public Optional<Player> findByName(String name) {
        return read(entityManager -> entityManager.createQuery(
                        "SELECT p FROM Player p WHERE p.name = :name", Player.class)
                .setParameter("name", name)
                .getResultStream().findFirst());
    }

}
