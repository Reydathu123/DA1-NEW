package com.example.da1nhom9jav102.dao;

import com.example.da1nhom9jav102.entity.Racket;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class RacketDAO extends GenericDAO<Racket> {

    public RacketDAO() {
        super(Racket.class);
    }

    public List<Racket> findAllActive() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT r FROM Racket r LEFT JOIN FETCH r.category LEFT JOIN FETCH r.brand WHERE r.active = true",
                    Racket.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Racket> findByCategory(Integer categoryId) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                            "SELECT r FROM Racket r LEFT JOIN FETCH r.brand WHERE r.category.id = :catId AND r.active = true",
                            Racket.class)
                    .setParameter("catId", categoryId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Racket> findByBrand(Integer brandId) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                            "SELECT r FROM Racket r LEFT JOIN FETCH r.category WHERE r.brand.id = :brandId AND r.active = true",
                            Racket.class)
                    .setParameter("brandId", brandId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Racket> search(String keyword) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                            "SELECT r FROM Racket r LEFT JOIN FETCH r.category LEFT JOIN FETCH r.brand " +
                                    "WHERE r.active = true AND (r.name LIKE :kw OR r.description LIKE :kw)",
                            Racket.class)
                    .setParameter("kw", "%" + keyword + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Racket findByIdWithDetails(Integer id) {
        EntityManager em = getEntityManager();
        try {
            List<Racket> results = em.createQuery(
                            "SELECT r FROM Racket r LEFT JOIN FETCH r.category LEFT JOIN FETCH r.brand " +
                                    "LEFT JOIN FETCH r.variants v LEFT JOIN FETCH v.color LEFT JOIN FETCH v.size " +
                                    "WHERE r.id = :id",
                            Racket.class)
                    .setParameter("id", id)
                    .getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }

    public List<Racket> findAllWithDetails() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT DISTINCT r FROM Racket r LEFT JOIN FETCH r.category LEFT JOIN FETCH r.brand",
                    Racket.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Racket> findLatest(int limit) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                            "SELECT r FROM Racket r LEFT JOIN FETCH r.brand WHERE r.active = true ORDER BY r.id DESC",
                            Racket.class)
                    .setMaxResults(limit)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Racket> findDiscounted() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                            "SELECT r FROM Racket r LEFT JOIN FETCH r.brand WHERE r.active = true AND r.discount > 0 ORDER BY r.discount DESC",
                            Racket.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Racket> findWithPagination(int page, int size) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                            "SELECT r FROM Racket r LEFT JOIN FETCH r.category LEFT JOIN FETCH r.brand WHERE r.active = true ORDER BY r.id DESC",
                            Racket.class)
                    .setFirstResult((page - 1) * size)
                    .setMaxResults(size)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public long countActive() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(r) FROM Racket r WHERE r.active = true", Long.class)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    public List<Racket> filter(Integer categoryId, Integer brandId, String gender, Double minPrice, Double maxPrice) {
        EntityManager em = getEntityManager();
        try {
            StringBuilder jpql = new StringBuilder(
                    "SELECT r FROM Racket r LEFT JOIN FETCH r.category LEFT JOIN FETCH r.brand WHERE r.active = true");

            if (categoryId != null) jpql.append(" AND r.category.id = :catId");
            if (brandId != null) jpql.append(" AND r.brand.id = :brandId");
            if (gender != null && !gender.isEmpty()) jpql.append(" AND r.gender = :gender");
            if (minPrice != null) jpql.append(" AND r.price >= :minPrice");
            if (maxPrice != null) jpql.append(" AND r.price <= :maxPrice");

            jpql.append(" ORDER BY r.id DESC");

            TypedQuery<Racket> query = em.createQuery(jpql.toString(), Racket.class);

            if (categoryId != null) query.setParameter("catId", categoryId);
            if (brandId != null) query.setParameter("brandId", brandId);
            if (gender != null && !gender.isEmpty()) query.setParameter("gender", gender);
            if (minPrice != null) query.setParameter("minPrice", minPrice);
            if (maxPrice != null) query.setParameter("maxPrice", maxPrice);

            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
