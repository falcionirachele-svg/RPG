package it.unicam.cs.mpgc.rpg126012.model.entity;
import lombok.*;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Enemy")
@NoArgsConstructor
@Getter
@Setter
public class Enemy extends GameCharacter {

}
