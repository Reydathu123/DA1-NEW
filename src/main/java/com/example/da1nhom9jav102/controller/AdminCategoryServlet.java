package com.example.da1nhom9jav102.controller;

import com.example.da1nhom9jav102.entity.Category;
import com.example.da1nhom9jav102.service.CategoryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "AdminCategoryServlet", urlPatterns = {"/admin/categories", "/admin/categories/add",
        "/admin/categories/edit", "/admin/categories/delete"})
public class AdminCategoryServlet extends HttpServlet {
    private final CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        if (!AdminDashboardServlet.isAdmin(req, resp)) return;

        String path = req.getServletPath();
        switch (path) {
            case "/admin/categories/add":
                req.getRequestDispatcher("/WEB-INF/views/admin/categories/add.jsp").forward(req, resp);
                break;
            case "/admin/categories/edit":
                String idStr = req.getParameter("id");
                if (idStr != null) {
                    Optional<Category> cat = categoryService.findById(Integer.parseInt(idStr));
                    cat.ifPresent(c -> req.setAttribute("category", c));
                }
                req.getRequestDispatcher("/WEB-INF/views/admin/categories/edit.jsp").forward(req, resp);
                break;
            case "/admin/categories/delete":
                handleDelete(req, resp);
                break;
            default:
                req.setAttribute("categories", categoryService.findAll());
                req.getRequestDispatcher("/WEB-INF/views/admin/categories/list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        if (!AdminDashboardServlet.isAdmin(req, resp)) return;

        String path = req.getServletPath();
        switch (path) {
            case "/admin/categories/add":
                handleAdd(req, resp);
                break;
            case "/admin/categories/edit":
                handleEdit(req, resp);
                break;
        }
    }

    private void handleAdd(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String name = req.getParameter("name");
        String activeStr = req.getParameter("active");

        if (name == null || name.trim().isEmpty()) {
            req.setAttribute("error", "Tên danh mục không được để trống!");
            req.getRequestDispatcher("/WEB-INF/views/admin/categories/add.jsp").forward(req, resp);
            return;
        }

        Category category = new Category();
        category.setName(name.trim());
        category.setActive(activeStr != null);
        categoryService.save(category);

        resp.sendRedirect(req.getContextPath() + "/admin/categories?success=add");
    }

    private void handleEdit(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String activeStr = req.getParameter("active");

        Optional<Category> opt = categoryService.findById(id);
        if (opt.isPresent()) {
            Category category = opt.get();
            category.setName(name.trim());
            category.setActive(activeStr != null);
            categoryService.update(category);
        }

        resp.sendRedirect(req.getContextPath() + "/admin/categories?success=edit");
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idStr = req.getParameter("id");
        if (idStr != null) {
            try {
                categoryService.delete(Integer.parseInt(idStr));
                resp.sendRedirect(req.getContextPath() + "/admin/categories?success=delete");
            } catch (Exception e) {
                resp.sendRedirect(req.getContextPath() + "/admin/categories?error=delete");
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/admin/categories");
        }
    }
}
