package com.example.da1nhom9jav102.service;

import com.example.da1nhom9jav102.dao.CustomerDAO;
import com.example.da1nhom9jav102.dao.UserDAO;
import com.example.da1nhom9jav102.entity.Customer;
import com.example.da1nhom9jav102.entity.User;

import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserDAO userDAO = new UserDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();

    public Optional<User> login(String email, String password) {
        return userDAO.findByEmailAndPassword(email, password);
    }

    public User register(String email, String password, String fullName, String phone, String address) {
        if (userDAO.existsByEmail(email)) {
            throw new RuntimeException("Email đã tồn tại!");
        }
        User user = new User(email, password, fullName, phone, address, false, true);
        User savedUser = userDAO.save(user);

        // Tạo customer profile
        Customer customer = new Customer();
        customer.setUser(savedUser);
        customer.setMembership("Bronze");
        customer.setPoints(0);
        customerDAO.save(customer);

        return savedUser;
    }

    public Optional<User> findById(Integer id) {
        return userDAO.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    public List<User> findAll() {
        return userDAO.findAll();
    }

    public List<User> findAllActive() {
        return userDAO.findAllActive();
    }

    public User update(User user) {
        return userDAO.update(user);
    }

    public boolean existsByEmail(String email) {
        return userDAO.existsByEmail(email);
    }

    public long count() {
        return userDAO.count();
    }
}
