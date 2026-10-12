package it.unicam.cs.mpgc.rpg126012.model;
import lombok.*;
import jakarta.persistence.*;

import java.util.Random;

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

    private static final Random RANDOM = new Random();


    public Move(String name, int power, int accuracy) {
        this.name = name;
        this.power = power;
        this.accuracy = accuracy;
    }

    public boolean hit() {
        return RANDOM.nextInt(100) < accuracy;
    }

}
