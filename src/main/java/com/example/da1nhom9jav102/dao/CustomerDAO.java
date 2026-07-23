package com.example.da1nhom9jav102.dao;

import com.example.da1nhom9jav102.entity.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.Optional;

public class CustomerDAO extends GenericDAO<Customer> {

    public CustomerDAO() {
        super(Customer.class);
    }

    public Optional<Customer> findByUserId(Integer userId) {
        EntityManager em = getEntityManager();
        try {
            Customer customer = em.createQuery(
                            "SELECT c FROM Customer c LEFT JOIN FETCH c.user WHERE c.user.id = :userId",
                            Customer.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
            return Optional.of(customer);
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }
}
