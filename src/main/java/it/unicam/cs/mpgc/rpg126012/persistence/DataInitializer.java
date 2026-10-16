package it.unicam.cs.mpgc.rpg126012.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unicam.cs.mpgc.rpg126012.model.entity.Enemy;
import it.unicam.cs.mpgc.rpg126012.model.entity.Move;
import it.unicam.cs.mpgc.rpg126012.model.entity.Player;
import it.unicam.cs.mpgc.rpg126012.persistence.data.EnemyData;
import it.unicam.cs.mpgc.rpg126012.persistence.data.GameData;
import it.unicam.cs.mpgc.rpg126012.persistence.data.PlayerData;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** Popola il database al primo avvio con i dati di data.json. */
public class DataInitializer {
    private static final String DATA_FILE = "/data/data.json";

    private final EnemyRepo enemyRepo;
    private final MoveRepo moveRepo;
    private final ObjectMapper mapper = new ObjectMapper();

    public DataInitializer(EnemyRepo enemyRepo, MoveRepo moveRepo) {
        this.enemyRepo = enemyRepo;
        this.moveRepo = moveRepo;
    }

    public void init() {
        if (!moveRepo.findAll().isEmpty()) return;   // già popolato

        GameData data = readData();
        data.moves().forEach(m -> moveRepo.save(new Move(m.name(), m.power(), m.accuracy())));
        data.enemies().forEach(e -> enemyRepo.save(toEnemy(e)));
        List<PlayerData> playerDataList= data.players();
    }

    public List<PlayerData> getPlayerDataList() {
        return readData().players();
    }

    private GameData readData() {
        try (InputStream in = DataInitializer.class.getResourceAsStream(DATA_FILE)) {
            if (in == null) throw new IllegalStateException("File non trovato: " + DATA_FILE);
            return mapper.readValue(in, GameData.class);
        } catch (IOException e) {
            throw new UncheckedIOException("Errore nella lettura di " + DATA_FILE, e);
        }
    }

    private Enemy toEnemy(EnemyData data) {
        Enemy enemy = new Enemy();
        enemy.setName(data.name());
        enemy.setLevel(data.level());
        enemy.setMaxHp(data.hp());
        enemy.setHp(data.hp());
        enemy.setAttack(data.attack());
        enemy.setShields(data.shields());
        enemy.setMoves(data.moves().stream()
                .map(name -> moveRepo.findByName(name).orElseThrow(() ->
                        new IllegalStateException("Il nemico " + data.name() + " usa la mossa sconosciuta " + name)))
                .collect(Collectors.toCollection(ArrayList::new)));
        return enemy;
    }
}