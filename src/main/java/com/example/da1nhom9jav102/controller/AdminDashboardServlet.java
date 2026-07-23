package com.example.da1nhom9jav102.controller;

import com.example.da1nhom9jav102.entity.User;
import com.example.da1nhom9jav102.service.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "AdminDashboardServlet", urlPatterns = {"/admin/dashboard"})
public class AdminDashboardServlet extends HttpServlet {
    private final RacketService racketService = new RacketService();
    private final OrderService orderService = new OrderService();
    private final UserService userService = new UserService();
    private final CategoryService categoryService = new CategoryService();
    private final BrandService brandService = new BrandService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        if (!isAdmin(req, resp)) return;

        long totalProducts = racketService.count();
        long totalOrders = orderService.count();
        long totalUsers = userService.count();
        Double totalRevenue = orderService.getTotalRevenue();
        long pendingOrders = orderService.countByStatus("pending");
        long shippingOrders = orderService.countByStatus("shipping");

        req.setAttribute("totalProducts", totalProducts);
        req.setAttribute("totalOrders", totalOrders);
        req.setAttribute("totalUsers", totalUsers);
        req.setAttribute("totalRevenue", totalRevenue);
        req.setAttribute("pendingOrders", pendingOrders);
        req.setAttribute("shippingOrders", shippingOrders);

        req.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(req, resp);
    }

    public static boolean isAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return false;
        }
        User user = (User) session.getAttribute("user");
        if (!user.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/");
            return false;
        }
        return true;
    }
}
