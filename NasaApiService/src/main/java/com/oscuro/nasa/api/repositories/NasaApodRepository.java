package com.oscuro.nasa.api.repositories;

import com.oscuro.nasa.api.entities.NasaApodEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@ApplicationScoped
public class NasaApodRepository {

    @PersistenceContext
    EntityManager em;

    public void save(NasaApodEntity nasaApodEntity) {
        em.persist(nasaApodEntity);
    }

    public NasaApodEntity findById(Long id) {
        return em.find(NasaApodEntity.class, id);
    }

    public List<NasaApodEntity> listAll() {
        return em.createQuery("SELECT p FROM NASAAPODENTITY p", NasaApodEntity.class).getResultList();
    }

    public NasaApodEntity findByDate(String date) {
        List<NasaApodEntity> results = em.createQuery(
                        "SELECT p FROM NasaApodEntity p WHERE p.date = :date", NasaApodEntity.class)
                .setParameter("date", date)
                .getResultList();

        return results.isEmpty() ? null : results.get(0);
    }


}
