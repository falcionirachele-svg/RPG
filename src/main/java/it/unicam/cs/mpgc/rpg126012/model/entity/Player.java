package it.unicam.cs.mpgc.rpg126012.model.entity;
import lombok.*;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Player")
@NoArgsConstructor
@Getter
@Setter
public class Player extends GameCharacter {

}
