package it.unicam.cs.mpgc.rpg126012.persistence;
import jakarta.persistence.*;

public class JPAUtil {
    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("rpgPU");
    private JPAUtil(){}
    public static EntityManager getEntityManager(){
        return factory.createEntityManager();
    }
    public static void close(){
        factory.close();
    }
}
