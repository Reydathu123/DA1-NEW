package com.example.da1nhom9jav102.dao;

import com.example.da1nhom9jav102.entity.Order;
import jakarta.persistence.EntityManager;

import java.util.List;

public class OrderDAO extends GenericDAO<Order> {

    public OrderDAO() {
        super(Order.class);
    }

    public List<Order> findByUserId(Integer userId) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                            "SELECT o FROM Order o WHERE o.user.id = :userId ORDER BY o.createdAt DESC",
                            Order.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Order findByIdWithDetails(Integer id) {
        EntityManager em = getEntityManager();
        try {
            List<Order> results = em.createQuery(
                            "SELECT o FROM Order o LEFT JOIN FETCH o.orderDetails od " +
                                    "LEFT JOIN FETCH od.variant v LEFT JOIN FETCH v.racket " +
                                    "LEFT JOIN FETCH v.color LEFT JOIN FETCH o.user " +
                                    "WHERE o.id = :id",
                            Order.class)
                    .setParameter("id", id)
                    .getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }

    public List<Order> findAllWithUser() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT o FROM Order o LEFT JOIN FETCH o.user ORDER BY o.createdAt DESC",
                    Order.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Order> findByStatus(String status) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                            "SELECT o FROM Order o LEFT JOIN FETCH o.user WHERE o.status = :status ORDER BY o.createdAt DESC",
                            Order.class)
                    .setParameter("status", status)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public long countByStatus(String status) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(o) FROM Order o WHERE o.status = :status", Long.class)
                    .setParameter("status", status)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    public Double getTotalRevenue() {
        EntityManager em = getEntityManager();
        try {
            Double result = em.createQuery(
                            "SELECT COALESCE(SUM(o.total), 0) FROM Order o WHERE o.status = 'delivered'",
                            Double.class)
                    .getSingleResult();
            return result != null ? result : 0.0;
        } finally {
            em.close();
        }
    }

    public Order findByCode(String code) {
        EntityManager em = getEntityManager();
        try {
            List<Order> results = em.createQuery("SELECT o FROM Order o WHERE o.code = :code", Order.class)
                    .setParameter("code", code)
                    .getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }
}
