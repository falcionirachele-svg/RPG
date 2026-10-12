package it.unicam.cs.mpgc.rpg126012.model;
import lombok.*;
import jakarta.persistence.*;

import java.util.List;

@Entity
@DiscriminatorValue("Player")
@NoArgsConstructor
@Getter
@Setter
public class Player extends GameCharacter{

    public Move chooseMove(int index){
        return getMoves().get(index);
    }
}
