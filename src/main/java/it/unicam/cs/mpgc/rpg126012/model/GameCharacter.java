package it.unicam.cs.mpgc.rpg126012.model;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@NoArgsConstructor
@Getter
@Setter
public class GameCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String name;
    protected int hp;
    protected int maxHp;
    @ManyToMany(fetch = FetchType.EAGER)
    protected List<Move> moves = new ArrayList<>();
    protected int defense;
    protected int attack;
    protected int level;

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

    public boolean isAlive(){
        return hp > 0;
    }

    public AttackResult attack(Move move, GameCharacter target) {
        int damage=0;
        boolean hit = move.hit();
        if(hit) {
            damage = Math.max(1, getAttack()*move.getPower()/100);
            target.takeDamage(damage);
        }
        return new AttackResult(this, move, damage, hit);
    }


}
