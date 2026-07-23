package com.example.da1nhom9jav102.dao;

import com.example.da1nhom9jav102.entity.Variant;
import jakarta.persistence.EntityManager;

import java.util.List;

public class VariantDAO extends GenericDAO<Variant> {

    public VariantDAO() {
        super(Variant.class);
    }

    public List<Variant> findByRacketId(Integer racketId) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                            "SELECT v FROM Variant v LEFT JOIN FETCH v.color LEFT JOIN FETCH v.size WHERE v.racket.id = :racketId",
                            Variant.class)
                    .setParameter("racketId", racketId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Variant> findAllWithDetails() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT v FROM Variant v LEFT JOIN FETCH v.racket LEFT JOIN FETCH v.color LEFT JOIN FETCH v.size",
                    Variant.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Variant findByIdWithDetails(Integer id) {
        EntityManager em = getEntityManager();
        try {
            List<Variant> results = em.createQuery(
                            "SELECT v FROM Variant v LEFT JOIN FETCH v.racket LEFT JOIN FETCH v.color LEFT JOIN FETCH v.size WHERE v.id = :id",
                            Variant.class)
                    .setParameter("id", id)
                    .getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }

    public boolean updateStock(Integer variantId, int quantity) {
        EntityManager em = getEntityManager();
        var tx = em.getTransaction();
        try {
            tx.begin();
            Variant v = em.find(Variant.class, variantId);
            if (v != null && v.getStock() >= quantity) {
                v.setStock(v.getStock() - quantity);
                em.merge(v);
                tx.commit();
                return true;
            }
            tx.rollback();
            return false;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
