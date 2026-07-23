package com.example.da1nhom9jav102.controller;

import com.example.da1nhom9jav102.entity.*;
import com.example.da1nhom9jav102.service.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "OrderServlet", urlPatterns = {"/checkout", "/order", "/orders"})
public class OrderServlet extends HttpServlet {
    private final OrderService orderService = new OrderService();
    private final CartService cartService = new CartService();
    private final CategoryService categoryService = new CategoryService();
    private final BrandService brandService = new BrandService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        req.setAttribute("categories", categoryService.findAllActive());
        req.setAttribute("brands", brandService.findAllActive());

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String path = req.getServletPath();
        switch (path) {
            case "/checkout":
                showCheckout(req, resp, user, session);
                break;
            case "/order":
                showOrderDetail(req, resp, user);
                break;
            case "/orders":
                showOrderHistory(req, resp, user);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String path = req.getServletPath();
        if ("/checkout".equals(path)) {
            handleCheckout(req, resp, user, session);
        }
    }

    private void showCheckout(HttpServletRequest req, HttpServletResponse resp, User user, HttpSession session)
            throws ServletException, IOException {
        Cart cart = cartService.getOrCreateCart(user, session.getId());
        List<CartItem> cartItems = cartService.getCartItems(cart.getId());

        if (cartItems.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        double cartTotal = cartService.getCartTotal(cart.getId());

        req.setAttribute("cartItems", cartItems);
        req.setAttribute("cartTotal", cartTotal);
        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(req, resp);
    }

    private void handleCheckout(HttpServletRequest req, HttpServletResponse resp, User user, HttpSession session)
            throws ServletException, IOException {
        String shippingAddress = req.getParameter("shippingAddress");
        String paymentMethod = req.getParameter("paymentMethod");

        if (shippingAddress == null || shippingAddress.trim().isEmpty()) {
            shippingAddress = user.getAddress();
        }
        if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
            paymentMethod = "COD";
        }

        try {
            Cart cart = cartService.getOrCreateCart(user, session.getId());
            List<CartItem> cartItems = cartService.getCartItems(cart.getId());

            if (cartItems.isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/cart");
                return;
            }

            Order order = orderService.createOrder(user, shippingAddress, paymentMethod, cartItems);
            cartService.clearCart(cart.getId());

            resp.sendRedirect(req.getContextPath() + "/order?id=" + order.getId() + "&success=true");
        } catch (Exception e) {
            req.setAttribute("error", "Đặt hàng thất bại: " + e.getMessage());
            showCheckout(req, resp, user, session);
        }
    }

    private void showOrderDetail(HttpServletRequest req, HttpServletResponse resp, User user)
            throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr == null) {
            resp.sendRedirect(req.getContextPath() + "/orders");
            return;
        }

        try {
            Integer id = Integer.parseInt(idStr);
            Order order = orderService.findByIdWithDetails(id);

            if (order == null || (!user.isAdmin() && !order.getUser().getId().equals(user.getId()))) {
                resp.sendRedirect(req.getContextPath() + "/orders");
                return;
            }

            req.setAttribute("categories", categoryService.findAllActive());
            req.setAttribute("brands", brandService.findAllActive());
            req.setAttribute("order", order);
            req.setAttribute("orderDetails", orderService.findOrderDetails(id));
            req.setAttribute("success", req.getParameter("success"));
            req.getRequestDispatcher("/WEB-INF/views/order-detail.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/orders");
        }
    }

    private void showOrderHistory(HttpServletRequest req, HttpServletResponse resp, User user)
            throws ServletException, IOException {
        List<Order> orders = orderService.findByUserId(user.getId());
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/WEB-INF/views/order-history.jsp").forward(req, resp);
    }
}
