package it.unicam.cs.mpgc.rpg126012.model.entity;
import lombok.*;
import jakarta.persistence.*;

import java.util.List;

@Entity
@DiscriminatorValue("Enemy")
@NoArgsConstructor
@Getter
@Setter
public class Enemy extends GameCharacter {
    public Enemy(String name, int hp, int maxhp, int shields, int attack, int level, List<Move> moves){
        super(name, hp, maxhp, shields, attack, level, moves);
    }

}
