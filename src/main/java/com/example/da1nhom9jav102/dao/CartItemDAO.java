package com.example.da1nhom9jav102.dao;

import com.example.da1nhom9jav102.entity.CartItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;
import java.util.Optional;

public class CartItemDAO extends GenericDAO<CartItem> {

    public CartItemDAO() {
        super(CartItem.class);
    }

    public List<CartItem> findByCartId(Integer cartId) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                            "SELECT ci FROM CartItem ci LEFT JOIN FETCH ci.variant v " +
                                    "LEFT JOIN FETCH v.racket LEFT JOIN FETCH v.color " +
                                    "WHERE ci.cart.id = :cartId",
                            CartItem.class)
                    .setParameter("cartId", cartId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Optional<CartItem> findByCartIdAndVariantId(Integer cartId, Integer variantId) {
        EntityManager em = getEntityManager();
        try {
            CartItem item = em.createQuery(
                            "SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.variant.id = :variantId",
                            CartItem.class)
                    .setParameter("cartId", cartId)
                    .setParameter("variantId", variantId)
                    .getSingleResult();
            return Optional.of(item);
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }

    public void deleteByCartId(Integer cartId) {
        EntityManager em = getEntityManager();
        var tx = em.getTransaction();
        try {
            tx.begin();
            em.createQuery("DELETE FROM CartItem ci WHERE ci.cart.id = :cartId")
                    .setParameter("cartId", cartId)
                    .executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
