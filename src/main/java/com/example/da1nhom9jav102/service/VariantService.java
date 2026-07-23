package com.example.da1nhom9jav102.service;

import com.example.da1nhom9jav102.dao.VariantDAO;
import com.example.da1nhom9jav102.entity.Variant;

import java.util.List;
import java.util.Optional;

public class VariantService {
    private final VariantDAO variantDAO = new VariantDAO();

    public List<Variant> findAll() {
        return variantDAO.findAll();
    }

    public List<Variant> findAllWithDetails() {
        return variantDAO.findAllWithDetails();
    }

    public Optional<Variant> findById(Integer id) {
        return variantDAO.findById(id);
    }

    public Variant findByIdWithDetails(Integer id) {
        return variantDAO.findByIdWithDetails(id);
    }

    public List<Variant> findByRacketId(Integer racketId) {
        return variantDAO.findByRacketId(racketId);
    }

    public Variant save(Variant variant) {
        if (variant.getStock() == null) {
            variant.setStock(0);
        }
        return variantDAO.save(variant);
    }

    public Variant update(Variant variant) {
        return variantDAO.update(variant);
    }

    public void delete(Integer id) {
        variantDAO.delete(id);
    }

    public boolean updateStock(Integer variantId, int quantity) {
        return variantDAO.updateStock(variantId, quantity);
    }
}
