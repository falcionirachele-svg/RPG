package it.unicam.cs.mpgc.rpg126012.persistence;


import it.unicam.cs.mpgc.rpg126012.model.entity.Enemy;

import java.util.List;

public class EnemyRepo extends Repository<Enemy, Long> {
    public EnemyRepo(){
        super(Enemy.class);
    }

    public List<Enemy> findByLevel(int level){
        return read(entityManager -> entityManager.createQuery(
                        "SELECT e FROM Enemy e WHERE e.level = :level", Enemy.class)
                .setParameter("level", level)
                .getResultList());
    }
}
