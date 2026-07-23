package com.example.da1nhom9jav102.controller;

import com.example.da1nhom9jav102.entity.Order;
import com.example.da1nhom9jav102.service.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminOrderServlet", urlPatterns = {"/admin/orders", "/admin/orders/detail", "/admin/orders/status"})
public class AdminOrderServlet extends HttpServlet {
    private final OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        if (!AdminDashboardServlet.isAdmin(req, resp)) return;

        String path = req.getServletPath();
        if ("/admin/orders/detail".equals(path)) {
            showDetail(req, resp);
        } else {
            listOrders(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        if (!AdminDashboardServlet.isAdmin(req, resp)) return;

        if ("/admin/orders/status".equals(req.getServletPath())) {
            Integer orderId = Integer.parseInt(req.getParameter("orderId"));
            String status = req.getParameter("status");
            orderService.updateStatus(orderId, status);
            resp.sendRedirect(req.getContextPath() + "/admin/orders/detail?id=" + orderId + "&success=status");
        }
    }

    private void listOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String statusFilter = req.getParameter("status");
        List<Order> orders;
        if (statusFilter != null && !statusFilter.isEmpty()) {
            orders = orderService.findByStatus(statusFilter);
        } else {
            orders = orderService.findAll();
        }
        req.setAttribute("orders", orders);
        req.setAttribute("statusFilter", statusFilter);
        req.getRequestDispatcher("/WEB-INF/views/admin/orders/list.jsp").forward(req, resp);
    }

    private void showDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr != null) {
            Order order = orderService.findByIdWithDetails(Integer.parseInt(idStr));
            req.setAttribute("order", order);
            req.setAttribute("orderDetails", orderService.findOrderDetails(Integer.parseInt(idStr)));
        }
        req.getRequestDispatcher("/WEB-INF/views/admin/orders/detail.jsp").forward(req, resp);
    }
}
