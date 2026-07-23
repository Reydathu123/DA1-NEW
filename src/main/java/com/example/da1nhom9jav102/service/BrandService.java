package com.example.da1nhom9jav102.service;

import com.example.da1nhom9jav102.dao.BrandDAO;
import com.example.da1nhom9jav102.entity.Brand;

import java.util.List;
import java.util.Optional;

public class BrandService {
    private final BrandDAO brandDAO = new BrandDAO();

    public List<Brand> findAll() {
        return brandDAO.findAll();
    }

    public List<Brand> findAllActive() {
        return brandDAO.findAllActive();
    }

    public Optional<Brand> findById(Integer id) {
        return brandDAO.findById(id);
    }

    public Brand save(Brand brand) {
        if (brand.getActive() == null) {
            brand.setActive(true);
        }
        return brandDAO.save(brand);
    }

    public Brand update(Brand brand) {
        return brandDAO.update(brand);
    }

    public void delete(Integer id) {
        brandDAO.delete(id);
    }

    public void toggleActive(Integer id) {
        Optional<Brand> opt = brandDAO.findById(id);
        opt.ifPresent(b -> {
            b.setActive(!b.getActive());
            brandDAO.update(b);
        });
    }
}
