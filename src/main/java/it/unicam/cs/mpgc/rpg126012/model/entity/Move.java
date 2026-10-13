package it.unicam.cs.mpgc.rpg126012.model.entity;
import lombok.*;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Move {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    private int power;
    //probabilità di colpire il nemico
    private int accuracy;

    public Move(String name, int power, int accuracy) {
        this.name = name;
        this.power = power;
        this.accuracy = accuracy;
    }

}
