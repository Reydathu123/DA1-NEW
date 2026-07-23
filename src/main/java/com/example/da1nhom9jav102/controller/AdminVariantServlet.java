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

@WebServlet(name = "AdminVariantServlet", urlPatterns = {"/admin/variants", "/admin/variants/add",
        "/admin/variants/edit", "/admin/variants/delete"})
public class AdminVariantServlet extends HttpServlet {
    private final VariantService variantService = new VariantService();
    private final RacketService racketService = new RacketService();
    private final CommonService commonService = new CommonService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        if (!AdminDashboardServlet.isAdmin(req, resp)) return;

        String path = req.getServletPath();
        switch (path) {
            case "/admin/variants/add":
                setFormAttributes(req);
                req.getRequestDispatcher("/WEB-INF/views/admin/variants/add.jsp").forward(req, resp);
                break;
            case "/admin/variants/edit":
                String idStr = req.getParameter("id");
                if (idStr != null) {
                    Variant variant = variantService.findByIdWithDetails(Integer.parseInt(idStr));
                    req.setAttribute("variant", variant);
                }
                setFormAttributes(req);
                req.getRequestDispatcher("/WEB-INF/views/admin/variants/edit.jsp").forward(req, resp);
                break;
            case "/admin/variants/delete":
                handleDelete(req, resp);
                break;
            default:
                req.setAttribute("variants", variantService.findAllWithDetails());
                req.getRequestDispatcher("/WEB-INF/views/admin/variants/list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        if (!AdminDashboardServlet.isAdmin(req, resp)) return;

        String path = req.getServletPath();
        if ("/admin/variants/add".equals(path)) {
            handleAdd(req, resp);
        } else if ("/admin/variants/edit".equals(path)) {
            handleEdit(req, resp);
        }
    }

    private void setFormAttributes(HttpServletRequest req) {
        req.setAttribute("rackets", racketService.findAllActive());
        req.setAttribute("colors", commonService.findAllColors());
        req.setAttribute("sizes", commonService.findAllSizes());
    }

    private void handleAdd(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            Variant variant = new Variant();
            populateVariant(variant, req);
            variantService.save(variant);
            resp.sendRedirect(req.getContextPath() + "/admin/variants?success=add");
        } catch (Exception e) {
            req.setAttribute("error", "Loi: " + e.getMessage());
            setFormAttributes(req);
            req.getRequestDispatcher("/WEB-INF/views/admin/variants/add.jsp").forward(req, resp);
        }
    }

    private void handleEdit(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            Integer id = Integer.parseInt(req.getParameter("id"));
            Optional<Variant> opt = variantService.findById(id);
            if (opt.isPresent()) {
                Variant variant = opt.get();
                populateVariant(variant, req);
                variantService.update(variant);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/variants?success=edit");
        } catch (Exception e) {
            req.setAttribute("error", "Loi: " + e.getMessage());
            setFormAttributes(req);
            req.getRequestDispatcher("/WEB-INF/views/admin/variants/edit.jsp").forward(req, resp);
        }
    }

    private void populateVariant(Variant variant, HttpServletRequest req) {
        String stockStr = req.getParameter("stock");
        variant.setStock(stockStr != null && !stockStr.isEmpty() ? Integer.parseInt(stockStr) : 0);
        variant.setGripSize(req.getParameter("gripSize"));
        variant.setFlex(req.getParameter("flex"));
        variant.setBalance(req.getParameter("balance"));
        String weightStr = req.getParameter("weight");
        variant.setWeight(weightStr != null && !weightStr.isEmpty() ? Double.parseDouble(weightStr) : null);
        String racketIdStr = req.getParameter("racketId");
        if (racketIdStr != null && !racketIdStr.isEmpty()) {
            racketService.findById(Integer.parseInt(racketIdStr)).ifPresent(variant::setRacket);
        }
        String colorIdStr = req.getParameter("colorId");
        if (colorIdStr != null && !colorIdStr.isEmpty()) {
            commonService.findColorById(Integer.parseInt(colorIdStr)).ifPresent(variant::setColor);
        }
        String sizeIdStr = req.getParameter("sizeId");
        if (sizeIdStr != null && !sizeIdStr.isEmpty()) {
            commonService.findSizeById(Integer.parseInt(sizeIdStr)).ifPresent(variant::setSize);
        }
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idStr = req.getParameter("id");
        if (idStr != null) {
            try {
                variantService.delete(Integer.parseInt(idStr));
                resp.sendRedirect(req.getContextPath() + "/admin/variants?success=delete");
            } catch (Exception e) {
                resp.sendRedirect(req.getContextPath() + "/admin/variants?error=delete");
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/admin/variants");
        }
    }
}
