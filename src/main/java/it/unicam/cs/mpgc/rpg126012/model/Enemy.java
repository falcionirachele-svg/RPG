package it.unicam.cs.mpgc.rpg126012.model;
import lombok.*;
import jakarta.persistence.*;

import java.util.Random;

@Entity
@DiscriminatorValue("Enemy")
@NoArgsConstructor
@Getter
@Setter
public class Enemy extends GameCharacter {

    private static final Random random = new Random();

    public AttackResult attack(GameCharacter target) {
        Move move = chooseMove();
        return attack(move, target);
    }
    public Move chooseMove(){
        if(getMoves().isEmpty()) throw new IllegalArgumentException("No moves");
        int index= random.nextInt(getMoves().size());
        return getMoves().get(index);
    }
}
