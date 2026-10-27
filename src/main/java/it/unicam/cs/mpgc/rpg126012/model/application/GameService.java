package it.unicam.cs.mpgc.rpg126012.model.application;

import it.unicam.cs.mpgc.rpg126012.model.entity.Enemy;
import it.unicam.cs.mpgc.rpg126012.model.entity.Move;
import it.unicam.cs.mpgc.rpg126012.model.entity.Player;
import it.unicam.cs.mpgc.rpg126012.persistence.EnemyRepo;
import it.unicam.cs.mpgc.rpg126012.persistence.MoveRepo;
import it.unicam.cs.mpgc.rpg126012.persistence.PlayerRepo;
import it.unicam.cs.mpgc.rpg126012.persistence.data.PlayerData;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**Classe contenente la logica di gioco.*/
@Getter
@Setter
public class GameService {

    private final MoveRepo moveRepo;
    private final PlayerRepo playerRepo;
    private final EnemyRepo enemyRepo;
    private final List<PlayerData> playersType;
    private final Random random;

    private Player player;
    private Battle currentBattle;
    private BattleResult battleResult;

    public GameService(PlayerRepo playerRepo, EnemyRepo enemyRepo, MoveRepo moveRepo,
                       List<PlayerData> playersType, Random random) {
        this.playerRepo = playerRepo;
        this.enemyRepo = enemyRepo;
        this.moveRepo = moveRepo;
        this.playersType = List.copyOf(playersType);
        this.random = random;
    }

    public Player newPlayer(String name, PlayerData type) {
        if(playerRepo.findByName(name).isPresent()) throw new IllegalArgumentException("Personaggio con questo nome gia esistente");
        player= playerRepo.save(new Player(name, type.hp(), type.maxhp(), type.shields(), type.attack(),1, toMoves(type.moves()), type.type()));
        return player;
    }

    public Player selectPlayer(String name){
        player = playerRepo.findByName(name).orElseThrow(()->new IllegalArgumentException("Player non trovato"));
        return player;
    }

    public List<PlayerData> getPlayersType() {
        return playersType;
    }

    public List<Player> getMatches(){
        return playerRepo.findAll();
    }

    /**Metodo di avvio battaglia: viene scelto un giocatore dello stesso livello del player*/
    public Battle startBattle(){
        if(player==null) throw new IllegalStateException("Player non selezionato");
        List<Enemy> enemies= enemyRepo.findByLevel(player.getLevel());
        Enemy enemy = enemies.get(random.nextInt(enemies.size()));
        enemy.setHp(enemy.getMaxHp());
        player.setHp(player.getMaxHp());

        currentBattle = new Battle(player, enemy, random);
        return currentBattle;

    }

    /**Fine di una battaglia, si sale di livello se viene vinta e poi viene salvata*/
    public void endBattle(){
        if(player.isAlive()){
            player.levelUp();
        }
        player.setHp(player.getMaxHp());
        player = playerRepo.save(player);
        battleResult = new BattleResult(currentBattle);

    }

    public Player getPlayer(){
        return player;
    }

    public Battle getCurrentBattle(){
        return currentBattle;
    }


    private List<Move> toMoves(List<String> moves) {
        return moves.stream().map(n -> moveRepo.findByName(n)
                        .orElseThrow(() -> new IllegalStateException("Mossa sconosciuta: " + n)))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
