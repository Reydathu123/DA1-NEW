package com.example.da1nhom9jav102.dao;

import com.example.da1nhom9jav102.entity.Review;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ReviewDAO extends GenericDAO<Review> {

    public ReviewDAO() {
        super(Review.class);
    }

    public List<Review> findByRacketId(Integer racketId) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                            "SELECT r FROM Review r LEFT JOIN FETCH r.user WHERE r.racket.id = :racketId ORDER BY r.createdAt DESC",
                            Review.class)
                    .setParameter("racketId", racketId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Double getAverageRating(Integer racketId) {
        EntityManager em = getEntityManager();
        try {
            Double avg = em.createQuery(
                            "SELECT AVG(r.rating) FROM Review r WHERE r.racket.id = :racketId", Double.class)
                    .setParameter("racketId", racketId)
                    .getSingleResult();
            return avg != null ? avg : 0.0;
        } finally {
            em.close();
        }
    }
}
