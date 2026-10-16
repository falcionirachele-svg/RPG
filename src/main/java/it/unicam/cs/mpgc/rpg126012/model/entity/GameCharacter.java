package it.unicam.cs.mpgc.rpg126012.model.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@NoArgsConstructor
@Getter
@Setter
public abstract class GameCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String name;
    protected int hp;
    protected int maxHp;
    @ManyToMany(fetch = FetchType.EAGER)
    protected List<Move> moves = new ArrayList<>();
    protected int shields;
    protected int attack;
    protected int level;

    public GameCharacter(String name, int hp, int maxHp, int shields, int attack, int level, List<Move> moves){
        this.name= name;
        this.hp=hp;
        this.maxHp=maxHp;
        this.shields=shields;
        this.attack=attack;
        this.level=level;
        this.moves=moves;
    }

    public int takeDamage(int damage) {
        return hp = Math.max(0, hp-damage);
    }

    public int heal(int amount) {
        return hp = Math.min(maxHp, hp+amount);
    }

    public int levelUp() {
        level++;
        maxHp += 10;
        hp = maxHp;
        return level;
    }

    public int shield(int amount) {
        return shields = Math.min(60, shields+amount);
    }

    public boolean isAlive(){
        return hp > 0;
    }

    public Move chooseMove(int index){
        if(index<0 || index>=getMoves().size()) throw new IllegalArgumentException("Index out of bounds");
        return getMoves().get(index);
    }


}
