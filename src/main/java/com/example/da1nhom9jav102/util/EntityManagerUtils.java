package com.example.da1nhom9jav102.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerUtils {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("da1nhom9jav102");
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
