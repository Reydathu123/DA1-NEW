package com.example.da1nhom9jav102.dao;

import com.example.da1nhom9jav102.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;
import java.util.Optional;

public class UserDAO extends GenericDAO<User> {

    public UserDAO() {
        super(User.class);
    }

    public Optional<User> findByEmail(String email) {
        EntityManager em = getEntityManager();
        try {
            User user = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }

    public Optional<User> findByEmailAndPassword(String email, String password) {
        EntityManager em = getEntityManager();
        try {
            User user = em.createQuery(
                            "SELECT u FROM User u WHERE u.email = :email AND u.password = :password AND u.active = true",
                            User.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }

    public List<User> findAllActive() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.active = true", User.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public boolean existsByEmail(String email) {
        EntityManager em = getEntityManager();
        try {
            long count = em.createQuery("SELECT COUNT(u) FROM User u WHERE u.email = :email", Long.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }
}
