package com.example.da1nhom9jav102.service;

import com.example.da1nhom9jav102.dao.CategoryDAO;
import com.example.da1nhom9jav102.entity.Category;

import java.util.List;
import java.util.Optional;

public class CategoryService {
    private final CategoryDAO categoryDAO = new CategoryDAO();

    public List<Category> findAll() {
        return categoryDAO.findAll();
    }

    public List<Category> findAllActive() {
        return categoryDAO.findAllActive();
    }

    public Optional<Category> findById(Integer id) {
        return categoryDAO.findById(id);
    }

    public Category save(Category category) {
        if (category.getActive() == null) {
            category.setActive(true);
        }
        return categoryDAO.save(category);
    }

    public Category update(Category category) {
        return categoryDAO.update(category);
    }

    public void delete(Integer id) {
        categoryDAO.delete(id);
    }

    public void toggleActive(Integer id) {
        Optional<Category> opt = categoryDAO.findById(id);
        opt.ifPresent(c -> {
            c.setActive(!c.getActive());
            categoryDAO.update(c);
        });
    }
}
