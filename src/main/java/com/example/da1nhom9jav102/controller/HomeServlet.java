package com.example.da1nhom9jav102.controller;

import com.example.da1nhom9jav102.entity.*;
import com.example.da1nhom9jav102.service.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomeServlet", urlPatterns = {"/home", "/product", "/search", "/products"})
public class HomeServlet extends HttpServlet {
    private final RacketService racketService = new RacketService();
    private final CategoryService categoryService = new CategoryService();
    private final BrandService brandService = new BrandService();
    private final CommonService commonService = new CommonService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String path = req.getServletPath();

        // Đặt categories và brands cho header
        req.setAttribute("categories", categoryService.findAllActive());
        req.setAttribute("brands", brandService.findAllActive());

        switch (path) {
            case "/product":
                showProductDetail(req, resp);
                break;
            case "/search":
                searchProducts(req, resp);
                break;
            case "/products":
                listProducts(req, resp);
                break;
            default:
                showHome(req, resp);
                break;
        }
    }

    private void showHome(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Racket> latestRackets = racketService.findLatest(8);
        List<Racket> discountedRackets = racketService.findDiscounted();

        req.setAttribute("latestRackets", latestRackets);
        req.setAttribute("discountedRackets", discountedRackets);
        req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
    }

    private void showProductDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        try {
            Integer id = Integer.parseInt(idStr);
            Racket racket = racketService.findByIdWithDetails(id);
            if (racket == null) {
                resp.sendRedirect(req.getContextPath() + "/");
                return;
            }

            List<Review> reviews = commonService.findReviewsByRacketId(id);
            Double avgRating = commonService.getAverageRating(id);

            req.setAttribute("racket", racket);
            req.setAttribute("reviews", reviews);
            req.setAttribute("avgRating", avgRating);
            req.getRequestDispatcher("/WEB-INF/views/product-detail.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/");
        }
    }

    private void searchProducts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        List<Racket> rackets;
        if (keyword != null && !keyword.trim().isEmpty()) {
            rackets = racketService.search(keyword.trim());
        } else {
            rackets = racketService.findAllActive();
        }
        req.setAttribute("rackets", rackets);
        req.setAttribute("keyword", keyword);
        req.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(req, resp);
    }

    private void listProducts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String categoryIdStr = req.getParameter("categoryId");
        String brandIdStr = req.getParameter("brandId");
        String gender = req.getParameter("gender");
        String minPriceStr = req.getParameter("minPrice");
        String maxPriceStr = req.getParameter("maxPrice");

        Integer categoryId = parseIntOrNull(categoryIdStr);
        Integer brandId = parseIntOrNull(brandIdStr);
        Double minPrice = parseDoubleOrNull(minPriceStr);
        Double maxPrice = parseDoubleOrNull(maxPriceStr);

        List<Racket> rackets;
        if (categoryId != null || brandId != null || (gender != null && !gender.isEmpty()) || minPrice != null || maxPrice != null) {
            rackets = racketService.filter(categoryId, brandId, gender, minPrice, maxPrice);
        } else {
            rackets = racketService.findAllActive();
        }

        req.setAttribute("rackets", rackets);
        req.setAttribute("selectedCategoryId", categoryId);
        req.setAttribute("selectedBrandId", brandId);
        req.setAttribute("selectedGender", gender);
        req.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(req, resp);
    }

    private Integer parseIntOrNull(String str) {
        try {
            return str != null && !str.isEmpty() ? Integer.parseInt(str) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Double parseDoubleOrNull(String str) {
        try {
            return str != null && !str.isEmpty() ? Double.parseDouble(str) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
