package com.example.da1nhom9jav102.dao;

import com.example.da1nhom9jav102.entity.Brand;
import jakarta.persistence.EntityManager;

import java.util.List;

public class BrandDAO extends GenericDAO<Brand> {

    public BrandDAO() {
        super(Brand.class);
    }

    public List<Brand> findAllActive() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT b FROM Brand b WHERE b.active = true", Brand.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Brand> findByName(String name) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT b FROM Brand b WHERE b.name LIKE :name", Brand.class)
                    .setParameter("name", "%" + name + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
