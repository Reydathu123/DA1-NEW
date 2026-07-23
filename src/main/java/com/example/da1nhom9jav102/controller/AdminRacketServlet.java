package com.example.da1nhom9jav102.controller;

import com.example.da1nhom9jav102.entity.*;
import com.example.da1nhom9jav102.service.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "AdminRacketServlet", urlPatterns = {"/admin/rackets", "/admin/rackets/add",
        "/admin/rackets/edit", "/admin/rackets/delete"})
public class AdminRacketServlet extends HttpServlet {
    private final RacketService racketService = new RacketService();
    private final CategoryService categoryService = new CategoryService();
    private final BrandService brandService = new BrandService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        if (!AdminDashboardServlet.isAdmin(req, resp)) return;

        String path = req.getServletPath();
        switch (path) {
            case "/admin/rackets/add":
                req.setAttribute("categories", categoryService.findAllActive());
                req.setAttribute("brands", brandService.findAllActive());
                req.getRequestDispatcher("/WEB-INF/views/admin/rackets/add.jsp").forward(req, resp);
                break;
            case "/admin/rackets/edit":
                String idStr = req.getParameter("id");
                if (idStr != null) {
                    Racket racket = racketService.findByIdWithDetails(Integer.parseInt(idStr));
                    req.setAttribute("racket", racket);
                }
                req.setAttribute("categories", categoryService.findAllActive());
                req.setAttribute("brands", brandService.findAllActive());
                req.getRequestDispatcher("/WEB-INF/views/admin/rackets/edit.jsp").forward(req, resp);
                break;
            case "/admin/rackets/delete":
                handleDelete(req, resp);
                break;
            default:
                req.setAttribute("rackets", racketService.findAllWithDetails());
                req.getRequestDispatcher("/WEB-INF/views/admin/rackets/list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        if (!AdminDashboardServlet.isAdmin(req, resp)) return;

        String path = req.getServletPath();
        switch (path) {
            case "/admin/rackets/add":
                handleAdd(req, resp);
                break;
            case "/admin/rackets/edit":
                handleEdit(req, resp);
                break;
        }
    }

    private void handleAdd(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            Racket racket = new Racket();
            populateRacket(racket, req);
            racketService.save(racket);
            resp.sendRedirect(req.getContextPath() + "/admin/rackets?success=add");
        } catch (Exception e) {
            req.setAttribute("error", "Lỗi: " + e.getMessage());
            req.setAttribute("categories", categoryService.findAllActive());
            req.setAttribute("brands", brandService.findAllActive());
            req.getRequestDispatcher("/WEB-INF/views/admin/rackets/add.jsp").forward(req, resp);
        }
    }

    private void handleEdit(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            Integer id = Integer.parseInt(req.getParameter("id"));
            Optional<Racket> opt = racketService.findById(id);
            if (opt.isPresent()) {
                Racket racket = opt.get();
                populateRacket(racket, req);
                racketService.update(racket);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/rackets?success=edit");
        } catch (Exception e) {
            req.setAttribute("error", "Lỗi: " + e.getMessage());
            req.setAttribute("categories", categoryService.findAllActive());
            req.setAttribute("brands", brandService.findAllActive());
            req.getRequestDispatcher("/WEB-INF/views/admin/rackets/edit.jsp").forward(req, resp);
        }
    }

    private void populateRacket(Racket racket, HttpServletRequest req) {
        racket.setName(req.getParameter("name"));
        racket.setPrice(Double.parseDouble(req.getParameter("price")));

        String discountStr = req.getParameter("discount");
        racket.setDiscount(discountStr != null && !discountStr.isEmpty() ? Double.parseDouble(discountStr) : 0.0);

        racket.setImage(req.getParameter("image"));
        racket.setDescription(req.getParameter("description"));
        racket.setMaterial(req.getParameter("material"));
        racket.setGender(req.getParameter("gender"));
        racket.setActive(req.getParameter("active") != null);

        String catIdStr = req.getParameter("categoryId");
        if (catIdStr != null && !catIdStr.isEmpty()) {
            categoryService.findById(Integer.parseInt(catIdStr)).ifPresent(racket::setCategory);
        }
        String brandIdStr = req.getParameter("brandId");
        if (brandIdStr != null && !brandIdStr.isEmpty()) {
            brandService.findById(Integer.parseInt(brandIdStr)).ifPresent(racket::setBrand);
        }
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idStr = req.getParameter("id");
        if (idStr != null) {
            try {
                racketService.delete(Integer.parseInt(idStr));
                resp.sendRedirect(req.getContextPath() + "/admin/rackets?success=delete");
            } catch (Exception e) {
                resp.sendRedirect(req.getContextPath() + "/admin/rackets?error=delete");
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/admin/rackets");
        }
    }
}
