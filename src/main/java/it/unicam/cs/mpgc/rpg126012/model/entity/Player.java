package it.unicam.cs.mpgc.rpg126012.model.entity;
import lombok.*;
import jakarta.persistence.*;

import java.util.List;

@Entity
@DiscriminatorValue("Player")
@NoArgsConstructor
@Getter
@Setter
public class Player extends GameCharacter {
    private String type;
    public Player(String name, int hp, int maxHp, int shields, int attack, int level, List<Move> moves, String type){
        super(name, hp, maxHp, shields, attack, level, moves);
        this.type= type;
    }
}
