package com.example.da1nhom9jav102.service;

import com.example.da1nhom9jav102.dao.ColorDAO;
import com.example.da1nhom9jav102.dao.SizeDAO;
import com.example.da1nhom9jav102.dao.ReviewDAO;
import com.example.da1nhom9jav102.entity.Color;
import com.example.da1nhom9jav102.entity.Size;
import com.example.da1nhom9jav102.entity.Review;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class CommonService {
    private final ColorDAO colorDAO = new ColorDAO();
    private final SizeDAO sizeDAO = new SizeDAO();
    private final ReviewDAO reviewDAO = new ReviewDAO();

    // Color
    public List<Color> findAllColors() {
        return colorDAO.findAll();
    }

    public Optional<Color> findColorById(Integer id) {
        return colorDAO.findById(id);
    }

    // Size
    public List<Size> findAllSizes() {
        return sizeDAO.findAll();
    }

    public Optional<Size> findSizeById(Integer id) {
        return sizeDAO.findById(id);
    }

    // Review
    public List<Review> findReviewsByRacketId(Integer racketId) {
        return reviewDAO.findByRacketId(racketId);
    }

    public Double getAverageRating(Integer racketId) {
        return reviewDAO.getAverageRating(racketId);
    }

    public Review saveReview(Review review) {
        review.setCreatedAt(LocalDate.now());
        return reviewDAO.save(review);
    }
}
