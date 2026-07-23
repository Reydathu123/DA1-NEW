package com.example.da1nhom9jav102.dao;

import com.example.da1nhom9jav102.entity.Category;
import jakarta.persistence.EntityManager;

import java.util.List;

public class CategoryDAO extends GenericDAO<Category> {

    public CategoryDAO() {
        super(Category.class);
    }

    public List<Category> findAllActive() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Category c WHERE c.active = true", Category.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Category> findByName(String name) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Category c WHERE c.name LIKE :name", Category.class)
                    .setParameter("name", "%" + name + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
