package com.example.da1nhom9jav102.service;

import com.example.da1nhom9jav102.dao.RacketDAO;
import com.example.da1nhom9jav102.entity.Racket;

import java.util.List;
import java.util.Optional;

public class RacketService {
    private final RacketDAO racketDAO = new RacketDAO();

    public List<Racket> findAll() {
        return racketDAO.findAll();
    }

    public List<Racket> findAllActive() {
        return racketDAO.findAllActive();
    }

    public List<Racket> findAllWithDetails() {
        return racketDAO.findAllWithDetails();
    }

    public Optional<Racket> findById(Integer id) {
        return racketDAO.findById(id);
    }

    public Racket findByIdWithDetails(Integer id) {
        return racketDAO.findByIdWithDetails(id);
    }

    public List<Racket> findByCategory(Integer categoryId) {
        return racketDAO.findByCategory(categoryId);
    }

    public List<Racket> findByBrand(Integer brandId) {
        return racketDAO.findByBrand(brandId);
    }

    public List<Racket> search(String keyword) {
        return racketDAO.search(keyword);
    }

    public List<Racket> findLatest(int limit) {
        return racketDAO.findLatest(limit);
    }

    public List<Racket> findDiscounted() {
        return racketDAO.findDiscounted();
    }

    public List<Racket> filter(Integer categoryId, Integer brandId, String gender, Double minPrice, Double maxPrice) {
        return racketDAO.filter(categoryId, brandId, gender, minPrice, maxPrice);
    }

    public Racket save(Racket racket) {
        if (racket.getActive() == null) {
            racket.setActive(true);
        }
        if (racket.getDiscount() == null) {
            racket.setDiscount(0.0);
        }
        return racketDAO.save(racket);
    }

    public Racket update(Racket racket) {
        return racketDAO.update(racket);
    }

    public void delete(Integer id) {
        racketDAO.delete(id);
    }

    public long countActive() {
        return racketDAO.countActive();
    }

    public long count() {
        return racketDAO.count();
    }
}
