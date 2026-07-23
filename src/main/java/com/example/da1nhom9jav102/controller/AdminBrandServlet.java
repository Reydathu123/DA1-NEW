package com.example.da1nhom9jav102.controller;

import com.example.da1nhom9jav102.entity.Brand;
import com.example.da1nhom9jav102.service.BrandService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "AdminBrandServlet", urlPatterns = {"/admin/brands", "/admin/brands/add",
        "/admin/brands/edit", "/admin/brands/delete"})
public class AdminBrandServlet extends HttpServlet {
    private final BrandService brandService = new BrandService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        if (!AdminDashboardServlet.isAdmin(req, resp)) return;

        String path = req.getServletPath();
        switch (path) {
            case "/admin/brands/add":
                req.getRequestDispatcher("/WEB-INF/views/admin/brands/add.jsp").forward(req, resp);
                break;
            case "/admin/brands/edit":
                String idStr = req.getParameter("id");
                if (idStr != null) {
                    Optional<Brand> brand = brandService.findById(Integer.parseInt(idStr));
                    brand.ifPresent(b -> req.setAttribute("brand", b));
                }
                req.getRequestDispatcher("/WEB-INF/views/admin/brands/edit.jsp").forward(req, resp);
                break;
            case "/admin/brands/delete":
                handleDelete(req, resp);
                break;
            default:
                req.setAttribute("brands", brandService.findAll());
                req.getRequestDispatcher("/WEB-INF/views/admin/brands/list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        if (!AdminDashboardServlet.isAdmin(req, resp)) return;

        String path = req.getServletPath();
        switch (path) {
            case "/admin/brands/add":
                handleAdd(req, resp);
                break;
            case "/admin/brands/edit":
                handleEdit(req, resp);
                break;
        }
    }

    private void handleAdd(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String name = req.getParameter("name");
        String logo = req.getParameter("logo");
        String activeStr = req.getParameter("active");

        if (name == null || name.trim().isEmpty()) {
            req.setAttribute("error", "Tên thương hiệu không được để trống!");
            req.getRequestDispatcher("/WEB-INF/views/admin/brands/add.jsp").forward(req, resp);
            return;
        }

        Brand brand = new Brand();
        brand.setName(name.trim());
        brand.setLogo(logo != null ? logo.trim() : null);
        brand.setActive(activeStr != null);
        brandService.save(brand);

        resp.sendRedirect(req.getContextPath() + "/admin/brands?success=add");
    }

    private void handleEdit(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String logo = req.getParameter("logo");
        String activeStr = req.getParameter("active");

        Optional<Brand> opt = brandService.findById(id);
        if (opt.isPresent()) {
            Brand brand = opt.get();
            brand.setName(name.trim());
            brand.setLogo(logo != null ? logo.trim() : null);
            brand.setActive(activeStr != null);
            brandService.update(brand);
        }

        resp.sendRedirect(req.getContextPath() + "/admin/brands?success=edit");
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idStr = req.getParameter("id");
        if (idStr != null) {
            try {
                brandService.delete(Integer.parseInt(idStr));
                resp.sendRedirect(req.getContextPath() + "/admin/brands?success=delete");
            } catch (Exception e) {
                resp.sendRedirect(req.getContextPath() + "/admin/brands?error=delete");
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/admin/brands");
        }
    }
}
