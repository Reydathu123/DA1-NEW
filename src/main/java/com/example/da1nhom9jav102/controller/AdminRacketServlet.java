package com.example.da1nhom9jav102.controller;

import com.example.da1nhom9jav102.entity.*;
import com.example.da1nhom9jav102.service.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

@WebServlet(name = "AdminRacketServlet", urlPatterns = {"/admin/rackets", "/admin/rackets/add",
        "/admin/rackets/edit", "/admin/rackets/delete"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
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

    private void populateRacket(Racket racket, HttpServletRequest req) throws Exception {
        racket.setName(req.getParameter("name"));
        racket.setPrice(Double.parseDouble(req.getParameter("price")));

        String discountStr = req.getParameter("discount");
        racket.setDiscount(discountStr != null && !discountStr.isEmpty() ? Double.parseDouble(discountStr) : 0.0);

        Part filePart = req.getPart("imageFile");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String uniqueFileName = System.currentTimeMillis() + "_" + fileName;

            // Lấy đường dẫn thư mục images trong thư mục dự án khi đang chạy trên Tomcat
            String uploadPath = getServletContext().getRealPath("/") + "images";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();

            filePart.write(uploadPath + File.separator + uniqueFileName);
            racket.setImage(uniqueFileName);
        } else {
            String existingImage = req.getParameter("existingImage");
            if (existingImage != null && !existingImage.isEmpty()) {
                racket.setImage(existingImage);
            }
        }

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
