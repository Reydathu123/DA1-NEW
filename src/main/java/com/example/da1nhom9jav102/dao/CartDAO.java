package com.example.da1nhom9jav102.dao;

import com.example.da1nhom9jav102.entity.Cart;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.Optional;

public class CartDAO extends GenericDAO<Cart> {

    public CartDAO() {
        super(Cart.class);
    }

    public Optional<Cart> findByUserId(Integer userId) {
        EntityManager em = getEntityManager();
        try {
            Cart cart = em.createQuery(
                            "SELECT c FROM Cart c LEFT JOIN FETCH c.cartItems ci " +
                                    "LEFT JOIN FETCH ci.variant v LEFT JOIN FETCH v.racket LEFT JOIN FETCH v.color " +
                                    "WHERE c.user.id = :userId",
                            Cart.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
            return Optional.of(cart);
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }

    public Optional<Cart> findBySessionId(String sessionId) {
        EntityManager em = getEntityManager();
        try {
            Cart cart = em.createQuery(
                            "SELECT c FROM Cart c LEFT JOIN FETCH c.cartItems ci " +
                                    "LEFT JOIN FETCH ci.variant v LEFT JOIN FETCH v.racket LEFT JOIN FETCH v.color " +
                                    "WHERE c.sessionId = :sessionId",
                            Cart.class)
                    .setParameter("sessionId", sessionId)
                    .getSingleResult();
            return Optional.of(cart);
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }
}
