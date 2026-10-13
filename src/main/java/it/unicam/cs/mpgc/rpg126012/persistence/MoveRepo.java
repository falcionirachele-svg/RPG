package it.unicam.cs.mpgc.rpg126012.persistence;

import it.unicam.cs.mpgc.rpg126012.model.entity.Move;

import java.util.Optional;

public class MoveRepo extends Repository<Move, Long>{
    public MoveRepo(){
        super(Move.class);
    }

    public Optional<Move> findByName(String name){
        return read(em -> em.createQuery(
                        "SELECT m FROM Move m WHERE m.name = :name", Move.class)
                .setParameter("name", name)
                .getResultStream()
                .findFirst());
    }
}
