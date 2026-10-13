package it.unicam.cs.mpgc.rpg126012.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@AllArgsConstructor
public abstract class Repository<T, ID> {
    private final Class<T> entityClass;

    public T save(T entity){
        return inTransaction(em -> em.merge(entity));
    }

    public void delete (T entity){
        inTransaction(em -> {
            em.remove(em.merge(entity));
            return null;
        });
    }

    public Optional<T> findById(ID id) {
        return read(em -> Optional.ofNullable(em.find(entityClass, id)));
    }

    public List<T> findAll() {
        return read(em -> em.createQuery(
                        "SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
                .getResultList());
    }

    /*
    * Metodo per la lettura a db, apre e chiude em
    * */
    protected <R> R read(Function<EntityManager, R> action) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return action.apply(em);
        }
    }


    /*
    * Metodo per modificare db, apre l'EntityManager, gestisce la transazione e fa rollback in caso di errore.
    * */
    protected <R> R inTransaction(Function<EntityManager, R> action) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                R result = action.apply(em);
                tx.commit();
                return result;
            } catch (RuntimeException e) {
                if (tx.isActive()) tx.rollback();
                throw e;
            }
        }
    }
}
