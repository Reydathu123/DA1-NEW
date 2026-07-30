package com.example.da1nhom9jav102.controller;

import com.example.da1nhom9jav102.entity.Brand;
import com.example.da1nhom9jav102.service.BrandService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

@WebServlet(name = "AdminBrandServlet", urlPatterns = {"/admin/brands", "/admin/brands/add",
        "/admin/brands/edit", "/admin/brands/delete"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
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
        String activeStr = req.getParameter("active");

        if (name == null || name.trim().isEmpty()) {
            req.setAttribute("error", "Tên thương hiệu không được để trống!");
            req.getRequestDispatcher("/WEB-INF/views/admin/brands/add.jsp").forward(req, resp);
            return;
        }

        Brand brand = new Brand();
        brand.setName(name.trim());
        Part logoPart = req.getPart("logoFile");
        if (logoPart != null && logoPart.getSize() > 0) {
            String fileName = Paths.get(logoPart.getSubmittedFileName()).getFileName().toString();
            String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
            String uploadPath = getServletContext().getRealPath("/") + "images";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();

            logoPart.write(uploadPath + File.separator + uniqueFileName);
            brand.setLogo(uniqueFileName);
        }
        brand.setActive(activeStr != null);
        brandService.save(brand);

        resp.sendRedirect(req.getContextPath() + "/admin/brands?success=add");
    }

    private void handleEdit(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String activeStr = req.getParameter("active");

        Optional<Brand> opt = brandService.findById(id);
        if (opt.isPresent()) {
            Brand brand = opt.get();
            brand.setName(name.trim());
            brand.setActive(activeStr != null);

            Part logoPart = req.getPart("logoFile");
            if (logoPart != null && logoPart.getSize() > 0) {
                String fileName = Paths.get(logoPart.getSubmittedFileName()).getFileName().toString();
                String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
                String uploadPath = getServletContext().getRealPath("/") + "images";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();

                logoPart.write(uploadPath + File.separator + uniqueFileName);
                brand.setLogo(uniqueFileName);
            }


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